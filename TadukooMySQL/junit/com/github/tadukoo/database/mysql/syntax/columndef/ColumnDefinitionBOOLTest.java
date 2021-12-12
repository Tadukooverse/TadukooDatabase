package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionBOOLTest implements ColumnDefinitionConstants{
	private ColumnDefinition boolDef;
	
	@BeforeEach
	public void setup(){
		boolDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bool()
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetBool(){
		assertEquals(SQLDataType.BOOL, boolDef.getDataType());
	}
	
	@Test
	public void testBoolNotNull(){
		boolDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bool()
				.notNull()
				.build();
		assertTrue(boolDef.isNotNull());
	}
	
	@Test
	public void testBoolPrimaryKey(){
		boolDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bool()
				.primaryKey()
				.build();
		assertTrue(boolDef.isPrimaryKey());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			boolDef = ColumnDefinition.builder()
					.columnName(null)
					.bool()
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
	public void testToStringBOOL(){
		assertEquals(columnName + " " + SQLDataType.BOOL, boolDef.toString());
	}
	
	@Test
	public void testToStringBOOLNotNull(){
		boolDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bool()
				.notNull()
				.build();
		assertEquals(columnName + " " + SQLDataType.BOOL + " NOT NULL", boolDef.toString());
	}
	
	@Test
	public void testToStringBOOLPrimaryKey(){
		boolDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bool()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.BOOL + " PRIMARY KEY", boolDef.toString());
	}
	
	@Test
	public void testToStringBOOLAll(){
		boolDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bool()
				.notNull()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.BOOL + " NOT NULL PRIMARY KEY", boolDef.toString());
	}
}
