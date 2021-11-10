package com.example.pcbe;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public final class DbContext {

    private static volatile DbContext instance;
    private static String dbURL;
    private static String user;
    private static String pass;

    private DbContext(String dbURL, String user, String pass) {
        DbContext.dbURL = dbURL;
        DbContext.user = user;
        DbContext.pass = pass;
    }

    public static DbContext getInstance() {

        JSONParser parser = new JSONParser();
        String user = "", pass = "", dbURL = "";

        // to know if we run from ide or from a jar (for paths reason)
        // if you want to run the program from jar you need to copy the AppSettings.json
        // file into the MessagingServer_jar directory
        String protocol = Optional.ofNullable(PcbeApplication.class.getResource("").getProtocol())
                .orElse("");

        try {
            File file = new File("AppSettings.json");
            if("file".equals(protocol)) {
                file = new File("src/main/resources/static/AppSettings.json");
            }

            JSONObject obj = (JSONObject) parser.parse(
                    new FileReader(file)
            );
            user = (String) obj.get("user");
            pass = (String) obj.get("pass");
            dbURL = (String) obj.get("dbURL");
        } catch (IOException | ParseException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        DbContext resultDb = instance;

        if (resultDb != null) {
            return resultDb;
        }

        synchronized (DbContext.class) {
            if(instance == null) {
                instance = new DbContext(dbURL, user, pass);
            }
            return instance;
        }
    }

    public Statement getDbConnection() throws SQLException{
        Connection conn = DriverManager.getConnection(dbURL, user, pass);
        Statement stmt = conn.createStatement();
        System.out.println(stmt.getConnection().toString());
        return stmt;
    }

    public DatabaseMetaData getDbMetaData() throws SQLException{
        Connection conn = DriverManager.getConnection(dbURL, user, pass);
        DatabaseMetaData metaData = conn.getMetaData();
        System.out.println(metaData.toString());
        return metaData;
    }

}
