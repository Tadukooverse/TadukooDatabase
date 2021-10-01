package com.github.tadukoo.database.mysql.syntax.conditional;

import com.github.tadukoo.util.StringUtil;

/**
 * SQL Conjunctive Operator represents operators that join conditional statements together.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public enum SQLConjunctiveOperator{
	/** Both statements must be true ({@code AND}) */
	AND("AND"),
	/** Either statement could be true ({@code OR}) */
	OR("OR");
	
	/** The operator string */
	private final String operator;
	
	/**
	 * Constructs a new SQLConjunctiveOperator using the given parameters
	 *
	 * @param operator The operator string
	 */
	SQLConjunctiveOperator(String operator){
		this.operator = operator;
	}
	
	/**
	 * Searches for an operator that matches the given operator string
	 *
	 * @param operator The operator string to match on
	 * @return The found operator, or null if none could be found
	 */
	public static SQLConjunctiveOperator fromString(String operator){
		// Look for an operator that matches the given operator string
		for(SQLConjunctiveOperator op: values()){
			if(StringUtil.equalsIgnoreCase(op.toString(), operator)){
				return op;
			}
		}
		
		// Return null if we couldn't find a matching operator
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		return operator;
	}
}
