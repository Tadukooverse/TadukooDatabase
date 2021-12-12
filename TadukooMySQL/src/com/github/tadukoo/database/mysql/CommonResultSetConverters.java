package com.github.tadukoo.database.mysql;

import com.github.tadukoo.util.functional.function.ThrowingFunction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Common Result Set Converters are used for common/simple conversions from a {@link ResultSet} to values
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 * @since Alpha v.0.1
 */
public class CommonResultSetConverters{
	
	/** Not allowed to instantiate */
	private CommonResultSetConverters(){ }
	
	/**
	 * Checks if any rows were returned in the {@link ResultSet}
	 *
	 * @param resultSet The {@link ResultSet} to be checked
	 * @return {@code true} if any rows are in the {@link ResultSet}, otherwise {@code false}
	 * @throws SQLException If anything goes wrong
	 */
	public static Boolean check(ResultSet resultSet) throws SQLException{
		return resultSet.next();
	}
	
	/**
	 * To be used when only one row is expected, this will convert that single row into a single value using the
	 * given getter
	 *
	 * @param resultSet The {@link ResultSet} to extract the value from
	 * @param getter The {@link ThrowingFunction} to use to convert the row into a value
	 * @param <Type> The Type of value to be returned
	 * @return The value found from the single row
	 * @throws SQLException If anything goes wrong, or if more than one row is present in the {@link ResultSet}
	 */
	public static <Type> Type simpleItem(ResultSet resultSet, ThrowingFunction<Integer, Type, SQLException> getter)
			throws SQLException{
		Type value = null;
		while(resultSet.next()){
			if(value != null){
				throw new SQLException("Found multiple rows of results, expected only one!");
			}
			value = getter.apply(1);
		}
		return value;
	}
	
	/**
	 * This will convert multiple rows of a {@link ResultSet} into a List of values, a single value for each row
	 * using the given getter
	 *
	 * @param resultSet The {@link ResultSet} to extract the values from
	 * @param getter The {@link ThrowingFunction} to use to convert rows into values
	 * @param <Type> The Type of values to be returned
	 * @return The List of values found from the rows
	 * @throws SQLException If anything goes wrong
	 */
	public static <Type> List<Type> simpleList(
			ResultSet resultSet, ThrowingFunction<Integer, Type, SQLException> getter) throws SQLException{
		List<Type> values = new ArrayList<>();
		while(resultSet.next()){
			values.add(getter.apply(1));
		}
		return values;
	}
	
	/**
	 * To be used when only one row is expected, this will convert that single row into a single boolean
	 *
	 * @param resultSet The {@link ResultSet} to extract the boolean from
	 * @return The boolean found from the single row
	 * @throws SQLException If anything goes wrong, or if more than one row is present in the {@link ResultSet}
	 */
	public static Boolean singleBoolean(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getBoolean);
	}
	
	/**
	 * This will convert multiple rows of a {@link ResultSet} into a List of booleans, a single boolean for each row
	 *
	 * @param resultSet The {@link ResultSet} to extract the booleans from
	 * @return The List of booleans found from the rows
	 * @throws SQLException If anything goes wrong
	 */
	public static List<Boolean> booleans(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getBoolean);
	}
	
	/**
	 * To be used when only one row is expected, this will convert that single row into a single integer
	 *
	 * @param resultSet The {@link ResultSet} to extract the integer from
	 * @return The integer found from the single row
	 * @throws SQLException If anything goes wrong, or if more than one row is present in the {@link ResultSet}
	 */
	public static Integer singleInteger(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getInt);
	}
	
	/**
	 * This will convert multiple rows of a {@link ResultSet} into a List of integers, a single integer for each row
	 *
	 * @param resultSet The {@link ResultSet} to extract the integers from
	 * @return The List of integers found from the rows
	 * @throws SQLException If anything goes wrong
	 */
	public static List<Integer> integers(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getInt);
	}
	
	/**
	 * To be used when only one row is expected, this will convert that single row into a single String
	 *
	 * @param resultSet The {@link ResultSet} to extract the String from
	 * @return The String found from the single row
	 * @throws SQLException If anything goes wrong, or if more than one row is present in the {@link ResultSet}
	 */
	public static String singleString(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getString);
	}
	
	/**
	 * This will convert multiple rows of a {@link ResultSet} into a List of Strings, a single String for each row
	 *
	 * @param resultSet The {@link ResultSet} to extract the Strings from
	 * @return The List of Strings found from the rows
	 * @throws SQLException If anything goes wrong
	 */
	public static List<String> strings(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getString);
	}
	
	/**
	 * To be used when only one row is expected, this will convert that single row into a single {@link Time}
	 *
	 * @param resultSet The {@link ResultSet} to extract the {@link Time} from
	 * @return The {@link Time} found from the single row
	 * @throws SQLException If anything goes wrong, or if more than one row is present in the {@link ResultSet}
	 */
	public static Time singleTime(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getTime);
	}
	
	/**
	 * This will convert multiple rows of a {@link ResultSet} into a List of {@link Time Times},
	 * a single {@link Time} for each row
	 *
	 * @param resultSet The {@link ResultSet} to extract the {@link Time Times} from
	 * @return The List of {@link Time Times} found from the rows
	 * @throws SQLException If anything goes wrong
	 */
	public static List<Time> times(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getTime);
	}
	
	/**
	 * To be used when only one row is expected, this will convert that single row into a single {@link Timestamp}
	 *
	 * @param resultSet The {@link ResultSet} to extract the {@link Timestamp} from
	 * @return The {@link Timestamp} found from the single row
	 * @throws SQLException If anything goes wrong, or if more than one row is present in the {@link ResultSet}
	 */
	public static Timestamp singleTimestamp(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getTimestamp);
	}
	
	/**
	 * This will convert multiple rows of a {@link ResultSet} into a List of {@link Timestamp Timestamps},
	 * a single {@link Timestamp} for each row
	 *
	 * @param resultSet The {@link ResultSet} to extract the {@link Timestamp Timestamps} from
	 * @return The List of {@link Timestamp Timestamps} found from the rows
	 * @throws SQLException If anything goes wrong
	 */
	public static List<Timestamp> timestamps(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getTimestamp);
	}
	
	/**
	 * To be used when only one row is expected, this will convert that single row into a single {@link Date}
	 *
	 * @param resultSet The {@link ResultSet} to extract the {@link Date} from
	 * @return The {@link Date} found from the single row
	 * @throws SQLException If anything goes wrong, or if more than one row is present in the {@link ResultSet}
	 */
	public static Date singleDate(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getDate);
	}
	
	/**
	 * This will convert multiple rows of a {@link ResultSet} into a List of {@link Date Dates},
	 * a single {@link Date} for each row
	 *
	 * @param resultSet The {@link ResultSet} to extract the {@link Date Dates} from
	 * @return The List of {@link Date Dates} found from the rows
	 * @throws SQLException If anything goes wrong
	 */
	public static List<Date> dates(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getDate);
	}
	
	/**
	 * To be used when only one row is expected, this will convert that single row into a single Short
	 *
	 * @param resultSet The {@link ResultSet} to extract the Short from
	 * @return The Short found from the single row
	 * @throws SQLException If anything goes wrong, or if more than one row is present in the {@link ResultSet}
	 */
	public static Short singleShort(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getShort);
	}
	
	/**
	 * This will convert multiple rows of a {@link ResultSet} into a List of shorts,
	 * a single short for each row
	 *
	 * @param resultSet The {@link ResultSet} to extract the short from
	 * @return The List of shorts found from the rows
	 * @throws SQLException If anything goes wrong
	 */
	public static List<Short> shorts(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getShort);
	}
	
	/**
	 * To be used when only one row is expected, this will convert that single row into a single long
	 *
	 * @param resultSet The {@link ResultSet} to extract the long from
	 * @return The long found from the single row
	 * @throws SQLException If anything goes wrong, or if more than one row is present in the {@link ResultSet}
	 */
	public static Long singleLong(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getLong);
	}
	
	/**
	 * This will convert multiple rows of a {@link ResultSet} into a List of longs,
	 * a single long for each row
	 *
	 * @param resultSet The {@link ResultSet} to extract the long from
	 * @return The List of longs found from the rows
	 * @throws SQLException If anything goes wrong
	 */
	public static List<Long> longs(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getLong);
	}
	
	/**
	 * To be used when only one row is expected, this will convert that single row into a single float
	 *
	 * @param resultSet The {@link ResultSet} to extract the float from
	 * @return The float found from the single row
	 * @throws SQLException If anything goes wrong, or if more than one row is present in the {@link ResultSet}
	 */
	public static Float singleFloat(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getFloat);
	}
	
	/**
	 * This will convert multiple rows of a {@link ResultSet} into a List of floats,
	 * a single float for each row
	 *
	 * @param resultSet The {@link ResultSet} to extract the float from
	 * @return The List of floats found from the rows
	 * @throws SQLException If anything goes wrong
	 */
	public static List<Float> floats(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getFloat);
	}
	
	/**
	 * To be used when only one row is expected, this will convert that single row into a single double
	 *
	 * @param resultSet The {@link ResultSet} to extract the double from
	 * @return The double found from the single row
	 * @throws SQLException If anything goes wrong, or if more than one row is present in the {@link ResultSet}
	 */
	public static Double singleDouble(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getDouble);
	}
	
	/**
	 * This will convert multiple rows of a {@link ResultSet} into a List of doubles,
	 * a single double for each row
	 *
	 * @param resultSet The {@link ResultSet} to extract the double from
	 * @return The List of doubles found from the rows
	 * @throws SQLException If anything goes wrong
	 */
	public static List<Double> doubles(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getDouble);
	}
}
