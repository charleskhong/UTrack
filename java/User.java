package cs5530;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User implements Comparable<User>
{
	String login;
	int tscore;
	int uscore;

	public User()
	{

	}

	/**
	 * For sorting in descending order
	 */
	@Override
	public int compareTo(User o)
	{
		int res = 0;
		if (o.tscore > tscore)
			res = 1;
		else if (o.tscore == tscore)
			res = 0;
		else if (o.tscore < tscore)
			res = -1;
		return res;
	}
	
	public static String getMostTrustedHTML(int m, Statement stmt)
	{
		List<User> users = getUsersWithTrustScore(stmt);
		int count = m;
		String out = m + " most trusted users <br>";
		out += "<table style\"width:100%\"><tr>" + "<td>login</td>" + "<td>trusted score</td>" + "</tr>";

		for (User u : users)
		{
			if (count == 0)
				break;
			out += "<tr>" + "<td>" + u.login + "</td>" + "<td>" + u.tscore + "</td>" + "</tr>";
			count--;
		}
		out += "</table>";
		return out;
	}

	/**
	 * Get m most trusted users
	 * @param m
	 * @param stmt
	 * @return
	 */
	public static String getMostTrusted(int m, Statement stmt)
	{
		List<User> users = getUsersWithTrustScore(stmt);
		int count = m;
		String out = m + " most trusted users ";

		for (User u : users)
		{
			if (count == 0)
				break;
			out += "\n" + u.login + "\t" + u.tscore;
			count--;
		}
		return out;
	}

	/**
	 * Get every user with their trust score
	 * @param stmt
	 * @return
	 */
	public static List<User> getUsersWithTrustScore(Statement stmt)
	{
		String sql = "select u.login, sum(trusts) " + "from users u left join trusts t " + "on u.login = t.login2 " + "group by u.login "
				+ "order by 2 desc";

		List<User> users = new ArrayList<User>();
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				User u = new User();
				u.login = rs.getString(1);
				u.tscore = rs.getInt(2);
				users.add(u);
			}
			rs.close();
			// Sort in descending order
			Collections.sort(users);
			return users;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get users with trust scores");
			return new ArrayList<User>();
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
	
	public static String getMostUsefulHTML(int m, Statement stmt)
	{
		List<User> users = getUsersWithUsefulScore(stmt);
		int count = m;
		String out = m + " most useful users <br>";
		out += "<table style\"width:100%\"><tr>" + "<td>login</td>" + "<td>usefulness score</td>" + "</tr>";

		for (User u : users)
		{
			if (count == 0)
				break;
			out += "<tr>" + "<td>" + u.login + "</td>" + "<td>" + u.uscore + "</td>" + "</tr>";
			count--;
		}
		out += "</table>";
		return out;
	}
	
	/**
	 * Get m most useful users
	 * @param m
	 * @param stmt
	 * @return
	 */
	public static String getMostUseful(int m, Statement stmt)
	{
		List<User> users = getUsersWithUsefulScore(stmt);
		int count = m;
		String out = m + " most useful users ";

		for (User u : users)
		{
			if (count == 0)
				break;
			out += "\n" + u.login + "\t" + u.uscore;
			count--;
		}
		return out;
	}

	/**
	 * Get users with their usefulness scores
	 * @param stmt
	 * @return
	 */
	public static List<User> getUsersWithUsefulScore(Statement stmt)
	{
		String sql = "select u.login, avg(usefulness) " + "from users u left join opinionratings o " + "on u.login = o.login " + "group by u.login "
				+ "order by 2 desc";

		List<User> users = new ArrayList<User>();
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				User u = new User();
				u.login = rs.getString(1);
				u.uscore = rs.getInt(2);
				users.add(u);
			}
			rs.close();
			return users;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get users with usefulness scores");
			return new ArrayList<User>();
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
	 * Add user to database
	 * @param login
	 * @param name
	 * @param pword
	 * @param a1
	 * @param a2
	 * @param city
	 * @param state
	 * @param zip
	 * @param phone
	 * @param stmt
	 */
	public static void addUser(String login, String name, String pword, String a1, String a2, String city, String state, String zip, String phone,
			Statement stmt)
	{
		String sql = "insert into users values ('" + login + "', '" + name + "', '" + pword + "', '" + a1;
		if (a2.equals(""))
			sql += "', NULL, '";
		else
			sql += "', " + a2 + "', '";
		sql += city + "', '" + state + "', '" + zip + "', '" + phone + "')";

		System.out.println("Executing: " + sql);

		try
		{
			stmt.executeUpdate(sql);
			System.out.println("User successfully inserted");
		}
		catch (SQLException e)
		{
			System.out.println("Insert failed to execute");
		}
	}

	/**
	 * Add or change a trust relationship
	 * @param login1
	 * @param login2
	 * @param trusts
	 * @param stmt
	 */
	public static boolean addOrChangeTrust(String login1, String login2, String trusts, Statement stmt)
	{
		String sql = "";
		if (!userExists(login2, stmt))
		{
			System.out.println("Cannot trust or untrust a user that does not exist.");
			return false;
		}
		if (login1.equals(login2))
		{
			System.out.println("Cannot trust or untrust yourself");
			return false;
		}
		int t = -1;
		if (trusts.equals("y"))
			t = 1;

		// Exists update it
		if (trustExists(login1, login2, stmt))
			sql = "update trusts set trusts = " + t + " where login1 = '" + login1 + "' and login2 = '" + login2 + "'";
		// Doesnt exist insert
		else
			sql = "insert into trusts values ('" + login1 + "', '" + login2 + "', " + t + ")";

		System.out.println("Executing: " + sql);

		try
		{
			stmt.executeUpdate(sql);
			System.out.println("User trust successfully inserted/changed");
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Insert failed to execute");
			return false;
		}

	}

	/**
	 * Check to see if trust exists between users
	 * @param login1
	 * @param login2
	 * @param stmt
	 * @return
	 */
	public static boolean trustExists(String login1, String login2, Statement stmt)
	{
		String sql = "select trusts from trusts where login1 = '" + login1 + "' and login2 = '" + login2 + "'";
		ResultSet rs = null;
		boolean exists = false;
		try
		{
			rs = stmt.executeQuery(sql);
			if (rs.next())
				exists = true;
			rs.close();
			return exists;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get trusts for login1, login2: " + login1 + ", " + login2);
			return false;
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
	
	public static String getUsersHTML(Statement stmt)
	{
		String output = "<table style\"width:100%\">"
				+ "<tr>"
				+ "<td>login</td>"
				+ "<td>name</td>"
				+ "</tr>";
		String sql = "select login, name from users where login != 'admin'";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next())
			{
				output += "<tr>";
				for (int i = 1; i <= rsmd.getColumnCount(); i++)
					output += "<td>" + rs.getString(i) + "</td>";
				output += "</tr>";
			}
			output += "</table>";
			rs.close();
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get places");
			output = "";
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
		return output;
	}

	/**
	 * Get all users
	 * @param stmt
	 * @return
	 */
	public static String getUsers(Statement stmt)
	{
		String output = "login\tname\n";
		String sql = "select login, name from users where login != 'admin'";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next())
			{
				for (int i = 1; i <= rsmd.getColumnCount(); i++)
					output += rs.getString(i) + "\t";
				output += "\n";
			}
			rs.close();
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get places");
			output = "";
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
		return output;
	}

	/**
	 * Check to see if a user exists
	 * @param login
	 * @param stmt
	 * @return
	 */
	public static boolean userExists(String login, Statement stmt)
	{
		String sql = "select login from users where login = '" + login + "'";
		ResultSet rs = null;
		boolean exists = false;
		try
		{
			rs = stmt.executeQuery(sql);
			if (rs.next())
				exists = true;
			rs.close();
			return exists;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get user for login: " + login);
			return false;
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
	 * Attempt to login
	 * @param login
	 * @param pw
	 * @param stmt
	 * @return
	 */
	public static boolean login(String login, String pw, Statement stmt)
	{
		String sql = "select password from users where login = '" + login + "'";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			String p = "";
			if (rs.next())
				p = rs.getString(1);
			rs.close();
			return pw.equals(p);
		}
		catch (SQLException e)
		{
			return false;
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
