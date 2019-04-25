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
  </head>
<body>
	<c:choose>
	<c:when test="${sessionScope.admin != null}">
		<jsp:include page="adminNav.jsp"></jsp:include>
		<div class="container">
			<h1>Please update the player points based on rules as below:</h1>
			<a href="#" class="btn btn-primary">Rules.pdf</a>
			<br><br>
			<c:out value="${fixture.getTeam1().getTeamName()}"/>(<c:out value="${fixture.getTeam1().getTeamAbv()}"/>) VS
			<c:out value="${fixture.getTeam2().getTeamName()}"/>(<c:out value="${fixture.getTeam2().getTeamAbv()}"/>)
			<div class="col-sm-6">
				<table>
				<c:forEach var="player" items="${team1}">
				<tr><td><input type="checkbox" name="playerid" value="${player.getPlayerId()}" ></td>
					<td><c:out value="${player.getPlayerName()}"/> - </td>
					<td><c:out value="${player.getPlayerType()}"/> </td>
					<td><c:if test="${player.isPlayerCaptain()}"><b>(Cap)</b></c:if>
					<c:if test="${player.isPlayerKeeper()}"><b>(Wk)</b></c:if></td></tr>
				</c:forEach>
				</table>
							
			</div>
			<div class="col-sm-6"></div>
		</div>
	</c:when>
	<c:otherwise>
		<jsp:include page="404PageNotFound.jsp"></jsp:include>
	</c:otherwise>
	</c:choose>
</body>
</html>