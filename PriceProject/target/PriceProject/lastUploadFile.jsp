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
 <%
 HttpSession session2 = request.getSession();
 ArrayList<ArrayList<String>> history = (ArrayList<ArrayList<String>>) session2.getAttribute("fileUploadHistory");
 
 for(ArrayList<String> li : history){
	 %> <%= li %><br><% 
 }
 %>
 <br>
 <br>
<p> Name of Last Uploaded File: &nbsp; ${lastFileNameUpload}
<br>   
    <form action="FileInfo" method="post" >
     <input type="submit" value=" SAVE " />
      </form>
   <br>
  <br>
  <br>
  <form action="UpdateExcel" method="post" >
     <input type="submit" value="Update Old Price by Excel" />
      </form>
  <br>
  <form action="UpdateCSV" method="post" >
     <input type="submit" value="Update Old Price by CSV" />
      </form>
   <br>
   <br>
   <br>
    <form action="Parsing" method="post" >
     <input type="submit" value="Save uploaded Excel into DB Table" />
      </form>
     <br>      
       <form action="WriteCSV" method="post" >
      <input type="submit" value="Save uploaded CSV into DB Table" />
      </form>      
      <br>  
</body>
</html>