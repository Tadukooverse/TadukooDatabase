package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionDOUBLETest implements ColumnDefinitionConstants{
	private ColumnDefinition doubleDef;
	
	@BeforeEach
	public void setup(){
		doubleDef = ColumnDefinition.builder()
				.columnName(columnName)
				.doubleType()
				.sizeAndDigits(size, digits)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetDouble(){
		assertEquals(SQLDataType.DOUBLE, doubleDef.getDataType());
	}
	
	@Test
	public void testDoubleDefaultSizeAndDigits(){
		doubleDef = ColumnDefinition.builder()
				.columnName(columnName)
				.doubleType()
				.defaultSizeAndDigits()
				.build();
		assertNull(doubleDef.getSize());
		assertNull(doubleDef.getDigits());
	}
	
	@Test
	public void testDoubleNotNull(){
		doubleDef = ColumnDefinition.builder()
				.columnName(columnName)
				.doubleType()
				.defaultSizeAndDigits()
				.notNull()
				.build();
		assertTrue(doubleDef.isNotNull());
	}
	
	@Test
	public void testDoubleUnsigned(){
		doubleDef = ColumnDefinition.builder()
				.columnName(columnName)
				.doubleType()
				.defaultSizeAndDigits()
				.unsigned()
				.build();
		assertTrue(doubleDef.isUnsigned());
	}
	
	@Test
	public void testDoubleAutoIncrement(){
		doubleDef = ColumnDefinition.builder()
				.columnName(columnName)
				.doubleType()
				.sizeAndDigits(size, digits)
				.autoIncrement()
				.build();
		assertTrue(doubleDef.isAutoIncremented());
	}
	
	@Test
	public void testDoublePrimaryKey(){
		doubleDef = ColumnDefinition.builder()
				.columnName(columnName)
				.doubleType()
				.defaultSizeAndDigits()
				.primaryKey()
				.build();
		assertTrue(doubleDef.isPrimaryKey());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			doubleDef = ColumnDefinition.builder()
					.columnName(null)
					.doubleType()
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
	public void testToStringDOUBLE(){
		assertEquals(columnName + " " + SQLDataType.DOUBLE + "(" + size + ", " + digits + ")", doubleDef.toString());
	}
	
	@Test
	public void testToStringDOUBLEDefaultSizeAndDigits(){
		doubleDef = ColumnDefinition.builder()
				.columnName(columnName)
				.doubleType()
				.defaultSizeAndDigits()
				.build();
		assertEquals(columnName + " " + SQLDataType.DOUBLE, doubleDef.toString());
	}
	
	@Test
	public void testToStringDOUBLENotNull(){
		doubleDef = ColumnDefinition.builder()
				.columnName(columnName)
				.doubleType()
				.defaultSizeAndDigits()
				.notNull()
				.build();
		assertEquals(columnName + " " + SQLDataType.DOUBLE + " NOT NULL", doubleDef.toString());
	}
	
	@Test
	public void testToStringDOUBLEUnsigned(){
		doubleDef = ColumnDefinition.builder()
				.columnName(columnName)
				.doubleType()
				.defaultSizeAndDigits()
				.unsigned()
				.build();
		assertEquals(columnName + " " + SQLDataType.DOUBLE + " UNSIGNED", doubleDef.toString());
	}
	
	@Test
	public void testToStringDOUBLEAutoIncrement(){
		doubleDef = ColumnDefinition.builder()
				.columnName(columnName)
				.doubleType()
				.defaultSizeAndDigits()
				.autoIncrement()
				.build();
		assertEquals(columnName + " " + SQLDataType.DOUBLE + " AUTO_INCREMENT", doubleDef.toString());
	}
	
	@Test
	public void testToStringDOUBLEPrimaryKey(){
		doubleDef = ColumnDefinition.builder()
				.columnName(columnName)
				.doubleType()
				.defaultSizeAndDigits()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.DOUBLE + " PRIMARY KEY", doubleDef.toString());
	}
	
	@Test
	public void testToStringDOUBLEAll(){
		doubleDef = ColumnDefinition.builder()
				.columnName(columnName)
				.doubleType()
				.sizeAndDigits(size, digits)
				.notNull()
				.unsigned()
				.autoIncrement()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.DOUBLE + "(" + size + ", " + digits + ") " +
						"NOT NULL UNSIGNED AUTO_INCREMENT PRIMARY KEY",
				doubleDef.toString());
	}
}
