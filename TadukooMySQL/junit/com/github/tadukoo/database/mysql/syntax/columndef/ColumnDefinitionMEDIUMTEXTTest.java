package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionMEDIUMTEXTTest implements ColumnDefinitionConstants{
	private ColumnDefinition mediumtextDef;
	
	@BeforeEach
	public void setup(){
		mediumtextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumtext()
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetMediumtext(){
		assertEquals(SQLDataType.MEDIUMTEXT, mediumtextDef.getDataType());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			mediumtextDef = ColumnDefinition.builder()
					.columnName(null)
					.mediumtext()
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
	public void testToStringMEDIUMTEXT(){
		assertEquals(columnName + " " + SQLDataType.MEDIUMTEXT, mediumtextDef.toString());
	}
}
