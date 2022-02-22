<%@page import="in.co.sunrays.ctl.FacultyCtl"%>
<%@page import="in.co.sunrays.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.util.DataUtility"%>
<%@page import="in.co.sunrays.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty View</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style>
.p1 {
	font-size: 18px;
}

.p2 {
	padding: 5px;
	margin: 3px;
}

.footer {
	position: relative;
	left: 0;
	bottom: 0;
	width: 100%;
	text-align: center;
}
</style>
<script>
	
	$(function() {
		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1970:2030',
			dateFormat : 'dd/mm/yy',
			endDate : '-18y',
		});
	});
	
</script>
<form action=<%=ORSView.FACULTY_CTL%> method="POST">
		<%@include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.co.sunrays.bean.FacultyBean"
			scope="request"></jsp:useBean>
		<center>
			<%
				if (bean.getId() > 0) {
			%>
			<h1 style="font-size: 40px;">Update Faculty</h1>
			<%
				} else {
			%>
			<h1 style="font-size: 40px;">Add Faculty</h1>
			<%
				}
			%>
			<h2>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h2>
			<h2>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h2>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> 
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
			<input type="hidden" name="createdDatetime"	value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime())%>">
			<input type="hidden" name="modifiedDatetime"value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">
			<%
				List ll = (List) request.getAttribute("collegeList");
				List lli = (List) request.getAttribute("courseList");
				List llist = (List) request.getAttribute("subjectList");
			%>
	<table>
		<tr>
		   <th align="left" class="p1">First Name<span style="color: red;">*</span></th>
			  <td><input type="text" name="firstName" class="p2" size="40"
						placeholder="Enter First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>

				<tr>
					<th align="left" class="p1">Last Name<span style="color: red;">*</span></th>

					<td><input type="text" name="lastName" class="p2" size="40"
						placeholder="Enter Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>

				<tr>
					<th align="left" class="p1">Joining Date<span
						style="color: red;">*</span></th>
					<td><input type="text" readonly="readonly" name="joiningDate" class="p2"
						id="datepicker" size="40" placeholder="Enter Joining Date"
						value="<%=DataUtility.getDateString(bean.getDateofjoining())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("joiningDate", request)%></font></td>

				</tr>
				<tr>
					<th align="left" class="p1">Qualification<span
						style="color: red;">*</span></th>
					<td><input type="text" name="qualification" class="p2"
						size="40" placeholder="Enter Qualification"
						value="<%=DataUtility.getStringData(bean.getQualification())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("qualification", request)%></font></td>
				</tr>
				<tr>
					<th align="left" class="p1">Mobile No<span style="color: red;">*</span></th>
					<td><input type="text" name="mobile"  maxlength="10" size="40" class="p2"
						placeholder="Enter Mobile No"
						value="<%=DataUtility.getStringData(bean.getMobileno())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("mobile", request)%></font></td>
				</tr>
				<tr>
					<th align="left" class="p1">Login ID<span style="color: red;">*</span></th>
					<td><input type="text" name="login" size="40" class="p2"
						placeholder="Enter Login ID"
						value="<%=DataUtility.getStringData(bean.getLoginId())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				 <tr>
					<th align="left" class="p1">Gender<span style="color: red;">*</span></th>
					<td>
						<%
							HashMap map = new HashMap();
					map.put("", "----------------------------Select------------------------------");
							map.put("Male", "Male");
							map.put("Female","Female");
							String HtmlList = HTMLUtility.getList("gender", bean.getGender(), map);
						%><%=HtmlList%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left" class="p1">College Name<span
						style="color: red;">*</span></th>
					<td><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeid()), ll)%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("collegeId", request)%></font></td>
				</tr>
				<tr>
					<th align="left" class="p1">Course Name<span
						style="color: red;">*</span></th>
					<td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), lli)%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("courseId", request)%></font></td>
				</tr> 

				<tr>
					<th align="left" class="p1">Subject Name<span
						style="color: red;">*</span></th>
					<td><%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), llist)%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("subjectId", request)%></font></td>
				</tr>
				
				<%-- <label class="p1">College Name</label><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), ll)%>&emsp;
				
				
				<tr><label class="p1">Course Name</label><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), lli)%>
				</tr> --%>
				<%
					if (bean.getId() > 0) {
				%>
				<tr>
					<td></td>
					<td colspan="1" align="center"><input type="submit"
						name="operation" style="padding: 5px;"
						value="<%=FacultyCtl.OP_UPDATE%>"> &emsp;<input
						type="submit" name="operation" style="padding: 5px;"
						value="<%=FacultyCtl.OP_CANCEL%>"></td>
				</tr>
				<%
					} else {
				%>
				<tr>
					<td></td>
					<td colspan="1" align="center"><input type="submit"
						name="operation" value="<%=FacultyCtl.OP_SAVE%>"
						style="padding: 5px;"> &emsp;<input type="submit"
						name="operation" value="<%=FacultyCtl.OP_RESET%>"
						style="padding: 5px;"></td>
				</tr>

				<%
					}
				%>
			</table>
		</center>
	</form>
</body>
<!-- <footer class="footer">
<div class="footer">  -->
	
	<center>

		
	</center>
<%@include file="Footer.jsp"%> 

</body>
</html>