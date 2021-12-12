package com.github.tadukoo.database.mysql;

import com.github.tadukoo.database.mysql.syntax.SQLSyntaxUtil;
import com.github.tadukoo.database.mysql.transaction.InsertAndGetID;
import com.github.tadukoo.database.mysql.transaction.query.Query;
import com.github.tadukoo.database.mysql.transaction.SQLTransaction;
import com.github.tadukoo.database.mysql.transaction.update.Updates;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import com.github.tadukoo.util.logger.EasyLogger;
import org.mariadb.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A class used to connect to a MySQL database and make queries, updates, etc. to it.
 * 
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 * @since Alpha v.0.1
 */
public class Database{
	
	/**
	 * A Builder to use to build a {@link Database}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>Database Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Default or Required</th>
	 *     </tr>
	 *     <tr>
	 *         <td>logger</td>
	 *         <td>The {@link EasyLogger logger} to use for logging</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>host</td>
	 *         <td>The MySQL host url</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>port</td>
	 *         <td>The MySQL host port</td>
	 *         <td>Defaults to 3306</td>
	 *     </tr>
	 *     <tr>
	 *         <td>databaseName</td>
	 *         <td>The MySQL host database name</td>
	 *         <td>Defaults to null (used e.g. when creating the database on the server)</td>
	 *     </tr>
	 *     <tr>
	 *         <td>username</td>
	 *         <td>The MySQL username for connecting to the Database</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>password</td>
	 *         <td>The MySQL password for connecting to the Database</td>
	 *         <td>Required, may be the empty string for a blank password</td>
	 *     </tr>
	 *     <tr>
	 *         <td>maxAttempts</td>
	 *         <td>The maximum number of attempts to try a SQL transaction before giving up</td>
	 *         <td>Defaults to 10</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class DatabaseBuilder implements Logger, Host, PortOrDatabaseNameOrUsername, DatabaseNameOrUsername,
			Username, Password, MaxAttemptsOrBuild, Build{
		/** The {@link EasyLogger logger} to use for logging */
		private EasyLogger logger;
		/** The MySQL host url */
		private String host;
		/** The MySQL host port */
		private int port = 3306;
		/** The MySQL host database name */
		private String databaseName = null;
		/** The MySQL username for connecting to the Database */
		private String username;
		/** The MySQL password for connecting to the Database */
		private String password;
		/** The maximum number of attempts to try a SQL transaction before giving up */
		private int maxAttempts = 10;
		
		/** Not allowed to instantiate outside of Database */
		private DatabaseBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		public Host logger(EasyLogger logger){
			this.logger = logger;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public PortOrDatabaseNameOrUsername host(String host){
			this.host = host;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public DatabaseNameOrUsername port(int port){
			this.port = port;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Username databaseName(String databaseName){
			this.databaseName = databaseName;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Password username(String username){
			this.username = username;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public MaxAttemptsOrBuild password(String password){
			this.password = password;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Build maxAttempts(int maxAttempts){
			this.maxAttempts = maxAttempts;
			return this;
		}
		
		/**
		 * Checks for any errors with the set parameters and will throw an IllegalArgumentException
		 * if any are found
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// logger is required
			if(logger == null){
				errors.add("logger is required!");
			}
			
			// host is required
			if(StringUtil.isBlank(host)){
				errors.add("host is required!");
			}
			
			// username is required
			if(StringUtil.isBlank(username)){
				errors.add("username is required!");
			}
			
			// password is required (but may be empty string for blank password)
			if(password == null){
				errors.add("password is required! (empty string is allowed for a blank password)");
			}
			
			// Report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Encountered errors in building a Database: \n" +
						StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/** {@inheritDoc} */
		@Override
		public Database build(){
			checkForErrors();
			
			return new Database(logger, host, port, databaseName, username, password, maxAttempts);
		}
	}
	
	// Load the MySQL JDBC driver
	static{
		try{
			Class.forName(Driver.class.getCanonicalName());
		}catch(Exception e){
			throw new IllegalStateException("Could not load JDBC driver");
		}
	}
	
	/** The {@link EasyLogger logger} to use for logging */
	private final EasyLogger logger;
	/** The MySQL host url */
	private final String host;
	/** The MySQL host port */
	private final int port;
	/** The MySQL host database name */
	private final String databaseName;
	/** The MySQL username for connecting to the database */
	private final String username;
	/** The MySQL password for connecting to the database */
	private final String password;
	/** The maximum number of attempts to try a SQL transaction before giving up */
	private final int maxAttempts;
	
	/**
	 * Constructs a new Database with the given parameters
	 *
	 * @param logger The {@link EasyLogger logger} to use for logging
	 * @param host The MySQL host url
	 * @param port The MySQL host port
	 * @param databaseName The MySQL host database name
	 * @param username The MySQL username for connecting to the database
	 * @param password The MySQL password for connecting to the database
	 * @param maxAttempts The maximum number of attempts to try a SQL transaction before giving up
	 */
	private Database(
			EasyLogger logger, String host, int port, String databaseName, String username, String password,
			int maxAttempts){
		this.logger = logger;
		this.host = host;
		this.port = port;
		this.databaseName = databaseName;
		this.username = username;
		this.password = password;
		this.maxAttempts = maxAttempts;
	}
	
	/**
	 * @return A new {@link DatabaseBuilder builder} to use to make a {@link Database}
	 */
	public static Logger builder(){
		return new DatabaseBuilder();
	}
	
	/**
	 * @return The {@link EasyLogger logger} to use for logging
	 */
	public EasyLogger getLogger(){
		return logger;
	}
	
	/**
	 * @return The connection URL (includes host, port, databaseName, but not login credentials)
	 */
	public String getConnectionURL(){
		// Put the jdbc MySQL portion on
		StringBuilder url = new StringBuilder("jdbc:mysql://");
		
		// Start with host + port
		url.append(host).append(":").append(port);
		
		// Add database name if we have it
		if(StringUtil.isNotBlank(databaseName)){
			url.append('/').append(databaseName);
		}
		
		return url.toString();
	}
	
	/**
	 * @return The maximum number of attempts to try a SQL transaction before giving up
	 */
	public int getMaxAttempts(){
		return maxAttempts;
	}
	
	/**
	 * Creates a {@link Connection} to a MySQL database with the url and login information 
	 * that was set in the constructor of this Database class.
	 * 
	 * @return The Connection that's been created
	 * @throws SQLException If anything goes wrong
	 */
	private Connection connect() throws SQLException{
		// Create the connection with the appropriate url and login credentials
		Connection conn = DriverManager.getConnection(getConnectionURL(), username, password);
		// Disable auto-commit to allow for transactions
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	/**
	 * Runs a SQL transaction. Will attempt {@link #maxAttempts} times until it works,
	 * before throwing a {@link SQLException} if it doesn't work in that many attempts. 
	 * 
	 * @param <ResultType> The type of result to be returned
	 * @param transaction The {@link SQLTransaction} to run
	 * @return The result from the transaction
	 * @throws SQLException If anything goes wrong
	 */
	public <ResultType> ResultType executeTransaction(SQLTransaction<ResultType> transaction) throws SQLException{
		// Create the connection
		try(Connection conn = connect()){
			// boolean to say when to stop - used in case a null result is returned
			boolean success = false;
			// Keep track of attempts for when to give up
			int attempts = 0;
			
			// Attempt to grab a result until it works or we hit the max attempts
			ResultType result = null;
			while(!success && attempts < maxAttempts){
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
				conn.rollback();
				String error = "Failed to execute transaction after " + maxAttempts + " attempts";
				logger.logError(error);
				throw new SQLException(error);
			}
			
			return result;
		}
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
	 * @throws SQLException If anything goes wrong
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
	 * @throws SQLException If anything goes wrong
	 */
	public boolean executeUpdates(String transactionName, List<String> names, List<String> sqls) throws SQLException{
		return executeTransaction(Updates.createUpdates(transactionName, names, sqls));
	}
	
	/**
	 * Executes a single sql update and returns if it was a success.
	 * <br><br>
	 * This version sends the name and statement to 
	 * {@link #executeUpdates(String, List, List) the plural version} to create the
	 * {@link Updates} object.
	 * 
	 * @param name The name to use for the update (optional - used for debugging)
	 * @param sql The sql update statement to run
	 * @return If it succeeded or not
	 * @throws SQLException If anything goes wrong
	 */
	public boolean executeUpdate(String name, String sql) throws SQLException{
		return executeUpdates(name, Collections.singletonList(name), Collections.singletonList(sql));
	}
	
	/**
	 * Executes a single sql insert statement.
	 * <br><br>
	 * This sends the table, cols, and values to
	 * {@link SQLSyntaxUtil#formatInsertStatement(String, Collection, Collection)} to create the insert statement
	 * and then uses {@link #executeUpdate(String, String)} to run it
	 *
	 * @param table The name of the table to insert into
	 * @param cols The names of the columns to insert into
	 * @param values The values to be inserted
	 * @throws SQLException If anything goes wrong
	 */
	public void insert(String table, Collection<String> cols, Collection<Object> values) throws SQLException{
		executeUpdate("Insert a " + table, SQLSyntaxUtil.formatInsertStatement(table, cols, values));
	}
	
	/**
	 * Executes a sql insert statement and then performs a query to retrieve an id (useful if the ID is
	 * auto-incremented).
	 * <br><br>
	 * To do this, a {@link InsertAndGetID} transaction object is created using the given parameters
	 *
	 * @param table The name of the table to insert into
	 * @param idColumnName The column name for the ID column
	 * @param cols The column names for the columns to use in the insert
	 * @param values The values to be inserted
	 * @return The ID found from the newly inserted data
	 * @throws SQLException If anything goes wrong
	 */
	public Integer insertAndGetID(String table, String idColumnName, Collection<String> cols, Collection<Object> values)
			throws SQLException{
		return executeTransaction(InsertAndGetID.createInsertAndGetID(table, idColumnName, cols, values));
	}
	
	/**
	 * Executes a single sql update statement
	 * <br><br>
	 * This sends the table, cols, values, whereCols, and whereValues to
	 * {@link SQLSyntaxUtil#formatUpdateStatement(String, Collection, Collection, Collection, Collection)} to create
	 * the update statement and then uses {@link #executeUpdate(String, String)} to run it
	 *
	 * @param table The name of the table to be updated
	 * @param cols The names of the columns to be updated
	 * @param values The values to use in the update
	 * @param whereCols The names of the columns for the where statement (can be empty/null)
	 * @param whereValues The values to use for the where statement (can be empty/null)
	 * @throws SQLException If anything goes wrong
	 */
	public void update(
			String table, Collection<String> cols, Collection<Object> values,
			Collection<String> whereCols, Collection<Object> whereValues) throws SQLException{
		executeUpdate("Update a " + table,
				SQLSyntaxUtil.formatUpdateStatement(table, cols, values, whereCols, whereValues));
	}
	
	/*
	 * Builder interfaces
	 */
	
	/**
	 * The {@link EasyLogger logger} part of building a {@link Database}
	 */
	public interface Logger{
		/**
		 * @param logger The {@link EasyLogger logger} to use for logging
		 * @return this, to continue building
		 */
		Host logger(EasyLogger logger);
	}
	
	/**
	 * The host URL part of building a {@link Database}
	 */
	public interface Host{
		/**
		 * @param host The MySQL host url
		 * @return this, to continue building
		 */
		PortOrDatabaseNameOrUsername host(String host);
	}
	
	/**
	 * The host port or host database or username part of building a {@link Database}
	 */
	public interface PortOrDatabaseNameOrUsername extends DatabaseNameOrUsername{
		/**
		 * @param port The MySQL host port
		 * @return this, to continue building
		 */
		DatabaseNameOrUsername port(int port);
	}
	
	/**
	 * The host database name or username part of building a {@link Database}
	 */
	public interface DatabaseNameOrUsername extends Username{
		/**
		 * @param databaseName The MySQL host database name
		 * @return this, to continue building
		 */
		Username databaseName(String databaseName);
	}
	
	/**
	 * The username part of building a {@link Database}
	 */
	public interface Username{
		/**
		 * @param username The MySQL username for connecting to the database
		 * @return this, to continue building
		 */
		Password username(String username);
	}
	
	/**
	 * The password part of building a {@link Database}
	 */
	public interface Password{
		/**
		 * @param password The MySQL password for connecting to the database
		 * @return this, to continue building
		 */
		MaxAttemptsOrBuild password(String password);
	}
	
	/**
	 * The max attempts or building part of building a {@link Database}
	 */
	public interface MaxAttemptsOrBuild extends Build{
		/**
		 * @param maxAttempts The maximum amount of attempts to try a SQL transaction before giving up
		 * @return this, to continue building
		 */
		Build maxAttempts(int maxAttempts);
	}
	
	/**
	 * The building part of building a {@link Database}
	 */
	public interface Build{
		/**
		 * Builds a new {@link Database} with the set parameters
		 *
		 * @return The newly built {@link Database}
		 */
		Database build();
	}
}
