<%@page import="in.co.sunrays.util.HTMLUtility"%>
<%@page import="in.co.sunrays.util.DataUtility"%>
<%@page import="in.co.sunrays.bean.SubjectBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.ctl.SubjectListCtl"%>
<%@page import="in.co.sunrays.util.ServletUtility"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Subject List View</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
<style>
.p1 {
	font-size: 18px;
	font-weight: bold;
}

.p2 {
	padding: 5px;
	margin: 3px;
}
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
<%@include file="Header.jsp"%>
	<jsp:useBean id="bean" class="in.co.sunrays.bean.SubjectBean"
		scope="request"></jsp:useBean>
	<center>
		<h1 style="font-size: 40px;">Subject List</h1>
		<center>
			<font color="red" size="5px"><%=ServletUtility.getErrorMessage(request)%></font>
		</center>
		<center>
			<font color="green" size="5px"><%=ServletUtility.getSuccessMessage(request)%></font>
		</center>
		<form action="<%=ORSView.SUBJECT_LIST_CTL%>" method="post">
			<%
				List list1 = (List) request.getAttribute("courseList");
			%>
			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				List list = ServletUtility.getList(request);
				Iterator<SubjectBean> it = list.iterator();
				if (list.size() != 0) {
			%>
			<table>
				<tr>
					<td><label class="p1"> Subject Name </label>&emsp;<input
						type="text" class="p2" name="name" placeholder="Enter Subject Name"
						value="<%=ServletUtility.getParameter("name", request)%>">&emsp;
			<%-- 	<label class="p1">College Name</label>
		<%=HTMLUtility.getList("collegeId",String.valueOf(bean.getCourseId()),list1)%>&emsp; --%>
						
						
						
						<input type="submit" name="operation"
						value="<%=SubjectListCtl.OP_SEARCH%>" style="padding: 5px;">&emsp;
						
			<input type="submit" name="operation"
						value="<%=SubjectListCtl.OP_RESET%>" style="padding: 5px;"></td>
				</tr>
			</table>
			<table border="1px" width="100%">
				<tr>
					<th width="10%"><input type="checkbox" id="select_all"
						name="Select"> Select All</th>
					<th>S.No</th>
					<th>SubjectName</th>
					<th>CourseName</th>
					<th>Description</th>
					<th>Edit</th>
				</tr>



				<%
					while (it.hasNext()) {
							SubjectBean bean1 = it.next();
				%>
				<tr>
					<td align="center"><input type="checkbox" class="checkbox"
						name="ids" value="<%=bean1.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td align="center"><%=bean1.getSubjectName()%></td>
					<td align="center"><%=bean1.getCourseName()%></td>
					<td align="center"><%=bean1.getDescription()%></td>
					<td align="center"><a href="SubjectCtl?id=<%=bean1.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
				</table>
			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						style="padding: 5px;" value="<%=SubjectListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td><input type="submit" name="operation"
						style="padding: 5px;" value="<%=SubjectListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						style="padding: 5px;" value="<%=SubjectListCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"
						style="padding: 5px;" value="<%=SubjectListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>
			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<table>
				<input type="submit" name="operation"
					value="<%=SubjectListCtl.OP_BACK%>">
			
			<% 
				}
			%>
			
			
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> 
			<input type="hidden" name="pageSize" value="<%=pageSize%>">

			
		</form>	
	    	</center>
<%@include file="Footer.jsp"%>
			

</body>
</html>