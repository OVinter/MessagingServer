package com.example.pcbe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

    public static DbContext getInstance(String dbURL, String user, String pass) {

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

}
