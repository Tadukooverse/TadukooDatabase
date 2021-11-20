package com.github.tadukoo.database.mysql.syntax.columndef;

import com.github.tadukoo.util.ListUtil;

import java.util.List;

public interface ColumnDefinitionConstants{
	String columnName = "Test";
	int size = 10;
	List<String> values = ListUtil.createList("Derp", "Plop");
	String valuesString = "Derp, Plop";
	int digits = 15;
	int fractionalSecondsPrecision = 3;
}
