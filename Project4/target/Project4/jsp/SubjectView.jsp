<%@page import="in.co.sunrays.ctl.SubjectCtl" %>
<%@page import="in.co.sunrays.util.ServletUtility" %>
<%@page import="in.co.sunrays.util.DataUtility" %>
<%@page import="in.co.sunrays.util.HTMLUtility" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Subject</title>
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
<form action="SubjectCtl" method="post">
<%@include file="Header.jsp" %>
	<jsp:useBean id="bean" class="in.co.sunrays.bean.SubjectBean" scope="request"></jsp:useBean>
	<center>
	 <%
	  	if(bean.getId()>0){
	  	
	 %>
	 <h1 style="font-size:40px;">Update Subject</h1>
	 <%
	    } else {
	 %>
	 <h1 style="font-size:40px;">Add Subject</h1>
	 <%
	    }
	 %>
	
	<center>
	
	<h2>
	<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
	
	</h2>
	<h2>
	<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
	
	</h2>
	<input type="hidden" name="id" value="<%=bean.getId() %>">
	<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy() %>">
	<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy() %>">
	<input type="hidden" name="createdDateTime" value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime()) %>">
	<input type="hidden" name="modifiedDateTime" value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime()) %>">

		<%
			List l = (List) request.getAttribute("courseList");
		%>

	<table>
 <tr>
	<th align="left" class="p1">Subject Name<span style="color: red;">*</span></th>
	<td><input type="text" name="subjectName" size="40%" placeholder="Enter Subject" class="p2"
			value="<%=DataUtility.getStringData(bean.getSubjectName())%>"></td>
	<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("subjectName", request)%></font></td>
 </tr>
 
 <tr>
   <th align="left" class="p1">CourseName<span style="color:red">*</span></th>
   <td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), l)%></td>
   <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("courseName",request) %></font></td>
   </tr>
   
   
 <tr>
   <th align="left" class="p1">Description<span style="color:red">*</span></th>
   <td><textarea name="description"  placeholder="Enter Description" style="width: 320px;height:50px; resize: none;"><%=DataUtility.getStringData(bean.getDescription())%></textarea></td>
  <td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("description",request) %></font></td>
   </tr>
   
  			    <%
					if (bean.getId() > 0) {
				%>

	<tr>
       <th></th>
       <td colspan="2" align="center"><input type="submit" name="operation" value="<%=SubjectCtl.OP_UPDATE%>" style="padding: 5px;">
						&emsp;
      <%--  &emsp;<input type="submit" name="operation" value="<%=SubjectCtl.OP_DELETE%>" style="padding: 5px;">&emsp; --%>
       &emsp; <input type="submit" name="operation" value="<%=SubjectCtl.OP_CANCEL%>" style="padding: 5px;"></td>
       </tr>
       
       <%
       }else{
       %>
       <tr><td colspan="2" align="center">
       <input type="submit" name="operation" style="padding: 5px;" value="<%=SubjectCtl.OP_SAVE%>" >&emsp;&emsp;
       <input type="submit" name="operation" style="padding: 5px;" value="<%=SubjectCtl.OP_RESET%>" >
       </td>
       </tr>
       <% } %>
</body>
<%@include file="Footer.jsp" %>
</html>