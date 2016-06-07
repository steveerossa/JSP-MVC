/**
 * 
 */
package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**Model class works with Student class.
 * @author steveerossa
 *
 */
public class StudentDBUtil 
{
	/**The DataSource.*/
	private DataSource datasource;

	/**Constructor.
	 * @param the_datasource the DataSource object for injection*/
	public StudentDBUtil(DataSource the_datasource)
	{
		datasource = the_datasource;
	}
	/**Returns a list of students.*/
	public List<Student> getStudents()
	{
		List<Student> students = new ArrayList<Student>();
		
		// GET A CONNECTION TO THE DATABASE
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		
		
		try
		{
			connection = datasource.getConnection();
			
			// CREATE SQL STATEMENTS
			String sql = "SELECT * FROM student";
			statement = connection.createStatement();
			
			// EXECUTE QUERY
			result = statement.executeQuery(sql);
			
			// PROCESS THE RESULT-SET
			while(result.next())
			{
				String email = result.getString("email");
				String lastname = result.getString("last_name");
				String firstname = result.getString("first_name");
				int id = result.getInt("id");
				
				// CREATE NEW STUDENT AND ADD IT TO ARRAYLIST
				students.add(new Student(id, firstname, lastname, email));
 			}
			
			
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
		finally 
		{
			close(connection, statement, result);
			
		}
		
		return students ;
		
	}
	/**Close JDBC RESOURCES
	 * @throws SQLException */
	private void close(Connection connection, Statement statement, ResultSet result)
	{
		try {
			if(connection != null)
			{
				connection.close(); // RETURNS IT TO POOL FOR OTHERS TO USE
			}
			if(result != null)
			{
				result.close();
			}
			if(statement != null)
			{
				statement.close();
			}
			
		} 
		catch (SQLException e)
		{
 			e.printStackTrace();
		}
		
	}
	/**Add a new Student to the database using JDBC.
	 * @param the_student the student to be added to the database*/
	public void addStudent(final Student the_student)
	{
		// CREATE THE SQL CONNECTION
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = datasource.getConnection();
			
			// CREATE THE SQL INSERT
			String sql= "insert into student " +
						"(first_name, last_name, email) " +
						"values (?, ?, ?)";
			statement = connection.prepareStatement(sql);
			
			// SET THE PARAM VALUE FOR THE STUDENT
			statement.setString(1, the_student.getFirstname());
			statement.setString(2, the_student.getLastname());
			statement.setString(3, the_student.getEmail());
			
			// EXECUTE INSERT
			statement.execute();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(connection, statement, null);
		}
		
		
		
		
		
		
	}
	/**Get one student. Method used to prefill form when updating student.*/
	public Student getStudent(String the_studentId) 
	{
		Student student = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int studentId;
		
		try {
			// CONVERT STUDENT ID TO INT
			studentId = Integer.parseInt(the_studentId);
			
			// GET CONNECTION TO DB
			connection = datasource.getConnection(); // CONNECT USING DATASOURCE
			
			// CREATE SQL STATEMENT
			String sql = "select * from student where id=?";
			
			// CREATE PREPARED STATEMENT
			statement = connection.prepareStatement(sql);  
			
			// SET PARAMS
			statement.setInt(1, studentId);
			
			// EXECUTE STATEMENT
			result = statement.executeQuery();
			
			// RETRIEVE THE DATA FORM RESULT SET ROW
			if(result.next())
			{
				String firstname = result.getString("first_name");
				String lastname = result.getString("last_name");
				String email = result.getString("email");
				student = new Student(studentId, firstname, lastname, email);
			}
			else
			{
				throw new Exception("Could not find student id: "+ studentId);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(connection, statement, result);
		}
		
		
		return student;
		
		
		
	}
	/**Update Student.
	 * @param the_student the Student updated*/
	public void updateStudent(Student student)throws Exception
	{
		Connection connection = null;
		PreparedStatement statement = null;
		
		try
		{
			connection = datasource.getConnection();;
			// CREATE SQL STATEMENT
			String sql = "update student "
						+ "set first_name=?, last_name=?, email=? "
						+ "where id=?";
			
			
			// PREPARE STATEMENT
			statement = connection.prepareStatement(sql);
			
			// SET PARAMS
			statement.setString(1, student.getFirstname());
			statement.setString(2, student.getLastname());
			statement.setString(3, student.getEmail());
			statement.setInt(4, student.getId());
			
			// EXECUTE STATEMENT
			statement.execute();
		}
		finally
		{
			close(connection, statement, null);
		}
	}
	/**Delete Student from Database.
	 * @param studentId ID of student to be deleted */
	public void deleteStudent(String studentId) throws Exception
	{
		// 
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			// CONVERT THE STUDENT ID TO INT
			int id = Integer.parseInt(studentId);
			
			// GET CONNECTION TO DATABASE
			connection = datasource.getConnection();
			
			// CREATE SQL TO DELETE STUDENT
			String sql = "delete from student where id=?";
			
			// PREPARE STATEMENT
			statement = connection.prepareStatement(sql);
			
			// SET PARAMS
			statement.setInt(1, id);
			
			// EXECUTE SQL STATEMENT
			statement.execute();
		}
		finally
		{
			close(connection, statement, null);
		}
		
	}
	
}
