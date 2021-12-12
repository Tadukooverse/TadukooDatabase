package com.github.tadukoo.database.mysql.syntax.conditional;

import com.github.tadukoo.database.mysql.syntax.SQLSyntaxUtil;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EqualsStatementTest{
	private EqualsStatement stmt;
	private ColumnRef column;
	private Object value;
	
	@BeforeEach
	public void setup(){
		column = ColumnRef.builder().columnName("Test").build();
		value = 42;
		stmt = new EqualsStatement(column, value);
	}
	
	@Test
	public void testGetColumn(){
		assertEquals(column, stmt.getColumn());
	}
	
	@Test
	public void testGetValue(){
		assertEquals(value, stmt.getValue());
	}
	
	@Test
	public void testToString(){
		assertEquals(column.toString() + " " + SQLOperator.EQUAL + " " +
				SQLSyntaxUtil.convertValueToString(value), stmt.toString());
	}
}
