<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="meta.jsp" %>  
	<title>2-Step Authentication</title> 
</head>

<body>

<%@ include file="navbar.jsp" %>
<%@ include file="header.jsp" %>

<main class="container p-5 d-flex justify-content-center">
<div class="container">
    <h1>You're Set up</h1>
    <h3>You will need to enter code form Authenticator App every time you signed in.</h3>
    <br>
    <form action="/Authenticator/twoStepAuthSet" method="post">
        <button type="submit" class="btn btn-secondary" name="submit_button" value="change_phone">Change Phone</button>
        <button type="submit" class="btn btn-danger"name="submit_button" value="delete">Delete</button>
    </form>
</div>
</main>
</body>

</html>