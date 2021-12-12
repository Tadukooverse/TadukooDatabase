package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionVARCHARTest implements ColumnDefinitionConstants{
	private ColumnDefinition varcharDef;
	
	@BeforeEach
	public void setup(){
		varcharDef = ColumnDefinition.builder()
				.columnName(columnName)
				.varchar()
				.length(size)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetVarchar(){
		assertEquals(SQLDataType.VARCHAR, varcharDef.getDataType());
	}
	
	@Test
	public void testVarcharNotNull(){
		varcharDef = ColumnDefinition.builder()
				.columnName(columnName)
				.varchar()
				.length(size)
				.notNull()
				.build();
		assertTrue(varcharDef.isNotNull());
	}
	
	@Test
	public void testVarcharPrimaryKey(){
		varcharDef = ColumnDefinition.builder()
				.columnName(columnName)
				.varchar()
				.length(size)
				.primaryKey()
				.build();
		assertTrue(varcharDef.isPrimaryKey());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testVARCHARMissingColumnName(){
		try{
			varcharDef = ColumnDefinition.builder()
					.columnName(null)
					.varchar()
					.length(size)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!", e.getMessage());
		}
	}
	
	@Test
	public void testVARCHARLowSize(){
		try{
			varcharDef = ColumnDefinition.builder()
					.columnName(columnName)
					.varchar()
					.length(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.VARCHAR + " size must be between 0 and 65,535!", e.getMessage());
		}
	}
	
	@Test
	public void testVARCHARHighSize(){
		try{
			varcharDef = ColumnDefinition.builder()
					.columnName(columnName)
					.varchar()
					.length(65536)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.VARCHAR + " size must be between 0 and 65,535!", e.getMessage());
		}
	}
	
	@Test
	public void testVARCHARAllErrors(){
		try{
			varcharDef = ColumnDefinition.builder()
					.columnName(null)
					.varchar()
					.length(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.VARCHAR + " size must be between 0 and 65,535!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringVARCHAR(){
		assertEquals(columnName + " " + SQLDataType.VARCHAR + "(" + size + ")", varcharDef.toString());
	}
	
	@Test
	public void testToStringVARCHARNotNull(){
		varcharDef = ColumnDefinition.builder()
				.columnName(columnName)
				.varchar()
				.length(size)
				.notNull()
				.build();
		assertEquals(columnName + " " + SQLDataType.VARCHAR + "(" + size + ") NOT NULL", varcharDef.toString());
	}
	
	@Test
	public void testToStringVARCHARPrimaryKey(){
		varcharDef = ColumnDefinition.builder()
				.columnName(columnName)
				.varchar()
				.length(size)
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.VARCHAR + "(" + size + ") PRIMARY KEY",
				varcharDef.toString());
	}
	
	@Test
	public void testToStringVARCHARAll(){
		varcharDef = ColumnDefinition.builder()
				.columnName(columnName)
				.varchar()
				.length(size)
				.notNull()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.VARCHAR + "(" + size + ") NOT NULL PRIMARY KEY",
				varcharDef.toString());
	}
}
