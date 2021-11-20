package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionINTEGERTest implements ColumnDefinitionConstants{
	private ColumnDefinition integerDef;
	
	@BeforeEach
	public void setup(){
		integerDef = ColumnDefinition.builder()
				.columnName(columnName)
				.integer()
				.size(size)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetInteger(){
		assertEquals(SQLDataType.INTEGER, integerDef.getDataType());
	}
	
	@Test
	public void testIntegerDefaultSize(){
		integerDef = ColumnDefinition.builder()
				.columnName(columnName)
				.integer()
				.defaultSize()
				.build();
		assertNull(integerDef.getSize());
	}
	
	@Test
	public void testIntegerAutoIncrement(){
		integerDef = ColumnDefinition.builder()
				.columnName(columnName)
				.integer()
				.size(size)
				.autoIncrement()
				.build();
		assertTrue(integerDef.isAutoIncremented());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			integerDef = ColumnDefinition.builder()
					.columnName(null)
					.integer()
					.size(size)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testINTEGERLowSize(){
		try{
			integerDef = ColumnDefinition.builder()
					.columnName(columnName)
					.integer()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.INTEGER + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testINTEGERHighSize(){
		try{
			integerDef = ColumnDefinition.builder()
					.columnName(columnName)
					.integer()
					.size(256)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.INTEGER + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testINTEGERAllErrors(){
		try{
			integerDef = ColumnDefinition.builder()
					.columnName(null)
					.integer()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.INTEGER + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringINTEGER(){
		assertEquals(columnName + " " + SQLDataType.INTEGER + "(" + size + ")", integerDef.toString());
	}
	
	@Test
	public void testToStringINTEGERDefaultSize(){
		integerDef = ColumnDefinition.builder()
				.columnName(columnName)
				.integer()
				.defaultSize()
				.build();
		assertEquals(columnName + " " + SQLDataType.INTEGER, integerDef.toString());
	}
	
	@Test
	public void testToStringINTEGERAutoIncrement(){
		integerDef = ColumnDefinition.builder()
				.columnName(columnName)
				.integer()
				.defaultSize()
				.autoIncrement()
				.build();
		assertEquals(columnName + " " + SQLDataType.INTEGER + " AUTO_INCREMENT", integerDef.toString());
	}
	
	@Test
	public void testToStringINTEGERAll(){
		integerDef = ColumnDefinition.builder()
				.columnName(columnName)
				.integer()
				.size(size)
				.autoIncrement()
				.build();
		assertEquals(columnName + " " + SQLDataType.INTEGER + "(" + size + ") AUTO_INCREMENT",
				integerDef.toString());
	}
}
