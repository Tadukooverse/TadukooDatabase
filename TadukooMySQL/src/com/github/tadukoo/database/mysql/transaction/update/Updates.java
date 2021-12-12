package com.github.tadukoo.database.mysql.transaction.update;

import com.github.tadukoo.database.mysql.transaction.SQLTransaction;
import com.github.tadukoo.util.AutoCloseableUtil;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.logger.EasyLogger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents one or more MySQL update statements to be executed as a transaction.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public abstract class Updates implements SQLTransaction<Boolean>{
	
	/**
	 * @return The names of the MySQL statements to be executed
	 */
	public List<String> getNames(){
		return null;
	}
	
	/**
	 * @return The List of MySQL statements to be executed as updates
	 */
	public abstract List<String> getSQLs();
	
	/** {@inheritDoc} */
	@Override
	public Boolean execute(Connection conn, EasyLogger logger) throws SQLException{
		// Grab names and sql strings and determine if we have names
		List<String> names = getNames();
		List<String> sqls = getSQLs();
		boolean haveNames = ListUtil.isNotBlank(names);
		
		// We'll need statements to run
		ArrayList<Statement> stmts = new ArrayList<>();
		
		try{
			// Report that we're starting the transaction
			logger.logInfo("Starting " + getTransactionName() + " transaction");
			
			// If we have names, make sure the size matches the sql strings size
			if(haveNames && names.size() != sqls.size()){
				throw new IllegalArgumentException("Must have all sql statements named or pass null names list!");
			}
			
			// Run the updates
			for(int i = 0; i < sqls.size(); i++){
				// Report starting this statement
				if(haveNames){
					logger.logInfo("Starting " + names.get(i) + " statement");
				}
				
				// Execute this statement
				stmts.add(conn.createStatement());
				stmts.get(i).executeUpdate(sqls.get(i));
				
				// Report finishing this statement
				if(haveNames){
					logger.logInfo("Finished " + names.get(i) + " statement");
				}
			}
			
			// Report that the transaction is done
			logger.logInfo("Finished " + getTransactionName() + " transaction");
			
			// Return true, that we succeeded
			return true;
		}finally{
			// If we fail, close the statements quietly
			for(Statement stmt: stmts){
				AutoCloseableUtil.closeQuietly(stmt);
			}
		}
	}
	
	/**
	 * Creates a new Updates transaction with the given parameters
	 *
	 * @param transactionName The name for the overall transaction
	 * @param names The names for individual statements, this may be null or an empty list
	 * @param sqls The MySQL statements to be executed in the updates
	 * @return An Updates object that can run the transaction
	 */
	public static Updates createUpdates(String transactionName, List<String> names, List<String> sqls){
		return new Updates(){
			/** {@inheritDoc} */
			@Override
			public String getTransactionName(){
				return transactionName;
			}
			
			/** {@inheritDoc} */
			@Override
			public List<String> getNames(){
				return names;
			}
			
			/** {@inheritDoc} */
			@Override
			public List<String> getSQLs(){
				return sqls;
			}
		};
	}
}
