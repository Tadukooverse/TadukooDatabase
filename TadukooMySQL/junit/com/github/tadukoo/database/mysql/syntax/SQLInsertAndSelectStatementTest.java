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

public class SQLInsertAndSelectStatementTest{
	private SQLInsertAndSelectStatement stmt;
	private TableRef table;
	private SQLSelectStatement selectStmt;
	
	@BeforeEach
	public void setup(){
		table = TableRef.builder().tableName("Test").build();
		selectStmt = SQLSelectStatement.builder().fromTable(TableRef.builder().tableName("Derp").build()).build();
		stmt = SQLInsertAndSelectStatement.builder(table, selectStmt).build();
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
	public void testBuilderSetSelectStmt(){
		assertEquals(selectStmt, stmt.getSelectStmt());
	}
	
	@Test
	public void testBuilderSetColumns(){
		List<ColumnRef> columns = ListUtil.createList(ColumnRef.builder().columnName("Col1").build(),
				ColumnRef.builder().columnName("Col2").build());
		stmt = SQLInsertAndSelectStatement.builder(table, selectStmt).columns(columns).build();
		assertEquals(columns, stmt.getColumns());
	}
	
	@Test
	public void testBuilderSetColumn(){
		ColumnRef column = ColumnRef.builder().columnName("Col1").build();
		stmt = SQLInsertAndSelectStatement.builder(table, selectStmt).column(column).build();
		List<ColumnRef> columns = stmt.getColumns();
		assertEquals(1, columns.size());
		assertEquals(column, columns.get(0));
	}
	
	@Test
	public void testBuilderMissingTable(){
		try{
			stmt = SQLInsertAndSelectStatement.builder(null, selectStmt).build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors trying to build a SQLInsertAndSelectStatement: " +
					"\ntable is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingSelectStmt(){
		try{
			stmt = SQLInsertAndSelectStatement.builder(table, null).build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors trying to build a SQLInsertAndSelectStatement: " +
					"\nselectStmt is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingAll(){
		try{
			stmt = SQLInsertAndSelectStatement.builder(null, null).build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Encountered the following errors trying to build a SQLInsertAndSelectStatement:\s
					table is required!
					selectStmt is required!""", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals("INSERT INTO " + table + " " + selectStmt, stmt.toString());
	}
	
	@Test
	public void testToStringWithSingleColumn(){
		ColumnRef column = ColumnRef.builder().columnName("Col1").build();
		stmt = SQLInsertAndSelectStatement.builder(table, selectStmt).column(column).build();
		assertEquals("INSERT INTO " + table + " (" + column + ") " + selectStmt, stmt.toString());
	}
	
	@Test
	public void testToStringWithMultipleColumns(){
		ColumnRef column1 = ColumnRef.builder().columnName("Col1").build();
		ColumnRef column2 = ColumnRef.builder().columnName("Col2").build();
		stmt = SQLInsertAndSelectStatement.builder(table, selectStmt).columns(ListUtil.createList(column1, column2))
				.build();
		assertEquals("INSERT INTO " + table + " (" + column1 + ", " + column2 + ") " + selectStmt,
				stmt.toString());
	}
}
