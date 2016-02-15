package company.helloworld.servlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectDB {
	private String username ="testuser" ;
	private String password ="testuser";
	public ConnectDB(String username,String password){
		this.username = username;
		this.password = password;
	}
	//returns Connection object
	public Connection connect(){
		
		try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:"+username+"/"+password+"@localhost:1521/orcl");
		return con;
		}catch(ClassNotFoundException e){
			System.out.println("Error in driver class Name");
		} catch (SQLException e) {
			System.out.println("Error at connection string, Check Username, Password");
		}
		return null;
	}

}
