package com.github.tadukoo.database.mysql.syntax.statement;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.ForeignKeyConstraint;
import com.github.tadukoo.database.mysql.syntax.SQLType;
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
	 *         <td>ifNotExists</td>
	 *         <td>Whether to include IF NOT EXISTS in the statement or not</td>
	 *         <td>Defaults to false</td>
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
	 *     <tr>
	 *         <td>foreignKeys</td>
	 *         <td>The {@link ForeignKeyConstraint foreign keys} to add to the table</td>
	 *         <td>Defaults to an empty list</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class SQLCreateStatementBuilder implements Type, ExistsOrTableName, TableName, AsOrColumns,
			ExistsOrDatabaseName, DatabaseName, ForeignKeysOrBuild, Build{
		/** The {@link SQLType type} to be created */
		private SQLType type;
		/** Whether to include IF NOT EXISTS or not */
		private boolean ifNotExists = false;
		/** The name of the table/database to be created */
		private String name;
		/** The {@link SQLSelectStatement select statement} to use to grab another table */
		private SQLSelectStatement selectStmt = null;
		/** The {@link ColumnDefinition columns} to create in the table */
		private List<ColumnDefinition> columns = new ArrayList<>();
		/** The {@link ForeignKeyConstraint foreign keys} to add to the table */
		private final List<ForeignKeyConstraint> foreignKeys = new ArrayList<>();
		
		/** Not allowed to instantiate outside of SQLCreateDatabaseStatement */
		private SQLCreateStatementBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		public ExistsOrTableName table(){
			this.type = SQLType.TABLE;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public ExistsOrDatabaseName database(){
			this.type = SQLType.DATABASE;
			return this;
		}
		
		/**
		 * Sets {@link #ifNotExists} to true
		 * @return this, to continue building
		 */
		private SQLCreateStatementBuilder ifNotExists(){
			this.ifNotExists = true;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public TableName ifTableNotExists(){
			return ifNotExists();
		}
		
		/** {@inheritDoc} */
		@Override
		public DatabaseName ifDatabaseNotExists(){
			return ifNotExists();
		}
		
		/** {@inheritDoc} */
		@Override
		public AsOrColumns tableName(String tableName){
			this.name = tableName;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public ForeignKeysOrBuild as(SQLSelectStatement selectStmt){
			this.selectStmt = selectStmt;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public ForeignKeysOrBuild columns(List<ColumnDefinition> columns){
			this.columns = columns;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public ForeignKeysOrBuild columns(ColumnDefinition ... columns){
			this.columns = ListUtil.createList(columns);
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public ForeignKeysOrBuild foreignKey(ForeignKeyConstraint foreignKey){
			this.foreignKeys.add(foreignKey);
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
			
			return new SQLCreateStatement(type, ifNotExists, name, selectStmt, columns, foreignKeys);
		}
	}
	
	/** The {@link SQLType type} to be created */
	private final SQLType type;
	/** Whether to include IF NOT EXISTS in the statement or not */
	private final boolean ifNotExists;
	/** The name of the table/database to be created */
	private final String name;
	/** The {@link SQLSelectStatement select statement} to use to grab another table */
	private final SQLSelectStatement selectStmt;
	/** The {@link ColumnDefinition columns} to create in the table */
	private final List<ColumnDefinition> columns;
	/** The {@link ForeignKeyConstraint foreign keys} to add to the table */
	private final List<ForeignKeyConstraint> foreignKeys;
	
	/**
	 * Constructs a new {@link SQLCreateStatement} with the given parameters
	 *
	 * @param type The {@link SQLType type} to be created
	 * @param ifNotExists Whether to include IF NOT EXISTS in the statement or not
	 * @param name The name of the table/database to be created
	 * @param selectStmt The {@link SQLSelectStatement select statement} to use to grab another table
	 * @param columns The {@link ColumnDefinition columns} to create in the table
	 * @param foreignKeys The {@link ForeignKeyConstraint foreign keys} to add to the table
	 */
	private SQLCreateStatement(
			SQLType type, boolean ifNotExists, String name, SQLSelectStatement selectStmt,
			List<ColumnDefinition> columns, List<ForeignKeyConstraint> foreignKeys){
		this.type = type;
		this.ifNotExists = ifNotExists;
		this.name = name;
		this.selectStmt = selectStmt;
		this.columns = columns;
		this.foreignKeys = foreignKeys;
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
	 * @return Whether to include IF NOT EXISTS in the statement or not
	 */
	public boolean getIfNotExists(){
		return ifNotExists;
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
	
	/**
	 * @return The {@link ForeignKeyConstraint foreign keys} to add to the table
	 */
	public List<ForeignKeyConstraint> getForeignKeys(){
		return foreignKeys;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		// Start the statement
		StringBuilder stmt = new StringBuilder("CREATE ").append(type).append(' ');
		
		// Add IF NOT EXISTS if specified
		if(ifNotExists){
			stmt.append("IF NOT EXISTS ");
		}
		
		// Add the name to the statement
		stmt.append(name);
		
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
			stmt.delete(stmt.length() - 2, stmt.length());
			
			// If we have foreign keys, add them to the statement
			if(ListUtil.isNotBlank(foreignKeys)){
				for(ForeignKeyConstraint foreignKey: foreignKeys){
					stmt.append(", ").append(foreignKey);
				}
			}
			
			// Close parentheses
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
		ExistsOrTableName table();
		
		/**
		 * We'll be creating a database
		 *
		 * @return this, to continue building
		 */
		ExistsOrDatabaseName database();
	}
	
	/**
	 * The If Not Exists or table name part of building a {@link SQLCreateStatement}
	 */
	public interface ExistsOrTableName extends TableName{
		/**
		 * Set to add ifNotExists to the create statement
		 * @return this, to continue building
		 */
		TableName ifTableNotExists();
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
		ForeignKeysOrBuild as(SQLSelectStatement selectStmt);
		
		/**
		 * @param columns The {@link ColumnDefinition columns} to create in the table
		 * @return this, to continue building
		 */
		ForeignKeysOrBuild columns(List<ColumnDefinition> columns);
		
		/**
		 * @param columns The {@link ColumnDefinition columns} to create in the table
		 * @return this, to continue building
		 */
		ForeignKeysOrBuild columns(ColumnDefinition ... columns);
	}
	
	/**
	 * The Foreign Keys or Building part of building a {@link SQLCreateStatement}
	 */
	public interface ForeignKeysOrBuild extends Build{
		
		/**
		 * @param foreignKey A {@link ForeignKeyConstraint foreign key} to add to the table
		 * @return this, to continue building
		 */
		ForeignKeysOrBuild foreignKey(ForeignKeyConstraint foreignKey);
	}
	
	/**
	 * The If Not Exists of Database name part of building a {@link SQLCreateStatement}
	 */
	public interface ExistsOrDatabaseName extends DatabaseName{
		/**
		 * Set to add ifNotExists to the create statement
		 * @return this, to continue building
		 */
		DatabaseName ifDatabaseNotExists();
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
