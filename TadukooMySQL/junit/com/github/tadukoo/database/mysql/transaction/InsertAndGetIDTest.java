package com.github.tadukoo.database.mysql.transaction;

import com.github.tadukoo.database.mysql.DatabaseConnectionTest;
import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.statement.SQLCreateStatement;
import com.github.tadukoo.database.mysql.syntax.statement.SQLDropStatement;
import com.github.tadukoo.database.mysql.transaction.update.Updates;
import com.github.tadukoo.util.junit.logger.JUnitEasyLogger;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class InsertAndGetIDTest extends DatabaseConnectionTest{
	private final String table = "Test";
	private final String idCol = "id";
	private final String otherCol = "other_col";
	private InsertAndGetID insertAndGetID;
	
	@BeforeEach
	public void setup() throws SQLException{
		insertAndGetID = InsertAndGetID.createInsertAndGetID(table, idCol, ListUtil.createList(otherCol),
				ListUtil.createList(42));
		
		// Drop the table between each
		db.executeTransaction(Updates.createUpdates("Drop Test table",
				null, ListUtil.createList(
						SQLDropStatement.builder()
								.table()
								.ifExists()
								.name(table)
								.build()
								.toString()
				)));
	}
	
	@Test
	public void testGetTransactionName(){
		assertEquals("Insert " + table + " and Get ID", insertAndGetID.getTransactionName());
	}
	
	@Test
	public void testGetInsertString(){
		assertEquals("Executed Insert of a " + table + "!", insertAndGetID.getInsertString());
	}
	
	@Test
	public void testGetInsertSQL(){
		assertEquals("INSERT INTO " + table + " (" + otherCol + ") VALUES (42)", insertAndGetID.getInsertSQL());
	}
	
	@Test
	public void testGetSelectString(){
		assertEquals("Pulled out " + idCol + " of just inserted " + table + "!",
				insertAndGetID.getSelectString());
	}
	
	@Test
	public void testGetSelectSQL(){
		assertEquals("SELECT DISTINCT " + idCol + " FROM " + table + " WHERE " + otherCol + " = 42",
				insertAndGetID.getSelectSQL());
	}
	
	@Test
	public void testWrongColValSize(){
		try{
			insertAndGetID = InsertAndGetID.createInsertAndGetID(table, idCol, ListUtil.createList(otherCol, otherCol),
					ListUtil.createList(42));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Cols and Values don't match up!", e.getMessage());
		}
	}
	
	@Test
	public void testDefaultInsertString(){
		insertAndGetID = new InsertAndGetID(){
			@Override
			public String getInsertSQL(){
				return null;
			}
			
			@Override
			public String getSelectSQL(){
				return null;
			}
			
			@Override
			public String getTransactionName(){
				return null;
			}
		};
		assertNull(insertAndGetID.getInsertString());
	}
	
	@Test
	public void testDefaultSelectString(){
		insertAndGetID = new InsertAndGetID(){
			@Override
			public String getInsertSQL(){
				return null;
			}
			
			@Override
			public String getSelectSQL(){
				return null;
			}
			
			@Override
			public String getTransactionName(){
				return null;
			}
		};
		assertNull(insertAndGetID.getSelectString());
	}
	
	@Test
	public void testExecute() throws SQLException{
		// Setup a table to insert into
		db.executeTransaction(Updates.createUpdates("Create Test table",
				null, ListUtil.createList(
						SQLCreateStatement.builder()
								.table()
								.tableName(table)
								.columns(
										ColumnDefinition.builder()
												.columnName(idCol)
												.integer()
												.defaultSize()
												.notNull()
												.autoIncrement()
												.primaryKey()
												.build(),
										ColumnDefinition.builder()
												.columnName(otherCol)
												.integer()
												.defaultSize()
												.build()
								)
								.build()
								.toString()
				)));
		
		// Do the insert and get id
		assertEquals(1, db.executeTransaction(insertAndGetID));
		
		// Verify some log entries
		List<JUnitEasyLogger.JUnitEasyLoggerEntry> entries = logger.getEntries();
		JUnitEasyLogger.assertEntries(
				ListUtil.createList(
						new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO, "Starting execution of " +
								insertAndGetID.getTransactionName(), null),
						new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO, insertAndGetID.getInsertString(),
								null),
						new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO, insertAndGetID.getSelectString(),
								null),
						new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO, "Finished execution of " +
								insertAndGetID.getTransactionName(), null)
				),
				ListUtil.createList(
						entries.get(entries.size()-4), entries.get(entries.size()-3),
						entries.get(entries.size()-2), entries.get(entries.size()-1)
				)
		);
	}
	
	@Test
	public void testExecuteNoInsertOrSelectStrings() throws SQLException{
		// Setup a table to insert into
		db.executeTransaction(Updates.createUpdates("Create Test table",
				null, ListUtil.createList(
						SQLCreateStatement.builder()
								.table()
								.tableName(table)
								.columns(
										ColumnDefinition.builder()
												.columnName(idCol)
												.integer()
												.defaultSize()
												.notNull()
												.autoIncrement()
												.primaryKey()
												.build(),
										ColumnDefinition.builder()
												.columnName(otherCol)
												.integer()
												.defaultSize()
												.build()
								)
								.build()
								.toString()
				)));
		
		// Do the insert and get id
		String insertSQL = insertAndGetID.getInsertSQL();
		String selectSQL = insertAndGetID.getSelectSQL();
		insertAndGetID = new InsertAndGetID(){
			@Override
			public String getInsertSQL(){
				return insertSQL;
			}
			
			@Override
			public String getSelectSQL(){
				return selectSQL;
			}
			
			@Override
			public String getTransactionName(){
				return "Yep";
			}
		};
		assertEquals(1, db.executeTransaction(insertAndGetID));
		
		// Verify some log entries
		List<JUnitEasyLogger.JUnitEasyLoggerEntry> entries = logger.getEntries();
		JUnitEasyLogger.assertEntries(
				ListUtil.createList(
						new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO, "Starting execution of " +
								insertAndGetID.getTransactionName(), null),
						new JUnitEasyLogger.JUnitEasyLoggerEntry(Level.INFO, "Finished execution of " +
								insertAndGetID.getTransactionName(), null)
				),
				ListUtil.createList(
						entries.get(entries.size()-2), entries.get(entries.size()-1)
				)
		);
	}
}
