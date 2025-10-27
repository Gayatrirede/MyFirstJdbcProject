package com.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BackentSide {

	public static void main(String[] args) {
		
		try {
			//query execute
		System.out.println("products available in the store");
		String user = "root";
		String pass = "gayatri@04";
		String url = "jdbc:mysql://localhost:3306/GayatriMart";

		Connection con =DriverManager.getConnection(url,user,pass);

		// create statement

		Statement st;
			st = con.createStatement();
		
			st.executeUpdate("insert into items values (001 , ' Basmati tukda ' ,'1 ', 50)");
			st.executeUpdate("insert into items values (002 , ' Davat Rice ' , '1' ,100)");
			st.executeUpdate("insert into items values (003 , ' Wheat '  , ' 1 ', 40)");
			st.executeUpdate("insert into items values (004 , ' Wheat Flour ' ,'2 ',45)");
			st.executeUpdate("insert into items values (005 , ' Salta ','2 ' , 30 )");
			st.executeUpdate("insert into items values (006 , ' Suger ','1 ', 100 )");
			st.executeUpdate("insert into items values (007 , ' ToothPest ','1',55)");
			st.executeUpdate("insert into items values (008 , ' Brush','3',70)");
			st.executeUpdate("insert into items values (009 , ' Huny ' , '1 ' , 20 )");
			st.executeUpdate("insert into items values (010 , ' Red chilli Powder','1', 80 )");
			st.executeUpdate("insert into items values (011 , ' Basan','1',48)");
			st.executeUpdate("insert into items values (012 , ' Turmaric Powder ' , ' 1 ' , 37 )");
			st.executeUpdate("insert into items values (013 , '  cooking Oil','50', 40 )");
			

			ResultSet rs = st.executeQuery("select * from items");

			while(rs.next()) {
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+"  "+ rs.getString(3)+" "+rs.getInt(4));
			}
		

			//connection closing
			con.close();
			
			System.out.print("Recorde inserted ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		


	}

}
