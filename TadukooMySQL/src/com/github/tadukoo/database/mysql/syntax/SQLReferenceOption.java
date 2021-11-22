package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.util.StringUtil;

/**
 * SQL Reference Option represents a Reference Option in MySQL
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public enum SQLReferenceOption{
	/** Rejects the delete or update operation for the parent table.
	 * This is also the default operation - RESTRICT */
	RESTRICT("RESTRICT"),
	/** Delete or update the row from the parent table and automatically
	 * delete or update the matching rows in the child table - CASCADE */
	CASCADE("CASCADE"),
	/** Delete or update the row from the parent table and set the foreign key
	 * column or columns in the child table to NULL - SET NULL */
	SET_NULL("SET NULL"),
	/** Rejects the delete or update operation for the parent table.
	 * This is the same as {@link #RESTRICT} - NO ACTION */
	NO_ACTION("NO ACTION"),
	/** This action is recognized by the MySQL parser, but both InnoDB and
	 * NDB reject table definitions containing this - SET DEFAULT */
	SET_DEFAULT("SET DEFAULT");
	
	/** The option value */
	private final String option;
	
	/**
	 * Constructs a new {@link SQLReferenceOption} with the given parameter
	 *
	 * @param option The option value
	 */
	SQLReferenceOption(String option){
		this.option = option;
	}
	
	/**
	 * Find a {@link SQLReferenceOption} with the given option value
	 *
	 * @param option The option value to find
	 * @return The found {@link SQLReferenceOption} or null
	 */
	public static SQLReferenceOption fromOption(String option){
		for(SQLReferenceOption opt: values()){
			if(StringUtil.equalsIgnoreCase(opt.option, option)){
				return opt;
			}
		}
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		return option;
	}
}
