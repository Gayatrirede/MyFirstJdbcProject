package com.app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GayatriMart {

	public static void main(String[] args) {
		try {
			String user = "root";
			String pass = "gayatri@04";
			String url = "jdbc:mysql://localhost:3306/gayatrimart";

			//CREATE DATABASE CONNECTION

			Connection con =DriverManager.getConnection(url,user,pass);
			Scanner sc = new Scanner(System.in);

			System.out.println("WELCOM TO GAYATRI MART 😊\n");

			//CHECK IF EXISTING USERS
			System.out.println("Are you an existing user? (yes/no)");
			String ExistingUser = sc.next();
			int userId=-1;

			if(ExistingUser.equalsIgnoreCase("yes")){
				System.out.println("Enter your UId to login: ");
				userId = sc.nextInt();

				PreparedStatement p = con.prepareStatement("SELECT * FROM users WHERE UId = ?");
				p.setInt(1, userId);

				ResultSet r = p.executeQuery();

				if (r.next()) {
					System.out.println("Welcome back, " + r.getString("Name"));

					//IF USER WANTS TO UPDATE USER DETAILS
					System.out.println("WOULD YOU LIKE TO UPDATE YOUR DETAILS? (YES/NO)");
					String updateChoice = sc.next();

					if (updateChoice.equalsIgnoreCase("yes")) {
						updateUserDetails(userId, con, sc);
					}
					} else {                               
						System.out.println("USER NOT FOUND. PLEASE REGISTER FIRST.");
						userId = registerNewUser(con, sc);
					}
				}else {
					userId = registerNewUser(con, sc);
				}
			
			if(userId!=-1){
				showUserOrders(userId, con, sc);
			}

			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static int registerNewUser(Connection con, Scanner sc) {

		try {
			PreparedStatement p1 = con.prepareStatement("INSERT INTO users values(?,?,?,?,?)");

			System.out.println("CREATE YOUR USER ID");
			int uid = sc.nextInt();

			System.out.println("ENTER YOUR NAME  ");
			String nm = sc.next();

			System.out.println("CREATE PASSWORD");
			String ps = sc.next();


			System.out.println("ENTER YOUR PHONE NO");
			String  ph = sc.next();

			System.out.println("ENTER YOUR EMAIL");
			String em = sc.next();

			p1.setInt(1, uid);
			p1.setString(2, nm);
			p1.setString(3, ps);
			p1.setString(4, ph);
			p1.setString(5, em);

			int r1=p1.executeUpdate();

			if(r1>0) {
				System.out.println("User registered successfully! Logging you in...");
				return uid;
			}else {
				System.out.println("Registration failed.");
				return -1;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
		
	}
	
	private static void showUserOrders(int userId, Connection con, Scanner sc2) {

		Scanner sc = new Scanner(System.in);

		try {

			PreparedStatement p = con.prepareStatement("SELECT * FROM orders WHERE UId = ?");
			p.setInt(1, userId);
			ResultSet r = p.executeQuery();

			//CHECK IF USER HAVE ANY PRIVIOUS ORDERS
			if(r.next()) {
				System.out.println("YOUR PRIVIOUS ORDERS");
				do {
					System.out.println("Order Id " + r.getInt("OrderId"));
					System.out.println("Order Date " + r.getDate("OrderDate"));
					System.out.println("item id " + r.getInt("item_id"));
					System.out.println("Quantity " + r.getInt("Quantity"));
					System.out.println("Total Amount " + r.getBigDecimal("Total_Amount"));

				}while(r.next());
				System.out.println("DO YOU WANT TO UPDATE YOUR ORDERS(YES/NO)");
				String updateorder = sc.next();
				
						if(updateorder.equalsIgnoreCase("yes")) {
							createNewOrder(userId, con, sc);
						}else {
						System.out.println("THANK YOU FOR VISITING GAYATRI MART 🤗❤️\n");
						System.out.println("VISIT US AGIN 🤗❤️\n");
						}
			} else {
				System.out.println("YOU HAVE NO PRIVIOUS ORDERS ");
				System.out.println("Do you want to create a new order? (yes/no)");
				String createorder = sc.next();
			
			if (createorder.equalsIgnoreCase("yes")) {
		        createNewOrder(userId, con, sc);
		    } else {
		        System.out.println("Thank you for visiting Gayatri Mart!");
		    }
			}

			
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static void createNewOrder(int userId, Connection con, Scanner sc) {

		try {
			System.out.println("ITEMS AVAIALBEL IN OUR STORE....\n");
			System.out.println("ID | Item Name         |WeightQty| Price");
			System.out.println("1  | Basmati tukda     |  1kg    |  50  ");
			System.out.println("2  | Davat Rice        |  1kg    |  100 ");
			System.out.println("3  | Wheat             |  1kg    |  40  ");
			System.out.println("4  | Wheat Flour       |  2kg    |  45  ");
			System.out.println("5  | Salta             |  200gm  |  30  ");
			System.out.println("6  | Suger             |  1/2kg  |  100 ");
			System.out.println("7  | ToothPest         |  1pack  |  55  ");
			System.out.println("8  | Brush             |  3      |  70  ");
			System.out.println("9  | Huny              |  1gm    |  20  ");
			System.out.println("10 | Red chilli Powder |  100gm  |  80  ");
			System.out.println("11 | Basan             |  1kg    |  48  ");
			System.out.println("12 | Turmaric Powder   |  100gm  |  37  ");
			System.out.println("13 | cooking Oil       |   50    |  40 ");


			System.out.println("Enter the Item ID:");
			int itemId = sc.nextInt();

			System.out.println("Enter the Quantity:");
			int quantity = sc.nextInt();

			double itemPrice = getItemPrice_RS(con, itemId);
			if (itemPrice == -1) {
				System.out.println("Item ID not found, can't create the order.");
				return;
			}

			int totalAmount = (int) (itemPrice * quantity);

			// GET CURRENT DATA
			java.sql.Date orderDate = new java.sql.Date(System.currentTimeMillis());

			PreparedStatement insertStmt = con.prepareStatement("INSERT INTO orders (UId, OrderDate, item_id, Quantity, Total_Amount) VALUES (?, ?, ?, ?, ?)");

			insertStmt.setInt(1, userId);
			insertStmt.setDate(2, orderDate);
			insertStmt.setInt(3, itemId);
			insertStmt.setInt(4, quantity);
			insertStmt.setDouble(5, totalAmount);

			int rowsInserted = insertStmt.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("ORDERD PLACED SUCCESSFULLY!");
				System.out.println("\n--- BILL ---");
				System.out.println("User ID: " + userId);
				System.out.println("Item ID: " + itemId);
				System.out.println("Quantity: " + quantity);
				System.out.println("Total Amount: " + totalAmount);
				System.out.println("Thank you for shopping at Gayatri Mart!");
			} else {
				System.out.println("FAILD TO CREATE ORDER.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	private static void updateUserDetails(int userId, Connection con, Scanner sc) {
		try {
			//IF USER WANTS TO UPDATE USER DETAILS
			System.out.println("WHAT WOULD YOU LIKE TO UPDATE? (Name/Password/PhoneNo/Email)");
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
				pstmt = con.prepareStatement("UPDATE users SET Password = ? WHERE UId = ?");
				break;
			case "phoneno":
				System.out.print("Enter new Phone Number: ");
				newValue = sc.next();
				pstmt = con.prepareStatement("UPDATE users SET PhoneNo = ? WHERE UId = ?");
				break;
			case "email":
				System.out.print("Enter new Email: ");
				sc.nextLine(); 
				newValue = sc.next();
				pstmt = con.prepareStatement( "UPDATE users SET Email = ? WHERE UId = ?");
				break;
			default:
				System.out.println("Invalid choice! Please enter a valid field name.");
				return;
			}
			pstmt.setString(1, newValue);
			pstmt.setInt(2, userId);
			// EXUCUTE THE UPDATE
			int rowsAffected;

			rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("USER DATA UPDATES SUCSSESFULLY!");
			} else {
				System.out.println("UPDATE FAILD! USER ID NOT FOUND.");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	//FUNCTION TO DISPLAY USER ORDERS
	private static int getItemPrice_RS(Connection con, int itemId) {
		try {

			PreparedStatement psmt = con.prepareStatement("SELECT Price_RS FROM items WHERE item_Id = ?");
			psmt.setInt(1, itemId);
			ResultSet rs = psmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("Price_RS");  
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;

	}

}


