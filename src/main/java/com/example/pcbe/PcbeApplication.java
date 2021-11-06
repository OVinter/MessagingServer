package com.example.pcbe;

import java.io.FileReader;
import java.io.IOException;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import consumer.Consumer;
import entities.Coffee;
import entities.users.User;
import entities.users.UserType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PcbeApplication {

	public static void main(String[] args) throws InterruptedException {

		JSONParser parser = new JSONParser();
		String user = "", pass = "", dbURL = "";

		// TODO: create a serverOrchestrator class in the com.example.pcbe package

		try {
			JSONObject obj = (JSONObject) parser.parse(
					new FileReader("src/main/resources/static/AppSettings.json")
			);
			user = (String) obj.get("user");
			pass = (String) obj.get("pass");
			dbURL = (String) obj.get("dbURL");
		} catch (IOException | ParseException fileNotFoundException) {
			fileNotFoundException.printStackTrace();
		}

		DbContext dbContext = DbContext.getInstance(dbURL, user, pass);

		try{
			Statement stmt = dbContext.getDbConnection();
			System.out.println(stmt.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		createTable(dbContext);
		populateTable(dbContext);

		Producer.main(args);
		Consumer.main(args);

//		BasicApp coffeeShopApp = CoffeeShopApp.getInstance("La colt");
//		System.out.println(coffeeShopApp.getClass());
//		System.out.println(((CoffeeShopApp) coffeeShopApp).getCoffeeShop().toString());
	}

	public static void createTable(DbContext dbContext)
	{
		try(Statement stmt = dbContext.getDbConnection())
		{
			DatabaseMetaData metaData = dbContext.getDbMetaData();

			ResultSet resultSet = metaData.getTables(null,null,"COFFEE",null);

			if (resultSet.next()) {
				String sql = "DROP TABLE COFFEE";
				stmt.executeUpdate(sql);

				sql = "DROP TABLE USERS";
				stmt.executeUpdate(sql);
			}
			String sql = "CREATE TABLE COFFEE " +
					"(id INTEGER not NULL, " +
					" name VARCHAR(255) not NULL," +
					" price FLOAT not NULL)";
			stmt.executeUpdate(sql);
			System.out.println("Created COFFEE table in database...");

			sql = "CREATE TABLE USERS " +
					"(id INTEGER not NULL, " +
					" name VARCHAR(255) not NULL," +
					" type VARCHAR(255) not NULL)";

			stmt.executeUpdate(sql);
			System.out.println("Created USERS table in database...");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void populateTable(DbContext dbContext)
	{
		Coffee coffee1 = new Coffee("latte",10);
		Coffee coffee2 = new Coffee("espresso",7);

		User user1 = new User("Floricel", UserType.PROVIDER);

		try(Statement stmt = dbContext.getDbConnection())
		{
			// Execute a query
			System.out.println("Inserting records into the table...");
			String sql = String.format("INSERT INTO COFFEE VALUES (%d,\"%s\",%f)",coffee1.getId(),coffee1.getName(),coffee1.getPrice());
			stmt.executeUpdate(sql);
			sql = String.format("INSERT INTO COFFEE VALUES (%d,\"%s\",%f)",coffee2.getId(),coffee2.getName(),coffee2.getPrice());
			stmt.executeUpdate(sql);
			sql = String.format("INSERT INTO USERS VALUES (%d,\"%s\",\"%s\")",user1.getId(),user1.getName(),user1.getUserType().toString());
			stmt.executeUpdate(sql);
			System.out.println("Inserted records into the table...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
