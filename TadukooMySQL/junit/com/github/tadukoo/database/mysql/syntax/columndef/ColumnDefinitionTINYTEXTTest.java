package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionTINYTEXTTest implements ColumnDefinitionConstants{
	private ColumnDefinition tinytextDef;
	
	@BeforeEach
	public void setup(){
		tinytextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinytext()
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetTinytext(){
		assertEquals(SQLDataType.TINYTEXT, tinytextDef.getDataType());
	}
	
	@Test
	public void testTinytextNotNull(){
		tinytextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinytext()
				.notNull()
				.build();
		assertTrue(tinytextDef.isNotNull());
	}
	
	@Test
	public void testTinytextPrimaryKey(){
		tinytextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinytext()
				.primaryKey()
				.build();
		assertTrue(tinytextDef.isPrimaryKey());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testTINYTEXTMissingColumnName(){
		try{
			tinytextDef = ColumnDefinition.builder()
					.columnName(null)
					.tinytext()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringTINYTEXT(){
		assertEquals(columnName + " " + SQLDataType.TINYTEXT, tinytextDef.toString());
	}
	
	@Test
	public void testToStringTINYTEXTNotNull(){
		tinytextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinytext()
				.notNull()
				.build();
		assertEquals(columnName + " " + SQLDataType.TINYTEXT + " NOT NULL", tinytextDef.toString());
	}
	
	@Test
	public void testToStringTINYTEXTPrimaryKey(){
		tinytextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinytext()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.TINYTEXT + " PRIMARY KEY", tinytextDef.toString());
	}
	
	@Test
	public void testToStringTINYTEXTAll(){
		tinytextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinytext()
				.notNull()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.TINYTEXT + " NOT NULL PRIMARY KEY", tinytextDef.toString());
	}
}
