package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLInsertAndSelectStatement represents a MySQL insert into select statement
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class SQLInsertAndSelectStatement{
	
	/**
	 * A builder class used to create a {@link SQLInsertAndSelectStatement}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>SQL Insert and Select Statement Parameters</caption>
	 *     <tr>
	 *         <th>Parameter Name</th>
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
	 *         <td>Defaults to an empty list</td>
	 *     </tr>
	 *     <tr>
	 *         <td>selectStmt</td>
	 *         <td>The {@link SQLSelectStatement select statement} to use for values to insert</td>
	 *         <td>Required</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class SQLInsertAndSelectStatementBuilder{
		/** The {@link TableRef table} to insert into */
		private final TableRef table;
		/** The {@link ColumnRef columns} to insert into */
		private List<ColumnRef> columns = new ArrayList<>();
		/** The {@link SQLSelectStatement select statement} to use for values to insert */
		private final SQLSelectStatement selectStmt;
		
		/**
		 * Not allowed to instantiate outside SQLInsertAndSelectStatement
		 *
		 * @param table The {@link TableRef table} to insert into
		 * @param selectStmt The {@link SQLSelectStatement select statement} to use for values to insert
		 */
		private SQLInsertAndSelectStatementBuilder(TableRef table, SQLSelectStatement selectStmt){
			this.table = table;
			this.selectStmt = selectStmt;
		}
		
		/**
		 * @param columns The {@link ColumnRef columns} to insert into
		 * @return this, to continue building
		 */
		public SQLInsertAndSelectStatementBuilder columns(List<ColumnRef> columns){
			this.columns = columns;
			return this;
		}
		
		/**
		 * @param column A {@link ColumnRef column} to insert into (added to the list)
		 * @return this, to continue building
		 */
		public SQLInsertAndSelectStatementBuilder column(ColumnRef column){
			columns.add(column);
			return this;
		}
		
		/**
		 * Checks for any errors in the set parameters
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// table is required
			if(table == null){
				errors.add("table is required!");
			}
			
			// selectStmt is required
			if(selectStmt == null){
				errors.add("selectStmt is required!");
			}
			
			// Report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Encountered the following errors trying to build a " +
						"SQLInsertAndSelectStatement: \n" + StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/**
		 * Builds a new {@link SQLInsertAndSelectStatement} based on the set parameters
		 *
		 * @return The newly built {@link SQLInsertAndSelectStatement}
		 */
		public SQLInsertAndSelectStatement build(){
			checkForErrors();
			
			return new SQLInsertAndSelectStatement(table, columns, selectStmt);
		}
	}
	
	/** The {@link TableRef table} to insert into */
	private final TableRef table;
	/** The {@link ColumnRef columns} to insert into */
	private final List<ColumnRef> columns;
	/** The {@link SQLSelectStatement select statement} to use for values to insert */
	private final SQLSelectStatement selectStmt;
	
	/**
	 * Constructs a new SQLInsertAndSelectStatement with the given parameters
	 *
	 * @param table The {@link TableRef table} to insert into
	 * @param columns The {@link ColumnRef columns} to insert into
	 * @param selectStmt The {@link SQLSelectStatement select statement} to use for values to insert
	 */
	private SQLInsertAndSelectStatement(TableRef table, List<ColumnRef> columns, SQLSelectStatement selectStmt){
		this.table = table;
		this.columns = columns;
		this.selectStmt = selectStmt;
	}
	
	/**
	 * @param table The {@link TableRef table} to insert into
	 * @param selectStmt The {@link SQLSelectStatement select statement} to use for values to insert
	 * @return A new {@link SQLInsertAndSelectStatementBuilder builder} to use to build a
	 * {@link SQLInsertAndSelectStatement}
	 */
	public static SQLInsertAndSelectStatementBuilder builder(TableRef table, SQLSelectStatement selectStmt){
		return new SQLInsertAndSelectStatementBuilder(table, selectStmt);
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
		
		// Add select statement
		statement.append(selectStmt);
		
		return statement.toString();
	}
}
