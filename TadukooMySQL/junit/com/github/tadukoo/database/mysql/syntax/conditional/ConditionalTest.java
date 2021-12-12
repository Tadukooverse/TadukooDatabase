package com.github.tadukoo.database.mysql.syntax.conditional;

import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class ConditionalTest{
	private Conditional cond;
	private ConditionalStatement firstCondStmt;
	
	@BeforeEach
	public void setup(){
		firstCondStmt = ConditionalStatement.builder()
				.column(ColumnRef.builder().columnName("Test").build())
				.operator(SQLOperator.EQUAL)
				.value(5)
				.build();
		
		cond = Conditional.builder()
				.firstCondStmt(firstCondStmt)
				.build();
	}
	
	@Test
	public void testBuilderSetFirstCondStmt(){
		assertEquals(firstCondStmt, cond.getFirstCondStmt());
	}
	
	@Test
	public void testBuilderDefaultFirstCond(){
		assertNull(cond.getFirstCond());
	}
	
	@Test
	public void testBuilderDefaultOperator(){
		assertNull(cond.getOperator());
	}
	
	@Test
	public void testBuilderDefaultSecondCond(){
		assertNull(cond.getSecondCond());
	}
	
	@Test
	public void testBuilderDefaultSecondCondStmt(){
		assertNull(cond.getSecondCondStmt());
	}
	
	@Test
	public void testBuilderDefaultFirstCondStmt(){
		Conditional cond2 = Conditional.builder()
				.firstCond(cond)
				.operator(SQLConjunctiveOperator.AND)
				.secondCondStmt(firstCondStmt)
				.build();
		assertNull(cond2.getFirstCondStmt());
	}
	
	@Test
	public void testBuilderSetFirstCond(){
		Conditional cond2 = Conditional.builder()
				.firstCond(cond)
				.operator(SQLConjunctiveOperator.AND)
				.secondCondStmt(firstCondStmt)
				.build();
		assertEquals(cond, cond2.getFirstCond());
	}
	
	@Test
	public void testBuilderSetOperator(){
		Conditional cond2 = Conditional.builder()
				.firstCond(cond)
				.operator(SQLConjunctiveOperator.AND)
				.secondCondStmt(firstCondStmt)
				.build();
		assertEquals(SQLConjunctiveOperator.AND, cond2.getOperator());
	}
	
	@Test
	public void testBuilderSetSecondCond(){
		Conditional cond2 = Conditional.builder()
				.firstCondStmt(firstCondStmt)
				.operator(SQLConjunctiveOperator.AND)
				.secondCond(cond)
				.build();
		assertEquals(cond, cond2.getSecondCond());
	}
	
	@Test
	public void testBuilderSetSecondCondStmt(){
		Conditional cond2 = Conditional.builder()
				.firstCond(cond)
				.operator(SQLConjunctiveOperator.AND)
				.secondCondStmt(firstCondStmt)
				.build();
		assertEquals(firstCondStmt, cond2.getSecondCondStmt());
	}
	
	@Test
	public void testBuilderSetBothConditions(){
		Conditional cond2 = Conditional.builder()
				.firstCond(cond)
				.operator(SQLConjunctiveOperator.AND)
				.secondCond(cond)
				.build();
		assertEquals(cond, cond2.getFirstCond());
		assertEquals(cond, cond2.getSecondCond());
	}
	
	@Test
	public void testBuilderMissingFirstCondition(){
		try{
			cond = Conditional.builder()
					.firstCondStmt(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors trying to build a Conditional:\n" +
					"Must specify either firstCond or firstCondStmt!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderSetSingleConditional(){
		try{
			Conditional.builder()
					.firstCond(cond)
					.operator(null)
					.secondCond(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors trying to build a Conditional:\n" +
					"Can't specify a single conditional - that's already a conditional", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderSetTwoConditionsMissingOperator(){
		try{
			Conditional.builder()
					.firstCond(cond)
					.operator(null)
					.secondCondStmt(firstCondStmt)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors trying to build a Conditional:\n" +
					"Must specify an operator if you have two conditions!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderSetOperatorSingleCondition(){
		try{
			Conditional.builder()
					.firstCondStmt(firstCondStmt)
					.operator(SQLConjunctiveOperator.OR)
					.secondCond(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors trying to build a Conditional:\n" +
					"Can't specify operator with only one condition!", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals(firstCondStmt.toString(), cond.toString());
	}
	
	@Test
	public void testToStringFirstIsCond(){
		Conditional cond2 = Conditional.builder().firstCond(cond).operator(SQLConjunctiveOperator.OR)
				.secondCondStmt(firstCondStmt).build();
		assertEquals("(" + cond.toString() + ") OR " + firstCondStmt.toString(), cond2.toString());
	}
	
	@Test
	public void testToStringSecondIsCond(){
		Conditional cond2 = Conditional.builder().firstCondStmt(firstCondStmt).operator(SQLConjunctiveOperator.AND)
				.secondCond(cond).build();
		assertEquals(firstCondStmt.toString() + " AND (" + cond.toString() + ")", cond2.toString());
	}
}
