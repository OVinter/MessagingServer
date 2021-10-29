package com.example.pcbe;

import java.io.FileReader;
import java.io.IOException;

import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PcbeApplication {

	public static void main(String[] args){

		JSONParser parser = new JSONParser();
		String user = "", pass = "", dbURL = "";

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
			System.out.println(e);
		}
	}

}
