package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionVARBINARYTest implements ColumnDefinitionConstants{
	private ColumnDefinition varbinaryDef;
	
	@BeforeEach
	public void setup(){
		varbinaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.varbinary()
				.length(size)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetVarbinary(){
		assertEquals(SQLDataType.VARBINARY, varbinaryDef.getDataType());
	}
	
	@Test
	public void testVarbinaryNotNull(){
		varbinaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.varbinary()
				.length(size)
				.notNull()
				.build();
		assertTrue(varbinaryDef.isNotNull());
	}
	
	@Test
	public void testVarbinaryPrimaryKey(){
		varbinaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.varbinary()
				.length(size)
				.primaryKey()
				.build();
		assertTrue(varbinaryDef.isPrimaryKey());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testVARBINARYMissingColumnName(){
		try{
			varbinaryDef = ColumnDefinition.builder()
					.columnName(null)
					.varbinary()
					.length(size)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!", e.getMessage());
		}
	}
	
	@Test
	public void testVARBINARYLowSize(){
		try{
			varbinaryDef = ColumnDefinition.builder()
					.columnName(columnName)
					.varbinary()
					.length(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.VARBINARY + " size must be between 0 and 65,535!", e.getMessage());
		}
	}
	
	@Test
	public void testVARBINARYHighSize(){
		try{
			varbinaryDef = ColumnDefinition.builder()
					.columnName(columnName)
					.varbinary()
					.length(65536)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.VARBINARY + " size must be between 0 and 65,535!", e.getMessage());
		}
	}
	
	@Test
	public void testVARBINARYAllErrors(){
		try{
			varbinaryDef = ColumnDefinition.builder()
					.columnName(null)
					.varbinary()
					.length(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.VARBINARY + " size must be between 0 and 65,535!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringVARBINARY(){
		assertEquals(columnName + " " + SQLDataType.VARBINARY + "(" + size + ")", varbinaryDef.toString());
	}
	
	@Test
	public void testToStringVARBINARYNotNull(){
		varbinaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.varbinary()
				.length(size)
				.notNull()
				.build();
		assertEquals(columnName + " " + SQLDataType.VARBINARY + "(" + size + ") NOT NULL",
				varbinaryDef.toString());
	}
	
	@Test
	public void testToStringVARBINARYPrimaryKey(){
		varbinaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.varbinary()
				.length(size)
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.VARBINARY + "(" + size + ") PRIMARY KEY",
				varbinaryDef.toString());
	}
	
	@Test
	public void testToStringVARBINARYAll(){
		varbinaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.varbinary()
				.length(size)
				.notNull()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.VARBINARY + "(" + size + ") NOT NULL PRIMARY KEY",
				varbinaryDef.toString());
	}
}
