<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
  </head>
<body>
	<c:choose>
	<c:when test="${sessionScope.admin != null}">
		<jsp:include page="adminNav.jsp"></jsp:include>
		<div class="container">
			<c:if test="${points == null}">
			<!-- Means the points are not yet updated, so form -->
			<form action="scoreboard.htm" method="POST">
				<div class="col-sm-6">
				<h2>Team 1:</h2><br>
				<h2><c:out value="${fixture.getTeam1().getTeamName()}"/></h2><br>
				<c:forEach var="pts" items="${fillout}">
					<c:if test="${pts.getTeam().getTeamId() == fixture.getTeam1().getTeamId()}">
						<c:out value="${pts.getPlayer().getPlayerName()}"/>
						<input class="form-control" type="number" name="${pts.getPlayer().getPlayerId()}"/> 
					</c:if>
				</c:forEach>
			</div>
			<div class="col-sm-6">
				<h2>Team 2:</h2><br>
				<h2><c:out value="${fixture.getTeam2().getTeamName()}"/></h2><br>
				<c:forEach var="pts" items="${fillout}">
					<c:if test="${pts.getTeam().getTeamId() == fixture.getTeam2().getTeamId()}">
						<c:out value="${pts.getPlayer().getPlayerName()}"/>
						<input class="form-control" type="number" name="${pts.getPlayer().getPlayerId()}"/>
					</c:if>
				</c:forEach>
			</div>
			<div class="col-sm-12">
				<button type="submit">Submit</button>
			</div>
			</form>
			
			</c:if>
			<c:if test="${points != null}">
			<!-- Points are there, just display them -->
			<div class="col-sm-6">
				<h2>Team 1:</h2><br>
				<h2><c:out value="${fixture.getTeam1().getTeamName()}"/></h2><br>
				<c:forEach var="pts" items="${points}">
					<c:if test="${pts.getTeam().getTeamId() == fixture.getTeam1().getTeamId()}">
						<c:out value="${pts.getPlayer().getPlayerName()}"/> - <c:out value="${pts.getPoints()}"/>pts<br>
					</c:if>
				</c:forEach>
			</div>
			<div class="col-sm-6">
				<h2>Team 2:</h2><br>
				<h2><c:out value="${fixture.getTeam2().getTeamName()}"/></h2><br>
				<c:forEach var="pts" items="${points}">
					<c:if test="${pts.getTeam().getTeamId() == fixture.getTeam2().getTeamId()}">
						<c:out value="${pts.getPlayer().getPlayerName()}"/> - <c:out value="${pts.getPoints()}"/>pts<br>
					</c:if>
				</c:forEach>
			</div>
			</c:if>
			<br><br><br>
		</div>
	</c:when>
	<c:otherwise>
		<jsp:include page="404PageNotFound.jsp"></jsp:include>
	</c:otherwise>
	</c:choose>
</body>
</html>