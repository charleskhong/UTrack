package cs5530;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OpinionRating
{

	/**
	 * Add rating for an opinion
	 * @param oid
	 * @param login
	 * @param score
	 * @param stmt
	 */
	public static boolean addRating(int oid, String login, int score, Statement stmt)
	{
		// Opinion must exist
		if (!Opinion.opinionExists(oid, stmt))
		{
			System.out.println("You can't rate an opinion that doesn't exist!");
			return false;
		}
		
		// Cannot rate own opinion
		String lg = Opinion.getOpinion(oid, stmt);
		if (lg.equals(login))
		{
			System.out.println("Cannot rate your own opinion.");
			return false;
		}
		else if (lg.equals(""))
		{
			System.out.println("Opinion does not exist");
			return false;
		}
		
		// Add into database
		String sql = "insert into opinionratings (oid, login, usefulness) values (" + oid + ", '" + login + "', " + score + ")";
		System.out.println("Executing: " + sql);
		try
		{
			stmt.executeUpdate(sql);
			System.out.println("Opinion successfully rated");
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Rating failed to execute");
			return false;
		}
	}
	
	/**
	 * Get average rating for an opinion
	 * @param oid
	 * @param stmt
	 * @return
	 */
	public static float getAverageRating(int oid, Statement stmt)
	{
		String sql = "select avg(usefulness) from opinionratings where oid = " + oid;
		ResultSet rs = null;
		float avg = 0;
		try
		{
			rs = stmt.executeQuery(sql);
			if (rs.next())
				avg = rs.getFloat(1);
			rs.close();
			return avg;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get average rating for oid: " + oid);
			return 0;
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
