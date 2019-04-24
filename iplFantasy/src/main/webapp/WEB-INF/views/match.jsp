<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
	<c:when test="${sessionScope.user != null}">
			<jsp:include page="userNav.jsp"></jsp:include>
			<br>
			<div class="container">
			<h1>Matches:</h1><br><br>
			
			<h2>Open Matches:</h2>
			<table class="table table-bordered">
			<tr>
				<td>Match</td><td>Day</td><td>Venue</td><td>Make Team</td>
			</tr>
		    <c:forEach var="mat" items="${fixtures}">
		   	<c:if test="${mat.getWinTeam() == null}">
		   		<tr>
		   		<td><c:out value="${mat.getTeam1().getTeamName()}"/>(<c:out value="${mat.getTeam1().getTeamAbv()}"/>) VS
		   		<c:out value="${mat.getTeam2().getTeamName()}"/>(<c:out value="${mat.getTeam2().getTeamAbv()}"/>)</td>
		   		<td><c:out value="${mat.getMatchDate()}"/></td>
		   		<td><c:out value="${mat.getVenue()}"/></td><c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 100) %></c:set>
		   		<td><a href="${pageContext.request.contextPath}/user/teamselection.htm?matid='<c:out value="${mat.getMatchId()}"/>'" class="btn btn-success">Make a Team</button>
		   		</td>
		   		</tr>
		   	</c:if>	
		    </c:forEach>
		    </table>
		    
		    <h2>Closed Matches:</h2>
		    <table class="table table-bordered">
			<tr>
				<td>Match</td><td>Day</td><td>Venue</td><td>Winner</td>
			</tr>
		    <c:forEach var="mat" items="${fixtures}">
		   	<c:if test="${mat.getWinTeam() != null}">
		   		<tr>
		   		<td><c:out value="${mat.getTeam1().getTeamName()}"/>(<c:out value="${mat.getTeam1().getTeamAbv()}"/>) VS
		   		<c:out value="${mat.getTeam2().getTeamName()}"/>(<c:out value="${mat.getTeam2().getTeamAbv()}"/>)</td>
		   		<td><c:out value="${mat.getMatchDate()}"/></td>
		   		<td><c:out value="${mat.getVenue()}"/></td>
		   		<td><c:out value="${mat.getWinTeam().getTeamName()}"/></td>
		   		</tr>
		   	</c:if>	
		    </c:forEach>
		    </table>
			</div>
	</c:when>
	<c:otherwise>
		<jsp:include page="404PageNotFound.jsp"></jsp:include>
	</c:otherwise>
</c:choose>
</body>
</html>