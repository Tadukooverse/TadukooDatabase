package com.github.tadukoo.database.mysql.update;

import com.github.tadukoo.database.mysql.Database;
import com.github.tadukoo.database.mysql.syntax.statement.SQLCreateStatement;
import com.github.tadukoo.database.mysql.syntax.statement.SQLDropStatement;
import com.github.tadukoo.database.mysql.transaction.update.Updates;
import com.github.tadukoo.junit.logger.JUnitEasyLogger;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class UpdatesTest{
	private JUnitEasyLogger logger;
	private final String databaseName = "TadukooDatabaseTest";
	private String createDBString;
	private Updates updates;
	
	@BeforeEach
	public void setup(){
		logger = new JUnitEasyLogger();
		createDBString = SQLCreateStatement.builder()
				.database()
				.databaseName(databaseName)
				.build()
				.toString();
		updates = Updates.createUpdates("Create Database",
				ListUtil.createList("Create Database " + databaseName),
				ListUtil.createList(createDBString));
	}
	
	private Database setupDB(){
		return Database.builder()
				.logger(logger)
				.host("localhost")
				.username("root")
				.password("")
				.build();
	}
	
	@Test
	public void testUpdatesGetTransactionName(){
		assertEquals("Create Database", updates.getTransactionName());
	}
	
	@Test
	public void testUpdatesGetNames(){
		List<String> names = updates.getNames();
		assertEquals(1, names.size());
		assertEquals("Create Database " + databaseName, names.get(0));
	}
	
	@Test
	public void testUpdatesDefaultNames(){
		updates = new Updates(){
			@Override
			public List<String> getSQLs(){
				return null;
			}
			
			@Override
			public String getTransactionName(){
				return null;
			}
		};
		assertNull(updates.getNames());
	}
	
	@Test
	public void testUpdatesGetSQLs(){
		List<String> sqls = updates.getSQLs();
		assertEquals(1, sqls.size());
		assertEquals(createDBString, sqls.get(0));
	}
	
	@Test
	public void testUpdates() throws SQLException{
		Database db = setupDB();
		
		assertTrue(db.executeTransaction(updates));
		JUnitEasyLogger.assertEntries(ListUtil.createList(
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Starting Create Database transaction", null),
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Starting Create Database " + databaseName + " statement", null),
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Finished Create Database " + databaseName + " statement", null),
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Finished Create Database transaction", null)
		), logger);
		
		updates = Updates.createUpdates("Drop Database",
				ListUtil.createList("Drop Database " + databaseName),
				ListUtil.createList(SQLDropStatement.builder()
						.database()
						.name(databaseName)
						.build()
						.toString()));
		
		assertTrue(db.executeTransaction(updates));
		
		JUnitEasyLogger.assertEntries(ListUtil.createList(
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Starting Create Database transaction", null),
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Starting Create Database " + databaseName + " statement", null),
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Finished Create Database " + databaseName + " statement", null),
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Finished Create Database transaction", null),
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Starting Drop Database transaction", null),
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Starting Drop Database " + databaseName + " statement", null),
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Finished Drop Database " + databaseName + " statement", null),
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Finished Drop Database transaction", null)
		), logger);
	}
	
	@Test
	public void testUpdatesNoNames() throws SQLException{
		Database db = setupDB();
		
		updates = Updates.createUpdates("Create Database", null,
				ListUtil.createList(createDBString));
		
		assertTrue(db.executeTransaction(updates));
		JUnitEasyLogger.assertEntries(ListUtil.createList(
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Starting Create Database transaction", null),
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Finished Create Database transaction", null)
		), logger);
		
		updates = Updates.createUpdates("Drop Database", null,
				ListUtil.createList(SQLDropStatement.builder()
						.database()
						.name(databaseName)
						.build()
						.toString()));
		
		assertTrue(db.executeTransaction(updates));
		
		JUnitEasyLogger.assertEntries(ListUtil.createList(
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Starting Create Database transaction", null),
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Finished Create Database transaction", null),
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Starting Drop Database transaction", null),
				new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO,
						"Finished Drop Database transaction", null)
		), logger);
	}
	
	@Test
	public void testUpdatesBadNames() throws SQLException{
		updates = Updates.createUpdates("Create Database",
				ListUtil.createList("Create Database", "Something Else"),
				ListUtil.createList(createDBString));
		
		Database db = setupDB();
		
		try{
			db.executeTransaction(updates);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must have all sql statements named or pass null names list!", e.getMessage());
		}
	}
}
