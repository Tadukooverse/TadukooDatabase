package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionTINYBLOBTest implements ColumnDefinitionConstants{
	private ColumnDefinition tinyblobDef;
	
	@BeforeEach
	public void setup(){
		tinyblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyblob()
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetTinyblob(){
		assertEquals(SQLDataType.TINYBLOB, tinyblobDef.getDataType());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testTINYBLOBMissingColumnName(){
		try{
			tinyblobDef = ColumnDefinition.builder()
					.columnName(null)
					.tinyblob()
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
	public void testToStringTINYBLOB(){
		assertEquals(columnName + " " + SQLDataType.TINYBLOB, tinyblobDef.toString());
	}
}
