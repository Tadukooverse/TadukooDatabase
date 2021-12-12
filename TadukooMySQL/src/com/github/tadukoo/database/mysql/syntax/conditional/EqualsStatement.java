package com.github.tadukoo.database.mysql.syntax.conditional;

import com.github.tadukoo.database.mysql.syntax.SQLSyntaxUtil;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;

/**
 * EqualsStatement represents a simple MySQL equals statement
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class EqualsStatement{
	
	/** The {@link ColumnRef column} of the statement */
	private final ColumnRef column;
	/** The value of the statement */
	private final Object value;
	
	/**
	 * Constructs a new EqualsStatement using the given parameters
	 *
	 * @param column The {@link ColumnRef column} of the statement
	 * @param value The value of the statement
	 */
	public EqualsStatement(ColumnRef column, Object value){
		this.column = column;
		this.value = value;
	}
	
	/**
	 * @return The {@link ColumnRef column} of the statement
	 */
	public ColumnRef getColumn(){
		return column;
	}
	
	/**
	 * @return The value of the statement
	 */
	public Object getValue(){
		return value;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		return column.toString() + " " + SQLOperator.EQUAL + " " + SQLSyntaxUtil.convertValueToString(value);
	}
}
