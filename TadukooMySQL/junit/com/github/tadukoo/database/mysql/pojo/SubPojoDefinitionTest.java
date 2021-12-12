package com.github.tadukoo.database.mysql.pojo;

import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class SubPojoDefinitionTest{
	private SubPojoDefinition def;
	private final String key = "Derp";
	private final Pair<ColumnRef, ColumnRef> junction = Pair.of(ColumnRef.builder().columnName("Test").build(),
			ColumnRef.builder().columnName("Derp").build());
	
	@SuppressWarnings("SpellCheckingInspection")
	private static class DBPE extends AbstractDatabasePojo{
		@Override
		public String getTableName(){
			return null;
		}
		@Override
		public String getIDColumnName(){
			return null;
		}
		@Override
		public void setDefaultColumnDefs(){ }
	}
	
	@BeforeEach
	public void setup(){
		def = SubPojoDefinition.builder()
				.key(key)
				.build();
	}
	
	@Test
	public void testBuilderSetKey(){
		assertEquals(key, def.getKey());
	}
	
	@Test
	public void testBuilderDefaultIDCol(){
		assertNull(def.getIDCol());
	}
	
	@Test
	public void testBuilderSetIDCol(){
		def = SubPojoDefinition.builder()
				.key(key)
				.idCol("Test")
				.build();
		assertEquals("Test", def.getIDCol());
	}
	
	@Test
	public void testBuilderDefaultType(){
		assertNull(def.getType());
	}
	
	@Test
	public void testBuilderDefaultJunction(){
		assertNull(def.getJunction());
	}
	
	@Test
	public void testBuilderSetTypeAndJunction(){
		def = SubPojoDefinition.builder()
				.key(key)
				.idCol("Plop")
				.typeAndJunction(DBPE.class, junction)
				.build();
		assertEquals(DBPE.class, def.getType());
		assertEquals(junction, def.getJunction());
	}
	
	@Test
	public void testBuilderMissingKey(){
		try{
			def = SubPojoDefinition.builder()
					.key(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered trying to build a SubPojoDefinition: \n" +
					"key is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingIDColWithTypeAndJunction(){
		try{
			def = SubPojoDefinition.builder()
					.key(key)
					.idCol(null)
					.typeAndJunction(DBPE.class, junction)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered trying to build a SubPojoDefinition: \n" +
					"Type or junction is specified - in this case, type, junction, and idCol are required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingType(){
		try{
			def = SubPojoDefinition.builder()
					.key(key)
					.idCol("Derp")
					.typeAndJunction(null, junction)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered trying to build a SubPojoDefinition: \n" +
							"Type or junction is specified - in this case, type, junction, and idCol are required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingJunction(){
		try{
			def = SubPojoDefinition.builder()
					.key(key)
					.idCol("Derp")
					.typeAndJunction(DBPE.class, null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered trying to build a SubPojoDefinition: \n" +
							"Type or junction is specified - in this case, type, junction, and idCol are required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testBuilderAllErrors(){
		try{
			def = SubPojoDefinition.builder()
					.key(null)
					.idCol(null)
					.typeAndJunction(null, junction)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
							Errors encountered trying to build a SubPojoDefinition:\s
							key is required!
							Type or junction is specified - in this case, type, junction, and idCol are required!""",
					e.getMessage());
		}
	}
}
