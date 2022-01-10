package com.github.tadukoo.database.mysql;

import com.github.tadukoo.database.mysql.syntax.statement.SQLCreateStatement;
import com.github.tadukoo.database.mysql.syntax.statement.SQLDropStatement;
import com.github.tadukoo.database.mysql.transaction.update.Updates;
import com.github.tadukoo.util.junit.logger.JUnitEasyLogger;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.sql.SQLException;

public abstract class DatabaseConnectionTest{
	protected static final String databaseName = "TadukooDatabaseTest";
	protected static final JUnitEasyLogger logger = new JUnitEasyLogger();
	protected static Database db;
	
	@BeforeAll
	public static void setupDB() throws SQLException{
		db = Database.builder()
				.logger(logger)
				.host("localhost")
				.username("root")
				.password("")
				.build();
		db.executeTransaction(Updates.createUpdates("Create DB TadukooDatabaseTest",
				null, ListUtil.createList(SQLCreateStatement.builder()
						.database()
						.databaseName(databaseName)
						.build()
						.toString())));
		db = Database.builder()
				.logger(logger)
				.host("localhost")
				.databaseName(databaseName)
				.username("root")
				.password("")
				.build();
	}
	
	@AfterAll
	public static void cleanupDB() throws SQLException{
		db = Database.builder()
				.logger(logger)
				.host("localhost")
				.username("root")
				.password("")
				.build();
		db.executeTransaction(Updates.createUpdates("Drop DB TadukooDatabaseTest",
				null, ListUtil.createList(SQLDropStatement.builder()
						.database()
						.name(databaseName)
						.build()
						.toString())));
	}
}
