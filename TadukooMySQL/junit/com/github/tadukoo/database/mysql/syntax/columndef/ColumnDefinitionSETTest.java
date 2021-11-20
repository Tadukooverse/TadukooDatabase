package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionSETTest implements ColumnDefinitionConstants{
	private ColumnDefinition setDef;
	
	@BeforeEach
	public void setup(){
		setDef = ColumnDefinition.builder()
				.columnName(columnName)
				.set()
				.values(values)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetSet(){
		assertEquals(SQLDataType.SET, setDef.getDataType());
	}
	
	@Test
	public void testSetIndividualValues(){
		setDef = ColumnDefinition.builder()
				.columnName(columnName)
				.set()
				.values("Derp", "Plop")
				.build();
		List<String> values = setDef.getValues();
		assertNotNull(values);
		assertEquals(2, values.size());
		assertEquals("Derp", values.get(0));
		assertEquals("Plop", values.get(1));
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testMissingColumnName(){
		try{
			setDef = ColumnDefinition.builder()
					.columnName(null)
					.set()
					.values(values)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testSETEmptyValues(){
		try{
			setDef = ColumnDefinition.builder()
					.columnName(columnName)
					.set()
					.values((ArrayList<String>) null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"Must specify values for " + SQLDataType.SET + "!", e.getMessage());
		}
	}
	
	@Test
	public void testSETHighValues(){
		try{
			List<String> values = new ArrayList<>();
			for(int i = 0; i < 65; i++){
				values.add("");
			}
			setDef = ColumnDefinition.builder()
					.columnName(columnName)
					.set()
					.values(values)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.SET + " values must be between 1 and 64 in size!", e.getMessage());
		}
	}
	
	@Test
	public void testSETAllErrors(){
		try{
			setDef = ColumnDefinition.builder()
					.columnName(null)
					.set()
					.values(new ArrayList<>())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"Must specify values for " + SQLDataType.SET + "!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringSET(){
		assertEquals(columnName + " " + SQLDataType.SET + "(" + valuesString + ")", setDef.toString());
	}
}
