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
	<c:when test="${sessionScope.user != null}">
		<jsp:include page="userNav.jsp"></jsp:include>
		<div class="container">
		Go Back<a href="${pageContext.request.contextPath}/user/match.htm">Matches</a>
		<br><br>
		<div class="col-sm-12">
			<c:if test="${points != null}">
				<c:forEach var="pt" items="${points}">
					<div class="well well-lg">
						<br><c:out value="${pt.getFixture().getTeam1().getTeamName()}"/>
							(<c:out value="${pt.getFixture().getTeam1().getTeamAbv()}"/>) vs
						    <c:out value="${pt.getFixture().getTeam2().getTeamName()}"/>
						    (<c:out value="${pt.getFixture().getTeam2().getTeamAbv()}"/>)<br>
						<c:if test="${null != pt.getPlayerpoints()}">
							<!-- Player points exists -->
							<c:forEach var="pset" items="${pt.getPlayerpoints()}">
								<c:out value="${pset.getPlayer().getPlayerName()}"/> -
								<c:out value="${pset.getPoints()}"/> 
								<c:set var="total" value="${total+pset.getPoints()}"/>
							</c:forEach>
							<h3>Your total points:</h3><c:out value="${total}"/>
						</c:if>
						<c:if test="${null == pt.getPlayerpoints()}">
						<!-- Player points do not exists -->
							<c:out value="${pt.getPlayers().size()}"></c:out>
							<c:forEach var="plyset" items="${pt.getPlayers()}">
								<c:out value="${plyset.getPlayerName()}"/><br>
							</c:forEach>
						</c:if>
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${points == null}">
				<br><br><br><h3>You have not played any games so far!</h3>
			</c:if>
		</div>
	</div>
	</c:when>
	<c:otherwise>
		<jsp:include page="404PageNotFound.jsp"></jsp:include>
	</c:otherwise>
</c:choose>
</body>
</html>