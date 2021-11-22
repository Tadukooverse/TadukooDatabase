package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.database.mysql.syntax.reference.ColumnRef;
import com.github.tadukoo.database.mysql.syntax.reference.TableRef;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Foreign Key Constraint represents a foreign key constraint in MySQL
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class ForeignKeyConstraint{
	
	/**
	 * A builder to use to make a {@link ForeignKeyConstraint}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>Foreign Key Constraint Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Default or Required</th>
	 *     </tr>
	 *     <tr>
	 *         <td>columnNames</td>
	 *         <td>The names of the columns for the foreign key</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>referenceTable</td>
	 *         <td>The name of the reference table for the foreign key</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>referenceColumnNames</td>
	 *         <td>The names of the columns from the reference table</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>onDeleteOption</td>
	 *         <td>The {@link SQLReferenceOption reference option} for on delete</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 *     <tr>
	 *         <td>onUpdateOption</td>
	 *         <td>The {@link SQLReferenceOption reference option} for on update</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class ForeignKeyConstraintBuilder implements ColumnNames, ReferenceTable, ReferenceColumnNames,
			OptionsOrBuild{
		/** The names of the columns for the foreign key */
		private List<String> columnNames;
		/** The name of the reference table for the foreign key */
		private String referenceTable;
		/** The names of the columns from the reference table */
		private List<String> referenceColumnNames;
		/** The {@link SQLReferenceOption reference option} for on delete */
		private SQLReferenceOption onDeleteOption = null;
		/** The {@link SQLReferenceOption reference option} for on update */
		private SQLReferenceOption onUpdateOption = null;
		
		/** Not allowed to instantiate outside of {@link ForeignKeyConstraint} */
		private ForeignKeyConstraintBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		public ReferenceTable columnNames(String ... columnNames){
			return columnNames(ListUtil.createList(columnNames));
		}
		
		/** {@inheritDoc} */
		@Override
		public ReferenceTable columnNames(List<String> columnNames){
			this.columnNames = columnNames;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public ReferenceTable columnRefs(ColumnRef ... columnRefs){
			return columnRefs(ListUtil.createList(columnRefs));
		}
		
		/** {@inheritDoc} */
		@Override
		public ReferenceTable columnRefs(List<ColumnRef> columnRefs){
			this.columnNames = columnRefs.stream()
					.map(ColumnRef::getColumnName)
					.collect(Collectors.toList());
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public ReferenceTable columnDefs(ColumnDefinition ... columnDefs){
			return columnDefs(ListUtil.createList(columnDefs));
		}
		
		/** {@inheritDoc} */
		@Override
		public ReferenceTable columnDefs(List<ColumnDefinition> columnDefs){
			this.columnNames = columnDefs.stream()
					.map(ColumnDefinition::getColumnName)
					.collect(Collectors.toList());
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public ReferenceColumnNames references(String referenceTable){
			this.referenceTable = referenceTable;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public ReferenceColumnNames references(TableRef referenceTable){
			this.referenceTable = referenceTable.getTableName();
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public OptionsOrBuild referenceColumnNames(String ... referenceColumnNames){
			return referenceColumnNames(ListUtil.createList(referenceColumnNames));
		}
		
		/** {@inheritDoc} */
		@Override
		public OptionsOrBuild referenceColumnNames(List<String> referenceColumnNames){
			this.referenceColumnNames = referenceColumnNames;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public OptionsOrBuild referenceColumnRefs(ColumnRef ... referenceColumnRefs){
			return referenceColumnRefs(ListUtil.createList(referenceColumnRefs));
		}
		
		/** {@inheritDoc} */
		@Override
		public OptionsOrBuild referenceColumnRefs(List<ColumnRef> referenceColumnRefs){
			this.referenceColumnNames = referenceColumnRefs.stream()
					.map(ColumnRef::getColumnName)
					.collect(Collectors.toList());
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public OptionsOrBuild referenceColumnDefs(ColumnDefinition ... referenceColumnDefs){
			return referenceColumnDefs(ListUtil.createList(referenceColumnDefs));
		}
		
		/** {@inheritDoc} */
		@Override
		public OptionsOrBuild referenceColumnDefs(List<ColumnDefinition> referenceColumnDefs){
			this.referenceColumnNames = referenceColumnDefs.stream()
					.map(ColumnDefinition::getColumnName)
					.collect(Collectors.toList());
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public OptionsOrBuild onDelete(SQLReferenceOption onDeleteOption){
			this.onDeleteOption = onDeleteOption;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public OptionsOrBuild onUpdate(SQLReferenceOption onUpdateOption){
			this.onUpdateOption = onUpdateOption;
			return this;
		}
		
		/**
		 * Checks for any errors in the set parameters and will throw an error if any are found
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// columnNames is required
			if(ListUtil.isBlank(columnNames)){
				errors.add("columnNames is required!");
			}
			
			// referenceTable is required
			if(StringUtil.isBlank(referenceTable)){
				errors.add("referenceTable is required!");
			}
			
			// referenceColumnNames is required
			if(ListUtil.isBlank(referenceColumnNames)){
				errors.add("referenceColumnNames is required!");
			}
			
			// Report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Encountered the following errors in building a " +
						"ForeignKeyConstraint: \n" + StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/** {@inheritDoc} */
		@Override
		public ForeignKeyConstraint build(){
			checkForErrors();
			
			return new ForeignKeyConstraint(columnNames, referenceTable, referenceColumnNames,
					onDeleteOption, onUpdateOption);
		}
	}
	
	/** The names of the columns for the foreign key */
	private final List<String> columnNames;
	/** The name of the reference table for the foreign key */
	private final String referenceTable;
	/** The names of the columns from the reference table */
	private final List<String> referenceColumnNames;
	/** The {@link SQLReferenceOption reference option} for on delete */
	private final SQLReferenceOption onDeleteOption;
	/** The {@link SQLReferenceOption reference option} for on update */
	private final SQLReferenceOption onUpdateOption;
	
	/**
	 * Constructs a new {@link ForeignKeyConstraint} using the given parameters
	 *
	 * @param columnNames The names of the columns for the foreign key
	 * @param referenceTable The name of the reference table for the foreign key
	 * @param referenceColumnNames The names of the columns from the reference table
	 * @param onDeleteOption The {@link SQLReferenceOption reference option} for on delete
	 * @param onUpdateOption The {@link SQLReferenceOption reference option} for on update
	 */
	private ForeignKeyConstraint(
			List<String> columnNames, String referenceTable, List<String> referenceColumnNames,
			SQLReferenceOption onDeleteOption, SQLReferenceOption onUpdateOption){
		this.columnNames = columnNames;
		this.referenceTable = referenceTable;
		this.referenceColumnNames = referenceColumnNames;
		this.onDeleteOption = onDeleteOption;
		this.onUpdateOption = onUpdateOption;
	}
	
	/**
	 * @return A new {@link ForeignKeyConstraintBuilder builder} to use to build a {@link ForeignKeyConstraint}
	 */
	public static ColumnNames builder(){
		return new ForeignKeyConstraintBuilder();
	}
	
	/**
	 * @return The names of the columns for the foreign key
	 */
	public List<String> getColumnNames(){
		return columnNames;
	}
	
	/**
	 * @return The name of the reference table for the foreign key
	 */
	public String getReferenceTable(){
		return referenceTable;
	}
	
	/**
	 * @return The names of the columns from the reference table
	 */
	public List<String> getReferenceColumnNames(){
		return referenceColumnNames;
	}
	
	/**
	 * @return The {@link SQLReferenceOption reference option} for on delete
	 */
	public SQLReferenceOption getOnDeleteOption(){
		return onDeleteOption;
	}
	
	/**
	 * @return The {@link SQLReferenceOption reference option} for on update
	 */
	public SQLReferenceOption getOnUpdateOption(){
		return onUpdateOption;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		StringBuilder constraint = new StringBuilder("FOREIGN KEY ");
		
		// Add columns
		constraint.append('(');
		for(String columnName: columnNames){
			constraint.append(columnName).append(", ");
		}
		// Remove last comma
		constraint.delete(constraint.length()-2, constraint.length());
		
		// Add reference table
		constraint.append(") REFERENCES ");
		constraint.append(referenceTable);
		
		// Add reference columns
		constraint.append(" (");
		for(String columnName: referenceColumnNames){
			constraint.append(columnName).append(", ");
		}
		// Remove last comma
		constraint.delete(constraint.length()-2, constraint.length());
		constraint.append(')');
		
		// Add onDelete if we have it
		if(onDeleteOption != null){
			constraint.append(" ON DELETE ").append(onDeleteOption);
		}
		
		// Add onUpdate if we have it
		if(onUpdateOption != null){
			constraint.append(" ON UPDATE ").append(onUpdateOption);
		}
		
		return constraint.toString();
	}
	
	/**
	 * The Column Names part of building a {@link ForeignKeyConstraint}
	 */
	public interface ColumnNames{
		
		/**
		 * @param columnNames The names of the columns for the foreign key
		 * @return this, to continue building
		 */
		ReferenceTable columnNames(String ... columnNames);
		
		/**
		 * @param columnNames The names of the columns for the foreign key
		 * @return this, to continue building
		 */
		ReferenceTable columnNames(List<String> columnNames);
		
		/**
		 * @param columnRefs The columns for the foreign key as {@link ColumnRef ColumnRefs}
		 * @return this, to continue building
		 */
		ReferenceTable columnRefs(ColumnRef ... columnRefs);
		
		/**
		 * @param columnRefs The columns for the foreign key as {@link ColumnRef ColumnRefs}
		 * @return this, to continue building
		 */
		ReferenceTable columnRefs(List<ColumnRef> columnRefs);
		
		/**
		 * @param columnDefs The columns for the foreign key as {@link ColumnDefinition ColumnDefinitions}
		 * @return this, to continue building
		 */
		ReferenceTable columnDefs(ColumnDefinition ... columnDefs);
		
		/**
		 * @param columnDefs The columns for the foreign key as {@link ColumnDefinition ColumnDefinitions}
		 * @return this, to continue building
		 */
		ReferenceTable columnDefs(List<ColumnDefinition> columnDefs);
	}
	
	/**
	 * The Reference Table part of building a {@link ForeignKeyConstraint}
	 */
	public interface ReferenceTable{
		
		/**
		 * @param referenceTable The name of the reference table for the foreign key
		 * @return this, to continue building
		 */
		ReferenceColumnNames references(String referenceTable);
		
		/**
		 * @param referenceTable The reference table for the foreign key as a {@link TableRef}
		 * @return this, to continue building
		 */
		ReferenceColumnNames references(TableRef referenceTable);
	}
	
	/**
	 * The Reference Column Names part of building a {@link ForeignKeyConstraint}
	 */
	public interface ReferenceColumnNames{
		
		/**
		 * @param referenceColumnNames The names of the columns from the reference table
		 * @return this, to continue building
		 */
		OptionsOrBuild referenceColumnNames(String ... referenceColumnNames);
		
		/**
		 * @param referenceColumnNames The names of the columns from the reference table
		 * @return this, to continue building
		 */
		OptionsOrBuild referenceColumnNames(List<String> referenceColumnNames);
		
		/**
		 * @param referenceColumnRefs The columns from the reference table as {@link ColumnRef ColumnRefs}
		 * @return this, to continue building
		 */
		OptionsOrBuild referenceColumnRefs(ColumnRef ... referenceColumnRefs);
		
		/**
		 * @param referenceColumnRefs The columns from the reference table as {@link ColumnRef ColumnRefs}
		 * @return this, to continue building
		 */
		OptionsOrBuild referenceColumnRefs(List<ColumnRef> referenceColumnRefs);
		
		/**
		 * @param referenceColumnDefs The columns from the reference table as {@link ColumnDefinition ColumnDefinitions}
		 * @return this, to continue building
		 */
		OptionsOrBuild referenceColumnDefs(ColumnDefinition ... referenceColumnDefs);
		
		/**
		 * @param referenceColumnDefs The columns from the reference table as {@link ColumnDefinition ColumnDefinitions}
		 * @return this, to continue building
		 */
		OptionsOrBuild referenceColumnDefs(List<ColumnDefinition> referenceColumnDefs);
	}
	
	/**
	 * The {@link SQLReferenceOption Options} and building part of building a {@link ForeignKeyConstraint}
	 */
	public interface OptionsOrBuild{
		
		/**
		 * @param onDeleteOption The {@link SQLReferenceOption reference option} for on delete
		 * @return this, to continue building
		 */
		OptionsOrBuild onDelete(SQLReferenceOption onDeleteOption);
		
		/**
		 * @param onUpdateOption The {@link SQLReferenceOption reference option} for on update
		 * @return this, to continue building
		 */
		OptionsOrBuild onUpdate(SQLReferenceOption onUpdateOption);
		
		/**
		 * Builds a new {@link ForeignKeyConstraint} using the set parameters
		 *
		 * @return The newly built {@link ForeignKeyConstraint}
		 */
		ForeignKeyConstraint build();
	}
}
