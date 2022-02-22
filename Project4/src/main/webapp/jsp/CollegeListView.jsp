<%@page import="in.co.sunrays.ctl.CollegeListCtl" %>
<%@page import="in.co.sunrays.util.ServletUtility" %>
<%@page import="in.co.sunrays.util.DataUtility" %>
<%@page import="in.co.sunrays.util.HTMLUtility" %>
<%@page import="in.co.sunrays.bean.CollegeBean" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Collage List</title>
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
<%@include file="Header.jsp" %>
<center>
<h1>College List</h1>
<form action="<%=ORSView.COLLEGE_LIST_CTL%>"method="POST" >

<center>
				<font color="red" size="5px"><%=ServletUtility.getErrorMessage(request)%></font>
			</center>
			<center>
				<font color="green" size="5px"><%=ServletUtility.getSuccessMessage(request)%></font>
			</center>
			<jsp:useBean id="bean" class="in.co.sunrays.bean.CollegeBean"
				scope="request"></jsp:useBean>
				
				<%
				List list1 = (List) request.getAttribute("collegeList");
			%>
			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				List list = ServletUtility.getList(request);
				Iterator<CollegeBean> it = list.iterator();
				if (list.size() != 0) {
			%>
			<table>
				<tr>
					<td><label class="p1">College Name</label> 
					<%= HTMLUtility.getList("name" , String.valueOf(bean.getId()), list1) %>&emsp;

						<label class="p1">City</label> <input type="text" name="city" class="p2" Placeholder ="City"
						value="<%=ServletUtility.getParameter("city", request)%>">&emsp;&emsp;
						
						<label class="p1">State</label> <input type="text" name="state" class="p2" Placeholder ="State"
						value="<%=ServletUtility.getParameter("state", request)%>">&emsp;&emsp;
						
						<input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_SEARCH%>" style="padding: 5px;">&emsp;
						<input type="submit" name="operation"
						value="<%=CollegeListCtl.OP_RESET%>" style="padding: 5px;"></td>
				</tr>
			</table>
			<br>
			<%
				
			%>
			<table border="1" width="100%">
				<tr>
					<th width="10%"><input type="checkbox" id="select_all"
						name="Select"> Select All</th>
					<th>S.No</th>
					<th>Name</th>
					<th>Address</th>
					<th>State</th>
					<th>City</th>
					<th>Mobile No</th>
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
					<td align="center"><%=bean.getName()%></td>
					<td align="center"><%=bean.getAddress()%></td>
					<td align="center"><%=bean.getState()%></td>
					<td align="center"><%=bean.getCity()%></td>
					<td align="center"><%=bean.getPhoneNo()%></td>
					<td align="center"><a href="CollegeCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>
			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						style="padding: 5px;" value="<%=CollegeListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td><input type="submit" name="operation" class="button1"
						style="padding: 5px;" value="<%=CollegeListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation" class="button1"
						style="padding: 5px;" value="<%=CollegeListCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"
						style="padding: 5px;" value="<%=CollegeListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>

			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<input type="submit" name="operation"
				value="<%=CollegeListCtl.OP_BACK%>">
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