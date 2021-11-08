package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.util.StringUtil;

/**
 * Represents a Type in MySQL
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public enum SQLType{
	/** Represents a Table in MySQL */
	TABLE,
	/** Represents a Database in MySQL */
	DATABASE;
	
	/**
	 * Find a SQLType using its type as a String
	 *
	 * @param type The type as a String
	 * @return The found SQLType if one matched, or null
	 */
	public static SQLType fromType(String type){
		for(SQLType typeE: values()){
			if(StringUtil.equalsIgnoreCase(typeE.name(), type)){
				return typeE;
			}
		}
		return null;
	}
}
