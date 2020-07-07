<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="meta.jsp" %>  
	<title>Authentication</title> 
</head>

<body>

<%@ include file="navbar.jsp" %>
<%@ include file="header.jsp" %>

<main class="container p-5 d-flex justify-content-center">
<form action="/Authenticator/authlogin" method="post" class="needs-validation" novalidate>
        <div class="form-group">
            <h3>2-Step Verification</h3>
            <p>This extra step shows its really you trying to sign in as 
            ${username}</p>
        </div>
        <div class="form-group">
            <p>Get a verication code from the Google Authenticator app</p>
            <input class="form-control" name="code" placeholder="code" type="number" required>
            <div class="invalid-feedback">Empty password field</div>
        </div>
        <button class="btn btn-primary" type="submit">Verify</button>
    </form>

    <script>
        (function() {
          'use strict';
            window.addEventListener('load', function() {
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            var forms = document.getElementsByClassName('needs-validation');
            // Loop over them and prevent submission
            var validation = Array.prototype.filter.call(forms, function(form) {
              form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                  event.preventDefault();
                  event.stopPropagation();
                }
                form.classList.add('was-validated');
              }, false);
            });
          }, false);
        })();
</main>
</body>

</html>