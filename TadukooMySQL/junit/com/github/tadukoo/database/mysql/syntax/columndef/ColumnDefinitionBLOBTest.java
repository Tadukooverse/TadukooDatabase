package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.SQLDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionBLOBTest implements ColumnDefinitionConstants{
	private ColumnDefinition blobDef;
	
	@BeforeEach
	public void setup(){
		blobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.blob()
				.length(size)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testSetBlob(){
		assertEquals(SQLDataType.BLOB, blobDef.getDataType());
	}
	
	@Test
	public void testBlobDefaultSize(){
		blobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.blob()
				.defaultLength()
				.build();
		assertNull(blobDef.getSize());
	}
	
	@Test
	public void testBlobLongSize(){
		long sizeL = 100L;
		blobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.blob()
				.length(sizeL)
				.build();
		assertEquals(sizeL, blobDef.getSize());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testBLOBMissingColumnName(){
		try{
			blobDef = ColumnDefinition.builder()
					.columnName(null)
					.blob()
					.length(size)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testBLOBLowSize(){
		try{
			blobDef = ColumnDefinition.builder()
					.columnName(columnName)
					.blob()
					.length(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.BLOB + " size must be between 0 and 4,294,967,295 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBLOBHighSize(){
		try{
			blobDef = ColumnDefinition.builder()
					.columnName(columnName)
					.blob()
					.length(4294967296L)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.BLOB + " size must be between 0 and 4,294,967,295 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBLOBAllErrors(){
		try{
			blobDef = ColumnDefinition.builder()
					.columnName(null)
					.blob()
					.length(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.BLOB + " size must be between 0 and 4,294,967,295 (or defaulted)!", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToStringBLOB(){
		assertEquals(columnName + " " + SQLDataType.BLOB + "(" + size + ")", blobDef.toString());
	}
	
	@Test
	public void testToStringBLOBDefaultSize(){
		blobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.blob()
				.defaultLength()
				.build();
		assertEquals(columnName + " " + SQLDataType.BLOB, blobDef.toString());
	}
}
