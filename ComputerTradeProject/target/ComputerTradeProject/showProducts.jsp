<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DB has Updated</title>
</head>
<body>
	<p>Table in DB has Updated. Now in DB:</p>
	<br>
	<c:forEach var="item" items="${productsList}">
	${begin=begin+1}&ensp; ${item.articleCode}&ensp; ${item.article}&ensp; ${item.price}&ensp; ${item.date}&ensp; ${item.status}<br>
	</c:forEach>	
	<br>
	<br>
	<a href="mainMenu.jsp">Back To Main Menu</a>
</body>
</html>