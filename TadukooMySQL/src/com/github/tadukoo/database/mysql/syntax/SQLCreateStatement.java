package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLCreateStatement represents a MySQL Create statement
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class SQLCreateStatement{
	
	/**
	 * A builder to create a {@link SQLCreateStatement}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>SQL Create Statement Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Required or Default</th>
	 *     </tr>
	 *     <tr>
	 *         <td>type</td>
	 *         <td>The {@link SQLType type} to be created</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>name</td>
	 *         <td>The name of the table/database to be created</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>selectStmt</td>
	 *         <td>The {@link SQLSelectStatement select statement} to use to grab another table</td>
	 *         <td>Requires either this or columns</td>
	 *     </tr>
	 *     <tr>
	 *         <td>columns</td>
	 *         <td>The {@link ColumnDefinition columns} to create in the table</td>
	 *         <td>Requires either this or selectStmt</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class SQLCreateStatementBuilder implements Type, TableName, AsOrColumns, DatabaseName, Build{
		/** The {@link SQLType type} to be created */
		private SQLType type;
		/** The name of the table/database to be created */
		private String name;
		/** The {@link SQLSelectStatement select statement} to use to grab another table */
		private SQLSelectStatement selectStmt = null;
		/** The {@link ColumnDefinition columns} to create in the table */
		private List<ColumnDefinition> columns = new ArrayList<>();
		
		/** Not allowed to instantiate outside of SQLCreateDatabaseStatement */
		private SQLCreateStatementBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		public TableName table(){
			this.type = SQLType.TABLE;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public DatabaseName database(){
			this.type = SQLType.DATABASE;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public AsOrColumns tableName(String tableName){
			this.name = tableName;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Build as(SQLSelectStatement selectStmt){
			this.selectStmt = selectStmt;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Build columns(List<ColumnDefinition> columns){
			this.columns = columns;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Build columns(ColumnDefinition ... columns){
			this.columns = ListUtil.createList(columns);
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Build databaseName(String databaseName){
			this.name = databaseName;
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
			
			// Check that if type is table, either selectStmt or columns is non-empty
			if(type == SQLType.TABLE && selectStmt == null && ListUtil.isBlank(columns)){
				errors.add("selectStmt or columns is required if creating a table!");
			}
			
			// Report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Encountered the following errors in building a " +
						"SQLCreateStatement: \n" + StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/** {@inheritDoc} */
		@Override
		public SQLCreateStatement build(){
			checkForErrors();
			
			return new SQLCreateStatement(type, name, selectStmt, columns);
		}
	}
	
	/** The {@link SQLType type} to be created */
	private final SQLType type;
	/** The name of the table/database to be created */
	private final String name;
	/** The {@link SQLSelectStatement select statement} to use to grab another table */
	private final SQLSelectStatement selectStmt;
	/** The {@link ColumnDefinition columns} to create in the table */
	private final List<ColumnDefinition> columns;
	
	/**
	 * Constructs a new {@link SQLCreateStatement} with the given parameters
	 *
	 * @param type The {@link SQLType type} to be created
	 * @param name The name of the table/database to be created
	 * @param selectStmt The {@link SQLSelectStatement select statement} to use to grab another table
	 * @param columns The {@link ColumnDefinition columns} to create in the table
	 */
	private SQLCreateStatement(
			SQLType type, String name, SQLSelectStatement selectStmt, List<ColumnDefinition> columns){
		this.type = type;
		this.name = name;
		this.selectStmt = selectStmt;
		this.columns = columns;
	}
	
	/**
	 * @return A new {@link SQLCreateStatementBuilder builder} to create a {@link SQLCreateStatement}
	 */
	public static Type builder(){
		return new SQLCreateStatementBuilder();
	}
	
	/**
	 * @return The {@link SQLType type} to be created
	 */
	public SQLType getType(){
		return type;
	}
	
	/**
	 * @return The name of the table/database to be created
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return The {@link SQLSelectStatement select statement} to use to grab another table
	 */
	public SQLSelectStatement getSelectStmt(){
		return selectStmt;
	}
	
	/**
	 * @return The {@link ColumnDefinition columns} to create in the table
	 */
	public List<ColumnDefinition> getColumns(){
		return columns;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		// Start the statement
		StringBuilder stmt = new StringBuilder("CREATE ").append(type).append(' ').append(name);
		
		// If we have the select statement, do an as
		if(selectStmt != null){
			stmt.append(" AS ").append(selectStmt);
		}
		
		// If we have columns, add them to the statement
		if(ListUtil.isNotBlank(columns)){
			stmt.append('(');
			for(ColumnDefinition column: columns){
				stmt.append(column).append(", ");
			}
			// Remove last comma
			stmt.delete(stmt.length()-2, stmt.length());
			stmt.append(')');
		}
		
		return stmt.toString();
	}
	
	/*
	 * Interfaces for building
	 */
	
	/**
	 * The {@link SQLType type} part of building a {@link SQLCreateStatement}
	 */
	public interface Type{
		/**
		 * We'll be creating a table
		 *
		 * @return this, to continue building
		 */
		TableName table();
		
		/**
		 * We'll be creating a database
		 *
		 * @return this, to continue building
		 */
		DatabaseName database();
	}
	
	/**
	 * The table name part of building a {@link SQLCreateStatement}
	 */
	public interface TableName{
		/**
		 * @param tableName The name of the table to be created
		 * @return this, to continue building
		 */
		AsOrColumns tableName(String tableName);
	}
	
	/**
	 * The As or Columns part of building a {@link SQLCreateStatement}
	 */
	public interface AsOrColumns{
		/**
		 * Create the table using another table
		 *
		 * @param selectStmt The {@link SQLSelectStatement select statement} to use to grab another table
		 * @return this, to continue building
		 */
		Build as(SQLSelectStatement selectStmt);
		
		/**
		 * @param columns The {@link ColumnDefinition columns} to create in the table
		 * @return this, to continue building
		 */
		Build columns(List<ColumnDefinition> columns);
		
		/**
		 * @param columns The {@link ColumnDefinition columns} to create in the table
		 * @return this, to continue building
		 */
		Build columns(ColumnDefinition ... columns);
	}
	
	/**
	 * The database name part of building a {@link SQLCreateStatement}
	 */
	public interface DatabaseName{
		/**
		 * @param databaseName The name of the database to be created
		 * @return this, to continue building
		 */
		Build databaseName(String databaseName);
	}
	
	/**
	 * The building part of building a {@link SQLCreateStatement}
	 */
	public interface Build{
		/**
		 * Constructs a new {@link SQLCreateStatement} using the set parameters
		 *
		 * @return The newly built {@link SQLCreateStatement}
		 */
		SQLCreateStatement build();
	}
}
