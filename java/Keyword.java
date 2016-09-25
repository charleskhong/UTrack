package cs5530;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Keyword
{
	/**
	 * Add keyword to database
	 * @param keyword
	 * @param stmt
	 * @return
	 */
	public static int addKeyword(String keyword, Statement stmt)
	{
		// If it exists just get the id
		int kid = -1;
		kid = keywordExists(keyword, stmt);
		if (kid != -1)
			return kid;
		
		String sql = "insert into keywords (kword) values ('" + keyword + "')";
		ResultSet rs = null;
		try
		{
			// Add and get the key generated from auto incrementing
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);	
			System.out.println("Keyword successfully inserted");
			rs = stmt.getGeneratedKeys();
			if (rs.next())
				kid = rs.getInt(1);
			rs.close();
			return kid;
		}
		catch (SQLException e)
		{
			System.out.println("Insert failed to execute");
			return -1;
		}
		finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			}
			catch (Exception e)
			{
				System.out.println("cannot close resultset");
			}
		}
	}
	
	/**
	 * Get a keyword by id
	 * @param kid
	 * @param stmt
	 * @return
	 */
	public static String getKeyword(int kid, Statement stmt)
	{
		String sql = "select kword from keywords where kid = " + kid;
		ResultSet rs = null;
		String kword = "";
		try
		{
			rs = stmt.executeQuery(sql);
			if (rs.next())
				kword = rs.getString(1);
			rs.close();
			return kword;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get kword for kid: " + kid);
			return "";
		}
		finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			}
			catch (Exception e)
			{
				System.out.println("cannot close resultset");
			}
		}
	}
	
	/**
	 * Check to see if keyword exists
	 * @param keyword
	 * @param stmt
	 * @return
	 */
	public static int keywordExists(String keyword, Statement stmt)
	{
		String sql = "select kid from keywords where kword = '" + keyword + "'";
		int kid = -1;
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			if (rs.next())
				kid = rs.getInt(1);
			rs.close();
			return kid;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get kid for kword: " + keyword);
			return -1;
		}
		finally
		{
			try
			{
				if (rs != null && !rs.isClosed())
					rs.close();
			}
			catch (Exception e)
			{
				System.out.println("cannot close resultset");
			}
		}
	}
}
