package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.database.mysql.syntax.conditional.ConditionalStatement;
import com.github.tadukoo.database.mysql.syntax.conditional.SQLOperator;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.database.mysql.syntax.statement.SQLInsertStatement;

import java.util.Collection;
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
			return "'" + s + "'";
		}else if(value instanceof Integer i){
			return i.toString();
		}else if(value instanceof Boolean b){
			return b.toString();
		}else{
			return value.toString();
		}
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
}
