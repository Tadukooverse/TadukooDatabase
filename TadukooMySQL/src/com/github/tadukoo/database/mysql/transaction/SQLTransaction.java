package com.github.tadukoo.database.mysql.transaction;

import com.github.tadukoo.util.logger.EasyLogger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Represents a transaction in MySQL
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 * @param <ResultType> The type of result returned from executing the transaction
 */
public interface SQLTransaction<ResultType>{
	
	/**
	 * @return The name of the transaction, used for logging purposes
	 */
	String getTransactionName();
	
	/**
	 * Executes the transaction using the given connection, and will log messages as needed to the given
	 * {@link EasyLogger}
	 *
	 * @param connection The {@link Connection} to use for the transaction
	 * @param logger The {@link EasyLogger} to use for logging messages as needed
	 * @return The result of the transaction
	 * @throws SQLException If anything goes wrong
	 */
	ResultType execute(Connection connection, EasyLogger logger) throws SQLException;
}
