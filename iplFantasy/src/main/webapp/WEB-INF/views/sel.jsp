<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>IPL Fantasy</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  <script type="text/javascript">
	$(document).ready(function(){

		$("#rc").click(function(){
				$("input:checkbox").removeAttr("disabled");
				$("input:checkbox").prop('checked', false);
			});
		
		$("input:checkbox").click(function(){
		    if ($("input:checkbox:checked").length > 5){
				  alert("only allowed to select 5 players");
				  $("input:checkbox").attr("disabled", true);
			      return false;
		   }else{
			   
			}
		});

		//$("button").click(function(e){
			//e.preventDefault();
			//if ($("input:checkbox:checked").length < 5){
			//	alert("You shoould select atleast 5 players");
			//}else{
			//	$(form).submit();
			//	}
			//});
		});
  </script>
</head>
<body>
 <c:choose>
	<c:when test="${sessionScope.user != null}">
			<jsp:include page="userNav.jsp"></jsp:include>
			<div class="container">
				<div class="col-sm-12">
					<c:if test="${sessionScope.error != null}">
						<p><c:out value="${sessionScope.error}"/></h2></p>
					</c:if>
					<h1>Match Number:<c:out value="${teamsel.getMatchId()}"/></h1>
					<br><br>
					<H1>Make a pick of your 5 players</H1><button id="rc" class="btn btn-success">Re pick</button>
					<form method="POST" action="teamselection.htm" class="form-group">
					<div class="col-sm-6">
						<h2>Team:<c:out value="${teamsel.getTeam1().getTeamName()}"/>(<c:out value="${teamsel.getTeam1().getTeamAbv()}"/>)</h2>
						<!-- Teams -->
						<input type="hidden" name="matchId" value="${teamsel.getMatchId()}"/>
						<c:if test="${pTeam1 != null}">
							<h4>
							<input type="hidden" name="matchid" value="${teamsel.getMatchId()}"/>
							<input type="hidden" name="team1id" value="${teamsel.getTeam1().getTeamId()}"/>
							<input type="hidden" name="team2id" value="${teamsel.getTeam2().getTeamId()}"/>
							<table>
							<c:forEach var="player" items="${pTeam1}">
								<tr><td><input type="checkbox" name="playerid" value="${player.getPlayerId()}" ></td>
									<td><c:out value="${player.getPlayerName()}"/> - </td>
									<td><c:out value="${player.getPlayerType()}"/> </td>
									<td><c:if test="${player.isPlayerCaptain()}"><b>(Cap)</b></c:if>
									<c:if test="${player.isPlayerKeeper()}"><b>(Wk)</b></c:if></td>
								</tr>
							</c:forEach>
							</table>
							</h4>
						</c:if>
					</div>
					<div  class="col-sm-6">
						<h2>Team:<c:out value="${teamsel.getTeam2().getTeamName()}"/>(<c:out value="${teamsel.getTeam2().getTeamAbv()}"/>)</h2>
						<!-- Teams -->
						<c:if test="${pTeam2 != null}">
							<h4>
							<table>
							<c:forEach var="player" items="${pTeam2}">
								<tr><td><input type="checkbox" name="playerid" value="${player.getPlayerId()}" >
									<td><c:out value="${player.getPlayerName()}"/> - </td>
									<td><c:out value="${player.getPlayerType()}"/> </td>
									<td><c:if test="${player.isPlayerCaptain()}"><b>(Cap)</b></c:if>
									<c:if test="${player.isPlayerKeeper()}"><b>(Wk)</b></c:if></td>
								</tr>
							</c:forEach>
							</table>
							</h4>
						</c:if>
					</div>
					<div class="col-sm-12">
						<h3>Proceed to creating your team!</h3>
						<h3>Can you predict the winner:</h3>
						<div class="col-sm-12">
							<div class="col-sm-6">
							<select name="winner" class="form-control" required>
							<option value="${teamsel.getTeam1().getTeamId()}">${teamsel.getTeam1().getTeamName()}</option>
							<option value="${teamsel.getTeam2().getTeamId()}">${teamsel.getTeam2().getTeamName()}</option>
							</select>
							</div>
								<button class="btn btn-warning">Play!</button>
								<a href="${pageContext.request.contextPath}/user/match.htm" class="btn btn-primary">Pick another match</a>
						</div>
					</div>
					<br><br><br>
					</form>
				</div>
			</div>
	</c:when>
	<c:otherwise>
		<jsp:include page="404PageNotFound.jsp"></jsp:include>
	</c:otherwise>
</c:choose>
</body>
</html>