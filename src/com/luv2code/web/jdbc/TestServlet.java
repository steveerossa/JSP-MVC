package com.luv2code.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;



/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Define datasource/connection pool
	@Resource(name = "jdbc/web_student_tracker")// MUST MATCH CONTEXT.XML FILE
	private DataSource datasource;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	// SET CONTENT TYPE
		response.setContentType("text/plain");
		
		// SET UP THE PRINTWRITER
		PrintWriter writer = response.getWriter();
		
		// GET A CONNECTION TO THE DATABASE
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		
		try
		{
			// CONNECTION TO POOL
			connection = datasource.getConnection(); // CONNECT USING DATASOURCE  
			
			// CREATE SQL STATEMENTS
			String sql = "SELECT * FROM student order by last_name";
			statement = connection.createStatement();
			
			// EXECUTE QUERY
			result = statement.executeQuery(sql);
			
			// PROCESS THE RESULT-SET
			while(result.next())
			{
				// RETRIEVE DATA FROM RESULTSET ROW
				
				// CREATE NEW STUDENT OBJECT
				// ADD IT TO LIST
				String email = result.getString("email");
				writer.println(email);
			}
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
		finally
		{
			
		}
		
		
	}

}
