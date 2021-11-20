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

public class ColumnDefinitionENUMTest implements ColumnDefinitionConstants{
	private ColumnDefinition enumDef;
	
	@BeforeEach
	public void setup(){
		enumDef = ColumnDefinition.builder()
				.columnName(columnName)
				.enumeration()
				.values(values)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetEnum(){
		assertEquals(SQLDataType.ENUM, enumDef.getDataType());
	}
	
	@Test
	public void testEnumIndividualValues(){
		enumDef = ColumnDefinition.builder()
				.columnName(columnName)
				.enumeration()
				.values("Derp", "Plop")
				.build();
		List<String> values = enumDef.getValues();
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
			enumDef = ColumnDefinition.builder()
					.columnName(null)
					.enumeration()
					.values(values)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testENUMEmptyValues(){
		try{
			enumDef = ColumnDefinition.builder()
					.columnName(columnName)
					.enumeration()
					.values((ArrayList<String>) null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"Must specify values for " + SQLDataType.ENUM + "!", e.getMessage());
		}
	}
	
	@Test
	public void testENUMHighValues(){
		try{
			List<String> values = new ArrayList<>();
			for(int i = 0; i < 65536; i++){
				values.add("");
			}
			enumDef = ColumnDefinition.builder()
					.columnName(columnName)
					.enumeration()
					.values(values)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.ENUM + " values must be between 1 and 65,535 in size!", e.getMessage());
		}
	}
	
	@Test
	public void testENUMAllErrors(){
		try{
			enumDef = ColumnDefinition.builder()
					.columnName(null)
					.enumeration()
					.values(new ArrayList<>())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"Must specify values for " + SQLDataType.ENUM + "!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringENUM(){
		assertEquals(columnName + " " + SQLDataType.ENUM + "(" + valuesString + ")", enumDef.toString());
	}
}
