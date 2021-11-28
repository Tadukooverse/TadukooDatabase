package com.github.tadukoo.database.mysql;

import com.github.tadukoo.util.functional.function.ThrowingFunction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommonResultSetConverters{
	
	public static Boolean check(ResultSet resultSet) throws SQLException{
		return resultSet.next();
	}
	
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
	
	public static <Type> List<Type> simpleList(ResultSet resultSet, ThrowingFunction<Integer, Type, SQLException> getter)
			throws SQLException{
		List<Type> values = new ArrayList<>();
		while(resultSet.next()){
			values.add(getter.apply(1));
		}
		return values;
	}
	
	public static Boolean singleBoolean(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getBoolean);
	}
	
	public static List<Boolean> booleans(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getBoolean);
	}
	
	public static Integer singleInteger(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getInt);
	}
	
	public static List<Integer> integers(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getInt);
	}
	
	public static String singleString(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getString);
	}
	
	public static List<String> strings(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getString);
	}
	
	public static Time singleTime(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getTime);
	}
	
	public static List<Time> times(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getTime);
	}
	
	public static Timestamp singleTimestamp(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getTimestamp);
	}
	
	public static List<Timestamp> timestamps(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getTimestamp);
	}
	
	public static Date singleDate(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getDate);
	}
	
	public static List<Date> dates(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getDate);
	}
	
	public static Short singleShort(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getShort);
	}
	
	public static List<Short> shorts(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getShort);
	}
	
	public static Long singleLong(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getLong);
	}
	
	public static List<Long> longs(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getLong);
	}
	
	public static Float singleFloat(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getFloat);
	}
	
	public static List<Float> floats(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getFloat);
	}
	
	public static Double singleDouble(ResultSet resultSet) throws SQLException{
		return simpleItem(resultSet, resultSet::getDouble);
	}
	
	public static List<Double> doubles(ResultSet resultSet) throws SQLException{
		return simpleList(resultSet, resultSet::getDouble);
	}
}
