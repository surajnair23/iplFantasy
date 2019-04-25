<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>IPL Fantasy</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
	  $("button").click(function(e){
		  	e.preventDefault();
			var option = $(e.target).text();
			var rownum = $(e.target).attr("id");
			console.log(option+"skugfeug"+rownum);
			var rand = Math.floor(Math.random() * 101);
			if(option === 'Approve'){
				var locaURL = "/ipl/userapprove.htm?id="+rand+"&userid="+rownum;
				console.log(rand);
				}else{
					var locaURL = "/ipl/userdelete.htm?id="+rand+"&userid="+rownum;
					}
			$.ajax({
				type:"GET",
				url: locaURL,
				complete: function(result){
	    			alert("Request processed!");
	    			window.location.href=window.location.href;
	  		}});
		});
	  });
  </script>
</head>
<body>
<c:choose>
	<c:when test="${sessionScope.admin != null}">
			<jsp:include page="adminNav.jsp"></jsp:include>
			<div id="workspace" class="container">
				<br><br>
				<h1>Please approve the below users:</h1><br><br>
				
				<sql:setDataSource var = "snapshot" driver = "com.mysql.cj.jdbc.Driver"
         			url = "jdbc:mysql://localhost:3306/ipl"
         			user = "root"  password = ""/>

		         <sql:query dataSource = "${snapshot}" var = "users">
		            SELECT * from user;
		         </sql:query>
		         <c:if test="${users != null}">	
		         <c:set var = "count" scope = "request" value = "${0}"/>  
		         <table class="table table-bordered">
		         	<tr><h2>
		         		<th>Selection</th>
		         		<th>User name</th>
		         		<th>Email</th>
		         		<th>Created date</th>
		         		<th>Phone</th></h2>
		         	</tr>
		         	<c:forEach var = "user" items = "${users.rows}">
		         	<c:choose>
		         	<c:when test="${user.isApproved}">
		         	</c:when>
		         	<c:otherwise>
		         		<c:set var="count" value="${count+1}"/>
		         		<tr>
		         		<td>
		         			<button id="${user.userId}" class="btn btn-success">Approve</button>
		         			<button id="${user.userId}" class="btn btn-danger">Delete</button>
		         		</td>
		         		<td><c:out value = "${user.username}"/></td>
		         		<td><c:out value = "${user.email}"/></td>
		         		<td><c:out value = "${user.createdDate}"/></td>
		         		<td><c:out value = "${user.phone}"/></td>
			         	</tr>
			         </c:otherwise>	
			         </c:choose>	
			         </c:forEach>
		         </table>
		         <c:if test="${count == 0}">
		         	<h3>No users currently to manage</h3>
		         </c:if>
		         </c:if>
			</div>
	</c:when>
	<c:otherwise>
		<jsp:include page="404PageNotFound.jsp"></jsp:include>
	</c:otherwise>
</c:choose>
<body>

</body>
</html>