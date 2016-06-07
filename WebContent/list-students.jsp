<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Student Tracker App</title>
		<link type = "text/css" rel ="stylesheet" href = "css/style.css">
	
	</head>
	
	
	<body>
		<div id = "wrapper">
			<div id = "header">
				<h2>Seattle Tech University</h2>
			</div>
		</div>
		
		<div id = "container">
			<div id = "content">
				<input type = "button" value = "Add Student" 
				onclick = "window.location.href='add-student-form.jsp'; return false"
				class = "add-student-button"/>
				<table>
					<tr>
						<th>First name</th>
						<th>Last name</th>
						<th>Email</th>
						<th>Action</th>
					</tr>
					<c:forEach var = "stu" items = "${STUDENT_LIST}"><!--ATTRIBUTE NAME IN SERVLET -->
					
						<!-- SET UP A LINK TO LOAD EACH STUDENT FOR UPDATING-->
						<c:url var = "temp_link" value = "StudentControllerServlet">
							<c:param name="command" value = "LOAD"></c:param>
							<c:param name="studentId" value = "${stu.id }"></c:param>
						</c:url>
						
						<!--  DELETE LINK-->
						<c:url var = "delete_link" value = "StudentControllerServlet">
							<c:param name="command" value = "DELETE"></c:param>
							<c:param name="studentId" value = "${stu.id }"></c:param>
						</c:url>
						
						<tr>
							<td> ${stu.getFirstname()}</td>
							<td> ${stu.getLastname()}</td>
							<td>${stu.getEmail()}</td>
							<td><a href = "${temp_link}">Update</a>
							 | <a href = "${delete_link }" 
							 onclick = "if(!(confirm('Are you sure you want to delete this student?'))) return false">
							  Delete</a>
							 
							 </td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</body>
</html>