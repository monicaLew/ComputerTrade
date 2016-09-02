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
	<h3>CARs in Base</h3>	
	<br>
	<br>
	<c:forEach var="item" items="${carList}">
	${item.id} &nbsp;${item.pageCode}&nbsp; ${item.article}&nbsp; ${item.priceByn}&nbsp;руб ${item.priceEuro}€&nbsp; ${item.year}г.в.&nbsp; ${item.status}<br>
	</c:forEach>
	<br>
	<br>
	<br>
	<form action="index.jsp" method="post">
		<input type="submit" value="Back to Menu" />
	</form>
</body>
</html>