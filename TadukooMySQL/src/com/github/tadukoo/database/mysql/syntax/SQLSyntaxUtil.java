package com.github.tadukoo.database.mysql.syntax;

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
}
