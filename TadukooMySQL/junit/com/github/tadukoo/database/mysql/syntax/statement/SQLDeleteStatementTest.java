package com.github.tadukoo.database.mysql.syntax.statement;

import com.github.tadukoo.database.mysql.syntax.conditional.Conditional;
import com.github.tadukoo.database.mysql.syntax.conditional.ConditionalStatement;
import com.github.tadukoo.database.mysql.syntax.conditional.SQLOperator;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class SQLDeleteStatementTest{
	private SQLDeleteStatement stmt;
	private TableRef table;
	
	@BeforeEach
	public void setup(){
		table = TableRef.builder().tableName("Test").build();
		stmt = SQLDeleteStatement.builder().table(table).build();
	}
	
	@Test
	public void testBuilderSetTable(){
		assertEquals(table, stmt.getTable());
	}
	
	@Test
	public void testBuilderDefaultWhereStatement(){
		assertNull(stmt.getWhereStatement());
	}
	
	@Test
	public void testBuilderSetWhereStatement(){
		Conditional where = Conditional.builder()
				.firstCondStmt(ConditionalStatement.builder()
						.column(ColumnRef.builder().columnName("Derp").build())
						.operator(SQLOperator.NOT_EQUAL)
						.value(42)
						.build())
				.build();
		stmt = SQLDeleteStatement.builder().table(table).whereStatement(where).build();
		assertEquals(where, stmt.getWhereStatement());
	}
	
	@Test
	public void testBuilderMissingTable(){
		try{
			stmt = SQLDeleteStatement.builder().table(null).build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered while creating SQLDeleteStatement:\ntable is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals("DELETE FROM " + table, stmt.toString());
	}
	
	@Test
	public void testToStringWithWhere(){
		Conditional where = Conditional.builder()
				.firstCondStmt(ConditionalStatement.builder()
						.column(ColumnRef.builder().columnName("Derp").build())
						.operator(SQLOperator.NOT_EQUAL)
						.value(42)
						.build())
				.build();
		stmt = SQLDeleteStatement.builder().table(table).whereStatement(where).build();
		assertEquals("DELETE FROM " + table + " WHERE " + where, stmt.toString());
	}
}
