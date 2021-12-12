package com.github.tadukoo.database.mysql.syntax.conditional;

import com.github.tadukoo.util.StringUtil;

/**
 * SQL Operator represents a conditional operator in MySQL
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public enum SQLOperator{
	/** Equal to ({@code =}) */
	EQUAL("="),
	/** Not Equal to ({@code !=}) */
	NOT_EQUAL("!="),
	/** Greater Than ({@code >}) */
	GREATER_THAN(">"),
	/** Less Than ({@code <}) */
	LESS_THAN("<"),
	/** Greater Than or Equal To ({@code >=}) */
	GREATER_THAN_OR_EQUAL(">="),
	/** Less Than or Equal To ({@code <=}) */
	LESS_THAN_OR_EQUAL("<="),
	/** Between a given range ({@code BETWEEN}) */
	BETWEEN("BETWEEN"),
	/** Search for a pattern ({@code LIKE}) */
	LIKE("LIKE"),
	/** To specify multiple possible values for a column ({@code IN}) */
	IN("IN");
	
	/** The operator string */
	private final String operator;
	
	/**
	 * Constructs a new {@link SQLOperator} with the given parameters
	 *
	 * @param operator The operator string
	 */
	SQLOperator(String operator){
		this.operator = operator;
	}
	
	/**
	 * Grab a {@link SQLOperator} using the given string to match on the operator string
	 *
	 * @param operator The operator string to match on
	 * @return The found {@link SQLOperator}, or null if none was found
	 */
	public static SQLOperator fromString(String operator){
		// Try to find a SQLOperator with the given string
		for(SQLOperator op: values()){
			if(StringUtil.equalsIgnoreCase(op.toString(), operator)){
				return op;
			}
		}
		
		// return null if we can't find a matching operator
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		return operator;
	}
}
