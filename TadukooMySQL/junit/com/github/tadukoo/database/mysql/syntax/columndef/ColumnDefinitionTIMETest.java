package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionTIMETest implements ColumnDefinitionConstants{
	private ColumnDefinition timeDef;
	
	@BeforeEach
	public void setup(){
		timeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.time()
				.fractionalSecondsPrecision(fractionalSecondsPrecision)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetTime(){
		assertEquals(SQLDataType.TIME, timeDef.getDataType());
	}
	
	@Test
	public void testTimeDefaultFractionalSecondsPrecision(){
		timeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.time()
				.defaultFractionalSecondsPrecision()
				.build();
		assertNull(timeDef.getFractionalSecondsPrecision());
	}
	
	@Test
	public void testTimeNotNull(){
		timeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.time()
				.defaultFractionalSecondsPrecision()
				.notNull()
				.build();
		assertTrue(timeDef.isNotNull());
	}
	
	@Test
	public void testTimePrimaryKey(){
		timeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.time()
				.defaultFractionalSecondsPrecision()
				.primaryKey()
				.build();
		assertTrue(timeDef.isPrimaryKey());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			timeDef = ColumnDefinition.builder()
					.columnName(null)
					.time()
					.defaultFractionalSecondsPrecision()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testTIMELowFSP(){
		try{
			timeDef = ColumnDefinition.builder()
					.columnName(columnName)
					.time()
					.fractionalSecondsPrecision(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.TIME + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTIMEHighFSP(){
		try{
			timeDef = ColumnDefinition.builder()
					.columnName(columnName)
					.time()
					.fractionalSecondsPrecision(7)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.TIME + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTIMEAllErrors(){
		try{
			timeDef = ColumnDefinition.builder()
					.columnName(null)
					.time()
					.fractionalSecondsPrecision(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.TIME + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringTIME(){
		assertEquals(columnName + " " + SQLDataType.TIME + "(" + fractionalSecondsPrecision + ")", timeDef.toString());
	}
	
	@Test
	public void testToStringTIMEDefaultFSP(){
		timeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.time()
				.defaultFractionalSecondsPrecision()
				.build();
		assertEquals(columnName + " " + SQLDataType.TIME, timeDef.toString());
	}
	
	@Test
	public void testToStringTIMENotNull(){
		timeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.time()
				.defaultFractionalSecondsPrecision()
				.notNull()
				.build();
		assertEquals(columnName + " " + SQLDataType.TIME + " NOT NULL", timeDef.toString());
	}
	
	@Test
	public void testToStringTIMEPrimaryKey(){
		timeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.time()
				.defaultFractionalSecondsPrecision()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.TIME + " PRIMARY KEY", timeDef.toString());
	}
	
	@Test
	public void testToStringTIMEAll(){
		timeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.time()
				.fractionalSecondsPrecision(fractionalSecondsPrecision)
				.notNull()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.TIME + "(" + fractionalSecondsPrecision + ") " +
				"NOT NULL PRIMARY KEY", timeDef.toString());
	}
}
