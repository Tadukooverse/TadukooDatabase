package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.database.mysql.syntax.conditional.Conditional;
import com.github.tadukoo.database.mysql.syntax.conditional.ConditionalStatement;
import com.github.tadukoo.database.mysql.syntax.conditional.EqualsStatement;
import com.github.tadukoo.database.mysql.syntax.conditional.SQLConjunctiveOperator;
import com.github.tadukoo.database.mysql.syntax.conditional.SQLOperator;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.database.mysql.syntax.statement.SQLInsertStatement;
import com.github.tadukoo.database.mysql.syntax.statement.SQLSelectStatement;
import com.github.tadukoo.database.mysql.syntax.statement.SQLUpdateStatement;
import com.github.tadukoo.util.ByteUtil;
import com.github.tadukoo.util.StringUtil;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SQL Syntax Util provides utilities for handling SQL syntax.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class SQLSyntaxUtil{
	
	/** Not allowed to instantiate SQLSyntaxUtil */
	private SQLSyntaxUtil(){ }
	
	/**
	 * Converts the given object into a string to use for a value in MySQL
	 *
	 * @param value The object to convert
	 * @return The string representing the given value
	 */
	public static String convertValueToString(Object value){
		if(value instanceof String s){
			return "'" + escapeString(s) + "'";
		}else if(value instanceof Integer i){
			return i.toString();
		}else if(value instanceof Boolean b){
			return b.toString();
		}else if(value instanceof byte[] b){
			return "0x" + ByteUtil.toHex(b);
		}else{
			String str = escapeString(value.toString());
			if(value instanceof Character ||
					value instanceof Time || value instanceof Date || value instanceof Timestamp ||
					value instanceof Enum<?>){
				str = "'" + str + "'";
			}
			return str;
		}
	}
	
	public static String escapeString(String str){
		return str.replace("'", "''");
	}
	
	/**
	 * Extracts a value from the given {@link ResultSet} based on the info in the given {@link ColumnDefinition}
	 *
	 * @param resultSet The {@link ResultSet} to extract data from
	 * @param tableName The name of the table (can be blank)
	 * @param columnDef The {@link ColumnDefinition} to use for info
	 * @return The extracted value
	 * @throws SQLException If anything goes wrong
	 */
	public static Object getValueBasedOnColumnDefinition(ResultSet resultSet, String tableName, ColumnDefinition columnDef)
			throws SQLException{
		String columnName = columnDef.getColumnName();
		if(StringUtil.isNotBlank(tableName)){
			columnName = tableName + "." + columnName;
		}
		return switch(columnDef.getDataType()){
			case CHAR -> resultSet.getString(columnName).charAt(0);
			case VARCHAR, TINYTEXT, TEXT, MEDIUMTEXT, LONGTEXT, ENUM, SET ->
					resultSet.getString(columnName);
			case BINARY, VARBINARY, TINYBLOB, BLOB, MEDIUMBLOB, LONGBLOB, BIT ->
					resultSet.getBytes(columnName);
			case TINYINT, SMALLINT, MEDIUMINT -> resultSet.getInt(columnName);
			case BOOL -> resultSet.getBoolean(columnName);
			case INTEGER -> {
				// Apparently a ternary operator doesn't work here ;( (will always use the Long version)
				if(columnDef.isUnsigned()){
					yield resultSet.getLong(columnName);
				}else{
					yield resultSet.getInt(columnName);
				}
			}
			case BIGINT -> columnDef.isUnsigned()
					?new BigInteger(resultSet.getString(columnName))
					:resultSet.getLong(columnName);
			case FLOAT -> resultSet.getFloat(columnName);
			case DOUBLE -> resultSet.getDouble(columnName);
			case DECIMAL -> resultSet.getBigDecimal(columnName);
			case DATE -> resultSet.getDate(columnName);
			case YEAR -> resultSet.getShort(columnName);
			case DATETIME, TIMESTAMP -> resultSet.getTimestamp(columnName);
			case TIME -> resultSet.getTime(columnName);
		};
	}
	
	/**
	 * Makes a single {@link ColumnRef} using the given columnName
	 *
	 * @param columnName The name of the column
	 * @return A {@link ColumnRef} with the given columnName
	 */
	public static ColumnRef makeColumnRef(String columnName){
		if(columnName.contains(".")){
			String[] pieces = columnName.split("\\.");
			return ColumnRef.builder()
					.tableName(pieces[0])
					.columnName(pieces[1])
					.build();
		}else{
			return ColumnRef.builder()
					.columnName(columnName)
					.build();
		}
	}
	
	/**
	 * Makes a List of {@link ColumnRef ColumnRefs} using the given columnNames
	 *
	 * @param columnNames The names of the columns
	 * @return A List of {@link ColumnRef ColumnRefs} with the given columnNames
	 */
	public static List<ColumnRef> makeColumnRefs(Collection<String> columnNames){
		return columnNames.stream()
				.map(SQLSyntaxUtil::makeColumnRef)
				.collect(Collectors.toList());
	}
	
	/**
	 * Creates a {@link ConditionalStatement} using the given parameters.
	 * <br><br>
	 * If search is {@code false}, it will create an equals statement with the given columnName and value
	 * <br><br>
	 * If search is {@code true}, it will create an equals statement for all non-string values. For String
	 * values, it will create a LIKE statement and surround the value with % for matching strings with that
	 * value anywhere in the string
	 *
	 * @param search Whether to consider this a search and do LIKE for strings, or not
	 * @param columnName The name of the column
	 * @param value The value for the conditional statement
	 * @return A new {@link ConditionalStatement}
	 */
	public static ConditionalStatement makeConditionalStmt(boolean search, String columnName, Object value){
		// Determine operator (search = true + String value will make it a LIKE with % around it)
		SQLOperator operator = SQLOperator.EQUAL;
		if(search && value instanceof String){
			operator = SQLOperator.LIKE;
			value = "%" + value + "%";
		}
		
		// Make the statement and return it
		return ConditionalStatement.builder()
				.column(makeColumnRef(columnName))
				.operator(operator)
				.value(value)
				.build();
	}
	
	/**
	 * Makes a {@link Conditional} using the given parameters
	 *
	 * @param columnNames The names of the columns to use in the {@link Conditional}
	 * @param values The values to use in the {@link Conditional}
	 * @param search Whether to consider this a search or not
	 * @return The {@link Conditional} that was made from the parameters
	 */
	public static Conditional makeConditional(Collection<String> columnNames, Collection<Object> values, boolean search){
		if(values.size() == 1){
			return Conditional.builder()
					.firstCondStmt(makeConditionalStmt(search, columnNames.iterator().next(), values.iterator().next()))
					.build();
		}else{
			Conditional cond = null;
			// Iterate over the columnNames and values
			Iterator<String> colIt = columnNames.iterator();
			Iterator<Object> valIt = values.iterator();
			while(colIt.hasNext()){
				ConditionalStatement condStmt = makeConditionalStmt(search, colIt.next(), valIt.next());
				if(cond == null){
					// If this is the first one, build a new conditional with just the first statement
					cond = Conditional.builder()
							.firstCondStmt(condStmt)
							.build();
				}else{
					// If we already started the conditional, and this statement with the existing conditional
					cond = Conditional.builder()
							.firstCond(cond)
							.operator(SQLConjunctiveOperator.AND)
							.secondCondStmt(condStmt)
							.build();
				}
			}
			return cond;
		}
	}
	
	/**
	 * Creates an Insert statement for the given parameters
	 *
	 * @param table The name of the table to insert into
	 * @param columnNames The names of the columns to insert into
	 * @param values The values to insert into the columns
	 * @return The SQL text for the insert statement
	 */
	public static String formatInsertStatement(String table, Collection<String> columnNames, Collection<Object> values){
		// Convert the columns to ColumnRefs
		List<ColumnRef> columns = makeColumnRefs(columnNames);
		
		// Build the insert statement
		SQLInsertStatement insertStmt = SQLInsertStatement.builder()
				.table(TableRef.builder().tableName(table).build())
				.columns(columns)
				.values(values.toArray())
				.build();
		
		// Return the insert statement string
		return insertStmt.toString();
	}
	
	/**
	 * Creates an Update statement for the given parameters
	 *
	 * @param table The name of the table to update
	 * @param columnNames The names of the columns to be updated
	 * @param values The values to be updated
	 * @param whereColNames The names of the columns for the where statement
	 * @param whereValues The values for the where statement
	 * @return The SQL text for the update statement
	 */
	public static String formatUpdateStatement(
			String table, Collection<String> columnNames, Collection<Object> values,
			Collection<String> whereColNames, Collection<Object> whereValues){
		// Convert the columns to ColumnRefs
		List<ColumnRef> columns = makeColumnRefs(columnNames);
		
		// Make the Set Statements
		List<EqualsStatement> setStmts = new ArrayList<>();
		Iterator<ColumnRef> columnIt = columns.iterator();
		Iterator<Object> valuesIt = values.iterator();
		while(columnIt.hasNext()){
			setStmts.add(new EqualsStatement(columnIt.next(), valuesIt.next()));
		}
		
		// Start the update statement
		SQLUpdateStatement.WhereStatementAndBuild updateStmtStart = SQLUpdateStatement.builder()
				.table(TableRef.builder().tableName(table).build())
				.setStatements(setStmts);
		
		// Add the where statement if we have it
		SQLUpdateStatement updateStmt;
		if(whereValues == null || whereValues.isEmpty()){
			updateStmt = updateStmtStart.build();
		}else{
				updateStmt = updateStmtStart.whereStatement(makeConditional(whereColNames, whereValues, false))
					.build();
		}
		
		// Return the update statement string
		return updateStmt.toString();
	}
	
	/**
	 * Creates a Select statement for the given parameters
	 *
	 * @param tables The name of the tables to select from
	 * @param returnColumns The names of the columns to be returned
	 * @param cols The names of the columns to include in the where query
	 * @param values The values for the columns in the where query
	 * @param search Whether this is a search (to do LIKE %value% for string values instead of equals)
	 * @return The SQL text for the select statement
	 */
	public static String formatQuery(
			Collection<String> tables, Collection<String> returnColumns, Collection<String> cols,
			Collection<Object> values, boolean search){
		// Cols and Values must be the same size
		if(cols.size() != values.size()){
			throw new IllegalArgumentException("cols and values must be the same size!");
		}
		
		// Convert the tables into TableRefs
		List<TableRef> fromTables = tables.stream()
				.map(table -> TableRef.builder().tableName(table).build())
				.collect(Collectors.toList());
		
		// Convert the return columns into ColumnRefs
		List<ColumnRef> returnCols = returnColumns.stream()
				.map(SQLSyntaxUtil::makeColumnRef)
				.collect(Collectors.toList());
		
		// Make the conditional
		Conditional cond = makeConditional(cols, values, search);
		
		// Build the select statement
		SQLSelectStatement selectStmt = SQLSelectStatement.builder()
				.distinct()
				.returnColumns(returnCols)
				.fromTables(fromTables)
				.whereStatement(cond)
				.build();
		
		// Return the select statement string
		return selectStmt.toString();
	}
}
