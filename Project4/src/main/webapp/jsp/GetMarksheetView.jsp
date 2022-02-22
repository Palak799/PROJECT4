<%@page import="in.co.sunrays.ctl.GetMarksheetCtl" %>
<%@page import="in.co.sunrays.util.DataUtility" %>
<%@page import="in.co.sunrays.util.ServletUtility" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get Marksheet View</title>
<style>

.p1 {
	font-size: 18px;
	font-weight: bold;
}

.p2 {
	padding: 5px;
	margin: 3px;
}
.p3 {
	font-size: 20px;
	padding: 9px;
	margin: 3px;
}

#po1 {
	font-size: 18px;
	height: 35px;
}

</style>

</head>
<body>

<%@include file="Header.jsp"%>
<jsp:useBean id="bean" class="in.co.sunrays.bean.MarksheetBean" scope="request"></jsp:useBean>

<center>
<h1 style="font-size: 40px;">Get Marksheet</h1>
<h2>
<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
</h2>
<h2>
<font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>

</h2>

<form action="<%=ORSView.GET_MARKSHEET_CTL%>"method="post">

<input type="hidden" name="id" value="<%=bean.getId()%>">
<table border=1>
<tr>
<th><b> RollNo :</b></th>&emsp;
<td><input type="text" name="rollNo" size="40" class="p2" placeholder="Enter RollNo" 
value="<%=ServletUtility.getParameter("rollNo", request) %>">&emsp;

<input type="submit" name="operation" 
value="<%=GetMarksheetCtl.OP_GO %>"size="40" class="p2">
 <font color="red" class="p3" style="position: fixed;"><%=ServletUtility.getErrorMessage("rollNo", request) %></font><td> 
<%
if(bean.getRollNo()!=null&&bean.getRollNo().trim().length()>0){
	
%></td>
</table>
<table border="1" width="60%">
<br><br>
<tr>
  <th id="po1" colspan="2">RollNo</th>
	<td id="po1" align="center" colspan="3">
		<%=DataUtility.getStringData(bean.getRollNo())%></td>
  
  </tr>
  <tr>
	<th id="po1" colspan="2">Name</th>
	<td id="po1" align="center" colspan="3">
	     <%=DataUtility.getStringData(bean.getName())%></td>
	</tr>
	<tr>
					<th colspan="1">Subject</th>
					
					<th colspan="1">Max Marks</th>
					<th colspan="1">Min Marks</th>
					<th colspan="2">Marks Obtain</th>
				</tr>
				<tr>
				
					<th></th>
					<th></th>
					<th></th>
					<th>Marks</th>
					<th style="color: blue;"  >Grade</th>
				</tr>
  <tr>
					<th id="po1">Physics</th>
					<td align="center">100</td>
					<td align="center">35</td>
					<td id="po1" align="center"><%=DataUtility.getStringData(String.valueOf(bean.getPhysics()))%>
						<%
							float physics = bean.getPhysics();
								if (bean.getPhysics() < 35) {
						%><span style="color: red;">*</span> <%
 	}
 %></td>
					<td align="center">
						<%
							if (physics >= 90) {
						%> <b>A++</b> <%
 	} else if (physics >= 80) {
 %> <b>A</b> <%
 	} else if (physics >= 70) {
 %> <b>B++</b> <%
 	} else if (physics >= 60) {
 %> <b>B</b> <%
 	} else if (physics >= 50) {
 %> <b>C++</b> <%
 	} else if (physics >= 40) {
 %> <b>C</b> <%
 	} else if (physics >= 35) {
 %> <b>D</b> <%
 	} else if (physics >= 0) {
 %> <font color="red"><b>F</b></font> <%
 	}
 %>
					</td>
				</tr>
				<tr>
					<th id="po1">Chemistry</th>
					<td align="center">100</td>
					<td align="center">35</td>
					<td id="po1" align="center"><%=DataUtility.getStringData(String.valueOf(bean.getChemistry()))%>
						<%
							float chemistry = bean.getChemistry();
								if (bean.getChemistry() < 35) {
						%><span style="color: red;">*</span> <%
 	}
 %></td>
					<td align="center">
						<%
							if (chemistry >= 90) {
						%> <b>A++</b> <%
 	} else if (chemistry >= 80) {
 %> <b>A</b> <%
 	} else if (chemistry >= 70) {
 %> <b>B++</b> <%
 	} else if (chemistry >= 60) {
 %> <b>B</b> <%
 	} else if (chemistry >= 50) {
 %> <b>C++</b> <%
 	} else if (chemistry >= 40) {
 %> <b>C</b> <%
 	} else if (chemistry >= 35) {
 %> <b>D</b> <%
 	} else if (chemistry >= 0) {
 %> <font color="red"><b>F</b></font> <%
 	}
 %>
					</td>
				</tr>
				<tr>
					<th id="po1">Maths</th>
					<td align="center">100</td>
					<td align="center">35</td>
					<td id="po1" align="center"><%=DataUtility.getStringData(String.valueOf(bean.getMaths()))%>
						<%
							float marks = bean.getMaths();
								if (marks <= 35) {
						%><span style="color: red;">*</span> <%
 	}
 %></td>
					<td align="center">
						<%
							if (marks >= 90) {
						%> <b>A++</b> <%
 	} else if (marks >= 80) {
 %> <b>A</b> <%
 	} else if (marks >= 70) {
 %> <b>B++</b> <%
 	} else if (marks >= 60) {
 %> <b>B</b> <%
 	} else if (marks >= 50) {
 %> <b>C++</b> <%
 	} else if (marks >= 40) {
 %> <b>C</b> <%
 	} else if (marks >= 35) {
 %> <b>D</b> <%
 	} else if (marks >= 0) {
 %> <font color="red"><b>F</b></font> <%
 	}
 %>
					</td>

				</tr>
				<tr>
					<th id="po1" colspan="1">Total</th>
					<td align="center">300</td>

					<td></td>
					<td id="po1" align="center" colspan="1">
						<%
							int total = ((bean.getMaths()) + (bean.getPhysics()) + (bean.getChemistry()));
								float percentage = (total * 100) / 300;
						%><%=total%></td>
						<td></td>
				</tr>
				<%
					if ((bean.getMaths() > 35) && (bean.getPhysics() > 35) && (bean.getChemistry() > 35)) {
				%>
				<tr>

					<th id="po1" colspan="2">Percentage</th>

					<td id="po1" align="center" colspan="3"><%=percentage%>%</td>
				</tr>
				<tr>
					<th id="po1" align="center" colspan="2"><font color="blue">Grade:</font>
						<%
							if (percentage >= 90) {
						%> <b>A++</b> <%
 	} else if (percentage >= 80) {
 %> <b>A</b> <%
 	} else if (percentage >= 70) {
 %> <b>B++</b> <%
 	} else if (percentage >= 60) {
 %> <b>B</b> <%
 	} else if (percentage >= 50) {
 %> <b>C++</b> <%
 	} else if (percentage >= 40) {
 %> <b>C</b> <%
 	} else if (percentage >= 35) {
 %> <b>D</b> <%
 	} else if (percentage >= 0) {
 %> <font color="red"><b>F</b></font> <%
 	}
 %></th>
					<td id="po1" align="center" colspan="3">
						<%
							if (percentage >= 35) {
						%> <font color="green"><b>PASS</b></font> <%
 	}
 %>
					</td>
				</tr>
				<%
					} else {
				%>
				<tr>
					<th id="po1" align="center" colspan="2"><font color="blue">Grade:</font><font
						color="red"><b>F</b></font></th>
					<td id="po1" align="center" colspan="3"><font color="red"><b>FAIL</b></font></td>
				</tr>

				<%
					}
				%>
			</table>

			<%
				}

			%>			

</form>
</center>

  <br>
<br>
<br>
<%@include file="Footer.jsp"%> 

</body>
</html>