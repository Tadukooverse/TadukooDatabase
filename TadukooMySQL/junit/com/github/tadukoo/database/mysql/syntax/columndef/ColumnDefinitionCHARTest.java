package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionCHARTest implements ColumnDefinitionConstants{
	private ColumnDefinition charDef;
	
	@BeforeEach
	public void setup(){
		charDef = ColumnDefinition.builder()
				.columnName(columnName)
				.character()
				.length(size)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetChar(){
		assertEquals(SQLDataType.CHAR, charDef.getDataType());
	}
	
	@Test
	public void testCharDefaultSize(){
		charDef = ColumnDefinition.builder()
				.columnName(columnName)
				.character()
				.defaultLength()
				.build();
		assertNull(charDef.getSize());
	}
	
	@Test
	public void testCharNotNull(){
		charDef = ColumnDefinition.builder()
				.columnName(columnName)
				.character()
				.defaultLength()
				.notNull()
				.build();
		assertTrue(charDef.isNotNull());
	}
	
	@Test
	public void testCharPrimaryKey(){
		charDef = ColumnDefinition.builder()
				.columnName(columnName)
				.character()
				.defaultLength()
				.primaryKey()
				.build();
		assertTrue(charDef.isPrimaryKey());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testCHARMissingColumnName(){
		try{
			charDef = ColumnDefinition.builder()
					.columnName(null)
					.character()
					.defaultLength()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testCHARLowSize(){
		try{
			charDef = ColumnDefinition.builder()
					.columnName(columnName)
					.character()
					.length(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.CHAR + " size must be between 0 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testCHARHighSize(){
		try{
			charDef = ColumnDefinition.builder()
					.columnName(columnName)
					.character()
					.length(256)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.CHAR + " size must be between 0 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testCHARAllErrors(){
		try{
			charDef = ColumnDefinition.builder()
					.columnName(null)
					.character()
					.length(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.CHAR + " size must be between 0 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringCHAR(){
		assertEquals(columnName + " " + SQLDataType.CHAR + "(" + size + ")", charDef.toString());
	}
	
	@Test
	public void testToStringCHARDefaultSize(){
		charDef = ColumnDefinition.builder()
				.columnName(columnName)
				.character()
				.defaultLength()
				.build();
		assertEquals(columnName + " " + SQLDataType.CHAR, charDef.toString());
	}
	
	@Test
	public void testToStringCHARNotNull(){
		charDef = ColumnDefinition.builder()
				.columnName(columnName)
				.character()
				.defaultLength()
				.notNull()
				.build();
		assertEquals(columnName + " " + SQLDataType.CHAR + " NOT NULL", charDef.toString());
	}
	
	@Test
	public void testToStringCHARPrimaryKey(){
		charDef = ColumnDefinition.builder()
				.columnName(columnName)
				.character()
				.defaultLength()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.CHAR + " PRIMARY KEY", charDef.toString());
	}
	
	@Test
	public void testToStringCHARAll(){
		charDef = ColumnDefinition.builder()
				.columnName(columnName)
				.character()
				.length(size)
				.notNull()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.CHAR + "(" + size + ") NOT NULL PRIMARY KEY",
				charDef.toString());
	}
}
