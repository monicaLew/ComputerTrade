<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Result Page</title>
</head>
<body>
	<h2>If You Want to Download new Price</h2>
	<br>
	<br>
	<form action="ReadOutputFromDbExcel" method="post"
		enctype="multipart/form-data">
		<input type="submit" value="Download new Price Excel Format" />
	</form>
	<br>
	<br>
	<form action="ReadOutputFromDbCsv" method="post">
		<input type="submit" value="Download new Price CSV Format" />
	</form>
	<br>
	<a href="ShowProducts">SHOW Products</a>
	<br>
	<br>
	<a href="mainMenu.jsp">Back To Main Menu</a>
</body>
</html>