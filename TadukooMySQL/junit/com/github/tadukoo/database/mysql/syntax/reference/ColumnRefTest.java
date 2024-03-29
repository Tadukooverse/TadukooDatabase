package com.github.tadukoo.database.mysql.syntax.reference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnRefTest{
	private ColumnRef columnRef;
	private final String columnName = "Test";
	
	@BeforeEach
	public void setup(){
		columnRef = ColumnRef.builder().columnName(columnName).build();
	}
	
	@Test
	public void testBuilderDefaultTableName(){
		assertNull(columnRef.getTableName());
	}
	
	@Test
	public void testBuilderSetTableName(){
		columnRef = ColumnRef.builder()
				.tableName("Derp")
				.columnName(columnName)
				.build();
		assertEquals("Derp", columnRef.getTableName());
	}
	
	@Test
	public void testBuilderSetTableRef(){
		TableRef tableRef = TableRef.builder()
				.tableName("Derp")
				.build();
		columnRef = ColumnRef.builder()
				.tableRef(tableRef)
				.columnName(columnName)
				.build();
		assertEquals("Derp", columnRef.getTableName());
	}
	
	@Test
	public void testBuilderSetColumnName(){
		assertEquals(columnName, columnRef.getColumnName());
	}
	
	@Test
	public void testBuilderDefaultAlias(){
		assertNull(columnRef.getAlias());
	}
	
	@Test
	public void testBuilderSetAlias(){
		columnRef = ColumnRef.builder().columnName(columnName).alias("Derp").build();
		assertEquals("Derp", columnRef.getAlias());
	}
	
	@Test
	public void testBuilderMissingColumnName(){
		try{
			columnRef = ColumnRef.builder().columnName(null).build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors trying to build a ColumnRef: \n" +
					"columnName is required!", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals(columnName, columnRef.toString());
	}
	
	@Test
	public void testToStringWithTableName(){
		columnRef = ColumnRef.builder()
				.tableName("Derp")
				.columnName(columnName)
				.build();
		assertEquals("Derp." + columnName, columnRef.toString());
	}
	
	@Test
	public void testToStringWithAlias(){
		columnRef = ColumnRef.builder().columnName(columnName).alias("Derp").build();
		assertEquals(columnName + " AS Derp", columnRef.toString());
	}
	
	@Test
	public void testToStringWithAliasWithSpaces(){
		columnRef = ColumnRef.builder().columnName(columnName).alias("Derp Test").build();
		assertEquals(columnName + " AS \"Derp Test\"", columnRef.toString());
	}
	
	@Test
	public void testToStringWithAll(){
		columnRef = ColumnRef.builder()
				.tableName("Derp")
				.columnName(columnName)
				.alias("Derp Test")
				.build();
		assertEquals("Derp." + columnName + " AS \"Derp Test\"", columnRef.toString());
	}
}
