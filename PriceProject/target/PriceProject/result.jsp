<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Result Page</title>
</head>
<body>
<h2>Table in DB has Updated</h2>
	<br>	
	<br>
	<form action="DounloadNewPriceAsCSV" method="post">
		<input type="submit" value="Dounload new Price as CSV" />
	</form>
	<br>
	<br>
	<form action="index.jsp" method="post">
		<input type="submit" value="Back to Menu" />
	</form>
</body>
</html>