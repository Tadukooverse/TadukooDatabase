package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionMEDIUMBLOBTest implements ColumnDefinitionConstants{
	private ColumnDefinition mediumblobDef;
	
	@BeforeEach
	public void setup(){
		mediumblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumblob()
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetMediumblob(){
		assertEquals(SQLDataType.MEDIUMBLOB, mediumblobDef.getDataType());
	}
	
	@Test
	public void testSetMediumblobNotNull(){
		mediumblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumblob()
				.notNull()
				.build();
		assertTrue(mediumblobDef.isNotNull());
	}
	
	@Test
	public void testSetMediumblobPrimaryKey(){
		mediumblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumblob()
				.primaryKey()
				.build();
		assertTrue(mediumblobDef.isPrimaryKey());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			mediumblobDef = ColumnDefinition.builder()
					.columnName(null)
					.mediumblob()
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
	public void testToStringMEDIUMBLOB(){
		assertEquals(columnName + " " + SQLDataType.MEDIUMBLOB, mediumblobDef.toString());
	}
	
	@Test
	public void testToStringMEDIUMBLOBNotNull(){
		mediumblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumblob()
				.notNull()
				.build();
		assertEquals(columnName + " " + SQLDataType.MEDIUMBLOB + " NOT NULL", mediumblobDef.toString());
	}
	
	@Test
	public void testToStringMEDIUMBLOBPrimaryKey(){
		mediumblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumblob()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.MEDIUMBLOB + " PRIMARY KEY", mediumblobDef.toString());
	}
	
	@Test
	public void testToStringMEDIUMBLOBAll(){
		mediumblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumblob()
				.notNull()
				.primaryKey()
				.build();
		assertEquals(columnName + " " + SQLDataType.MEDIUMBLOB + " NOT NULL PRIMARY KEY",
				mediumblobDef.toString());
	}
}
