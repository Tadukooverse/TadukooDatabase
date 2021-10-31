package com.github.tadukoo.database.mysql.syntax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SQLDropDatabaseStatementTest{
	private SQLDropDatabaseStatement stmt;
	private String databaseName;
	
	@BeforeEach
	public void setup(){
		databaseName = "Test";
		stmt = SQLDropDatabaseStatement.builder()
				.databaseName(databaseName)
				.build();
	}
	
	@Test
	public void testBuilder(){
		assertEquals(databaseName, stmt.getDatabaseName());
	}
	
	@Test
	public void testBuilderMissingDatabaseName(){
		try{
			stmt = SQLDropDatabaseStatement.builder()
					.databaseName(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors in building a " +
					"SQLDropDatabaseStatement: \ndatabaseName is required!", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals("DROP DATABASE " + databaseName, stmt.toString());
	}
}
