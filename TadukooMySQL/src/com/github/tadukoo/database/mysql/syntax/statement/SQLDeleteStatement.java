package com.github.tadukoo.database.mysql.syntax.statement;

import com.github.tadukoo.database.mysql.syntax.conditional.Conditional;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLDeleteStatement represents a delete statement in MySQL
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class SQLDeleteStatement{
	
	/**
	 * A builder used to build a {@link SQLDeleteStatement}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>SQL Delete Statement Parameters</caption>
	 *     <tr>
	 *         <th>Parameter Name</th>
	 *         <th>Description</th>
	 *         <th>Default or Required</th>
	 *     </tr>
	 *     <tr>
	 *         <td>table</td>
	 *         <td>The {@link TableRef table} to delete from</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>whereStatement</td>
	 *         <td>The {@link Conditional where statement} to use for what to delete</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class SQLDeleteStatementBuilder implements Table, WhereStatementAndBuild{
		/** The {@link TableRef table} to delete from */
		private TableRef table;
		/** The {@link Conditional where statement} to use for what to delete */
		private Conditional whereStatement = null;
		
		/**
		 * Not allowed to instantiate outside {@link SQLDeleteStatement}
		 */
		private SQLDeleteStatementBuilder(){ }
		
		/** {@inheritDoc} */
		public WhereStatementAndBuild table(TableRef table){
			this.table = table;
			return this;
		}
		
		/** {@inheritDoc} */
		public WhereStatementAndBuild whereStatement(Conditional whereStatement){
			this.whereStatement = whereStatement;
			return this;
		}
		
		/**
		 * Throws an IllegalArgumentException if any errors occur in the parameters set
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// table is required
			if(table == null){
				errors.add("table is required!");
			}
			
			// report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Errors encountered while creating SQLDeleteStatement:\n" +
						StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/** {@inheritDoc} */
		public SQLDeleteStatement build(){
			checkForErrors();
			
			return new SQLDeleteStatement(table, whereStatement);
		}
	}
	
	/** The {@link TableRef table} to delete from */
	private final TableRef table;
	/** The {@link Conditional where statement} to use for what to delete */
	private final Conditional whereStatement;
	
	/**
	 * Constructs a new SQL Delete Statement with the given parameters
	 *
	 * @param table The {@link TableRef table} to delete from
	 * @param whereStatement The {@link Conditional where statement} to use for what to delete
	 */
	private SQLDeleteStatement(TableRef table, Conditional whereStatement){
		this.table = table;
		this.whereStatement = whereStatement;
	}
	
	/**
	 * @return A new {@link SQLDeleteStatementBuilder builder} to build a {@link SQLDeleteStatement}
	 */
	public static Table builder(){
		return new SQLDeleteStatementBuilder();
	}
	
	/**
	 * @return The {@link TableRef table} to delete from
	 */
	public TableRef getTable(){
		return table;
	}
	
	/**
	 * @return The {@link Conditional where statement} to use for what to delete
	 */
	public Conditional getWhereStatement(){
		return whereStatement;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		return "DELETE FROM " + table + (whereStatement == null?"":" WHERE " + whereStatement);
	}
	
	/*
	 * Interfaces for builder
	 */
	
	/**
	 * The {@link TableRef table} part of building a {@link SQLDeleteStatement}
	 */
	public interface Table{
		/**
		 * @param table The {@link TableRef table} to delete from
		 * @return this, to continue building
		 */
		WhereStatementAndBuild table(TableRef table);
	}
	
	/**
	 * The {@link Conditional Where Statement} and building part of building a {@link SQLDeleteStatement}
	 */
	public interface WhereStatementAndBuild{
		/**
		 * @param whereStatement The {@link Conditional where statement} to use for what to delete
		 * @return this, to continue building
		 */
		WhereStatementAndBuild whereStatement(Conditional whereStatement);
		
		/**
		 * Builds a new {@link SQLDeleteStatement} with the given parameters
		 *
		 * @return The newly built {@link SQLDeleteStatement}
		 */
		SQLDeleteStatement build();
	}
}
