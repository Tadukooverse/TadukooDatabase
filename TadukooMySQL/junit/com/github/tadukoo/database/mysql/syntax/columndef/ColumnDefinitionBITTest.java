package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionBITTest implements ColumnDefinitionConstants{
	private ColumnDefinition bitDef;
	
	@BeforeEach
	public void setup(){
		bitDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bit()
				.length(size)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetBit(){
		assertEquals(SQLDataType.BIT, bitDef.getDataType());
	}
	
	@Test
	public void testBitDefaultSize(){
		bitDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bit()
				.defaultLength()
				.build();
		assertNull(bitDef.getSize());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			bitDef = ColumnDefinition.builder()
					.columnName(null)
					.bit()
					.defaultLength()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testBITLowSize(){
		try{
			bitDef = ColumnDefinition.builder()
					.columnName(columnName)
					.bit()
					.length(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.BIT + " size must be between 1 and 64 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBITHighSize(){
		try{
			bitDef = ColumnDefinition.builder()
					.columnName(columnName)
					.bit()
					.length(65)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.BIT + " size must be between 1 and 64 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBITAllErrors(){
		try{
			bitDef = ColumnDefinition.builder()
					.columnName(null)
					.bit()
					.length(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.BIT + " size must be between 1 and 64 (or defaulted)!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringBIT(){
		assertEquals(columnName + " " + SQLDataType.BIT + "(" + size + ")", bitDef.toString());
	}
	
	@Test
	public void testToStringBITDefaultSize(){
		bitDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bit()
				.defaultLength()
				.build();
		assertEquals(columnName + " " + SQLDataType.BIT, bitDef.toString());
	}
}
