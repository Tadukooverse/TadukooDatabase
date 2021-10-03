package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.database.mysql.syntax.conditional.Conditional;
import com.github.tadukoo.database.mysql.syntax.conditional.ConditionalStatement;
import com.github.tadukoo.database.mysql.syntax.conditional.EqualsStatement;
import com.github.tadukoo.database.mysql.syntax.conditional.SQLOperator;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class SQLUpdateStatementTest{
	private SQLUpdateStatement stmt;
	private TableRef table;
	private EqualsStatement setStatement;
	
	@BeforeEach
	public void setup(){
		table = TableRef.builder().tableName("Test").build();
		setStatement = new EqualsStatement(ColumnRef.builder().columnName("Derp").build(), 42);
		stmt = SQLUpdateStatement.builder(table)
				.setStatement(setStatement)
				.build();
	}
	
	@Test
	public void testBuilderSetTable(){
		assertEquals(table, stmt.getTable());
	}
	
	@Test
	public void testBuilderSetSetStatement(){
		List<EqualsStatement> setStatements = stmt.getSetStatements();
		assertEquals(1, setStatements.size());
		assertEquals(setStatement, setStatements.get(0));
	}
	
	@Test
	public void testBuilderDefaultWhereStatement(){
		assertNull(stmt.getWhereStatement());
	}
	
	@Test
	public void testBuilderSetSetStatements(){
		List<EqualsStatement> setStatements = ListUtil.createList(
				setStatement,
				new EqualsStatement(ColumnRef.builder().columnName("Derp2").build(), true)
		);
		stmt = SQLUpdateStatement.builder(table)
				.setStatements(setStatements)
				.build();
		assertEquals(setStatements, stmt.getSetStatements());
	}
	
	@Test
	public void testBuilderSetWhereStatement(){
		Conditional where = Conditional.builder()
				.firstCondStmt(ConditionalStatement.builder()
						.column(ColumnRef.builder().columnName("Something").build())
						.operator(SQLOperator.NOT_EQUAL)
						.value(67)
						.build())
				.build();
		stmt = SQLUpdateStatement.builder(table)
				.setStatement(setStatement).whereStatement(where)
				.build();
		assertEquals(where, stmt.getWhereStatement());
	}
	
	@Test
	public void testBuilderMissingTable(){
		try{
			stmt = SQLUpdateStatement.builder(null)
					.setStatement(setStatement)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("The following errors occurred trying to create a " +
					"SQLUpdateStatement: \ntable is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingSetStatements(){
		try{
			stmt = SQLUpdateStatement.builder(table)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("The following errors occurred trying to create a " +
					"SQLUpdateStatement: \nsetStatements are required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingAll(){
		try{
			stmt = SQLUpdateStatement.builder(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					The following errors occurred trying to create a SQLUpdateStatement:\s
					table is required!
					setStatements are required!""", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals("UPDATE " + table + " SET " + setStatement, stmt.toString());
	}
	
	@Test
	public void testToStringMultipleSetStatements(){
		EqualsStatement setStatement2 = new EqualsStatement(ColumnRef.builder().columnName("Derp2").build(), true);
		stmt = SQLUpdateStatement.builder(table)
				.setStatement(setStatement).setStatement(setStatement2)
				.build();
		assertEquals("UPDATE " + table + " SET " + setStatement + ", " + setStatement2, stmt.toString());
	}
	
	@Test
	public void testToStringWithWhereStatement(){
		Conditional where = Conditional.builder()
				.firstCondStmt(ConditionalStatement.builder()
						.column(ColumnRef.builder().columnName("Something").build())
						.operator(SQLOperator.NOT_EQUAL)
						.value(67)
						.build())
				.build();
		stmt = SQLUpdateStatement.builder(table)
				.setStatement(setStatement).whereStatement(where)
				.build();
		assertEquals("UPDATE " + table + " SET " + setStatement + " WHERE " + where, stmt.toString());
	}
	
	@Test
	public void testToStringWithEverything(){
		EqualsStatement setStatement2 = new EqualsStatement(ColumnRef.builder().columnName("Derp2").build(), true);
		Conditional where = Conditional.builder()
				.firstCondStmt(ConditionalStatement.builder()
						.column(ColumnRef.builder().columnName("Something").build())
						.operator(SQLOperator.NOT_EQUAL)
						.value(67)
						.build())
				.build();
		stmt = SQLUpdateStatement.builder(table)
				.setStatement(setStatement).setStatement(setStatement2).whereStatement(where)
				.build();
		assertEquals("UPDATE " + table + " SET " + setStatement + ", " + setStatement2 + " WHERE " + where,
				stmt.toString());
	}
}
