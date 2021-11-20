package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionLONGTEXTTest implements ColumnDefinitionConstants{
	private ColumnDefinition longtextDef;
	
	@BeforeEach
	public void setup(){
		longtextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.longtext()
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetLongtext(){
		assertEquals(SQLDataType.LONGTEXT, longtextDef.getDataType());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			longtextDef = ColumnDefinition.builder()
					.columnName(null)
					.longtext()
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
	public void testToStringLONGTEXT(){
		assertEquals(columnName + " " + SQLDataType.LONGTEXT, longtextDef.toString());
	}
}
