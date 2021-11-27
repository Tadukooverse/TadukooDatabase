package com.github.tadukoo.database.mysql.syntax.statement;

import com.github.tadukoo.database.mysql.syntax.conditional.EqualsStatement;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SQLStatementTest{
	private final TableRef table = TableRef.builder()
			.tableName("Test")
			.build();
	private final ColumnRef column = ColumnRef.builder()
			.columnName("Derp")
			.build();
	
	@Test
	public void testSelectBuilder(){
		SQLSelectStatement selectStmt = SQLStatement.builder()
				.select()
				.fromTables(table)
				.build();
		assertNotNull(selectStmt);
	}
	
	@Test
	public void testInsertBuilder(){
		SQLInsertStatement insertStmt = SQLStatement.builder()
				.insert()
				.table(table)
				.values(42)
				.build();
		assertNotNull(insertStmt);
	}
	
	@Test
	public void testUpdateBuilder(){
		SQLUpdateStatement updateStmt = SQLStatement.builder()
				.update()
				.table(table)
				.setStatements(new EqualsStatement(column, 42))
				.build();
		assertNotNull(updateStmt);
	}
	
	@Test
	public void testDeleteBuilder(){
		SQLDeleteStatement deleteStmt = SQLStatement.builder()
				.delete()
				.table(table)
				.build();
		assertNotNull(deleteStmt);
	}
	
	@Test
	public void testCreateBuilder(){
		SQLCreateStatement createStmt = SQLStatement.builder()
				.create()
				.database()
				.databaseName("Test")
				.build();
		assertNotNull(createStmt);
	}
	
	@Test
	public void testDropBuilder(){
		SQLDropStatement dropStmt = SQLStatement.builder()
				.drop()
				.database()
				.name("Test")
				.build();
		assertNotNull(dropStmt);
	}
	
	@Test
	public void testAlterBuilder(){
		SQLAlterStatement alterStmt = SQLStatement.builder()
				.alter()
				.table()
				.tableName("Test")
				.drop()
				.columnName("Derp")
				.build();
		assertNotNull(alterStmt);
	}
}
