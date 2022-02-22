<%@page import="in.co.sunrays.ctl.MyProfileCtl" %>
<%@page import="in.co.sunrays.util.HTMLUtility" %>
<%@page import="in.co.sunrays.util.DataUtility" %>
<%@page import="in.co.sunrays.util.ServletUtility" %>
<%@page import="java.util.HashMap" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MY Profile</title>
 <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>

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
<form action="<%=ORSView.MY_PROFILE_CTL%>"method="post">
<%@include file="Header.jsp" %>
<script type="text/javascript" src="../jss/calendar.js"></script>
<jsp:useBean id="bean" class="in.co.sunrays.bean.UserBean" scope="request"></jsp:useBean>

<center>
  <h1>My Profile</h1>
  <h2>
     <font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
     
  </h2>
  <input type="hidden" name="id" value="<%=bean.getId()%>">
  <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
  <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
  <input type="hidden" name="createdDateTime" value="<%=bean.getCreatedDateTime()%>">
  <input type="hidden" name="modifiedDateTime" value="<%=bean.getModifiedDateTime()%>">
 
 <table>
 <tr>
    <th align="left" class="p1">LoginId<span style="color:red">*</span></th>
    <td><input type="text" name="login" placeholder="Enter LoginId" size="40" class="p2" value="<%=DataUtility.getStringData(bean.getLogin()) %>"readonly="readonly"></td>
   <td> <font color="red"><%=ServletUtility.getErrorMessage("login", request) %></font></td>
    </tr>
  <tr>
  <th align="left" class="p1">FirstName<span style="color:red">*</span></th>
    <td><input type="text" name="firstName" placeholder="Enter First Name" size="40" class="p2" value="<%=DataUtility.getStringData(bean.getFirstName()) %>"></td>
   <td> <font color="red"><%=ServletUtility.getErrorMessage("firstName", request) %></font></td>
    </tr>  
    <tr>
    <th align="left" class="p1">LastName<span style="color:red">*</span></th>
    <td><input type="text" name="lastName" placeholder="Enter Last Name" size="40" class="p2" value="<%=DataUtility.getStringData(bean.getLastName()) %>"></td>
  <td>  <font color="red"><%=ServletUtility.getErrorMessage("lastName", request) %></font></td>
    </tr>
    <%-- <tr>
      <th align="left" class="p1">Gender<span style="color:red">*</span></th>
      <td>
        <%
          HashMap map=new HashMap();
        map.put("Male","Male");
        map.put("Female","Female");
        
        String htmlList=HTMLUtility.getList("gender", bean.getGender(), map);
        
        %> <%=htmlList %>
        </td>
        </tr> --%>
        <tr>
		<th align="left" class="p1">Gender<span style="color: red;">*</span></th>
		<td><input type="text" name="gender" class="p2" size="40"
		 placeholder="Enter Gender" value="<%=DataUtility.getStringData(bean.getGender())%>"></td>
		<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font></td>
				</tr>
        <tr>
          <th align="left" class="p1">MobileNo<span style="color:red">*</span></th>
    <td><input type="text" name="mobileNo" placeholder="Enter MobileNo" size="40" class="p2" value="<%=DataUtility.getStringData(bean.getMobileNo()) %>"></td>
   <td> <font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request) %></font></td>
    </tr>
    <tr>
      <th align="left" class="p1">Date Of Birth<span style="color:red">*</span></th>
    <td><input type="text" id="datepicker" name="dob" readonly="readonly" size="40" class="p2" value="<%=DataUtility.getDateString(bean.getDob()) %>"></td>
 
 <td>   <font color="red"><%=ServletUtility.getErrorMessage("dob", request) %></font></td>
    </tr>
    <h2>
    <font color="green"><%=ServletUtility.getSuccessMessage(request) %></font>
    </h2>
    
    <tr>
      <th></th>
      <td colspan="2"><input type="submit" name="operation" value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD%>">&nbsp;
      <input type="submit" name="operation" value="<%=MyProfileCtl.OP_SAVE%>">&nbsp;</td>
	</tr>
	
 </table>
 
 </center>
 </form>
<%@include file="Footer.jsp"%> 
</body>
</html>