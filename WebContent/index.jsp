<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="meta.jsp" %>  
	<title>Hello</title> 
</head>

<body>

<%@ include file="navbar.jsp" %>
<%@ include file="header.jsp" %>

<main class="container p-5 justify-content-center">
<div class="jumbotron">
  <div class="container">
    <h1 class="display-4">Welcome, ${username}!</h1>
    <p class="lead">You have successfully signed in to your account.</p>
  </div>
</div>

</main>
</body>

</html>