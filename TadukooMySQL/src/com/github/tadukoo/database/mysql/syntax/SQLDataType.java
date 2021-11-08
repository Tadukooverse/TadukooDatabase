package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.util.StringUtil;

/**
 * SQL Data Type represents the data type for a MySQL table column
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public enum SQLDataType{
	/*
	 * String data types
	 */
	/** The CHAR type in MySQL */
	CHAR,
	/** The VARCHAR type in MySQL */
	VARCHAR,
	/** The BINARY type in MySQL */
	BINARY,
	/** The VARBINARY type in MySQL */
	VARBINARY,
	/** The TINYBLOB type in MySQL */
	TINYBLOB,
	/** The TINYTEXT type in MySQL */
	TINYTEXT,
	/** The TEXT type in MySQL */
	TEXT,
	/** The BLOB type in MySQL */
	BLOB,
	/** The MEDIUMTEXT type in MySQL */
	MEDIUMTEXT,
	/** The MEDIUMBLOB type in MySQL */
	MEDIUMBLOB,
	/** The LONGTEXT type in MySQL */
	LONGTEXT,
	/** The LONGBLOB type in MySQL */
	LONGBLOB,
	/** The ENUM type in MySQL */
	ENUM,
	/** The SET type in MySQL */
	SET,
	
	/*
	 * Numeric data types
	 */
	/** The BIT type in MySQL */
	BIT,
	/** The TINYINT type in MySQL */
	TINYINT,
	/** The BOOL type in MySQL */
	BOOL,
	/** The SMALLINT type in MySQL */
	SMALLINT,
	/** The MEDIUMINT type in MySQL */
	MEDIUMINT,
	/** The INTEGER type in MySQL */
	INTEGER,
	/** The BIGINT type in MySQL */
	BIGINT,
	/** The FLOAT type in MySQL */
	FLOAT,
	/** The DOUBLE Type in MySQL */
	DOUBLE,
	/** The DECIMAL type in MySQL */
	DECIMAL,
	
	/*
	 * Date and Time data types
	 */
	/** The DATE type in MySQL */
	DATE,
	/** The DATETIME type in MySQL */
	DATETIME,
	/** The TIMESTAMP type in MySQL */
	TIMESTAMP,
	/** The TIME type in MySQL */
	TIME,
	/** The YEAR type in MySQL */
	YEAR;
	
	/**
	 * Find a SQLDataType using its type as a String
	 *
	 * @param type The type as a String
	 * @return The found SQLDataType if one matched, or null
	 */
	public static SQLDataType fromType(String type){
		for(SQLDataType dataType: values()){
			if(StringUtil.equalsIgnoreCase(dataType.name(), type)){
				return dataType;
			}
		}
		return null;
	}
}
