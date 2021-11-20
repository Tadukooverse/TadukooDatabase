package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionDECIMALTest implements ColumnDefinitionConstants{
	private ColumnDefinition decimalDef;
	
	@BeforeEach
	public void setup(){
		decimalDef = ColumnDefinition.builder()
				.columnName(columnName)
				.decimal()
				.sizeAndDigits(size, digits)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetDecimal(){
		assertEquals(SQLDataType.DECIMAL, decimalDef.getDataType());
	}
	
	@Test
	public void testDecimalDefaultSizeAndDigits(){
		decimalDef = ColumnDefinition.builder()
				.columnName(columnName)
				.decimal()
				.defaultSizeAndDigits()
				.build();
		assertNull(decimalDef.getSize());
		assertNull(decimalDef.getDigits());
	}
	
	@Test
	public void testDecimalAutoIncrement(){
		decimalDef = ColumnDefinition.builder()
				.columnName(columnName)
				.decimal()
				.sizeAndDigits(size, digits)
				.autoIncrement()
				.build();
		assertTrue(decimalDef.isAutoIncremented());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			decimalDef = ColumnDefinition.builder()
					.columnName(null)
					.decimal()
					.sizeAndDigits(size, digits)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testDECIMALLowSize(){
		try{
			decimalDef = ColumnDefinition.builder()
					.columnName(columnName)
					.decimal()
					.sizeAndDigits(0, digits)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.DECIMAL + " size must be between 1 and 65 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testDECIMALHighSize(){
		try{
			decimalDef = ColumnDefinition.builder()
					.columnName(columnName)
					.decimal()
					.sizeAndDigits(66, digits)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.DECIMAL + " size must be between 1 and 65 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testDECIMALLowDigits(){
		try{
			decimalDef = ColumnDefinition.builder()
					.columnName(columnName)
					.decimal()
					.sizeAndDigits(size, -1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.DECIMAL + " digits must be between 0 and 30 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testDECIMALHighDigits(){
		try{
			decimalDef = ColumnDefinition.builder()
					.columnName(columnName)
					.decimal()
					.sizeAndDigits(size, 31)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.DECIMAL + " digits must be between 0 and 30 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testDECIMALAllErrors(){
		try{
			decimalDef = ColumnDefinition.builder()
					.columnName(null)
					.decimal()
					.sizeAndDigits(0, -1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.DECIMAL + " size must be between 1 and 65 (or defaulted)!\n" +
					"For " + SQLDataType.DECIMAL + " digits must be between 0 and 30 (or defaulted)!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringDECIMAL(){
		assertEquals(columnName + " " + SQLDataType.DECIMAL + "(" + size + ", " + digits + ")", decimalDef.toString());
	}
	
	@Test
	public void testToStringDECIMALDefaultSizeAndDigits(){
		decimalDef = ColumnDefinition.builder()
				.columnName(columnName)
				.decimal()
				.defaultSizeAndDigits()
				.build();
		assertEquals(columnName + " " + SQLDataType.DECIMAL, decimalDef.toString());
	}
	
	@Test
	public void testToStringDECIMALAutoIncrement(){
		decimalDef = ColumnDefinition.builder()
				.columnName(columnName)
				.decimal()
				.defaultSizeAndDigits()
				.autoIncrement()
				.build();
		assertEquals(columnName + " " + SQLDataType.DECIMAL + " AUTO_INCREMENT", decimalDef.toString());
	}
	
	@Test
	public void testToStringDECIMALAll(){
		decimalDef = ColumnDefinition.builder()
				.columnName(columnName)
				.decimal()
				.sizeAndDigits(size, digits)
				.autoIncrement()
				.build();
		assertEquals(columnName + " " + SQLDataType.DECIMAL + "(" + size + ", " + digits + ") AUTO_INCREMENT",
				decimalDef.toString());
	}
}
