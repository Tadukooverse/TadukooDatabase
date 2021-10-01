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
	public static class ColumnRefBuilder{
		/** The name of the column */
		private String columnName;
		/** The alias to use for the column */
		private String alias = null;
		
		/** Not allowed to instantiate outside ColumnRef */
		private ColumnRefBuilder(){ }
		
		/**
		 * @param columnName The name of the column
		 * @return this, to continue building
		 */
		public ColumnRefBuilder columnName(String columnName){
			this.columnName = columnName;
			return this;
		}
		
		/**
		 * @param alias The alias to use for the column
		 * @return this, to continue building
		 */
		public ColumnRefBuilder alias(String alias){
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
		
		/**
		 * Builds a new {@link ColumnRef} using the set parameters
		 *
		 * @return The newly built {@link ColumnRef}
		 */
		public ColumnRef build(){
			checkForErrors();
			
			return new ColumnRef(columnName, alias);
		}
	}
	
	/** The name of the column */
	private final String columnName;
	/** The alias to use for the column */
	private final String alias;
	
	/**
	 * Constructs a {@link ColumnRef} using the given parameters
	 *
	 * @param columnName The name of the column
	 * @param alias The alias to use for the column
	 */
	private ColumnRef(String columnName, String alias){
		this.columnName = columnName;
		this.alias = alias;
	}
	
	/**
	 * @return A {@link ColumnRefBuilder builder} to use to build a {@link ColumnRef}
	 */
	public static ColumnRefBuilder builder(){
		return new ColumnRefBuilder();
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
		StringBuilder columnRef = new StringBuilder(columnName);
		
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
}
