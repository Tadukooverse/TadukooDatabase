package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class ColumnDefinitionTest{
	private String columnName;
	private int size;
	private List<String> values;
	private String valuesString;
	private int digits;
	private int fractionalSecondsPrecision;
	private ColumnDefinition charDef;
	private ColumnDefinition varcharDef;
	private ColumnDefinition binaryDef;
	private ColumnDefinition varbinaryDef;
	private ColumnDefinition tinyblobDef;
	private ColumnDefinition tinytextDef;
	private ColumnDefinition textDef;
	private ColumnDefinition blobDef;
	private ColumnDefinition mediumtextDef;
	private ColumnDefinition mediumblobDef;
	private ColumnDefinition longtextDef;
	private ColumnDefinition longblobDef;
	private ColumnDefinition enumDef;
	private ColumnDefinition setDef;
	private ColumnDefinition bitDef;
	private ColumnDefinition tinyintDef;
	private ColumnDefinition boolDef;
	private ColumnDefinition smallintDef;
	private ColumnDefinition mediumintDef;
	private ColumnDefinition integerDef;
	private ColumnDefinition bigintDef;
	private ColumnDefinition floatDef;
	private ColumnDefinition doubleDef;
	private ColumnDefinition decimalDef;
	private ColumnDefinition dateDef;
	private ColumnDefinition datetimeDef;
	private ColumnDefinition timestampDef;
	private ColumnDefinition timeDef;
	private ColumnDefinition yearDef;
	
	@BeforeEach
	public void setup(){
		columnName = "Test";
		size = 10;
		values = ListUtil.createList("Derp", "Plop");
		valuesString = "Derp, Plop";
		digits = 15;
		fractionalSecondsPrecision = 3;
		charDef = ColumnDefinition.builder()
				.columnName(columnName)
				.character()
				.size(size)
				.build();
		varcharDef = ColumnDefinition.builder()
				.columnName(columnName)
				.varchar()
				.size(size)
				.build();
		binaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.binary()
				.size(size)
				.build();
		varbinaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.varbinary()
				.size(size)
				.build();
		tinyblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyblob()
				.build();
		tinytextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinytext()
				.build();
		textDef = ColumnDefinition.builder()
				.columnName(columnName)
				.text()
				.size(size)
				.build();
		blobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.blob()
				.size(size)
				.build();
		mediumtextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumtext()
				.build();
		mediumblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumblob()
				.build();
		longtextDef = ColumnDefinition.builder()
				.columnName(columnName)
				.longtext()
				.build();
		longblobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.longblob()
				.build();
		enumDef = ColumnDefinition.builder()
				.columnName(columnName)
				.enumeration()
				.values(values)
				.build();
		setDef = ColumnDefinition.builder()
				.columnName(columnName)
				.set()
				.values(values)
				.build();
		bitDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bit()
				.size(size)
				.build();
		tinyintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyint()
				.size(size)
				.build();
		boolDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bool()
				.build();
		smallintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.smallint()
				.size(size)
				.build();
		mediumintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumint()
				.size(size)
				.build();
		integerDef = ColumnDefinition.builder()
				.columnName(columnName)
				.integer()
				.size(size)
				.build();
		bigintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bigint()
				.size(size)
				.build();
		floatDef = ColumnDefinition.builder()
				.columnName(columnName)
				.floatType()
				.sizeAndDigits(size, digits)
				.build();
		doubleDef = ColumnDefinition.builder()
				.columnName(columnName)
				.doubleType()
				.sizeAndDigits(size, digits)
				.build();
		decimalDef = ColumnDefinition.builder()
				.columnName(columnName)
				.decimal()
				.sizeAndDigits(size, digits)
				.build();
		dateDef = ColumnDefinition.builder()
				.columnName(columnName)
				.date()
				.build();
		datetimeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.datetime()
				.fractionalSecondsPrecision(fractionalSecondsPrecision)
				.build();
		timestampDef = ColumnDefinition.builder()
				.columnName(columnName)
				.timestamp()
				.fractionalSecondsPrecision(fractionalSecondsPrecision)
				.build();
		timeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.time()
				.fractionalSecondsPrecision(fractionalSecondsPrecision)
				.build();
		yearDef = ColumnDefinition.builder()
				.columnName(columnName)
				.year()
				.build();
	}
	
	/*
	 * Setting fields
	 */
	
	@Test
	public void testSetColumnName(){
		assertEquals(columnName, charDef.getColumnName());
	}
	
	@Test
	public void testSetSize(){
		assertEquals(size, charDef.getSize());
	}
	
	@Test
	public void testSetValues(){
		assertEquals(values, enumDef.getValues());
	}
	
	@Test
	public void testSetSizeAndDigits(){
		assertEquals(size, floatDef.getSize());
		assertEquals(digits, floatDef.getDigits());
	}
	
	@Test
	public void testSetFractionalSecondsPrecision(){
		assertEquals(fractionalSecondsPrecision, datetimeDef.getFractionalSecondsPrecision());
	}
	
	/*
	 * Default Fields
	 */
	
	@Test
	public void testDefaultSize(){
		assertNull(tinyblobDef.getSize());
	}
	
	@Test
	public void testDefaultValues(){
		List<String> values = charDef.getValues();
		assertNotNull(values);
		assertEquals(0, values.size());
	}
	
	@Test
	public void testDefaultDigits(){
		assertNull(charDef.getDigits());
	}
	
	@Test
	public void testDefaultFractionalSecondsPrecision(){
		assertNull(charDef.getFractionalSecondsPrecision());
	}
	
	/*
	 * Data Type settings
	 */
	
	@Test
	public void testSetChar(){
		assertEquals(SQLDataType.CHAR, charDef.getDataType());
	}
	
	@Test
	public void testSetVarchar(){
		assertEquals(SQLDataType.VARCHAR, varcharDef.getDataType());
	}
	
	@Test
	public void testSetBinary(){
		assertEquals(SQLDataType.BINARY, binaryDef.getDataType());
	}
	
	@Test
	public void testSetVarbinary(){
		assertEquals(SQLDataType.VARBINARY, varbinaryDef.getDataType());
	}
	
	@Test
	public void testSetTinyblob(){
		assertEquals(SQLDataType.TINYBLOB, tinyblobDef.getDataType());
	}
	
	@Test
	public void testSetTinytext(){
		assertEquals(SQLDataType.TINYTEXT, tinytextDef.getDataType());
	}
	
	@Test
	public void testSetText(){
		assertEquals(SQLDataType.TEXT, textDef.getDataType());
	}
	
	@Test
	public void testSetBlob(){
		assertEquals(SQLDataType.BLOB, blobDef.getDataType());
	}
	
	@Test
	public void testSetMediumtext(){
		assertEquals(SQLDataType.MEDIUMTEXT, mediumtextDef.getDataType());
	}
	
	@Test
	public void testSetMediumblob(){
		assertEquals(SQLDataType.MEDIUMBLOB, mediumblobDef.getDataType());
	}
	
	@Test
	public void testSetLongtext(){
		assertEquals(SQLDataType.LONGTEXT, longtextDef.getDataType());
	}
	
	@Test
	public void testSetLongblob(){
		assertEquals(SQLDataType.LONGBLOB, longblobDef.getDataType());
	}
	
	@Test
	public void testSetEnum(){
		assertEquals(SQLDataType.ENUM, enumDef.getDataType());
	}
	
	@Test
	public void testSetSet(){
		assertEquals(SQLDataType.SET, setDef.getDataType());
	}
	
	@Test
	public void testSetBit(){
		assertEquals(SQLDataType.BIT, bitDef.getDataType());
	}
	
	@Test
	public void testSetTinyint(){
		assertEquals(SQLDataType.TINYINT, tinyintDef.getDataType());
	}
	
	@Test
	public void testSetBool(){
		assertEquals(SQLDataType.BOOL, boolDef.getDataType());
	}
	
	@Test
	public void testSetSmallint(){
		assertEquals(SQLDataType.SMALLINT, smallintDef.getDataType());
	}
	
	@Test
	public void testSetMediumint(){
		assertEquals(SQLDataType.MEDIUMINT, mediumintDef.getDataType());
	}
	
	@Test
	public void testSetInteger(){
		assertEquals(SQLDataType.INTEGER, integerDef.getDataType());
	}
	
	@Test
	public void testSetBigint(){
		assertEquals(SQLDataType.BIGINT, bigintDef.getDataType());
	}
	
	@Test
	public void testSetFloat(){
		assertEquals(SQLDataType.FLOAT, floatDef.getDataType());
	}
	
	@Test
	public void testSetDouble(){
		assertEquals(SQLDataType.DOUBLE, doubleDef.getDataType());
	}
	
	@Test
	public void testSetDecimal(){
		assertEquals(SQLDataType.DECIMAL, decimalDef.getDataType());
	}
	
	@Test
	public void testSetDate(){
		assertEquals(SQLDataType.DATE, dateDef.getDataType());
	}
	
	@Test
	public void testSetDatetime(){
		assertEquals(SQLDataType.DATETIME, datetimeDef.getDataType());
	}
	
	@Test
	public void testSetTimestamp(){
		assertEquals(SQLDataType.TIMESTAMP, timestampDef.getDataType());
	}
	
	@Test
	public void testSetTime(){
		assertEquals(SQLDataType.TIME, timeDef.getDataType());
	}
	
	@Test
	public void testSetYear(){
		assertEquals(SQLDataType.YEAR, yearDef.getDataType());
	}
	
	/*
	 * Data Type Set Defaults/Alternates
	 */
	
	@Test
	public void testCharDefaultSize(){
		charDef = ColumnDefinition.builder()
				.columnName(columnName)
				.character()
				.defaultSize()
				.build();
		assertNull(charDef.getSize());
	}
	
	@Test
	public void testBinaryDefaultSize(){
		binaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.binary()
				.defaultSize()
				.build();
		assertNull(binaryDef.getSize());
	}
	
	@Test
	public void testTextDefaultSize(){
		textDef = ColumnDefinition.builder()
				.columnName(columnName)
				.text()
				.defaultSize()
				.build();
		assertNull(textDef.getSize());
	}
	
	@Test
	public void testTextLongSize(){
		long sizeL = 100L;
		textDef = ColumnDefinition.builder()
				.columnName(columnName)
				.text()
				.size(sizeL)
				.build();
		assertEquals(sizeL, textDef.getSize());
	}
	
	@Test
	public void testBlobDefaultSize(){
		blobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.blob()
				.defaultSize()
				.build();
		assertNull(blobDef.getSize());
	}
	
	@Test
	public void testBlobLongSize(){
		long sizeL = 100L;
		blobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.blob()
				.size(sizeL)
				.build();
		assertEquals(sizeL, blobDef.getSize());
	}
	
	@Test
	public void testEnumIndividualValues(){
		enumDef = ColumnDefinition.builder()
				.columnName(columnName)
				.enumeration()
				.values("Derp", "Plop")
				.build();
		List<String> values = enumDef.getValues();
		assertNotNull(values);
		assertEquals(2, values.size());
		assertEquals("Derp", values.get(0));
		assertEquals("Plop", values.get(1));
	}
	
	@Test
	public void testSetIndividualValues(){
		setDef = ColumnDefinition.builder()
				.columnName(columnName)
				.set()
				.values("Derp", "Plop")
				.build();
		List<String> values = setDef.getValues();
		assertNotNull(values);
		assertEquals(2, values.size());
		assertEquals("Derp", values.get(0));
		assertEquals("Plop", values.get(1));
	}
	
	@Test
	public void testBitDefaultSize(){
		bitDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bit()
				.defaultSize()
				.build();
		assertNull(bitDef.getSize());
	}
	
	@Test
	public void testTinyintDefaultSize(){
		tinyintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyint()
				.defaultSize()
				.build();
		assertNull(tinyintDef.getSize());
	}
	
	@Test
	public void testSmallintDefaultSize(){
		smallintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.smallint()
				.defaultSize()
				.build();
		assertNull(smallintDef.getSize());
	}
	
	@Test
	public void testMediumintDefaultSize(){
		mediumintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumint()
				.defaultSize()
				.build();
		assertNull(mediumintDef.getSize());
	}
	
	@Test
	public void testIntegerDefaultSize(){
		integerDef = ColumnDefinition.builder()
				.columnName(columnName)
				.integer()
				.defaultSize()
				.build();
		assertNull(integerDef.getSize());
	}
	
	@Test
	public void testBigintDefaultSize(){
		bigintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bigint()
				.defaultSize()
				.build();
		assertNull(bigintDef.getSize());
	}
	
	@Test
	public void testFloatDefaultSizeAndDigits(){
		floatDef = ColumnDefinition.builder()
				.columnName(columnName)
				.floatType()
				.defaultSizeAndDigits()
				.build();
		assertNull(floatDef.getSize());
		assertNull(floatDef.getDigits());
	}
	
	@Test
	public void testDoubleDefaultSizeAndDigits(){
		doubleDef = ColumnDefinition.builder()
				.columnName(columnName)
				.doubleType()
				.defaultSizeAndDigits()
				.build();
		assertNull(doubleDef.getSize());
		assertNull(doubleDef.getDigits());
	}
	
	@Test
	public void testDecimalDefaultSizeAndDigits(){
		decimalDef = ColumnDefinition.builder()
				.columnName(columnName)
				.decimal()
				.defaultSizeAndDigits()
				.build();
		assertNull(decimalDef.getSize());
		assertNull(decimalDef.getDigits());
	}
	
	@Test
	public void testDatetimeDefaultFractionalSecondsPrecision(){
		datetimeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.datetime()
				.defaultFractionalSecondsPrecision()
				.build();
		assertNull(datetimeDef.getFractionalSecondsPrecision());
	}
	
	@Test
	public void testTimestampDefaultFractionalSecondsPrecision(){
		timestampDef = ColumnDefinition.builder()
				.columnName(columnName)
				.timestamp()
				.defaultFractionalSecondsPrecision()
				.build();
		assertNull(timestampDef.getFractionalSecondsPrecision());
	}
	
	@Test
	public void testTimeDefaultFractionalSecondsPrecision(){
		timeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.time()
				.defaultFractionalSecondsPrecision()
				.build();
		assertNull(timeDef.getFractionalSecondsPrecision());
	}
	
	/*
	 * Test Errors in Building
	 */
	
	@Test
	public void testCHARMissingColumnName(){
		try{
			charDef = ColumnDefinition.builder()
					.columnName(null)
					.character()
					.defaultSize()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \ncolumnName is required!",
					e.getMessage());
		}
	}
	
	@Test
	public void testBITLowSize(){
		try{
			bitDef = ColumnDefinition.builder()
					.columnName(columnName)
					.bit()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.BIT + " size must be between 1 and 64 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBITHighSize(){
		try{
			bitDef = ColumnDefinition.builder()
					.columnName(columnName)
					.bit()
					.size(65)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.BIT + " size must be between 1 and 64 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBITAllErrors(){
		try{
			bitDef = ColumnDefinition.builder()
					.columnName(null)
					.bit()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.BIT + " size must be between 1 and 64 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testDECIMALLowSize(){
		try{
			decimalDef = ColumnDefinition.builder()
					.columnName(columnName)
					.decimal()
					.sizeAndDigits(0, digits)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.DECIMAL + " size must be between 1 and 65 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testDECIMALHighSize(){
		try{
			decimalDef = ColumnDefinition.builder()
					.columnName(columnName)
					.decimal()
					.sizeAndDigits(66, digits)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.DECIMAL + " size must be between 1 and 65 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testDECIMALLowDigits(){
		try{
			decimalDef = ColumnDefinition.builder()
					.columnName(columnName)
					.decimal()
					.sizeAndDigits(size, -1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.DECIMAL + " digits must be between 0 and 30 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testDECIMALHighDigits(){
		try{
			decimalDef = ColumnDefinition.builder()
					.columnName(columnName)
					.decimal()
					.sizeAndDigits(size, 31)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.DECIMAL + " digits must be between 0 and 30 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testDECIMALAllErrors(){
		try{
			decimalDef = ColumnDefinition.builder()
					.columnName(null)
					.decimal()
					.sizeAndDigits(0, -1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.DECIMAL + " size must be between 1 and 65 (or defaulted)!\n" +
					"For " + SQLDataType.DECIMAL + " digits must be between 0 and 30 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testCHARLowSize(){
		try{
			charDef = ColumnDefinition.builder()
					.columnName(columnName)
					.character()
					.size(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.CHAR + " size must be between 0 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testCHARHighSize(){
		try{
			charDef = ColumnDefinition.builder()
					.columnName(columnName)
					.character()
					.size(256)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.CHAR + " size must be between 0 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testCHARAllErrors(){
		try{
			charDef = ColumnDefinition.builder()
					.columnName(null)
					.character()
					.size(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.CHAR + " size must be between 0 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBINARYLowSize(){
		try{
			binaryDef = ColumnDefinition.builder()
					.columnName(columnName)
					.binary()
					.size(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.BINARY + " size must be between 0 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBINARYHighSize(){
		try{
			binaryDef = ColumnDefinition.builder()
					.columnName(columnName)
					.binary()
					.size(256)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.BINARY + " size must be between 0 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBINARYAllErrors(){
		try{
			binaryDef = ColumnDefinition.builder()
					.columnName(null)
					.binary()
					.size(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.BINARY + " size must be between 0 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTINYINTLowSize(){
		try{
			tinyintDef = ColumnDefinition.builder()
					.columnName(columnName)
					.tinyint()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.TINYINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTINYINTHighSize(){
		try{
			tinyintDef = ColumnDefinition.builder()
					.columnName(columnName)
					.tinyint()
					.size(256)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.TINYINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTINYINTAllErrors(){
		try{
			tinyintDef = ColumnDefinition.builder()
					.columnName(null)
					.tinyint()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.TINYINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testSMALLINTLowSize(){
		try{
			smallintDef = ColumnDefinition.builder()
					.columnName(columnName)
					.smallint()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.SMALLINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testSMALLINTHighSize(){
		try{
			smallintDef = ColumnDefinition.builder()
					.columnName(columnName)
					.smallint()
					.size(256)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.SMALLINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testSMALLINTAllErrors(){
		try{
			smallintDef = ColumnDefinition.builder()
					.columnName(null)
					.smallint()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.SMALLINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testMEDIUMINTLowSize(){
		try{
			mediumintDef = ColumnDefinition.builder()
					.columnName(columnName)
					.mediumint()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.MEDIUMINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testMEDIUMINTHighSize(){
		try{
			mediumintDef = ColumnDefinition.builder()
					.columnName(columnName)
					.mediumint()
					.size(256)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.MEDIUMINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testMEDIUMINTAllErrors(){
		try{
			mediumintDef = ColumnDefinition.builder()
					.columnName(null)
					.mediumint()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.MEDIUMINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testINTEGERLowSize(){
		try{
			integerDef = ColumnDefinition.builder()
					.columnName(columnName)
					.integer()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.INTEGER + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testINTEGERHighSize(){
		try{
			integerDef = ColumnDefinition.builder()
					.columnName(columnName)
					.integer()
					.size(256)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.INTEGER + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testINTEGERAllErrors(){
		try{
			integerDef = ColumnDefinition.builder()
					.columnName(null)
					.integer()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.INTEGER + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBIGINTLowSize(){
		try{
			bigintDef = ColumnDefinition.builder()
					.columnName(columnName)
					.bigint()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.BIGINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBIGINTHighSize(){
		try{
			bigintDef = ColumnDefinition.builder()
					.columnName(columnName)
					.bigint()
					.size(256)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.BIGINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBIGINTAllErrors(){
		try{
			bigintDef = ColumnDefinition.builder()
					.columnName(null)
					.bigint()
					.size(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.BIGINT + " size must be between 1 and 255 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testVARCHARLowSize(){
		try{
			varcharDef = ColumnDefinition.builder()
					.columnName(columnName)
					.varchar()
					.size(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.VARCHAR + " size must be between 0 and 65,535!", e.getMessage());
		}
	}
	
	@Test
	public void testVARCHARHighSize(){
		try{
			varcharDef = ColumnDefinition.builder()
					.columnName(columnName)
					.varchar()
					.size(65536)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.VARCHAR + " size must be between 0 and 65,535!", e.getMessage());
		}
	}
	
	@Test
	public void testVARCHARAllErrors(){
		try{
			varcharDef = ColumnDefinition.builder()
					.columnName(null)
					.varchar()
					.size(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.VARCHAR + " size must be between 0 and 65,535!", e.getMessage());
		}
	}
	
	@Test
	public void testVARBINARYLowSize(){
		try{
			varbinaryDef = ColumnDefinition.builder()
					.columnName(columnName)
					.varbinary()
					.size(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.VARBINARY + " size must be between 0 and 65,535!", e.getMessage());
		}
	}
	
	@Test
	public void testVARBINARYHighSize(){
		try{
			varbinaryDef = ColumnDefinition.builder()
					.columnName(columnName)
					.varbinary()
					.size(65536)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.VARBINARY + " size must be between 0 and 65,535!", e.getMessage());
		}
	}
	
	@Test
	public void testVARBINARYAllErrors(){
		try{
			varbinaryDef = ColumnDefinition.builder()
					.columnName(null)
					.varbinary()
					.size(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.VARBINARY + " size must be between 0 and 65,535!", e.getMessage());
		}
	}
	
	@Test
	public void testTEXTLowSize(){
		try{
			textDef = ColumnDefinition.builder()
					.columnName(columnName)
					.text()
					.size(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.TEXT + " size must be between 0 and 4,294,967,295 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTEXTHighSize(){
		try{
			textDef = ColumnDefinition.builder()
					.columnName(columnName)
					.text()
					.size(4294967296L)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.TEXT + " size must be between 0 and 4,294,967,295 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTEXTAllErrors(){
		try{
			textDef = ColumnDefinition.builder()
					.columnName(null)
					.text()
					.size(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.TEXT + " size must be between 0 and 4,294,967,295 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBLOBLowSize(){
		try{
			blobDef = ColumnDefinition.builder()
					.columnName(columnName)
					.blob()
					.size(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.BLOB + " size must be between 0 and 4,294,967,295 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBLOBHighSize(){
		try{
			blobDef = ColumnDefinition.builder()
					.columnName(columnName)
					.blob()
					.size(4294967296L)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.BLOB + " size must be between 0 and 4,294,967,295 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testBLOBAllErrors(){
		try{
			blobDef = ColumnDefinition.builder()
					.columnName(null)
					.blob()
					.size(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.BLOB + " size must be between 0 and 4,294,967,295 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testENUMEmptyValues(){
		try{
			enumDef = ColumnDefinition.builder()
					.columnName(columnName)
					.enumeration()
					.values((ArrayList<String>) null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"Must specify values for " + SQLDataType.ENUM + "!", e.getMessage());
		}
	}
	
	@Test
	public void testENUMHighValues(){
		try{
			List<String> values = new ArrayList<>();
			for(int i = 0; i < 65536; i++){
				values.add("");
			}
			enumDef = ColumnDefinition.builder()
					.columnName(columnName)
					.enumeration()
					.values(values)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.ENUM + " values must be between 1 and 65,535 in size!", e.getMessage());
		}
	}
	
	@Test
	public void testENUMAllErrors(){
		try{
			enumDef = ColumnDefinition.builder()
					.columnName(null)
					.enumeration()
					.values(new ArrayList<>())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"Must specify values for " + SQLDataType.ENUM + "!", e.getMessage());
		}
	}
	
	@Test
	public void testSETEmptyValues(){
		try{
			setDef = ColumnDefinition.builder()
					.columnName(columnName)
					.set()
					.values((ArrayList<String>) null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"Must specify values for " + SQLDataType.SET + "!", e.getMessage());
		}
	}
	
	@Test
	public void testSETHighValues(){
		try{
			List<String> values = new ArrayList<>();
			for(int i = 0; i < 65; i++){
				values.add("");
			}
			setDef = ColumnDefinition.builder()
					.columnName(columnName)
					.set()
					.values(values)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.SET + " values must be between 1 and 64 in size!", e.getMessage());
		}
	}
	
	@Test
	public void testSETAllErrors(){
		try{
			setDef = ColumnDefinition.builder()
					.columnName(null)
					.set()
					.values(new ArrayList<>())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"Must specify values for " + SQLDataType.SET + "!", e.getMessage());
		}
	}
	
	@Test
	public void testDATETIMELowFSP(){
		try{
			datetimeDef = ColumnDefinition.builder()
					.columnName(columnName)
					.datetime()
					.fractionalSecondsPrecision(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.DATETIME + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testDATETIMEHighFSP(){
		try{
			datetimeDef = ColumnDefinition.builder()
					.columnName(columnName)
					.datetime()
					.fractionalSecondsPrecision(7)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.DATETIME + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testDATETIMEAllErrors(){
		try{
			datetimeDef = ColumnDefinition.builder()
					.columnName(null)
					.datetime()
					.fractionalSecondsPrecision(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.DATETIME + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTIMESTAMPLowFSP(){
		try{
			timestampDef = ColumnDefinition.builder()
					.columnName(columnName)
					.timestamp()
					.fractionalSecondsPrecision(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.TIMESTAMP + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTIMESTAMPHighFSP(){
		try{
			timestampDef = ColumnDefinition.builder()
					.columnName(columnName)
					.timestamp()
					.fractionalSecondsPrecision(7)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.TIMESTAMP + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTIMESTAMPAllErrors(){
		try{
			timestampDef = ColumnDefinition.builder()
					.columnName(null)
					.timestamp()
					.fractionalSecondsPrecision(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.TIMESTAMP + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTIMELowFSP(){
		try{
			timeDef = ColumnDefinition.builder()
					.columnName(columnName)
					.time()
					.fractionalSecondsPrecision(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.TIME + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTIMEHighFSP(){
		try{
			timeDef = ColumnDefinition.builder()
					.columnName(columnName)
					.time()
					.fractionalSecondsPrecision(7)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"For " + SQLDataType.TIME + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	@Test
	public void testTIMEAllErrors(){
		try{
			timeDef = ColumnDefinition.builder()
					.columnName(null)
					.time()
					.fractionalSecondsPrecision(-1)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered in building a ColumnDefinition: \n" +
					"columnName is required!\n" +
					"For " + SQLDataType.TIME + " fractionalSecondsPrecision must be between " +
					"0 and 6 (or defaulted)!", e.getMessage());
		}
	}
	
	/*
	 * toString Tests
	 */
	
	@Test
	public void testToStringCHAR(){
		assertEquals(columnName + " " + SQLDataType.CHAR + "(" + size + ")", charDef.toString());
	}
	
	@Test
	public void testToStringCHARDefaultSize(){
		charDef = ColumnDefinition.builder()
				.columnName(columnName)
				.character()
				.defaultSize()
				.build();
		assertEquals(columnName + " " + SQLDataType.CHAR, charDef.toString());
	}
	
	@Test
	public void testToStringVARCHAR(){
		assertEquals(columnName + " " + SQLDataType.VARCHAR + "(" + size + ")", varcharDef.toString());
	}
	
	@Test
	public void testToStringBINARY(){
		assertEquals(columnName + " " + SQLDataType.BINARY + "(" + size + ")", binaryDef.toString());
	}
	
	@Test
	public void testToStringBINARYDefaultSize(){
		binaryDef = ColumnDefinition.builder()
				.columnName(columnName)
				.binary()
				.defaultSize()
				.build();
		assertEquals(columnName + " " + SQLDataType.BINARY, binaryDef.toString());
	}
	
	@Test
	public void testToStringVARBINARY(){
		assertEquals(columnName + " " + SQLDataType.VARBINARY + "(" + size + ")", varbinaryDef.toString());
	}
	
	@Test
	public void testToStringTINYBLOB(){
		assertEquals(columnName + " " + SQLDataType.TINYBLOB, tinyblobDef.toString());
	}
	
	@Test
	public void testToStringTINYTEXT(){
		assertEquals(columnName + " " + SQLDataType.TINYTEXT, tinytextDef.toString());
	}
	
	@Test
	public void testToStringTEXT(){
		assertEquals(columnName + " " + SQLDataType.TEXT + "(" + size + ")", textDef.toString());
	}
	
	@Test
	public void testToStringTEXTDefaultSize(){
		textDef = ColumnDefinition.builder()
				.columnName(columnName)
				.text()
				.defaultSize()
				.build();
		assertEquals(columnName + " " + SQLDataType.TEXT, textDef.toString());
	}
	
	@Test
	public void testToStringBLOB(){
		assertEquals(columnName + " " + SQLDataType.BLOB + "(" + size + ")", blobDef.toString());
	}
	
	@Test
	public void testToStringBLOBDefaultSize(){
		blobDef = ColumnDefinition.builder()
				.columnName(columnName)
				.blob()
				.defaultSize()
				.build();
		assertEquals(columnName + " " + SQLDataType.BLOB, blobDef.toString());
	}
	
	@Test
	public void testToStringMEDIUMTEXT(){
		assertEquals(columnName + " " + SQLDataType.MEDIUMTEXT, mediumtextDef.toString());
	}
	
	@Test
	public void testToStringMEDIUMBLOB(){
		assertEquals(columnName + " " + SQLDataType.MEDIUMBLOB, mediumblobDef.toString());
	}
	
	@Test
	public void testToStringLONGTEXT(){
		assertEquals(columnName + " " + SQLDataType.LONGTEXT, longtextDef.toString());
	}
	
	@Test
	public void testToStringLONGBLOB(){
		assertEquals(columnName + " " + SQLDataType.LONGBLOB, longblobDef.toString());
	}
	
	@Test
	public void testToStringENUM(){
		assertEquals(columnName + " " + SQLDataType.ENUM + "(" + valuesString + ")", enumDef.toString());
	}
	
	@Test
	public void testToStringSET(){
		assertEquals(columnName + " " + SQLDataType.SET + "(" + valuesString + ")", setDef.toString());
	}
	
	@Test
	public void testToStringBIT(){
		assertEquals(columnName + " " + SQLDataType.BIT + "(" + size + ")", bitDef.toString());
	}
	
	@Test
	public void testToStringBITDefaultSize(){
		bitDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bit()
				.defaultSize()
				.build();
		assertEquals(columnName + " " + SQLDataType.BIT, bitDef.toString());
	}
	
	@Test
	public void testToStringTINYINT(){
		assertEquals(columnName + " " + SQLDataType.TINYINT + "(" + size + ")", tinyintDef.toString());
	}
	
	@Test
	public void testToStringTINYINTDefaultSize(){
		tinyintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.tinyint()
				.defaultSize()
				.build();
		assertEquals(columnName + " " + SQLDataType.TINYINT, tinyintDef.toString());
	}
	
	@Test
	public void testToStringBOOL(){
		assertEquals(columnName + " " + SQLDataType.BOOL, boolDef.toString());
	}
	
	@Test
	public void testToStringSMALLINT(){
		assertEquals(columnName + " " + SQLDataType.SMALLINT + "(" + size + ")", smallintDef.toString());
	}
	
	@Test
	public void testToStringSMALLINTDefaultSize(){
		smallintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.smallint()
				.defaultSize()
				.build();
		assertEquals(columnName + " " + SQLDataType.SMALLINT, smallintDef.toString());
	}
	
	@Test
	public void testToStringMEDIUMINT(){
		assertEquals(columnName + " " + SQLDataType.MEDIUMINT + "(" + size + ")", mediumintDef.toString());
	}
	
	@Test
	public void testToStringMEDIUMINTDefaultSize(){
		mediumintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.mediumint()
				.defaultSize()
				.build();
		assertEquals(columnName + " " + SQLDataType.MEDIUMINT, mediumintDef.toString());
	}
	
	@Test
	public void testToStringINTEGER(){
		assertEquals(columnName + " " + SQLDataType.INTEGER + "(" + size + ")", integerDef.toString());
	}
	
	@Test
	public void testToStringINTEGERDefaultSize(){
		integerDef = ColumnDefinition.builder()
				.columnName(columnName)
				.integer()
				.defaultSize()
				.build();
		assertEquals(columnName + " " + SQLDataType.INTEGER, integerDef.toString());
	}
	
	@Test
	public void testToStringBIGINT(){
		assertEquals(columnName + " " + SQLDataType.BIGINT + "(" + size + ")", bigintDef.toString());
	}
	
	@Test
	public void testToStringBIGINTDefaultSize(){
		bigintDef = ColumnDefinition.builder()
				.columnName(columnName)
				.bigint()
				.defaultSize()
				.build();
		assertEquals(columnName + " " + SQLDataType.BIGINT, bigintDef.toString());
	}
	
	@Test
	public void testToStringFLOAT(){
		assertEquals(columnName + " " + SQLDataType.FLOAT + "(" + size + ", " + digits + ")", floatDef.toString());
	}
	
	@Test
	public void testToStringFLOATDefaultSizeAndDigits(){
		floatDef = ColumnDefinition.builder()
				.columnName(columnName)
				.floatType()
				.defaultSizeAndDigits()
				.build();
		assertEquals(columnName + " " + SQLDataType.FLOAT, floatDef.toString());
	}
	
	@Test
	public void testToStringDOUBLE(){
		assertEquals(columnName + " " + SQLDataType.DOUBLE + "(" + size + ", " + digits + ")", doubleDef.toString());
	}
	
	@Test
	public void testToStringDOUBLEDefaultSizeAndDigits(){
		doubleDef = ColumnDefinition.builder()
				.columnName(columnName)
				.doubleType()
				.defaultSizeAndDigits()
				.build();
		assertEquals(columnName + " " + SQLDataType.DOUBLE, doubleDef.toString());
	}
	
	@Test
	public void testToStringDECIMAL(){
		assertEquals(columnName + " " + SQLDataType.DECIMAL + "(" + size + ", " + digits + ")", decimalDef.toString());
	}
	
	@Test
	public void testToStringDECIMALDefaultSizeAndDigits(){
		decimalDef = ColumnDefinition.builder()
				.columnName(columnName)
				.decimal()
				.defaultSizeAndDigits()
				.build();
		assertEquals(columnName + " " + SQLDataType.DECIMAL, decimalDef.toString());
	}
	
	@Test
	public void testToStringDATE(){
		assertEquals(columnName + " " + SQLDataType.DATE, dateDef.toString());
	}
	
	@Test
	public void testToStringDATETIME(){
		assertEquals(columnName + " " + SQLDataType.DATETIME + "(" + fractionalSecondsPrecision + ")", datetimeDef.toString());
	}
	
	@Test
	public void testToStringDATETIMEDefaultFSP(){
		datetimeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.datetime()
				.defaultFractionalSecondsPrecision()
				.build();
		assertEquals(columnName + " " + SQLDataType.DATETIME, datetimeDef.toString());
	}
	
	@Test
	public void testToStringTIMESTAMP(){
		assertEquals(columnName + " " + SQLDataType.TIMESTAMP + "(" + fractionalSecondsPrecision + ")", timestampDef.toString());
	}
	
	@Test
	public void testToStringTIMESTAMPDefaultFSP(){
		timestampDef = ColumnDefinition.builder()
				.columnName(columnName)
				.timestamp()
				.defaultFractionalSecondsPrecision()
				.build();
		assertEquals(columnName + " " + SQLDataType.TIMESTAMP, timestampDef.toString());
	}
	
	@Test
	public void testToStringTIME(){
		assertEquals(columnName + " " + SQLDataType.TIME + "(" + fractionalSecondsPrecision + ")", timeDef.toString());
	}
	
	@Test
	public void testToStringTIMEDefaultFSP(){
		timeDef = ColumnDefinition.builder()
				.columnName(columnName)
				.time()
				.defaultFractionalSecondsPrecision()
				.build();
		assertEquals(columnName + " " + SQLDataType.TIME, timeDef.toString());
	}
	
	@Test
	public void testToStringYEAR(){
		assertEquals(columnName + " " + SQLDataType.YEAR, yearDef.toString());
	}
}
