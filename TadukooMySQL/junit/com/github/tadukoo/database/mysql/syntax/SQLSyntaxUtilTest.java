package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
