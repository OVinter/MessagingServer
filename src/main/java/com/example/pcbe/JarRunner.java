package com.example.pcbe;

import apps.CoffeeProviderApp;
import apps.CoffeeShopApp;
import apps.CustomerApp;
import entities.users.User;
import entities.users.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JarRunner {

    public static void startMainByType(String type)
    {
        String[] args = new String[0];
        switch (type)
        {
            case "CUSTOMER":
                CustomerApp.main(args);
                break;
            case "COFFEE_ADMIN":
                CoffeeShopApp.main(args);
                break;
            case "PROVIDER":
                CoffeeProviderApp.main(args);
                break;
            default:
                System.out.println("Invalid user");
        }
    }

    public static String getUserTypeFromDatabase(String username)
    {
        DbContext dbContext = DbContext.getInstance();
        String sql = "SELECT name, type " +
                "FROM users";
        try{
            Statement stmt = dbContext.getDbConnection();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                if(username.equals(rs.getString("name")))
                    return rs.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "NO_TYPE";
    }

    private static UserType getUserTypeFromString(String type)
    {
        switch (type)
        {
            case "CUSTOMER":
                return UserType.CUSTOMER;
            case "SHOP":
                return UserType.COFFEE_ADMIN;
            case "PROVIDER":
                return UserType.PROVIDER;
            default:
                System.out.println("Not a valid type");
        }
        return null;
    }

    public static void addUserToDb(String username, UserType userType)
    {
        DbContext dbContext = DbContext.getInstance();
        User newUser = new User(username, userType);

        try(Statement stmt = dbContext.getDbConnection())
        {
            String sql = String.format("INSERT INTO USERS VALUES (%d,\"%s\",\"%s\")",newUser.getId(),newUser.getName(),newUser.getUserType().toString());
            stmt.executeUpdate(sql);
            System.out.println("User registered");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void startApp(String[] args)
    {
        if(args.length == 0)
        {
            String username, type;
            Scanner sc = new Scanner(System.in);
            int choiceentry;
            do {
                System.out.println("\n-----MENU-----\n\n \"1\" Login \n \"2\" Register \n \"3\" Exit");
                choiceentry = sc.nextInt();

                switch (choiceentry)
                {
                    case 1:
                        System.out.println("Username:");
                        username = sc.next();
                        startMainByType(getUserTypeFromDatabase(username));
                        break;
                    case 2:
                        System.out.println("Name : ");
                        username = sc.next();
                        System.out.println("Type [CUSTOMER / SHOP / PROVIDER]: ");
                        type = sc.next();
                        addUserToDb(username, getUserTypeFromString(type));
                        break;
                    case 3:
                        System.out.println("Exit");
                        break;
                    default:
                        System.out.println("Choice must be a value between 1 and 3.");
                }
            } while (choiceentry != 3);
        }
        else
        {
            PcbeApplication.main(args);
        }
    }

    public static void main(String[] args) {
        // TODO: make a menu with: 1.Login and 2.Register
        // TODO: let a new customer to register (you also need to update the user table)
        startApp(args);
    }
}
