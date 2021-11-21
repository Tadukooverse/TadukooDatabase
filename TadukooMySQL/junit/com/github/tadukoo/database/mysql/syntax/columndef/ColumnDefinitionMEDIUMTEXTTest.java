package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionMEDIUMTEXTTest implements ColumnDefinitionConstants{
	private ColumnDefinition mediumtextDef;
	
	@BeforeEach
	public void setup(){
		mediumtextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumtext()
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetMediumtext(){
		assertEquals(SQLDataType.MEDIUMTEXT, mediumtextDef.getDataType());
	}
	
	@Test
	public void testMediumtextNotNull(){
		mediumtextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumtext()
				.notNull()
				.build();
		assertTrue(mediumtextDef.isNotNull());
	}
	
	@Test
	public void testMediumtextPrimaryKey(){
		mediumtextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumtext()
				.primaryKey()
				.build();
		assertTrue(mediumtextDef.isPrimaryKey());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			mediumtextDef = ColumnDefinition.builder()
					.columnName(null)
					.mediumtext()
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
	public void testToStringMEDIUMTEXT(){
		assertEquals(columnName + " " + SQLDataType.MEDIUMTEXT, mediumtextDef.toString());
	}
	
	@Test
	public void testToStringMEDIUMTEXTNotNull(){
		mediumtextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumtext()
				.notNull()
				.build();
		assertEquals(columnName + " " +SQLDataType.MEDIUMTEXT + " NOT NULL", mediumtextDef.toString());
	}
	
	@Test
	public void testToStringMEDIUMTEXTPrimaryKey(){
		mediumtextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumtext()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.MEDIUMTEXT + " PRIMARY KEY", mediumtextDef.toString());
	}
	
	@Test
	public void testToStringMEDIUMTEXTAll(){
		mediumtextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumtext()
				.notNull()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.MEDIUMTEXT + " NOT NULL PRIMARY KEY", mediumtextDef.toString());
	}
}
