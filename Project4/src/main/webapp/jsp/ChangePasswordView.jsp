<%@page import="in.co.sunrays.ctl.ChangePasswordCtl"%>
<%@page import="in.co.sunrays.util.DataUtility"%>
<%@page import="in.co.sunrays.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
	<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="POST">
	<%@include file="Header.jsp"%>
	<jsp:useBean id="bean" class="in.co.sunrays.bean.UserBean"
		scope="request"></jsp:useBean>
	<center>
		<h1 style="font-size: 40px;">Change Password</h1>
		<h2>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
		</h2>
		<h2>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</h2>
		<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
			type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy"
			value="<%=bean.getModifiedBy()%>"> <input type="hidden"
			name="createdDatetime" value="<%=bean.getCreatedDateTime()%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=bean.getModifiedDateTime()%>">

		<table>
			<tr>
				<th align="left" class="p1">Old Password<span style="color:red;">*</span></th>
				<td><input type="password" name="oldPassword"  class="p2" size="30" placeholder="Enter Old Password"
					value=<%=DataUtility.getString(request.getParameter("oldPassword") == null ? ""
					: DataUtility.getString(request.getParameter("oldPassword")))%>></td>
						<td style="position: fixed;"><font
					color="red"><%=ServletUtility.getErrorMessage("oldPassword", request)%></font>
				</td>
			</tr>
			<tr>
				<th align="left" class="p1">New Password<span style="color:red;">*</span></th>
				<td><input type="password" name="newPassword" class="p2" size="30" placeholder="Enter New Password"
					value=<%=DataUtility.getString(request.getParameter("newPassword") == null ? ""
					: DataUtility.getString(request.getParameter("newPassword")))%>></td>
						<td style="position: fixed;"><font
					color="red"><%=ServletUtility.getErrorMessage("newPassword", request)%></font>
				</td>
			</tr>
			<tr>
				<th align="left" class="p1">Confirm Password<span style="color:red;">*</span></th> 
				<td><input type="password" name="confirmPassword"  class="p2" size="30" placeholder="Enter Confirm password"
					value=<%=DataUtility.getString(request.getParameter("confirmPassword") == null ? ""
							: DataUtility.getString(request.getParameter("confirmPassword")))%>></td>
						<td style="position: fixed;"><font
					color="red"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></font>
				</td>
			</tr>
			
			
			
			 <tr>
                    <th></th>
                    <td colspan="2" align="center"><input type="submit"
                        name="operation" value="<%= ChangePasswordCtl.OP_SAVE%>" style="padding: 5px;"> &nbsp; <input type="submit" name="operation"
                        value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE %>" style="padding: 5px;"> &nbsp;</td>
                </tr>

			




		</table>




	</center>
	<%@include file="Footer.jsp"%>
	</form>
</body>
</html>