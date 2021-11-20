package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionSMALLINTTest implements ColumnDefinitionConstants{
	private ColumnDefinition smallintDef;
	
	@BeforeEach
	public void setup(){
		smallintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.smallint()
				.size(size)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetSmallint(){
		assertEquals(SQLDataType.SMALLINT, smallintDef.getDataType());
	}
	
	@Test
	public void testSmallintDefaultSize(){
		smallintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.smallint()
				.defaultSize()
				.build();
		assertNull(smallintDef.getSize());
	}
	
	@Test
	public void testSmallintAutoIncrement(){
		smallintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.smallint()
				.size(size)
				.autoIncrement()
				.build();
		assertTrue(smallintDef.isAutoIncremented());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			smallintDef = ColumnDefinition.builder()
					.columnName(null)
					.smallint()
					.defaultSize()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testSMALLINTLowSize(){
		try{
			smallintDef = ColumnDefinition.builder()
					.columnName(columnName)
					.smallint()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.SMALLINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testSMALLINTHighSize(){
		try{
			smallintDef = ColumnDefinition.builder()
					.columnName(columnName)
					.smallint()
					.size(256)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.SMALLINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testSMALLINTAllErrors(){
		try{
			smallintDef = ColumnDefinition.builder()
					.columnName(null)
					.smallint()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.SMALLINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringSMALLINT(){
		assertEquals(columnName + " " + SQLDataType.SMALLINT + "(" + size + ")", smallintDef.toString());
	}
	
	@Test
	public void testToStringSMALLINTDefaultSize(){
		smallintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.smallint()
				.defaultSize()
				.build();
		assertEquals(columnName + " " + SQLDataType.SMALLINT, smallintDef.toString());
	}
	
	@Test
	public void testToStringSMALLINTAutoIncrement(){
		smallintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.smallint()
				.defaultSize()
				.autoIncrement()
				.build();
		assertEquals(columnName + " " + SQLDataType.SMALLINT + " AUTO_INCREMENT", smallintDef.toString());
	}
	
	@Test
	public void testToStringSMALLINTAll(){
		smallintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.smallint()
				.size(size)
				.autoIncrement()
				.build();
		assertEquals(columnName + " " + SQLDataType.SMALLINT + "(" + size + ") AUTO_INCREMENT",
				smallintDef.toString());
	}
}
