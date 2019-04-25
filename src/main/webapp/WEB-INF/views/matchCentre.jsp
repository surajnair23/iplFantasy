<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />

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
		$("#showForm").hide();
		$("#showDiv").click(function(){
			  $("#showForm").show();
			  $("p").hide();
			});
		$("button").click(function(){
			var matid = this.id;
			var winteam = $(this).siblings("select").val();
			var sendData = {
					"matid" : matid,
					"winteam" : winteam
					}
			console.log(matid+winteam);
			var rand = Math.floor(Math.random() * 101);
			var givenutl="/ipl/matchupdate.htm?id="+rand+"&matid="+matid+"&winteam="+winteam;
			$.ajax({
				type:"GET",
				url: givenutl,
				complete: function(result){
	    			alert("Winner is updated,refresh to update");
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
				<div id="matchadd" class="col-sm-12">
				<!-- fetch all the teams -->
				<sql:setDataSource var = "snapshot" driver = "com.mysql.cj.jdbc.Driver" 
					url = "jdbc:mysql://localhost:3306/ipl" user = "root"  password = ""/>
				<sql:query dataSource = "${snapshot}" var = "teams">
				    SELECT teamName, teamId from Team;
				</sql:query>
				<!-- dynamically create the input flds -->
				<c:if test="${teams != null}">
					<!-- dynamically show form -->
					<a class="btn btn-primary" id="showDiv">Add match</a><br>
					<c:if test="${requestScope.success != null}">
    					<p><c:out value="${requestScope.success}"></c:out></p>
    				</c:if>
					<div id="showForm" class="col-sm-12">
						<form action="matchCentre.htm" method="POST">
							<div id="tesmSel1" class="col-sm-6">
							<h1>Team 1</h1>
							<c:forEach var = "team" items = "${teams.rows}">
								<input name="team1" required type="radio" value="${team.teamName}" id="${team.teamId}">${team.teamName}
							</c:forEach>
							</div>
							<div id="tesmSel2" class="col-sm-6">
							<h1>Team 2</h1>
							<c:forEach var = "team" items = "${teams.rows}">
								<input name="team2" required type="radio" value="${team.teamName}" id="${team.teamId}">${team.teamName}
							</c:forEach>
							<br><br>
							</div>
							<div class="col-sm-6">
						    <select class="form-control" name="city" required>
						            <option>Mumbai</option>
						            <option>Delhi</option>
						            <option>Jaipur</option>
						            <option>Mohali</option>
						            <option>Ranchi</option>
						            <option>Kolkata</option>
						            <option>Bangalore</option>
						            <option>Hyderabad</option>
						        </select>
						        <br>
						    </div>
						    <fmt:formatDate var="day" value="${now}" pattern="yyyy-MM-dd" />
						    <div class="col-sm-6">
						    	<input class="form-control" type="date" name="currdate" value='<c:out value="${day}"/>'
						    		   min='<c:out value="${day}"/>' max="2019-04-30"/>
						    </div>
						    <div class="col-sm-4">
						    <br><br>
						    <input type="submit" id="matchaddbtn" class="btn btn-success" value="Create Match"/>
						    </div>
						</form>
					</div>
				</c:if>
				</div>
				<hr>
				<div id="showMatch">
					<h1>Scheduled Matches:</h1>
					<c:if test="${fixtures != null}">
						<table class="table table-bordered">
						<tr>
							<td>Match Id</td><td>Match Date</td><td>Team1</td><td>Team2</td><td>Venue</td><td>Winner?</td>
						</tr>
					
						<c:forEach var = "fixture" items = "${fixtures}">
						<tr>
							<td>
								<c:out value="${fixture.getMatchId()}"/>
							</td>
							<td>
								<c:out value="${fixture.getMatchDate()}"/>
							</td>
							<td>
								<c:out value="${fixture.getTeam1().getTeamName()}"/>(<b><c:out value="${fixture.getTeam1().getTeamAbv()}"/></b>)
							</td>
							<td>
								<c:out value="${fixture.getTeam2().getTeamName()}"/>(<b><c:out value="${fixture.getTeam2().getTeamAbv()}"/></b>)
							</td>
							<td>
								<c:out value="${fixture.getVenue()}"/>
							</td>
							<td>
								<c:if test="${fixture.getWinTeam() == null}">
									<div id="${fixture.getMatchId()}">
										<input type="hidden" value="${fixture.getMatchId()}" name="matchId"/>
											<select required name="winner" class="form-control" id="winnersel">
												<option value="${fixture.getTeam1().getTeamId()}">${fixture.getTeam1().getTeamName()}</option>
												<option value="${fixture.getTeam2().getTeamId()}">${fixture.getTeam2().getTeamName()}</option>
											</select>
										<button class="btn btn-warning" id="${fixture.getMatchId()}">Update</button>
									</div>	
								</c:if>
								<c:if test="${fixture.getWinTeam() != null}">
									<c:out value="${fixture.getWinTeam().getTeamName()}"/>
									<a href="${pageContext.request.contextPath}/scoreboard.htm?matid='<c:out value="${fixture.getMatchId()}"/>'" class="btn btn-success">Player scores</a>
								</c:if>
							</td>
						</tr>
						</c:forEach>
					</table>
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