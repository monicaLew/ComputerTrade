<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ERROR Page</title>
</head>
<body>
	<h2>Oops! Something is Wrong</h2>
	<br>
	<h3>Check File matching</h3>
	<br>
	<p>or check the exception message: ${exception}</p>
	<br>
	<br>
	<form action="index.jsp" method="post">
		<input type="submit" value="Back to Menu" />
	</form>
</body>
</html>