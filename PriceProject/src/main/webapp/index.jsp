<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>File Uploading Form</title>
</head>
<body>
	<h3>File Upload:</h3>
	Select a file to upload:
	<br />
	<br />
	<br />
	<form action="UploadServlet" method="post"
		enctype="multipart/form-data">
		<input type="file" name="file" size="50" /> <br /> <br /> <input
			type="submit" value="Upload File" />
	</form>
	<br />
	<br />
	<br />
	<form action="ScanCarPage" method="get" enctype="text/plain">
		<p>
			<b>ID from:</b><br> <input type="text" name="idFrom" size="40" />
		</p>
		<br />
		<p>
			<b>ID till:</b><br> <input type="text" name="idTill" size="40" />
		</p>
		<br /> <input type="submit" value="ScanningPage" />
	</form>
</body>
</html>