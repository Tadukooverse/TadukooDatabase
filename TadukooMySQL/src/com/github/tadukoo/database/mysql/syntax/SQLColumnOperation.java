package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.util.StringUtil;

/**
 * SQLColumnOperation represents an operation that can be performed on a column in MySQL
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public enum SQLColumnOperation{
	/** Add a new column - ADD */
	ADD("ADD"),
	/** Modify an existing column - MODIFY COLUMN */
	MODIFY("MODIFY COLUMN"),
	/** Drop an existing column - DROP COLUMN */
	DROP("DROP COLUMN");
	
	/** The type string */
	private final String type;
	
	/**
	 * Constructs a new {@link SQLColumnOperation} from the given parameters
	 *
	 * @param type The type string
	 */
	SQLColumnOperation(String type){
		this.type = type;
	}
	
	/**
	 * Find a {@link SQLColumnOperation} by its type string
	 *
	 * @param type The type string
	 * @return The found {@link SQLColumnOperation} or null
	 */
	public static SQLColumnOperation fromType(String type){
		for(SQLColumnOperation op: values()){
			if(StringUtil.equalsIgnoreCase(op.type, type)){
				return op;
			}
		}
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		return type;
	}
}
