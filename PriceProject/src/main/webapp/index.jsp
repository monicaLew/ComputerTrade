<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<title>File Uploading Form</title>
</head>
<body>
<h3>File Upload:</h3>
Select a file to upload: <br /><br/>
<form action="UploadServlet" method="post"
                        enctype="multipart/form-data">
<input type="file" name="file" size="50" />
<br/>
<br/>
<input type="submit" value="Upload File" />
</form>
<br/>
<br/>
</form>
<form action="WriteCSV" method="post"
                        enctype="multipart/form-data">
                       
<input type="submit" value=" ReadWrite CSV into DB " />
<br/>
</form>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<form action="DBconnect" method="post"
                        enctype="multipart/form-data">
<input type="submit" value=" Connection with DB (just Check)" />
</form>	
</body>
</html>