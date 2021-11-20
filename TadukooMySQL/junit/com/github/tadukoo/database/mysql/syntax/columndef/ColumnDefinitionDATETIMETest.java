package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionDATETIMETest implements ColumnDefinitionConstants{
	private ColumnDefinition datetimeDef;
	
	@BeforeEach
	public void setup(){
		datetimeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.datetime()
				.fractionalSecondsPrecision(fractionalSecondsPrecision)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetDatetime(){
		assertEquals(SQLDataType.DATETIME, datetimeDef.getDataType());
	}
	
	@Test
	public void testDatetimeDefaultFractionalSecondsPrecision(){
		datetimeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.datetime()
				.defaultFractionalSecondsPrecision()
				.build();
		assertNull(datetimeDef.getFractionalSecondsPrecision());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			datetimeDef = ColumnDefinition.builder()
					.columnName(null)
					.datetime()
					.defaultFractionalSecondsPrecision()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testDATETIMELowFSP(){
		try{
			datetimeDef = ColumnDefinition.builder()
					.columnName(columnName)
					.datetime()
					.fractionalSecondsPrecision(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.DATETIME + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testDATETIMEHighFSP(){
		try{
			datetimeDef = ColumnDefinition.builder()
					.columnName(columnName)
					.datetime()
					.fractionalSecondsPrecision(7)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.DATETIME + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testDATETIMEAllErrors(){
		try{
			datetimeDef = ColumnDefinition.builder()
					.columnName(null)
					.datetime()
					.fractionalSecondsPrecision(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.DATETIME + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringDATETIME(){
		assertEquals(columnName + " " + SQLDataType.DATETIME + "(" + fractionalSecondsPrecision + ")", datetimeDef.toString());
	}
	
	@Test
	public void testToStringDATETIMEDefaultFSP(){
		datetimeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.datetime()
				.defaultFractionalSecondsPrecision()
				.build();
		assertEquals(columnName + " " + SQLDataType.DATETIME, datetimeDef.toString());
	}
}
