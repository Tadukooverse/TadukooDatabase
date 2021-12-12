package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
	
	@Test
	public void testTinyblobNotNull(){
		tinyblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyblob()
				.notNull()
				.build();
		assertTrue(tinyblobDef.isNotNull());
	}
	
	@Test
	public void testTinyblobPrimaryKey(){
		tinyblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyblob()
				.primaryKey()
				.build();
		assertTrue(tinyblobDef.isPrimaryKey());
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
	
	@Test
	public void testToStringTINYBLOBNotNull(){
		tinyblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyblob()
				.notNull()
				.build();
		assertEquals(columnName + " " + SQLDataType.TINYBLOB + " NOT NULL", tinyblobDef.toString());
	}
	
	@Test
	public void testToStringTINYBLOBPrimaryKey(){
		tinyblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyblob()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.TINYBLOB + " PRIMARY KEY", tinyblobDef.toString());
	}
	
	@Test
	public void testToStringTINYBLOBAll(){
		tinyblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyblob()
				.notNull()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.TINYBLOB + " NOT NULL PRIMARY KEY", tinyblobDef.toString());
	}
}
