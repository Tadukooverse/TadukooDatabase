package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.database.mysql.Database;
import com.github.tadukoo.database.mysql.DatabaseConnectionTest;
import com.github.tadukoo.database.mysql.syntax.conditional.ConditionalStatement;
import com.github.tadukoo.database.mysql.syntax.conditional.SQLOperator;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.database.mysql.syntax.statement.SQLCreateStatement;
import com.github.tadukoo.database.mysql.syntax.statement.SQLDropStatement;
import com.github.tadukoo.database.mysql.syntax.statement.SQLSelectStatement;
import com.github.tadukoo.util.ByteUtil;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class SQLSyntaxUtilTest extends DatabaseConnectionTest{
	
	@Test
	public void testConvertValueToStringWithString(){
		assertEquals("'Test'", SQLSyntaxUtil.convertValueToString("Test"));
	}
	
	@Test
	public void testConvertValueToStringWithInt(){
		assertEquals("42", SQLSyntaxUtil.convertValueToString(42));
	}
	
	@Test
	public void testConvertValueToStringWithBoolean(){
		assertEquals("true", SQLSyntaxUtil.convertValueToString(true));
	}
	
	@Test
	public void testConvertValueToStringWithByteArray(){
		assertEquals("0xA4D1", SQLSyntaxUtil.convertValueToString(ByteUtil.fromHex("A4D1")));
	}
	
	@Test
	public void testConvertValueToStringWithCharacter(){
		assertEquals("'F'", SQLSyntaxUtil.convertValueToString('F'));
	}
	
	@Test
	public void testConvertValueToStringWithTime(){
		assertEquals("'16:20:00'", SQLSyntaxUtil.convertValueToString(Time.valueOf("16:20:00")));
	}
	
	@Test
	public void testConvertValueToStringWithDate(){
		assertEquals("'2020-09-05'", SQLSyntaxUtil.convertValueToString(Date.valueOf("2020-09-05")));
	}
	
	@Test
	public void testConvertValueToStringWithTimestamp(){
		assertEquals("'2020-09-05 20:01:00.0'",
				SQLSyntaxUtil.convertValueToString(Timestamp.valueOf("2020-09-05 20:01:00")));
	}
	
	@Test
	public void testConvertValueToStringWithTableRef(){
		TableRef table = TableRef.builder().tableName("Derp").build();
		assertEquals("Derp", SQLSyntaxUtil.convertValueToString(table));
	}
	
	/**
	 * Tests the {@link SQLSyntaxUtil#getValueBasedOnColumnDefinition(ResultSet, ColumnDefinition)} method
	 * using the given {@link ColumnDefinition} and value
	 *
	 * @param columnDef The {@link ColumnDefinition} to use for testing
	 * @param value The value to be inserted into and retrieved from the {@link Database}
	 * @throws SQLException If anything goes wrong
	 */
	private void testExtractValueFromResultSet(ColumnDefinition columnDef, Object value) throws SQLException{
		// Create the Table
		String tableName = "TestResultSet";
		db.executeUpdate("Create Table", SQLCreateStatement.builder()
				.table()
				.tableName(tableName)
				.columns(columnDef)
				.build()
				.toString());
		
		// Add a row to the table with the value
		db.insert(tableName, ListUtil.createList(columnDef.getColumnName()), ListUtil.createList(value));
		
		// Create the query
		String query = SQLSelectStatement.builder()
				.returnColumns(ColumnRef.builder()
						.columnName(columnDef.getColumnName())
						.build())
				.fromTables(TableRef.builder()
						.tableName(tableName)
						.build())
				.build()
				.toString();
		
		// Run the query
		Object actualValue = db.executeQuery("Test grab value", query, resultSet -> {
			resultSet.next();
			return SQLSyntaxUtil.getValueBasedOnColumnDefinition(resultSet, columnDef);
		});
		
		// Drop the table
		db.executeUpdate("Drop Table", SQLDropStatement.builder()
				.table()
				.name(tableName)
				.build()
				.toString());
		
		// Check the value
		if(value instanceof byte[] b){
			assertArrayEquals(b, (byte[]) actualValue);
		}else{
			assertEquals(value, actualValue);
		}
	}
	
	@Test
	public void testExtractValueFromResultSetCHAR() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
				.columnName("Test")
				.character()
				.defaultLength()
				.build(),
				'T');
	}
	
	@Test
	public void testExtractValueFromResultSetVARCHAR() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
				.columnName("Test")
				.varchar()
				.length(5)
				.build(),
				"Testy");
	}
	
	@Test
	public void testExtractValueFromResultSetTINYTEXT() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
				.columnName("Test")
				.tinytext()
				.build(),
				"Derp");
	}
	
	@Test
	public void testExtractValueFromResultSetTEXT() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
				.columnName("Test")
				.text()
				.length(5)
				.build(),
				"Testy");
	}
	
	@Test
	public void testExtractValueFromResultSetMEDIUMTEXT() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.mediumtext()
						.build(),
				"Testy");
	}
	
	@Test
	public void testExtractValueFromResultSetLONGTEXT() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.longtext()
						.build(),
				"Testy");
	}
	
	@Test
	public void testExtractValueFromResultSetENUM() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
				.columnName("Test")
				.enumeration()
				.values("Testy", "Derp")
				.build(),
				"Testy");
	}
	
	@Test
	public void testExtractValueFromResultSetSET() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
				.columnName("Test")
				.set()
				.values("Testy", "Derp")
				.build(),
				"Testy");
	}
	
	@Test
	public void testExtractValueFromResultSetBINARY() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.binary()
						.length(4)
						.build(),
				"Test".getBytes());
	}
	
	@Test
	public void testExtractValueFromResultSetVARBINARY() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
				.columnName("Test")
				.varbinary()
				.length(10)
				.build(),
				"Test".getBytes());
	}
	
	@Test
	public void testExtractValueFromResultSetTINYBLOB() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.tinyblob()
						.build(),
				"Test".getBytes());
	}
	
	@Test
	public void testExtractValueFromResultSetBLOB() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.blob()
						.length(10)
						.build(),
				"Test".getBytes());
	}
	
	@Test
	public void testExtractValueFromResultSetMEDIUMBLOB() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.mediumblob()
						.build(),
				"Test".getBytes());
	}
	
	@Test
	public void testExtractValueFromResultSetLONGBLOB() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.longblob()
						.build(),
				"Test".getBytes());
	}
	
	@Test
	public void testExtractValueFromResultSetBIT() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.bit()
						.length(32)
						.build(),
				"Test".getBytes());
	}
	
	@Test
	public void testExtractValueFromResultSetTINYINT() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
				.columnName("Test")
				.tinyint()
				.defaultSize()
				.build(),
				42);
	}
	
	@Test
	public void testExtractValueFromResultSetSMALLINT() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.smallint()
						.defaultSize()
						.build(),
				42);
	}
	
	@Test
	public void testExtractValueFromResultSetMEDIUMINT() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.mediumint()
						.defaultSize()
						.build(),
				42);
	}
	
	@Test
	public void testExtractValueFromResultSetBOOLEAN() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
				.columnName("Test")
				.bool()
				.build(),
				true);
	}
	
	@Test
	public void testExtractValueFromResultSetINTEGER() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.integer()
						.defaultSize()
						.build(),
				42);
	}
	
	@Test
	public void testExtractValueFromResultSetINTEGER_UNSIGNED() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.integer()
						.defaultSize()
						.unsigned()
						.build(),
				42L);
	}
	
	@Test
	public void testExtractValueFromResultSetBIGINT() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.bigint()
						.defaultSize()
						.build(),
				42L);
	}
	
	@Test
	public void testExtractValueFromResultSetBIGINT_UNSIGNED() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.bigint()
						.defaultSize()
						.unsigned()
						.build(),
				new BigInteger("42"));
	}
	
	@Test
	public void testExtractValueFromResultSetFLOAT() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.floatType()
						.defaultSizeAndDigits()
						.build(),
				42.0f);
	}
	
	@Test
	public void testExtractValueFromResultSetDOUBLE() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.doubleType()
						.defaultSizeAndDigits()
						.build(),
				42.0d);
	}
	
	@Test
	public void testExtractValueFromResultSetDECIMAL() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.decimal()
						.defaultSizeAndDigits()
						.build(),
				new BigDecimal("42"));
	}
	
	@Test
	public void testExtractValueFromResultSetDATE() throws SQLException{
		@SuppressWarnings("deprecation")
		Date date = new Date(2021, 8, 21);
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.date()
						.build(),
				date);
	}
	
	@Test
	public void testExtractValueFromResultSetYEAR() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.year()
						.build(),
				(short) 2021);
	}
	
	@Test
	public void testExtractValueFromResultSetDATETIME() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.datetime()
						.fractionalSecondsPrecision(3)
						.build(),
				new Timestamp(System.currentTimeMillis()));
	}
	
	@Test
	public void testExtractValueFromResultSetTIMESTAMP() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.timestamp()
						.fractionalSecondsPrecision(3)
						.build(),
				new Timestamp(System.currentTimeMillis()));
	}
	
	@Test
	public void testExtractValueFromResultSetTIME() throws SQLException{
		testExtractValueFromResultSet(ColumnDefinition.builder()
						.columnName("Test")
						.time()
						.fractionalSecondsPrecision(3)
						.build(),
				Time.valueOf("20:44:58"));
	}
	
	@Test
	public void testMakeColumnRef(){
		ColumnRef columnRef = SQLSyntaxUtil.makeColumnRef("Derp");
		assertNull(columnRef.getTableName());
		assertEquals("Derp", columnRef.getColumnName());
		assertNull(columnRef.getAlias());
		assertEquals("Derp", columnRef.toString());
	}
	
	@Test
	public void testMakeColumnRefDot(){
		ColumnRef columnRef = SQLSyntaxUtil.makeColumnRef("Derp.Plop");
		assertEquals("Derp", columnRef.getTableName());
		assertEquals("Plop", columnRef.getColumnName());
		assertNull(columnRef.getAlias());
		assertEquals("Derp.Plop", columnRef.toString());
	}
	
	@Test
	public void testMakeColumnRefs(){
		List<ColumnRef> columnRefs = SQLSyntaxUtil.makeColumnRefs(ListUtil.createList("Test", "Derp.Plop"));
		assertEquals(2, columnRefs.size());
		ColumnRef columnRef1 = columnRefs.get(0);
		assertNull(columnRef1.getTableName());
		assertEquals("Test", columnRef1.getColumnName());
		assertNull(columnRef1.getAlias());
		assertEquals("Test", columnRef1.toString());
		ColumnRef columnRef2 = columnRefs.get(1);
		assertEquals("Derp", columnRef2.getTableName());
		assertEquals("Plop", columnRef2.getColumnName());
		assertNull(columnRef2.getAlias());
		assertEquals("Derp.Plop", columnRef2.toString());
	}
	
	@Test
	public void testMakeConditionalStatement(){
		ConditionalStatement stmt = SQLSyntaxUtil.makeConditionalStmt(false, "Test", 42);
		ColumnRef column = stmt.getColumn();
		assertNull(column.getTableName());
		assertEquals("Test", column.getColumnName());
		assertNull(column.getAlias());
		assertEquals(SQLOperator.EQUAL, stmt.getOperator());
		assertEquals(42, stmt.getValue());
	}
	
	@Test
	public void testMakeConditionalStatementColumnWithDot(){
		ConditionalStatement stmt = SQLSyntaxUtil.makeConditionalStmt(true, "Test.Derp", 42);
		ColumnRef column = stmt.getColumn();
		assertEquals("Test", column.getTableName());
		assertEquals("Derp", column.getColumnName());
		assertNull(column.getAlias());
		assertEquals(SQLOperator.EQUAL, stmt.getOperator());
		assertEquals(42, stmt.getValue());
	}
	
	@Test
	public void testMakeConditionalStatementStringNotSearch(){
		ConditionalStatement stmt = SQLSyntaxUtil.makeConditionalStmt(false, "Test", "Derp");
		ColumnRef column = stmt.getColumn();
		assertNull(column.getTableName());
		assertEquals("Test", column.getColumnName());
		assertNull(column.getAlias());
		assertEquals(SQLOperator.EQUAL, stmt.getOperator());
		assertEquals("Derp", stmt.getValue());
	}
	
	@Test
	public void testMakeConditionalStatementStringSearch(){
		ConditionalStatement stmt = SQLSyntaxUtil.makeConditionalStmt(true, "Test", "Derp");
		ColumnRef column = stmt.getColumn();
		assertNull(column.getTableName());
		assertEquals("Test", column.getColumnName());
		assertNull(column.getAlias());
		assertEquals(SQLOperator.LIKE, stmt.getOperator());
		assertEquals("%Derp%", stmt.getValue());
	}
	
	@Test
	public void testFormatInsertStatement(){
		String insertStmt = SQLSyntaxUtil.formatInsertStatement("Test", ListUtil.createList("Derp", "Plop"),
				ListUtil.createList(42, true));
		assertEquals("INSERT INTO Test (Derp, Plop) VALUES (42, true)", insertStmt);
	}
	
	@Test
	public void testFormatQueryError(){
		try{
			SQLSyntaxUtil.formatQuery(ListUtil.createList("Test"), ListUtil.createList("Derp"),
					ListUtil.createList("Plop"), ListUtil.createList(), false);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("cols and values must be the same size!", e.getMessage());
		}
	}
	
	@Test
	public void testFormatQuery(){
		String selectStmt = SQLSyntaxUtil.formatQuery(ListUtil.createList("Test"), ListUtil.createList("Derp"),
				ListUtil.createList("Plop"), ListUtil.createList(42), false);
		assertEquals("SELECT DISTINCT Derp FROM Test WHERE Plop = 42", selectStmt);
	}
	
	@Test
	public void testFormatQueryMultipleConditions(){
		String selectStmt = SQLSyntaxUtil.formatQuery(ListUtil.createList("Test"), ListUtil.createList("Derp"),
				ListUtil.createList("Plop", "Yep"), ListUtil.createList(42, "something"), false);
		assertEquals("SELECT DISTINCT Derp FROM Test WHERE (Plop = 42) AND Yep = 'something'", selectStmt);
	}
	
	@Test
	public void testFormatQuerySearchTrue(){
		String selectStmt = SQLSyntaxUtil.formatQuery(ListUtil.createList("Test"), ListUtil.createList("Derp"),
				ListUtil.createList("Plop", "Yep"), ListUtil.createList(42, "something"), true);
		assertEquals("SELECT DISTINCT Derp FROM Test WHERE (Plop = 42) AND Yep LIKE '%something%'", selectStmt);
	}
}
