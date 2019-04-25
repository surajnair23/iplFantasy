<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register league</title>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/register.css" />">
</head>
<body>
 <div class="card bg-light">
<article class="card-body mx-auto" style="max-width: 400px;">
	<h4 class="card-title mt-3 text-center">Create Account</h4>
	<p class="text-center">Get started with your free account</p>
	
	<!-- <form action='register' method='POST'> -->
	<form:form method='POST' commandName="user">
 	<div class="form-group input-group">
		<div class="input-group-prepend">
		    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
		 </div>
        <form:input path="fName" placeholder="First name" class="form-control"/><br>
        <form:input path="lName" class="form-control" placeholder="Last name"/>
        <div>
        	<form:errors path="fName"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	<form:errors path="lName"/>
        </div>
    </div> <!-- form-group// -->
    <div class="form-group input-group">
    	<div class="input-group-prepend">
		    <span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
		 </div>
        <form:input path="email" class="form-control" placeholder="Email address" type="email" style="width:auto"/>
        <div>
        	<span><form:errors path="email"/></span>
        </div>
    </div> <!-- form-group// -->
    <div class="form-group input-group">
    	<div class="input-group-prepend">
		    <span class="input-group-text"> <i class="fa fa-phone"></i> </span>
		</div>
    	<form:input path="phone" class="form-control" placeholder="Phone number" type="number" style="width:auto"/>
    	<div>
    		<span><form:errors path="phone"/></span>
    	</div>
    </div> <!-- form-group// -->
	<div class="form-group input-group">
	<div class="input-group-prepend">
		 <span class="input-group-text"> <i class="fa fa-id-badge"></i> </span>
	</div>
	<form:input path="username" class="form-control" placeholder="Username" style="width:auto"/>
	<div>
		<form:errors path="username"/>
	</div>
	</div>
    <div class="form-group input-group">
    	<div class="input-group-prepend">
		    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
		</div>
        <form:input path="password" class="form-control" placeholder="Create password" type="password" style="width:auto"/>
        <div>
        	<form:errors path="password"/>
        </div>
    </div> <!-- form-group// -->
                                     
    <div class="form-group">
        <button type="submit" class="btn btn-primary btn-block"> Create Account  </button>
    </div> <!-- form-group// -->      
    <p class="text-center">Have an account? <a href="login.htm">Log In</a> </p> 
    <div id="errorReporting">
    </div>                                                                
</form:form>
</article>
</div> <!-- card.// -->

</div> 
</body>
</html>