package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SQLInsertStatementTest{
	private SQLInsertStatement stmt;
	private TableRef table;
	private Object value;
	
	@BeforeEach
	public void setup(){
		table = TableRef.builder().tableName("Test").build();
		value = 42;
		stmt = SQLInsertStatement.builder()
				.table(table).value(value)
				.build();
	}
	
	@Test
	public void testBuilderSetTable(){
		assertEquals(table, stmt.getTable());
	}
	
	@Test
	public void testBuilderDefaultColumns(){
		assertTrue(ListUtil.isBlank(stmt.getColumns()));
	}
	
	@Test
	public void testBuilderSetValue(){
		List<Object> values = stmt.getValues();
		assertEquals(1, values.size());
		assertEquals(value, values.get(0));
	}
	
	@Test
	public void testBuilderSetColumn(){
		ColumnRef column = ColumnRef.builder().columnName("Derp").build();
		stmt = SQLInsertStatement.builder()
				.table(table).column(column).value(value)
				.build();
		List<ColumnRef> columns = stmt.getColumns();
		assertEquals(1, columns.size());
		assertEquals(column, columns.get(0));
	}
	
	@Test
	public void testBuilderSetColumnsAndSetValues(){
		List<ColumnRef> columns = ListUtil.createList(
				ColumnRef.builder().columnName("Derp").build(),
				ColumnRef.builder().columnName("Derp2").build()
		);
		List<Object> values = ListUtil.createList(value, "test");
		stmt = SQLInsertStatement.builder()
				.table(table).columns(columns).values(values)
				.build();
		assertEquals(columns, stmt.getColumns());
		assertEquals(values, stmt.getValues());
	}
	
	@Test
	public void testBuilderMissingTable(){
		try{
			stmt = SQLInsertStatement.builder()
					.value(value)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered while building SQLInsertStatement: \n" +
					"table is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingValues(){
		try{
			stmt = SQLInsertStatement.builder()
					.table(table)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered while building SQLInsertStatement: \n" +
					"values can't be empty!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderColumnsAndValuesSizeMismatch(){
		try{
			stmt = SQLInsertStatement.builder()
					.table(table).column(ColumnRef.builder().columnName("Derp").build())
					.column(ColumnRef.builder().columnName("Derp2").build()).value(value)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered while building SQLInsertStatement: \n" +
					"Number of columns must equal number of values if specified!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderAllErrors(){
		try{
			stmt = SQLInsertStatement.builder()
					.column(ColumnRef.builder().columnName("Derp").build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Errors encountered while building SQLInsertStatement:\s
					table is required!
					values can't be empty!
					Number of columns must equal number of values if specified!""", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals("INSERT INTO " + table.toString() + " VALUES (" +
				SQLSyntaxUtil.convertValueToString(value) + ")", stmt.toString());
	}
	
	@Test
	public void testToStringMultipleValues(){
		stmt = SQLInsertStatement.builder()
				.table(table).value(value).value(true)
				.build();
		assertEquals("INSERT INTO " + table.toString() + " VALUES (" +
				SQLSyntaxUtil.convertValueToString(value) + ", " + SQLSyntaxUtil.convertValueToString(true) + ")",
				stmt.toString());
	}
	
	@Test
	public void testToStringWithColumn(){
		ColumnRef column = ColumnRef.builder().columnName("Derp").build();
		stmt = SQLInsertStatement.builder()
				.table(table).column(column).value(value)
				.build();
		assertEquals("INSERT INTO " + table.toString() + " (" + column.toString() + ") VALUES (" +
				SQLSyntaxUtil.convertValueToString(value) + ")", stmt.toString());
	}
	
	@Test
	public void testToStringWithEverything(){
		ColumnRef column = ColumnRef.builder().columnName("Derp").build();
		ColumnRef column2 = ColumnRef.builder().columnName("Derp2").build();
		stmt = SQLInsertStatement.builder()
				.table(table).column(column).column(column2).value(value).value(true)
				.build();
		assertEquals("INSERT INTO " + table.toString() + " (" + column.toString() + ", " + column2.toString() +
				") VALUES (" + SQLSyntaxUtil.convertValueToString(value) + ", " +
				SQLSyntaxUtil.convertValueToString(true) + ")", stmt.toString());
	}
}
