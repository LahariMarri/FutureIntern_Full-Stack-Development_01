package userForm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
	private static final String Url="jdbc:oracle:thin:@localhost:1521:xe";
	private static final String User="system";
	private static final String Password="marri";
	
	public static Connection getConnection() throws SQLException
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			return DriverManager.getConnection(Url, User, Password);
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Oracle JDBC Driver not found..");
			e.printStackTrace();
			throw new SQLException(e);
		}
	}

}