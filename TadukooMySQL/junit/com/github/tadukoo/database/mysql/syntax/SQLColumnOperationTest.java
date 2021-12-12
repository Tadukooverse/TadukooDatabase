package com.github.tadukoo.database.mysql.syntax;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SQLColumnOperationTest{
	
	@Test
	public void testToStringADD(){
		assertEquals("ADD", SQLColumnOperation.ADD.toString());
	}
	
	@Test
	public void testToStringMODIFY(){
		assertEquals("MODIFY COLUMN", SQLColumnOperation.MODIFY.toString());
	}
	
	@Test
	public void testToStringDROP(){
		assertEquals("DROP COLUMN", SQLColumnOperation.DROP.toString());
	}
	
	@Test
	public void testFromTypeADD(){
		assertEquals(SQLColumnOperation.ADD, SQLColumnOperation.fromType("ADD"));
	}
	
	@Test
	public void testFromTypeMODIFY(){
		assertEquals(SQLColumnOperation.MODIFY, SQLColumnOperation.fromType("MODIFY COLUMN"));
	}
	
	@Test
	public void testFromTypeDROP(){
		assertEquals(SQLColumnOperation.DROP, SQLColumnOperation.fromType("DROP COLUMN"));
	}
	
	@Test
	public void testFromTypeFail(){
		assertNull(SQLColumnOperation.fromType("garbage_string"));
	}
}
