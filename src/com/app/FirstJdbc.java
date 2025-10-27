package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class FirstJdbc {

	public static void main(String[] args) {


		try {

			//step 1: Register Driver

			Class.forName("com.mysql.cj.jdbc.Driver");

			//step 2: create connection

			String user = "root";
			String pass = "your_password_here";
			String url = "jdbc:mysql://localhost:3306/school";

			Connection con = DriverManager.getConnection(url,user,pass);

			// create statement

			Statement st =  con.createStatement();

			//query execute

			//st.executeUpdate("insert into student values (9,'sonu',5)");
			//st.executeUpdate("update student set sid =8 where sid =9");

			int no= st.executeUpdate("DELETE FROM student WHERE sname = 'sonu'");

			System.out.println(no);

			ResultSet rs = st.executeQuery("select * from student");

			while(rs.next()) {
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+"  "+ rs.getFloat(3));
			}

			//connection closing


			con.close();

			System.out.print("Recorde inserted ");

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
