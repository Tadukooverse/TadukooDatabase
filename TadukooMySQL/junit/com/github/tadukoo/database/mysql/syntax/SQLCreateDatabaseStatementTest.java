package com.github.tadukoo.database.mysql.syntax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SQLCreateDatabaseStatementTest{
	private SQLCreateDatabaseStatement stmt;
	private String databaseName;
	
	@BeforeEach
	public void setup(){
		databaseName = "Test";
		stmt = SQLCreateDatabaseStatement.builder()
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
			stmt = SQLCreateDatabaseStatement.builder()
					.databaseName(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors in building a " +
					"SQLCreateDatabaseStatement: \ndatabaseName is required!", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals("CREATE DATABASE " + databaseName, stmt.toString());
	}
}
