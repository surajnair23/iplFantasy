<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/mainPage.htm">IPL User</a>
		</div>
		<ul class="nav navbar-nav">
		<li class="active"><a href="${pageContext.request.contextPath}/mainPage.htm">Home</a></li>
		<li><a href="${pageContext.request.contextPath}/user/match.htm">Matches</a></li>
		<li><a href="${pageContext.request.contextPath}/user/myselection.htm">MyTeams</a></li>
		
		<li class="dropdown">
			<a class="dropdown-toggle" data-toggle="dropdown" href="mainPage.htm">Dashboard
			<span class="caret"></span></a>
			<ul class="dropdown-menu">
			<li>
				<a href="${pageContext.request.contextPath}/signout.htm"><i class="fas fa-sign-out-alt"></i> &nbsp; Sign out</a>
			</li>
			</ul>
			</li>
		</ul>
	</div>
</nav>