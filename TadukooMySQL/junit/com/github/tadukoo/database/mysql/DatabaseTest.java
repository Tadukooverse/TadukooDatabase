package com.github.tadukoo.database.mysql;

import com.github.tadukoo.database.mysql.syntax.statement.SQLDropStatement;
import com.github.tadukoo.database.mysql.transaction.update.Updates;
import com.github.tadukoo.util.junit.logger.JUnitEasyLogger;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DatabaseTest{
	private Database database;
	private final JUnitEasyLogger logger = new JUnitEasyLogger();
	private final String host = "localhost";
	private final String username = "root";
	private final String password = "";
	
	@BeforeEach
	public void setup(){
		database = Database.builder()
				.logger(logger)
				.host(host)
				.username(username)
				.password(password)
				.build();
	}
	
	@Test
	public void testSetLogger(){
		assertEquals(logger, database.getLogger());
	}
	
	@Test
	public void testConnectionURL(){
		assertEquals("jdbc:mysql://" + host + ":3306", database.getConnectionURL());
	}
	
	@Test
	public void testConnectionURLSetPort(){
		database = Database.builder()
				.logger(logger)
				.host(host)
				.port(3307)
				.username(username)
				.password(password)
				.build();
		assertEquals("jdbc:mysql://" + host + ":3307", database.getConnectionURL());
	}
	
	@Test
	public void testConnectionURLSetDatabaseName(){
		database = Database.builder()
				.logger(logger)
				.host(host)
				.databaseName("Test")
				.username(username)
				.password(password)
				.build();
		assertEquals("jdbc:mysql://" + host + ":3306/Test", database.getConnectionURL());
	}
	
	@Test
	public void testConnectionURLSetAll(){
		database = Database.builder()
				.logger(logger)
				.host(host)
				.port(3307)
				.databaseName("Test")
				.username(username)
				.password(password)
				.build();
		assertEquals("jdbc:mysql://" + host + ":3307/Test", database.getConnectionURL());
	}
	
	@Test
	public void testDefaultMaxAttempts(){
		assertEquals(10, database.getMaxAttempts());
	}
	
	@Test
	public void testSetMaxAttempts(){
		database = Database.builder()
				.logger(logger)
				.host(host)
				.username(username)
				.password(password)
				.maxAttempts(11)
				.build();
		assertEquals(11, database.getMaxAttempts());
	}
	
	@Test
	public void testBuilderMissingLogger(){
		try{
			database = Database.builder()
					.logger(null)
					.host(host)
					.username(username)
					.password(password)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered errors in building a Database: \n" +
					"logger is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingHost(){
		try{
			database = Database.builder()
					.logger(logger)
					.host(null)
					.username(username)
					.password(password)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered errors in building a Database: \n" +
					"host is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingUsername(){
		try{
			database = Database.builder()
					.logger(logger)
					.host(host)
					.username(null)
					.password(password)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered errors in building a Database: \n" +
					"username is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingPassword(){
		try{
			database = Database.builder()
					.logger(logger)
					.host(host)
					.username(username)
					.password(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered errors in building a Database: \n" +
					"password is required! (empty string is allowed for a blank password)", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderAllErrors(){
		try{
			database = Database.builder()
					.logger(null)
					.host(null)
					.username(null)
					.password(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Encountered errors in building a Database:\s
					logger is required!
					host is required!
					username is required!
					password is required! (empty string is allowed for a blank password)""", e.getMessage());
		}
	}
	
	@Test
	public void testFailedTransaction(){
		try{
			database = Database.builder()
					.logger(logger)
					.host(host)
					.username(username)
					.password(password)
					.maxAttempts(2)
					.build();
			database.executeTransaction(Updates.createUpdates("Drop Garbage", null,
					ListUtil.createList(
							SQLDropStatement.builder()
									.database()
									.name("SomethingStupidGarbageNotReal")
									.build()
									.toString()
					)));
			fail();
		}catch(SQLException e){
			assertEquals("Failed to execute transaction after 2 attempts", e.getMessage());
			/* TODO: Someday improve Tadukoo JUnit to actually allow this logger check
			SQLTransientConnectionException ex =
					new SQLTransientConnectionException("(conn=345) Can't drop database " +
							"'somethingstupidgarbagenotreal'; database doesn't exist");
			JUnitEasyLogger.assertEntries(
					ListUtil.createList(
							new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
									"Starting Drop Garbage transaction", null),
							new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.SEVERE,
									"Failed to execute Drop Garbage", ex),
							new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
									"Starting Drop Garbage", null),
							new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.SEVERE,
									"Failed to execute Drop Garbage", ex),
							new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.SEVERE,
									"Failed to execute transaction after 2 attempts", null)
					),
					logger
			);
			/**/
		}
	}
}
