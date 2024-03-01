package com.github.tadukoo.database.mysql.pojo;

import com.github.tadukoo.database.mysql.CommonResultSetConverters;
import com.github.tadukoo.database.mysql.DatabaseConnectionTest;
import com.github.tadukoo.database.mysql.syntax.ColumnDefinition;
import com.github.tadukoo.database.mysql.syntax.ForeignKeyConstraint;
import com.github.tadukoo.database.mysql.syntax.conditional.Conditional;
import com.github.tadukoo.database.mysql.syntax.conditional.ConditionalStatement;
import com.github.tadukoo.database.mysql.syntax.conditional.SQLOperator;
import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.database.mysql.syntax.statement.SQLCreateStatement;
import com.github.tadukoo.database.mysql.syntax.statement.SQLDropStatement;
import com.github.tadukoo.database.mysql.syntax.statement.SQLSelectStatement;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.pojo.AbstractMappedPojo;
import com.github.tadukoo.util.pojo.MappedPojo;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabasePojoTest extends DatabaseConnectionTest{
	private DatabasePojo pojo;
	private final String tableName = "Test";
	private final String idColName = "id";
	private boolean ranSetDefaultColumnDefs;
	private static final String otherTableName = "Test5";
	private static final String subPojoIDColName = "id";
	private static final ColumnDefinition subPojoIDCol = ColumnDefinition.builder()
			.columnName(subPojoIDColName)
			.integer()
			.defaultSize()
			.primaryKey()
			.autoIncrement()
			.build();
	private static final ColumnDefinition other = ColumnDefinition.builder()
			.columnName("Derp")
			.integer()
			.defaultSize()
			.build();
	
	private class SubPojoClass extends AbstractDatabasePojo{
		
		public SubPojoClass(){
			super();
		}
		
		@Override
		public String getTableName(){
			return tableName + "2";
		}
		
		@Override
		public String getIDColumnName(){
			return idColName + "2";
		}
		
		@Override
		public void setDefaultColumnDefs(){
		
		}
	}
	
	public static class SubPojoClass2 extends AbstractDatabasePojo{
		public SubPojoClass2(){
			super();
		}
		@Override
		public String getTableName(){
			return otherTableName;
		}
		@Override
		public String getIDColumnName(){
			return subPojoIDColName;
		}
		@Override
		public void setDefaultColumnDefs(){
			addColumnDef(subPojoIDCol);
			addColumnDef(other);
		}
	}
	
	@BeforeEach
	public void setup(){
		ranSetDefaultColumnDefs = false;
		pojo = new AbstractDatabasePojo(){
			@Override
			public String getTableName(){
				return tableName;
			}
			
			@Override
			public String getIDColumnName(){
				return idColName;
			}
			
			@Override
			public void setDefaultColumnDefs(){
				ranSetDefaultColumnDefs = true;
			}
		};
	}
	
	@Test
	public void testGetTableName(){
		assertEquals(tableName, pojo.getTableName());
	}
	
	@Test
	public void testGetIDColumnName(){
		assertEquals(idColName, pojo.getIDColumnName());
	}
	
	@Test
	public void testRanSetDefaultColumnDefs(){
		assertTrue(ranSetDefaultColumnDefs);
	}
	
	@Test
	public void testDefaultMap(){
		Map<String, Object> map = pojo.getMap();
		assertNotNull(map);
		assertTrue(map.isEmpty());
	}
	
	@Test
	public void testDefaultColumnMap(){
		Map<String, ColumnDefinition> columnDefMap = pojo.getColumnDefs();
		assertNotNull(columnDefMap);
		assertTrue(columnDefMap.isEmpty());
	}
	
	@Test
	public void testPojoConstructor(){
		MappedPojo otherPojo = new AbstractMappedPojo(){
			@Override
			public Map<String, Object> getMap(){
				Map<String, Object> map = new HashMap<>();
				map.put("Derp", 42);
				return map;
			}
		};
		ranSetDefaultColumnDefs = false;
		pojo = new AbstractDatabasePojo(otherPojo){
			@Override
			public String getTableName(){
				return null;
			}
			
			@Override
			public String getIDColumnName(){
				return null;
			}
			
			@Override
			public void setDefaultColumnDefs(){
				ranSetDefaultColumnDefs = true;
			}
		};
		assertTrue(ranSetDefaultColumnDefs);
		Map<String, Object> map = pojo.getMap();
		assertNotNull(map);
		assertFalse(map.isEmpty());
		assertTrue(map.containsKey("Derp"));
		assertEquals(42, map.get("Derp"));
		Map<String, ColumnDefinition> columnDefMap = pojo.getColumnDefs();
		assertNotNull(columnDefMap);
		assertTrue(columnDefMap.isEmpty());
	}
	
	@Test
	public void testDefaultGetSubPojoKeys(){
		Set<String> subPojoKeys = pojo.getSubPojoKeys();
		assertNotNull(subPojoKeys);
		assertTrue(subPojoKeys.isEmpty());
	}
	
	@Test
	public void testDefaultGetForeignKeys(){
		List<ForeignKeyConstraint> foreignKeys = pojo.getForeignKeys();
		assertNotNull(foreignKeys);
		assertTrue(foreignKeys.isEmpty());
	}
	
	@Test
	public void testAddForeignKey(){
		List<ForeignKeyConstraint> foreignKeys = pojo.getForeignKeys();
		assertNotNull(foreignKeys);
		assertTrue(foreignKeys.isEmpty());
		
		// Add a foreign key
		ForeignKeyConstraint foreignKey = ForeignKeyConstraint.builder()
				.columnNames("id_42")
				.references("OtherTable")
				.referenceColumnNames("id")
				.build();
		pojo.addForeignKey(foreignKey);
		
		// Check that it's there properly
		foreignKeys = pojo.getForeignKeys();
		assertNotNull(foreignKeys);
		assertFalse(foreignKeys.isEmpty());
		assertEquals(1, foreignKeys.size());
		assertEquals(foreignKey, foreignKeys.get(0));
	}
	
	@Test
	public void testAddSubPojo(){
		SubPojoDefinition subPojoDef = SubPojoDefinition.builder()
				.key("Derp")
				.build();
		DatabasePojo subPojo = new SubPojoClass();
		String subPojoIDCol = "other_pojo_id";
		ForeignKeyConstraint foreignKey = ForeignKeyConstraint.builder()
				.columnNames(subPojoIDCol)
				.references("OtherPojo")
				.referenceColumnNames(idColName + "2")
				.build();
		pojo.addSubPojo(subPojoDef, subPojo, foreignKey);
		// Check item stored
		assertEquals(subPojo, pojo.getItem("Derp"));
		// Check key stored
		Set<String> subPojoKeys = pojo.getSubPojoKeys();
		assertFalse(subPojoKeys.isEmpty());
		assertEquals(1, subPojoKeys.size());
		assertEquals("Derp", subPojoKeys.iterator().next());
		// Check subPojoDef stored
		Map<String, SubPojoDefinition> subPojoDefs = pojo.getSubPojoDefs();
		assertFalse(subPojoDefs.isEmpty());
		assertTrue(subPojoDefs.containsKey("Derp"));
		assertEquals(subPojoDef, subPojoDefs.get("Derp"));
		assertEquals(subPojoDef, pojo.getSubPojoDefBySubPojoKey("Derp"));
		// Check Foreign Key stored
		List<ForeignKeyConstraint> foreignKeys = pojo.getForeignKeys();
		assertFalse(foreignKeys.isEmpty());
		assertEquals(1, foreignKeys.size());
		assertEquals(foreignKey, foreignKeys.get(0));
	}
	
	@Test
	public void testAddSubPojoNoForeignKey(){
		SubPojoDefinition subPojoDef = SubPojoDefinition.builder()
				.key("Derp")
				.build();
		DatabasePojo subPojo = new SubPojoClass();
		pojo.addSubPojo(subPojoDef, subPojo, null);
		// Check item stored
		assertEquals(subPojo, pojo.getItem("Derp"));
		// Check key stored
		Set<String> subPojoKeys = pojo.getSubPojoKeys();
		assertFalse(subPojoKeys.isEmpty());
		assertEquals(1, subPojoKeys.size());
		assertEquals("Derp", subPojoKeys.iterator().next());
		// Check subPojoDef stored
		Map<String, SubPojoDefinition> subPojoDefs = pojo.getSubPojoDefs();
		assertFalse(subPojoDefs.isEmpty());
		assertTrue(subPojoDefs.containsKey("Derp"));
		assertEquals(subPojoDef, subPojoDefs.get("Derp"));
		assertEquals(subPojoDef, pojo.getSubPojoDefBySubPojoKey("Derp"));
		// Check Foreign Key stored
		List<ForeignKeyConstraint> foreignKeys = pojo.getForeignKeys();
		assertTrue(foreignKeys.isEmpty());
	}
	
	@Test
	public void testAddColumnDef(){
		ColumnDefinition columnDef = ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.build();
		pojo.addColumnDef(columnDef);
		// Check a null was put in items for it
		Map<String, Object> items = pojo.getMap();
		assertFalse(items.isEmpty());
		assertTrue(items.containsKey(idColName));
		assertNull(items.get(idColName));
		assertNull(pojo.getItem(idColName));
		// Check it was put in the column defs map
		Map<String, ColumnDefinition> columnDefs = pojo.getColumnDefs();
		assertFalse(columnDefs.isEmpty());
		assertTrue(columnDefs.containsKey(idColName));
		assertEquals(columnDef, columnDefs.get(idColName));
		List<String> columnDefKeys = pojo.getColumnDefKeys();
		assertFalse(columnDefKeys.isEmpty());
		assertEquals(1, columnDefKeys.size());
		assertEquals(idColName, columnDefKeys.iterator().next());
		assertEquals(columnDef, pojo.getColumnDefByKey(idColName));
	}
	
	@Test
	public void testAddColumnDefWithValue(){
		ColumnDefinition columnDef = ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.build();
		pojo.addColumnDef(columnDef, 42);
		// Check the value was put in items for it
		Map<String, Object> items = pojo.getMap();
		assertFalse(items.isEmpty());
		assertTrue(items.containsKey(idColName));
		assertEquals(42, items.get(idColName));
		assertEquals(42, pojo.getItem(idColName));
		// Check it was put in the column defs map
		Map<String, ColumnDefinition> columnDefs = pojo.getColumnDefs();
		assertFalse(columnDefs.isEmpty());
		assertTrue(columnDefs.containsKey(idColName));
		assertEquals(columnDef, columnDefs.get(idColName));
		List<String> columnDefKeys = pojo.getColumnDefKeys();
		assertFalse(columnDefKeys.isEmpty());
		assertEquals(1, columnDefKeys.size());
		assertEquals(idColName, columnDefKeys.iterator().next());
		assertEquals(columnDef, pojo.getColumnDefByKey(idColName));
	}
	
	@Test
	public void testCreateTable() throws SQLException{
		pojo.addColumnDef(ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.notNull()
				.primaryKey()
				.autoIncrement()
				.build());
		pojo.createTable(db);
		
		db.insert(pojo.getTableName(), ListUtil.createList(), ListUtil.createList());
		
		int result = db.executeQuery("Test Retrieve",
				SQLSelectStatement.builder()
						.fromTables(TableRef.builder().tableName(pojo.getTableName()).build())
						.build()
						.toString(),
				CommonResultSetConverters::singleInteger);
		assertEquals(1, result);
		
		db.executeUpdate("Drop table",
				SQLDropStatement.builder()
						.table()
						.name(pojo.getTableName())
						.build()
						.toString());
	}
	
	@Test
	public void testCreateTableForeignKey() throws SQLException{
		// Add another table to the db
		String otherTableName = "OtherTable";
		db.executeUpdate("Create OtherTable",
				SQLCreateStatement.builder()
						.table()
						.tableName(otherTableName)
						.columns(ColumnDefinition.builder()
								.columnName("id")
								.integer()
								.defaultSize()
								.primaryKey()
								.notNull()
								.build())
						.build()
						.toString());
		db.insert(otherTableName, ListUtil.createList("id"), ListUtil.createList(42));
		
		// Create the pojo table
		pojo.addColumnDef(ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.notNull()
				.primaryKey()
				.autoIncrement()
				.build());
		pojo.addColumnDef(ColumnDefinition.builder()
				.columnName(idColName + "2")
				.integer()
				.defaultSize()
				.build());
		pojo.addForeignKey(ForeignKeyConstraint.builder()
				.columnNames(idColName + "2")
				.references(otherTableName)
				.referenceColumnNames("id")
				.build());
		pojo.createTable(db);
		
		// Try to insert a row for the other table's foreign key
		db.insert(pojo.getTableName(), ListUtil.createList(idColName + "2"), ListUtil.createList(42));
		
		// Drop both tables from the db
		db.executeUpdates("Drop Tables",
				ListUtil.createList("", ""),
				ListUtil.createList(SQLDropStatement.builder()
						.table()
						.name(pojo.getTableName())
						.build()
						.toString(),
				SQLDropStatement.builder()
						.table()
						.name(otherTableName)
						.build()
						.toString()));
	}
	
	@Test
	public void testNotNullDefaultResultSetFunc(){
		assertNotNull(pojo.getResultSetFunc());
	}
	
	@Test
	public void testNotNullDefaultResultSetListFunc(){
		assertNotNull(pojo.getResultSetListFunc(pojo.getClass()));
	}
	
	@Test
	public void testRetrieveValues() throws SQLException{
		ColumnDefinition id = ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		ColumnDefinition other = ColumnDefinition.builder()
				.columnName("Derp")
				.integer()
				.defaultSize()
				.build();
		
		// Create the table and insert a row
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(pojo.getTableName())
						.columns(id, other)
						.build()
						.toString());
		db.insert(pojo.getTableName(), ListUtil.createList(other.getColumnName()), ListUtil.createList(42));
		
		// Setup the pojo properly
		pojo.addColumnDef(id);
		pojo.addColumnDef(other);
		pojo.setItem(idColName, 1);
		
		// Try to retrieve values
		pojo.retrieveValues(db, false);
		
		// Drop the table
		db.executeUpdate("Drop table",
				SQLDropStatement.builder()
						.table()
						.name(pojo.getTableName())
						.build()
						.toString());
		
		// Check value
		assertEquals(42, pojo.getItem(other.getColumnName()));
	}
	
	@Test
	public void testRetrieveValuesSubPojosTrueButNoSubPojos() throws SQLException{
		ColumnDefinition id = ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		ColumnDefinition other = ColumnDefinition.builder()
				.columnName("Derp")
				.integer()
				.defaultSize()
				.build();
		
		// Create the table and insert a row
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(pojo.getTableName())
						.columns(id, other)
						.build()
						.toString());
		db.insert(pojo.getTableName(), ListUtil.createList(other.getColumnName()), ListUtil.createList(42));
		
		// Setup the pojo properly
		pojo.addColumnDef(id);
		pojo.addColumnDef(other);
		pojo.setItem(idColName, 1);
		
		// Try to retrieve values
		pojo.retrieveValues(db, true);
		
		// Drop the table
		db.executeUpdate("Drop table",
				SQLDropStatement.builder()
						.table()
						.name(pojo.getTableName())
						.build()
						.toString());
		
		// Check value
		assertEquals(42, pojo.getItem(other.getColumnName()));
	}
	
	@Test
	public void testRetrieveValuesSubPojoExists() throws SQLException{
		ColumnDefinition id = ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		ColumnDefinition other = ColumnDefinition.builder()
				.columnName("Derp")
				.integer()
				.defaultSize()
				.build();
		
		// Setup the subPojo
		SubPojoClass subPojo = new SubPojoClass();
		ColumnDefinition subPojoIDCol = ColumnDefinition.builder()
				.columnName(subPojo.getIDColumnName())
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		subPojo.addColumnDef(subPojoIDCol);
		subPojo.addColumnDef(other);
		subPojo.setItem(subPojo.getIDColumnName(), 1);
		String otherTableName = subPojo.getTableName();
		
		// Setup the foreign key
		ColumnDefinition foreignIDCol = ColumnDefinition.builder()
				.columnName("id_2")
				.integer()
				.defaultSize()
				.build();
		ForeignKeyConstraint foreignKey = ForeignKeyConstraint.builder()
				.columnNames("id_2")
				.references(subPojo.getTableName())
				.referenceColumnNames(subPojoIDCol.getColumnName())
				.build();
		
		// Create the sub pojo table and insert a row
		db.executeUpdate("Create subPojo table",
				SQLCreateStatement.builder()
						.table()
						.tableName(otherTableName)
						.columns(subPojoIDCol, other)
						.build()
						.toString());
		db.insert(otherTableName, ListUtil.createList(other.getColumnName()), ListUtil.createList(42));
		
		// Create the pojo table and insert a row
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(pojo.getTableName())
						.columns(id, foreignIDCol)
						.foreignKey(foreignKey)
						.build()
						.toString());
		db.insert(pojo.getTableName(), ListUtil.createList(foreignIDCol.getColumnName()), ListUtil.createList(1));
		
		// Setup the pojo properly
		pojo.addColumnDef(id);
		pojo.addColumnDef(foreignIDCol);
		pojo.setItem(idColName, 1);
		pojo.addSubPojo(SubPojoDefinition.builder()
				.key("Derp")
				.idCol("id_2")
				.build(),
				subPojo,
				foreignKey);
		
		// Try to retrieve values
		pojo.retrieveValues(db, true);
		
		// Drop the table
		db.executeUpdates("Drop tables",
				ListUtil.createList("", ""),
				ListUtil.createList(
						SQLDropStatement.builder()
								.table()
								.name(pojo.getTableName())
								.build()
								.toString(),
						SQLDropStatement.builder()
								.table()
								.name(subPojo.getTableName())
								.build()
								.toString()));
		
		// Check value
		assertEquals(subPojo, pojo.getItem("Derp"));
		assertEquals(42, subPojo.getItem(other.getColumnName()));
	}
	
	@Test
	public void testRetrieveValuesSubPojoNull() throws SQLException{
		// Setup column defs
		ColumnDefinition id = ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		
		// Setup the foreign key
		ColumnDefinition foreignIDCol = ColumnDefinition.builder()
				.columnName("id_2")
				.integer()
				.defaultSize()
				.build();
		ForeignKeyConstraint foreignKey = ForeignKeyConstraint.builder()
				.columnNames("id_2")
				.references(otherTableName)
				.referenceColumnNames(subPojoIDCol.getColumnName())
				.build();
		
		// Create the sub pojo table and insert a row
		db.executeUpdate("Create subPojo table",
				SQLCreateStatement.builder()
						.table()
						.tableName(otherTableName)
						.columns(subPojoIDCol, other)
						.build()
						.toString());
		db.insert(otherTableName, ListUtil.createList(other.getColumnName()), ListUtil.createList(42));
		
		// Create the pojo table and insert a row
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(pojo.getTableName())
						.columns(id, foreignIDCol)
						.foreignKey(foreignKey)
						.build()
						.toString());
		db.insert(pojo.getTableName(), ListUtil.createList(foreignIDCol.getColumnName()), ListUtil.createList(1));
		
		// Setup the pojo properly
		pojo.addColumnDef(id);
		pojo.addColumnDef(foreignIDCol);
		pojo.setItem(idColName, 1);
		pojo.addSubPojo(SubPojoDefinition.builder()
						.key("Derp")
						.idCol("id_2")
						.typeAndJunction(SubPojoClass2.class,
								Pair.of(
										ColumnRef.builder().columnName(id.getColumnName()).build(),
										ColumnRef.builder().columnName(subPojoIDCol.getColumnName()).build()))
						.build(),
				null,
				foreignKey);
		
		// Try to retrieve values
		pojo.retrieveValues(db, true);
		
		// Drop the table
		db.executeUpdates("Drop tables",
				ListUtil.createList("", ""),
				ListUtil.createList(
						SQLDropStatement.builder()
								.table()
								.name(pojo.getTableName())
								.build()
								.toString(),
						SQLDropStatement.builder()
								.table()
								.name(otherTableName)
								.build()
								.toString()));
		
		// Check value
		SubPojoClass2 subPojo = (SubPojoClass2) pojo.getItem("Derp");
		assertNotNull(subPojo);
		assertEquals(42, subPojo.getItem(other.getColumnName()));
	}
	
	@Test
	public void testRetrieveValuesSubPojoNullMissingIDCol() throws SQLException{
		// Setup column defs
		ColumnDefinition id = ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		
		// Setup the foreign key
		ColumnDefinition foreignIDCol = ColumnDefinition.builder()
				.columnName("id_2")
				.integer()
				.defaultSize()
				.build();
		ForeignKeyConstraint foreignKey = ForeignKeyConstraint.builder()
				.columnNames("id_2")
				.references(otherTableName)
				.referenceColumnNames(subPojoIDCol.getColumnName())
				.build();
		
		// Create the sub pojo table and insert a row
		db.executeUpdate("Create subPojo table",
				SQLCreateStatement.builder()
						.table()
						.tableName(otherTableName)
						.columns(subPojoIDCol, other)
						.build()
						.toString());
		db.insert(otherTableName, ListUtil.createList(other.getColumnName()), ListUtil.createList(42));
		
		// Create the pojo table and insert a row
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(pojo.getTableName())
						.columns(id, foreignIDCol)
						.foreignKey(foreignKey)
						.build()
						.toString());
		db.insert(pojo.getTableName(), ListUtil.createList(foreignIDCol.getColumnName()), ListUtil.createList(1));
		
		// Setup the pojo properly
		pojo.addColumnDef(id);
		pojo.addColumnDef(foreignIDCol);
		pojo.setItem(idColName, 1);
		pojo.addSubPojo(SubPojoDefinition.builder()
						.key("Derp")
						.build(),
				null,
				foreignKey);
		
		// Try to retrieve values
		pojo.retrieveValues(db, true);
		
		// Drop the table
		db.executeUpdates("Drop tables",
				ListUtil.createList("", ""),
				ListUtil.createList(
						SQLDropStatement.builder()
								.table()
								.name(pojo.getTableName())
								.build()
								.toString(),
						SQLDropStatement.builder()
								.table()
								.name(otherTableName)
								.build()
								.toString()));
		
		// Check value
		assertNull(pojo.getItem("Derp"));
	}
	
	@Test
	public void testRetrieveValuesSubPojoNullMissingClass() throws SQLException{
		// Setup column defs
		ColumnDefinition id = ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		
		// Setup the foreign key
		ColumnDefinition foreignIDCol = ColumnDefinition.builder()
				.columnName("id_2")
				.integer()
				.defaultSize()
				.build();
		ForeignKeyConstraint foreignKey = ForeignKeyConstraint.builder()
				.columnNames("id_2")
				.references(otherTableName)
				.referenceColumnNames(subPojoIDCol.getColumnName())
				.build();
		
		// Create the sub pojo table and insert a row
		db.executeUpdate("Create subPojo table",
				SQLCreateStatement.builder()
						.table()
						.tableName(otherTableName)
						.columns(subPojoIDCol, other)
						.build()
						.toString());
		db.insert(otherTableName, ListUtil.createList(other.getColumnName()), ListUtil.createList(42));
		
		// Create the pojo table and insert a row
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(pojo.getTableName())
						.columns(id, foreignIDCol)
						.foreignKey(foreignKey)
						.build()
						.toString());
		db.insert(pojo.getTableName(), ListUtil.createList(foreignIDCol.getColumnName()), ListUtil.createList(1));
		
		// Setup the pojo properly
		pojo.addColumnDef(id);
		pojo.addColumnDef(foreignIDCol);
		pojo.setItem(idColName, 1);
		pojo.addSubPojo(SubPojoDefinition.builder()
						.key("Derp")
						.idCol("id_2")
						.build(),
				null,
				foreignKey);
		
		// Try to retrieve values
		pojo.retrieveValues(db, true);
		
		// Drop the table
		db.executeUpdates("Drop tables",
				ListUtil.createList("", ""),
				ListUtil.createList(
						SQLDropStatement.builder()
								.table()
								.name(pojo.getTableName())
								.build()
								.toString(),
						SQLDropStatement.builder()
								.table()
								.name(otherTableName)
								.build()
								.toString()));
		
		// Check value
		assertNull(pojo.getItem("Derp"));
	}
	
	@Test
	public void testStoreValuesFirstInsert() throws SQLException{
		ColumnDefinition id = ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		ColumnDefinition other = ColumnDefinition.builder()
				.columnName("Derp")
				.integer()
				.defaultSize()
				.build();
		
		// Create the table
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(pojo.getTableName())
						.columns(id, other)
						.build()
						.toString());
		
		// Setup the pojo properly
		pojo.addColumnDef(id);
		pojo.addColumnDef(other);
		pojo.setItem("Derp", 42);
		
		// Do the insert
		assertNotNull(pojo.storeValues(db, false));
		
		assertNotNull(pojo.getItem(pojo.getIDColumnName()));
		int result = db.executeQuery("Get Value", SQLSelectStatement.builder()
						.returnColumns(ColumnRef.builder().columnName(other.getColumnName()).build())
						.fromTables(TableRef.builder().tableName(pojo.getTableName()).build())
						.whereStatement(Conditional.builder()
								.firstCondStmt(ConditionalStatement.builder()
										.column(ColumnRef.builder().columnName(pojo.getIDColumnName()).build())
										.operator(SQLOperator.EQUAL)
										.value(pojo.getItem(pojo.getIDColumnName()))
										.build()
								)
								.build())
						.build()
						.toString(),
				CommonResultSetConverters::singleInteger);
		assertEquals(42, result);
		
		// Drop the table
		db.executeUpdate("Drop table",
				SQLDropStatement.builder()
						.table()
						.name(pojo.getTableName())
						.build()
						.toString());
		
		// Check value
		assertEquals(42, pojo.getItem(other.getColumnName()));
	}
	
	@Test
	public void testStoreValuesUpdate() throws SQLException{
		ColumnDefinition id = ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		ColumnDefinition other = ColumnDefinition.builder()
				.columnName("Derp")
				.integer()
				.defaultSize()
				.build();
		
		// Create the table and insert a row
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(pojo.getTableName())
						.columns(id, other)
						.build()
						.toString());
		db.insert(pojo.getTableName(), ListUtil.createList(other.getColumnName()), ListUtil.createList(14));
		
		// Setup the pojo properly
		pojo.addColumnDef(id);
		pojo.addColumnDef(other);
		pojo.setItem(pojo.getIDColumnName(), 1);
		pojo.setItem("Derp", 42);
		
		// Do the insert
		assertNull(pojo.storeValues(db, false));
		
		int result = db.executeQuery("Get Value", SQLSelectStatement.builder()
						.returnColumns(ColumnRef.builder().columnName(other.getColumnName()).build())
						.fromTables(TableRef.builder().tableName(pojo.getTableName()).build())
						.whereStatement(Conditional.builder()
								.firstCondStmt(ConditionalStatement.builder()
										.column(ColumnRef.builder().columnName(pojo.getIDColumnName()).build())
										.operator(SQLOperator.EQUAL)
										.value(pojo.getItem(pojo.getIDColumnName()))
										.build()
								)
								.build())
						.build()
						.toString(),
				CommonResultSetConverters::singleInteger);
		assertEquals(42, result);
		
		// Drop the table
		db.executeUpdate("Drop table",
				SQLDropStatement.builder()
						.table()
						.name(pojo.getTableName())
						.build()
						.toString());
		
		// Check value
		assertEquals(42, pojo.getItem(other.getColumnName()));
	}
	
	@Test
	public void testStoreValuesNullSubPojo() throws SQLException{
		ColumnDefinition id = ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		ColumnDefinition other = ColumnDefinition.builder()
				.columnName("Derp")
				.integer()
				.defaultSize()
				.build();
		
		// Create the table and insert a row
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(pojo.getTableName())
						.columns(id, other)
						.build()
						.toString());
		
		// Setup the pojo properly
		pojo.addColumnDef(id);
		pojo.addColumnDef(other);
		pojo.setItem("Derp", 42);
		pojo.addSubPojo(SubPojoDefinition.builder()
				.key("Test")
				.build(),
				null,
				null);
		
		// Do the insert
		assertNotNull(pojo.storeValues(db, true));
		
		assertNotNull(pojo.getItem(pojo.getIDColumnName()));
		int result = db.executeQuery("Get Value", SQLSelectStatement.builder()
						.returnColumns(ColumnRef.builder().columnName(other.getColumnName()).build())
						.fromTables(TableRef.builder().tableName(pojo.getTableName()).build())
						.whereStatement(Conditional.builder()
								.firstCondStmt(ConditionalStatement.builder()
										.column(ColumnRef.builder().columnName(pojo.getIDColumnName()).build())
										.operator(SQLOperator.EQUAL)
										.value(pojo.getItem(pojo.getIDColumnName()))
										.build()
								)
								.build())
						.build()
						.toString(),
				CommonResultSetConverters::singleInteger);
		assertEquals(42, result);
		
		// Drop the table
		db.executeUpdate("Drop table",
				SQLDropStatement.builder()
						.table()
						.name(pojo.getTableName())
						.build()
						.toString());
		
		// Check value
		assertEquals(42, pojo.getItem(other.getColumnName()));
	}
	
	@Test
	public void testStoreValuesNoSubPojosDefined() throws SQLException{
		ColumnDefinition id = ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		ColumnDefinition other = ColumnDefinition.builder()
				.columnName("Derp")
				.integer()
				.defaultSize()
				.build();
		
		// Create the table and insert a row
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(pojo.getTableName())
						.columns(id, other)
						.build()
						.toString());
		
		// Setup the pojo properly
		pojo.addColumnDef(id);
		pojo.addColumnDef(other);
		pojo.setItem("Derp", 42);
		
		// Do the insert
		assertNotNull(pojo.storeValues(db, true));
		
		assertNotNull(pojo.getItem(pojo.getIDColumnName()));
		int result = db.executeQuery("Get Value", SQLSelectStatement.builder()
						.returnColumns(ColumnRef.builder().columnName(other.getColumnName()).build())
						.fromTables(TableRef.builder().tableName(pojo.getTableName()).build())
						.whereStatement(Conditional.builder()
								.firstCondStmt(ConditionalStatement.builder()
										.column(ColumnRef.builder().columnName(pojo.getIDColumnName()).build())
										.operator(SQLOperator.EQUAL)
										.value(pojo.getItem(pojo.getIDColumnName()))
										.build()
								)
								.build())
						.build()
						.toString(),
				CommonResultSetConverters::singleInteger);
		assertEquals(42, result);
		
		// Drop the table
		db.executeUpdate("Drop table",
				SQLDropStatement.builder()
						.table()
						.name(pojo.getTableName())
						.build()
						.toString());
		
		// Check value
		assertEquals(42, pojo.getItem(other.getColumnName()));
	}
	
	@Test
	public void testStoreValuesWithSubPojoInsert() throws SQLException{
		ColumnDefinition id = ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		ColumnDefinition other = ColumnDefinition.builder()
				.columnName("Derp")
				.integer()
				.defaultSize()
				.build();
		
		// Setup the subPojo
		SubPojoClass subPojo = new SubPojoClass();
		ColumnDefinition otherID = ColumnDefinition.builder()
				.columnName(subPojo.getIDColumnName())
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		subPojo.addColumnDef(otherID);
		subPojo.addColumnDef(other);
		subPojo.setItem(other.getColumnName(), 15);
		
		// Create the subPojo table
		db.executeUpdate("Create subPojo table",
				SQLCreateStatement.builder()
						.table()
						.tableName(subPojo.getTableName())
						.columns(otherID, other)
						.build()
						.toString());
		
		// Create the table and insert a row
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(pojo.getTableName())
						.columns(id, other)
						.build()
						.toString());
		
		// Setup the pojo properly
		pojo.addColumnDef(id);
		pojo.addColumnDef(other);
		pojo.setItem("Derp", 42);
		pojo.addSubPojo(SubPojoDefinition.builder()
						.key("Test")
						.build(),
				subPojo,
				null);
		
		// Do the insert
		assertNotNull(pojo.storeValues(db, true));
		
		assertNotNull(pojo.getItem(pojo.getIDColumnName()));
		int result = db.executeQuery("Get Value", SQLSelectStatement.builder()
						.returnColumns(ColumnRef.builder().columnName(other.getColumnName()).build())
						.fromTables(TableRef.builder().tableName(pojo.getTableName()).build())
						.whereStatement(Conditional.builder()
								.firstCondStmt(ConditionalStatement.builder()
										.column(ColumnRef.builder().columnName(pojo.getIDColumnName()).build())
										.operator(SQLOperator.EQUAL)
										.value(pojo.getItem(pojo.getIDColumnName()))
										.build()
								)
								.build())
						.build()
						.toString(),
				CommonResultSetConverters::singleInteger);
		assertEquals(42, result);
		
		assertNotNull(subPojo.getItem(subPojo.getIDColumnName()));
		int subPojoResult = db.executeQuery("Get Value", SQLSelectStatement.builder()
						.returnColumns(ColumnRef.builder().columnName(other.getColumnName()).build())
						.fromTables(TableRef.builder().tableName(subPojo.getTableName()).build())
						.whereStatement(Conditional.builder()
								.firstCondStmt(ConditionalStatement.builder()
										.column(ColumnRef.builder().columnName(subPojo.getIDColumnName()).build())
										.operator(SQLOperator.EQUAL)
										.value(subPojo.getItem(subPojo.getIDColumnName()))
										.build()
								)
								.build())
						.build()
						.toString(),
				CommonResultSetConverters::singleInteger);
		assertEquals(15, subPojoResult);
		
		// Drop the tables
		db.executeUpdates("Drop tables",
				ListUtil.createList("", ""),
				ListUtil.createList(
						SQLDropStatement.builder()
								.table()
								.name(pojo.getTableName())
								.build()
								.toString(),
						SQLDropStatement.builder()
								.table()
								.name(subPojo.getTableName())
								.build()
								.toString()));
		
		// Check value
		assertEquals(42, pojo.getItem(other.getColumnName()));
	}
	
	@Test
	public void testStoreValuesWithSubPojoUpdate() throws SQLException{
		ColumnDefinition id = ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		ColumnDefinition other = ColumnDefinition.builder()
				.columnName("Derp")
				.integer()
				.defaultSize()
				.build();
		
		// Setup the subPojo
		SubPojoClass subPojo = new SubPojoClass();
		ColumnDefinition otherID = ColumnDefinition.builder()
				.columnName(subPojo.getIDColumnName())
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		subPojo.addColumnDef(otherID);
		subPojo.addColumnDef(other);
		subPojo.setItem(other.getColumnName(), 15);
		subPojo.setItem(subPojo.getIDColumnName(), 1);
		assertNotNull(subPojo.getItem(subPojo.getIDColumnName()));
		
		// Create the subPojo table and insert a row
		db.executeUpdate("Create subPojo table",
				SQLCreateStatement.builder()
						.table()
						.tableName(subPojo.getTableName())
						.columns(otherID, other)
						.build()
						.toString());
		db.insert(subPojo.getTableName(), ListUtil.createList(other.getColumnName()), ListUtil.createList(17));
		
		// Create the table and insert a row
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(pojo.getTableName())
						.columns(id, other)
						.build()
						.toString());
		
		// Setup the pojo properly
		pojo.addColumnDef(id);
		pojo.addColumnDef(other);
		pojo.setItem("Derp", 42);
		pojo.addSubPojo(SubPojoDefinition.builder()
						.key("Test")
						.build(),
				subPojo,
				null);
		
		// Do the insert
		assertNotNull(pojo.storeValues(db, true));
		
		assertNotNull(pojo.getItem(pojo.getIDColumnName()));
		int result = db.executeQuery("Get Value", SQLSelectStatement.builder()
						.returnColumns(ColumnRef.builder().columnName(other.getColumnName()).build())
						.fromTables(TableRef.builder().tableName(pojo.getTableName()).build())
						.whereStatement(Conditional.builder()
								.firstCondStmt(ConditionalStatement.builder()
										.column(ColumnRef.builder().columnName(pojo.getIDColumnName()).build())
										.operator(SQLOperator.EQUAL)
										.value(pojo.getItem(pojo.getIDColumnName()))
										.build()
								)
								.build())
						.build()
						.toString(),
				CommonResultSetConverters::singleInteger);
		assertEquals(42, result);
		
		assertNotNull(subPojo.getItem(subPojo.getIDColumnName()));
		int subPojoResult = db.executeQuery("Get Value", SQLSelectStatement.builder()
						.returnColumns(ColumnRef.builder().columnName(other.getColumnName()).build())
						.fromTables(TableRef.builder().tableName(subPojo.getTableName()).build())
						.whereStatement(Conditional.builder()
								.firstCondStmt(ConditionalStatement.builder()
										.column(ColumnRef.builder().columnName(subPojo.getIDColumnName()).build())
										.operator(SQLOperator.EQUAL)
										.value(subPojo.getItem(subPojo.getIDColumnName()))
										.build()
								)
								.build())
						.build()
						.toString(),
				CommonResultSetConverters::singleInteger);
		assertEquals(15, subPojoResult);
		
		// Drop the tables
		db.executeUpdates("Drop tables",
				ListUtil.createList("", ""),
				ListUtil.createList(
						SQLDropStatement.builder()
								.table()
								.name(pojo.getTableName())
								.build()
								.toString(),
						SQLDropStatement.builder()
								.table()
								.name(subPojo.getTableName())
								.build()
								.toString()));
		
		// Check value
		assertEquals(42, pojo.getItem(other.getColumnName()));
	}
	
	@Test
	public void testStoreValuesWithSubPojoUpdateSubPojoID() throws SQLException{
		ColumnDefinition id = ColumnDefinition.builder()
				.columnName(idColName)
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		ColumnDefinition other = ColumnDefinition.builder()
				.columnName("Derp")
				.integer()
				.defaultSize()
				.build();
		
		// Setup the subPojo
		SubPojoClass subPojo = new SubPojoClass();
		ColumnDefinition otherID = ColumnDefinition.builder()
				.columnName(subPojo.getIDColumnName())
				.integer()
				.defaultSize()
				.primaryKey()
				.autoIncrement()
				.build();
		subPojo.addColumnDef(otherID);
		subPojo.addColumnDef(other);
		subPojo.setItem(other.getColumnName(), 15);
		
		// Create the subPojo table and insert a row
		db.executeUpdate("Create subPojo table",
				SQLCreateStatement.builder()
						.table()
						.tableName(subPojo.getTableName())
						.columns(otherID, other)
						.build()
						.toString());
		
		// Create the table and insert a row
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(pojo.getTableName())
						.columns(id, other)
						.build()
						.toString());
		
		// Setup the pojo properly
		pojo.addColumnDef(id);
		pojo.addColumnDef(other);
		pojo.setItem("Derp", 42);
		pojo.addSubPojo(SubPojoDefinition.builder()
						.key("Test")
						.idCol("Yep")
						.build(),
				subPojo,
				null);
		
		// Do the insert
		assertNotNull(pojo.storeValues(db, true));
		
		assertEquals(subPojo.getItem(subPojo.getIDColumnName()), pojo.getItem("Yep"));
		
		assertNotNull(pojo.getItem(pojo.getIDColumnName()));
		int result = db.executeQuery("Get Value", SQLSelectStatement.builder()
						.returnColumns(ColumnRef.builder().columnName(other.getColumnName()).build())
						.fromTables(TableRef.builder().tableName(pojo.getTableName()).build())
						.whereStatement(Conditional.builder()
								.firstCondStmt(ConditionalStatement.builder()
										.column(ColumnRef.builder().columnName(pojo.getIDColumnName()).build())
										.operator(SQLOperator.EQUAL)
										.value(pojo.getItem(pojo.getIDColumnName()))
										.build()
								)
								.build())
						.build()
						.toString(),
				CommonResultSetConverters::singleInteger);
		assertEquals(42, result);
		
		assertNotNull(subPojo.getItem(subPojo.getIDColumnName()));
		int subPojoResult = db.executeQuery("Get Value", SQLSelectStatement.builder()
						.returnColumns(ColumnRef.builder().columnName(other.getColumnName()).build())
						.fromTables(TableRef.builder().tableName(subPojo.getTableName()).build())
						.whereStatement(Conditional.builder()
								.firstCondStmt(ConditionalStatement.builder()
										.column(ColumnRef.builder().columnName(subPojo.getIDColumnName()).build())
										.operator(SQLOperator.EQUAL)
										.value(subPojo.getItem(subPojo.getIDColumnName()))
										.build()
								)
								.build())
						.build()
						.toString(),
				CommonResultSetConverters::singleInteger);
		assertEquals(15, subPojoResult);
		
		// Drop the tables
		db.executeUpdates("Drop tables",
				ListUtil.createList("", ""),
				ListUtil.createList(
						SQLDropStatement.builder()
								.table()
								.name(pojo.getTableName())
								.build()
								.toString(),
						SQLDropStatement.builder()
								.table()
								.name(subPojo.getTableName())
								.build()
								.toString()));
		
		// Check value
		assertEquals(42, pojo.getItem(other.getColumnName()));
	}
	
	@Test
	public void testDoSearch() throws SQLException{
		DatabasePojo subPojo = new SubPojoClass2();
		
		// Create the table and insert 2 rows
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(subPojo.getTableName())
						.columns(subPojoIDCol, other)
						.build()
						.toString());
		db.insert(subPojo.getTableName(), ListUtil.createList(other.getColumnName()), ListUtil.createList(42));
		db.insert(subPojo.getTableName(), ListUtil.createList(other.getColumnName()), ListUtil.createList(42));
		
		// Setup the pojo properly
		subPojo.setItem(other.getColumnName(), 42);
		
		// Do the search
		List<SubPojoClass2> foundPojos = subPojo.doSearch(db, SubPojoClass2.class, false);
		
		// Drop the table
		db.executeUpdate("Drop table",
				SQLDropStatement.builder()
						.table()
						.name(subPojo.getTableName())
						.build()
						.toString());
		
		// Check the results
		assertEquals(2, foundPojos.size());
		assertEquals(42, foundPojos.get(0).getItem(other.getColumnName()));
		assertEquals(42, foundPojos.get(1).getItem(other.getColumnName()));
		assertNotNull(foundPojos.get(0).getItem(subPojoIDColName));
		assertNotNull(foundPojos.get(1).getItem(subPojoIDColName));
		assertNotEquals(foundPojos.get(0).getItem(subPojoIDColName), foundPojos.get(1).getItem(subPojoIDColName));
	}
	
	@Test
	public void testDoSearchNullSubPojo() throws SQLException{
		DatabasePojo subPojo = new SubPojoClass2();
		
		// Create the table and insert 2 rows
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(subPojo.getTableName())
						.columns(subPojoIDCol, other)
						.build()
						.toString());
		db.insert(subPojo.getTableName(), ListUtil.createList(other.getColumnName()), ListUtil.createList(42));
		db.insert(subPojo.getTableName(), ListUtil.createList(other.getColumnName()), ListUtil.createList(42));
		
		// Setup the pojo properly
		subPojo.setItem(other.getColumnName(), 42);
		subPojo.addSubPojo(SubPojoDefinition.builder()
				.key("Something")
				.build(),
				null,
				null);
		
		// Do the search
		List<SubPojoClass2> foundPojos = subPojo.doSearch(db, SubPojoClass2.class, true);
		
		// Drop the table
		db.executeUpdate("Drop table",
				SQLDropStatement.builder()
						.table()
						.name(subPojo.getTableName())
						.build()
						.toString());
		
		// Check the results
		assertEquals(2, foundPojos.size());
		assertEquals(42, foundPojos.get(0).getItem(other.getColumnName()));
		assertEquals(42, foundPojos.get(1).getItem(other.getColumnName()));
		assertNotNull(foundPojos.get(0).getItem(subPojoIDColName));
		assertNotNull(foundPojos.get(1).getItem(subPojoIDColName));
		assertNotEquals(foundPojos.get(0).getItem(subPojoIDColName), foundPojos.get(1).getItem(subPojoIDColName));
	}
	
	@Test
	public void testDoSearchSubPojoNoColDefs() throws SQLException{
		DatabasePojo subPojo = new SubPojoClass2();
		
		// Create the table and insert 2 rows
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(subPojo.getTableName())
						.columns(subPojoIDCol, other)
						.build()
						.toString());
		db.insert(subPojo.getTableName(), ListUtil.createList(other.getColumnName()), ListUtil.createList(42));
		db.insert(subPojo.getTableName(), ListUtil.createList(other.getColumnName()), ListUtil.createList(42));
		
		// Setup the pojo properly
		subPojo.setItem(other.getColumnName(), 42);
		DatabasePojo subPojo2 = new AbstractDatabasePojo(){
			@Override
			public String getTableName(){
				return subPojo.getTableName() + "2";
			}
			@Override
			public String getIDColumnName(){
				return "id";
			}
			
			@Override
			public void setDefaultColumnDefs(){
			
			}
		};
		subPojo.addSubPojo(SubPojoDefinition.builder()
						.key("Something")
						.build(),
				subPojo2,
				null);
		
		// Do the search
		List<SubPojoClass2> foundPojos = subPojo.doSearch(db, SubPojoClass2.class, true);
		
		// Drop the table
		db.executeUpdate("Drop table",
				SQLDropStatement.builder()
						.table()
						.name(subPojo.getTableName())
						.build()
						.toString());
		
		// Check the results
		assertEquals(2, foundPojos.size());
		assertEquals(42, foundPojos.get(0).getItem(other.getColumnName()));
		assertEquals(42, foundPojos.get(1).getItem(other.getColumnName()));
		assertNotNull(foundPojos.get(0).getItem(subPojoIDColName));
		assertNotNull(foundPojos.get(1).getItem(subPojoIDColName));
		assertNotEquals(foundPojos.get(0).getItem(subPojoIDColName), foundPojos.get(1).getItem(subPojoIDColName));
	}
	
	@Test
	public void testDoSearchSubPojoHasValues() throws SQLException{
		DatabasePojo subPojo = new SubPojoClass2();
		ColumnDefinition forKey = ColumnDefinition.builder()
				.columnName("id_42")
				.integer()
				.defaultSize()
				.build();
		subPojo.addColumnDef(forKey);
		ForeignKeyConstraint foreignKey = ForeignKeyConstraint.builder()
				.columnNames("id_42")
				.references(subPojo.getTableName() + "2")
				.referenceColumnNames(subPojoIDColName)
				.build();
		
		// Create subPojo table and do an insert
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(subPojo.getTableName() + "2")
						.columns(subPojoIDCol, other)
						.build()
						.toString());
		db.insert(subPojo.getTableName() + "2", ListUtil.createList(other.getColumnName()),
				ListUtil.createList(17));
		
		// Create the table and insert 2 rows
		db.executeUpdate("Create table",
				SQLCreateStatement.builder()
						.table()
						.tableName(subPojo.getTableName())
						.columns(subPojoIDCol, other, forKey)
						.foreignKey(foreignKey)
						.build()
						.toString());
		db.insert(subPojo.getTableName(), ListUtil.createList(other.getColumnName(), forKey.getColumnName()),
				ListUtil.createList(42, 1));
		db.insert(subPojo.getTableName(), ListUtil.createList(other.getColumnName()), ListUtil.createList(42));
		
		// Setup the pojo properly
		subPojo.setItem(other.getColumnName(), 42);
		DatabasePojo subPojo2 = new AbstractDatabasePojo(){
			@Override
			public String getTableName(){
				return subPojo.getTableName() + "2";
			}
			@Override
			public String getIDColumnName(){
				return subPojoIDColName;
			}
			
			@Override
			public void setDefaultColumnDefs(){
				addColumnDef(subPojoIDCol);
				addColumnDef(other);
			}
		};
		subPojo2.setItem(other.getColumnName(), 17);
		subPojo.addSubPojo(SubPojoDefinition.builder()
						.key("Something")
						.idCol("id_42")
						.typeAndJunction(SubPojoClass2.class,
								Pair.of(ColumnRef.builder().tableName(subPojo.getTableName()).columnName("id_42").build(),
								ColumnRef.builder().tableName(subPojo.getTableName() + "2").columnName(subPojoIDColName).build()))
						.build(),
				subPojo2,
				foreignKey);
		
		// Do the search
		List<SubPojoClass2> foundPojos = subPojo.doSearch(db, SubPojoClass2.class, true);
		
		// Drop the tables
		db.executeUpdates("Drop tables",
				ListUtil.createList("", ""),
				ListUtil.createList(
						SQLDropStatement.builder()
								.table()
								.name(subPojo.getTableName())
								.build()
								.toString(),
						SQLDropStatement.builder()
								.table()
								.name(subPojo2.getTableName())
								.build()
								.toString()
				));
		
		// Check the results
		assertEquals(1, foundPojos.size());
		assertEquals(42, foundPojos.get(0).getItem(other.getColumnName()));
		assertNotNull(foundPojos.get(0).getItem(subPojoIDColName));
		assertEquals(1, foundPojos.get(0).getItem("id_42"));
	}
}
