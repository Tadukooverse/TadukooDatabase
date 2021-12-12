package com.github.tadukoo.database.mysql.syntax.conditional;

import com.github.tadukoo.database.mysql.syntax.conditional.SQLConjunctiveOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SQLConjunctiveOperatorTest{
	
	@Test
	public void testToStringAND(){
		assertEquals("AND", SQLConjunctiveOperator.AND.toString());
	}
	
	@Test
	public void testToStringOR(){
		assertEquals("OR", SQLConjunctiveOperator.OR.toString());
	}
	
	@Test
	public void testFromStringAND(){
		assertEquals(SQLConjunctiveOperator.AND, SQLConjunctiveOperator.fromString("AND"));
	}
	
	@Test
	public void testFromStringOR(){
		assertEquals(SQLConjunctiveOperator.OR, SQLConjunctiveOperator.fromString("OR"));
	}
	
	@Test
	public void testFromStringFail(){
		assertNull(SQLConjunctiveOperator.fromString("garbage_string"));
	}
}
