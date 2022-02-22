<%@page import="in.co.sunrays.ctl.RoleListCtl" %>
<%@page import="in.co.sunrays.ctl.BaseCtl" %>
<%@page import="in.co.sunrays.bean.RoleBean" %>
<%@page import="in.co.sunrays.util.ServletUtility" %>
<%@page import="in.co.sunrays.util.HTMLUtility" %>
<%@page import="in.co.sunrays.util.DataUtility" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Role List View</title>
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
</style>
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
	<h1 style="font-size: 40px;">Role List</h1>
	<center>
			<font color="red" size="5px"><%=ServletUtility.getErrorMessage(request)%></font>
		</center>
		<center>
			<font color="green" size="5px"><%=ServletUtility.getSuccessMessage(request)%></font>
		</center>
	
	<form action="<%=ORSView.ROLE_LIST_CTL%>"method="post">
	<jsp:useBean id="bean" class="in.co.sunrays.bean.RoleBean"
				scope="request"></jsp:useBean>
				
				<%
				List list1 = (List)request.getAttribute("roleList");
			    %>
			     <%
	     	int pageNo=ServletUtility.getPageNo(request);
	        int pageSize=ServletUtility.getPageSize(request);
	        int index=((pageNo-1)*pageSize)+1;
	        int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
	        List list=ServletUtility.getList(request);
	        Iterator<RoleBean> it=list.iterator();
	        if (list.size() != 0) {
	      %>
	<table width="100%">
	<tr>
	  <td align="center"><label>Name</label><%=HTMLUtility.getList("name", String.valueOf(bean.getId()), list1)%>
	  &nbsp; <input type="submit" name="operation" value="<%=RoleListCtl.OP_SEARCH%>" style="padding: 5px;">&nbsp;
	  <input type="submit" name="operation"	value="<%=RoleListCtl.OP_RESET%>" style="padding: 5px;"></td>
						
	 
	 </tr>
	 
	</table>
	<table border="1" width="100%">
	  <tr>
	  	<th width="10%"><input type="checkbox" id="select_all"	name="Select"> Select All</th>
	    <th>S.No.</th>
	    <th>Name</th>
	    <th>Description</th>
	    <th>Edit</th>
	   </tr>
	    
	    <%
					while (it.hasNext()) {
							bean = it.next();
				%>
	    
	      <tr>
			<td align="center"><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"
			
			<%if (bean.getId() == RoleBean.ADMIN) {%> <%="disabled"%> <%}%>></td>
			
	        <td align="center"><%=index++%></td>
	        <td align="center"><%=bean.getName()%></td>
	        <td align="center"><%=bean.getDescription() %>
	        <td align="center"><a href="RoleCtl?id=<%=bean.getId() %>"
	        <%if (bean.getId() == RoleBean.ADMIN) {%> onclick="return false;"<%}%>>Edit</a></td>
	       </tr>
	       <%
	        } 
	       %>
	     
	</table>
	<table width="100%">
	<tr>
	<td><input type="submit" name="operation"
						style="padding: 5px;" value="<%=RoleListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
		<td><input type="submit" name="operation" class="button1"
						style="padding: 5px;" value="<%=RoleListCtl.OP_NEW%>"></td>
						<td><input type="submit" name="operation" class="button1"
						style="padding: 5px;" value="<%=RoleListCtl.OP_DELETE%>"></td>				
	  <td align="right"><input type="submit" name="operation"
						style="padding: 5px;" value="<%=RoleListCtl.OP_NEXT%>"
						<%=/* (nextPageSize != 0) ? "" :  */"disabled"%>></td>
	  </tr>
	  <tr></tr>
	</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<input type="submit" name="operation"
				value="<%=RoleListCtl.OP_BACK%>">
			<%
				}
			%>
	<input type="hidden" name="pageNo" value="<%=pageNo%>">
	<input type="hidden" name="pageSize" value="<%=pageSize%>">
	</form>
</center>

</body>
<%@include file="Footer.jsp"%>
</html>