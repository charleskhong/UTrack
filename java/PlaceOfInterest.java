package cs5530;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaceOfInterest implements Comparable<PlaceOfInterest>
{
	int pid;
	int visits;
	double acost;
	String name, url, year, hours, category, a1, a2, city, state, zip, phone;
	double price;
	double avg;

	public PlaceOfInterest()
	{

	}

	/**
	 * For sorting in descending order
	 */
	@Override
	public int compareTo(PlaceOfInterest o)
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
	 * Get all places with their average rating
	 * 
	 * @param stmt
	 * @return
	 */
	public static List<PlaceOfInterest> getPlacesWithRating(Statement stmt)
	{
		String sql = "select p.pid, name, url, year, hours, price, addr1, addr2, city, state, zip, phone, category, avg(score) as a "
				+ "from places p left join opinions o " + "on p.pid = o.pid " + "group by p.pid " + "order by a desc";

		ResultSet rs = null;
		List<PlaceOfInterest> places = new ArrayList<PlaceOfInterest>();
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				PlaceOfInterest poi = new PlaceOfInterest();
				poi.pid = rs.getInt(1);
				poi.name = rs.getString(2);
				poi.url = rs.getString(3);
				poi.year = rs.getString(4);
				poi.hours = rs.getString(5);
				poi.price = rs.getDouble(6);
				poi.a1 = rs.getString(7);
				poi.a2 = rs.getString(8);
				poi.city = rs.getString(9);
				poi.state = rs.getString(10);
				poi.zip = rs.getString(11);
				poi.phone = rs.getString(12);
				poi.category = rs.getString(13);
				poi.avg = rs.getDouble(14);

				places.add(poi);
			}
			rs.close();
			return places;

		}
		catch (SQLException e)
		{
			System.out.println("Failed to get average rating for places");
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
	 * Get all places with their average cost per head
	 * 
	 * @param stmt
	 * @return
	 */
	public static List<PlaceOfInterest> getPlacesWithAvgCost(Statement stmt)
	{
		String sql = "select p.pid, name, url, year, hours, price, addr1, addr2, city, state, zip, phone, category, avg(cost/partysize) as a "
				+ "from places p left join visits v " + "on p.pid = v.pid " + "left join visitevents v1 " + "on v.vid = v1.vid " + "group by p.pid "
				+ "order by a desc";
		ResultSet rs = null;
		List<PlaceOfInterest> places = new ArrayList<PlaceOfInterest>();
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				PlaceOfInterest poi = new PlaceOfInterest();
				poi.pid = rs.getInt(1);
				poi.name = rs.getString(2);
				poi.url = rs.getString(3);
				poi.year = rs.getString(4);
				poi.hours = rs.getString(5);
				poi.price = rs.getDouble(6);
				poi.a1 = rs.getString(7);
				poi.a2 = rs.getString(8);
				poi.city = rs.getString(9);
				poi.state = rs.getString(10);
				poi.zip = rs.getString(11);
				poi.phone = rs.getString(12);
				poi.category = rs.getString(13);
				poi.acost = rs.getDouble(14);

				places.add(poi);
			}
			rs.close();
			return places;

		}
		catch (SQLException e)
		{
			System.out.println("Failed to get average cost per head for places");
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
	 * Get all places with their visit count
	 * 
	 * @param stmt
	 * @return
	 */
	public static List<PlaceOfInterest> getPlacesWithVisits(Statement stmt)
	{
		String sql = "select p.pid, name, url, year, hours, price, addr1, addr2, city, state, zip, phone, category, count(*) as c "
				+ "from places p left join visits v " + "on p.pid = v.pid " + "group by p.pid " + "order by c desc";

		ResultSet rs = null;
		List<PlaceOfInterest> places = new ArrayList<PlaceOfInterest>();
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				PlaceOfInterest poi = new PlaceOfInterest();
				poi.pid = rs.getInt(1);
				poi.name = rs.getString(2);
				poi.url = rs.getString(3);
				poi.year = rs.getString(4);
				poi.hours = rs.getString(5);
				poi.price = rs.getDouble(6);
				poi.a1 = rs.getString(7);
				poi.a2 = rs.getString(8);
				poi.city = rs.getString(9);
				poi.state = rs.getString(10);
				poi.zip = rs.getString(11);
				poi.phone = rs.getString(12);
				poi.category = rs.getString(13);
				poi.visits = rs.getInt(14);

				places.add(poi);
			}
			rs.close();
			return places;

		}
		catch (SQLException e)
		{
			System.out.println("Failed to get num visits for places");
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

	public static List<String> getHighestRatedHTML(int m, Statement stmt)
	{
		List<PlaceOfInterest> places = getPlacesWithRating(stmt);
		List<String> categories = getCategories(stmt);
		List<String> outputs = new ArrayList<String>();
		for (String cat : categories)
		{
			int count = m;
			String out = m + " highest rated POIs for " + cat + "<br>";
			out += "<table style\"width:100%\">" + "<tr>" + "<td>name</td>" + "<td>url</td>" + "<td>year</td>" + "<td>hours</td>" + "<td>phone</td>"
					+ "<td>a1</td>" + "<td>a2</td>" + "<td>city</td>" + "<td>state</td>" + "<td>zip</td>" + "<td>phone</td>" + "<td>category</td>"
					+ "<td>average rating</td>" + "</tr>";
			for (PlaceOfInterest p : places)
			{
				if (count == 0)
					break;
				if (p.category.equals(cat))
				{
					out += "<tr>" + "<td>" + p.name + "</td>" + "<td>" + p.url + "</td>" + "<td>" + p.year + "</td>" + "<td>" + p.hours + "</td>"
							+ "<td>" + p.phone + "</td>" + "<td>" + p.a1 + "</td>" + "<td>" + p.a2 + "</td>" + "<td>" + p.city + "</td>" + "<td>"
							+ p.state + "</td>" + "<td>" + p.zip + "</td>" + "<td>" + p.phone + "</td>" + "<td>" + p.category + "</td>" + "<td>"
							+ p.avg + "</td>" + "</tr>";
					count--;
				}
			}
			out += "</table>";
			outputs.add(out);
		}
		return outputs;
	}

	/**
	 * Get m highest rated places
	 * 
	 * @param m
	 * @param stmt
	 * @return
	 */
	public static List<String> getHighestRated(int m, Statement stmt)
	{
		List<PlaceOfInterest> places = getPlacesWithRating(stmt);
		List<String> categories = getCategories(stmt);
		List<String> outputs = new ArrayList<String>();
		for (String cat : categories)
		{
			int count = m;
			String out = m + " highest rated POIs for " + cat;
			for (PlaceOfInterest p : places)
			{
				if (count == 0)
					break;
				if (p.category.equals(cat))
				{
					out += "\n" + p.name + "\t" + p.url + "\t" + p.year + "\t" + p.hours + "\t" + p.phone + "\t" + p.a1 + "\t" + p.a2 + "\t" + p.city
							+ "\t" + p.state + "\t" + p.zip + "\t" + p.phone + "\t" + p.category + "\t" + p.avg;
					count--;
				}
			}
			outputs.add(out);
		}
		return outputs;
	}
	
	public static List<String> getMostExpensiveHTML(int m, Statement stmt)
	{
		List<PlaceOfInterest> places = getPlacesWithAvgCost(stmt);
		List<String> categories = getCategories(stmt);
		List<String> outputs = new ArrayList<String>();
		for (String cat : categories)
		{
			int count = m;
			String out = m + " most expensive POIs for " + cat + "<br>";
			out += "<table style\"width:100%\">" + "<tr>" + "<td>name</td>" + "<td>url</td>" + "<td>year</td>" + "<td>hours</td>" + "<td>phone</td>"
					+ "<td>a1</td>" + "<td>a2</td>" + "<td>city</td>" + "<td>state</td>" + "<td>zip</td>" + "<td>phone</td>" + "<td>category</td>"
					+ "<td>average cost</td>" + "</tr>";
			for (PlaceOfInterest p : places)
			{
				if (count == 0)
					break;
				if (p.category.equals(cat))
				{
					out += "<tr>" + "<td>" + p.name + "</td>" + "<td>" + p.url + "</td>" + "<td>" + p.year + "</td>" + "<td>" + p.hours + "</td>"
							+ "<td>" + p.phone + "</td>" + "<td>" + p.a1 + "</td>" + "<td>" + p.a2 + "</td>" + "<td>" + p.city + "</td>" + "<td>"
							+ p.state + "</td>" + "<td>" + p.zip + "</td>" + "<td>" + p.phone + "</td>" + "<td>" + p.category + "</td>" + "<td>"
							+ p.acost + "</td>" + "</tr>";
					count--;
				}
			}
			out += "</table>";
			outputs.add(out);
		}
		return outputs;
	}

	/**
	 * Get m most expensive places
	 * 
	 * @param m
	 * @param stmt
	 * @return
	 */
	public static List<String> getMostExpensive(int m, Statement stmt)
	{
		List<PlaceOfInterest> places = getPlacesWithAvgCost(stmt);
		List<String> categories = getCategories(stmt);
		List<String> outputs = new ArrayList<String>();
		for (String cat : categories)
		{
			int count = m;
			String out = m + " most expensive POIs for " + cat;
			for (PlaceOfInterest p : places)
			{
				if (count == 0)
					break;
				if (p.category.equals(cat))
				{
					out += "\n" + p.name + "\t" + p.url + "\t" + p.year + "\t" + p.hours + "\t" + p.phone + "\t" + p.a1 + "\t" + p.a2 + "\t" + p.city
							+ "\t" + p.state + "\t" + p.zip + "\t" + p.phone + "\t" + p.category + "\t" + p.acost;
					count--;
				}
			}
			outputs.add(out);
		}
		return outputs;
	}
	
	public static List<String> getMostPopularHTML(int m, Statement stmt)
	{
		List<PlaceOfInterest> places = getPlacesWithVisits(stmt);
		List<String> categories = getCategories(stmt);
		List<String> outputs = new ArrayList<String>();
		for (String cat : categories)
		{
			int count = m;
			String out = m + " most popular POIs for " + cat + "<br>";
			out += "<table style\"width:100%\">" + "<tr>" + "<td>name</td>" + "<td>url</td>" + "<td>year</td>" + "<td>hours</td>" + "<td>phone</td>"
					+ "<td>a1</td>" + "<td>a2</td>" + "<td>city</td>" + "<td>state</td>" + "<td>zip</td>" + "<td>phone</td>" + "<td>category</td>"
					+ "<td>visits</td>" + "</tr>";
			for (PlaceOfInterest p : places)
			{
				if (count == 0)
					break;
				if (p.category.equals(cat))
				{
					out += "<tr>" + "<td>" + p.name + "</td>" + "<td>" + p.url + "</td>" + "<td>" + p.year + "</td>" + "<td>" + p.hours + "</td>"
							+ "<td>" + p.phone + "</td>" + "<td>" + p.a1 + "</td>" + "<td>" + p.a2 + "</td>" + "<td>" + p.city + "</td>" + "<td>"
							+ p.state + "</td>" + "<td>" + p.zip + "</td>" + "<td>" + p.phone + "</td>" + "<td>" + p.category + "</td>" + "<td>"
							+ p.visits + "</td>" + "</tr>";
					count--;
				}
			}
			out += "</table>";
			outputs.add(out);
		}
		return outputs;
	}

	/**
	 * Get m most popular places
	 * 
	 * @param m
	 * @param stmt
	 * @return
	 */
	public static List<String> getMostPopular(int m, Statement stmt)
	{
		List<PlaceOfInterest> places = getPlacesWithVisits(stmt);
		List<String> categories = getCategories(stmt);
		List<String> outputs = new ArrayList<String>();
		for (String cat : categories)
		{
			int count = m;
			String out = m + " most popular POIs for " + cat;
			for (PlaceOfInterest p : places)
			{
				if (count == 0)
					break;
				if (p.category.equals(cat))
				{
					out += "\n" + p.name + "\t" + p.url + "\t" + p.year + "\t" + p.hours + "\t" + p.phone + "\t" + p.a1 + "\t" + p.a2 + "\t" + p.city
							+ "\t" + p.state + "\t" + p.zip + "\t" + p.phone + "\t" + p.category + "\t" + p.visits;
					count--;
				}
			}
			outputs.add(out);
		}
		return outputs;
	}

	/**
	 * Get all categories
	 * 
	 * @param stmt
	 * @return
	 */
	public static List<String> getCategories(Statement stmt)
	{
		String sql = "select distinct category from places";
		List<String> cats = new ArrayList<String>();
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
				cats.add(rs.getString(1));
			rs.close();
			return cats;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get categories");
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
	 * Add place to database
	 * 
	 * @param name
	 * @param url
	 * @param year
	 * @param hours
	 * @param price
	 * @param category
	 * @param kwords
	 * @param a1
	 * @param a2
	 * @param city
	 * @param state
	 * @param zip
	 * @param phone
	 * @param stmt
	 */
	public static void addPOI(String name, String url, String year, String hours, double price, String category, List<String> kwords, String a1,
			String a2, String city, String state, String zip, String phone, Statement stmt)
	{
		// add keywords to database
		List<Integer> kids = new ArrayList<Integer>();
		for (String kw : kwords)
		{
			int kid = Keyword.addKeyword(kw, stmt);
			kids.add(kid);
		}
		String sql = "insert into places (name, url, year, hours, price, addr1, addr2, city, state, zip, phone, category)" + " values ('" + name
				+ "', '" + url + "', '" + year + "', '" + hours + "', " + price + ", '" + a1;
		if (a2.equals(""))
			sql += "', NULL, '";
		else
			sql += "', " + a2 + "', '";
		sql += city + "', '" + state + "', '" + zip + "', '" + phone + "', '" + category + "')";

		System.out.println("Executing: " + sql);
		ResultSet rs = null;
		int pid = 0;
		try
		{
			// Get place id
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			System.out.println("Place of interest successfully inserted");
			rs = stmt.getGeneratedKeys();
			if (rs.next())
				pid = rs.getInt(1);

			// Insert keyword/place relationship
			for (int kid : kids)
				insertPOIKeyword(pid, kid, stmt);
		}
		catch (SQLException e)
		{
			System.out.println("Insert failed to execute");
		}
	}

	public static String browsePOIHTML(String login, double hi, double lo, String city, String state, String kw, String cat, int sb, Statement stmt,
			Connection con)
	{
		// Construct query based on what user provides
		String sql = "select * from places where ";
		// Price
		if (hi != 0)
			sql += "price >= " + lo + " and price <= " + hi + " and ";
		// Address by City
		if (!city.equals(""))
			sql += "city like '%" + city + "%' and ";
		// Address by State
		if (!state.equals(""))
			sql += "state like '%" + state + "%' and ";
		// By name
		if (!kw.equals(""))
			sql += "name like '%" + kw + "%' and ";
		// By Category
		if (!cat.equals(""))
			sql += "category like '%" + cat + "%' and ";

		if (sql.endsWith(" and "))
			sql = sql.substring(0, sql.length() - 5);

		// Sort by price
		if (sb == 1)
			sql += " order by price";

		System.out.println("Executing: " + sql);
		String output = "<table style\"width:100%\">" + "<tr>" + "<td>pid</td>" + "<td>name</td>" + "<td>url</td>" + "<td>year</td>"
				+ "<td>hours</td>" + "<td>price</td>" + "<td>address1</td>" + "<td>address2</td>" + "<td>city</td>" + "<td>state</td>"
				+ "<td>zip</td>" + "<td>phone number</td>" + "<td>category</td>";
		if (sb == 2)
			output += "<td>average score</td>";
		else if (sb == 3)
			output += "<td>average trusted score</td>";
		output += "</tr>";

		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			ArrayList<PlaceOfInterest> places = new ArrayList<PlaceOfInterest>();
			while (rs.next())
			{
				// Get all places
				PlaceOfInterest poi = new PlaceOfInterest();
				poi.pid = rs.getInt(1);
				poi.name = rs.getString(2);
				poi.url = rs.getString(3);
				poi.year = rs.getString(4);
				poi.hours = rs.getString(5);
				poi.price = rs.getDouble(6);
				poi.a1 = rs.getString(7);
				poi.a2 = rs.getString(8);
				poi.city = rs.getString(9);
				poi.state = rs.getString(10);
				poi.zip = rs.getString(11);
				poi.phone = rs.getString(12);
				poi.category = rs.getString(13);

				// Get average scores
				if (sb == 2)
				{
					Statement temp = con.createStatement();
					float avg = Opinion.getAverageScore(poi.pid, temp);
					poi.avg = avg;
					temp.close();
				}
				// Get average trusted scores
				else if (sb == 3)
				{
					Statement temp = con.createStatement();
					float avg = Opinion.getTrustedScore(login, poi.pid, temp);
					poi.avg = avg;
					temp.close();
				}
				places.add(poi);
			}
			// Sort by scores
			if (sb == 2 || sb == 3)
			{
				Collections.sort(places);
				for (PlaceOfInterest p : places)
					output += "<tr>" + "<td>" + p.pid + "</td>" + "<td>" + p.name + "</td>" + "<td>" + p.url + "</td>" + "<td>" + p.year + "</td>"
							+ "<td>" + p.hours + "</td>" + "<td>" + p.price + "</td>" + "<td>" + p.a1 + "</td>" + "<td>" + p.a2 + "</td>" + "<td>"
							+ p.city + "</td>" + "<td>" + p.state + "</td>" + "<td>" + p.zip + "</td>" + "<td>" + p.phone + "</td>" + "<td>"
							+ p.category + "</td>" + "<td>" + p.avg + "</td>" + "</tr>";

			}
			// Sort by price
			else if (sb == 1)
			{
				for (PlaceOfInterest p : places)
					output += "<tr>" + "<td>" + p.pid + "</td>" + "<td>" + p.name + "</td>" + "<td>" + p.url + "</td>" + "<td>" + p.year + "</td>"
							+ "<td>" + p.hours + "</td>" + "<td>" + p.price + "</td>" + "<td>" + p.a1 + "</td>" + "<td>" + p.a2 + "</td>" + "<td>"
							+ p.city + "</td>" + "<td>" + p.state + "</td>" + "<td>" + p.zip + "</td>" + "<td>" + p.phone + "</td>" + "<td>"
							+ p.category + "</td>" + "</tr>";
			}
			rs.close();
			output += "</table>";
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
	 * Browse for a POI then sort
	 * 
	 * @param login
	 * @param hi
	 * @param lo
	 * @param city
	 * @param state
	 * @param kw
	 * @param cat
	 * @param sb
	 * @param stmt
	 * @param con
	 * @return
	 */
	public static String browsePOI(String login, double hi, double lo, String city, String state, String kw, String cat, int sb, Statement stmt,
			Connection con)
	{
		// Construct query based on what user provides
		String sql = "select * from places where ";
		// Price
		if (hi != 0)
			sql += "price >= " + lo + " and price <= " + hi + " and ";
		// Address by City
		if (!city.equals(""))
			sql += "city like '%" + city + "%' and ";
		// Address by State
		if (!state.equals(""))
			sql += "state like '%" + state + "%' and ";
		// By name
		if (!kw.equals(""))
			sql += "name like '%" + kw + "%' and ";
		// By Category
		if (!cat.equals(""))
			sql += "category like '%" + cat + "%' and ";

		if (sql.endsWith(" and "))
			sql = sql.substring(0, sql.length() - 5);

		// Sort by price
		if (sb == 1)
			sql += " order by price";

		System.out.println("Executing: " + sql);
		String output = "";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			ArrayList<PlaceOfInterest> places = new ArrayList<PlaceOfInterest>();
			while (rs.next())
			{
				// Get all places
				PlaceOfInterest poi = new PlaceOfInterest();
				poi.pid = rs.getInt(1);
				poi.name = rs.getString(2);
				poi.url = rs.getString(3);
				poi.year = rs.getString(4);
				poi.hours = rs.getString(5);
				poi.price = rs.getDouble(6);
				poi.a1 = rs.getString(7);
				poi.a2 = rs.getString(8);
				poi.city = rs.getString(9);
				poi.state = rs.getString(10);
				poi.zip = rs.getString(11);
				poi.phone = rs.getString(12);
				poi.category = rs.getString(13);

				// Get average scores
				if (sb == 2)
				{
					Statement temp = con.createStatement();
					float avg = Opinion.getAverageScore(poi.pid, temp);
					poi.avg = avg;
					temp.close();
				}
				// Get average trusted scores
				else if (sb == 3)
				{
					Statement temp = con.createStatement();
					float avg = Opinion.getTrustedScore(login, poi.pid, temp);
					poi.avg = avg;
					temp.close();
				}
				places.add(poi);
			}
			// Sort by scores
			if (sb == 2 || sb == 3)
			{
				Collections.sort(places);
				for (PlaceOfInterest p : places)
				{
					output += p.name + "\t" + p.url + "\t" + p.year + "\t" + p.hours + "\t" + p.price + "\t" + p.a1 + "\t" + p.a2 + "\t" + p.city
							+ "\t" + p.state + "\t" + p.zip + "\t" + p.phone + "\t" + p.category + "\t" + p.avg + "\n";
				}
			}
			// Sort by price
			else if (sb == 1)
			{
				for (PlaceOfInterest p : places)
				{
					output += p.name + "\t" + p.url + "\t" + p.year + "\t" + p.hours + "\t" + p.price + "\t" + p.a1 + "\t" + p.a2 + "\t" + p.city
							+ "\t" + p.state + "\t" + p.zip + "\t" + p.phone + "\t" + p.category + "\n";
				}
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
	 * Insert poi/keyword database relationship
	 * 
	 * @param pid
	 * @param kid
	 * @param stmt
	 */
	public static void insertPOIKeyword(int pid, int kid, Statement stmt)
	{
		String sql = "insert into poikeywords values (" + pid + ", " + kid + ")";
		System.out.println("Executing: " + sql);
		try
		{
			stmt.executeUpdate(sql);
			System.out.println("Place of interest keyword successfully inserted");
		}
		catch (SQLException e)
		{
			System.out.println("Insert failed to execute");
		}
	}

	/**
	 * Remove keyword relationship
	 * 
	 * @param pid
	 * @param kid
	 * @param stmt
	 */
	public static void removeKeyword(int pid, int kid, Statement stmt)
	{
		String sql = "delete from poikeywords where pid = " + pid + " and kid = " + kid;
		System.out.println("Executing: " + sql);
		try
		{
			stmt.executeUpdate(sql);
			System.out.println("Place of interest keyword successfully removed");
		}
		catch (SQLException e)
		{
			System.out.println("Remove failed to execute");
		}
	}

	/**
	 * Check to see if poi exists by pid
	 * 
	 * @param pid
	 * @param stmt
	 * @return
	 */
	public static boolean poiExists(int pid, Statement stmt)
	{
		String sql = "select name from places where pid = " + pid;
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
			System.out.println("Failed to get poi for pid: " + pid);
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
	 * Update a POI
	 * 
	 * @param pid
	 * @param name
	 * @param url
	 * @param year
	 * @param hours
	 * @param price
	 * @param category
	 * @param oldkws
	 * @param newkws
	 * @param a1
	 * @param a2
	 * @param city
	 * @param state
	 * @param zip
	 * @param phone
	 * @param stmt
	 */
	public static void updatePOI(int pid, String name, String url, String year, String hours, double price, String category, List<String> oldkws,
			List<String> newkws, String a1, String a2, String city, String state, String zip, String phone, Statement stmt)
	{
		// Make sure it exists
		if (!poiExists(pid, stmt))
		{
			System.out.println("POI does not exist.");
			return;
		}

		// Remove relationship from old keywords
		for (String kw : oldkws)
		{
			int kid = Keyword.keywordExists(kw, stmt);
			if (kid == -1)
				continue;
			removeKeyword(pid, kid, stmt);
		}

		// Add new keywords
		for (String kw : newkws)
		{
			int kid = Keyword.addKeyword(kw, stmt);
			if (kid == -1)
				continue;
			insertPOIKeyword(pid, kid, stmt);
		}

		// Append attributes to update
		String sql = "update places set ";
		if (!name.equals(""))
			sql += "name='" + name + "', ";
		if (!url.equals(""))
			sql += "url='" + url + "', ";
		if (!year.equals(""))
			sql += "year='" + year + "', ";
		if (!hours.equals(""))
			sql += "hours='" + hours + "', ";
		if (price != -1)
			sql += "price=" + price + ", ";
		if (!a1.equals(""))
			sql += "a1='" + a1 + "', ";
		if (!a2.equals(""))
			sql += "a2='" + a2 + "', ";
		if (!city.equals(""))
			sql += "city='" + city + "', ";
		if (!state.equals(""))
			sql += "state='" + state + "', ";
		if (!zip.equals(""))
			sql += "zip='" + zip + "', ";
		if (!phone.equals(""))
			sql += "phone='" + phone + "', ";
		if (!category.equals(""))
			sql += "category='" + category + "', ";

		if (sql.endsWith(", "))
			sql = sql.substring(0, sql.length() - 2);
		// Nothing changed
		else if (category.equals("") && newkws.size() == 0 && oldkws.size() == 0)
		{
			System.out.println("No changes made.");
			return;
		}
		// Only keywords changed
		else if (!sql.endsWith(", "))
		{
			return;
		}
		sql += " where pid = " + pid;

		System.out.println("Executing: " + sql);
		try
		{
			stmt.executeUpdate(sql);
			System.out.println("Place of interest successfully updated");
		}
		catch (SQLException e)
		{
			System.out.println("Update failed to execute");
		}
	}

	public static String suggestedPlacesHTML(List<PlaceOfInterest> places, Statement stmt)
	{
		String output = "<table style\"width:100%\">" + "<tr>" + "<td>name</td>" + "<td>url</td>" + "<td>year</td>" + "<td>hours</td>"
				+ "<td>price</td>" + "<td>address1</td>" + "<td>address2</td>" + "<td>city</td>" + "<td>state</td>" + "<td>zip</td>"
				+ "<td>phone number</td>" + "<td>category</td>" + "<td>visits</td>" + "</tr>";
		for (PlaceOfInterest p : places)
			output += getPlaceHTML(p.pid, p.visits, stmt);
		output += "</table>";
		return output;
	}

	public static String getPlaceHTML(int pid, int visits, Statement stmt)
	{
		String sql = "select name, url, year, hours, price, addr1, addr2, city, state, zip, phone, category from places where pid = " + pid;
		ResultSet rs = null;

		String output = "<tr>";
		try
		{
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			if (rs.next())
			{
				for (int i = 1; i <= rsmd.getColumnCount(); i++)
					output += "<td>" + rs.getString(i) + "</td>";
				output += "<td>" + visits + "</td></tr>";
			}
			rs.close();
			return output;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get place for pid: " + pid);
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
	 * Get place and append number of visits
	 * 
	 * @param pid
	 * @param visits
	 * @param stmt
	 * @return
	 */
	public static String getPlace(int pid, int visits, Statement stmt)
	{
		String sql = "select name, url, hours, price, addr1, addr2, city, state, zip, phone, category from places where pid = " + pid;
		ResultSet rs = null;
		String output = "";
		try
		{
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			if (rs.next())
			{
				for (int i = 1; i <= rsmd.getColumnCount(); i++)
					output += rs.getString(i) + "\t";
				output += visits + "\n";
			}
			rs.close();
			return output;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get place for pid: " + pid);
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
	 * Get name for place
	 * 
	 * @param pid
	 * @param stmt
	 * @return
	 */
	public static String getName(int pid, Statement stmt)
	{
		String sql = "select name from places where pid = " + pid;
		ResultSet rs = null;
		String name = "";
		try
		{
			rs = stmt.executeQuery(sql);
			if (rs.next())
				name = rs.getString(1);
			rs.close();
			return name;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get name for pid: " + pid);
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
	 * Get keywords for place
	 * 
	 * @param pid
	 * @param stmt
	 * @return
	 */
	public static List<Integer> getKeywords(int pid, Statement stmt)
	{
		String sql = "select kid from poikeywords where pid = " + pid;
		ResultSet rs = null;
		List<Integer> kids = new ArrayList<Integer>();
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
				kids.add(rs.getInt(1));
			rs.close();
			return kids;
		}
		catch (SQLException e)
		{
			System.out.println("Failed to get kids for pid: " + pid);
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
	 * Get places
	 * 
	 * @param stmt
	 * @param con
	 * @return
	 */
	public static String getPlaces(Statement stmt, Connection con)
	{
		String sql = "select * from places";
		String output = "pid\tname\turl\tyear\thours\tprice\taddress1\taddress2\tcity\tstate\tzip\tphone number\tcategory\t\n";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next())
			{
				int pid = rs.getInt(1);
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

	public static String getPlacesHTML(Statement stmt, Connection con)
	{
		String sql = "select * from places";
		String output = "<table style\"width:100%\">" + "<tr>" + "<td>pid</td>" + "<td>name</td>" + "<td>url</td>" + "<td>year</td>"
				+ "<td>hours</td>" + "<td>price</td>" + "<td>address1</td>" + "<td>address2</td>" + "<td>city</td>" + "<td>state</td>"
				+ "<td>zip</td>" + "<td>phone number</td>" + "<td>category</td>" + "</tr>";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next())
			{
				output += "<tr>";
				int pid = rs.getInt(1);
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
	 * Get places with keywords
	 * 
	 * @param stmt
	 * @param con
	 * @return
	 */
	public static String getPlacesToEdit(Statement stmt, Connection con)
	{
		String sql = "select * from places";
		String output = "pid\tname\turl\tyear\thours\tprice\taddress1\taddress2\tcity\tstate\tzip\tphone number\tcategory\tkeywords\n";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next())
			{
				int pid = rs.getInt(1);
				for (int i = 1; i <= rsmd.getColumnCount(); i++)
					output += rs.getString(i) + "\t";

				Statement temp = con.createStatement();

				for (int kid : getKeywords(pid, temp))
				{
					String kw = Keyword.getKeyword(kid, temp);
					output += kw + "\t";
				}

				temp.close();
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

	public static String getPlacesToEditHTML(Statement stmt, Connection con)
	{
		String sql = "select * from places";
		String output = "<table style\"width:100%\">" + "<tr>" + "<td>pid</td>" + "<td>name</td>" + "<td>url</td>" + "<td>year</td>"
				+ "<td>hours</td>" + "<td>price</td>" + "<td>address1</td>" + "<td>address2</td>" + "<td>city</td>" + "<td>state</td>"
				+ "<td>zip</td>" + "<td>phone number</td>" + "<td>category</td>" + "<td>keywords</td>" + "</tr>";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next())
			{
				output += "<tr>";
				int pid = rs.getInt(1);
				for (int i = 1; i <= rsmd.getColumnCount(); i++)
					output += "<td>" + rs.getString(i) + "</td>";

				Statement temp = con.createStatement();
				String kw = "<td>";
				for (int kid : getKeywords(pid, temp))
					kw += Keyword.getKeyword(kid, temp) + ", ";
				temp.close();
				kw = kw.substring(0, kw.length() - 2) + "</td>";
				output += kw + "</tr>";
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
}
