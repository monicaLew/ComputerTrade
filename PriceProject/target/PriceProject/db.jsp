<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Data from DB</title>
</head>
<body>
<h2> SOME results of DB connect </h2>
<br>
<br>
	<!-- ${array_history} -->
	
<!-- <%= session.getAttribute("array_history0").toString() %> -->
<br>
<br>
<!-- <%= session.getAttribute("array_history1").toString() %> -->
<br>
<br>
<!-- <%= session.getAttribute("array_history2").toString() %> -->
<br>
<br>
<%  HttpSession session1 = request.getSession();	
ArrayList<ArrayList<String>> list = (ArrayList<ArrayList<String>>)session1.getAttribute("array_history");
	for(ArrayList<String> li : list){
		
		for(String s : li){
			
			%><%= s %> &nbsp; <%
		}
		%><br><%
	}
%>
</body>
</html>