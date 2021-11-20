package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionTINYINTTest implements ColumnDefinitionConstants{
	private ColumnDefinition tinyintDef;
	
	@BeforeEach
	public void setup(){
		tinyintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyint()
				.size(size)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetTinyint(){
		assertEquals(SQLDataType.TINYINT, tinyintDef.getDataType());
	}
	
	@Test
	public void testTinyintDefaultSize(){
		tinyintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyint()
				.defaultSize()
				.build();
		assertNull(tinyintDef.getSize());
	}
	
	@Test
	public void testTinyintUnsigned(){
		tinyintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyint()
				.defaultSize()
				.unsigned()
				.build();
		assertTrue(tinyintDef.isUnsigned());
	}
	
	@Test
	public void testTinyintAutoIncrement(){
		tinyintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyint()
				.size(size)
				.autoIncrement()
				.build();
		assertTrue(tinyintDef.isAutoIncremented());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			tinyintDef = ColumnDefinition.builder()
					.columnName(null)
					.tinyint()
					.size(size)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testTINYINTLowSize(){
		try{
			tinyintDef = ColumnDefinition.builder()
					.columnName(columnName)
					.tinyint()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.TINYINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTINYINTHighSize(){
		try{
			tinyintDef = ColumnDefinition.builder()
					.columnName(columnName)
					.tinyint()
					.size(256)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.TINYINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTINYINTAllErrors(){
		try{
			tinyintDef = ColumnDefinition.builder()
					.columnName(null)
					.tinyint()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.TINYINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringTINYINT(){
		assertEquals(columnName + " " + SQLDataType.TINYINT + "(" + size + ")", tinyintDef.toString());
	}
	
	@Test
	public void testToStringTINYINTDefaultSize(){
		tinyintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyint()
				.defaultSize()
				.build();
		assertEquals(columnName + " " + SQLDataType.TINYINT, tinyintDef.toString());
	}
	
	@Test
	public void testToStringTINYINTUnsigned(){
		tinyintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyint()
				.defaultSize()
				.unsigned()
				.build();
		assertEquals(columnName + " " + SQLDataType.TINYINT + " UNSIGNED", tinyintDef.toString());
	}
	
	@Test
	public void testToStringTINYINTAutoIncrement(){
		tinyintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyint()
				.defaultSize()
				.autoIncrement()
				.build();
		assertEquals(columnName + " " + SQLDataType.TINYINT + " AUTO_INCREMENT", tinyintDef.toString());
	}
	
	@Test
	public void testToStringTINYINTAll(){
		tinyintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyint()
				.size(size)
				.unsigned()
				.autoIncrement()
				.build();
		assertEquals(columnName + " " + SQLDataType.TINYINT + "(" + size + ") UNSIGNED AUTO_INCREMENT",
				tinyintDef.toString());
	}
}
