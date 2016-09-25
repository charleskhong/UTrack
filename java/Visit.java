package cs5530;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Visit
{
	public String login;
	public String date;
	public int pid;
	public int size;
	public double cost;
	
	public Visit()
	{

	}
	
	/**
	 * Find suggested POIs based on one visited
	 * @param pid
	 * @param login
	 * @param stmt
	 * @return
	 */
	public static List<PlaceOfInterest> suggestedPOIs(int pid, String login, Statement stmt)
	{
		String sql = "select v2.pid, count(*) from visits v1, visits v2 where v1.pid = " + pid +
				" and v2.pid != " + pid + " and v1.login != '" + login + "' group by v2.pid order by count(*) desc";
		ResultSet rs = null;
		List<PlaceOfInterest> places = new ArrayList<PlaceOfInterest>();
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				PlaceOfInterest p = new PlaceOfInterest();
				p.pid = rs.getInt(1);
				p.visits = rs.getInt(2);
				places.add(p);
			}
			rs.close();
			return places;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get suggested places for pid: " + pid);
			return new ArrayList<PlaceOfInterest>();
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
	 * Find places a user has visited
	 * @param login
	 * @param stmt
	 * @return
	 */
	public static List<Integer> getVisited(String login, Statement stmt)
	{
		String sql = "select distinct pid from visits where login = '" + login + "'";
		ResultSet rs = null;
		List<Integer> places = new ArrayList<Integer>();
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
				places.add(rs.getInt(1));
			rs.close();
			return places;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get places visited for login: " + login);
			return new ArrayList<Integer>();
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
	 * Create visit event
	 * @param cost
	 * @param partysize
	 * @param stmt
	 * @return
	 */
	private static int createVisitEvent(double cost, int partysize, Statement stmt)
	{
		String sql = "insert into visitevents (cost, partysize) values (" + cost + ", " + partysize + ")";
		ResultSet rs = null;
		int vid = -1;
		try
		{
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			System.out.println("Visit event successfully inserted");
			rs = stmt.getGeneratedKeys();
			if (rs.next())
				vid = rs.getInt(1);
			rs.close();

		}
		catch (SQLException e)
		{
			System.out.println("Insert failed to execute");
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
		return vid;
	}

	/**
	 * Add visit and visit event
	 * @param login
	 * @param pid
	 * @param cost
	 * @param partysize
	 * @param date
	 * @param stmt
	 */
	public static void addVisit(String login, int pid, double cost, int partysize, String date, Statement stmt)
	{
		if (!PlaceOfInterest.poiExists(pid, stmt))
		{
			System.out.println("You can't visit a place that doesn't exist!");
			return;
		}
		
		int vid = createVisitEvent(cost, partysize, stmt);
		if (vid == -1)
			return;

		String sql = "insert into visits values ('" + login + "', " + pid + ", " + vid + ", STR_TO_DATE('" + date + "', '%m/%d/%Y'))";
		System.out.println("Executing: " + sql);

		try
		{
			stmt.executeUpdate(sql);
			System.out.println("Visit successfully inserted");
		}
		catch (SQLException e)
		{
			System.out.println("Insert failed to execute");
		}
	}
}
