package com.github.tadukoo.database.mysql;

import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.database.mysql.syntax.statement.SQLCreateStatement;
import com.github.tadukoo.database.mysql.syntax.statement.SQLDropStatement;
import com.github.tadukoo.database.mysql.syntax.statement.SQLSelectStatement;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class CommonResultSetConvertersTest extends DatabaseConnectionTest{
	private final String tableName = "Test";
	private final TableRef tableRef = TableRef.builder()
			.tableName(tableName)
			.build();
	private final ThrowingFunction<ResultSet, Integer, SQLException> simpleItemFunc = resultSet ->
			CommonResultSetConverters.simpleItem(resultSet, resultSet::getInt);
	private final ThrowingFunction<ResultSet, List<Integer>, SQLException> simpleListFunc = resultSet ->
			CommonResultSetConverters.simpleList(resultSet, resultSet::getInt);
	
	@BeforeEach
	public void setup() throws SQLException{
		// Drop the table if it exists
		assertTrue(db.executeUpdate("Drop Table Test",
				SQLDropStatement.builder()
						.table()
						.ifExists()
						.name(tableName)
						.build()
						.toString()));
	}
	
	@Test
	public void testCheck() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.integer()
								.defaultSize()
								.build())
						.build()
						.toString()));
		
		// Add a row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(42));
		
		// Run the check
		assertTrue(db.executeQuery("Check if Tests exist",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::check));
	}
	
	@Test
	public void testSimpleItem() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.integer()
								.defaultSize()
								.build())
						.build()
						.toString()));
		
		// Add a row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(42));
		
		assertEquals(42, db.executeQuery("Run simpleItem",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				simpleItemFunc));
	}
	
	@Test
	public void testSimpleItemMultipleRowsError() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.integer()
								.defaultSize()
								.build())
						.build()
						.toString()));
		
		// Add multiple row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(42));
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(35));
		
		try{
			db.executeQuery("Run simpleItem",
					SQLSelectStatement.builder()
							.fromTables(tableRef)
							.build()
							.toString(),
					simpleItemFunc);
			fail();
		}catch(SQLException e){
			SQLException ex = (SQLException) logger.getEntries().get(logger.getEntries().size()-2).throwable();
			assertEquals("Found multiple rows of results, expected only one!", ex.getMessage());
		}
	}
	
	@Test
	public void testSimpleList() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.integer()
								.defaultSize()
								.build())
						.build()
						.toString()));
		
		// Add multiple row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(42));
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(35));
		
		// Run the query
		List<Integer> results = db.executeQuery("Run simpleList",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				simpleListFunc);
		assertEquals(2, results.size());
		assertEquals(42, results.get(0));
		assertEquals(35, results.get(1));
	}
	
	@Test
	public void testSingleBoolean() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.bool()
								.build())
						.build()
						.toString()));
		
		// Add a row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(true));
		
		assertTrue(db.executeQuery("Run singleBoolean",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::singleBoolean));
	}
	
	@Test
	public void testBooleans() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.bool()
								.build())
						.build()
						.toString()));
		
		// Add multiple row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(true));
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(false));
		
		// Run the query
		List<Boolean> results = db.executeQuery("Run booleans",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::booleans);
		assertEquals(2, results.size());
		assertTrue(results.get(0));
		assertFalse(results.get(1));
	}
	
	@Test
	public void testSingleInteger() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.integer()
								.defaultSize()
								.build())
						.build()
						.toString()));
		
		// Add a row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(42));
		
		assertEquals(42, db.executeQuery("Run singleInteger",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::singleInteger));
	}
	
	@Test
	public void testIntegers() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.integer()
								.defaultSize()
								.build())
						.build()
						.toString()));
		
		// Add multiple row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(42));
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(35));
		
		// Run the query
		List<Integer> results = db.executeQuery("Run integers",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::integers);
		assertEquals(2, results.size());
		assertEquals(42, results.get(0));
		assertEquals(35, results.get(1));
	}
	
	@Test
	public void testSingleString() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.text()
								.defaultLength()
								.build())
						.build()
						.toString()));
		
		// Add a row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList("Derp"));
		
		assertEquals("Derp", db.executeQuery("Run singleString",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::singleString));
	}
	
	@Test
	public void testStrings() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.text()
								.defaultLength()
								.build())
						.build()
						.toString()));
		
		// Add multiple row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList("Derp"));
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList("Plop"));
		
		// Run the query
		List<String> results = db.executeQuery("Run strings",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::strings);
		assertEquals(2, results.size());
		assertEquals("Derp", results.get(0));
		assertEquals("Plop", results.get(1));
	}
	
	@Test
	public void testSingleTime() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.time()
								.defaultFractionalSecondsPrecision()
								.build())
						.build()
						.toString()));
		
		// Add a row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(Time.valueOf("16:20:00")));
		
		assertEquals(Time.valueOf("16:20:00"), db.executeQuery("Run singleTime",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::singleTime));
	}
	
	@Test
	public void testTimes() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.time()
								.defaultFractionalSecondsPrecision()
								.build())
						.build()
						.toString()));
		
		// Add multiple row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(Time.valueOf("16:20:00")));
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(Time.valueOf("00:00:00")));
		
		// Run the query
		List<Time> results = db.executeQuery("Run times",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::times);
		assertEquals(2, results.size());
		assertEquals(Time.valueOf("16:20:00"), results.get(0));
		assertEquals(Time.valueOf("00:00:00"), results.get(1));
	}
	
	@Test
	public void testSingleTimestamp() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.timestamp()
								.defaultFractionalSecondsPrecision()
								.build())
						.build()
						.toString()));
		
		// Add a row to the table
		db.insert(tableName, ListUtil.createList("id"),
				ListUtil.createList(Timestamp.valueOf("2020-09-05 20:01:00")));
		// Timestamp for when Tadukoo Util Alpha v.0.1 was released ^
		
		assertEquals(Timestamp.valueOf("2020-09-05 20:01:00"), db.executeQuery("Run singleTimestamp",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::singleTimestamp));
	}
	
	@Test
	public void testTimestamps() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.timestamp()
								.defaultFractionalSecondsPrecision()
								.build())
						.build()
						.toString()));
		
		// Add multiple row to the table
		db.insert(tableName, ListUtil.createList("id"),
				ListUtil.createList(Timestamp.valueOf("2020-09-05 20:01:00")));
		db.insert(tableName, ListUtil.createList("id"),
				ListUtil.createList(Timestamp.valueOf("2020-08-14 20:08:00")));
		// Timestamp for the post "The Tadukooverse Master Plan" ^
		
		// Run the query
		List<Timestamp> results = db.executeQuery("Run timestamps",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::timestamps);
		assertEquals(2, results.size());
		assertEquals(Timestamp.valueOf("2020-09-05 20:01:00"), results.get(0));
		assertEquals(Timestamp.valueOf("2020-08-14 20:08:00"), results.get(1));
	}
	
	@Test
	public void testSingleDate() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.date()
								.build())
						.build()
						.toString()));
		
		// Add a row to the table
		db.insert(tableName, ListUtil.createList("id"),
				ListUtil.createList(Date.valueOf("2020-09-05")));
		
		assertEquals(Date.valueOf("2020-09-05"), db.executeQuery("Run singleDate",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::singleDate));
	}
	
	@Test
	public void testDates() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.date()
								.build())
						.build()
						.toString()));
		
		// Add multiple row to the table
		db.insert(tableName, ListUtil.createList("id"),
				ListUtil.createList(Date.valueOf("2020-09-05")));
		db.insert(tableName, ListUtil.createList("id"),
				ListUtil.createList(Date.valueOf("2020-08-14")));
		
		// Run the query
		List<Date> results = db.executeQuery("Run dates",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::dates);
		assertEquals(2, results.size());
		assertEquals(Date.valueOf("2020-09-05"), results.get(0));
		assertEquals(Date.valueOf("2020-08-14"), results.get(1));
	}
	
	@Test
	public void testSingleShort() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.tinyint()
								.defaultSize()
								.build())
						.build()
						.toString()));
		
		// Add a row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList((short) 2));
		
		assertEquals((short) 2, db.executeQuery("Run singleShort",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::singleShort));
	}
	
	@Test
	public void testShorts() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.tinyint()
								.defaultSize()
								.build())
						.build()
						.toString()));
		
		// Add multiple row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList((short) 4));
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList((short) 2));
		
		// Run the query
		List<Short> results = db.executeQuery("Run shorts",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::shorts);
		assertEquals(2, results.size());
		assertEquals((short) 4, results.get(0));
		assertEquals((short) 2, results.get(1));
	}
	
	@Test
	public void testSingleLong() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.bigint()
								.defaultSize()
								.build())
						.build()
						.toString()));
		
		// Add a row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(42L));
		
		assertEquals(42L, db.executeQuery("Run singleLong",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::singleLong));
	}
	
	@Test
	public void testLongs() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.bigint()
								.defaultSize()
								.build())
						.build()
						.toString()));
		
		// Add multiple row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(42L));
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(35L));
		
		// Run the query
		List<Long> results = db.executeQuery("Run longs",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::longs);
		assertEquals(2, results.size());
		assertEquals(42L, results.get(0));
		assertEquals(35L, results.get(1));
	}
	
	@Test
	public void testSingleFloat() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.floatType()
								.defaultSizeAndDigits()
								.build())
						.build()
						.toString()));
		
		// Add a row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(42f));
		
		assertEquals(42f, db.executeQuery("Run singleFloat",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::singleFloat));
	}
	
	@Test
	public void testFloats() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.floatType()
								.defaultSizeAndDigits()
								.build())
						.build()
						.toString()));
		
		// Add multiple row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(42f));
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(35f));
		
		// Run the query
		List<Float> results = db.executeQuery("Run floats",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::floats);
		assertEquals(2, results.size());
		assertEquals(42f, results.get(0));
		assertEquals(35f, results.get(1));
	}
	
	@Test
	public void testSingleDouble() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.doubleType()
								.defaultSizeAndDigits()
								.build())
						.build()
						.toString()));
		
		// Add a row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(42d));
		
		assertEquals(42d, db.executeQuery("Run singleDouble",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::singleDouble));
	}
	
	@Test
	public void testDoubles() throws SQLException{
		// Create the table
		assertTrue(db.executeUpdate("Create Table Test",
				SQLCreateStatement.builder()
						.table()
						.tableName(tableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.doubleType()
								.defaultSizeAndDigits()
								.build())
						.build()
						.toString()));
		
		// Add multiple row to the table
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(42d));
		db.insert(tableName, ListUtil.createList("id"), ListUtil.createList(35d));
		
		// Run the query
		List<Double> results = db.executeQuery("Run doubles",
				SQLSelectStatement.builder()
						.fromTables(tableRef)
						.build()
						.toString(),
				CommonResultSetConverters::doubles);
		assertEquals(2, results.size());
		assertEquals(42d, results.get(0));
		assertEquals(35d, results.get(1));
	}
}
