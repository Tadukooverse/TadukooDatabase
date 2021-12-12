package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
	
	@Test
	public void testSetLongblobNotNull(){
		longblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.longblob()
				.notNull()
				.build();
		assertTrue(longblobDef.isNotNull());
	}
	
	@Test
	public void testSetLongblobPrimaryKey(){
		longblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.longblob()
				.primaryKey()
				.build();
		assertTrue(longblobDef.isPrimaryKey());
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
	
	@Test
	public void testToStringLONGBLOBNotNull(){
		longblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.longblob()
				.notNull()
				.build();
		assertEquals(columnName + " " + SQLDataType.LONGBLOB + " NOT NULL", longblobDef.toString());
	}
	
	@Test
	public void testToStringLONGBLOBPrimaryKey(){
		longblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.longblob()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.LONGBLOB + " PRIMARY KEY", longblobDef.toString());
	}
	
	@Test
	public void testToStringLONGBLOBAll(){
		longblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.longblob()
				.notNull()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.LONGBLOB + " NOT NULL PRIMARY KEY", longblobDef.toString());
	}
}
