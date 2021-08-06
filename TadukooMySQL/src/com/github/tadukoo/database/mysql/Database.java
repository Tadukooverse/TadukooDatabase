package com.github.tadukoo.database.mysql;

import com.github.tadukoo.database.mysql.transaction.InsertAndGetID;
import com.github.tadukoo.database.mysql.transaction.query.Query;
import com.github.tadukoo.database.mysql.transaction.SQLTransaction;
import com.github.tadukoo.database.mysql.transaction.update.Updates;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import com.github.tadukoo.util.logger.EasyLogger;
import org.mariadb.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A class used to connect to a MySQL database and make queries, 
 * updates, etc. to it.
 * 
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 * @since Alpha v.0.1
 */
public class Database{
	// TODO: Make this configurable
	private static final int MAX_ATTEMPTS = 10;
	
	private final EasyLogger logger;
	private final String host;
	private final int port;
	private final String dbName;
	private final String user;
	private final String pass;
	
	public Database(EasyLogger logger, String host, int port, String dbName, String user, String pass){
		this.logger = logger;
		this.host = host;
		this.port = port;
		this.dbName = dbName;
		this.user = user;
		this.pass = pass;
	}
	
	// Load the MySQL JDBC driver
	static{
		try{
			Class.forName(Driver.class.getCanonicalName());
		}catch(Exception e){
			throw new IllegalStateException("Could not load JDBC driver");
		}
	}
	
	/**
	 * Creates a {@link Connection} to a MySQL database with the url and login information 
	 * that was set in the constructor of this Database class.
	 * 
	 * @return The Connection that's been created
	 */
	private Connection connect() throws SQLException{
		// Create the connection with the appropriate url and login credentials
		Connection conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbName + 
														"?user=" + user + "&password=" + pass);
		// Disable auto-commit to allow for transactions
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	/**
	 * Runs a SQL transaction. Will attempt {@link #MAX_ATTEMPTS} times until it works, 
	 * before throwing a {@link SQLException} if it doesn't work in that many attempts. 
	 * 
	 * @param <ResultType> The type of result to be returned
	 * @param transaction The {@link SQLTransaction} to run
	 * @return The result from the transaction
	 * @throws SQLException If anything goes wrong
	 */
	public <ResultType> ResultType executeTransaction(SQLTransaction<ResultType> transaction) throws SQLException{
		// Create the connection
		Connection conn = connect();
		
		// boolean to say when to stop - used in case a null result is returned
		boolean success = false;
		// Keep track of attempts for when to give up
		int attempts = 0;
		
		// Attempt to grab a result until it works or we hit the max attempts
		ResultType result = null;
		while(!success && attempts < MAX_ATTEMPTS){
			try{
				result = transaction.execute(conn, logger);
				conn.commit();
				success = true;
			}catch(SQLException e){
				logger.logError("Failed to execute " + transaction.getTransactionName(), e);
				attempts++;
			}
		}
		
		// Throw an exception if it fails
		if(!success){
			throw new SQLException("Failed to execute transaction");
		}
		
		return result;
	}
	
	/**
	 * Executes a sql query after building a {@link Query} object for 
	 * it from the given pieces. Returns the result of the query.
	 * 
	 * @param <ResultType> The type of result to be returned
	 * @param name The name to use for the query (for debugging purposes - may be null)
	 * @param sql The sql query string to run
	 * @param convertFromResultSet The {@link ThrowingFunction} to use to run the query
	 * @return The result from the query
	 */
	public <ResultType> ResultType executeQuery(String name, String sql, 
			ThrowingFunction<ResultSet, ResultType, SQLException> convertFromResultSet) throws SQLException{
		return executeTransaction(Query.createQuery(name, sql, convertFromResultSet));
	}
	
	// TODO: Rework this (Move to DBUtil and such)
	public<ResultType> ResultType doSearch(ThrowingFunction<ResultSet, ResultType, SQLException> convertFromResultSet,
			String returnPieces, String mainTable, 
			Collection<String> otherTables, Collection<String> junctions, 
			String[] intArgs, int[] intValues, 
			boolean[] partialStrings, String[] stringArgs, String[] stringValues) throws SQLException{
		if(junctions != null && junctions.size() > 0 && (otherTables == null || otherTables.size() == 0)){
			throw new IllegalArgumentException("Database.doSearch: Need otherTables to do junctions!");
		}
		if(intArgs.length != intValues.length){
			throw new IllegalArgumentException("Database.doSearch: intArgs and intValues are different sizes!");
		}
		if(stringArgs.length != stringValues.length || stringArgs.length != partialStrings.length){
			throw new IllegalArgumentException("Database.doSearch: partialStrings, stringArgs, and stringValues must be the same "
					+ "size!");
		}
		StringBuilder name = new StringBuilder();
		StringBuilder sql = new StringBuilder("select distinct " + returnPieces + " from " + mainTable);
		boolean prevSet = false;
		if(otherTables != null && otherTables.size() > 0){
			for(String table: otherTables){
				sql.append(", ").append(table);
			}
		}
		boolean searchAll = junctions == null || junctions.size() == 0;
		for(int i = 0; searchAll && i < intValues.length; i++){
			if(intValues[i] != -1){
				searchAll = false;
				break;
			}
		}
		for(int i = 0; searchAll && i < stringValues.length; i++){
			String s = stringValues[i];
			if(s != null && !s.equalsIgnoreCase("")){
				searchAll = false;
				break;
			}
		}
		if(searchAll){
			name.append("Get all ").append(mainTable).append("s");
		}else{
			name.append("Get ").append(mainTable).append("s with ");
			sql.append(" where ");
			
			if(junctions != null){
				for(String junction: junctions){
					DBUtil.addJunctionStringToQuery(prevSet, name, sql, junction);
					prevSet = true;
				}
			}
			for(int i = 0; i < intValues.length; i++){
				prevSet = (DBUtil.addConditionalIntToQuery(prevSet, name, sql, intArgs[i], intValues[i])) || prevSet;
			}
			for(int i = 0; i < stringValues.length; i++){
				prevSet = (DBUtil.addConditionalStringToQuery(prevSet, name, sql, partialStrings[i],
						stringArgs[i], stringValues[i])) || prevSet;
			}
		}
		
		System.out.println("Name: " + name.toString());
		System.out.println("SQL: " + sql.toString());
		
		return executeQuery(name.toString(), sql.toString(), convertFromResultSet);
	}
	
	/**
	 * Executes sql updates and returns if they were a success. This version 
	 * builds the {@link Updates} object using the given parameters.
	 *
	 * @param transactionName The name for the overall transaction
	 * @param names The names to use for the updates (optional - used for debugging)
	 * @param sqls The sql update statements to run
	 * @return If it succeeded or not
	 */
	public boolean executeUpdates(String transactionName, List<String> names, List<String> sqls) throws SQLException{
		return executeTransaction(Updates.createUpdates(transactionName, names, sqls));
	}
	
	/**
	 * Executes a single sql update and returns if it was a success. 
	 * This version sends the name and statement to 
	 * {@link #executeUpdates(String, List, List) the plural version} to create the
	 * {@link Updates} object.
	 * 
	 * @param name The name to use for the update (optional - used for debugging)
	 * @param sql The sql update statement to run
	 * @return If it succeeded or not
	 */
	public boolean executeUpdate(String name, String sql) throws SQLException{
		return executeUpdates(name, Collections.singletonList(name), Collections.singletonList(sql));
	}
	
	public void insert(String table, String[] args, String[] values) throws SQLException{
		executeUpdate("Insert a " + table, DBUtil.formatInsertStatement(table, args, values));
	}
	
	public Integer insertAndGetID(String table, String id_str, String[] args, String[] values) throws SQLException{
		return executeTransaction(InsertAndGetID.createInsertAndGetID(table, id_str, args, values));
	}
}
