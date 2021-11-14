package com.github.tadukoo.database.mysql.syntax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SQLAlterStatementTest{
	private SQLType type;
	private String tableName;
	private SQLColumnOperation operation;
	private String columnName;
	private ColumnDefinition columnDef;
	private SQLAlterStatement stmt;
	
	@BeforeEach
	public void setup(){
		type = SQLType.TABLE;
		tableName = "Test";
		operation = SQLColumnOperation.ADD;
		columnName = "Derp";
		columnDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bool()
				.build();
		stmt = SQLAlterStatement.builder()
				.table()
				.tableName(tableName)
				.add()
				.columnDef(columnDef)
				.build();
	}
	
	@Test
	public void testBuilderTableType(){
		assertEquals(type, stmt.getType());
	}
	
	@Test
	public void testBuilderSetTableName(){
		assertEquals(tableName, stmt.getTableName());
	}
	
	@Test
	public void testBuilderAddOperation(){
		assertEquals(operation, stmt.getOperation());
	}
	
	@Test
	public void testBuilderModifyOperation(){
		stmt = SQLAlterStatement.builder()
				.table()
				.tableName(tableName)
				.modify()
				.columnDef(columnDef)
				.build();
		assertEquals(SQLColumnOperation.MODIFY, stmt.getOperation());
	}
	
	@Test
	public void testBuilderDropOperation(){
		stmt = SQLAlterStatement.builder()
				.table()
				.tableName(tableName)
				.drop()
				.columnName(columnName)
				.build();
		assertEquals(SQLColumnOperation.DROP, stmt.getOperation());
	}
	
	@Test
	public void testBuilderSetColumnName(){
		stmt = SQLAlterStatement.builder()
				.table()
				.tableName(tableName)
				.drop()
				.columnName(columnName)
				.build();
		assertEquals(columnName, stmt.getColumnName());
	}
	
	@Test
	public void testBuilderSetColumnDef(){
		assertEquals(columnDef, stmt.getColumnDef());
	}
	
	@Test
	public void testBuilderMissingTableName(){
		try{
			stmt = SQLAlterStatement.builder()
					.table()
					.tableName(null)
					.add()
					.columnDef(columnDef)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered errors in building a SQLAlterStatement: \n" +
					"tableName is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingColumnName(){
		try{
			stmt = SQLAlterStatement.builder()
					.table()
					.tableName(tableName)
					.drop()
					.columnName(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered errors in building a SQLAlterStatement: \n" +
					"columnName or columnDef must be specified!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingColumnDef(){
		try{
			stmt = SQLAlterStatement.builder()
					.table()
					.tableName(tableName)
					.add()
					.columnDef(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered errors in building a SQLAlterStatement: \n" +
					"columnName or columnDef must be specified!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderAllErrors(){
		try{
			stmt = SQLAlterStatement.builder()
					.table()
					.tableName(null)
					.add()
					.columnDef(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Encountered errors in building a SQLAlterStatement:\s
					tableName is required!
					columnName or columnDef must be specified!""", e.getMessage());
		}
	}
	
	@Test
	public void testToStringAdd(){
		assertEquals("ALTER " + type + " " + tableName + " " + operation + " " + columnDef, stmt.toString());
	}
	
	@Test
	public void testToStringModify(){
		stmt = SQLAlterStatement.builder()
				.table()
				.tableName(tableName)
				.modify()
				.columnDef(columnDef)
				.build();
		assertEquals("ALTER " + type + " " + tableName + " " + SQLColumnOperation.MODIFY + " " + columnDef,
				stmt.toString());
	}
	
	@Test
	public void testToStringDrop(){
		stmt = SQLAlterStatement.builder()
				.table()
				.tableName(tableName)
				.drop()
				.columnName(columnName)
				.build();
		assertEquals("ALTER " + type + " " + tableName + " " + SQLColumnOperation.DROP + " " + columnName,
				stmt.toString());
	}
}
