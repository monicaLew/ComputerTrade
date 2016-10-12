<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<br>
	<p>
		Name of Last Uploaded File: &nbsp; ${lastFileNameUpload} &nbsp;&nbsp;
		Size:&nbsp; ${fileSize} &nbsp; KB<br>
	<form action="SaveFileInfo" method="post">
		<input type="submit" value=" SAVE " />
	</form>
	<br>
	<br>
	<br>
	<form action="UpdateExcel" method="post">
		<input type="submit" value="Update Old Price by Excel" />
	</form>
	<br>
	<form action="UpdateCSV" method="post">
		<input type="submit" value="Update Old Price by CSV" />
	</form>
	<br>
	<br>
	<br>
	<form action="WriteExcelInEmptyDb" method="post">
		<input type="submit" value="Save uploaded Excel into DB Table" />
	</form>
	<br>
	<form action="WriteCsvInEmptyDb" method="post">
		<input type="submit" value="Save uploaded CSV into DB Table" />
	</form>
	<br>
</body>
</html>