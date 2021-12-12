package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionBINARYTest implements ColumnDefinitionConstants{
	private ColumnDefinition binaryDef;
	
	@BeforeEach
	public void setup(){
		binaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.binary()
				.length(size)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetBinary(){
		assertEquals(SQLDataType.BINARY, binaryDef.getDataType());
	}
	
	@Test
	public void testBinaryDefaultSize(){
		binaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.binary()
				.defaultLength()
				.build();
		assertNull(binaryDef.getSize());
	}
	
	@Test
	public void testBinaryNotNull(){
		binaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.binary()
				.defaultLength()
				.notNull()
				.build();
		assertTrue(binaryDef.isNotNull());
	}
	
	@Test
	public void testBinaryPrimaryKey(){
		binaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.binary()
				.length(size)
				.primaryKey()
				.build();
		assertTrue(binaryDef.isPrimaryKey());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testBINARYMissingColumnName(){
		try{
			binaryDef = ColumnDefinition.builder()
					.columnName(null)
					.binary()
					.length(size)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBINARYLowSize(){
		try{
			binaryDef = ColumnDefinition.builder()
					.columnName(columnName)
					.binary()
					.length(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.BINARY + " size must be between 0 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBINARYHighSize(){
		try{
			binaryDef = ColumnDefinition.builder()
					.columnName(columnName)
					.binary()
					.length(256)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.BINARY + " size must be between 0 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBINARYAllErrors(){
		try{
			binaryDef = ColumnDefinition.builder()
					.columnName(null)
					.binary()
					.length(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.BINARY + " size must be between 0 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringBINARY(){
		assertEquals(columnName + " " + SQLDataType.BINARY + "(" + size + ")", binaryDef.toString());
	}
	
	@Test
	public void testToStringBINARYDefaultSize(){
		binaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.binary()
				.defaultLength()
				.build();
		assertEquals(columnName + " " + SQLDataType.BINARY, binaryDef.toString());
	}
	
	@Test
	public void testToStringBINARYNotNull(){
		binaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.binary()
				.defaultLength()
				.notNull()
				.build();
		assertEquals(columnName + " " + SQLDataType.BINARY + " NOT NULL", binaryDef.toString());
	}
	
	@Test
	public void testToStringBINARYPrimaryKey(){
		binaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.binary()
				.defaultLength()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.BINARY + " PRIMARY KEY", binaryDef.toString());
	}
	
	@Test
	public void testToStringBINARYAll(){
		binaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.binary()
				.length(size)
				.notNull()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.BINARY + "(" + size + ") NOT NULL PRIMARY KEY",
				binaryDef.toString());
	}
}
