package com.github.tadukoo.database.mysql.syntax.conditional;

import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ConditionalStatementTest{
	private ConditionalStatement stmt;
	private final String columnName = "Test";
	
	@BeforeEach
	public void setup(){
		stmt = ConditionalStatement.builder()
				.column(ColumnRef.builder().columnName(columnName).build()).operator(SQLOperator.EQUAL).value("yep")
				.build();
	}
	
	@Test
	public void testBuilderDefaultNegated(){
		assertFalse(stmt.isNegated());
	}
	
	@Test
	public void testBuilderSetColumnRef(){
		assertEquals(columnName, stmt.getColumn().getColumnName());
		assertNull(stmt.getColumn().getAlias());
	}
	
	@Test
	public void testBuilderSetOperator(){
		assertEquals(SQLOperator.EQUAL, stmt.getOperator());
	}
	
	@Test
	public void testBuilderSetValue(){
		assertEquals("yep", stmt.getValue());
	}
	
	@Test
	public void testBuilderSetNegated(){
		stmt = ConditionalStatement.builder()
				.column(ColumnRef.builder().columnName(columnName).build()).operator(SQLOperator.EQUAL).value("yep")
				.negated(true)
				.build();
		assertTrue(stmt.isNegated());
	}
	
	@Test
	public void testBuilderSetNegatedNoParam(){
		stmt = ConditionalStatement.builder()
				.column(ColumnRef.builder().columnName(columnName).build()).operator(SQLOperator.EQUAL).value("yep")
				.negated()
				.build();
		assertTrue(stmt.isNegated());
	}
	
	@Test
	public void testBuilderMissingColumn(){
		try{
			stmt = ConditionalStatement.builder().operator(SQLOperator.EQUAL).value("yep").build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors trying to build a ConditionalStatement:\n" +
					"column is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingOperator(){
		try{
			stmt = ConditionalStatement.builder().column(ColumnRef.builder().columnName(columnName).build())
					.value("yep").build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors trying to build a ConditionalStatement:\n" +
					"operator is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingValue(){
		try{
			stmt = ConditionalStatement.builder().column(ColumnRef.builder().columnName(columnName).build())
					.operator(SQLOperator.EQUAL).build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors trying to build a ConditionalStatement:\n" +
					"value is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingEverything(){
		try{
			stmt = ConditionalStatement.builder().build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Encountered the following errors trying to build a ConditionalStatement:
					column is required!
					operator is required!
					value is required!""", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals(columnName + " = 'yep'", stmt.toString());
	}
	
	@Test
	public void testToStringIntValue(){
		stmt = ConditionalStatement.builder()
				.column(ColumnRef.builder().columnName(columnName).build()).operator(SQLOperator.NOT_EQUAL).value(5)
				.build();
		assertEquals(columnName + " != 5", stmt.toString());
	}
	
	@Test
	public void testToStringBoolValue(){
		stmt = ConditionalStatement.builder()
				.column(ColumnRef.builder().columnName(columnName).build()).operator(SQLOperator.EQUAL).value(true)
				.build();
		assertEquals(columnName + " = true", stmt.toString());
	}
	
	@Test
	public void testToStringColumnRefValue(){
		stmt = ConditionalStatement.builder()
				.column(ColumnRef.builder().columnName(columnName).build()).operator(SQLOperator.NOT_EQUAL)
				.value(ColumnRef.builder().columnName("Derp").build())
				.build();
		assertEquals(columnName + " != Derp", stmt.toString());
	}
	
	@Test
	public void testToStringNegated(){
		stmt = ConditionalStatement.builder()
				.column(ColumnRef.builder().columnName(columnName).build()).operator(SQLOperator.EQUAL).value("yep")
				.negated()
				.build();
		assertEquals("NOT " + columnName + " = 'yep'", stmt.toString());
	}
}
