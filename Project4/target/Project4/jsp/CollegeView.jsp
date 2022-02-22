<%@page import="in.co.sunrays.ctl.CollegeCtl"%>
<%@page import="in.co.sunrays.util.DataUtility"%>
<%@page import="in.co.sunrays.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add College</title>
<style>
.p1 {
	font-size: 18px;
}

.p2 {
	padding: 5px;
	margin: 3px;
}
</style>
<script>
	function phoneno() {
		$('#phone').keypress(function(e) {
			var a = [];
			var k = e.which;

			for (i = 48; i < 58; i++)
				a.push(i);

			if (!(a.indexOf(k) >= 0))
				e.preventDefault();
		});
	}
</script>


</head>
<body>
	<form action="CollegeCtl" method="post">
		<%@include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.co.sunrays.bean.CollegeBean"
			scope="request"></jsp:useBean>

		<center>
			<%
				if (bean.getId() > 0) {
			%>
			<h1 style="font-size: 40px;">Update College</h1>
			<%
				} else {
			%>
			<h1 style="font-size: 40px;">Add College</h1>
			<%
				}
			%>



			<h2>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>

			</h2>
			<h2>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>

			</h2>
			<center>

				<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
					type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
				<input type="hidden" name="modifiedBy"
					value="<%=bean.getModifiedBy()%>"> <input type="hidden"
					name="createdDateTime"
					value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime())%>">
				<input type="hidden" name="modifiedDateTime"
					value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">

				<table>
					<tr>
						<th align="left" class="p1">Name<span style="color: red">*</span></th>
						<td><input type="text" name="name" class="p2" size="40"
							placeholder="Enter Name"
							value="<%=DataUtility.getStringData(bean.getName())%>"></td>
						<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>
					</tr>
					<tr>
						<th align="left" class="p1">Address<span style="color: red">*</span></th>
						<td><input type="text" name="address" class="p2" size="40"
							placeholder="Enter Address"
							value="<%=DataUtility.getStringData(bean.getAddress())%>"></td>
						<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("address", request)%></font></td>
					</tr>
					<tr>
						<th align="left" class="p1">State<span style="color: red">*</span></th>
						<td><input type="text" name="state" class="p2" size="40"
							placeholder="Enter State"
							value="<%=DataUtility.getStringData(bean.getState())%>"></td>
						<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("state", request)%></font></td>
					</tr>
					<tr>
						<th align="left" class="p1">City<span style="color: red">*</span></th>
						<td><input type="text" name="city" class="p2" size="40"
							placeholder="Enter City"
							value="<%=DataUtility.getStringData(bean.getCity())%>"></td>
						<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("city", request)%></font></td>
					</tr>
					<tr>
						<th align="left" class="p1">PhoneNo<span style="color: red">*</span></th>
						<td><input type="text" name="phoneNo" class="p2" size="40"
							placeholder="Enter PhoneNo"
							value="<%=DataUtility.getStringData(bean.getPhoneNo())%>"></td>
						<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("phoneNo", request)%></font></td>
					</tr>

					<%
						if (bean.getId() > 0) {
					%>
					<tr>
						<th></th>
						<td colspan="2" align="center"><input type="submit"
							name="operation" value="<%=CollegeCtl.OP_UPDATE%>"
							style="padding: 5px;"> &emsp; <%--  &emsp;<input type="submit" name="operation" value="<%=CollegeCtl.OP_DELETE%>" style="padding: 5px;">&emsp; --%>
							&emsp; <input type="submit" name="operation"
							value="<%=CollegeCtl.OP_CANCEL%>" style="padding: 5px;"></td>
					</tr>

					<%
						} else {
					%>
					<tr>
						<td colspan="2" align="center"><input type="submit"
							name="operation" value="<%=CollegeCtl.OP_SAVE%>"
							style="padding: 5px;">&emsp;&emsp; <input type="submit"
							name="operation" value="<%=CollegeCtl.OP_RESET%>"
							style="padding: 5px;"></td>
					</tr>
					<%
						}
					%>
				</table>
			</center>
			<%@include file="Footer.jsp"%>
	</form>
</body>
</html>