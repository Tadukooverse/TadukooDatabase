package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionTINYTEXTTest implements ColumnDefinitionConstants{
	private ColumnDefinition tinytextDef;
	
	@BeforeEach
	public void setup(){
		tinytextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinytext()
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetTinytext(){
		assertEquals(SQLDataType.TINYTEXT, tinytextDef.getDataType());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testTINYTEXTMissingColumnName(){
		try{
			tinytextDef = ColumnDefinition.builder()
					.columnName(null)
					.tinytext()
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
	public void testToStringTINYTEXT(){
		assertEquals(columnName + " " + SQLDataType.TINYTEXT, tinytextDef.toString());
	}
}
