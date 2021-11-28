package com.github.tadukoo.database.mysql;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.database.mysql.syntax.statement.SQLCreateStatement;
import com.github.tadukoo.database.mysql.syntax.statement.SQLDropStatement;
import com.github.tadukoo.database.mysql.syntax.statement.SQLInsertStatement;
import com.github.tadukoo.database.mysql.syntax.statement.SQLSelectStatement;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseExecuteTests extends DatabaseConnectionTest{
	
	@Test
	public void testExecuteQueryAndExecuteUpdates() throws SQLException{
		// Try dropping the Test table (should succeed due to IF EXISTS)
		assertTrue(db.executeUpdate("Drop Test", SQLDropStatement.builder()
				.table()
				.ifExists()
				.name("Test")
				.build()
				.toString()));
		
		// Create one row of data
		assertTrue(db.executeUpdates("Create test table", null,
				ListUtil.createList(
						SQLCreateStatement.builder()
								.table()
								.ifTableNotExists()
								.tableName("Test")
								.columns(ColumnDefinition.builder()
										.columnName("id")
										.integer()
										.defaultSize()
										.build())
								.build()
								.toString(),
						SQLInsertStatement.builder()
								.table(TableRef.builder()
										.tableName("Test")
										.build())
								.columns(ColumnRef.builder()
										.columnName("id")
										.build())
								.values(42)
								.build()
								.toString()
				)));
		
		assertEquals(42, db.executeQuery("Find Tests", SQLSelectStatement.builder()
				.fromTables(TableRef.builder()
						.tableName("Test")
						.build())
				.build()
				.toString(),
				CommonResultSetConverters::singleInteger));
	}
	
	@Test
	public void testExecuteUpdate() throws SQLException{
		// Try dropping the Test table (should succeed due to IF EXISTS)
		assertTrue(db.executeUpdate("Drop Test", SQLDropStatement.builder()
				.table()
				.ifExists()
				.name("Test")
				.build()
				.toString()));
	}
	
	@Test
	public void testInsert() throws SQLException{
		// Try dropping the Test table (should succeed due to IF EXISTS)
		assertTrue(db.executeUpdate("Drop Test", SQLDropStatement.builder()
				.table()
				.ifExists()
				.name("Test")
				.build()
				.toString()));
		
		// Create the table if it doesn't exist
		assertTrue(db.executeUpdate("Create test table",
						SQLCreateStatement.builder()
								.table()
								.ifTableNotExists()
								.tableName("Test")
								.columns(ColumnDefinition.builder()
										.columnName("id")
										.integer()
										.defaultSize()
										.build())
								.build()
								.toString()));
		
		// Run the insert
		db.insert("Test", ListUtil.createList("id"), ListUtil.createList(42));
		
		assertEquals(42, db.executeQuery("Find Tests", SQLSelectStatement.builder()
						.fromTables(TableRef.builder()
								.tableName("Test")
								.build())
						.build()
						.toString(),
				CommonResultSetConverters::singleInteger));
	}
	
	@Test
	public void testInsertAndGetID() throws SQLException{
		// Try dropping the Test table (should succeed due to IF EXISTS)
		assertTrue(db.executeUpdate("Drop Test", SQLDropStatement.builder()
				.table()
				.ifExists()
				.name("Test")
				.build()
				.toString()));
		
		// Create the table if it doesn't exist
		assertTrue(db.executeUpdate("Create test table",
				SQLCreateStatement.builder()
						.table()
						.ifTableNotExists()
						.tableName("Test")
						.columns(ColumnDefinition.builder()
										.columnName("id")
										.integer()
										.defaultSize()
										.notNull()
										.autoIncrement()
										.primaryKey()
										.build(),
								ColumnDefinition.builder()
										.columnName("other")
										.integer()
										.defaultSize()
										.build())
						.build()
						.toString()));
		
		// Run the insert and get ID
		assertEquals(1, db.insertAndGetID("Test", "id",
				ListUtil.createList("other"), ListUtil.createList(42)));
	}
}
