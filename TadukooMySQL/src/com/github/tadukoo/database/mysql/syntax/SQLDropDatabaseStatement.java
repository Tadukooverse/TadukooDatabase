package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLDropDatabaseStatement represents a MySQL Drop Database statement
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class SQLDropDatabaseStatement{
	
	/**
	 * A builder to create a {@link SQLDropDatabaseStatement}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>SQL Drop Database Statement Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Required or Default</th>
	 *     </tr>
	 *     <tr>
	 *         <td>databaseName</td>
	 *         <td>The name of the database to be dropped</td>
	 *         <td>Required</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class SQLDropDatabaseStatementBuilder implements DatabaseName, Build{
		/** The name of the database to be dropped */
		private String databaseName;
		
		/** Not allowed to instantiate outside of SQLDropDatabaseStatement */
		private SQLDropDatabaseStatementBuilder(){ }
		
		/** {@inheritDoc} */
		public Build databaseName(String databaseName){
			this.databaseName = databaseName;
			return this;
		}
		
		/**
		 * Checks for any errors and throws an IllegalArgumentException if any errors are found
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// Check that database name isn't empty
			if(StringUtil.isBlank(databaseName)){
				errors.add("databaseName is required!");
			}
			
			// Report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Encountered the following errors in building a " +
						"SQLDropDatabaseStatement: \n" + StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/** {@inheritDoc} */
		public SQLDropDatabaseStatement build(){
			checkForErrors();
			
			return new SQLDropDatabaseStatement(databaseName);
		}
	}
	
	/** The name of the database to be dropped */
	private final String databaseName;
	
	/**
	 * Constructs a new {@link SQLDropDatabaseStatement} with the given parameters
	 *
	 * @param databaseName The name of the database to be dropped
	 */
	private SQLDropDatabaseStatement(String databaseName){
		this.databaseName = databaseName;
	}
	
	/**
	 * @return A new {@link SQLDropDatabaseStatementBuilder builder} to create a {@link SQLDropDatabaseStatement}
	 */
	public static DatabaseName builder(){
		return new SQLDropDatabaseStatementBuilder();
	}
	
	/**
	 * @return The name of the database to be dropped
	 */
	public String getDatabaseName(){
		return databaseName;
	}
	
	@Override
	public String toString(){
		return "DROP DATABASE " + databaseName;
	}
	
	/*
	 * Interfaces for building
	 */
	
	/**
	 * The database name part of building a {@link SQLDropDatabaseStatement}
	 */
	public interface DatabaseName{
		/**
		 * @param databaseName The name of the database to be dropped
		 * @return this, to continue building
		 */
		Build databaseName(String databaseName);
	}
	
	/**
	 * The building part of building a {@link SQLDropDatabaseStatement}
	 */
	public interface Build{
		/**
		 * Constructs a new {@link SQLDropDatabaseStatement} using the set parameters
		 *
		 * @return The newly built {@link SQLDropDatabaseStatement}
		 */
		SQLDropDatabaseStatement build();
	}
}
