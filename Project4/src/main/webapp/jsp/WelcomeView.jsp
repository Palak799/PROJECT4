<%@page import="in.co.sunrays.ctl.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
        <%@ include file="Header.jsp"%>

<center>
<form action="<%=ORSView.WELCOME_CTL%>" method="post">

<h1 align="center">
	<font size="10px" color="red">Welcome to ORS</font>
	</h1>
			</form>
			
</center>
<%@include file="Footer.jsp"%> 

</body>
</html>