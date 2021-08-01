package com.github.tadukoo.database.mysql.transaction;

import com.github.tadukoo.database.mysql.DBUtil;
import com.github.tadukoo.util.AutoCloseableUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.logger.EasyLogger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Insert and Get ID is a {@link SQLTransaction} that will run an insert statement on a table and then
 * retrieve the id of the new entry.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public abstract class InsertAndGetID implements SQLTransaction<Integer>{
	
	/**
	 * @return The string to report when the insert is run (can be blank for no reporting)
	 */
	public String getInsertString(){
		return null;
	}
	
	/**
	 * @return The MySQL string to use for the insert statement
	 */
	public abstract String getInsertSQL();
	
	/**
	 * @return The string to report when the id retrieval is run (can be blank for no reporting)
	 */
	public String getSelectString(){
		return null;
	}
	
	/**
	 * @return The MySQL string to use for retrieving the id from the table
	 */
	public abstract String getSelectSQL();
	
	/** {@inheritDoc} */
	@Override
	public Integer execute(Connection conn, EasyLogger logger) throws SQLException{
		// We need two statements and a ResultSet for our execution
		Statement insert = null;
		Statement selectID = null;
		ResultSet id = null;
		
		// Grab strings to use for reporting
		String name = getTransactionName();
		String insertString = getInsertString();
		String selectString = getSelectString();
		
		try{
			logger.logInfo("Starting execution of " + name);
			
			// Execute Insert Statement
			insert = conn.createStatement();
			String insertSQL = getInsertSQL();
			insert.executeUpdate(insertSQL);
			// Log that we finished the insert if we have an insert string
			if(StringUtil.isNotBlank(insertString)){
				logger.logInfo(insertString);
			}
			
			// Execute Get ID Statement
			selectID = conn.createStatement();
			id = selectID.executeQuery(getSelectSQL());
			// Log that we finished the select if we have a select string
			if(StringUtil.isNotBlank(selectString)){
				logger.logInfo(selectString);
			}
			
			logger.logInfo("Finished execution of " + name);
			
			// Return the id we found
			id.next();
			return id.getInt(1);
		}finally{
			// If we fail, close the statements and ResultSet quietly
			AutoCloseableUtil.closeQuietly(insert);
			AutoCloseableUtil.closeQuietly(selectID);
			AutoCloseableUtil.closeQuietly(id);
		}
	}
	
	/**
	 * Creates a new {@link InsertAndGetID} using the given information.
	 *
	 * @param table The table to use for the insert and id retrieval
	 * @param id_str The name of the id column in the table
	 * @param args The columns of the table to use in the insert
	 * @param values The values to use for the insert (should be in the same order as the args)
	 * @return An {@link InsertAndGetID} object to use for a transaction
	 */
	public static InsertAndGetID createInsertAndGetID(String table, String id_str, String[] args, String[] values){
		// Check that we have the same amount of args and values
		if(args.length != values.length){
			throw new IllegalArgumentException("Args and Values don't match up!");
		}
		
		return new InsertAndGetID(){
			/** {@inheritDoc} */
			@Override
			public String getTransactionName(){
				return "Insert " + table + " and Get ID";
			}
			
			/** {@inheritDoc} */
			@Override
			public String getInsertString(){
				return "Executed Insert of a " + table + "!";
			}
			
			/** {@inheritDoc} */
			@Override
			public String getInsertSQL(){
				return DBUtil.formatInsertStatement(table, args, values);
			}
			
			/** {@inheritDoc} */
			@Override
			public String getSelectString(){
				return "Pulled out " + id_str + " of just inserted " + table + "!";
			}
			
			/** {@inheritDoc} */
			@Override
			public String getSelectSQL(){
				return DBUtil.formatQuery(table, id_str, args, values);
			}
		};
	}
}
