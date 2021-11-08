package com.github.tadukoo.database.mysql.syntax;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SQLDataTypeTest{
	
	@Test
	public void testToStringCHAR(){
		assertEquals("CHAR", SQLDataType.CHAR.toString());
	}
	
	@Test
	public void testToStringVARCHAR(){
		assertEquals("VARCHAR", SQLDataType.VARCHAR.toString());
	}
	
	@Test
	public void testToStringBINARY(){
		assertEquals("BINARY", SQLDataType.BINARY.toString());
	}
	
	@Test
	public void testToStringVARBINARY(){
		assertEquals("VARBINARY", SQLDataType.VARBINARY.toString());
	}
	
	@Test
	public void testToStringTINYBLOB(){
		assertEquals("TINYBLOB", SQLDataType.TINYBLOB.toString());
	}
	
	@Test
	public void testToStringTINYTEXT(){
		assertEquals("TINYTEXT", SQLDataType.TINYTEXT.toString());
	}
	
	@Test
	public void testToStringTEXT(){
		assertEquals("TEXT", SQLDataType.TEXT.toString());
	}
	
	@Test
	public void testToStringBLOB(){
		assertEquals("BLOB", SQLDataType.BLOB.toString());
	}
	
	@Test
	public void testToStringMEDIUMTEXT(){
		assertEquals("MEDIUMTEXT", SQLDataType.MEDIUMTEXT.toString());
	}
	
	@Test
	public void testToStringMEDIUMBLOB(){
		assertEquals("MEDIUMBLOB", SQLDataType.MEDIUMBLOB.toString());
	}
	
	@Test
	public void testToStringLONGTEXT(){
		assertEquals("LONGTEXT", SQLDataType.LONGTEXT.toString());
	}
	
	@Test
	public void testToStringLONGBLOB(){
		assertEquals("LONGBLOB", SQLDataType.LONGBLOB.toString());
	}
	
	@Test
	public void testToStringENUM(){
		assertEquals("ENUM", SQLDataType.ENUM.toString());
	}
	
	@Test
	public void testToStringSET(){
		assertEquals("SET", SQLDataType.SET.toString());
	}
	
	@Test
	public void testToStringBIT(){
		assertEquals("BIT", SQLDataType.BIT.toString());
	}
	
	@Test
	public void testToStringTINYINT(){
		assertEquals("TINYINT", SQLDataType.TINYINT.toString());
	}
	
	@Test
	public void testToStringBOOL(){
		assertEquals("BOOL", SQLDataType.BOOL.toString());
	}
	
	@Test
	public void testToStringSMALLINT(){
		assertEquals("SMALLINT", SQLDataType.SMALLINT.toString());
	}
	
	@Test
	public void testToStringMEDIUMINT(){
		assertEquals("MEDIUMINT", SQLDataType.MEDIUMINT.toString());
	}
	
	@Test
	public void testToStringINTEGER(){
		assertEquals("INTEGER", SQLDataType.INTEGER.toString());
	}
	
	@Test
	public void testToStringBIGINT(){
		assertEquals("BIGINT", SQLDataType.BIGINT.toString());
	}
	
	@Test
	public void testToStringFLOAT(){
		assertEquals("FLOAT", SQLDataType.FLOAT.toString());
	}
	
	@Test
	public void testToStringDOUBLE(){
		assertEquals("DOUBLE", SQLDataType.DOUBLE.toString());
	}
	
	@Test
	public void testToStringDECIMAL(){
		assertEquals("DECIMAL", SQLDataType.DECIMAL.toString());
	}
	
	@Test
	public void testToStringDATE(){
		assertEquals("DATE", SQLDataType.DATE.toString());
	}
	
	@Test
	public void testToStringDATETIME(){
		assertEquals("DATETIME", SQLDataType.DATETIME.toString());
	}
	
	@Test
	public void testToStringTIMESTAMP(){
		assertEquals("TIMESTAMP", SQLDataType.TIMESTAMP.toString());
	}
	
	@Test
	public void testToStringTIME(){
		assertEquals("TIME", SQLDataType.TIME.toString());
	}
	
	@Test
	public void testToStringYEAR(){
		assertEquals("YEAR", SQLDataType.YEAR.toString());
	}
	
	/*
	 * From String
	 */
	
	@Test
	public void testFromTypeCHAR(){
		assertEquals(SQLDataType.CHAR, SQLDataType.fromType("CHAR"));
	}
	
	@Test
	public void testFromTypeVARCHAR(){
		assertEquals(SQLDataType.VARCHAR, SQLDataType.fromType("VARCHAR"));
	}
	
	@Test
	public void testFromTypeBINARY(){
		assertEquals(SQLDataType.BINARY, SQLDataType.fromType("BINARY"));
	}
	
	@Test
	public void testFromTypeVARBINARY(){
		assertEquals(SQLDataType.VARBINARY, SQLDataType.fromType("VARBINARY"));
	}
	
	@Test
	public void testFromTypeTINYBLOB(){
		assertEquals(SQLDataType.TINYBLOB, SQLDataType.fromType("TINYBLOB"));
	}
	
	@Test
	public void testFromTypeTINYTEXT(){
		assertEquals(SQLDataType.TINYTEXT, SQLDataType.fromType("TINYTEXT"));
	}
	
	@Test
	public void testFromTypeTEXT(){
		assertEquals(SQLDataType.TEXT, SQLDataType.fromType("TEXT"));
	}
	
	@Test
	public void testFromTypeBLOB(){
		assertEquals(SQLDataType.BLOB, SQLDataType.fromType("BLOB"));
	}
	
	@Test
	public void testFromTypeMEDIUMTEXT(){
		assertEquals(SQLDataType.MEDIUMTEXT, SQLDataType.fromType("MEDIUMTEXT"));
	}
	
	@Test
	public void testFromTypeMEDIUMBLOB(){
		assertEquals(SQLDataType.MEDIUMBLOB, SQLDataType.fromType("MEDIUMBLOB"));
	}
	
	@Test
	public void testFromTypeLONGTEXT(){
		assertEquals(SQLDataType.LONGTEXT, SQLDataType.fromType("LONGTEXT"));
	}
	
	@Test
	public void testFromTypeLONGBLOB(){
		assertEquals(SQLDataType.LONGBLOB, SQLDataType.fromType("LONGBLOB"));
	}
	
	@Test
	public void testFromTypeENUM(){
		assertEquals(SQLDataType.ENUM, SQLDataType.fromType("ENUM"));
	}
	
	@Test
	public void testFromTypeSET(){
		assertEquals(SQLDataType.SET, SQLDataType.fromType("SET"));
	}
	
	@Test
	public void testFromTypeBIT(){
		assertEquals(SQLDataType.BIT, SQLDataType.fromType("BIT"));
	}
	
	@Test
	public void testFromTypeTINYINT(){
		assertEquals(SQLDataType.TINYINT, SQLDataType.fromType("TINYINT"));
	}
	
	@Test
	public void testFromTypeBOOL(){
		assertEquals(SQLDataType.BOOL, SQLDataType.fromType("BOOL"));
	}
	
	@Test
	public void testFromTypeSMALLINT(){
		assertEquals(SQLDataType.SMALLINT, SQLDataType.fromType("SMALLINT"));
	}
	
	@Test
	public void testFromTypeMEDIUMINT(){
		assertEquals(SQLDataType.MEDIUMINT, SQLDataType.fromType("MEDIUMINT"));
	}
	
	@Test
	public void testFromTypeINTEGER(){
		assertEquals(SQLDataType.INTEGER, SQLDataType.fromType("INTEGER"));
	}
	
	@Test
	public void testFromTypeBIGINT(){
		assertEquals(SQLDataType.BIGINT, SQLDataType.fromType("BIGINT"));
	}
	
	@Test
	public void testFromTypeFLOAT(){
		assertEquals(SQLDataType.FLOAT, SQLDataType.fromType("FLOAT"));
	}
	
	@Test
	public void testFromTypeDOUBLE(){
		assertEquals(SQLDataType.DOUBLE, SQLDataType.fromType("DOUBLE"));
	}
	
	@Test
	public void testFromTypeDECIMAL(){
		assertEquals(SQLDataType.DECIMAL, SQLDataType.fromType("DECIMAL"));
	}
	
	@Test
	public void testFromTypeDATE(){
		assertEquals(SQLDataType.DATE, SQLDataType.fromType("DATE"));
	}
	
	@Test
	public void testFromTypeDATETIME(){
		assertEquals(SQLDataType.DATETIME, SQLDataType.fromType("DATETIME"));
	}
	
	@Test
	public void testFromTypeTIMESTAMP(){
		assertEquals(SQLDataType.TIMESTAMP, SQLDataType.fromType("TIMESTAMP"));
	}
	
	@Test
	public void testFromTypeTIME(){
		assertEquals(SQLDataType.TIME, SQLDataType.fromType("TIME"));
	}
	
	@Test
	public void testFromTypeYEAR(){
		assertEquals(SQLDataType.YEAR, SQLDataType.fromType("YEAR"));
	}
	
	@Test
	public void testFromTypeFail(){
		assertNull(SQLDataType.fromType("garbage_string"));
	}
}
