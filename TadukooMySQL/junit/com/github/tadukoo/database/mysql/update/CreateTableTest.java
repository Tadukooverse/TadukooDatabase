package com.github.tadukoo.database.mysql.update;

import com.github.tadukoo.database.mysql.Database;
import com.github.tadukoo.junit.logger.JUnitEasyLogger;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class CreateTableTest{
	
	/*
	@Test
	public void testCreateTable() throws SQLException{
		Database db = new Database(new JUnitEasyLogger(), "localhost", 3306, "test", "root", "");
		db.executeUpdate("Create Table", "create table Attributes(" +
				"attribute_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, " +
				"official VARCHAR(5) NOT NULL, " +
				"holder VARCHAR(10) NOT NULL, " +
				"type VARCHAR(10) NOT NULL, " +
				"name VARCHAR(30) NOT NULL)");
	}
	/**/
}
