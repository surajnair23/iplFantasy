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
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
  <script defer src="https://use.fontawesome.com/releases/v5.8.1/js/all.js" integrity="sha384-g5uSoOSBd7KkhAMlnQILrecXvzst9TdC09/VM+pjDTCM+1il8RHz5fKANTFFb+gQ" crossorigin="anonymous"></script>
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
		         <form method="POST">
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
		         		<c:out value="${count}"/>
		         		<tr>
		         		<td>
		         			<input type="checkbox" value="${user.userId}" name="userIds"/>
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
		         <c:if test="${count > 0}">
		         	<button formaction="userapprove.htm" class="btn btn-success">Approve</button>
		         	<button formaction="userdelete.htm" class="btn btn-danger">Delete</button>
		         </c:if>
		         <c:if test="${count == 0}">
		         	<h3>No users currently to manage</h3>
		         </c:if>
		         </form>
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