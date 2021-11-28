package com.github.tadukoo.database.mysql;

import com.github.tadukoo.junit.logger.JUnitEasyLogger;
import com.github.tadukoo.util.logger.EasyLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DatabaseTest{
	private Database database;
	private final EasyLogger logger = new JUnitEasyLogger();
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
}
