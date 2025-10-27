package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ChatBridgeFinal {

	public static void main(String[] args) {
		try {
		System.out.println("----- WELCOM TO ChatBridege 🤗 -----");
		

		String user = "root";
		String pass = "gayatri@04";
		String url = "jdbc:mysql://localhost:3306/ChatBridge";

		//CREATE DATABASE CONNECTION
		
			Connection con =DriverManager.getConnection(url,user,pass);
		
		Scanner sc = new Scanner(System.in);
		
	
		System.out.println("ARE YOU EXISTING USER(YES/NO)");
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
