package com.github.tadukoo.database.mysql.syntax.conditional;

import com.github.tadukoo.database.mysql.syntax.conditional.SQLOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SQLOperatorTest{
	
	@Test
	public void testToStringEQUAL(){
		assertEquals("=", SQLOperator.EQUAL.toString());
	}
	
	@Test
	public void testToStringNOT_EQUAL(){
		assertEquals("!=", SQLOperator.NOT_EQUAL.toString());
	}
	
	@Test
	public void testToStringGREATER_THAN(){
		assertEquals(">", SQLOperator.GREATER_THAN.toString());
	}
	
	@Test
	public void testToStringLESS_THAN(){
		assertEquals("<", SQLOperator.LESS_THAN.toString());
	}
	
	@Test
	public void testToStringGREATER_THAN_OR_EQUAL(){
		assertEquals(">=", SQLOperator.GREATER_THAN_OR_EQUAL.toString());
	}
	
	@Test
	public void testToStringLESS_THAN_OR_EQUAL(){
		assertEquals("<=", SQLOperator.LESS_THAN_OR_EQUAL.toString());
	}
	
	@Test
	public void testToStringBETWEEN(){
		assertEquals("BETWEEN", SQLOperator.BETWEEN.toString());
	}
	
	@Test
	public void testToStringLIKE(){
		assertEquals("LIKE", SQLOperator.LIKE.toString());
	}
	
	@Test
	public void testToStringIN(){
		assertEquals("IN", SQLOperator.IN.toString());
	}
	
	@Test
	public void testFromStringEQUAL(){
		assertEquals(SQLOperator.EQUAL, SQLOperator.fromString("="));
	}
	
	@Test
	public void testFromStringNOT_EQUAL(){
		assertEquals(SQLOperator.NOT_EQUAL, SQLOperator.fromString("!="));
	}
	
	@Test
	public void testFromStringGREATER_THAN(){
		assertEquals(SQLOperator.GREATER_THAN, SQLOperator.fromString(">"));
	}
	
	@Test
	public void testFromStringLESS_THAN(){
		assertEquals(SQLOperator.LESS_THAN, SQLOperator.fromString("<"));
	}
	
	@Test
	public void testFromStringGREATER_THAN_OR_EQUAL(){
		assertEquals(SQLOperator.GREATER_THAN_OR_EQUAL, SQLOperator.fromString(">="));
	}
	
	@Test
	public void testFromStringLESS_THAN_OR_EQUAL(){
		assertEquals(SQLOperator.LESS_THAN_OR_EQUAL, SQLOperator.fromString("<="));
	}
	
	@Test
	public void testFromStringBETWEEN(){
		assertEquals(SQLOperator.BETWEEN, SQLOperator.fromString("BETWEEN"));
	}
	
	@Test
	public void testFromStringLIKE(){
		assertEquals(SQLOperator.LIKE, SQLOperator.fromString("LIKE"));
	}
	
	@Test
	public void testFromStringIN(){
		assertEquals(SQLOperator.IN, SQLOperator.fromString("IN"));
	}
	
	@Test
	public void testFromStringFail(){
		assertNull(SQLOperator.fromString("garbage_string"));
	}
}
