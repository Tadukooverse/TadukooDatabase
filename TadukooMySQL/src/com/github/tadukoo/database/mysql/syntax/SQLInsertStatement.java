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
	 *         <td>Required</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class SQLInsertStatementBuilder{
		/** The {@link TableRef table} to insert into */
		private TableRef table = null;
		/** The {@link ColumnRef columns} to insert into */
		private List<ColumnRef> columns = new ArrayList<>();
		/** The values to insert */
		private List<Object> values = new ArrayList<>();
		
		/** Not allowed to instantiate outside SQLInsertStatement */
		private SQLInsertStatementBuilder(){ }
		
		/**
		 * @param table The {@link TableRef table} to insert into
		 * @return this, to continue building
		 */
		public SQLInsertStatementBuilder table(TableRef table){
			this.table = table;
			return this;
		}
		
		/**
		 * @param columns The {@link ColumnRef columns} to insert into
		 * @return this, to continue building
		 */
		public SQLInsertStatementBuilder columns(List<ColumnRef> columns){
			this.columns = columns;
			return this;
		}
		
		/**
		 * @param column A {@link ColumnRef column} to insert into (added to the list)
		 * @return this, to continue building
		 */
		public SQLInsertStatementBuilder column(ColumnRef column){
			columns.add(column);
			return this;
		}
		
		/**
		 * @param values The values to insert
		 * @return this, to continue building
		 */
		public SQLInsertStatementBuilder values(List<Object> values){
			this.values = values;
			return this;
		}
		
		/**
		 * @param value A value to insert (added to the list)
		 * @return this, to continue building
		 */
		public SQLInsertStatementBuilder value(Object value){
			values.add(value);
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
			
			// values can't be empty
			if(ListUtil.isBlank(values)){
				errors.add("values can't be empty!");
			}
			
			// if columns isn't empty, it must match the number of values
			if(ListUtil.isNotBlank(columns) && columns.size() != values.size()){
				errors.add("Number of columns must equal number of values if specified!");
			}
			
			// Report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Errors encountered while building SQLInsertStatement: \n"
						+ StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/**
		 * Creates a new {@link SQLInsertStatement} after checking for errors
		 *
		 * @return The newly built {@link SQLInsertStatement}
		 */
		public SQLInsertStatement build(){
			checkForErrors();
			
			return new SQLInsertStatement(table, columns, values);
		}
	}
	
	/** The {@link TableRef table} to insert into */
	private final TableRef table;
	/** The {@link ColumnRef columns} to insert into */
	private final List<ColumnRef> columns;
	/** The values to insert */
	private final List<Object> values;
	
	/**
	 * Constructs a new SQLInsertStatement using the given parameters
	 *
	 * @param table The {@link TableRef table} to insert into
	 * @param columns The {@link ColumnRef columns} to insert into
	 * @param values The values to insert
	 */
	private SQLInsertStatement(TableRef table, List<ColumnRef> columns, List<Object> values){
		this.table = table;
		this.columns = columns;
		this.values = values;
	}
	
	/**
	 * @return A {@link SQLInsertStatementBuilder builder} to use to make a {@link SQLInsertStatement}
	 */
	public static SQLInsertStatementBuilder builder(){
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
		
		// Add values
		statement.append("VALUES (");
		for(Object value: values){
			statement.append(SQLSyntaxUtil.convertValueToString(value)).append(", ");
		}
		// Remove last unnecessary comma
		statement.delete(statement.length()-2, statement.length());
		statement.append(')');
		
		return statement.toString();
	}
}
