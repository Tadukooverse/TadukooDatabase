package com.github.tadukoo.database.mysql.syntax.statement;

import com.github.tadukoo.database.mysql.syntax.SQLType;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLDropStatement represents a MySQL Drop statement
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class SQLDropStatement{
	
	/**
	 * A builder to create a {@link SQLDropStatement}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>SQL Drop Statement Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Required or Default</th>
	 *     </tr>
	 *     <tr>
	 *         <td>type</td>
	 *         <td>The {@link SQLType type} to be dropped</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>databaseName</td>
	 *         <td>The name of the table/database to be dropped</td>
	 *         <td>Required</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class SQLDropStatementBuilder implements Type, Name, Build{
		/** The {@link SQLType type} to be dropped */
		private SQLType type;
		/** The name of the table/database to be dropped */
		private String name;
		
		/** Not allowed to instantiate outside of SQLDropDatabaseStatement */
		private SQLDropStatementBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		public Name table(){
			this.type = SQLType.TABLE;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Name database(){
			this.type = SQLType.DATABASE;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Build name(String name){
			this.name = name;
			return this;
		}
		
		/**
		 * Checks for any errors and throws an IllegalArgumentException if any errors are found
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// Check that name isn't empty
			if(StringUtil.isBlank(name)){
				errors.add("name is required!");
			}
			
			// Report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Encountered the following errors in building a " +
						"SQLDropStatement: \n" + StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/** {@inheritDoc} */
		@Override
		public SQLDropStatement build(){
			checkForErrors();
			
			return new SQLDropStatement(type, name);
		}
	}
	
	/** The {@link SQLType type} to be dropped */
	private final SQLType type;
	/** The name of the table/database to be dropped */
	private final String name;
	
	/**
	 * Constructs a new {@link SQLDropStatement} with the given parameters
	 *
	 * @param type The {@link SQLType type} to be dropped
	 * @param name The name of the table/database to be dropped
	 */
	private SQLDropStatement(SQLType type, String name){
		this.type = type;
		this.name = name;
	}
	
	/**
	 * @return A new {@link SQLDropStatementBuilder builder} to create a {@link SQLDropStatement}
	 */
	public static Type builder(){
		return new SQLDropStatementBuilder();
	}
	
	/**
	 * @return The {@link SQLType type} to be dropped
	 */
	public SQLType getType(){
		return type;
	}
	
	/**
	 * @return The name of the table/database to be dropped
	 */
	public String getName(){
		return name;
	}
	
	@Override
	public String toString(){
		return "DROP " + type + " " + name;
	}
	
	/*
	 * Interfaces for building
	 */
	
	/**
	 * The type part of building a {@link SQLDropStatement}
	 */
	public interface Type{
		/**
		 * We'll be dropping a Table
		 *
		 * @return this, to continue building
		 */
		Name table();
		
		/**
		 * We'll be dropping a Database
		 *
		 * @return this, to continue building
		 */
		Name database();
	}
	
	/**
	 * The name part of building a {@link SQLDropStatement}
	 */
	public interface Name{
		/**
		 * @param name The name of the table/database to be dropped
		 * @return this, to continue building
		 */
		Build name(String name);
	}
	
	/**
	 * The building part of building a {@link SQLDropStatement}
	 */
	public interface Build{
		/**
		 * Constructs a new {@link SQLDropStatement} using the set parameters
		 *
		 * @return The newly built {@link SQLDropStatement}
		 */
		SQLDropStatement build();
	}
}
