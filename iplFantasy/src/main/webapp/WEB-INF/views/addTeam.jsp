<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>IPL Fantasy</title>
<meta name="viewport" content="width=device-width, initial-scale=1" charset="utf-8">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
		$("#addForm").hide();
		$("#showDiv").click(function(){
			  $("#addForm").show();
			  $("p").hide();
			});
  });
  </script>
  <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/team.css" />">
</head>
<body>
	<c:choose>
		<c:when test="${sessionScope.admin != null}">
			<jsp:include page="adminNav.jsp"></jsp:include>
				<div id="workspace" class="container">
    <br><br>
    <div class="col-sm-12">
	<!-- Add Teams here -->	
	<button class="btn btn-primary" id="showDiv">Add a Team</button>
    <br><br><br>
    <c:if test="${requestScope.success != null}">
    	<p style="color:green;"><c:out value="${requestScope.success}"></c:out></p>
    </c:if>
    <c:if test="${requestScope.error != null}">
    	<p style="color:blue;"><c:out value="${requestScope.erro}"></c:out></p>
    </c:if>
    </div>
	<div id="addForm" class="col-sm-6">
        <br>
		<form action="addTeam.htm" method="POST" class="form-group">
			<table class="table table-bordered">
				<tr>
					<td>Team Abbrevation:</td>
					<td colspan=3><input type="text" name="tAbv" maxlength="3" class="form-control" required></td>
				</tr>
				<tr>
					<td>Team Name</td>
					<td colspan=3><input type="text" name="tName" class="form-control" required></td>
				</tr>
				<tr>
					<td>Home Ground</td>
					<td colspan=3><select name="homeGround" class="form-control" required>
							<option>Mumbai</option>
							<option>Hyderabad</option>
							<option>Delhi</option>
							<option>Patiala</option>
							<option>Kolkata</option>
							<option>Chennai</option>
							<option>Bangalore</option>
							<option>Jaipur</option>
						</select>
					</td>
				</tr>
				<tr><td colspan=4><h3>Enter player names</h3></td></tr>
				<tr>
					<td>Player Name:</td>
					<td>Player type:</td>
                    <td>Captain?</td>
                    <td>WicketKeeper</td>
				</tr>
				<c:forEach begin="0" end="10" var="val">
				<tr>
                    <td><input type="text" class="form-control" name="pName" required></td> 
                    <td><select name="tPlayer" class="form-control" required>
                        <option>Bats man</option>
                        <option>Bowler</option>
                        <option>All rounder</option>
                    </select></td>
                    <td>&emsp;&emsp;<input type="radio" name="captain" value="${val+1}" required></td> 
                    <td>&emsp;&emsp;<input type="radio" name="wk" value="${val+1}" required></td> 
                </tr>
                </c:forEach>
            </table>
            <button type="submit" class="btn btn-success">Submit</button>
		</form>
	</div>
	<!-- take team name and all attributes of team -->
	<div id="teamexists" class="col-sm-6">
	<h2>Existing Teams:</h2>
	<sql:setDataSource var = "snapshot" driver = "com.mysql.cj.jdbc.Driver" url = "jdbc:mysql://localhost:3306/ipl" user = "root"  password = ""/>
	<sql:query dataSource = "${snapshot}" var = "teams">
		    SELECT teamName, teamAbv, teamId from Team;
	</sql:query>
		<c:if test="${teams != null}">
			<table>
			<c:forEach var = "team" items = "${teams.rows}">
				<div class="well well-lg">
					<h3>Team:</h3><c:out value="${team.teamAbv}" />
					<br><h3>Name:</h3><c:out value="${team.teamName}"/>,<c:out value="${team.teamId}"/>
					<sql:query dataSource = "${snapshot}" var="players">
						SELECT pName,pType,pCap,pWk from Player where team_teamid = ?
						   <sql:param value = "${team.teamId}" />
					</sql:query>
					<h3>Players:</h3>
					
					<c:forEach var="p" items="${players.rows}">
					<c:out value="${p.pName}"/>(<c:out value="${p.pType}"/>)
					<c:if test="${p.pCap}"><b>(Cap)</b></c:if><c:if test="${p.pWk}"><b>(Wk)</b></c:if>,
					</c:forEach>
				</div>
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