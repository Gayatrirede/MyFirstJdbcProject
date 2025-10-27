package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ChatBridge {

	public static void main(String[] args) {

		try {

			String user = "root";
			String pass = "gayatri@04";
			String url = "jdbc:mysql://localhost:3306/ChatBridge";

			//CREATE DATABASE CONNECTION
			Connection con =DriverManager.getConnection(url,user,pass);
			Scanner sc = new Scanner(System.in);
			
			System.out.println("----- WELCOME TO ChatBridge 🤗 -----\n");
			
			//CHECK IF EXISTING USERS
			System.out.println("Are you an existing user? (yes/no)");
			String ExistingUser = sc.next();
			String uid;
			
			if(ExistingUser.equalsIgnoreCase("yes")){
				System.out.println("Enter your UId to login: ");
				uid = sc.next();
				
				PreparedStatement p = con.prepareStatement("SELECT * FROM users WHERE UId = ?");
				p.setString(1, uid);
				
				ResultSet r = p.executeQuery();
				
				System.out.println("Enter your Password to login: ");
				pass = sc.next();
				
				PreparedStatement pr = con.prepareStatement("SELECT * FROM users WHERE Password  = ?");
				pr.setString(1, pass);
				
				ResultSet r2 = p.executeQuery();

				if (r2.next()) {
					System.out.println("Welcome back, " + r.getString("UName"));

					//IF USER WANTS TO UPDATE USER DETAILS
					System.out.println("WOULD YOU LIKE TO UPDATE YOUR DETAILS? (YES/NO)");
					String updateChoice = sc.next();

					if (updateChoice.equalsIgnoreCase("yes")) {
						updateUserDetails(uid, con, sc);
					}
					} else {                                                        
						System.out.println("USER NOT FOUND. PLEASE REGISTER FIRST.");
						uid = registerNewUser(con, sc);
					}
				}else {
					uid = registerNewUser(con, sc);
				}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static String registerNewUser(Connection con, Scanner sc) {
		try {
	
			PreparedStatement psmrt = con.prepareStatement("INSERT into  users values(?,?,?,?)");
			
			System.out.println("CREATE YOUR USER ID");
			String uid = sc.next();
			
			System.out.println("ENTER YOUR FULL NAME");
			String nm = sc.next();
			
			System.out.println("CREATE YOUR PASSWORD");
			String pas= sc.next();
			
			System.out.println("ENTER YOUR PHONE NO");
			String ph = sc.next();
			
			psmrt.setString(1, uid);
			psmrt.setString(2, nm);
			psmrt.setString(3, pas);
			psmrt.setString(4, ph);
			
			int r =psmrt.executeUpdate();

			if(r>0) {
				System.out.println("User registered successfully! Logging you in...");
				return uid;
			}else {
				System.out.println("Registration failed.");
				return null;
			}
			
	} catch (SQLException e) {				
		e.printStackTrace();
	}
		return null;
	}

	private static void updateUserDetails(String uid, Connection con, Scanner sc) {
		try {
		//IF USER WANTS TO UPDATE USER DETAILS
		System.out.println("WHAT WOULD YOU LIKE TO UPDATE? (Name/Password/PhoneNo)");
		String updateChoice = sc.next().toLowerCase(); 
		
		String newValue;
		PreparedStatement pstmt = null;

		switch (updateChoice) {
		case "name":
			System.out.print("Enter new Name: ");
			sc.nextLine(); 
			newValue = sc.next();
			pstmt = con.prepareStatement("UPDATE users SET Name = ? WHERE UId = ?");
			break;
		case "password":
			System.out.print("Enter new Password: ");
			sc.nextLine();
			newValue = sc.next();
			pstmt = con.prepareStatement( "UPDATE users SET Password = ? WHERE UId = ?");
			break;
		case "phoneno":
			System.out.print("Enter new Phone Number: ");
			newValue = sc.next();
			pstmt = con.prepareStatement("UPDATE users SET PhoneNo = ? WHERE UId = ?");
			break;
		default:
			System.out.println("Invalid choice! Please enter a valid field name.");
			return;
		}
		pstmt.setString(1, newValue);
		pstmt.setString(2, updateChoice);
		// EXUCUTE THE UPDATE
		

		int rowsAffected = pstmt.executeUpdate();

		if (rowsAffected > 0) {
			System.out.println("USER DATA UPDATES SUCSSESFULLY!");
		} else {
			System.out.println("UPDATE FAILD! USER ID NOT FOUND.");
		}
	
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
	}
}
