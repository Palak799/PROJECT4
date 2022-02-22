<%@page import="in.co.sunrays.ctl.CourseCtl"  %>
<%@page import="in.co.sunrays.util.DataUtility" %>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.util.HTMLUtility"%>
<%@page import="in.co.sunrays.util.ServletUtility" %>
<%@page import="in.co.sunrays.ctl.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course View</title>
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
	<form action="<%=ORSView.COURSE_CTL%>" method="POST">
<%@include file="Header.jsp" %>
	<jsp:useBean id="bean" class="in.co.sunrays.bean.CourseBean" scope="request"></jsp:useBean>
	
	<center>
	
	
	
	<input type="hidden" name="id" value="<%=bean.getId() %>">
	<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy() %>">
	<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy() %>">
	<input type="hidden" name="createdDateTime" value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime()) %>">
	<input type="hidden" name="modifiedDateTime" value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime()) %>">
	
		<%
				if (bean.getId() > 0) {
			%>
			<h1 style="font-size: 40px;">Update Course</h1>
			<%
				} else {
			%>
			<h1 style="font-size: 40px;">Add Course</h1>
			<%
				}
			%>	
	<h2>
	<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
	
	</h2>
	<h2>
	<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
	</h2>
	
	<table>
	<tr>
	    <th align="left" class="p1">Course<span style="color:red;">*</span></th>
	    <td><input type="text" name="courseName" size="40" placeholder="Enter course " class="p2"
		value="<%=DataUtility.getStringData(bean.getCourseName())%>"></td>
		<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("courseName", request)%></font>
		</td>
	</tr>
	
	<tr>
		<th align="left" class="p1">Description<span style="color: red;">*</span></th>
		<td><textarea name="description"   placeholder="Enter Description" style="width: 305px;height:50px; resize: none;"><%=DataUtility.getStringData(bean.getDescription())%></textarea></td>
		<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
				</tr>
	<tr>
					<th align="left">Duration<span style="color: red;">*</span></th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("1Year", "1Year");
							map.put("2Year", "2Year");
							map.put("3Year", "3Year");
							map.put("4Year", "4Year");
							map.put("5Year", "5Year");
							
							String HtmlList = HTMLUtility.getList("duration", bean.getDuration(), map);
						%> <%=HtmlList%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("duration", request)%></font>
					</td>

				<%
						if (bean.getId() > 0) {
					%>
				
				<tr>
					<th></th>
					<td colspan="2" align="center"><input type="submit"
						name="operation" value="<%=CourseCtl.OP_UPDATE%>"
						style="padding: 5px;">&emsp; <input type="submit"
						name="operation" value="<%=CourseCtl.OP_CANCEL%>"
						style="padding: 5px;"></td>
				</tr>
				<%
					} else {
				%>
				<tr>	<th></th>
					<td colspan="2" align="center"><input type="submit"
						name="operation" value="<%=CourseCtl.OP_SAVE%>"
						style="padding: 5px;">&emsp; <input type="submit"
						name="operation" value="<%=CourseCtl.OP_RESET%>"
						style="padding: 5px;"></td>
				</tr>
				<%
					}
				%>
				</tr>
				</table>
			  <%@include file="Footer.jsp"%> 
		</center>
	</form>	
</body>
</html>