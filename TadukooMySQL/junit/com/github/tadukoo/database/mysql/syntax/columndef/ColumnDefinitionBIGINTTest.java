package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionBIGINTTest implements ColumnDefinitionConstants{
	private ColumnDefinition bigintDef;
	
	@BeforeEach
	public void setup(){
		bigintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bigint()
				.size(size)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetBigint(){
		assertEquals(SQLDataType.BIGINT, bigintDef.getDataType());
	}
	
	@Test
	public void testBigintDefaultSize(){
		bigintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bigint()
				.defaultSize()
				.build();
		assertNull(bigintDef.getSize());
	}
	
	@Test
	public void testBigintUnsigned(){
		bigintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bigint()
				.defaultSize()
				.unsigned()
				.build();
		assertTrue(bigintDef.isUnsigned());
	}
	
	@Test
	public void testBigintAutoIncrement(){
		bigintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bigint()
				.size(size)
				.autoIncrement()
				.build();
		assertTrue(bigintDef.isAutoIncremented());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			bigintDef = ColumnDefinition.builder()
					.columnName(null)
					.bigint()
					.size(size)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testBIGINTLowSize(){
		try{
			bigintDef = ColumnDefinition.builder()
					.columnName(columnName)
					.bigint()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.BIGINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBIGINTHighSize(){
		try{
			bigintDef = ColumnDefinition.builder()
					.columnName(columnName)
					.bigint()
					.size(256)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.BIGINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBIGINTAllErrors(){
		try{
			bigintDef = ColumnDefinition.builder()
					.columnName(null)
					.bigint()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.BIGINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringBIGINT(){
		assertEquals(columnName + " " + SQLDataType.BIGINT + "(" + size + ")", bigintDef.toString());
	}
	
	@Test
	public void testToStringBIGINTDefaultSize(){
		bigintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bigint()
				.defaultSize()
				.build();
		assertEquals(columnName + " " + SQLDataType.BIGINT, bigintDef.toString());
	}
	
	@Test
	public void testToStringBIGINTUnsigned(){
		bigintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bigint()
				.defaultSize()
				.unsigned()
				.build();
		assertEquals(columnName + " " + SQLDataType.BIGINT + " UNSIGNED", bigintDef.toString());
	}
	
	@Test
	public void testToStringBIGINTAutoIncrement(){
		bigintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bigint()
				.defaultSize()
				.autoIncrement()
				.build();
		assertEquals(columnName + " " + SQLDataType.BIGINT + " AUTO_INCREMENT", bigintDef.toString());
	}
	
	@Test
	public void testToStringBIGINTAll(){
		bigintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bigint()
				.size(size)
				.unsigned()
				.autoIncrement()
				.build();
		assertEquals(columnName + " " + SQLDataType.BIGINT + "(" + size + ") UNSIGNED AUTO_INCREMENT",
				bigintDef.toString());
	}
}
