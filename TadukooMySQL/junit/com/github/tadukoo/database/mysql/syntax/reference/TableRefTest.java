package com.github.tadukoo.database.mysql.syntax.reference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class TableRefTest{
	private TableRef tableRef;
	private final String tableName = "Test";
	
	@BeforeEach
	public void setup(){
		tableRef = TableRef.builder().tableName(tableName).build();
	}
	
	@Test
	public void testBuilderSetTableName(){
		assertEquals(tableName, tableRef.getTableName());
	}
	
	@Test
	public void testBuilderDefaultAlias(){
		assertNull(tableRef.getAlias());
	}
	
	@Test
	public void testBuilderSetAlias(){
		tableRef = TableRef.builder().tableName(tableName).alias("Derp").build();
		assertEquals("Derp", tableRef.getAlias());
	}
	
	@Test
	public void testBuilderMissingTableName(){
		try{
			tableRef = TableRef.builder().tableName(null).build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("The following errors occurred trying to build a TableRef: \ntableName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals(tableName, tableRef.toString());
	}
	
	@Test
	public void testToStringWithAlias(){
		tableRef = TableRef.builder().tableName(tableName).alias("Derp").build();
		assertEquals(tableName + " AS Derp", tableRef.toString());
	}
}
