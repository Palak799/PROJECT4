<%@page import="in.co.sunrays.ctl.CourseListCtl" %>
<%@page import="in.co.sunrays.util.ServletUtility" %>
<%@page import="in.co.sunrays.util.DataUtility" %>
<%@page import="in.co.sunrays.util.HTMLUtility" %>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.bean.CourseBean" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CourseList View</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
<style>
.footer{
	position:relative;
	left:  0;
	bottom: 0;
	width: 100%;
	text-align:center;
}
</style>
</head>
<body>
<%@include file="Header.jsp" %>
<center>
<!-- <h1>Course List</h1> -->
<form action="<%=ORSView.COURSE_LIST_CTL%>"method="POST" >
<jsp:useBean id="bean" class="in.co.sunrays.bean.CourseBean"
		scope="request"></jsp:useBean>

	<center>
			<h1 style="font-size: 40px;">Course List</h1>
	</center>
	<center>
				<font color="red" size="5px"><%=ServletUtility.getErrorMessage(request)%></font>
			</center>
			<center>
				<font color="green" size="5px"><%=ServletUtility.getSuccessMessage(request)%></font>
			</center>
	
	<%
		List list1 = (List) request.getAttribute("courseList");
	%>
	<%
		int pageNo = ServletUtility.getPageNo(request);
		int pageSize = ServletUtility.getPageSize(request);
		int index = ((pageNo - 1) * pageSize) + 1;
		int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
		List list = ServletUtility.getList(request);
		Iterator<CourseBean> it = list.iterator();
		if (list.size() != 0) {
	%>
	<table>
				<tr>
					<td><label class="p1">Course</label>&emsp;<%=HTMLUtility.getList("courseId", String.valueOf(bean.getId()), list1)%>&emsp;&emsp;&emsp;
		<label class="p1">Duration</label>&emsp;<%
						HashMap map = new HashMap();
							map.put("1year","1year");
							map.put("2year","2year");
							map.put("3year","3year");
							map.put("4year","4year");
							map.put("5year","5year");
							
							String HtmlList = HTMLUtility.getList("duration", bean.getDuration(), map);
					%><%=HtmlList%>&emsp;  
					<input type="submit" name="operation"
						value="<%=CourseListCtl.OP_SEARCH%>" style="padding: 5px;">&emsp;
						<input type="submit" name="operation"
						value="<%=CourseListCtl.OP_RESET%>" style="padding: 5px;"></td>
				</tr>
		</table>
		
		<table border="1" width="100%">
				<tr>
					<th width="10%"><input type="checkbox" id="select_all"
						name="Select"> Select All</th>
					<th>S.No</th>
					<th>Course Name</th>
					<th>Duration</th>
					<th>Description</th>
					<th>Edit</th>
				</tr>
		        <%
					while (it.hasNext()) {
							CourseBean cbean = it.next();
				%>
		<tr>
					
					<td align="center"><input type="checkbox" class="checkbox"
						name="ids" value="<%=cbean.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td align="center"><%=cbean.getCourseName()%></td>
					<td align="center"><%=cbean.getDuration()%></td>
					<td align="center"><%=cbean.getDescription()%></td>
					<td align="center"><a href="CourseCtl?id=<%=cbean.getId()%>">Edit</a></td>
				</tr>
				
				<%
					}
				%>
				</table>
						

<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						style="padding: 5px;" value="<%=CourseListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td><input type="submit" name="operation"
						style="padding: 5px;" value="<%=CourseListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						style="padding: 5px;" value="<%=CourseListCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"
						style="padding: 5px;" value="<%=CourseListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>

		</table>

<%
				}
				if (list.size() == 0) {
			%>

			<input type="submit" name="operation" style="padding: 5px;"
				value="<%=CourseListCtl.OP_BACK%>">
			<%
				}
			%>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>
		
	</center>
<%@include file="Footer.jsp"%>


</body>
</html>