package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionAllTest implements ColumnDefinitionConstants{
	private ColumnDefinition charDef;
	private ColumnDefinition tinyblobDef;
	private ColumnDefinition enumDef;
	private ColumnDefinition floatDef;
	private ColumnDefinition datetimeDef;
	
	@BeforeEach
	public void setup(){
		charDef = ColumnDefinition.builder()
				.columnName(columnName)
				.character()
				.length(size)
				.build();
		tinyblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyblob()
				.build();
		enumDef = ColumnDefinition.builder()
				.columnName(columnName)
				.enumeration()
				.values(values)
				.build();
		floatDef = ColumnDefinition.builder()
				.columnName(columnName)
				.floatType()
				.sizeAndDigits(size, digits)
				.build();
		datetimeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.datetime()
				.fractionalSecondsPrecision(fractionalSecondsPrecision)
				.build();
	}
	
	/*
	 * Setting fields
	 */
	
	@Test
	public void testSetColumnName(){
		assertEquals(columnName, charDef.getColumnName());
	}
	
	@Test
	public void testSetSize(){
		assertEquals(size, charDef.getSize());
	}
	
	@Test
	public void testSetValues(){
		assertEquals(values, enumDef.getValues());
	}
	
	@Test
	public void testSetSizeAndDigits(){
		assertEquals(size, floatDef.getSize());
		assertEquals(digits, floatDef.getDigits());
	}
	
	@Test
	public void testSetFractionalSecondsPrecision(){
		assertEquals(fractionalSecondsPrecision, datetimeDef.getFractionalSecondsPrecision());
	}
	
	@Test
	public void testSetAutoIncrement(){
		ColumnDefinition tinyintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyint()
				.size(size)
				.autoIncrement()
				.build();
		assertTrue(tinyintDef.isAutoIncremented());
	}
	
	/*
	 * Default Fields
	 */
	
	@Test
	public void testDefaultSize(){
		assertNull(tinyblobDef.getSize());
	}
	
	@Test
	public void testDefaultValues(){
		List<String> values = charDef.getValues();
		assertNotNull(values);
		assertEquals(0, values.size());
	}
	
	@Test
	public void testDefaultDigits(){
		assertNull(charDef.getDigits());
	}
	
	@Test
	public void testDefaultFractionalSecondsPrecision(){
		assertNull(charDef.getFractionalSecondsPrecision());
	}
	
	@Test
	public void testDefaultAutoIncrement(){
		assertFalse(charDef.isAutoIncremented());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			charDef = ColumnDefinition.builder()
					.columnName(null)
					.character()
					.defaultLength()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
}
