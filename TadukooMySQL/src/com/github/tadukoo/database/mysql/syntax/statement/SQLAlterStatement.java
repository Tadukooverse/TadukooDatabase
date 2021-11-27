package com.github.tadukoo.database.mysql.syntax.statement;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.ForeignKeyConstraint;
import com.github.tadukoo.database.mysql.syntax.SQLColumnOperation;
import com.github.tadukoo.database.mysql.syntax.SQLType;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class SQLAlterStatement{
	
	/**
	 * A builder to use to build a {@link SQLAlterStatement}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>SQL Alter Statement Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Default or Required</th>
	 *     </tr>
	 *     <tr>
	 *         <td>type</td>
	 *         <td>The {@link SQLType type} to be altered</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>tableName</td>
	 *         <td>The name of the table to alter</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>operation</td>
	 *         <td>The {@link SQLColumnOperation operation} to perform</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>columnName</td>
	 *         <td>The name of the column to alter</td>
	 *         <td>Either this or columnDef or foreignKey required, depending on operation</td>
	 *     </tr>
	 *     <tr>
	 *         <td>columnDef</td>
	 *         <td>The {@link ColumnDefinition column definition} to be used</td>
	 *         <td>Either this or columnName or foreign key required, depending on operation</td>
	 *     </tr>
	 *     <tr>
	 *         <td>foreignKey</td>
	 *         <td>The {@link ForeignKeyConstraint foreign key} to be added to the table</td>
	 *         <td>Either this or columnName or columnDef required, depending on operation</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class SQLAlterStatementBuilder implements Type, TableName, Operation, ColumnName,
			ColumnDefOrForeignKey, ColumnDef, Build{
		/** The {@link SQLType type} to be altered */
		private SQLType type;
		/** The name of the table to alter */
		private String tableName;
		/** The {@link SQLColumnOperation operation} to perform */
		private SQLColumnOperation operation;
		/** The name of the column to alter */
		private String columnName = null;
		/** The {@link ColumnDefinition column definition} to be used */
		private ColumnDefinition columnDef = null;
		/** The {@link ForeignKeyConstraint foreign key} to be added to the table */
		private ForeignKeyConstraint foreignKey = null;
		
		/** Not allowed to instantiate outside {@link SQLAlterStatement} */
		private SQLAlterStatementBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		public TableName table(){
			this.type = SQLType.TABLE;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Operation tableName(String tableName){
			this.tableName = tableName;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public ColumnDefOrForeignKey add(){
			this.operation = SQLColumnOperation.ADD;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public ColumnDef modify(){
			this.operation = SQLColumnOperation.MODIFY;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public ColumnName drop(){
			this.operation = SQLColumnOperation.DROP;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Build columnName(String columnName){
			this.columnName = columnName;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Build foreignKey(ForeignKeyConstraint foreignKey){
			this.foreignKey = foreignKey;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Build columnDef(ColumnDefinition columnDef){
			this.columnDef = columnDef;
			return this;
		}
		
		/**
		 * Checks for any errors in the set parameters and throws an exception if any are found
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// tableName is required
			if(StringUtil.isBlank(tableName)){
				errors.add("tableName is required!");
			}
			
			// columnName or columnDef or foreign key is required
			if(StringUtil.isBlank(columnName) && columnDef == null && foreignKey == null){
				errors.add("columnName or columnDef or foreignKey must be specified!");
			}
			
			// Report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Encountered errors in building a SQLAlterStatement: \n" +
						StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/** {@inheritDoc} */
		@Override
		public SQLAlterStatement build(){
			checkForErrors();
			
			return new SQLAlterStatement(type, tableName, operation, columnName, columnDef, foreignKey);
		}
	}
	
	/** The {@link SQLType type} to be altered */
	private final SQLType type;
	/** The name of the table to alter */
	private final String tableName;
	/** The {@link SQLColumnOperation operation} to perform */
	private final SQLColumnOperation operation;
	/** The name of the column to alter */
	private final String columnName;
	/** The {@link ColumnDefinition column definition} to be used */
	private final ColumnDefinition columnDef;
	/** The {@link ForeignKeyConstraint foreign key} to be added to the table */
	private final ForeignKeyConstraint foreignKey;
	
	/**
	 * Constructs a {@link SQLAlterStatement} using the given parameters
	 *
	 * @param type  The {@link SQLType type} to be altered
	 * @param tableName The name of the table to alter
	 * @param operation The {@link SQLColumnOperation operation} to perform
	 * @param columnName The name of the column to alter
	 * @param columnDef The {@link ColumnDefinition column definition} to be used
	 * @param foreignKey The {@link ForeignKeyConstraint foreign key} to be added to the table
	 */
	private SQLAlterStatement(
			SQLType type, String tableName, SQLColumnOperation operation,
			String columnName, ColumnDefinition columnDef, ForeignKeyConstraint foreignKey){
		this.type = type;
		this.tableName = tableName;
		this.operation = operation;
		this.columnName = columnName;
		this.columnDef = columnDef;
		this.foreignKey = foreignKey;
	}
	
	/**
	 * @return A new {@link SQLAlterStatementBuilder} to build a new {@link SQLAlterStatement}
	 */
	public static Type builder(){
		return new SQLAlterStatementBuilder();
	}
	
	/**
	 * @return The {@link SQLType type} to be altered
	 */
	public SQLType getType(){
		return type;
	}
	
	/**
	 * @return The name of the table to alter
	 */
	public String getTableName(){
		return tableName;
	}
	
	/**
	 * @return The {@link SQLColumnOperation operation} to perform
	 */
	public SQLColumnOperation getOperation(){
		return operation;
	}
	
	/**
	 * @return The name of the column to alter
	 */
	public String getColumnName(){
		return columnName;
	}
	
	/**
	 * @return The {@link ColumnDefinition column definition} to be used
	 */
	public ColumnDefinition getColumnDef(){
		return columnDef;
	}
	
	/**
	 * @return The {@link ForeignKeyConstraint foreign key} to be added to the table
	 */
	public ForeignKeyConstraint getForeignKey(){
		return foreignKey;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		// Start statement
		StringBuilder stmt = new StringBuilder("ALTER ").append(type).append(' ').append(tableName)
				.append(' ').append(operation).append(' ');
		
		// Append either columnName or columnDef or foreign key
		if(columnDef != null){
			stmt.append(columnDef);
		}else if(foreignKey != null){
			stmt.append(foreignKey);
		}else{
			stmt.append(columnName);
		}
		
		return stmt.toString();
	}
	
	/*
	 * Interfaces for building
	 */
	
	/**
	 * The Type part of building a {@link SQLAlterStatement}
	 */
	public interface Type{
		/**
		 * Set the type to altering a table
		 * @return this, to continue building
		 */
		TableName table();
	}
	
	/**
	 * The Table name part of building a {@link SQLAlterStatement}
	 */
	public interface TableName{
		/**
		 * @param tableName The name of the table to alter
		 * @return this, to continue building
		 */
		Operation tableName(String tableName);
	}
	
	/**
	 * The {@link SQLColumnOperation operation} part of building a {@link SQLAlterStatement}
	 */
	public interface Operation{
		/**
		 * Sets the {@link SQLColumnOperation operation} to add a column
		 * @return this, to continue building
		 */
		ColumnDefOrForeignKey add();
		
		/**
		 * Sets the {@link SQLColumnOperation operation} to modify a column
		 * @return this, to continue building
		 */
		ColumnDef modify();
		
		/**
		 * Sets the {@link SQLColumnOperation operation} to drop a column
		 * @return this, to continue building
		 */
		ColumnName drop();
	}
	
	/**
	 * The Column Name part of building a {@link SQLAlterStatement}
	 */
	public interface ColumnName{
		/**
		 * @param columnName The name of the column to alter
		 * @return this, to continue building
		 */
		Build columnName(String columnName);
	}
	
	/**
	 * The {@link ColumnDefinition column definition} or {@link ForeignKeyConstraint foreign key} part of building
	 * a {@link SQLAlterStatement}
	 */
	public interface ColumnDefOrForeignKey extends ColumnDef{
		/**
		 * @param foreignKey The {@link ForeignKeyConstraint foreign key} to add to the table
		 * @return this, to continue building
		 */
		Build foreignKey(ForeignKeyConstraint foreignKey);
	}
	
	/**
	 * The {@link ColumnDefinition column definition} part of building a {@link SQLAlterStatement}
	 */
	public interface ColumnDef{
		/**
		 * @param columnDef The {@link ColumnDefinition column definition} to be used
		 * @return this, to continue building
		 */
		Build columnDef(ColumnDefinition columnDef);
	}
	
	/**
	 * The building part of building a {@link SQLAlterStatement}
	 */
	public interface Build{
		/**
		 * Builds a new {@link SQLAlterStatement} using the set parameters
		 *
		 * @return The newly built {@link SQLAlterStatement}
		 */
		SQLAlterStatement build();
	}
}
