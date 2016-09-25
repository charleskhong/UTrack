package cs5530;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Favorite
{
	/**
	 * Get Places favorited by user
	 * @param login - user
	 * @param stmt
	 * @return
	 */
	public static List<Integer> getPidsForLogin(String login, Statement stmt)
	{
		String sql = "select pid from favorites where login = '" + login + "'";
		ResultSet rs = null;
		ArrayList<Integer> pids = new ArrayList<Integer>();
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
				pids.add(rs.getInt(1));
			rs.close();
			return pids;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get favorites for login: " + login);
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
	 * Get degrees of separation for two users
	 * @param u1
	 * @param u2
	 * @param stmt
	 * @return
	 */
	public static int getDegreesOfSeparation(String u1, String u2, Statement stmt)
	{
		if (u1.equals(u2))
			return 0;

		HashSet<String> users = new HashSet<String>();
		HashSet<String> usersChecked = new HashSet<String>();
		HashSet<Integer> pids = new HashSet<Integer>();
		
		// Get Places favorited by user 1
		pids.addAll(getPidsForLogin(u1, stmt));
		usersChecked.add(u1);

		// Check all places user favorited
		for (int pid : pids)
		{
			// Find all other users who favorited the same place
			users.addAll(getUsersWhoFavorited(pid, stmt));
			
			// If we find that user 2 favorited one of these places, 1 degree away
			if (users.contains(u2))
				return 1;
		}
		// Get all users 1 degree away and find places they favorited
		for (String user : users)
		{
			if (usersChecked.contains(user))
				continue;
			
			// Get places 1 degree away favorited
			pids = new HashSet<Integer>();
			pids.addAll(getPidsForLogin(user, stmt));
			usersChecked.add(user);
			
			// For each of those places
			for (int pid : pids)
			{
				// Get users who favorited those places
				HashSet<String> temp = new HashSet<String>();
				temp.addAll(getUsersWhoFavorited(pid, stmt));
				
				// If user 2 favorited the same thing as someone 1 degree away from user 1
				// they are 2 degrees away
				if (temp.contains(u2))
					return 2;
			}
		}
		
		// Not 2 degrees away
		return -1;
	}

	/**
	 * Get users who favorited a place
	 * @param pid - place
	 * @param stmt
	 * @return
	 */
	public static List<String> getUsersWhoFavorited(int pid, Statement stmt)
	{
		String sql = "select login from favorites where pid = " + pid;
		ResultSet rs = null;
		ArrayList<String> users = new ArrayList<String>();
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
				users.add(rs.getString(1));
			rs.close();
			return users;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get users who favorited pid: " + pid);
			return new ArrayList<String>();
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
	 * Add favorite record to database
	 * @param login
	 * @param pid
	 * @param stmt
	 */
	public static void addFavorite(String login, int pid, Statement stmt)
	{
		// If place does not exist cannot favorite
		if (!PlaceOfInterest.poiExists(pid, stmt))
		{
			System.out.println("You can't favorite a place that doesn't exist!");
			return;
		}

		// Add into database
		String sql = "insert into favorites values ('" + login + "', " + pid + ")";
		System.out.println("Executing: " + sql);

		try
		{
			stmt.executeUpdate(sql);
			System.out.println("Place of interest successfully favorited");
		}
		catch (SQLException e)
		{
			System.out.println("Failed to favorite place of interest");
		}
	}
}
