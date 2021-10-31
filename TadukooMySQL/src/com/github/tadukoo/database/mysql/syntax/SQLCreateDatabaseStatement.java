package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLCreateDatabaseStatement represents a MySQL Create Database statement
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class SQLCreateDatabaseStatement{
	
	/**
	 * A builder to create a {@link SQLCreateDatabaseStatement}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>SQL Create Database Statement Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Required or Default</th>
	 *     </tr>
	 *     <tr>
	 *         <td>databaseName</td>
	 *         <td>The name of the database to be created</td>
	 *         <td>Required</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class SQLCreateDatabaseStatementBuilder implements DatabaseName, Build{
		/** The name of the database to be created */
		private String databaseName;
		
		/** Not allowed to instantiate outside of SQLCreateDatabaseStatement */
		private SQLCreateDatabaseStatementBuilder(){ }
		
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
						"SQLCreateDatabaseStatement: \n" + StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/** {@inheritDoc} */
		public SQLCreateDatabaseStatement build(){
			checkForErrors();
			
			return new SQLCreateDatabaseStatement(databaseName);
		}
	}
	
	/** The name of the database to be created */
	private final String databaseName;
	
	/**
	 * Constructs a new {@link SQLCreateDatabaseStatement} with the given parameters
	 *
	 * @param databaseName The name of the database to be created
	 */
	private SQLCreateDatabaseStatement(String databaseName){
		this.databaseName = databaseName;
	}
	
	/**
	 * @return A new {@link SQLCreateDatabaseStatementBuilder builder} to create a {@link SQLCreateDatabaseStatement}
	 */
	public static DatabaseName builder(){
		return new SQLCreateDatabaseStatementBuilder();
	}
	
	/**
	 * @return The name of the database to be created
	 */
	public String getDatabaseName(){
		return databaseName;
	}
	
	@Override
	public String toString(){
		return "CREATE DATABASE " + databaseName;
	}
	
	/*
	 * Interfaces for building
	 */
	
	/**
	 * The database name part of building a {@link SQLCreateDatabaseStatement}
	 */
	public interface DatabaseName{
		/**
		 * @param databaseName The name of the database to be created
		 * @return this, to continue building
		 */
		Build databaseName(String databaseName);
	}
	
	/**
	 * The building part of building a {@link SQLCreateDatabaseStatement}
	 */
	public interface Build{
		/**
		 * Constructs a new {@link SQLCreateDatabaseStatement} using the set parameters
		 *
		 * @return The newly built {@link SQLCreateDatabaseStatement}
		 */
		SQLCreateDatabaseStatement build();
	}
}
