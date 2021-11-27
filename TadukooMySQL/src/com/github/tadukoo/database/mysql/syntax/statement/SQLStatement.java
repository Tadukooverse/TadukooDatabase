package com.github.tadukoo.database.mysql.syntax.statement;

/**
 * SQL Statement is a class used to create a MySQL Statement
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class SQLStatement{
	
	/**
	 * A builder class used to start building a SQL Statement. The following options are available:
	 *
	 * <table>
	 *     <caption>SQL Statement Types</caption>
	 *     <tr>
	 *         <th>Type</th>
	 *         <th>Class</th>
	 *     </tr>
	 *     <tr>
	 *         <td>select</td>
	 *         <td>{@link SQLSelectStatement}</td>
	 *     </tr>
	 *     <tr>
	 *         <td>insert</td>
	 *         <td>{@link SQLInsertStatement}</td>
	 *     </tr>
	 *     <tr>
	 *         <td>update</td>
	 *         <td>{@link SQLUpdateStatement}</td>
	 *     </tr>
	 *     <tr>
	 *         <td>delete</td>
	 *         <td>{@link SQLDeleteStatement}</td>
	 *     </tr>
	 *     <tr>
	 *         <td>create</td>
	 *         <td>{@link SQLCreateStatement}</td>
	 *     </tr>
	 *     <tr>
	 *         <td>drop</td>
	 *         <td>{@link SQLDropStatement}</td>
	 *     </tr>
	 *     <tr>
	 *         <td>alter</td>
	 *         <td>{@link SQLAlterStatement}</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class SQLStatementBuilder{
		
		/** Not allowed to instantiate outside {@link SQLStatement} */
		private SQLStatementBuilder(){ }
		
		/**
		 * @return A {@link SQLSelectStatement.SQLSelectStatementBuilder builder} to use to make a
		 * {@link SQLSelectStatement}
		 */
		public SQLSelectStatement.ColumnsAndTables select(){
			return SQLSelectStatement.builder();
		}
		
		/**
		 * @return A {@link SQLInsertStatement.SQLInsertStatementBuilder builder} to use to make a
		 * {@link SQLInsertStatement}
		 */
		public SQLInsertStatement.Table insert(){
			return SQLInsertStatement.builder();
		}
		
		/**
		 * @return A {@link SQLUpdateStatement.SQLUpdateStatementBuilder builder} to use to make a
		 * {@link SQLUpdateStatement}
		 */
		public SQLUpdateStatement.Table update(){
			return SQLUpdateStatement.builder();
		}
		
		/**
		 * @return A {@link SQLDeleteStatement.SQLDeleteStatementBuilder builder} to use to make a
		 * {@link SQLDeleteStatement}
		 */
		public SQLDeleteStatement.Table delete(){
			return SQLDeleteStatement.builder();
		}
		
		/**
		 * @return A {@link SQLCreateStatement.SQLCreateStatementBuilder builder} to use to create a
		 * {@link SQLCreateStatement}
		 */
		public SQLCreateStatement.Type create(){
			return SQLCreateStatement.builder();
		}
		
		/**
		 * @return A {@link SQLDropStatement.SQLDropStatementBuilder builder} to use to create a
		 * {@link SQLDropStatement}
		 */
		public SQLDropStatement.Type drop(){
			return SQLDropStatement.builder();
		}
		
		/**
		 * @return A {@link SQLAlterStatement.SQLAlterStatementBuilder builder} to use to create a
		 * {@link SQLAlterStatement}
		 */
		public SQLAlterStatement.Type alter(){
			return SQLAlterStatement.builder();
		}
	}
	
	/** Not allowed to actually instantiate */
	private SQLStatement(){ }
	
	/**
	 * @return A {@link SQLStatementBuilder builder} to start building a SQL Statement
	 */
	public static SQLStatementBuilder builder(){
		return new SQLStatementBuilder();
	}
}
