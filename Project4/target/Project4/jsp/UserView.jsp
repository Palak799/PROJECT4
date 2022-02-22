<%@page import="in.co.sunrays.ctl.UserCtl"%>
<%@page import="in.co.sunrays.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.util.DataUtility"%>
<%@page import="in.co.sunrays.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Datepicker - Display month &amp; year menus</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
	$(function() {
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1970:2030',
			maxDate:0
		});
	});
	
</script>
  

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<style>
.p1 {
	font-size: 18px;
}

.p2 {
	padding: 4px;
	margin: 3px;
}
/* .footer{
	position:relative;
	left:  0;
	bottom: 0;
	width: 100%;
	text-align:center;
}

.footer {
   position: relative;
   left: 0;
   bottom: 0;
   width: 100%;
   text-align: center;
}
 */</style>


 </head>
<body>
	<form action="<%=ORSView.USER_CTL%>" method="post">
		<%@ include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.co.sunrays.bean.UserBean"
			scope="request"></jsp:useBean>

		
		
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">

					<center>
				
			<%
				if (bean.getId() > 0) {
			%>

			<h1 style="font-size: 40px;">Update User</h1>
			<%
				} else {
			%>

			<h1 style="font-size: 40px;">Add User</h1>
			<%
				}
			%>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>

			<H2>


				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>





			<%
				List l = (List) request.getAttribute("roleList");
			%>
			
			<table>
				<tr>
					<th align="left" class="p1">First Name<span
						style="color: red;">*</span></th>
					<td><input type="text" name="firstName" size="40" class="p2"
						placeholder="Enter First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>
				<tr>
					<th align="left" class="p1">Last Name<span style="color: red;">*</span></th>
					<td><input type="text" name="lastName" size="40" class="p2"
						placeholder="Enter Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position:fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>
				<tr>
					<th align="left" class="p1">LoginId<span style="color: red;">*</span></th>
					<td><input type="text" name="login" size="40" class="p2"
						placeholder="Enter Login ID"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"
						<%=(bean.getId() > 0) ? "readonly" : ""%>></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				<%
					} else {
				%>
				<tr>
					<th align="left" class="p1">Password<span style="color: red;">*</span></th>
					<td><input type="password" name="password" size="40"
						class="p2" placeholder="Enter password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>

				<tr>
					<th align="left" class="p1">Confirm Password<span
						style="color: red;">*</span></th>
					<td><input type="password" name="confirmPassword" class="p2"
						size="40" placeholder="Enter Confirm password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
				</tr>
				<%
					}
				%>
				<tr>
					<th align="left" class="p1">MobileNo<span style="color: red;">*</span></th>
					<td><input type="text" name="mobile" size="40" 
						class="p2"  maxlength="10"
						placeholder="Enter Mobile No"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("mobile", request)%></font></td>
				</tr>
				<tr>
					<th align="left" class="p1">Gender<span style="color: red;">*</span></th>
					<td>
						<%
							HashMap map = new HashMap();
						map.put("","-----------------------------Select--------------------------------");
							map.put("Male", "Male");
							map.put("Female", "Female");
map.put("others", "others");
							String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
						%> <%=htmlList%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font>
					</td>
				</tr>
				 <tr>
					<th align="left" class="p1">Role :<span style="color: red;">*</span></th>
					<td><%=HTMLUtility.getList("roleList", String.valueOf(bean.getRoleId()), l)%></td>
					 <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("rolename", request)%></font>
					 </td>
				</tr>
				<tr>
					<th align="left" class="p1">Date Of Birth<span
						style="color: red;">*</span></th>
					<td><input type="text" readonly="readonly"  name="dob" id="datepicker" class="p2"
						size="40" placeholder="Enter Date of Birth"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>

				</tr>
				<%
					if (bean.getId() > 0) {
				%>
				<tr>
					<th></th>
					<td colspan="2" align="center"><input type="submit"
						name="operation" style="padding: 4px;"
						value="<%=UserCtl.OP_UPDATE%>">&emsp; <input type="submit"
						style="padding: 4px;" name="operation"
						value="<%=UserCtl.OP_CANCEL%>"></td>
				</tr>
				<%
					} else {
				%>
				<tr>
					
					<td colspan="2" align="center">
					<input type="submit" name="operation" style="padding: 4px;"
						value="<%=UserCtl.OP_SAVE%>">
					<input type="submit" style="padding: 4px;" name="operation"
						value="<%=UserCtl.OP_RESET%>"></td>
				</tr>
				<%
					}
				%>
			</table>
				</center>
			
	</form>
 <%@include file="Footer.jsp"%>

</body>
</html>