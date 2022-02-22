<%@page import="in.co.sunrays.ctl.LoginCtl"%>
<%@page import="in.co.sunrays.util.DataUtility"%>
<%@page import="in.co.sunrays.util.ServletUtility"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
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
<form action ="<%=ORSView.LOGIN_CTL%>"method="post">
	<%@include file="Header.jsp"%>
	<jsp:useBean id="bean" class="in.co.sunrays.bean.UserBean" scope="request"></jsp:useBean>
	
	
	<input type ="hidden" name="id" value="<%=bean.getId()%>">
	<input type ="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
	<input type ="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
	<input type ="hidden" name="createdDateTime" value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime())%>">
	<input type ="hidden" name="modifiedDateTime" value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">
	
	   
	<center>
	<h1 style="font-size: 40px;">Login</h1>
	</center>
	
	<center>
		<table>
				<tr>
					<td></td>
				</tr>
				<tr>
				
				 <%
			String msg = (String) request.getAttribute("msg");
			if (msg != null) {
		%>
		<h3>
			<font color="red"><%=msg%></font>
		</h3>
	<% } %>
					<H2>
						<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
						</font>
					</H2>

					<H2>
						<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
						</font>
					</H2>
					<%
						String uri = (String) request.getAttribute("uri");
					%>
					
					
					
	
			<th align="left" class="p1">LoginId<span style="color:red">*</span></th>
			<td><input type ="text" name="login" class="p2" size="40" size=30 placeholder="Enter LoginID"
			value="<%=DataUtility.getStringData(bean.getLogin())%>"><td>
			<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("login",request)%></font></td>
			</tr>
			<tr>
			  <th align="left" class="p1">Password<span style="color:red">*</span></th>
			  <td><input type="password" name="password" class="p2" size="40" size=30 
			  placeholder="Enter Password" value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
			  <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("password",request)%></font></td>
			  </tr>
			  <tr>
			   <th></th>
			   <td colspan="2"><input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_IN %>"style="padding: 5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			   <input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_UP%>"style="padding: 5px;">&nbsp;</td>
			   </tr>
			   <tr><th><br></th>
			   <td><br><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"font-size: 15px;"><b>Forget my password</b></a>&nbsp;</td>
			   </tr>
			   
			   </table>
			   
			   <input type="hidden" name="uri" value="<%=uri%>">
			   
			   </form>
	</center>
	
</body>
	<%@include file="Footer.jsp"%>
</html>