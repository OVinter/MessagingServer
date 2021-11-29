//package com.example.pcbe;
//
//import apps.CoffeeProviderApp;
//import consumer.Consumer;
//import entities.messages.PrivateMessage;
//
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//
//// this is the server class
//public class PcbeApplication {
//
//	public static void main(String[] args) {
//
//
//		DbContext dbContext = DbContext.getInstance();
//
//		try{
//			Statement stmt = dbContext.getDbConnection();
//			System.out.println(stmt.getConnection());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		DatabaseConfig.createTable(dbContext);
//		DatabaseConfig.populateTable(dbContext);
//
////		Producer.main(args);
////		Consumer.main(args);
////		CoffeeProviderApp.main(args);
////		Consumer.main(args);
//
////		BasicApp coffeeShopApp = CoffeeShopApp.getInstance("La colt");
////		System.out.println(coffeeShopApp.getClass());
////		System.out.println(((CoffeeShopApp) coffeeShopApp).getCoffeeShop().toString());
//	}
//}
