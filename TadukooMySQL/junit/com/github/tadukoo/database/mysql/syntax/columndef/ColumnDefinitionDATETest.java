package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionDATETest implements ColumnDefinitionConstants{
	private ColumnDefinition dateDef;
	
	@BeforeEach
	public void setup(){
		dateDef = ColumnDefinition.builder()
				.columnName(columnName)
				.date()
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetDate(){
		assertEquals(SQLDataType.DATE, dateDef.getDataType());
	}
	
	@Test
	public void testDateNotNull(){
		dateDef = ColumnDefinition.builder()
				.columnName(columnName)
				.date()
				.notNull()
				.build();
		assertTrue(dateDef.isNotNull());
	}
	
	@Test
	public void testDatePrimaryKey(){
		dateDef = ColumnDefinition.builder()
				.columnName(columnName)
				.date()
				.primaryKey()
				.build();
		assertTrue(dateDef.isPrimaryKey());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			dateDef = ColumnDefinition.builder()
					.columnName(null)
					.date()
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
	public void testToStringDATE(){
		assertEquals(columnName + " " + SQLDataType.DATE, dateDef.toString());
	}
	
	@Test
	public void testToStringDATENotNull(){
		dateDef = ColumnDefinition.builder()
				.columnName(columnName)
				.date()
				.notNull()
				.build();
		assertEquals(columnName + " " + SQLDataType.DATE + " NOT NULL", dateDef.toString());
	}
	
	@Test
	public void testToStringDATEPrimaryKey(){
		dateDef = ColumnDefinition.builder()
				.columnName(columnName)
				.date()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.DATE + " PRIMARY KEY", dateDef.toString());
	}
	
	@Test
	public void testToStringDATEAll(){
		dateDef = ColumnDefinition.builder()
				.columnName(columnName)
				.date()
				.notNull()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.DATE + " NOT NULL PRIMARY KEY", dateDef.toString());
	}
}
