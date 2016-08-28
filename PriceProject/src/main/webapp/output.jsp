<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Output has Done</title>
</head>
<body>
	<h2>Watch file priceList</h2>
	<br />
	<br />
	<form action="index.jsp" method="post">
		<input type="submit" value="Back to Menu" />
	</form>
	<br />
	<br />
	<form action="ConvertCsvToXlsx" method="post"
		enctype="multipart/form-data">
		<input type="submit"
			value=" Convert CSV to Excel(it doesn't work right)" />
	</form>
</body>
</html>