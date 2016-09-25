package cs5530;

import java.sql.*;

public class Connector
{
	public Connection con;
	public Statement stmt;

	public Connector() throws Exception
	{
		try
		{
			// TODO: Migrate these to command line args
			String userName = ""; 
			String password = "";
			String url = "";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, userName, password);

			stmt = con.createStatement();
		}
		catch (Exception e)
		{
			System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
			System.err.println(e.getMessage());
			throw (e);
		}
	}

	public void closeConnection() throws Exception
	{
		con.close();
	}
}
