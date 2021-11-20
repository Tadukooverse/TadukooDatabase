package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionLONGBLOBTest implements ColumnDefinitionConstants{
	private ColumnDefinition longblobDef;
	
	@BeforeEach
	public void setup(){
		longblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.longblob()
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetLongblob(){
		assertEquals(SQLDataType.LONGBLOB, longblobDef.getDataType());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			longblobDef = ColumnDefinition.builder()
					.columnName(null)
					.longblob()
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
	public void testToStringLONGBLOB(){
		assertEquals(columnName + " " + SQLDataType.LONGBLOB, longblobDef.toString());
	}
}
