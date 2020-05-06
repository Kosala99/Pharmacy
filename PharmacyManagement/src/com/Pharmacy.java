package com;

import java.sql.*;

public class Pharmacy {
	private Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pharmacy?serverTimezone=UTC", "root", "root123");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return con;
	}
	
	public String insertItem(String no, String pName, String amount, String mDetails, String dName, String email)
	{
		String output = "";
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for inserting.";
			}
			
			// create a prepared statement
			String query = " insert into pharmacydb (`billId`,`billNo`,`patientName`,`amount`,`medicationDetails`,`doctorName`,`email`) values (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, no);
			preparedStmt.setString(3, pName);
			preparedStmt.setDouble(4, Double.parseDouble(amount));
			preparedStmt.setString(5, mDetails);
			preparedStmt.setString(6, dName);
			preparedStmt.setString(7, email);
			
			
			preparedStmt.execute();
			con.close();
			
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" +newItems + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	
	public String readItems()
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			
			// Prepare the html table to be displayed
			output = "<table border='1'>"
					+ "<tr><th>Bill No</th>"
					+ "<th>Patient Name</th>"
					+ "<th>Amount</th>"
					+ "<th>Medication Details</th>"
					+ "<th>Doctor Name</th>"
					+ "<th>Email</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th></tr>";
	
			String query = "select * from pharmacydb";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next())
			{
				String billId = Integer.toString(rs.getInt("billId"));
				String billNo = rs.getString("billNo");
				String patientName = rs.getString("patientName");
				String amount = Double.toString(rs.getDouble("amount"));
				String medicationDetails = rs.getString("medicationDetails");
				String doctorName = rs.getString("doctorName");
				String email = rs.getString("email");
				
				// Add into the html table
				output += "<tr><td><input id='hidItemIDUpdate'name='hidItemIDUpdate' type='hidden' value='" + billId+ "'>" + billNo + "</td>";
				output += "<td>" + patientName + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + medicationDetails + "</td>";
				output += "<td>" + doctorName + "</td>";
				output += "<td>" + email + "</td>";
			
				// buttons
				output += "<td><input name='btnUpdate'type='button' "
						+ "value='Update'class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove'type='button' "
						+ "value='Remove'class='btnRemove btn btn-danger'data-billId='"+ billId + "'>" + "</td></tr>";
			}
			
			con.close();
			
			// Complete the html table
			output += "</table>";
			
		}
		catch (Exception e)
		{
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	
	public String updateItem(String ID, String no, String pName, String amount, String mDetails, String dName, String email)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for updating.";
			}
			
			// create a prepared statement
			String query = "UPDATE pharmacydb SET billNo=?,patientName=?,Amount=?,medicationDetails=?,doctorName=?,email=? WHERE billId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, no);
			preparedStmt.setString(2, pName);
			preparedStmt.setDouble(3, Double.parseDouble(amount));
			preparedStmt.setString(4, mDetails);
			preparedStmt.setString(5, dName);
			preparedStmt.setString(6, email);
			preparedStmt.setInt(7, Integer.parseInt(ID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	
	
	public String deleteItem(String billId)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for deleting.";
			}
			
			// create a prepared statement
			String query = "delete from pharmacydb where billId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(billId));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
}
