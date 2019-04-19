<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>IPL Fantasy</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
<header>
	//NAV BAR
</header>
<div id="myCarousel" class="carousel slide" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
      <li data-target="#myCarousel" data-slide-to="1"></li>
      <li data-target="#myCarousel" data-slide-to="2"></li>
    </ol>

    <!-- Wrapper for slides -->
    <div class="carousel-inner">
      <div class="item active">
        <img src="<c:url value="/resources/assets/ipl1.png" />" alt="ipl1" style="width:100%;">
      </div>

      <div class="item">
        <img src="<c:url value="/resources/assets/ipl2.jpg" />" alt="ipl2" style="width:100%;">
      </div>
    
      <div class="item">
        <img src="<c:url value="/resources/assets/ipl3.jpg" />" alt="ipl3" style="width:100%;">
      </div>
    </div>

    <!-- Left and right controls -->
    <a class="left carousel-control" href="#myCarousel" data-slide="prev">
      <span class="glyphicon glyphicon-chevron-left"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#myCarousel" data-slide="next">
      <span class="glyphicon glyphicon-chevron-right"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>
<div id="workspace" style="text-align:center">
	<h1>Lets get started!</h1>
	<a class="btn btn-primary" href="login.htm">Login/Register</a><br>
</div>
<br>
<footer>
	The time on the server is ${serverTime}.
</footer>
</body>
</html>
