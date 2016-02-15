package company.helloworld.servlet;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import java.sql.Connection;

public class selectRows {
	private Connection conn;
	private Statement st;
	ArrayList<String> Countries = new ArrayList<String>();
	int[] myCloumns ={1,3,4,16,17,18,20};
	public selectRows(){
		this.conn = new ConnectDB("testuser","testuser").connect();		
	}
	public String queryEndUserCountry() throws SQLException{
		String htmlOutput = "";
		queryAllCountries();		
		for(String country:Countries){
		String query = "select * from (select * from project "
						+ "where endusercountry = '"+country+"' order by bookings desc)"
						+ " WHERE ROWNUM <= 10";
		this.st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		//start of table for each country
		htmlOutput = htmlOutput+"<font size = \"3\">Top 10 bookings from "
					+new CountryCodes().getCountryName(country)+"</font><br><br>";
		htmlOutput = htmlOutput+"<table border = 1>";
		ResultSetMetaData rsmd = rs.getMetaData();
		
		//print labels
		htmlOutput = htmlOutput+"<tr>";
		for(int m = 0;m < myCloumns.length;m++){
			htmlOutput = htmlOutput+"<td BGCOLOR=\"#FDF5E6\">"
					+rsmd.getColumnLabel(myCloumns[m])+"</td>";
		}		
		htmlOutput = htmlOutput+"</tr>";
		
		//print data
		htmlOutput = htmlOutput+"<tr>";
		while(rs.next()){
			for(int i = 0;i < myCloumns.length;i++){
				htmlOutput = htmlOutput+"<td>"+rs.getString(myCloumns[i])+"</td>";
			}
			htmlOutput = htmlOutput+"</tr>";
		}
		
		htmlOutput = htmlOutput+"</table>";
		
		htmlOutput = htmlOutput+"<font color = \"green\">"+queryTotalBookings(country)+""
					+ "</font><br><br>";
		}
		return htmlOutput;
	}
	/*
	 * Method to query total bookings
	 */
	public String queryTotalBookings(String country) throws SQLException{
		String total = "";
		this.st = conn.createStatement();
		
		String query = "select sum(bookings) from project "
						+ "where endusercountry ='"+country+"'";
		ResultSet rs = st.executeQuery(query);
		while(rs.next()){
			total = total+rs.getString(1);
		}
		
		return "Total bookings "+total;
		
	}
	/*
	 * Method to get all distinct country codes from 'endusercountry' column
	 */
	public void queryAllCountries() throws SQLException{
		String query = "select distinct endusercountry from project";
		this.st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()){
				Countries.add(rs.getString(1));//adding results to Countries list
		}
		
		
	}
}
