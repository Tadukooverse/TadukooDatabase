package com.github.tadukoo.database.mysql.syntax.reference;

import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Table Ref is used as a reference to a Table in SQL.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class TableRef{
	
	/**
	 * A builder to build a new {@link TableRef}. It uses the following parameters:
	 *
	 * <table>
	 *     <caption>TableRef Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Required or Default</th>
	 *     </tr>
	 *     <tr>
	 *         <td>tableName</td>
	 *         <td>The name of the table</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>alias</td>
	 *         <td>The alias to use for the table</td>
	 *         <td>Defaults to null (no alias)</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class TableRefBuilder{
		/** The name of the table */
		private String tableName;
		/** The alias to use for the table */
		private String alias = null;
		
		/** Not allowed to instantiate outside TableRef */
		private TableRefBuilder(){ }
		
		/**
		 * @param tableName The name of the table
		 * @return this, to continue building
		 */
		public TableRefBuilder tableName(String tableName){
			this.tableName = tableName;
			return this;
		}
		
		/**
		 * @param alias The alias to use for the table
		 * @return this, to continue building
		 */
		public TableRefBuilder alias(String alias){
			this.alias = alias;
			return this;
		}
		
		/**
		 * Checks for any errors with the set parameters
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// tableName is required
			if(StringUtil.isBlank(tableName)){
				errors.add("tableName is required!");
			}
			
			// Report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("The following errors occurred trying to build a TableRef: \n" +
						StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/**
		 * Builds a new {@link TableRef} with the set parameters
		 *
		 * @return The newly built {@link TableRef}
		 */
		public TableRef build(){
			checkForErrors();
			
			return new TableRef(tableName, alias);
		}
	}
	
	/** The name of the table */
	private final String tableName;
	/** The alias to use for the table */
	private final String alias;
	
	/**
	 * Constructs a new {@link TableRef} using the given parameters.
	 *
	 * @param tableName The name of the table
	 * @param alias The alias to use for the table
	 */
	private TableRef(String tableName, String alias){
		this.tableName = tableName;
		this.alias = alias;
	}
	
	/**
	 * @return A new {@link TableRefBuilder builder} to use to build a {@link TableRef}
	 */
	public static TableRefBuilder builder(){
		return new TableRefBuilder();
	}
	
	/**
	 * @return The name of the table
	 */
	public String getTableName(){
		return tableName;
	}
	
	/**
	 * @return The alias to use for the table
	 */
	public String getAlias(){
		return alias;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		StringBuilder tableRef = new StringBuilder(tableName);
		
		// If we have alias, add it
		if(StringUtil.isNotBlank(alias)){
			tableRef.append(" AS ").append(alias);
		}
		
		return tableRef.toString();
	}
}
