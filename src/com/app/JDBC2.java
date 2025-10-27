package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBC2 {

	public static void main(String[] args) {

		try {
			
		String user = "root";
		String pass = "gayatri@04";
		String url = "jdbc:mysql://localhost:3306/school";

		
			Connection con =DriverManager.getConnection(url,user,pass);
			
			//prepaed statment
			
			//PreparedStatement psmt = con.prepareStatement("Insert into student value (?,?,?)");
			PreparedStatement psmt = con.prepareStatement("Update student SET sid = ? WHERE sid =? ");
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter new sId to update : ");
			int newid = sc.nextInt();
			
			System.out.println(" Enter existing sId to match (where to update):");
            int oldId = sc.nextInt();
			
			//System.out.println("Enter sname");
		//	String nm = sc.next();
			
			//System.out.println("Enter std");
			//int st = sc.nextInt();
			
			psmt.setInt(1, newid);
			//psmt.setString(2, nm);
			psmt.setInt(2, oldId);
			
			int r=psmt.executeUpdate();
			
			if(r>0) {
				System.out.println("inserted");
			}else {
				System.out.println("not inserted");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
