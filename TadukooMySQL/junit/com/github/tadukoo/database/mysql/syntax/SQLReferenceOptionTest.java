package com.github.tadukoo.database.mysql.syntax;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SQLReferenceOptionTest{
	
	@Test
	public void testToStringRESTRICT(){
		assertEquals("RESTRICT", SQLReferenceOption.RESTRICT.toString());
	}
	
	@Test
	public void testFromOptionRESTRICT(){
		assertEquals(SQLReferenceOption.RESTRICT, SQLReferenceOption.fromOption("RESTRICT"));
	}
	
	@Test
	public void testToStringCASCADE(){
		assertEquals("CASCADE", SQLReferenceOption.CASCADE.toString());
	}
	
	@Test
	public void testFromOptionCASCADE(){
		assertEquals(SQLReferenceOption.CASCADE, SQLReferenceOption.fromOption("CASCADE"));
	}
	
	@Test
	public void testToStringSET_NULL(){
		assertEquals("SET NULL", SQLReferenceOption.SET_NULL.toString());
	}
	
	@Test
	public void testFromOptionSET_NULL(){
		assertEquals(SQLReferenceOption.SET_NULL, SQLReferenceOption.fromOption("SET NULL"));
	}
	
	@Test
	public void testToStringNO_ACTION(){
		assertEquals("NO ACTION", SQLReferenceOption.NO_ACTION.toString());
	}
	
	@Test
	public void testFromOptionNO_ACTION(){
		assertEquals(SQLReferenceOption.NO_ACTION, SQLReferenceOption.fromOption("NO ACTION"));
	}
	
	@Test
	public void testToStringSET_DEFAULT(){
		assertEquals("SET DEFAULT", SQLReferenceOption.SET_DEFAULT.toString());
	}
	
	@Test
	public void testFromOptionSET_DEFAULT(){
		assertEquals(SQLReferenceOption.SET_DEFAULT, SQLReferenceOption.fromOption("SET DEFAULT"));
	}
	
	@Test
	public void testFromOptionFail(){
		assertNull(SQLReferenceOption.fromOption("garbage_string"));
	}
}
