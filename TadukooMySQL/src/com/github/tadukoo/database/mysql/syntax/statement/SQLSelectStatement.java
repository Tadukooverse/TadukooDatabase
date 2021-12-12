package com.github.tadukoo.database.mysql.syntax.statement;

import com.github.tadukoo.database.mysql.syntax.conditional.Conditional;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL Select Statement is used to build a MySQL select statement
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class SQLSelectStatement{
	
	/**
	 * A builder to build a {@link SQLSelectStatement}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>SQL Select Statement Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Required or Default</th>
	 *     </tr>
	 *     <tr>
	 *         <td>distinct</td>
	 *         <td>Whether to select distinct columns or not</td>
	 *         <td>Defaults to false</td>
	 *     </tr>
	 *     <tr>
	 *         <td>returnColumns</td>
	 *         <td>The columns to be selected</td>
	 *         <td>Defaults to an empty list (will select all then)</td>
	 *     </tr>
	 *     <tr>
	 *         <td>fromTables</td>
	 *         <td>The tables to grab data from</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>whereStatement</td>
	 *         <td>The conditional where statement</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class SQLSelectStatementBuilder implements DistinctOrColumnsOrTables, ColumnsOrTables, FromTables,
			WhereStatementAndBuild, Build{
		/** Whether to select distinct columns or not */
		private boolean distinct = false;
		/** The columns to be selected */
		private List<ColumnRef> returnColumns = new ArrayList<>();
		/** The tables to grab data from */
		private List<TableRef> fromTables = new ArrayList<>();
		/** The conditional where statement */
		private Conditional whereStatement = null;
		
		/** Not allowed to instantiate outside SQLSelectStatement */
		private SQLSelectStatementBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		public ColumnsOrTables distinct(){
			this.distinct = true;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public FromTables returnColumns(List<ColumnRef> returnColumns){
			this.returnColumns = returnColumns;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public FromTables returnColumns(ColumnRef ... returnColumns){
			this.returnColumns = ListUtil.createList(returnColumns);
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public WhereStatementAndBuild fromTables(List<TableRef> fromTables){
			this.fromTables = fromTables;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public WhereStatementAndBuild fromTables(TableRef ... fromTables){
			this.fromTables = ListUtil.createList(fromTables);
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Build whereStatement(Conditional whereStatement){
			this.whereStatement = whereStatement;
			return this;
		}
		
		/**
		 * Checks for any errors with the set parameters
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// fromTables is required
			if(ListUtil.isBlank(fromTables)){
				errors.add("Must add at least one fromTable!");
			}
			
			// Report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Encountered the following errors trying to build a " +
						"SQLSelectStatement: \n" + StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/** {@inheritDoc} */
		@Override
		public SQLSelectStatement build(){
			checkForErrors();
			
			return new SQLSelectStatement(distinct, returnColumns, fromTables, whereStatement);
		}
	}
	
	/** Whether to select distinct columns or not */
	private final boolean distinct;
	/** The columns to be selected */
	private final List<ColumnRef> returnColumns;
	/** The tables to grab data from */
	private final List<TableRef> fromTables;
	/** The conditional where statement */
	private final Conditional whereStatement;
	
	/**
	 * Constructs a new {@link SQLSelectStatement} using the given parameters.
	 *
	 * @param distinct Whether to select distinct columns or not
	 * @param returnColumns The columns to be selected
	 * @param fromTables The tables to grab data from
	 * @param whereStatement The conditional where statement
	 */
	private SQLSelectStatement(boolean distinct, List<ColumnRef> returnColumns, List<TableRef> fromTables,
	                           Conditional whereStatement){
		this.distinct = distinct;
		this.returnColumns = returnColumns;
		this.fromTables = fromTables;
		this.whereStatement = whereStatement;
	}
	
	/**
	 * @return A new {@link SQLSelectStatementBuilder builder} to build a {@link SQLSelectStatement}
	 */
	public static DistinctOrColumnsOrTables builder(){
		return new SQLSelectStatementBuilder();
	}
	
	/**
	 * @return Whether to select distinct columns or not
	 */
	public boolean isDistinct(){
		return distinct;
	}
	
	/**
	 * @return The columns to be selected
	 */
	public List<ColumnRef> getReturnColumns(){
		return returnColumns;
	}
	
	/**
	 * @return The tables to grab data from
	 */
	public List<TableRef> getFromTables(){
		return fromTables;
	}
	
	/**
	 * @return The conditional where statement
	 */
	public Conditional getWhereStatement(){
		return whereStatement;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		StringBuilder statement = new StringBuilder("SELECT ");
		
		// Set distinct if it's set
		if(distinct){
			statement.append("DISTINCT ");
		}
		
		// Determine all vs. columns
		if(ListUtil.isBlank(returnColumns)){
			statement.append("* ");
		}else{
			// Add return columns
			for(ColumnRef column: returnColumns){
				statement.append(column.toString()).append(", ");
			}
			// Remove last unnecessary comma
			statement.delete(statement.length()-2, statement.length());
			statement.append(' ');
		}
		
		// Add from to statement
		statement.append("FROM ");
		
		// Add from table(s)
		for(TableRef table: fromTables){
			statement.append(table.toString()).append(", ");
		}
		// Remove last unnecessary comma
		statement.delete(statement.length()-2, statement.length());
		
		// Add where statement if we have it
		if(whereStatement != null){
			statement.append(" WHERE ").append(whereStatement);
		}
		
		// Return the statement we built
		return statement.toString();
	}
	
	/*
	 * Interfaces for the builder
	 */
	
	/**
	 * The distinct of {@link ColumnRef columns} or {@link TableRef Tables} part of building a
	 * {@link SQLSelectStatement}
	 */
	public interface DistinctOrColumnsOrTables extends ColumnsOrTables{
		/**
		 * Sets distinct to true for this select statement
		 * @return this, to continue building
		 */
		ColumnsOrTables distinct();
	}
	
	/**
	 * The {@link ColumnRef Columns} and {@link TableRef Tables} part of building a {@link SQLSelectStatement}
	 */
	public interface ColumnsOrTables extends FromTables{
		/**
		 * @param returnColumns The columns to be selected
		 * @return this, to continue building
		 */
		FromTables returnColumns(List<ColumnRef> returnColumns);
		
		/**
		 * @param returnColumns The columns to be selected
		 * @return this, to continue building
		 */
		FromTables returnColumns(ColumnRef ... returnColumns);
	}
	
	/**
	 * The {@link TableRef Tables} part of building a {@link SQLSelectStatement}
	 */
	public interface FromTables{
		/**
		 * @param fromTables The tables to grab data from
		 * @return this, to continue building
		 */
		WhereStatementAndBuild fromTables(List<TableRef> fromTables);
		
		/**
		 * @param fromTables The tables to grab data from
		 * @return this, to continue building
		 */
		WhereStatementAndBuild fromTables(TableRef ... fromTables);
	}
	
	/**
	 * The {@link Conditional Where Statement} and building part of building a {@link SQLSelectStatement}
	 */
	public interface WhereStatementAndBuild extends Build{
		/**
		 * @param whereStatement The conditional where statement
		 * @return this, to continue building
		 */
		Build whereStatement(Conditional whereStatement);
	}
	
	/**
	 * The building part of building a {@link SQLSelectStatement}
	 */
	public interface Build{
		/**
		 * Builds a new {@link SQLSelectStatement} using the set parameters
		 *
		 * @return The newly built {@link SQLSelectStatement}
		 */
		SQLSelectStatement build();
	}
}
