package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.database.mysql.syntax.conditional.ConditionalStatement;
import com.github.tadukoo.database.mysql.syntax.conditional.SQLOperator;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class SQLSyntaxUtilTest{
	
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
	public void testConvertValueToStringWithTableRef(){
		TableRef table = TableRef.builder().tableName("Derp").build();
		assertEquals("Derp", SQLSyntaxUtil.convertValueToString(table));
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
