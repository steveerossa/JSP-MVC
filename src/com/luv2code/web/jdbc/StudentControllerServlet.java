package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet.
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	/**StudentDBUtil object. */
	StudentDBUtil studentDbUtil;
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	

	@Resource(name = "jdbc/web_student_tracker") // CONNECT DATASOURCE TO CONTEXT.XML FILE
	private DataSource data_source;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		
		try 
		{
			// READ THE COMMAND PARAM FROM THE JSP
			String command = request.getParameter("command"); // COMMAND FROM FORM
			
			// IF THE COMMMAND IS MISSING, THEN JUST LIST THE STUDENTS
			if(command == null)
			{
				command = "LIST";
			}
			// ROUTE THE APPROPIATE METHOD
			switch(command)
			{
				case "LIST":
				{
					listStudents(request, response);// JUST LIST THE STUDENTS IN MVC FASHION
					break;
				}
				case "ADD":
				{
					addStudent(request, response);
					break;
				}
				case "LOAD":
				{
					loadStudent(request, response);
					break;
				}
				case "UPDATE":
				{
					updateStudent(request, response);
					break;
				}
				case "DELETE":
				{
					deleteStudent(request, response);
					break;
				}
				default:
				{
					listStudents(request, response);// LIST THE STUDENTS IN MVC FASHION
					//System.out.println("Error");
					
				}
					
			}
			
		} 
		catch (Exception e) 
		{
 			e.printStackTrace();
		}
		//
		 
	}
	/**Delete Student from Database.*/
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		// READ STUDENT ID FORM DATA
		String studentId = request.getParameter("studentId");
		
		// DELETE STUDENT FROM DATABASE
		studentDbUtil.deleteStudent(studentId);
		
		
		// SEND THEM BACK  TO 'LIST_STUDENTS' PAGE
		listStudents(request, response);
		
	}
	/**Update student to make changes*/
	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// READ INFO FORM THE FORM DATA
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		
		// CREATE A NEW STUDENT OBJECT
		Student student = new Student(id, firstname, lastname, email);
		
		// PERFORM THE DATABASE UPDATE
		studentDbUtil.updateStudent(student);
		
		// BACK TO LIST PAGE
		listStudents(request, response);
		
	}
	/**Load Student info database from database for updating fields.
	 * @param request the request object
	 * @param response the response object
	 * @throws Exception*/
	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//READ STUDENT ID FROM DATA
		String studentId = request.getParameter("studentId");
		
		// GET STUDENT FROM THE DATABASE
		Student theStudent = studentDbUtil.getStudent(studentId);
		
		// PLACE STUDENT IN THE REQUEST ATTRIBUTE
		request.setAttribute("THE_STUDENT", theStudent);
		
		// SEND TO JSP PAGE update-student-form.jsp
		RequestDispatcher dispatcher  = request.getRequestDispatcher("/update-student-form.jsp");
		
		dispatcher.forward(request, response);;
		
	}
	/**Add Student to database using form.
	 * @throws Exception 
	 * @throws ServletException */
	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, Exception 
	{
		// READ STUDENT INFO FROM THE FORM DATA
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		
		// CREATE A NEW STUDENT
		Student new_student = new Student(firstname, lastname, email);
		
		// ADD THE STUDENT TO THE DATABASE
		studentDbUtil.addStudent(new_student);
		
		
		// SEND BACK TO MAIN PAGE
		listStudents(request, response);
		
	}
	/**Method list students.
	 * @throws Exception 
	 * @throws ServletException */
	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, Exception {
		// GET THE STUDENTS FROM DB UTIL
		List<Student> students = studentDbUtil.getStudents();
		
		// ADD STUDENTS TO THE REQUEST
		request.setAttribute("STUDENT_LIST", students);
		
		// SEND TO JSP (VIEW)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	public void init() throws ServletException 
	{
		// WORK THAT THE CONSTRUCTOR COULD HAVE DONE... INITIALLY RUN
 		super.init();
 		
 		// CREATE STUDENT DB UTIL AND PASS IN THE CONNECTION POOL / DATASOURCE
 		try
 		{
 			studentDbUtil = new StudentDBUtil(data_source);
 		}
 		catch(Exception exc)
 		{
 			throw new ServletException(exc);
 		}
	}

}
