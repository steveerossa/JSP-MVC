<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add Student</title>
	
	<link rel = "stylesheet" type = "text/css" href = "css/style.css">
	<link rel = "stylesheet" type = "text/css" href = "css/add-student-style.css">	
	</head>
	<body>
		<div id = "wrapper">
			<div id = "header">
				<h2>FooBar University</h2>
			</div>
			
			<div id = "container">
				<h3>Add Student</h3>
				<form action="StudentControllerServlet" method = "GET">
					<input type="hidden" name = "command" value = "ADD" />
					<table>
						<tbody>
							<tr>
								<td><label>First name:</label></td>
								<td><input type = "text" name = "firstname" /></td>
							</tr>
							<tr>
								<td><label>Last name:</label></td>
								<td><input type = "text" name = "lastname" /></td>
							</tr>
							<tr>
								<td><label>Email:</label></td>
								<td><input type = "text" name = "email" /></td>
							</tr>
							<tr>
								<td><label></label></td>
								<td><input type = "submit" value = "Save" class = "save" /></td>
							</tr>
						</tbody>
					</table>
					<div style = "clear:both;">
					
						<p>
							<a href = "StudentControllerServlet">Back to List</a>
						</p>
					</div>
				</form>
			</div>
		</div>
	
	</body>
</html>