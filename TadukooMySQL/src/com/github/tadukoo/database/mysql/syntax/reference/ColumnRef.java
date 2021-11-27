package com.github.tadukoo.database.mysql.syntax.reference;

import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Column Ref is a reference to a column in SQL
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class ColumnRef{
	
	/**
	 * A builder to use to build a {@link ColumnRef}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>ColumnRef Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Required or Default</th>
	 *     </tr>
	 *     <tr>
	 *         <td>tableName</td>
	 *         <td>The name of the table the column is on</td>
	 *         <td>Defaults to null (no table name)</td>
	 *     </tr>
	 *     <tr>
	 *         <td>columnName</td>
	 *         <td>The name of the column</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>alias</td>
	 *         <td>The alias to use for the column</td>
	 *         <td>Defaults to null (no alias)</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class ColumnRefBuilder implements TableNameOrColumnName, ColumnName, AliasOrBuild, Build{
		/** The name of the table the column is on */
		private String tableName;
		/** The name of the column */
		private String columnName;
		/** The alias to use for the column */
		private String alias = null;
		
		/** Not allowed to instantiate outside ColumnRef */
		private ColumnRefBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		public ColumnName tableName(String tableName){
			this.tableName = tableName;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public ColumnName tableRef(TableRef tableRef){
			this.tableName = tableRef.getTableName();
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public AliasOrBuild columnName(String columnName){
			this.columnName = columnName;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Build alias(String alias){
			this.alias = alias;
			return this;
		}
		
		/**
		 * Checks for any errors in the set parameters
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// columnName is required
			if(StringUtil.isBlank(columnName)){
				errors.add("columnName is required!");
			}
			
			// Report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Encountered the following errors trying to build a ColumnRef: \n" +
						StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/** {@inheritDoc} */
		@Override
		public ColumnRef build(){
			checkForErrors();
			
			return new ColumnRef(tableName, columnName, alias);
		}
	}
	
	/** The name of the table the column is on */
	private final String tableName;
	/** The name of the column */
	private final String columnName;
	/** The alias to use for the column */
	private final String alias;
	
	/**
	 * Constructs a {@link ColumnRef} using the given parameters
	 *
	 * @param tableName The name of the table the column is on
	 * @param columnName The name of the column
	 * @param alias The alias to use for the column
	 */
	private ColumnRef(String tableName, String columnName, String alias){
		this.tableName = tableName;
		this.columnName = columnName;
		this.alias = alias;
	}
	
	/**
	 * @return A {@link ColumnRefBuilder builder} to use to build a {@link ColumnRef}
	 */
	public static TableNameOrColumnName builder(){
		return new ColumnRefBuilder();
	}
	
	/**
	 * @return The name of the table the column is on
	 */
	public String getTableName(){
		return tableName;
	}
	
	/**
	 * @return The name of the column
	 */
	public String getColumnName(){
		return columnName;
	}
	
	/**
	 * @return The alias to use for the column
	 */
	public String getAlias(){
		return alias;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		StringBuilder columnRef = new StringBuilder();
		
		// Add table name and a dot if we have it
		if(StringUtil.isNotBlank(tableName)){
			columnRef.append(tableName).append('.');
		}
		
		// Add column name
		columnRef.append(columnName);
		
		// Add alias if we have it
		if(StringUtil.isNotBlank(alias)){
			columnRef.append(" AS ");
			
			// If we have spaces, it needs to be surrounded by quotes
			if(alias.contains(" ")){
				columnRef.append('"').append(alias).append('"');
			}else{
				columnRef.append(alias);
			}
		}
		
		return columnRef.toString();
	}
	
	/*
	 * Interfaces for the builder
	 */
	
	/**
	 * The Table Name or Column Name part of building a {@link ColumnRef}
	 */
	public interface TableNameOrColumnName extends ColumnName{
		/**
		 * @param tableName The name of the table the column is in
		 * @return this, to continue building
		 */
		ColumnName tableName(String tableName);
		
		/**
		 * @param tableRef The {@link TableRef} to use to get the table name the column is in
		 * @return this, to continue building
		 */
		ColumnName tableRef(TableRef tableRef);
	}
	
	/**
	 * The Column Name part of building a {@link ColumnRef}
	 */
	public interface ColumnName{
		/**
		 * @param columnName The name of the column
		 * @return this, to continue building
		 */
		AliasOrBuild columnName(String columnName);
	}
	
	/**
	 * The Alias and building part of building a {@link ColumnRef}
	 */
	public interface AliasOrBuild extends Build{
		/**
		 * @param alias The alias to use for the column
		 * @return this, to continue building
		 */
		Build alias(String alias);
	}
	
	/**
	 * The building part of building a {@link ColumnRef}
	 */
	public interface Build{
		/**
		 * Builds a new {@link ColumnRef} using the set parameters
		 *
		 * @return The newly built {@link ColumnRef}
		 */
		ColumnRef build();
	}
}
