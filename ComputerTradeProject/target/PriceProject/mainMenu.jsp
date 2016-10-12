<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.pctrade.price.servlet.ScanCarPage"%>
<%@page import="com.pctrade.price.validation.FormValidator"%>
<%@page import="java.util.Map"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="validation" uri="http://belhard.com/validation"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 -->
<title>File Uploading Form</title>
<link rel="stylesheet" href="mainMenu.css" type="text/css">
<style type="text/css">
.top {
	width: 200px;
	display: inline-block;
}
</style>
</head>
<body>
	<form class="top" action="inputCars.jsp" method="get">
		<input type="submit" value="				" />
	</form>
	<form class="top" action="ShowCars" method="post">
		<input type="submit" value="Process Statistic" />
	</form>
	<form class="top" action="inputCars.jsp" method="get">
		<input type="submit" value="				" />
	</form>
	<br>
	<br>
	<br>
	<br>
	<h3>File Upload:</h3>
	Select a file to upload:
	<br>
	<br>
	<form action="UploadServlet" method="post"
		enctype="multipart/form-data">
		<input type="file" name="file" size="50" /> <br> <br> <input
			type="submit" value="Upload File" />
	</form>
	<br>
	<br>
	<br>
	<form action="ScanCarPage" method="post">
		<p>
			<b>ID from:</b><br> <input type="text" name="idFrom" size="40" />

			<validation:fieldError errorCode="idFrom.negative.error">
				&emsp;
				<span style="color: red;">${fieldErrorText}</span>
			</validation:fieldError>

			<validation:fieldError errorCode="idFrom.less.error">
				&emsp;
				<span style="color: red;">${fieldErrorText}</span>
			</validation:fieldError>

			<validation:fieldError errorCode="idFrom.text.error">
				&emsp;
				<span style="color: red;">${fieldErrorText}</span>
			</validation:fieldError>
		</p>
		<br>
		<p>
			<b>ID till:</b><br> <input type="text" name="idTill" size="40" />
			<validation:fieldError errorCode="idTill.negative.error">
				&emsp;
				<span style="color: red;">${fieldErrorText}</span>
			</validation:fieldError>

			<validation:fieldError errorCode="idTill.bigger.error">
				&emsp;
				<span style="color: red;">${fieldErrorText}</span>
			</validation:fieldError>

			<validation:fieldError errorCode="idTill.text.error">
				&emsp;
				<span style="color: red;">${fieldErrorText}</span>
			</validation:fieldError>
		</p>
		<br>
		<p>
			<b>Pool Capacity:</b><br> <input type="text" name="poolCapacity"
				size="40" />
			<validation:fieldError errorCode="idTill.negative.error">
				&emsp;
				<span style="color: red;">${fieldErrorText}</span>
			</validation:fieldError>

			<validation:fieldError errorCode="idTill.bigger.error">
				&emsp;
				<span style="color: red;">${fieldErrorText}</span>
			</validation:fieldError>

			<validation:fieldError errorCode="idTill.text.error">
				&emsp;
				<span style="color: red;">${fieldErrorText}</span>
			</validation:fieldError>
		</p>
		<br> <input type="submit" value="ScanningPages" />
	</form>
	<br>
</body>
</html>