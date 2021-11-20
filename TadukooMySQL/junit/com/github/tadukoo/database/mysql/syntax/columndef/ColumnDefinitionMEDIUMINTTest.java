package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionMEDIUMINTTest implements ColumnDefinitionConstants{
	private ColumnDefinition mediumintDef;
	
	@BeforeEach
	public void setup(){
		mediumintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumint()
				.size(size)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetMediumint(){
		assertEquals(SQLDataType.MEDIUMINT, mediumintDef.getDataType());
	}
	
	@Test
	public void testMediumintDefaultSize(){
		mediumintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumint()
				.defaultSize()
				.build();
		assertNull(mediumintDef.getSize());
	}
	
	@Test
	public void testMediumintAutoIncrement(){
		mediumintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumint()
				.size(size)
				.autoIncrement()
				.build();
		assertTrue(mediumintDef.isAutoIncremented());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			mediumintDef = ColumnDefinition.builder()
					.columnName(null)
					.mediumint()
					.size(size)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testMEDIUMINTLowSize(){
		try{
			mediumintDef = ColumnDefinition.builder()
					.columnName(columnName)
					.mediumint()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.MEDIUMINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testMEDIUMINTHighSize(){
		try{
			mediumintDef = ColumnDefinition.builder()
					.columnName(columnName)
					.mediumint()
					.size(256)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.MEDIUMINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testMEDIUMINTAllErrors(){
		try{
			mediumintDef = ColumnDefinition.builder()
					.columnName(null)
					.mediumint()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.MEDIUMINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringMEDIUMINT(){
		assertEquals(columnName + " " + SQLDataType.MEDIUMINT + "(" + size + ")", mediumintDef.toString());
	}
	
	@Test
	public void testToStringMEDIUMINTDefaultSize(){
		mediumintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumint()
				.defaultSize()
				.build();
		assertEquals(columnName + " " + SQLDataType.MEDIUMINT, mediumintDef.toString());
	}
	
	@Test
	public void testToStringMEDIUMINTAutoIncrement(){
		mediumintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumint()
				.defaultSize()
				.autoIncrement()
				.build();
		assertEquals(columnName + " " + SQLDataType.MEDIUMINT + " AUTO_INCREMENT", mediumintDef.toString());
	}
	
	@Test
	public void testToStringMEDIUMINTAll(){
		mediumintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumint()
				.size(size)
				.autoIncrement()
				.build();
		assertEquals(columnName + " " + SQLDataType.MEDIUMINT + "(" + size + ") AUTO_INCREMENT",
				mediumintDef.toString());
	}
}
