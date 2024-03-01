package com.github.tadukoo.database.mysql.transaction.query;

import com.github.tadukoo.database.mysql.CommonResultSetConverters;
import com.github.tadukoo.database.mysql.DatabaseConnectionTest;
import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.database.mysql.syntax.statement.SQLCreateStatement;
import com.github.tadukoo.database.mysql.syntax.statement.SQLInsertStatement;
import com.github.tadukoo.database.mysql.syntax.statement.SQLSelectStatement;
import com.github.tadukoo.database.mysql.transaction.update.Updates;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.junit.logger.JUnitEasyLoggerAssertEntries;
import com.github.tadukoo.util.junit.logger.JUnitEasyLoggerEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryTest extends DatabaseConnectionTest{
	private final TableRef table = TableRef.builder()
			.tableName("Thing")
			.build();
	private final String name = "Select ID from Thing";
	private final String sql = SQLSelectStatement.builder()
			.fromTables(table)
			.build()
			.toString();
	private Query<Integer> query;
	
	@BeforeEach
	public void setup(){
		query = Query.createQuery(name, sql, CommonResultSetConverters::singleInteger);
	}
	
	@Test
	public void testGetTransactionName(){
		assertEquals(name, query.getTransactionName());
	}
	
	@Test
	public void testGetTransactionNameBlankName(){
		query = Query.createQuery(null, sql, CommonResultSetConverters::singleInteger);
		assertEquals(sql, query.getTransactionName());
	}
	
	@Test
	public void testGetSQL(){
		assertEquals(sql, query.getSQL());
	}
	
	@Test
	public void testExecuteQuery() throws SQLException{
		// Create a table and put some data in it
		db.executeTransaction(Updates.createUpdates("Create Table Thing", null,
				ListUtil.createList(SQLCreateStatement.builder()
						.table()
						.tableName("Thing")
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.integer()
								.defaultSize()
								.build())
						.build()
						.toString(),
				SQLInsertStatement.builder()
						.table(TableRef.builder()
								.tableName("Thing")
								.build())
						.columns(ColumnRef.builder()
								.columnName("id")
								.build())
						.values(42)
						.build()
						.toString())));
		
		// Try to pull out the data
		assertEquals(42, db.executeTransaction(query));
		
		// Check the last two entries in the logger
		List<JUnitEasyLoggerEntry> entries = logger.getEntries();
		JUnitEasyLoggerAssertEntries.assertEntries(ListUtil.createList(
				new JUnitEasyLoggerEntry(Level.INFO, "Running query " + name, null),
				new JUnitEasyLoggerEntry(Level.INFO, "Finished query " + name, null)),
				ListUtil.createList(entries.get(entries.size()-2), entries.get(entries.size()-1)));
	}
}
