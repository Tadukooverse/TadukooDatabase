package com.github.tadukoo.database.mysql.transaction.query;

import com.github.tadukoo.database.mysql.transaction.SQLTransaction;
import com.github.tadukoo.util.AutoCloseableUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import com.github.tadukoo.util.logger.EasyLogger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Represents a single statement query in MySQL, to be executed as a transaction.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 * @param <ResultType> The type of result returned from the query
 */
public abstract class Query<ResultType> implements SQLTransaction<ResultType>{
	
	/**
	 * @return The SQL statement to be used for the query
	 */
	public abstract String getSQL();
	
	/** {@inheritDoc} */
	@Override
	public ResultType execute(Connection conn, EasyLogger logger) throws SQLException{
		// We'll need a statement and result set for this transaction
		Statement stmt = null;
		ResultSet resultSet = null;
		
		try{
			// Report that we're starting the query
			String name = getTransactionName();
			logger.logInfo("Running query " + name);
			
			// Create and run the statement
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(getSQL());
			
			// Report that we finished the query
			logger.logInfo("Finished query " + name);
			
			// Convert the ResultSet to the proper type and return it
			return convertFromResultSet(resultSet);
		}finally{
			// If we fail, close the statement and result set quietly
			AutoCloseableUtil.closeQuietly(stmt);
			AutoCloseableUtil.closeQuietly(resultSet);
		}
	}
	
	/**
	 * Converts the given {@link ResultSet} to the proper {@link ResultType} to be returned
	 * from the query.
	 *
	 * @param resultSet The {@link ResultSet} to be converted
	 * @return The result converted from the {@link ResultSet}
	 * @throws SQLException If something goes wrong during conversion
	 */
	public abstract ResultType convertFromResultSet(ResultSet resultSet) throws SQLException;
	
	/**
	 * Creates a new {@link Query} using the given transaction name, MySQL query statement,
	 * and {@link ResultSet} conversion function.
	 *
	 * @param name The MySQL transaction name
	 * @param sql The MySQL query statement to be executed
	 * @param convertFromResultSet A method to convert the {@link ResultSet} to the proper {@link ResultType}
	 * @param <ResultType> The type of result to be returned from the Query
	 * @return A {@link Query} object
	 */
	public static <ResultType> Query<ResultType> createQuery(
			String name, String sql, ThrowingFunction<ResultSet, ResultType, SQLException> convertFromResultSet){
		return new Query<>(){
			
			/** {@inheritDoc} */
			@Override
			public String getTransactionName(){
				return StringUtil.isNotBlank(name)?name:sql;
			}
			
			/** {@inheritDoc} */
			@Override
			public String getSQL(){
				return sql;
			}
			
			/** {@inheritDoc} */
			@Override
			public ResultType convertFromResultSet(ResultSet resultSet) throws SQLException{
				return convertFromResultSet.apply(resultSet);
			}
		};
	}
}
