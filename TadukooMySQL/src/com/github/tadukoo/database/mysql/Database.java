package com.github.tadukoo.database.mysql;

import com.github.tadukoo.database.mysql.syntax.SQLSyntaxUtil;
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
	
	// TODO: Make a builder, with port, dbName being optional, pass allowing empty string but not null
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
		// Create the connection string
		String connString = "jdbc:mysql://" + host;
		if(port != -1){
		
		}
		
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
	
	public void insert(String table, Collection<String> cols, Collection<Object> values) throws SQLException{
		executeUpdate("Insert a " + table, SQLSyntaxUtil.formatInsertStatement(table, cols, values));
	}
	
	public Integer insertAndGetID(String table, String id_str, Collection<String> cols, Collection<Object> values) throws SQLException{
		return executeTransaction(InsertAndGetID.createInsertAndGetID(table, id_str, cols, values));
	}
}
