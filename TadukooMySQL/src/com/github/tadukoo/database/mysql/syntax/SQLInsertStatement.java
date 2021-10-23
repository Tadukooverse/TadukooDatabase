package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLInsertStatement represents a MySQL Insert Statement
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class SQLInsertStatement{
	
	/**
	 * A builder class used to build a {@link SQLInsertStatement}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>SQL Insert Statement Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Default or Required</th>
	 *     </tr>
	 *     <tr>
	 *         <td>table</td>
	 *         <td>The {@link TableRef table} to insert into</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>columns</td>
	 *         <td>The {@link ColumnRef columns} to insert into</td>
	 *         <td>Defaults to empty list</td>
	 *     </tr>
	 *     <tr>
	 *         <td>values</td>
	 *         <td>The values to insert</td>
	 *         <td>Defaults to empty list - need either this or select statement</td>
	 *     </tr>
	 *     <tr>
	 *         <td>selectStmt</td>
	 *         <td>The {@link SQLSelectStatement select statement} to use for values to insert</td>
	 *         <td>Defaults to null - need either this or values</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class SQLInsertStatementBuilder implements Table, ColumnsAndValuesOrSelectStatement, Build{
		/** The {@link TableRef table} to insert into */
		private TableRef table;
		/** The {@link ColumnRef columns} to insert into */
		private List<ColumnRef> columns = new ArrayList<>();
		/** The values to insert */
		private List<Object> values = new ArrayList<>();
		/** The {@link SQLSelectStatement select statement} to use for values to insert */
		private SQLSelectStatement selectStmt = null;
		
		/**
		 * Not allowed to instantiate outside SQLInsertStatement
		 */
		private SQLInsertStatementBuilder(){ }
		
		/** {@inheritDoc} */
		public ColumnsAndValuesOrSelectStatement table(TableRef table){
			this.table = table;
			return this;
		}
		
		/** {@inheritDoc} */
		public ColumnsAndValuesOrSelectStatement columns(List<ColumnRef> columns){
			this.columns = columns;
			return this;
		}
		
		/** {@inheritDoc} */
		public ColumnsAndValuesOrSelectStatement columns(ColumnRef ... columns){
			this.columns = ListUtil.createList(columns);
			return this;
		}
		
		/** {@inheritDoc} */
		public Build values(List<Object> values){
			this.values = values;
			return this;
		}
		
		/** {@inheritDoc} */
		public Build values(Object ... values){
			this.values = ListUtil.createList(values);
			return this;
		}
		
		/** {@inheritDoc} */
		public Build selectStmt(SQLSelectStatement selectStmt){
			this.selectStmt = selectStmt;
			return this;
		}
		
		/**
		 * Checks for any errors and throws an IllegalArgumentException if any are found
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// table is required
			if(table == null){
				errors.add("table is required!");
			}
			
			// Need either values or select statement
			if(ListUtil.isBlank(values) && selectStmt == null){
				errors.add("Must specify either values or selectStmt!");
			}
			
			// if columns and values aren't empty, it must match the number of values
			if(ListUtil.isNotBlank(columns) && ListUtil.isNotBlank(values) && columns.size() != values.size()){
				errors.add("Number of columns must equal number of values if specified!");
			}
			
			// Report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Errors encountered while building SQLInsertStatement: \n"
						+ StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/** {@inheritDoc} */
		public SQLInsertStatement build(){
			checkForErrors();
			
			return new SQLInsertStatement(table, columns, values, selectStmt);
		}
	}
	
	/** The {@link TableRef table} to insert into */
	private final TableRef table;
	/** The {@link ColumnRef columns} to insert into */
	private final List<ColumnRef> columns;
	/** The values to insert */
	private final List<Object> values;
	/** The {@link SQLSelectStatement select statement} to use for values to insert */
	private final SQLSelectStatement selectStmt;
	
	/**
	 * Constructs a new SQLInsertStatement using the given parameters
	 *
	 * @param table The {@link TableRef table} to insert into
	 * @param columns The {@link ColumnRef columns} to insert into
	 * @param values The values to insert
	 * @param selectStmt The {@link SQLSelectStatement select statement} to use for values to insert
	 */
	private SQLInsertStatement(TableRef table, List<ColumnRef> columns, List<Object> values,
	                           SQLSelectStatement selectStmt){
		this.table = table;
		this.columns = columns;
		this.values = values;
		this.selectStmt = selectStmt;
	}
	
	/**
	 * @return A {@link SQLInsertStatementBuilder builder} to use to make a {@link SQLInsertStatement}
	 */
	public static Table builder(){
		return new SQLInsertStatementBuilder();
	}
	
	/**
	 * @return The {@link TableRef table} to insert into
	 */
	public TableRef getTable(){
		return table;
	}
	
	/**
	 * @return The {@link ColumnRef columns} to insert into
	 */
	public List<ColumnRef> getColumns(){
		return columns;
	}
	
	/**
	 * @return The values to insert
	 */
	public List<Object> getValues(){
		return values;
	}
	
	/**
	 * @return The {@link SQLSelectStatement select statement} to use for values to insert
	 */
	public SQLSelectStatement getSelectStmt(){
		return selectStmt;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		StringBuilder statement = new StringBuilder("INSERT INTO ");
		
		// Add table
		statement.append(table).append(' ');
		
		// Add columns if we have them
		if(ListUtil.isNotBlank(columns)){
			statement.append('(');
			for(ColumnRef column: columns){
				statement.append(column).append(", ");
			}
			// Remove last unnecessary comma
			statement.delete(statement.length()-2, statement.length());
			statement.append(") ");
		}
		
		// Add select statement if we have it
		if(selectStmt != null){
			statement.append(selectStmt);
		}else{
			// Add values
			statement.append("VALUES (");
			for(Object value: values){
				statement.append(SQLSyntaxUtil.convertValueToString(value)).append(", ");
			}
			// Remove last unnecessary comma
			statement.delete(statement.length() - 2, statement.length());
			statement.append(')');
		}
		
		return statement.toString();
	}
	
	/*
	 * Interfaces for builder
	 */
	
	/**
	 * The {@link TableRef Table} part of building a {@link SQLInsertStatement}
	 */
	public interface Table{
		/**
		 * @param table The {@link TableRef table} to insert into
		 * @return this, to continue building
		 */
		ColumnsAndValuesOrSelectStatement table(TableRef table);
	}
	
	/**
	 * The {@link ColumnRef Columns} and Values part of building a {@link SQLInsertStatement}
	 */
	public interface ColumnsAndValuesOrSelectStatement{
		/**
		 * @param columns The {@link ColumnRef columns} to insert into
		 * @return this, to continue building
		 */
		ColumnsAndValuesOrSelectStatement columns(List<ColumnRef> columns);
		
		/**
		 * @param columns A {@link ColumnRef column} to insert into
		 * @return this, to continue building
		 */
		ColumnsAndValuesOrSelectStatement columns(ColumnRef ... columns);
		
		/**
		 * @param values The values to insert
		 * @return this, to continue building
		 */
		Build values(List<Object> values);
		
		/**
		 * @param values The values to insert
		 * @return this, to continue building
		 */
		Build values(Object ... values);
		
		/**
		 * @param selectStmt The {@link SQLSelectStatement select statement} to use for values to insert
		 * @return this, to continue building
		 */
		Build selectStmt(SQLSelectStatement selectStmt);
	}
	
	/**
	 * The Building part of building a {@link SQLInsertStatement}
	 */
	public interface Build{
		/**
		 * Creates a new {@link SQLInsertStatement} after checking for errors
		 *
		 * @return The newly built {@link SQLInsertStatement}
		 */
		SQLInsertStatement build();
	}
}
