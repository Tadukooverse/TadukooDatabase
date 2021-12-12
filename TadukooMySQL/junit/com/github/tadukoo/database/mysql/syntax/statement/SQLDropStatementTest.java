package com.github.tadukoo.database.mysql.syntax.statement;

import com.github.tadukoo.database.mysql.syntax.SQLType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SQLDropStatementTest{
	private SQLDropStatement stmt;
	private String databaseName;
	private String tableName;
	
	@BeforeEach
	public void setup(){
		databaseName = "Test";
		tableName = "Derp";
		stmt = SQLDropStatement.builder()
				.database()
				.name(databaseName)
				.build();
	}
	
	@Test
	public void testBuilderSetTypeDatabase(){
		assertEquals(SQLType.DATABASE, stmt.getType());
	}
	
	@Test
	public void testBuilderDefaultIfExistsDatabase(){
		assertFalse(stmt.getIfExists());
	}
	
	@Test
	public void testBuilderSetIfExistsDatabase(){
		stmt = SQLDropStatement.builder()
				.database()
				.ifExists()
				.name(databaseName)
				.build();
		assertTrue(stmt.getIfExists());
	}
	
	@Test
	public void testBuilderSetDatabaseName(){
		assertEquals(databaseName, stmt.getName());
	}
	
	@Test
	public void testBuilderMissingDatabaseName(){
		try{
			stmt = SQLDropStatement.builder()
					.database()
					.name(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors in building a " +
					"SQLDropStatement: \nname is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderSetTypeTable(){
		stmt = SQLDropStatement.builder()
				.table()
				.name(tableName)
				.build();
		assertEquals(SQLType.TABLE, stmt.getType());
	}
	
	@Test
	public void testBuilderTableDefaultIfExists(){
		stmt = SQLDropStatement.builder()
				.table()
				.name(tableName)
				.build();
		assertFalse(stmt.getIfExists());
	}
	
	@Test
	public void testBuilderTableSetIfExists(){
		stmt = SQLDropStatement.builder()
				.table()
				.ifExists()
				.name(tableName)
				.build();
		assertTrue(stmt.getIfExists());
	}
	
	@Test
	public void testBuilderSetTableName(){
		stmt = SQLDropStatement.builder()
				.table()
				.name(tableName)
				.build();
		assertEquals(tableName, stmt.getName());
	}
	
	@Test
	public void testBuilderMissingTableName(){
		try{
			stmt = SQLDropStatement.builder()
					.table()
					.name(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors in building a " +
					"SQLDropStatement: \nname is required!", e.getMessage());
		}
	}
	
	@Test
	public void testToStringDatabase(){
		assertEquals("DROP " + SQLType.DATABASE + " " + databaseName, stmt.toString());
	}
	
	@Test
	public void testToStringDatabaseIfExists(){
		stmt = SQLDropStatement.builder()
				.database()
				.ifExists()
				.name(databaseName)
				.build();
		assertEquals("DROP " + SQLType.DATABASE + " IF EXISTS " + databaseName, stmt.toString());
	}
	
	@Test
	public void testToStringTable(){
		stmt = SQLDropStatement.builder()
				.table()
				.name(tableName)
				.build();
		assertEquals("DROP " + SQLType.TABLE + " " + tableName, stmt.toString());
	}
	
	@Test
	public void testToStringTableIfExists(){
		stmt = SQLDropStatement.builder()
				.table()
				.ifExists()
				.name(tableName)
				.build();
		assertEquals("DROP " + SQLType.TABLE + " IF EXISTS " + tableName, stmt.toString());
	}
}
