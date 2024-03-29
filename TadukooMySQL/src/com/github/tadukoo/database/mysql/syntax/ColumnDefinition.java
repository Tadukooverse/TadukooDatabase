package com.github.tadukoo.database.mysql.syntax;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Column Definition is a definition for a column in MySQL.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class ColumnDefinition{
	
	/**
	 * A builder used to build a {@link ColumnDefinition}. It takes the following parameters:
	 *
	 * <table>
	 *     <caption>Column Definition Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Required or Default</th>
	 *     </tr>
	 *     <tr>
	 *         <td>columnName</td>
	 *         <td>The name to use for the column</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>dataType</td>
	 *         <td>The {@link SQLDataType data type} of the column</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>size</td>
	 *         <td>The length of the column or total number of digits</td>
	 *         <td>Required for some data types - otherwise defaults to -1</td>
	 *     </tr>
	 *     <tr>
	 *         <td>values</td>
	 *         <td>The possible values for the column</td>
	 *         <td>Required for some data types - otherwise defaults to an empty List</td>
	 *     </tr>
	 *     <tr>
	 *         <td>digits</td>
	 *         <td>The number of digits after the decimal</td>
	 *         <td>Required for some data types - otherwise defaults to -1</td>
	 *     </tr>
	 *     <tr>
	 *         <td>fractionalSecondsPrecision</td>
	 *         <td>The precision for fractional seconds</td>
	 *         <td>Required for some data types - otherwise defaults to -1</td>
	 *     </tr>
	 *     <tr>
	 *         <td>notNull</td>
	 *         <td>Whether this column allows null (false) or not (true)</td>
	 *         <td>Defaults to false (allowing null)</td>
	 *     </tr>
	 *     <tr>
	 *         <td>unsigned</td>
	 *         <td>Whether this column is unsigned (true) or signed (false)</td>
	 *         <td>Defaults to signed (false)</td>
	 *     </tr>
	 *     <tr>
	 *         <td>autoIncrement</td>
	 *         <td>Whether to auto-increment the column or not</td>
	 *         <td>Defaults to false</td>
	 *     </tr>
	 *     <tr>
	 *         <td>primaryKey</td>
	 *         <td>Whether this column is the primary key or not</td>
	 *         <td>Defaults to false</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class ColumnDefinitionBuilder implements ColumnName, DataType, Length, AllowDefaultLength,
			AllowDefaultLengthLong, Size, AllowDefaultSize, SizeAndDigits, Values, FractionalSecondsPrecision{
		/** The name to use for the column */
		private String columnName;
		/** The {@link SQLDataType data type} of the column */
		private SQLDataType dataType;
		/** The length of the column or total number of digits */
		private Long size = null;
		/** The possible values for the column */
		private List<String> values = new ArrayList<>();
		/** The number of digits after the decimal */
		private Integer digits = null;
		/** The precision for fractional seconds */
		private Integer fractionalSecondsPrecision = null;
		
		/** Not allowed to instantiate outside of {@link ColumnDefinition} */
		private ColumnDefinitionBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		public DataType columnName(String columnName){
			this.columnName = columnName;
			return this;
		}
		
		/*
		 * Data Types
		 */
		
		/** {@inheritDoc} */
		@Override
		public AllowDefaultLength character(){
			this.dataType = SQLDataType.CHAR;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Length varchar(){
			this.dataType = SQLDataType.VARCHAR;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public AllowDefaultLength binary(){
			this.dataType = SQLDataType.BINARY;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Length varbinary(){
			this.dataType = SQLDataType.VARBINARY;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding tinyblob(){
			this.dataType = SQLDataType.TINYBLOB;
			return createNormalEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding tinytext(){
			this.dataType = SQLDataType.TINYTEXT;
			return createNormalEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public AllowDefaultLengthLong text(){
			this.dataType = SQLDataType.TEXT;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public AllowDefaultLengthLong blob(){
			this.dataType = SQLDataType.BLOB;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding mediumtext(){
			this.dataType = SQLDataType.MEDIUMTEXT;
			return createNormalEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding mediumblob(){
			this.dataType = SQLDataType.MEDIUMBLOB;
			return createNormalEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding longtext(){
			this.dataType = SQLDataType.LONGTEXT;
			return createNormalEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding longblob(){
			this.dataType = SQLDataType.LONGBLOB;
			return createNormalEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public Values enumeration(){
			this.dataType = SQLDataType.ENUM;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public Values set(){
			this.dataType = SQLDataType.SET;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public AllowDefaultLength bit(){
			this.dataType = SQLDataType.BIT;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public AllowDefaultSize tinyint(){
			this.dataType = SQLDataType.TINYINT;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding bool(){
			this.dataType = SQLDataType.BOOL;
			return createNormalEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public AllowDefaultSize smallint(){
			this.dataType = SQLDataType.SMALLINT;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public AllowDefaultSize mediumint(){
			this.dataType = SQLDataType.MEDIUMINT;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public AllowDefaultSize integer(){
			this.dataType = SQLDataType.INTEGER;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public AllowDefaultSize bigint(){
			this.dataType = SQLDataType.BIGINT;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public SizeAndDigits floatType(){
			this.dataType = SQLDataType.FLOAT;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public SizeAndDigits doubleType(){
			this.dataType = SQLDataType.DOUBLE;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public SizeAndDigits decimal(){
			this.dataType = SQLDataType.DECIMAL;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding date(){
			this.dataType = SQLDataType.DATE;
			return createNormalEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public FractionalSecondsPrecision datetime(){
			this.dataType = SQLDataType.DATETIME;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public FractionalSecondsPrecision timestamp(){
			this.dataType = SQLDataType.TIMESTAMP;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public FractionalSecondsPrecision time(){
			this.dataType = SQLDataType.TIME;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding year(){
			this.dataType = SQLDataType.YEAR;
			return createNormalEnding();
		}
		
		/*
		 * Other Parameters
		 */
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding length(int length){
			this.size = Integer.valueOf(length).longValue();
			return createNormalEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding defaultLength(){
			// Don't set size (keep null to use default)
			return createNormalEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding length(long length){
			this.size = length;
			return createNormalEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public NumericEnding size(int size){
			this.size = Integer.valueOf(size).longValue();
			return createNumericEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public NumericEnding defaultSize(){
			// Don't set size (keep null to use default)
			return createNumericEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public NumericEnding sizeAndDigits(int size, int digits){
			this.size = Integer.valueOf(size).longValue();
			this.digits = digits;
			return createNumericEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public NumericEnding defaultSizeAndDigits(){
			// Don't set size and digits (keep null to use defaults)
			return createNumericEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding values(List<String> values){
			this.values = values;
			return createNormalEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding values(String ... values){
			this.values = ListUtil.createList(values);
			return createNormalEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding fractionalSecondsPrecision(int fractionalSecondsPrecision){
			this.fractionalSecondsPrecision = fractionalSecondsPrecision;
			return createNormalEnding();
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding defaultFractionalSecondsPrecision(){
			// Don't set fractional seconds precision (keep null to use default)
			return createNormalEnding();
		}
		
		/*
		 * Endings
		 */
		
		/**
		 * @return A normal ending to continue building this for non-numeric data types
		 */
		private Builder createNormalEnding(){
			return new Builder(this);
		}
		
		/**
		 * @return A numeric ending to continue building this for numeric data types
		 */
		private NumericEnding createNumericEnding(){
			return new NumericColumnDefinitionBuilder(this);
		}
	}
	
	/**
	 * A builder class for non-numeric column definitions
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class Builder implements NormalEnding{
		/** The name to use for the column */
		protected final String columnName;
		/** The {@link SQLDataType data type} of the column */
		protected final SQLDataType dataType;
		/** The length of the column or total number of digits */
		protected final Long size;
		/** The possible values for the column */
		protected final List<String> values;
		/** The number of digits after the decimal */
		protected final Integer digits;
		/** The precision for fractional seconds */
		protected final Integer fractionalSecondsPrecision;
		/** Whether this column allows null (false) or not (true) */
		protected boolean notNull = false;
		/** Whether this column is unsigned (true) or signed (false) */
		protected boolean unsigned = false;
		/** Whether to auto-increment the column or not */
		protected boolean autoIncrement = false;
		/** Whether this column is the primary key or not */
		protected boolean primaryKey = false;
		
		/**
		 * Creates a new Builder using the given parameters from the {@link ColumnDefinitionBuilder}
		 *
		 * @param builder The {@link ColumnDefinitionBuilder} to use for values
		 */
		protected Builder(ColumnDefinitionBuilder builder){
			this.columnName = builder.columnName;
			this.dataType = builder.dataType;
			this.size = builder.size;
			this.values = builder.values;
			this.digits = builder.digits;
			this.fractionalSecondsPrecision = builder.fractionalSecondsPrecision;
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding notNull(){
			this.notNull = true;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public NormalEnding primaryKey(){
			this.primaryKey = true;
			return this;
		}
		
		/**
		 * Checks for any errors in the set parameters and throws an error if any are found
		 */
		public void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// columnName is required
			if(StringUtil.isBlank(columnName)){
				errors.add("columnName is required!");
			}
			
			// Size must be between 1 and 64 for BIT
			if(dataType == SQLDataType.BIT && size != null && (size < 1 || size > 64)){
				errors.add("For " + dataType + " size must be between 1 and 64 (or defaulted)!");
			}
			
			// Size must be between 1 and 65 for DECIMAL
			if(dataType == SQLDataType.DECIMAL && size != null && (size < 1 || size > 65)){
				errors.add("For " + dataType + " size must be between 1 and 65 (or defaulted)!");
			}
			
			// Size must be between 0 and 255 for CHAR/BINARY
			if((dataType == SQLDataType.CHAR || dataType == SQLDataType.BINARY) &&
					size != null && (size < 0 || size > 255)){
				errors.add("For " + dataType + " size must be between 0 and 255 (or defaulted)!");
			}
			
			// Size must be between 1 and 255 for TINYINT/SMALLINT/MEDIUMINT/INT/BIGINT
			if((dataType == SQLDataType.TINYINT || dataType == SQLDataType.SMALLINT ||
					dataType == SQLDataType.MEDIUMINT || dataType == SQLDataType.INTEGER ||
					dataType == SQLDataType.BIGINT) &&
					size != null && (size < 1 || size > 255)){
				errors.add("For " + dataType + " size must be between 1 and 255 (or defaulted)!");
			}
			
			// Size must be between 0 and 65,535 for VARCHAR/VARBINARY
			if((dataType == SQLDataType.VARCHAR || dataType == SQLDataType.VARBINARY) &&
					(size < 0 || size > 65535)){
				errors.add("For " + dataType + " size must be between 0 and 65,535!");
			}
			
			// Size must be between 0 and 4,294,967,295 for TEXT/BLOB
			if((dataType == SQLDataType.TEXT || dataType == SQLDataType.BLOB) &&
					size != null && (size < 0 || size > 4294967295L)){
				errors.add("For " + dataType + " size must be between 0 and 4,294,967,295 (or defaulted)!");
			}
			
			// Values can't be empty for ENUM or SET
			if((dataType == SQLDataType.ENUM || dataType == SQLDataType.SET) && ListUtil.isBlank(values)){
				errors.add("Must specify values for " + dataType + "!");
			}
			
			// Values can't have more than 64 item for SET
			if(dataType == SQLDataType.SET && values != null && values.size() > 64){
				errors.add("For " + dataType + " values must be between 1 and 64 in size!");
			}
			
			// Values can't have more than 65,535 items for ENUM
			if(dataType == SQLDataType.ENUM && values != null && values.size() > 65535){
				errors.add("For " + dataType + " values must be between 1 and 65,535 in size!");
			}
			
			// Digits must be between 0 and 30 for DECIMAL
			if(dataType == SQLDataType.DECIMAL && digits != null && (digits < 0 || digits > 30)){
				errors.add("For " + dataType + " digits must be between 0 and 30 (or defaulted)!");
			}
			
			// Fractional Seconds Precision must be between 0 and 6 for DATETIME/TIMESTAMP/TIME
			if((dataType == SQLDataType.DATETIME || dataType == SQLDataType.TIMESTAMP ||
					dataType == SQLDataType.TIME) &&
					fractionalSecondsPrecision != null && (fractionalSecondsPrecision < 0 ||
					fractionalSecondsPrecision > 6)){
				errors.add("For " + dataType + " fractionalSecondsPrecision must be between " +
						"0 and 6 (or defaulted)!");
			}
			
			// Report any errors
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Errors encountered in building a ColumnDefinition: \n" +
						StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/** {@inheritDoc} */
		@Override
		public ColumnDefinition build(){
			checkForErrors();
			
			return new ColumnDefinition(columnName, dataType, size, values, digits, fractionalSecondsPrecision,
					notNull, unsigned, autoIncrement, primaryKey);
		}
	}
	
	/**
	 * A builder class to use for numeric column definitions
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class NumericColumnDefinitionBuilder extends Builder implements NumericEnding{
		
		/**
		 * Creates a new Numeric Column Definition Builder using the values from the given
		 * {@link ColumnDefinitionBuilder}
		 *
		 * @param builder The {@link ColumnDefinitionBuilder} to use for values
		 */
		private NumericColumnDefinitionBuilder(ColumnDefinitionBuilder builder){
			super(builder);
		}
		
		/** {@inheritDoc} */
		@Override
		public NumericEnding notNull(){
			return (NumericEnding) super.notNull();
		}
		
		/** {@inheritDoc} */
		@Override
		public NumericEnding unsigned(){
			this.unsigned = true;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public NumericEnding autoIncrement(){
			this.autoIncrement = true;
			return this;
		}
		
		/** {@inheritDoc} */
		@Override
		public NumericEnding primaryKey(){
			return (NumericEnding) super.primaryKey();
		}
	}
	
	/** The name to use for the column */
	private final String columnName;
	/** The {@link SQLDataType data type} of the column */
	private final SQLDataType dataType;
	/** The length of the column or total number of digits */
	private final Long size;
	/** The possible values for the column */
	private final List<String> values;
	/** The number of digits after the decimal */
	private final Integer digits;
	/** The precision for fractional seconds */
	private final Integer fractionalSecondsPrecision;
	/** Whether this column allows null (false) or not (true) */
	private final boolean notNull;
	/** Whether this column is unsigned (true) or signed (false) */
	private final boolean unsigned;
	/** Whether to auto-increment this column or not */
	private final boolean autoIncrement;
	/** Whether this column is the primary key or not */
	private final boolean primaryKey;
	
	/**
	 * Constructs a new {@link ColumnDefinition} with the given parameters
	 *
	 * @param columnName The name to use for the column
	 * @param dataType The {@link SQLDataType data type} of the column
	 * @param size The length of the column or total number of digits
	 * @param values The possible values for the column
	 * @param digits The number of digits after the decimal
	 * @param fractionalSecondsPrecision The precision for fractional seconds
	 * @param notNull Whether this column allows null (false) or not (true)
	 * @param unsigned Whether this column is unsigned (true) or signed (false)
	 * @param autoIncrement Whether to auto-increment this column or not
	 * @param primaryKey Whether this column is the primary key or not
	 */
	private ColumnDefinition(
			String columnName, SQLDataType dataType,
			Long size, List<String> values, Integer digits, Integer fractionalSecondsPrecision,
			boolean notNull, boolean unsigned, boolean autoIncrement, boolean primaryKey){
		this.columnName = columnName;
		this.dataType = dataType;
		this.size = size;
		this.values = values;
		this.digits = digits;
		this.fractionalSecondsPrecision = fractionalSecondsPrecision;
		this.notNull = notNull;
		this.unsigned = unsigned;
		this.autoIncrement = autoIncrement;
		this.primaryKey = primaryKey;
	}
	
	/**
	 * @return A new {@link ColumnDefinitionBuilder builder} to build a new {@link ColumnDefinition}
	 */
	public static ColumnName builder(){
		return new ColumnDefinitionBuilder();
	}
	
	/**
	 * @return The name to use for the column
	 */
	public String getColumnName(){
		return columnName;
	}
	
	/**
	 * @return The {@link SQLDataType data type} of the column
	 */
	public SQLDataType getDataType(){
		return dataType;
	}
	
	/**
	 * @return The length of the column or total number of digits
	 */
	public Long getSize(){
		return size;
	}
	
	/**
	 * @return The possible values for the column
	 */
	public List<String> getValues(){
		return values;
	}
	
	/**
	 * @return The number of digits after the decimal
	 */
	public Integer getDigits(){
		return digits;
	}
	
	/**
	 * @return The precision for fractional seconds
	 */
	public Integer getFractionalSecondsPrecision(){
		return fractionalSecondsPrecision;
	}
	
	/**
	 * @return Whether this column allows null (false) or not (true)
	 */
	public boolean isNotNull(){
		return notNull;
	}
	
	/**
	 * @return Whether this column is unsigned (true) or signed (false)
	 */
	public boolean isUnsigned(){
		return unsigned;
	}
	
	/**
	 * @return Whether to auto-increment this column or not
	 */
	public boolean isAutoIncremented(){
		return autoIncrement;
	}
	
	/**
	 * @return Whether this column is the primary key or not
	 */
	public boolean isPrimaryKey(){
		return primaryKey;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		// Start with columnName and dataType
		StringBuilder colDef = new StringBuilder(columnName);
		colDef.append(' ').append(dataType);
		
		// Check if we have size
		if(size != null){
			colDef.append('(').append(size);
			// Check if we also have digits
			if(digits != null){
				colDef.append(", ").append(digits);
			}
			colDef.append(')');
		}
		
		// Check if we have values
		if(ListUtil.isNotBlank(values)){
			colDef.append('(');
			for(String value: values){
				colDef.append("'").append(value).append("', ");
			}
			// Delete extra comma
			colDef.delete(colDef.length()-2, colDef.length());
			colDef.append(')');
		}
		
		// Check if we have FSP
		if(fractionalSecondsPrecision != null){
			colDef.append('(').append(fractionalSecondsPrecision).append(')');
		}
		
		// Add not null if we have it
		if(notNull){
			colDef.append(" NOT NULL");
		}
		
		// Add unsigned if we have it
		if(unsigned){
			colDef.append(" UNSIGNED");
		}
		
		// Add auto-increment if we have it
		if(autoIncrement){
			colDef.append(" AUTO_INCREMENT");
		}
		
		// Add primary key if we have it
		if(primaryKey){
			colDef.append(" PRIMARY KEY");
		}
		
		return colDef.toString();
	}
	
	/*
	 * Interfaces for building
	 */
	
	/**
	 * The Column Name part of building a {@link ColumnDefinition}
	 */
	public interface ColumnName{
		/**
		 * @param columnName The name to use for the column
		 * @return this, to continue building
		 */
		DataType columnName(String columnName);
	}
	
	/**
	 * The {@link SQLDataType Data Type} part of building a {@link ColumnDefinition}
	 */
	public interface DataType{
		/**
		 * Sets the data type to {@link SQLDataType#CHAR CHAR}.
		 * Length specifies the column length in characters - can be 0 to 255. Defaults to 1
		 *
		 * @return this, to continue building
		 */
		AllowDefaultLength character();
		
		/**
		 * Sets the data type to {@link SQLDataType#VARCHAR VARCHAR}.
		 * Length specifies the column length in characters - can be 0 to 65,535
		 *
		 * @return this, to continue building
		 */
		Length varchar();
		
		/**
		 * Sets the data type to {@link SQLDataType#BINARY BINARY}.
		 * Length specifies the column length in bytes - can be 0 to 255. Defaults to 1
		 *
		 * @return this, to continue building
		 */
		AllowDefaultLength binary();
		
		/**
		 * Sets the data type to {@link SQLDataType#VARBINARY VARBINARY}.
		 * Length specifies the column length in bytes - can be 0 to 65,535
		 *
		 * @return this, to continue building
		 */
		Length varbinary();
		
		/**
		 * Sets the data type to {@link SQLDataType#TINYBLOB TINYBLOB}.
		 *
		 * @return this, to continue building
		 */
		NormalEnding tinyblob();
		
		/**
		 * Sets the data type to {@link SQLDataType#TINYTEXT TINYTEXT}.
		 *
		 * @return this, to continue building
		 */
		NormalEnding tinytext();
		
		/**
		 * Sets the data type to {@link SQLDataType#TEXT TEXT}.
		 * If you specify length, MySQL will pick the smallest text type that can fit that many characters.
		 *
		 * @return this, to continue building
		 */
		AllowDefaultLengthLong text();
		
		/**
		 * Sets the data type to {@link SQLDataType#BLOB BLOB}.
		 * If you specify length, MySQL will pick the smallest blob type that can fit that many bytes.
		 *
		 * @return this, to continue building
		 */
		AllowDefaultLengthLong blob();
		
		/**
		 * Sets the data type to {@link SQLDataType#MEDIUMTEXT MEDIUMTEXT}.
		 *
		 * @return this, to continue building
		 */
		NormalEnding mediumtext();
		
		/**
		 * Sets the data type to {@link SQLDataType#MEDIUMBLOB MEDIUMBLOB}.
		 *
		 * @return this, to continue building
		 */
		NormalEnding mediumblob();
		
		/**
		 * Sets the data type to {@link SQLDataType#LONGTEXT LONGTEXT}.
		 *
		 * @return this, to continue building
		 */
		NormalEnding longtext();
		
		/**
		 * Sets the data type to {@link SQLDataType#LONGBLOB LONGBLOB}.
		 *
		 * @return this, to continue building
		 */
		NormalEnding longblob();
		
		/**
		 * Sets the data type to {@link SQLDataType#ENUM ENUM}.
		 * You can specify up to 65,535 values for it
		 *
		 * @return this, to continue building
		 */
		Values enumeration();
		
		/**
		 * Sets the data type to {@link SQLDataType#SET SET}.
		 * You can specify up to 64 values for it
		 *
		 * @return this, to continue building
		 */
		Values set();
		
		/**
		 * Sets the data type to {@link SQLDataType#BIT BIT}.
		 * Size specifies the length in bits for values - can be 1 to 64, defaults to 1.
		 *
		 * @return this, to continue building
		 */
		AllowDefaultLength bit();
		
		/**
		 * Sets the data type to {@link SQLDataType#TINYINT TINYINT}.
		 * Size specifies the maximum display width - can be 1 to 255, defaults to 255.
		 *
		 * @return this, to continue building
		 */
		AllowDefaultSize tinyint();
		
		/**
		 * Sets the data type to {@link SQLDataType#BOOL BOOL}.
		 *
		 * @return this, to continue building
		 */
		NormalEnding bool();
		
		/**
		 * Sets the data type to {@link SQLDataType#SMALLINT SMALLINT}.
		 * Size specifies the maximum display width - can be 1 to 255, defaults to 255.
		 *
		 * @return this, to continue building
		 */
		AllowDefaultSize smallint();
		
		/**
		 * Sets the data type to {@link SQLDataType#MEDIUMINT MEDIUMINT}.
		 * Size specifies the maximum display width - can be 1 to 255, defaults to 255.
		 *
		 * @return this, to continue building
		 */
		AllowDefaultSize mediumint();
		
		/**
		 * Sets the data type to {@link SQLDataType#INTEGER INTEGER}.
		 * Size specifies the maximum display width - can be 1 to 255, defaults to 255.
		 *
		 * @return this, to continue building
		 */
		AllowDefaultSize integer();
		
		/**
		 * Sets the data type to {@link SQLDataType#BIGINT BIGINT}.
		 * Size specifies the maximum display width - can be 1 to 255, defaults to 255.
		 *
		 * @return this, to continue building
		 */
		AllowDefaultSize bigint();
		
		/**
		 * Sets the data type to {@link SQLDataType#FLOAT FLOAT}.
		 * Size specifies the total number of digits and digits is the number of digits after the decimal
		 *
		 * @return this, to continue building
		 */
		SizeAndDigits floatType();
		
		/**
		 * Sets the data type to {@link SQLDataType#DOUBLE DOUBLE}.
		 * Size specifies the total number of digits and digits is the number of digits after the decimal
		 *
		 * @return this, to continue building
		 */
		SizeAndDigits doubleType();
		
		/**
		 * Sets the data type to {@link SQLDataType#DECIMAL DECIMAL}.
		 * Size specifies the total number of digits - can be 1 to 65, defaults to 10.
		 * Digits specifies the number of digits after the decimal - can be 0 to 30, defaults to 0.
		 *
		 * @return this, to continue building
		 */
		SizeAndDigits decimal();
		
		/**
		 * Sets the data type to {@link SQLDataType#DATE DATE}.
		 *
		 * @return this, to continue building
		 */
		NormalEnding date();
		
		/**
		 * Sets the data type to {@link SQLDataType#DATETIME DATETIME}.
		 * Fractional Seconds Precision specifies how many digits after the decimal to use for fractions of a second -
		 * can be 0 to 6, defaults to 0
		 *
		 * @return this, to continue building
		 */
		FractionalSecondsPrecision datetime();
		
		/**
		 * Sets the data type to {@link SQLDataType#TIMESTAMP TIMESTAMP}.
		 * Fractional Seconds Precision specifies how many digits after the decimal to use for fractions of a second -
		 * can be 0 to 6, defaults to 0
		 *
		 * @return this, to continue building
		 */
		FractionalSecondsPrecision timestamp();
		
		/**
		 * Sets the data type to {@link SQLDataType#TIME TIME}.
		 * Fractional Seconds Precision specifies how many digits after the decimal to use for fractions of a second -
		 * can be 0 to 6, defaults to 0
		 *
		 * @return this, to continue building
		 */
		FractionalSecondsPrecision time();
		
		/**
		 * Sets the data type to {@link SQLDataType#YEAR YEAR}.
		 *
		 * @return this, to continue building
		 */
		NormalEnding year();
	}
	
	/**
	 * The Length part of building a {@link ColumnDefinition}
	 */
	public interface Length{
		/**
		 * @param length The length of the column
		 * @return this, to continue building
		 */
		NormalEnding length(int length);
	}
	
	/**
	 * The Length part of building a {@link ColumnDefinition}, allowing using a default length
	 */
	public interface AllowDefaultLength extends Length{
		/**
		 * Sets length to the default for the data type
		 * @return this, to continue building
		 */
		NormalEnding defaultLength();
	}
	
	/**
	 * The Length part of building a {@link ColumnDefinition}, allowing using a default length and using a long
	 */
	public interface AllowDefaultLengthLong extends AllowDefaultLength{
		/**
		 * @param length The length to use for the column
		 * @return this, to continue building
		 */
		NormalEnding length(long length);
	}
	
	/**
	 * The Size part of building a {@link ColumnDefinition}
	 */
	public interface Size{
		/**
		 * @param size The length of the column
		 * @return this, to continue building
		 */
		NumericEnding size(int size);
	}
	
	/**
	 * The Size part of building a {@link ColumnDefinition}, allowing using a default size
	 */
	public interface AllowDefaultSize extends Size{
		/**
		 * Sets size to the default for the data type
		 * @return this, to continue building
		 */
		NumericEnding defaultSize();
	}
	
	/**
	 * The Size and Digits part of building a {@link ColumnDefinition}
	 */
	public interface SizeAndDigits{
		/**
		 * @param size The total number of digits
		 * @param digits The number of digits after the decimal
		 * @return this, to continue building
		 */
		NumericEnding sizeAndDigits(int size, int digits);
		
		/**
		 * Sets to use the default size and digits for the data type
		 * @return this, to continue building
		 */
		NumericEnding defaultSizeAndDigits();
	}
	
	/**
	 * The Values part of building a {@link ColumnDefinition}
	 */
	public interface Values{
		/**
		 * @param values The possible values for the column
		 * @return this, to continue building
		 */
		NormalEnding values(List<String> values);
		
		/**
		 * @param values The possible values for the column
		 * @return this, to continue building
		 */
		NormalEnding values(String ... values);
	}
	
	/**
	 * The Fractional Seconds Precision part of building a {@link ColumnDefinition}
	 */
	public interface FractionalSecondsPrecision{
		/**
		 * @param fractionalSecondsPrecision The precision for fractional seconds
		 * @return this, to continue building
		 */
		NormalEnding fractionalSecondsPrecision(int fractionalSecondsPrecision);
		
		/**
		 * Sets to use the default fractional seconds precision for the data type
		 * @return this, to continue building
		 */
		NormalEnding defaultFractionalSecondsPrecision();
	}
	
	/**
	 * The normal ending part of building a {@link ColumnDefinition}
	 */
	public interface NormalEnding extends Build{
		/**
		 * Sets this column to not allow null values
		 * @return this, to continue building
		 */
		NormalEnding notNull();
		
		/**
		 * Sets this column as the primary key
		 * @return this, to continue building
		 */
		NormalEnding primaryKey();
	}
	
	/**
	 * The numeric ending part of building a {@link ColumnDefinition}
	 */
	public interface NumericEnding extends NormalEnding{
		/** {@inheritDoc} */
		@Override
		NumericEnding notNull();
		
		/**
		 * Sets the column as unsigned
		 * @return this, to continue building
		 */
		NumericEnding unsigned();
		
		/**
		 * Sets the column to auto-increment
		 * @return this, to continue building
		 */
		NumericEnding autoIncrement();
		
		/** {@inheritDoc} */
		@Override
		NumericEnding primaryKey();
	}
	
	/**
	 * The Building part of building a {@link ColumnDefinition}
	 */
	public interface Build{
		/**
		 * Constructs a new {@link ColumnDefinition} using the set parameters
		 *
		 * @return The newly built {@link ColumnDefinition}
		 */
		ColumnDefinition build();
	}
}
