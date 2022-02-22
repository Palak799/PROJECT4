<%@page import="in.co.sunrays.util.HTMLUtility"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.sunrays.util.DataUtility"%>
<%@page import="in.co.sunrays.ctl.ORSView"%>
<%@page import="in.co.sunrays.ctl.FacultyCtl"%>
<%@page import="in.co.sunrays.bean.FacultyBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.ctl.FacultyListCtl"%>
<%@page import="in.co.sunrays.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty List View</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.p1 {
	font-size: 18px;
	font-weight: bold;
}

.p2 {
	padding: 3px;
	margin: 1px;
	
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
	<center>

		<h1 style="font-size: 40px;">Faculty List</h1>

		<center>
			<font color="red" size="5px"><%=ServletUtility.getErrorMessage(request)%></font>
		</center>
		<center>
			<font color="green" size="5px"><%=ServletUtility.getSuccessMessage(request)%></font>
		</center>
		<jsp:useBean id="bean" class="in.co.sunrays.bean.FacultyBean"
			scope="request"></jsp:useBean>

		<%
			List list1 = (List) request.getAttribute("collegeList");
			List list2 = (List) request.getAttribute("courseList");
		%>
		<form action="<%=ORSView.FACULTY_LIST_CTL%>" method="POST">
			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				List list = ServletUtility.getList(request);
				Iterator<FacultyBean> it = list.iterator();
				if (list.size() != 0) {
			%>
		<table>
				<tr>
					<td><label class="p1">First Name</label>&emsp;<input type="text"
						name="firstName" placeholder="Enter FacultyName" class="p2" size="41"
						value="<%=ServletUtility.getParameter("firstName", request)%>">&emsp;
						
						<label class="p1">Email_Id</label>&emsp;&emsp;<input type="text"
						class="p2" size="41" name="login" placeholder="Enter Email Id"
						value="<%=ServletUtility.getParameter("login", request)%>">&emsp;<br><br>
						
		<label class="p1">College Name</label>
		<%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeid()), list1)%>&emsp;
						
		<label class="p1">Course Name</label>
		<%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), list2)%>&emsp;&emsp;
						
						<input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_SEARCH%>" style="padding: 5px;">
						
						<input type="submit" name="operation"
						value="<%=FacultyListCtl.OP_RESET%>" style="padding: 5px;"></td>
				</tr>
<tr></tr>
<tr></tr>

			</table>
			<table border="1" width="100%">
				<tr>
					<th width="10%"><input type="checkbox" id="select_all"
						name="Select"> Select All</th>
					<th>S.NO</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Qualification</th>
					<th>Email Id</th>
					<th>Joining Date</th>
					<th>College Name</th>
					<th>Course Name</th>
					<th>Subject Name</th>
					<th>Edit</th>

				</tr>

				<%
					while (it.hasNext()) {
							bean = it.next();
				%>
				<tr>
					<td align="center"><input type="checkbox" class="checkbox"
						name="ids" value="<%=bean.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td align="center"><%=bean.getFirstName()%></td>
					<td align="center"><%=bean.getLastName()%></td>
					<td align="center"><%=bean.getQualification()%></td>
					<td align="center"><%=bean.getLoginId()%></td>
				<%-- 	<%
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
								String date = formatter.format(bean.getDateofjoining());
					%> --%>
					<td align="center"><%=bean.getDateofjoining()%></td> 
					<td align="center"><%=bean.getCollegeName()%></td>
					<td align="center"><%=bean.getCourseName()%></td>
					<td align="center"><%=bean.getSubjectName()%></td>
					<td align="center"><a href="FacultyCtl?id=<%=bean.getId()%>">Edit</a></td>


				</tr>
				<%
					}
				%>
			</table>
			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						style="padding: 5px;" value="<%=FacultyCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td><input type="submit" name="operation" class="button1"
						style="padding: 5px;" value="<%=FacultyCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation" class="button1"
						style="padding: 5px;" value="<%=FacultyCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"
						style="padding: 5px;" value="<%=FacultyCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>

			</table>
			<%
				}
				if (list.size() == 0) {
			%>

			<input type="submit" name="operation" style="padding: 5px;"
				value="<%=FacultyCtl.OP_BACK%>">
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