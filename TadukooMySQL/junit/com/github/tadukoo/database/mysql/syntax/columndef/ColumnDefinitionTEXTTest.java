package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionTEXTTest implements ColumnDefinitionConstants{
	private ColumnDefinition textDef;
	
	@BeforeEach
	public void setup(){
		textDef = ColumnDefinition.builder()
				.columnName(columnName)
				.text()
				.length(size)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetText(){
		assertEquals(SQLDataType.TEXT, textDef.getDataType());
	}
	
	@Test
	public void testTextDefaultSize(){
		textDef = ColumnDefinition.builder()
				.columnName(columnName)
				.text()
				.defaultLength()
				.build();
		assertNull(textDef.getSize());
	}
	
	@Test
	public void testTextLongSize(){
		long sizeL = 100L;
		textDef = ColumnDefinition.builder()
				.columnName(columnName)
				.text()
				.length(sizeL)
				.build();
		assertEquals(sizeL, textDef.getSize());
	}
	
	@Test
	public void testTextNotNull(){
		textDef = ColumnDefinition.builder()
				.columnName(columnName)
				.text()
				.length(size)
				.notNull()
				.build();
		assertTrue(textDef.isNotNull());
	}
	
	@Test
	public void testTextPrimaryKey(){
		textDef = ColumnDefinition.builder()
				.columnName(columnName)
				.text()
				.length(size)
				.primaryKey()
				.build();
		assertTrue(textDef.isPrimaryKey());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testTEXTMissingColumnName(){
		try{
			textDef = ColumnDefinition.builder()
					.columnName(null)
					.text()
					.length(size)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testTEXTLowSize(){
		try{
			textDef = ColumnDefinition.builder()
					.columnName(columnName)
					.text()
					.length(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.TEXT + " size must be between 0 and 4,294,967,295 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTEXTHighSize(){
		try{
			textDef = ColumnDefinition.builder()
					.columnName(columnName)
					.text()
					.length(4295967296L)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.TEXT + " size must be between 0 and 4,294,967,295 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTEXTAllErrors(){
		try{
			textDef = ColumnDefinition.builder()
					.columnName(null)
					.text()
					.length(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.TEXT + " size must be between 0 and 4,294,967,295 (or defaulted)!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringTEXT(){
		assertEquals(columnName + " " + SQLDataType.TEXT + "(" + size + ")", textDef.toString());
	}
	
	@Test
	public void testToStringTEXTDefaultSize(){
		textDef = ColumnDefinition.builder()
				.columnName(columnName)
				.text()
				.defaultLength()
				.build();
		assertEquals(columnName + " " + SQLDataType.TEXT, textDef.toString());
	}
	
	@Test
	public void testToStringTEXTNotNull(){
		textDef = ColumnDefinition.builder()
				.columnName(columnName)
				.text()
				.defaultLength()
				.notNull()
				.build();
		assertEquals(columnName + " " + SQLDataType.TEXT + " NOT NULL", textDef.toString());
	}
	
	@Test
	public void testToStringTEXTPrimaryKey(){
		textDef = ColumnDefinition.builder()
				.columnName(columnName)
				.text()
				.defaultLength()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.TEXT + " PRIMARY KEY", textDef.toString());
	}
	
	@Test
	public void testToStringTEXTAll(){
		textDef = ColumnDefinition.builder()
				.columnName(columnName)
				.text()
				.length(size)
				.notNull()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.TEXT + "(" + size + ") NOT NULL PRIMARY KEY",
				textDef.toString());
	}
}
