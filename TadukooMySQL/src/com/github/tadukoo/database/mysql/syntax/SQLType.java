package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.util.StringUtil;

/**
 * Represents a Type in MySQL
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public enum SQLType{
	/** Represents a Table in MySQL - TABLE */
	TABLE("TABLE"),
	/** Represents a Database in MySQL - DATABASE */
	DATABASE("DATABASE");
	
	/** The type as a String */
	private final String type;
	
	/**
	 * Constructs a new SQLType with the given parameters
	 *
	 * @param type The type as a String
	 */
	SQLType(String type){
		this.type = type;
	}
	
	/**
	 * Find a SQLType using its type as a String
	 *
	 * @param type The type as a String
	 * @return The found SQLType if one matched, or null
	 */
	public static SQLType fromType(String type){
		for(SQLType typeE: values()){
			if(StringUtil.equalsIgnoreCase(typeE.type, type)){
				return typeE;
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
