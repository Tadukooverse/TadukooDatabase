package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionYEARTest implements ColumnDefinitionConstants{
	private ColumnDefinition yearDef;
	
	@BeforeEach
	public void setup(){
		yearDef = ColumnDefinition.builder()
				.columnName(columnName)
				.year()
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetYear(){
		assertEquals(SQLDataType.YEAR, yearDef.getDataType());
	}
	
	@Test
	public void testYearNotNull(){
		yearDef = ColumnDefinition.builder()
				.columnName(columnName)
				.year()
				.notNull()
				.build();
		assertTrue(yearDef.isNotNull());
	}
	
	@Test
	public void testYearPrimaryKey(){
		yearDef = ColumnDefinition.builder()
				.columnName(columnName)
				.year()
				.primaryKey()
				.build();
		assertTrue(yearDef.isPrimaryKey());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			yearDef = ColumnDefinition.builder()
					.columnName(null)
					.year()
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
	public void testToStringYEAR(){
		assertEquals(columnName + " " + SQLDataType.YEAR, yearDef.toString());
	}
	
	@Test
	public void testToStringYEARNotNull(){
		yearDef = ColumnDefinition.builder()
				.columnName(columnName)
				.year()
				.notNull()
				.build();
		assertEquals(columnName + " " + SQLDataType.YEAR + " NOT NULL", yearDef.toString());
	}
	
	@Test
	public void testToStringYEARPrimaryKey(){
		yearDef = ColumnDefinition.builder()
				.columnName(columnName)
				.year()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.YEAR + " PRIMARY KEY", yearDef.toString());
	}
	
	@Test
	public void testToStringYEARAll(){
		yearDef = ColumnDefinition.builder()
				.columnName(columnName)
				.year()
				.notNull()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.YEAR + " NOT NULL PRIMARY KEY", yearDef.toString());
	}
}
