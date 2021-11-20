package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
