package com.github.tadukoo.database.mysql.syntax;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SQLTypeTest{
	
	@Test
	public void testToStringTABLE(){
		assertEquals("TABLE", SQLType.TABLE.toString());
	}
	
	@Test
	public void testToStringDATABASE(){
		assertEquals("DATABASE", SQLType.DATABASE.toString());
	}
	
	@Test
	public void testFromTypeTABLE(){
		assertEquals(SQLType.TABLE, SQLType.fromType("TABLE"));
	}
	
	@Test
	public void testFromTypeDATABASE(){
		assertEquals(SQLType.DATABASE, SQLType.fromType("DATABASE"));
	}
	
	@Test
	public void testFromTypeFail(){
		assertNull(SQLType.fromType("garbage_string"));
	}
}
