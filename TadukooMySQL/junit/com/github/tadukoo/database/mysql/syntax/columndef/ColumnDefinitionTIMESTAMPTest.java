package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionTIMESTAMPTest implements ColumnDefinitionConstants{
	private ColumnDefinition timestampDef;
	
	@BeforeEach
	public void setup(){
		timestampDef = ColumnDefinition.builder()
				.columnName(columnName)
				.timestamp()
				.fractionalSecondsPrecision(fractionalSecondsPrecision)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetTimestamp(){
		assertEquals(SQLDataType.TIMESTAMP, timestampDef.getDataType());
	}
	
	@Test
	public void testTimestampDefaultFractionalSecondsPrecision(){
		timestampDef = ColumnDefinition.builder()
				.columnName(columnName)
				.timestamp()
				.defaultFractionalSecondsPrecision()
				.build();
		assertNull(timestampDef.getFractionalSecondsPrecision());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			timestampDef = ColumnDefinition.builder()
					.columnName(null)
					.timestamp()
					.defaultFractionalSecondsPrecision()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testTIMESTAMPLowFSP(){
		try{
			timestampDef = ColumnDefinition.builder()
					.columnName(columnName)
					.timestamp()
					.fractionalSecondsPrecision(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.TIMESTAMP + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTIMESTAMPHighFSP(){
		try{
			timestampDef = ColumnDefinition.builder()
					.columnName(columnName)
					.timestamp()
					.fractionalSecondsPrecision(7)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.TIMESTAMP + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTIMESTAMPAllErrors(){
		try{
			timestampDef = ColumnDefinition.builder()
					.columnName(null)
					.timestamp()
					.fractionalSecondsPrecision(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.TIMESTAMP + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringTIMESTAMP(){
		assertEquals(columnName + " " + SQLDataType.TIMESTAMP + "(" + fractionalSecondsPrecision + ")", timestampDef.toString());
	}
	
	@Test
	public void testToStringTIMESTAMPDefaultFSP(){
		timestampDef = ColumnDefinition.builder()
				.columnName(columnName)
				.timestamp()
				.defaultFractionalSecondsPrecision()
				.build();
		assertEquals(columnName + " " + SQLDataType.TIMESTAMP, timestampDef.toString());
	}
}
