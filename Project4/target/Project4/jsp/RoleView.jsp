<%@page import="in.co.sunrays.ctl.RoleCtl" %>
<%@page import="in.co.sunrays.ctl.BaseCtl" %>
<%@page import="in.co.sunrays.util.DataUtility" %>
<%@page import="in.co.sunrays.util.ServletUtility" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Role</title>
<style>
.p1 {
	font-size: 18px;
}

.p2 {
	padding: 5px;
	margin: 3px;
}
</style>

</head>
<body>
<form action="<%=ORSView.ROLE_CTL%>"method="post">
<%@include file="Header.jsp" %>
<jsp:useBean id="bean" class="in.co.sunrays.bean.RoleBean" scope="request"></jsp:useBean>
<center>
	<table>

				<%
					if (bean.getId() > 0) {
				%>

				<h1 style="font-size: 40px;">Update Role</h1>
				<%
					} else {
				%>

				<h1 style="font-size: 40px;">Add Role</h1>
				<%
					}
				%>

	<h2>
	<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
	
	</h2>
	<h2>
	<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
	</h2>
	<input type="hidden" name="id" value="<%=bean.getId()%>">
	<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
	<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
	<input type="hidden" name="createDateTime" value="<%=bean.getCreatedDateTime()%>">
	<input type="hidden" name="modifiedDateTime" value="<%=bean.getModifiedDateTime()%>">

<table>
<tr>
	<th align="left">Name<span style="color:red">*</span></th>
	<td><input type="text" name="name" size="40"  class="p2" placeholder="Enter Name" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
	<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name",request) %></font></td>
</tr>
<tr>
	<th align="left">Description<span style="color:red">*</span></th>
	<td><input type="text" name="description" size="40"  class="p2" placeholder="Enter Description" value="<%=DataUtility.getStringData(bean.getDescription())%>"></td>
	<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("description",request) %></font></td>
</tr>
	
	<%
		
				if (bean.getId() > 0) {
				%>
	
	
<tr>
	<th ></th>
	<td colspan="2"><input type="submit" name="operation" value="<%=RoleCtl.OP_UPDATE%>">&emsp;
	<input type="submit" name="operation" value="<%=RoleCtl.OP_CANCEL%>"></td>
	</tr>
		<%
				}else{
		%>
		<th ></th>
	<td colspan="2"><input type="submit" name="operation" value="<%=RoleCtl.OP_SAVE%>">&emsp;
	<input type="submit" name="operation" value="<%=RoleCtl.OP_RESET%>"></td>
	</tr>

		<% } %>
</table>
</center>

</form>
<%@include file="Footer.jsp"%>
</body>
</html>