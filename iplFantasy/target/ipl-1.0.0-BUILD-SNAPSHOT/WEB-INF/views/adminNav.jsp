<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="mainPage.htm">IPL Admin</a>
		</div>
		<ul class="nav navbar-nav">
		<li class="active"><a href="mainPage.htm">Home</a></li>
		<li class="dropdown">
			<a  class="dropdown-toggle" data-toggle="dropdown" href="${pageContext.request.contextPath}/matchCentre.htm">Match Centre
			<span class="caret"></span></a>
			<ul class="dropdown-menu">
				<li>
					<a href="${pageContext.request.contextPath}/matchCentre.htm"><i class="fas fa-stream"></i> &nbsp; Matches</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath}/addTeam.htm"><i class="fas fa-user-plus"></i> &nbsp; Add Team</a>
				</li>
			</ul>
		</li>
		<li><a href="${pageContext.request.contextPath}/scoreboard.htm">Scoreboard</a></li>
		<li class="dropdown">
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">Dashboard
			<span class="caret"></span></a>
			<ul class="dropdown-menu">
			<li>
				<a href="${pageContext.request.contextPath}/approval.htm"><i class="fa fa-users" aria-hidden="true"></i> &nbsp; Approve users</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/signout.htm"><i class="fas fa-sign-out-alt"></i> &nbsp; Sign out</a>
			</li>
			</ul>
		</li>
		</ul>
	</div>
</nav>