package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionFLOATTest implements ColumnDefinitionConstants{
	private ColumnDefinition floatDef;
	
	@BeforeEach
	public void setup(){
		floatDef = ColumnDefinition.builder()
				.columnName(columnName)
				.floatType()
				.sizeAndDigits(size, digits)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetFloat(){
		assertEquals(SQLDataType.FLOAT, floatDef.getDataType());
	}
	
	@Test
	public void testFloatDefaultSizeAndDigits(){
		floatDef = ColumnDefinition.builder()
				.columnName(columnName)
				.floatType()
				.defaultSizeAndDigits()
				.build();
		assertNull(floatDef.getSize());
		assertNull(floatDef.getDigits());
	}
	
	@Test
	public void testFloatUnsigned(){
		floatDef = ColumnDefinition.builder()
				.columnName(columnName)
				.floatType()
				.defaultSizeAndDigits()
				.unsigned()
				.build();
		assertTrue(floatDef.isUnsigned());
	}
	
	@Test
	public void testFloatAutoIncrement(){
		floatDef = ColumnDefinition.builder()
				.columnName(columnName)
				.floatType()
				.sizeAndDigits(size, digits)
				.autoIncrement()
				.build();
		assertTrue(floatDef.isAutoIncremented());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			floatDef = ColumnDefinition.builder()
					.columnName(null)
					.floatType()
					.sizeAndDigits(size, digits)
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
	public void testToStringFLOAT(){
		assertEquals(columnName + " " + SQLDataType.FLOAT + "(" + size + ", " + digits + ")", floatDef.toString());
	}
	
	@Test
	public void testToStringFLOATDefaultSizeAndDigits(){
		floatDef = ColumnDefinition.builder()
				.columnName(columnName)
				.floatType()
				.defaultSizeAndDigits()
				.build();
		assertEquals(columnName + " " + SQLDataType.FLOAT, floatDef.toString());
	}
	
	@Test
	public void testToStringFLOATUnsigned(){
		floatDef = ColumnDefinition.builder()
				.columnName(columnName)
				.floatType()
				.defaultSizeAndDigits()
				.unsigned()
				.build();
		assertEquals(columnName + " " + SQLDataType.FLOAT + " UNSIGNED", floatDef.toString());
	}
	
	@Test
	public void testToStringFLOATAutoIncrement(){
		floatDef = ColumnDefinition.builder()
				.columnName(columnName)
				.floatType()
				.defaultSizeAndDigits()
				.autoIncrement()
				.build();
		assertEquals(columnName + " " + SQLDataType.FLOAT + " AUTO_INCREMENT", floatDef.toString());
	}
	
	@Test
	public void testToStringFLOATAll(){
		floatDef = ColumnDefinition.builder()
				.columnName(columnName)
				.floatType()
				.sizeAndDigits(size, digits)
				.unsigned()
				.autoIncrement()
				.build();
		assertEquals(columnName + " " + SQLDataType.FLOAT + "(" + size + ", " + digits + ") UNSIGNED AUTO_INCREMENT",
				floatDef.toString());
	}
}
