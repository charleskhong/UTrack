package cs5530;

import java.lang.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class testdriver2
{

	/**
	 * @param args
	 */
	public static void displayMenu()
	{
		System.out.println("");
		System.out.println("1.  Enter your own query");
		System.out.println("2.  Add visit to cart");
		System.out.println("3.  (Admin only) Add new POI");
		System.out.println("4.  (Admin only) Update POI");
		System.out.println("5.  Add POI as favorite");
		System.out.println("6.  Add feedback to POI");
		System.out.println("7.  Rate feedback");
		System.out.println("8.  Declare another user as trusted/untrusted");
		System.out.println("9.  Browse for a POI");
		System.out.println("10. Most useful feedbacks for a POI");
		System.out.println("11. Visiting suggestions");
		System.out.println("12. Degrees of Separation");
		System.out.println("13. Get POI statistics");
		System.out.println("14. User awards");
		System.out.println("15. exit");
		System.out.println("please enter your choice:");
	}

	/**
	 * Prompts user for specific input
	 * @param msg - type of input
	 * @param required - optional or required
	 * @param in
	 * @return - input
	 * @throws Exception
	 */
	public static String getInput(String msg, boolean required, BufferedReader in) throws Exception
	{
		String output = "";
		// Determine which essage to prompt user with
		switch (msg)
		{
		case "login":
			output = "login";
			break;
		case "pw":
			output = "password";
			break;
		case "name":
			output = "name";
			break;
		case "addr1":
			output = "address 1";
			break;
		case "addr2":
			output = "address 2";
			break;
		case "city":
			output = "city";
			break;
		case "state":
			output = "state (UT)";
			break;
		case "zip":
			output = "zip (11111)";
			break;
		case "phone":
			output = "phone number (1234567890)";
			break;
		case "date":
			output = "date (mm/dd/yyyy)";
			break;
		case "url":
			output = "url";
			break;
		case "year":
			output = "year (2016)";
			break;
		case "hours":
			output = "hours";
			break;
		case "opt":
			output = "description";
			break;
		case "cat":
			output = "category";
			break;
		case "kword":
			output = "keyword";
			break;
		case "oldkw":
			output = "old keyword";
			break;
		case "newkw":
			output = "new keyword";
			break;
		case "user":
			output = "their login";
			break;
		case "trusts":
			output = "trusted (y/n)";
			break;
		case "visit":
			output = "add more (y/n)";
			break;
		case "done":
			output = "submit visits (y/n)";
			break;
		case "u1":
			output = "user 1";
			break;
		case "u2":
			output = "user 2";
			break;
		default:
			throw new Exception();
		}
		
		// Tell user if required or optional
		if (required)
			output += " (required): ";
		else
			output += " (optional): ";
		System.out.print(output);
		
		// Prompt user, if required continue asking until we get input
		// If optional, return "" if user does not provide anything
		String temp = "";
		while ((temp = in.readLine()) == null || (temp.length() == 0 && required))
			System.out.print(output);
		return temp;
	}

	/**
	 * Prompt user for integer input
	 * @param msg - type of message
	 * @param required - optional or required
	 * @param in
	 * @return - integer
	 * @throws Exception
	 */
	public static int getIntInput(String msg, boolean required, BufferedReader in) throws Exception
	{
		String output = "";
		// Type of int to prompt for
		switch (msg)
		{
		case "pid":
			output = "pid";
			break;
		case "party":
			output = "party size";
			break;
		case "score":
			output = "score (0-10)";
			break;
		case "oid":
			output = "oid";
			break;
		case "rating":
			output = "usefulness (0 = useless, 1 = useful, 2 = very useful)";
			break;
		case "search":
			output = "search by";
			break;
		case "sort":
			output = "sort by";
			break;
		case "stats":
			output = "choice";
			break;
		case "num":
			output = "count (default 5)";
			break;
		case "useful":
			output = "number of feedbacks";
			break;
		default:
			return -1;
		}
		
		// Required or optional
		if (required)
			output += " (required): ";
		else
			output += " (optional): ";

		// Prompt the user for an integer
		String temp = "";
		int val = 0;
		while (true)
		{
			System.out.print(output);
			while ((temp = in.readLine()) == null || (temp.length() == 0 && required))
				System.out.print(output);
			
			// Check to see if it is an integer
			try
			{
				// Optional return -1
				if (temp.equals(""))
					return -1;
				
				// Parse and return
				val = Integer.parseInt(temp);
				return val;
			}
			catch (Exception e)
			{
				// Ask again for a number
				System.out.println("Please enter a number.");
			}
		}
	}

	/**
	 * Prompt for double input
	 * @param msg - type of double
	 * @param req - required or optional
	 * @param in
	 * @return - double
	 * @throws Exception
	 */
	public static double getDoubleInput(String msg, boolean req, BufferedReader in) throws Exception
	{
		String output = "";
		// Type of double to prompt for
		switch (msg)
		{
		case "price":
			output = "price (11.11)";
			break;
		case "low":
			output = "low price (default 0)";
			break;
		case "high":
			output = "high price (default 100)";
			break;
		default:
			return -1;
		}
		// Required or optional
		if (req)
			output += " (required): ";
		else
			output += " (optional): ";

		// Prompt user for a double
		String temp = "";
		double val = 0;
		while (true)
		{
			System.out.print(output);
			while ((temp = in.readLine()) == null || (temp.length() == 0 && req))
				System.out.print(output);
			// Check to see if double
			try
			{
				// If optional return -1
				if (temp.equals(""))
					return -1;
				
				// Parse double and return
				val = Double.parseDouble(temp);
				return val;
			}
			catch (Exception e)
			{
				// Keep asking if user does not enter a number
				System.out.println("Please enter a number.");
			}
		}
	}

	public static void main(String[] args)
	{
		System.out.println("Establishing connection to database");
		Connector con = null;
		String choice;
		String sql = null;
		int c = 0;
		String uname = "", pw = "";
		boolean loggedIn = false;
		try
		{
			con = new Connector();
			System.out.println("Database connection established");

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			// Prompt the user to login or register a new user
			System.out.println("        Welcome to the UTrack System     ");
			System.out.println("Would you like to login or register as a new user?");
			
			while (!loggedIn)
			{
				System.out.println("Type login to login or register to register");
				System.out.println("Type anything else to exit.");
				choice = in.readLine();
				if (choice.equals("login"))
				{
					System.out.println("Please login");

					// Get username and password
					uname = getInput("login", true, in);
					pw = getInput("pw", true, in);

					// Try logging in
					if (User.login(uname, pw, con.stmt))
						loggedIn = true;
					else
						System.out.println("Login failed please try again.");
				}
				else if (choice.equals("register"))
				{
					System.out.println("Please fill out the following form.");
					String login, name, pword, addr1, addr2, city, state, zip, phone;

					// Get necessary information for user
					login = getInput("login", true, in);
					name = getInput("name", true, in);
					pword = getInput("pw", true, in);
					addr1 = getInput("addr1", true, in);
					addr2 = getInput("addr2", false, in);
					city = getInput("city", true, in);
					state = getInput("state", true, in);
					zip = getInput("zip", true, in);
					phone = getInput("phone", true, in);

					// Add user to database
					User.addUser(login, name, pword, addr1, addr2, city, state, zip, phone, con.stmt);
					loggedIn = true;
					uname = login;
				}
				else
				{
					con.stmt.close();
					break;
				}
			}
			
			// Continuously allow user to do more than one thing
			while (true && loggedIn)
			{
				// Options menu
				displayMenu();

				// Read in a number and try to parse it
				choice = in.readLine();
				try
				{
					c = Integer.parseInt(choice);
				}
				catch (Exception e)
				{
					System.out.println("Please enter a number.");
					continue;
				}
				if (c < 1 | c > 15)
				{
					System.out.println("Please enter a number in the given range.");
					continue;
				}

				// Enter our own query
				if (c == 1)
				{
					System.out.println("please enter your query below:");
					while ((sql = in.readLine()) == null && sql.length() == 0)
						System.out.println(sql);
					ResultSet rs = con.stmt.executeQuery(sql);
					ResultSetMetaData rsmd = rs.getMetaData();
					int numCols = rsmd.getColumnCount();
					while (rs.next())
					{
						// System.out.print("cname:");
						for (int i = 1; i <= numCols; i++)
							System.out.print(rs.getString(i) + "  ");
						System.out.println("");
					}
					System.out.println(" ");
					rs.close();
				}
				// Add visits
				else if (c == 2)
				{
					String more;

					// Display places so user can choose which one they visited
					System.out.println("Which place did you visit?");
					String places = PlaceOfInterest.getPlaces(con.stmt, con.con);
					if (places.equals(""))
						continue;
					System.out.println(places);

					// Prompt user to fill in information on which place they visited
					List<Visit> visits = new ArrayList<Visit>();
					Visit v = new Visit();
					v.pid = getIntInput("pid", true, in);
					v.cost = getDoubleInput("price", true, in);
					v.size = getIntInput("party", true, in);
					v.date = getInput("date", true, in);
					v.login = uname;
					visits.add(v);
					
					// Ask the user if they wish to add more visits
					more = getInput("visit", true, in);
					while (more.equals("y"))
					{
						v = new Visit();
						v.pid = getIntInput("pid", true, in);
						v.cost = getDoubleInput("price", true, in);
						v.size = getIntInput("party", true, in);
						v.date = getInput("date", true, in);
						v.login = uname;
						visits.add(v);
						more = getInput("visit", true, in);
					}
					
					// Allow user to review visits recorded
					System.out.println("Please review your visits and confirm");
					System.out.println("pid\tcost\tsize\tdate");
					for (Visit t : visits)
						System.out.println(t.pid + "\t" + t.cost + "\t" + t.size + "\t" + t.date);

					// Ask the user if they are satisfied
					more = getInput("done", true, in);
					while (!more.equals("y") && !more.equals("n"))
					{
						System.out.println("Enter only a y or an n");
						more = getInput("done", true, in);
					}

					// If they did not want to add the visits, drop them and go back to menu
					if (more.equals("n"))
						continue;
					// Add each visit to the database
					else
						for (Visit t : visits)
							Visit.addVisit(t.login, t.pid, t.cost, t.size, t.date, con.stmt);

				}
				// Let admin add new POIs
				else if (c == 3)
				{
					// Check to see if user is the admin
					if (!uname.equals("admin"))
					{
						System.out.println("Only administrators have this option");
						continue;
					}

					String name, url, year, hours, a1, a2, city, state, zip, phone;
					String category, keyword;
					List<String> kwords = new ArrayList<String>();
					double price;

					// Prompt admin with POI form
					System.out.println("Please fill out the following form.");
					name = getInput("name", true, in);
					url = getInput("url", true, in);
					year = getInput("year", true, in);
					hours = getInput("hours", true, in);
					price = getDoubleInput("price", true, in);

					// Keywords are optional, they can input as many as they wish
					keyword = getInput("kword", false, in);
					while (!keyword.equals(""))
					{
						kwords.add(keyword);
						keyword = getInput("kword", false, in);
					}

					a1 = getInput("addr1", true, in);
					a2 = getInput("addr2", false, in);
					city = getInput("city", true, in);
					state = getInput("state", true, in);
					zip = getInput("zip", true, in);
					phone = getInput("phone", true, in);
					category = getInput("cat", true, in);

					// Add place of interest to database
					PlaceOfInterest.addPOI(name, url, year, hours, price, category, kwords, a1, a2, city, state, zip, phone, con.stmt);
				}
				// Allow Admin to update POIs
				else if (c == 4)
				{
					// Check to see if user is the admin
					if (!uname.equals("admin"))
					{
						System.out.println("Only administrators have this option");
						continue;
					}

					int pid;
					String name, url, year, hours, a1, a2, city, state, zip, phone;
					double price;
					String category, oldkw, newkw;
					List<String> rmKwords = new ArrayList<String>();
					List<String> addKwords = new ArrayList<String>();

					// Display places for admin to choose from
					System.out.println("Which POI would you like to update?");
					String places = PlaceOfInterest.getPlacesToEdit(con.stmt, con.con);
					if (places.equals(""))
						continue;
					System.out.println(places);
					
					// Prompt admin for place to edit
					pid = getIntInput("pid", true, in);

					// Prompt admin for potential changes
					System.out.println("Leave field blank if you do not wish to change it");
					name = getInput("name", false, in);
					url = getInput("url", false, in);
					year = getInput("year", false, in);
					hours = getInput("hours", false, in);
					price = getDoubleInput("price", false, in);
					a1 = getInput("addr1", false, in);
					a2 = getInput("addr2", false, in);
					city = getInput("city", false, in);
					state = getInput("state", false, in);
					zip = getInput("zip", false, in);
					phone = getInput("phone", false, in);
					category = getInput("cat", false, in);

					// Keywords can be removed or edited
					System.out.println("Which keyword(s) would you like to remove or edit?");
					oldkw = getInput("oldkw", false, in);
					while (!oldkw.equals(""))
					{
						// Add keyword to list to disassociate from POI
						rmKwords.add(oldkw);
						// Prompt admin for new keyword to associate with
						newkw = getInput("newkw", false, in);
						// If admin entered something add to list
						if (!newkw.equals(""))
							addKwords.add(newkw);
						// Prompt for more keywords to remove/edit
						oldkw = getInput("oldkw", false, in);
					}

					// Done with old keywords, ask admin if they want to add any more
					System.out.println("Would you like to add anymore keywords?");
					newkw = getInput("newkw", false, in);
					while (!newkw.equals(""))
					{
						addKwords.add(newkw);
						newkw = getInput("newkw", false, in);
					}

					// Update the POI, remove keywords they requested, and add new keywords
					PlaceOfInterest.updatePOI(pid, name, url, year, hours, price, category, rmKwords, addKwords, a1, a2, city, state, zip, phone,
							con.stmt);
				}
				// Add favorite
				else if (c == 5)
				{
					int pid;
					// Display places for user to decide from
					System.out.println("Which place would you like favorite?");
					String places = PlaceOfInterest.getPlaces(con.stmt, con.con);
					if (places.equals(""))
						continue;
					System.out.println(places);
					
					// Prompt for place to favorite
					pid = getIntInput("pid", true, in);

					// Add favorite relationship to database
					Favorite.addFavorite(uname, pid, con.stmt);
				}
				// Add feedback/rating
				else if (c == 6)
				{
					int pid, score;
					String opt, date;
					// Display place for user to decide from
					System.out.println("Which place would you like rate?");
					String places = PlaceOfInterest.getPlaces(con.stmt, con.con);
					if (places.equals(""))
						continue;
					System.out.println(places);
					
					// Prompt user for place and the score
					pid = getIntInput("pid", true, in);
					score = getIntInput("score", true, in);

					// Allow score to only be from 0-10
					if (score > 10)
						score = 10;
					else if (score < 0)
						score = 0;
					
					// Prompt for additional description (optional) and the date
					opt = getInput("opt", false, in);
					
					// Date is in format mm/dd/yyyy
					date = getInput("date", true, in);

					// Add feedback/opinion to database for the POI
					Opinion.addOpinion(score, opt, date, pid, uname, con.stmt);
				}
				// Rate other opinions
				else if (c == 7)
				{
					int oid, use;

					// Display opinions for user to rate
					System.out.println("Which opinion would you like to rate?");
					String opinions = Opinion.getOpinions(con.stmt);
					if (opinions.equals(""))
						continue;
					System.out.println(opinions);
					
					// Prompt user for opinion and the usefulness score
					oid = getIntInput("oid", true, in);
					use = getIntInput("rating", true, in);

					// Keep the score in 0-2 range
					if (use > 2)
						use = 2;
					else if (use < 0)
						use = 0;
					
					// Add score to database
					OpinionRating.addRating(oid, uname, use, con.stmt);
				}
				// Trust/Untrust other users
				else if (c == 8)
				{
					String ologin, trusts;
					
					// Display users for user to choose from
					System.out.println("Which person would you like to (un)trust?");
					String users = User.getUsers(con.stmt);
					if (users.equals(""))
						continue;
					System.out.println(users);
					
					// Get login for other user and trust/untrust
					ologin = getInput("user", true, in);
					trusts = getInput("trusts", true, in);
					while (!trusts.equals("y") && !trusts.equals("n"))
						trusts = getInput("trusts", true, in);

					// Add to database
					User.addOrChangeTrust(uname, ologin, trusts, con.stmt);
				}
				// Browse for POI
				else if (c == 9)
				{
					// Let user choose how to search for a POI
					System.out.println("Search for a POI");
					System.out.println("How would you like to search?");
					System.out.println("1. Price range");
					System.out.println("2. Address");
					System.out.println("3. By name");
					System.out.println("4. Category");
					System.out.println("Choose at least one.");
					
					// Allow user to choose more than 1
					int s;
					List<Integer> choices = new ArrayList<Integer>();

					// First choice
					s = getIntInput("search", true, in);
					while (s < 1 || s > 4)
					{
						System.out.println("Keep your choice in the range");
						s = getIntInput("search", true, in);
					}
					choices.add(s);

					// Additional choices
					s = getIntInput("search", false, in);
					while (s != -1)
					{
						if (choices.contains(s))
							break;
						if (s < 1 || s > 4)
							System.out.println("Keep your choice in the range");
						else
							choices.add(s);
						s = getIntInput("search", false, in);
					}

					double lo = 0, hi = 0;
					String ci = "", st = "";
					String n = "";
					String ct = "";

					for (int i = 0; i < choices.size(); i++)
					{
						int ch = choices.get(i);
						// By Price
						if (ch == 1)
						{
							System.out.println("Search by price range");
							lo = getDoubleInput("low", false, in);
							// Default 0 is low range
							if (lo < 0)
								lo = 0;

							hi = getDoubleInput("high", false, in);
							// Default 100 is high range
							if (hi <= lo)
								hi = 100;
						}
						// By address
						else if (ch == 2)
						{
							System.out.println("Search by either a city and/or state");
							// Get either city or state, at least one is required both is ok
							ci = getInput("city", false, in);
							st = getInput("state", false, in);
							while (ci.equals("") && st.equals(""))
							{
								System.out.println("At least one required");
								ci = getInput("city", false, in);
								st = getInput("state", false, in);
							}
						}
						// By name like %%
						else if (ch == 3)
						{
							System.out.println("Search by name");
							n = getInput("name", true, in);
						}
						// By category
						else if (ch == 4)
						{
							System.out.println("Search by category");
							ct = getInput("cat", true, in);
						}
					}

					// Let user choose how to sort the results
					System.out.println("How would you like to sort the results?");
					System.out.println("1. Price");
					System.out.println("2. Feedback score");
					System.out.println("3. Trusted feedback score");

					s = getIntInput("sort", true, in);
					while (s < 1 || s > 3)
					{
						System.out.println("Keep your choice in the range");
						s = getIntInput("sort", true, in);
					}
					
					// Browse for any POIs that fit criteria and sort by how the user desired
					String result = PlaceOfInterest.browsePOI(uname, hi, lo, ci, st, n, ct, s, con.stmt, con.con);
					System.out.println(result);
				}
				// Most useful feedbacks
				else if (c == 10)
				{
					int n, pid;
					System.out.println("Get most useful feedbacks for a POI");
					// Display places for user to choose from
					System.out.println("Which place would you like to get a usefulness score for?");
					String places = PlaceOfInterest.getPlaces(con.stmt, con.con);
					if (places.equals(""))
						continue;
					System.out.println(places);
					
					// Prompt for place and how many scores they want
					pid = getIntInput("pid", true, in);
					n = getIntInput("useful", true, in);

					// Get most useful  feedbacks for that place
					String result = Opinion.getMostUsefulOpinions(pid, n, con.stmt, con.con);
					System.out.println(result);

				}
				// Visited suggestions
				else if (c == 11)
				{
					System.out.println("Suggestions");

					// Get every place user has visited
					List<Integer> visited = Visit.getVisited(uname, con.stmt);
					for (int pid : visited)
					{
						// Find suggested places based on place he visited
						String name = PlaceOfInterest.getName(pid, con.stmt);
						System.out.println("Because you visited " + name + " we suggest");
						List<PlaceOfInterest> sugg = Visit.suggestedPOIs(pid, uname, con.stmt);

						for (PlaceOfInterest p : sugg)
						{
							String place = PlaceOfInterest.getPlace(p.pid, p.visits, con.stmt);
							System.out.print(place);
						}
					}

				}
				// Find degrees of separation
				else if (c == 12)
				{
					System.out.println("Get up to two degrees of separation for two users");
					String u1, u2;
					
					// Prompt for two users
					u1 = getInput("u1", true, in);
					u2 = getInput("u2", true, in);

					// Find the degrees of separation between them (up to 2)
					int deg = Favorite.getDegreesOfSeparation(u1, u2, con.stmt);
					if (deg == 0)
						System.out.println("Users are the same 0 degrees of separation");
					else if (deg == 1)
						System.out.println("Users have 1 degree of separation");
					else if (deg == 2)
						System.out.println("Users have 2 degrees of separation");
					else if (deg == -1)
						System.out.println("Users are separated by more than 2 degrees");
				}
				// Stats
				else if (c == 13)
				{
					System.out.println("Which statistics would you like to see?");
					System.out.println("1. Most popular POIs for each category");
					System.out.println("2. Most expensive POIs for each category");
					System.out.println("3. Highest rated POIs for each category");

					// Prompt for stat choice and how many (default 5)
					int ch = getIntInput("stats", true, in);
					int m = getIntInput("num", false, in);
					if (m == -1)
						m = 5;

					List<String> op = new ArrayList<String>();;
					// Get most popular pois by category
					if (ch == 1)
						op = PlaceOfInterest.getMostPopular(m, con.stmt);
					// Get most expensive by category
					else if (ch == 2)
						op = PlaceOfInterest.getMostExpensive(m, con.stmt);
					// Get highest rated by category
					else if (ch == 3)
						op = PlaceOfInterest.getHighestRated(m, con.stmt);
					else 
						continue;
					
					for (String o : op)
						System.out.println(o);

				}
				// User awards
				else if (c == 14)
				{
					System.out.println("Which statistics would you like to see?");
					System.out.println("1. Most trusted users");
					System.out.println("2. Most useful users");
					
					// Prompt for stat choice and how many of them (default 5)
					int ch = getIntInput("stats", true, in);
					int m = getIntInput("num", false, in);
					if (m == -1)
						m = 5;
					
					// Most trusted users
					if (ch == 1)
						System.out.println(User.getMostTrusted(m, con.stmt));
					// Most useful users
					else if (ch == 2)
						System.out.println(User.getMostUseful(m, con.stmt));
					else 
						continue;
					
				}
				else if (c == 15)
				{
					System.out.println("Remember to pay us!");
					con.stmt.close();
					break;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Either connection error or query execution error!");
		}
		finally
		{
			if (con != null)
			{
				try
				{
					con.closeConnection();
					System.out.println("Database connection terminated");
				}

				catch (Exception e)
				{
					/* ignore close errors */ }
			}
		}
	}
}
