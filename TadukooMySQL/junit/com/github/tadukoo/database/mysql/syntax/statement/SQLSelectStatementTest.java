package com.github.tadukoo.database.mysql.syntax.statement;

import com.github.tadukoo.database.mysql.syntax.conditional.Conditional;
import com.github.tadukoo.database.mysql.syntax.conditional.ConditionalStatement;
import com.github.tadukoo.database.mysql.syntax.conditional.SQLOperator;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SQLSelectStatementTest{
	private SQLSelectStatement stmt;
	private TableRef fromTable;
	
	@BeforeEach
	public void setup(){
		fromTable = TableRef.builder()
				.tableName("Test")
				.build();
		stmt = SQLSelectStatement.builder()
				.fromTables(fromTable)
				.build();
	}
	
	@Test
	public void testBuilderDefaultDistinct(){
		assertFalse(stmt.isDistinct());
	}
	
	@Test
	public void testBuilderSetDistinct(){
		stmt = SQLSelectStatement.builder()
				.distinct()
				.fromTables(fromTable)
				.build();
		assertTrue(stmt.isDistinct());
	}

	@Test
	public void testBuilderDefaultReturnColumns(){
		assertTrue(ListUtil.isBlank(stmt.getReturnColumns()));
	}
	
	@Test
	public void testBuilderSetFromTable(){
		List<TableRef> fromTables = stmt.getFromTables();
		assertEquals(1, fromTables.size());
		assertEquals(fromTable, fromTables.get(0));
	}
	
	@Test
	public void testBuilderDefaultWhereStatement(){
		assertNull(stmt.getWhereStatement());
	}
	
	@Test
	public void testBuilderSetReturnColumn(){
		ColumnRef column = ColumnRef.builder()
				.columnName("Derp")
				.build();
		stmt = SQLSelectStatement.builder()
				.returnColumns(column).fromTables(fromTable)
				.build();
		List<ColumnRef> returnColumns = stmt.getReturnColumns();
		assertEquals(1, returnColumns.size());
		assertEquals(column, returnColumns.get(0));
	}
	
	@Test
	public void testBuilderSetReturnColumns(){
		List<ColumnRef> columns = ListUtil.createList(
				ColumnRef.builder().columnName("Derp").build(),
				ColumnRef.builder().columnName("Derp2").build()
		);
		stmt = SQLSelectStatement.builder()
				.returnColumns(columns).fromTables(fromTable)
				.build();
		assertEquals(columns, stmt.getReturnColumns());
	}
	
	@Test
	public void testBuilderSetFromTables(){
		List<TableRef> tables = ListUtil.createList(
				fromTable,
				TableRef.builder().tableName("Test2").build()
		);
		stmt = SQLSelectStatement.builder()
				.fromTables(tables)
				.build();
		assertEquals(tables, stmt.getFromTables());
	}
	
	@Test
	public void testBuilderSetWhereStatement(){
		Conditional cond = Conditional.builder()
				.firstCondStmt(ConditionalStatement.builder()
						.column(ColumnRef.builder().columnName("Derp").build())
						.operator(SQLOperator.EQUAL)
						.value("yep")
						.build())
				.build();
		stmt = SQLSelectStatement.builder()
				.fromTables(fromTable).whereStatement(cond)
				.build();
		assertEquals(cond, stmt.getWhereStatement());
	}
	
	@Test
	public void testBuilderMissingFromTable(){
		try{
			stmt = SQLSelectStatement.builder().fromTables().build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors trying to build a SQLSelectStatement: \n" +
					"Must add at least one fromTable!", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals("SELECT * FROM " + fromTable.toString(), stmt.toString());
	}
	
	@Test
	public void testToStringDistinct(){
		stmt = SQLSelectStatement.builder()
				.distinct()
				.fromTables(fromTable)
				.build();
		assertEquals("SELECT DISTINCT * FROM " + fromTable.toString(), stmt.toString());
	}
	
	@Test
	public void testToStringMultipleFromTables(){
		TableRef table2 = TableRef.builder().tableName("Test2").build();
		stmt = SQLSelectStatement.builder()
				.fromTables(fromTable, table2)
				.build();
		assertEquals("SELECT * FROM " + fromTable.toString() + ", " + table2.toString(), stmt.toString());
	}
	
	@Test
	public void testToStringSingleReturnColumn(){
		ColumnRef column = ColumnRef.builder().columnName("Derp").build();
		stmt = SQLSelectStatement.builder()
				.returnColumns(column).fromTables(fromTable)
				.build();
		assertEquals("SELECT " + column.toString() + " FROM " + fromTable.toString(), stmt.toString());
	}
	
	@Test
	public void testToStringMultipleReturnColumns(){
		ColumnRef column = ColumnRef.builder().columnName("Derp").build();
		ColumnRef column2 = ColumnRef.builder().columnName("Derp2").build();
		stmt = SQLSelectStatement.builder()
				.returnColumns(column, column2).fromTables(fromTable)
				.build();
		assertEquals("SELECT " + column.toString() + ", " + column2.toString() + " FROM " + fromTable.toString(),
				stmt.toString());
	}
	
	@Test
	public void testToStringWhereStatement(){
		Conditional cond = Conditional.builder()
				.firstCondStmt(ConditionalStatement.builder()
						.column(ColumnRef.builder().columnName("Derp").build())
						.operator(SQLOperator.EQUAL)
						.value("yep")
						.build())
				.build();
		stmt = SQLSelectStatement.builder()
				.fromTables(fromTable).whereStatement(cond)
				.build();
		assertEquals("SELECT * FROM " + fromTable.toString() + " WHERE " + cond.toString(), stmt.toString());
	}
	
	@Test
	public void testToStringEverything(){
		TableRef table2 = TableRef.builder().tableName("Test2").build();
		ColumnRef column = ColumnRef.builder().columnName("Derp").build();
		ColumnRef column2 = ColumnRef.builder().columnName("Derp2").build();
		Conditional cond = Conditional.builder()
				.firstCondStmt(ConditionalStatement.builder()
						.column(ColumnRef.builder().columnName("Derp").build())
						.operator(SQLOperator.EQUAL)
						.value("yep")
						.build())
				.build();
		stmt = SQLSelectStatement.builder()
				.distinct()
				.returnColumns(column, column2)
				.fromTables(fromTable, table2)
				.whereStatement(cond)
				.build();
		assertEquals("SELECT DISTINCT " + column.toString() + ", " + column2.toString() + " FROM " +
						fromTable.toString() + ", " + table2.toString() + " WHERE " + cond.toString(),
				stmt.toString());
	}
}
