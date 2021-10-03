package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.database.mysql.syntax.conditional.Conditional;
import com.github.tadukoo.database.mysql.syntax.conditional.EqualsStatement;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLUpdateStatement represents an Update statement from MySQL
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class SQLUpdateStatement{
	
	/**
	 * A builder to use to build a {@link SQLUpdateStatement}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>SQL Insert Statement Parameters</caption>
	 *     <tr>
	 *         <th>Parameter Name</th>
	 *         <th>Description</th>
	 *         <th>Default or Required</th>
	 *     </tr>
	 *     <tr>
	 *         <td>table</td>
	 *         <td>The {@link TableRef table} to update</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>setStatements</td>
	 *         <td>The {@link EqualsStatement statements} to set values</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>whereStatement</td>
	 *         <td>The {@link Conditional where statement}</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class SQLUpdateStatementBuilder{
		/** The {@link TableRef table} to update */
		private final TableRef table;
		/** The {@link EqualsStatement statements} to set values */
		private List<EqualsStatement> setStatements = new ArrayList<>();
		/** The {@link Conditional where statement} */
		private Conditional whereStatement = null;
		
		/**
		 * Not allowed to instantiate outside SQLUpdateStatement
		 *
		 * @param table The {@link TableRef table} to update
		 */
		private SQLUpdateStatementBuilder(TableRef table){
			this.table = table;
		}
		
		/**
		 * @param setStatements The {@link EqualsStatement statements} to set values
		 * @return this, to continue building
		 */
		public SQLUpdateStatementBuilder setStatements(List<EqualsStatement> setStatements){
			this.setStatements = setStatements;
			return this;
		}
		
		/**
		 * @param setStatement A {@link EqualsStatement statement} to set a value (added to the list)
		 * @return this, to continue building
		 */
		public SQLUpdateStatementBuilder setStatement(EqualsStatement setStatement){
			setStatements.add(setStatement);
			return this;
		}
		
		/**
		 * @param whereStatement The {@link Conditional where statement}
		 * @return this, to continue building
		 */
		public SQLUpdateStatementBuilder whereStatement(Conditional whereStatement){
			this.whereStatement = whereStatement;
			return this;
		}
		
		/**
		 * Throws an IllegalArgumentException if any errors are found in the parameters
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// table is required
			if(table == null){
				errors.add("table is required!");
			}
			
			// set statements are required
			if(ListUtil.isBlank(setStatements)){
				errors.add("setStatements are required!");
			}
			
			// report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("The following errors occurred trying to create a " +
						"SQLUpdateStatement: \n" + StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/**
		 * Builds a new {@link SQLUpdateStatement} based on the set parameters after checking for errors
		 *
		 * @return The newly built {@link SQLUpdateStatement}
		 */
		public SQLUpdateStatement build(){
			checkForErrors();
			
			return new SQLUpdateStatement(table, setStatements, whereStatement);
		}
	}
	
	/** The {@link TableRef table} to update */
	private final TableRef table;
	/** The {@link EqualsStatement statements} to set values */
	private final List<EqualsStatement> setStatements;
	/** The {@link Conditional where statement} */
	private final Conditional whereStatement;
	
	/**
	 * Constructs a SQLUpdateStatement using the given parameters
	 *
	 * @param table The {@link TableRef table} to update
	 * @param setStatements The {@link EqualsStatement statements} to set values
	 * @param whereStatement The {@link Conditional where statement}
	 */
	private SQLUpdateStatement(TableRef table, List<EqualsStatement> setStatements,
	                           Conditional whereStatement){
		this.table = table;
		this.setStatements = setStatements;
		this.whereStatement = whereStatement;
	}
	
	/**
	 * @param table The {@link TableRef table} to update
	 * @return A new {@link SQLUpdateStatementBuilder builder} to use to build a {@link SQLUpdateStatement}
	 */
	public static SQLUpdateStatementBuilder builder(TableRef table){
		return new SQLUpdateStatementBuilder(table);
	}
	
	/**
	 * @return The {@link TableRef table} to update
	 */
	public TableRef getTable(){
		return table;
	}
	
	/**
	 * @return The {@link EqualsStatement statements} to set values
	 */
	public List<EqualsStatement> getSetStatements(){
		return setStatements;
	}
	
	/**
	 * @return The {@link Conditional where statement}
	 */
	public Conditional getWhereStatement(){
		return whereStatement;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		// Start statement
		StringBuilder statement = new StringBuilder("UPDATE ");
		
		// Add table
		statement.append(table).append(" SET ");
		
		// Add statements
		for(EqualsStatement setStatement: setStatements){
			statement.append(setStatement).append(", ");
		}
		// Remove last unnecessary comma
		statement.delete(statement.length()-2, statement.length());
		
		// Add where statement if we have it
		if(whereStatement != null){
			statement.append(" WHERE ").append(whereStatement);
		}
		
		return statement.toString();
	}
}
