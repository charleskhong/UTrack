package cs5530;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class Opinion implements Comparable<Opinion>
{

	int oid, score, pid;
	String optdesc, date, login;
	float avg;

	public Opinion()
	{

	}

	/**
	 * Compare to another opinion to get descending order
	 */
	@Override
	public int compareTo(Opinion o)
	{
		// -1 if less, 0 if same, 1 if greater
		int res = 0;
		if (o.avg > avg)
			res = 1;
		else if (o.avg == avg)
			res = 0;
		else if (o.avg < avg)
			res = -1;
		return res;
	}

	/**
	 * Add opinion to database
	 * @param score
	 * @param opt
	 * @param date
	 * @param pid
	 * @param login
	 * @param stmt
	 */
	public static void addOpinion(int score, String opt, String date, int pid, String login, Statement stmt)
	{
		// Can't rate non-existing place
		if (!PlaceOfInterest.poiExists(pid, stmt))
		{
			System.out.println("You can't rate a place that doesn't exist!");
			return;
		}

		String sql = "insert into opinions (score, optdesc, date, pid, login) " + "values (" + score + ", '" + opt + "', STR_TO_DATE('" + date
				+ "', '%m/%d/%Y'), " + pid + ", '" + login + "')";

		// Cannot rate twice
		if (opinionExists(login, pid, stmt))
		{
			System.out.println("Cannot rate the same place more than once!");
			return;
		}

		System.out.println("Executing: " + sql);
		try
		{
			stmt.executeUpdate(sql);
			System.out.println("Opinion successfully inserted");
		}
		catch (SQLException e)
		{
			System.out.println("Insert failed to execute");
		}
	}

	/**
	 * Get average trusted score for user
	 * @param login
	 * @param pid
	 * @param stmt
	 * @return
	 */
	public static float getTrustedScore(String login, int pid, Statement stmt)
	{
		String sql = "select avg(score) from opinions o, trusts t where o.login = t.login2 and t.trusts = 1 and t.login1 = '" + login
				+ "' and o.pid = " + pid;
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
			System.out.println("Failed to get average score for trusted users for pid: " + pid);
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

	/**
	 * Get average score for this place
	 * @param pid
	 * @param stmt
	 * @return
	 */
	public static float getAverageScore(int pid, Statement stmt)
	{
		String sql = "select avg(score) from opinions where pid = " + pid;
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
			System.out.println("Failed to get average score for pid: " + pid);
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

	public static String getMostUsefulOpinionsHTML(int pid, int num, Statement stmt, Connection con)
	{
		String sql = "select * from opinions where pid = " + pid;
		ResultSet rs = null;
		ArrayList<Opinion> ops = new ArrayList<Opinion>();
		String output = "<table style\"width:100%\">" + "<tr>" + "<td>oid</td>" + "<td>review score</td>" + "<td>description</td>" + "<td>date</td>"
				+ "<td>pid</td>" + "<td>login</td>" + "<td>average usefulness</td>" + "</tr>";
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				// Add each opinion to the list
				Opinion o = new Opinion();
				o.oid = rs.getInt(1);
				o.score = rs.getInt(2);
				o.optdesc = rs.getString(3);
				o.date = rs.getDate(4).toString();
				o.pid = rs.getInt(5);
				o.login = rs.getString(6);

				Statement temp = con.createStatement();
				float avg = OpinionRating.getAverageRating(o.oid, temp);
				o.avg = avg;
				ops.add(o);
			}

			// Sort the opinions descending order
			Collections.sort(ops);

			// Grab only num opinions
			for (int i = 0; i < num && i < ops.size(); i++)
			{
				Opinion o = ops.get(i);
				output += "<tr>" + 
				"<td>" + o.oid + "</td>" +
				"<td>" + o.score + "</td>" +
				"<td>" + o.optdesc + "</td>" +
				"<td>" + o.date + "</td>" +
				"<td>" + o.pid + "</td>" +
				"<td>" + o.login + "</td>" +
				"<td>" + o.avg + "</td>" +
				"</tr>";
			}
			output += "</table>";
			rs.close();
			return output;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get n most useful opinions for pid: " + pid);
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
	 * Get num most useful opinions
	 * @param pid
	 * @param num
	 * @param stmt
	 * @param con
	 * @return
	 */
	public static String getMostUsefulOpinions(int pid, int num, Statement stmt, Connection con)
	{
		String sql = "select * from opinions where pid = " + pid;
		ResultSet rs = null;
		ArrayList<Opinion> ops = new ArrayList<Opinion>();
		String output = "";
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				// Add each opinion to the list
				Opinion o = new Opinion();
				o.oid = rs.getInt(1);
				o.score = rs.getInt(2);
				o.optdesc = rs.getString(3);
				o.date = rs.getDate(4).toString();
				o.pid = rs.getInt(5);
				o.login = rs.getString(6);

				Statement temp = con.createStatement();
				float avg = OpinionRating.getAverageRating(o.oid, temp);
				o.avg = avg;
				ops.add(o);
			}

			// Sort the opinions descending order
			Collections.sort(ops);

			// Grab only num opinions
			for (int i = 0; i < num && i < ops.size(); i++)
			{
				Opinion o = ops.get(i);
				output += o.oid + "\t" + o.score + "\t" + o.optdesc + "\t" + o.date + "\t" + o.pid + "\t" + o.login + "\t" + o.avg + "\n";
			}
			rs.close();
			return output;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get n most useful opinions for pid: " + pid);
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
	 * Get opinion
	 * @param oid
	 * @param stmt
	 * @return
	 */
	public static String getOpinion(int oid, Statement stmt)
	{
		String sql = "select login from opinions where oid = " + oid;
		ResultSet rs = null;
		String login = "";
		try
		{
			rs = stmt.executeQuery(sql);
			if (rs.next())
				login = rs.getString(1);
			rs.close();
			return login;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get opinion for oid: " + oid);
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
	
	public static String getOpinionsHTML(Statement stmt)
	{
		String sql = "select * from opinions";
		String output = "<table style\"width:100%\">"
				+ "<tr>"
				+ "<td>oid</td>"
				+ "<td>score</td>"
				+ "<td>description</td>"
				+ "<td>date</td>"
				+ "<td>pid</td>"
				+ "<td>user</td>"
				+ "</tr>";
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
			System.out.println("Failed to get opinions");
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
	 * Get all opinions
	 * @param stmt
	 * @return
	 */
	public static String getOpinions(Statement stmt)
	{
		String sql = "select * from opinions";
		String output = "oid\tscore\tdescription\tdate\tpid\tuser\n";
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
			System.out.println("Failed to get opinions");
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
	 * Check to see if an opinion exists based on oid
	 * @param oid
	 * @param stmt
	 * @return
	 */
	public static boolean opinionExists(int oid, Statement stmt)
	{
		String sql = "select login from opinions where oid = " + oid;
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
			System.out.println("Failed to get opinion for oid: " + oid);
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
	 * Check to see if a user has left an opinion on a specific place
	 * @param login
	 * @param pid
	 * @param stmt
	 * @return
	 */
	public static boolean opinionExists(String login, int pid, Statement stmt)
	{
		String sql = "select oid from opinions where login = '" + login + "' and pid = " + pid;
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
			System.out.println("Failed to get opinions for " + login + " and " + pid);
			return true;
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
