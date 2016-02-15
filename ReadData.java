package company.helloworld.servlet;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Arrays;
import java.util.NoSuchElementException;



public class ReadData {
	
@SuppressWarnings("unused")
private static void createDbUserTable(Connection conn) throws SQLException, ClassNotFoundException{
		String createTableSQL = "CREATE TABLE project("
				+"OrderNumber NUMBER(30), "
				+"ConstantCurrency NUMBER(30), "
				+"Bookings NUMBER(30), "
				+"RouteToMarket VARCHAR(20), "
				+"AsIsSplit VARCHAR(50), "
				+"SagittaAsIsSubRegion VARCHAR(50), "
				+"SagittaAsIsTerritory VARCHAR(40), "
				+"VMStarAccountLinkType VARCHAR(25), "
				+"VMStarSubRegion VARCHAR(40), "
				+"VMStarAccountTerritory VARCHAR(40), "
				+"VMStarAccountName VARCHAR(100), "
				+"VMStarCustomerID VARCHAR(25), "
				+"VMStarAccountCountry VARCHAR(50), "
				+"ETMSubRegion VARCHAR(45), "
				+"ETMTerritory VARCHAR(45), "
				+"SubRegionTJ VARCHAR(45), "
				+"TerritoryTJ VARCHAR(50), "
				+"DirectRep VARCHAR(50), "
				+"EndUserOrganization VARCHAR(100), "
				+"EndUserCountry VARCHAR(5) "
				+")";
		
		Statement stmt = conn.createStatement();
		stmt.executeQuery(createTableSQL);
		System.out.println("table created successfully");
		stmt.close();
	}

	public Connection getConnection(){
		return new ConnectDB("testuser","testuser").connect();
	}
	
	public void selectData(Connection conn) throws SQLException{
		String query = "select * from project where endusercountry = 'CA'";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()){
			System.out.println("column 2"+rs.getString(1));
		}
		
	}
	
	public static void insertDbUserTable(String[] values,Connection dbConnection)throws SQLException, ClassNotFoundException{
		
		String query = "INSERT INTO PROJECT VALUES ("
				+ "?,?,?,?,?,"
				+ "?,?,?,?,?,"
				+ "?,?,?,?,?,"
				+ "?,?,?,?,?)";

		try {
			
			 /*
			 * prepared statement for insertion
			 */
		      String const_currency = values[2].toString().replace(",", "");
		      PreparedStatement ps = dbConnection.prepareStatement(query);
		      
		      ps.setString(1,values[1].toString());
		      ps.setString(2,const_currency.trim());
		      ps.setString(3,values[3].toString().trim());
		      ps.setString(4,values[4].toString());
		      ps.setString(5,values[5].toString());
		      ps.setString(6,values[6].toString());
		      ps.setString(7,values[7].toString());
		      ps.setString(8,values[8].toString());
		      ps.setString(9,values[9].toString());
		      ps.setString(10,values[10].toString());
		      ps.setString(11,values[11].toString());
		      ps.setString(12,values[12].toString());
		      ps.setString(13,values[13].toString());
		      ps.setString(14,values[14].toString());
		      ps.setString(15,values[15].toString());
		      ps.setString(16,values[16].toString());
		      ps.setString(17,values[17].toString());
		      ps.setString(18,values[18].toString());
		      ps.setString(19,values[19].toString());
		      ps.setString(20,values[20].toString());
		      
		      ps.executeUpdate();
		      ps.close();
		      dbConnection.commit();
		     
		      
		} catch (SQLException e) {

			System.out.println(e.getMessage());
			System.out.println(Arrays.toString(values)+"\t"+values.length);

		} 

	}
	public static void main(String[] args){
		
		Connection connection = new ReadData().getConnection(); 
		/*
		 *Creating table one time
		 */
		/*try {
			ReadData.createDbUserTable(connection);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}*/
		
		/*
		 * Inserting rows to data One time
		 */
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		//start reading file
		try {
			is = new FileInputStream("data.txt");			
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String line;
			//start read line try block
			int rcount = 0;
			try {
				while ((line = br.readLine()) != null){
					
					String[] row = line.split("\t");
					//System.out.println(Arrays.toString(row));
					//System.out.println(row.length);
					/*
					 * if array length
					 * 
					 */
					
					//send row to insert values in table
					if(row[1]=="22831024")
					System.out.println(Arrays.toString(row));

					try {
						if(row.length==21){
						ReadData.insertDbUserTable(row,connection);
						rcount++;
						/*if(rcount>1 && (rcount%1000==0 || rcount == 3217))
							connection.commit();*/  
						}
						
					} catch (SQLException e) {
						System.out.println(Arrays.toString(row));
						e.printStackTrace();
					}catch (ClassNotFoundException e) {
						
						e.printStackTrace();
					}
					
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}//end reading line
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(NoSuchElementException e){System.out.println("Nothing here");
		}//end reading file
		
	    System.out.println("Successfully completed loading");
	}
	
	

}
