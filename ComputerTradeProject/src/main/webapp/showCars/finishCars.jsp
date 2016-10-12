<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cars</title>
</head>
<body>
	<h2>CARs in Base</h2>
	<br> ${doneMessage}
	<br>
	<br> ${numberOfPages} &nbsp;
	<c:out value="Pages Scanned"></c:out>
	<br>
	<c:out value="In Base"></c:out>
	&nbsp; ${numberOfCars} &nbsp;
	<c:out value="available Cars"></c:out>
	<br>
	<c:forEach var="item" items="${carList}">
	${begin=begin+1}&ensp; ${item.pageCode}&ensp; ${item.article}&ensp; ${item.priceByn}руб.&ensp; ${item.priceEuro}€&ensp; ${item.year}г.в.&ensp; ${item.status}<br>
	</c:forEach>
	<br>
	<br>
	<br>
	<a href="mainMenu.jsp">Back To Main Page(menu)</a>
	<br>
</body>
</html>