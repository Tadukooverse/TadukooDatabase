package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SQLCreateStatementTest{
	private SQLCreateStatement stmt;
	private String databaseName;
	
	@BeforeEach
	public void setup(){
		databaseName = "Test";
		stmt = SQLCreateStatement.builder()
				.database()
				.databaseName(databaseName)
				.build();
	}
	
	@Test
	public void testBuilderDatabaseType(){
		assertEquals(SQLType.DATABASE, stmt.getType());
	}
	
	@Test
	public void testBuilderDatabaseName(){
		assertEquals(databaseName, stmt.getName());
	}
	
	@Test
	public void testBuilderMissingDatabaseName(){
		try{
			stmt = SQLCreateStatement.builder()
					.database()
					.databaseName(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors in building a " +
					"SQLCreateStatement: \nname is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderTableAs(){
		String tableName = "Test";
		SQLSelectStatement selectStmt = SQLSelectStatement.builder()
				.fromTables(TableRef.builder().tableName("Derp").build())
				.build();
		stmt = SQLCreateStatement.builder()
				.table()
				.tableName(tableName)
				.as(selectStmt)
				.build();
		assertEquals(SQLType.TABLE, stmt.getType());
		assertEquals(tableName, stmt.getName());
		assertEquals(selectStmt, stmt.getSelectStmt());
		assertTrue(ListUtil.isBlank(stmt.getColumns()));
	}
	
	@Test
	public void testBuilderTableColumnsList(){
		String tableName = "Test";
		List<ColumnDefinition> columns = ListUtil.createList(
				ColumnDefinition.builder()
						.columnName("Derp")
						.bit()
						.defaultLength()
						.build(),
				ColumnDefinition.builder()
						.columnName("Derp2")
						.bool()
						.build()
		);
		stmt = SQLCreateStatement.builder()
				.table()
				.tableName(tableName)
				.columns(columns)
				.build();
		assertEquals(SQLType.TABLE, stmt.getType());
		assertEquals(tableName, stmt.getName());
		assertEquals(columns, stmt.getColumns());
		assertNull(stmt.getSelectStmt());
	}
	
	@Test
	public void testBuilderTableIndividualColumns(){
		String tableName = "Test";
		ColumnDefinition col1 = ColumnDefinition.builder()
				.columnName("Derp")
				.bit()
				.defaultLength()
				.build();
		ColumnDefinition col2 = ColumnDefinition.builder()
				.columnName("Derp2")
				.bool()
				.build();
		stmt = SQLCreateStatement.builder()
				.table()
				.tableName(tableName)
				.columns(col1, col2)
				.build();
		assertEquals(SQLType.TABLE, stmt.getType());
		assertEquals(tableName, stmt.getName());
		List<ColumnDefinition> columns = stmt.getColumns();
		assertEquals(2, columns.size());
		assertEquals(col1, columns.get(0));
		assertEquals(col2, columns.get(1));
		assertNull(stmt.getSelectStmt());
	}
	
	@Test
	public void testBuilderDefaultForeignKeys(){
		List<ForeignKeyConstraint> foreignKeys = stmt.getForeignKeys();
		assertNotNull(foreignKeys);
		assertEquals(0, foreignKeys.size());
	}
	
	@Test
	public void testBuilderTableSetForeignKeys(){
		ForeignKeyConstraint foreignKey = ForeignKeyConstraint.builder()
				.columnNames("Test")
				.references("Derp")
				.referenceColumnNames("Plop")
				.build();
		stmt = SQLCreateStatement.builder()
				.table()
				.tableName("Test")
				.columns(ColumnDefinition.builder().columnName("Derp").year().build())
				.foreignKey(foreignKey)
				.build();
		List<ForeignKeyConstraint> foreignKeys = stmt.getForeignKeys();
		assertEquals(1, foreignKeys.size());
		assertEquals(foreignKey, foreignKeys.get(0));
	}
	
	@Test
	public void testBuilderTableMissingAs(){
		try{
			stmt = SQLCreateStatement.builder()
					.table()
					.tableName("Test")
					.as(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors in building a SQLCreateStatement: \n" +
					"selectStmt or columns is required if creating a table!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderTableMissingColumns(){
		try{
			stmt = SQLCreateStatement.builder()
					.table()
					.tableName("Test")
					.columns(new ArrayList<>())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors in building a SQLCreateStatement: \n" +
					"selectStmt or columns is required if creating a table!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderTableAllErrors(){
		try{
			stmt = SQLCreateStatement.builder()
					.table()
					.tableName(null)
					.as(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Encountered the following errors in building a SQLCreateStatement:\s
					name is required!
					selectStmt or columns is required if creating a table!""", e.getMessage());
		}
	}
	
	@Test
	public void testToStringDatabase(){
		assertEquals("CREATE " + SQLType.DATABASE + " " + databaseName, stmt.toString());
	}
	
	@Test
	public void testToStringTableAs(){
		String tableName = "Test";
		SQLSelectStatement selectStmt = SQLSelectStatement.builder()
				.fromTables(TableRef.builder().tableName("Derp").build())
				.build();
		stmt = SQLCreateStatement.builder()
				.table()
				.tableName(tableName)
				.as(selectStmt)
				.build();
		assertEquals("CREATE " + SQLType.TABLE + " " + tableName + " AS " + selectStmt, stmt.toString());
	}
	
	@Test
	public void testToStringTableColumns(){
		String tableName = "Test";
		ColumnDefinition col1 = ColumnDefinition.builder()
				.columnName("Derp")
				.bit()
				.defaultLength()
				.build();
		ColumnDefinition col2 = ColumnDefinition.builder()
				.columnName("Derp2")
				.bool()
				.build();
		stmt = SQLCreateStatement.builder()
				.table()
				.tableName(tableName)
				.columns(col1, col2)
				.build();
		assertEquals("CREATE " + SQLType.TABLE + " " + tableName + "(" + col1 + ", " + col2 + ")", stmt.toString());
	}
	
	@Test
	public void testToStringTableForeignKey(){
		String tableName = "Test";
		ColumnDefinition col1 = ColumnDefinition.builder()
				.columnName("Derp")
				.bit()
				.defaultLength()
				.build();
		ForeignKeyConstraint foreignKey = ForeignKeyConstraint.builder()
				.columnNames("Test")
				.references("Derp")
				.referenceColumnNames("Plop")
				.build();
		stmt = SQLCreateStatement.builder()
				.table()
				.tableName(tableName)
				.columns(col1)
				.foreignKey(foreignKey)
				.build();
		assertEquals("CREATE " + SQLType.TABLE + " " + tableName + "(" + col1 +") " + foreignKey, stmt.toString());
	}
	
	@Test
	public void testToStringTableForeignKeys(){
		String tableName = "Test";
		ColumnDefinition col1 = ColumnDefinition.builder()
				.columnName("Derp")
				.bit()
				.defaultLength()
				.build();
		ForeignKeyConstraint foreignKey = ForeignKeyConstraint.builder()
				.columnNames("Test")
				.references("Derp")
				.referenceColumnNames("Plop")
				.build();
		ForeignKeyConstraint foreignKey2 = ForeignKeyConstraint.builder()
				.columnNames("Yep")
				.references("Nope")
				.referenceColumnNames("Maybe")
				.build();
		stmt = SQLCreateStatement.builder()
				.table()
				.tableName(tableName)
				.columns(col1)
				.foreignKey(foreignKey)
				.foreignKey(foreignKey2)
				.build();
		assertEquals("CREATE " + SQLType.TABLE + " " + tableName + "(" + col1 +") " + foreignKey +
				" " + foreignKey2, stmt.toString());
	}
}
