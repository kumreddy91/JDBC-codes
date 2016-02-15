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
		htmlOutput = htmlOutput+"<font size = \"3\">Top bookings from "
					+new CountryCodes().getCountryName(country)+"</font><br><br>";
		
		//sub region list for each country
		ArrayList<String> subregion = new ArrayList<String>();	
		subregion = getSubRegionTJ(country);
		
		//query results for each sub region in country
		
		for(String srtj:subregion){
		String query = "select * from (select * from (select * from project "
						+ "where endusercountry = '"+country+"')"
						+ "where subregiontj ='"+srtj+"'"
						+ "order by bookings desc)"
						+ " WHERE ROWNUM <= 10";
		this.st = conn.createStatement();
		
		htmlOutput = htmlOutput+"<font size = \"3\" color = \"red\">From sub region TJ"
				+srtj+"</font><br><br>";
		//execute query
		ResultSet rs = st.executeQuery(query);
		//print results into html table
		//start of table for each country		
		htmlOutput = htmlOutput+"<table border = 1>";
		ResultSetMetaData rsmd = rs.getMetaData();
		
		//print labels (column names)
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
		htmlOutput = htmlOutput+"<font color = \"green\"> Total Sub Region bookings"
				+ " "+queryTotalBookings(srtj,country)+""
				+ "</font><br><br>";
		}
		htmlOutput = htmlOutput+"<font color = \"green\"> Total Bookings for Country "+queryTotalBookings(country)+""
					+ "</font><br><br>";
		}
		System.out.println(htmlOutput);
		return htmlOutput;
	}
	
	/*
	 * Method to query total bookings
	 */
	private String queryTotalBookings(String country) throws SQLException{
		String total = "";
		this.st = conn.createStatement();
		
		String query = "select sum(bookings) from project "
						+ "where endusercountry ='"+country+"'";
		ResultSet rs = st.executeQuery(query);
		while(rs.next()){
			total = total+rs.getString(1);
		}
		
		return total;
		
	}
	/*
	 * Method to get totals for sub region
	 */
	
	private String queryTotalBookings(String subregion,String country) throws SQLException {
		String total = "";
		this.st = conn.createStatement();
		
		String query = "select sum(bookings) from (select * from project "
						+ "where endusercountry = '"+country+"')"
						+ "where subregiontj ='"+subregion+"'";
		ResultSet rs = st.executeQuery(query);
		while(rs.next()){
			total = total+rs.getString(1);
		}
		
		return total;
		
	}
	/*
	 * get distinct sub region name list for country
	 */
	private ArrayList<String> getSubRegionTJ(String country) throws SQLException {
		ArrayList<String> subregionLocal = new ArrayList<String>();
		String subregionQuery = "select distinct subregiontj from"
		 		+ " project where endusercountry = '"+country+"'";
		st =conn.createStatement();
		ResultSet rs = st.executeQuery(subregionQuery);
		while(rs.next()){
			subregionLocal.add(rs.getString(1));
		}
		return subregionLocal;
	}
	
	/*
	 * Method to get all distinct country codes from 'endusercountry' column
	 */
	private void queryAllCountries() throws SQLException{
		String query = "select distinct endusercountry from project";
		this.st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()){
				Countries.add(rs.getString(1));//adding results to Countries list
		}
		
		
	}
}
