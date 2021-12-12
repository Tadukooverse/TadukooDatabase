package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class ForeignKeyConstraintTest{
	private final String columnName1 = "Test";
	private final String columnName2 = "Derp";
	private final List<String> columnNames = ListUtil.createList(columnName1, columnName2);
	private final ColumnRef columnRef1 = ColumnRef.builder()
			.columnName(columnName1)
			.build();
	private final ColumnRef columnRef2 = ColumnRef.builder()
			.columnName(columnName2)
			.build();
	private final List<ColumnRef> columnRefs = ListUtil.createList(columnRef1, columnRef2);
	private final ColumnDefinition columnDef1 = ColumnDefinition.builder()
			.columnName(columnName1)
			.year()
			.build();
	private final ColumnDefinition columnDef2 = ColumnDefinition.builder()
			.columnName(columnName2)
			.year()
			.build();
	private final List<ColumnDefinition> columnDefs = ListUtil.createList(columnDef1, columnDef2);
	private final String referenceTable = "Plop";
	private final TableRef referenceTableRef = TableRef.builder()
			.tableName(referenceTable)
			.build();
	private final String referenceColumn1 = "Blah";
	private final String referenceColumn2 = "Flap";
	private final List<String> referenceColumnNames = ListUtil.createList(referenceColumn1, referenceColumn2);
	private final ColumnRef referenceColumnRef1 = ColumnRef.builder()
			.columnName(referenceColumn1)
			.build();
	private final ColumnRef referenceColumnRef2 = ColumnRef.builder()
			.columnName(referenceColumn2)
			.build();
	private final List<ColumnRef> referenceColumnRefs = ListUtil.createList(referenceColumnRef1, referenceColumnRef2);
	private final ColumnDefinition referenceColumnDef1 = ColumnDefinition.builder()
			.columnName(referenceColumn1)
			.year()
			.build();
	private final ColumnDefinition referenceColumnDef2 = ColumnDefinition.builder()
			.columnName(referenceColumn2)
			.year()
			.build();
	private final List<ColumnDefinition> referenceColumnDefs = ListUtil.createList(referenceColumnDef1, referenceColumnDef2);
	private final SQLReferenceOption onDeleteOption = SQLReferenceOption.CASCADE;
	private final SQLReferenceOption onUpdateOption = SQLReferenceOption.RESTRICT;
	private ForeignKeyConstraint constraint;
	
	@BeforeEach
	public void setup(){
		constraint = ForeignKeyConstraint.builder()
				.columnNames(columnName1)
				.references(referenceTable)
				.referenceColumnNames(referenceColumn1)
				.build();
	}
	
	/*
	 * Test Builder Settings
	 */
	
	@Test
	public void testBuilderSetColumnNamesSingle(){
		List<String> columns = constraint.getColumnNames();
		assertEquals(1, columns.size());
		assertEquals(columnName1, columns.get(0));
	}
	
	@Test
	public void testBuilderSetColumnNamesList(){
		constraint = ForeignKeyConstraint.builder()
				.columnNames(columnNames)
				.references(referenceTable)
				.referenceColumnNames(referenceColumn1)
				.build();
		assertEquals(columnNames, constraint.getColumnNames());
	}
	
	@Test
	public void testBuilderSetColumnRefsIndividual(){
		constraint = ForeignKeyConstraint.builder()
				.columnRefs(columnRef1, columnRef2)
				.references(referenceTable)
				.referenceColumnNames(referenceColumn1)
				.build();
		List<String> columns = constraint.getColumnNames();
		assertEquals(2, columns.size());
		assertEquals(columnName1, columns.get(0));
		assertEquals(columnName2, columns.get(1));
	}
	
	@Test
	public void testBuilderSetColumnRefsList(){
		constraint = ForeignKeyConstraint.builder()
				.columnRefs(columnRefs)
				.references(referenceTable)
				.referenceColumnNames(referenceColumn1)
				.build();
		List<String> columns = constraint.getColumnNames();
		assertEquals(2, columns.size());
		assertEquals(columnName1, columns.get(0));
		assertEquals(columnName2, columns.get(1));
	}
	
	@Test
	public void testBuilderSetColumnDefsIndividual(){
		constraint = ForeignKeyConstraint.builder()
				.columnDefs(columnDef1, columnDef2)
				.references(referenceTable)
				.referenceColumnNames(referenceColumn1)
				.build();
		List<String> columns = constraint.getColumnNames();
		assertEquals(2, columns.size());
		assertEquals(columnName1, columns.get(0));
		assertEquals(columnName2, columns.get(1));
	}
	
	@Test
	public void testBuilderSetColumnDefsList(){
		constraint = ForeignKeyConstraint.builder()
				.columnDefs(columnDefs)
				.references(referenceTable)
				.referenceColumnNames(referenceColumn1)
				.build();
		List<String> columns = constraint.getColumnNames();
		assertEquals(2, columns.size());
		assertEquals(columnName1, columns.get(0));
		assertEquals(columnName2, columns.get(1));
	}
	
	@Test
	public void testBuilderSetReferenceTable(){
		assertEquals(referenceTable, constraint.getReferenceTable());
	}
	
	@Test
	public void testBuilderSetReferenceTableRef(){
		constraint = ForeignKeyConstraint.builder()
				.columnNames(columnName1)
				.references(referenceTableRef)
				.referenceColumnNames(referenceColumn1)
				.build();
		assertEquals(referenceTable, constraint.getReferenceTable());
	}
	
	@Test
	public void testBuilderSetReferenceColumnNamesIndividual(){
		List<String> columns = constraint.getReferenceColumnNames();
		assertEquals(1, columns.size());
		assertEquals(referenceColumn1, columns.get(0));
	}
	
	@Test
	public void testBuilderSetReferenceColumnNamesList(){
		constraint = ForeignKeyConstraint.builder()
				.columnNames(columnName1)
				.references(referenceTable)
				.referenceColumnNames(referenceColumnNames)
				.build();
		assertEquals(referenceColumnNames, constraint.getReferenceColumnNames());
	}
	
	@Test
	public void testBuilderSetReferenceColumnRefsIndividual(){
		constraint = ForeignKeyConstraint.builder()
				.columnNames(columnName1)
				.references(referenceTable)
				.referenceColumnRefs(referenceColumnRef1, referenceColumnRef2)
				.build();
		List<String> columns = constraint.getReferenceColumnNames();
		assertEquals(2, columns.size());
		assertEquals(referenceColumn1, columns.get(0));
		assertEquals(referenceColumn2, columns.get(1));
	}
	
	@Test
	public void testBuilderSetReferenceColumnRefsList(){
		constraint = ForeignKeyConstraint.builder()
				.columnNames(columnName1)
				.references(referenceTable)
				.referenceColumnRefs(referenceColumnRefs)
				.build();
		List<String> columns = constraint.getReferenceColumnNames();
		assertEquals(2, columns.size());
		assertEquals(referenceColumn1, columns.get(0));
		assertEquals(referenceColumn2, columns.get(1));
	}
	
	@Test
	public void testBuilderSetReferenceColumnDefsIndividual(){
		constraint = ForeignKeyConstraint.builder()
				.columnNames(columnName1)
				.references(referenceTable)
				.referenceColumnDefs(referenceColumnDef1, referenceColumnDef2)
				.build();
		List<String> columns = constraint.getReferenceColumnNames();
		assertEquals(2, columns.size());
		assertEquals(referenceColumn1, columns.get(0));
		assertEquals(referenceColumn2, columns.get(1));
	}
	
	@Test
	public void testBuilderSetReferenceColumnDefsList(){
		constraint = ForeignKeyConstraint.builder()
				.columnNames(columnName1)
				.references(referenceTable)
				.referenceColumnDefs(referenceColumnDefs)
				.build();
		List<String> columns = constraint.getReferenceColumnNames();
		assertEquals(2, columns.size());
		assertEquals(referenceColumn1, columns.get(0));
		assertEquals(referenceColumn2, columns.get(1));
	}
	
	@Test
	public void testBuilderDefaultOnDeleteOption(){
		assertNull(constraint.getOnDeleteOption());
	}
	
	@Test
	public void testBuilderSetOnDeleteOption(){
		constraint = ForeignKeyConstraint.builder()
				.columnNames(columnName1)
				.references(referenceTable)
				.referenceColumnNames(referenceColumn1)
				.onDelete(onDeleteOption)
				.build();
		assertEquals(onDeleteOption, constraint.getOnDeleteOption());
	}
	
	@Test
	public void testBuilderDefaultOnUpdateOption(){
		assertNull(constraint.getOnUpdateOption());
	}
	
	@Test
	public void testBuilderSetOnUpdateOption(){
		constraint = ForeignKeyConstraint.builder()
				.columnNames(columnName1)
				.references(referenceTable)
				.referenceColumnNames(referenceColumn1)
				.onUpdate(onUpdateOption)
				.build();
		assertEquals(onUpdateOption, constraint.getOnUpdateOption());
	}
	
	/*
	 * Test Builder Errors
	 */
	
	@Test
	public void testBuilderMissingColumnNames(){
		try{
			constraint = ForeignKeyConstraint.builder()
					.columnNames(new ArrayList<>())
					.references(referenceTable)
					.referenceColumnNames(referenceColumn1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors in building a ForeignKeyConstraint: \n" +
							"columnNames is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingReferenceTable(){
		try{
			constraint = ForeignKeyConstraint.builder()
					.columnNames(columnName1)
					.references("")
					.referenceColumnNames(referenceColumn1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors in building a ForeignKeyConstraint: \n" +
					"referenceTable is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderMissingReferenceColumnNames(){
		try{
			constraint = ForeignKeyConstraint.builder()
					.columnNames(columnName1)
					.references(referenceTable)
					.referenceColumnNames(new ArrayList<>())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Encountered the following errors in building a ForeignKeyConstraint: \n" +
					"referenceColumnNames is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderAllErrors(){
		try{
			constraint = ForeignKeyConstraint.builder()
					.columnNames(new ArrayList<>())
					.references("")
					.referenceColumnNames(new ArrayList<>())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Encountered the following errors in building a ForeignKeyConstraint:\s
					columnNames is required!
					referenceTable is required!
					referenceColumnNames is required!""", e.getMessage());
		}
	}
	
	/*
	 * Test toString
	 */
	
	@Test
	public void testToString(){
		assertEquals("FOREIGN KEY (" + columnName1 + ") REFERENCES " + referenceTable + " (" +
				referenceColumn1 + ")", constraint.toString());
	}
	
	@Test
	public void testToStringMultipleColumnNames(){
		constraint = ForeignKeyConstraint.builder()
				.columnNames(columnNames)
				.references(referenceTable)
				.referenceColumnNames(referenceColumn1)
				.build();
		assertEquals("FOREIGN KEY (" + columnName1 + ", " + columnName2 + ") REFERENCES " +
				referenceTable + " (" + referenceColumn1 + ")", constraint.toString());
	}
	
	@Test
	public void testToStringMultipleReferenceColumnNames(){
		constraint = ForeignKeyConstraint.builder()
				.columnNames(columnName1)
				.references(referenceTable)
				.referenceColumnNames(referenceColumnNames)
				.build();
		assertEquals("FOREIGN KEY (" + columnName1 + ") REFERENCES " + referenceTable + " (" +
				referenceColumn1 + ", " + referenceColumn2 + ")", constraint.toString());
	}
	
	@Test
	public void testToStringOnDelete(){
		constraint = ForeignKeyConstraint.builder()
				.columnNames(columnName1)
				.references(referenceTable)
				.referenceColumnNames(referenceColumn1)
				.onDelete(onDeleteOption)
				.build();
		assertEquals("FOREIGN KEY (" + columnName1 + ") REFERENCES " + referenceTable + " (" +
				referenceColumn1 + ") ON DELETE " + onDeleteOption, constraint.toString());
	}
	
	@Test
	public void testToStringOnUpdate(){
		constraint = ForeignKeyConstraint.builder()
				.columnNames(columnName1)
				.references(referenceTable)
				.referenceColumnNames(referenceColumn1)
				.onUpdate(onUpdateOption)
				.build();
		assertEquals("FOREIGN KEY (" + columnName1 + ") REFERENCES " + referenceTable + " (" +
				referenceColumn1 + ") ON UPDATE " + onUpdateOption, constraint.toString());
	}
	
	@Test
	public void testToStringAll(){
		constraint = ForeignKeyConstraint.builder()
				.columnNames(columnNames)
				.references(referenceTable)
				.referenceColumnNames(referenceColumnNames)
				.onDelete(onDeleteOption)
				.onUpdate(onUpdateOption)
				.build();
		assertEquals("FOREIGN KEY (" + columnName1 + ", " + columnName2 + ") REFERENCES " +
				referenceTable + " (" + referenceColumn1 + ", " + referenceColumn2 + ") ON DELETE " +
				onDeleteOption + " ON UPDATE " + onUpdateOption, constraint.toString());
	}
}
