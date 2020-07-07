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

	
    <h2>Get codes from Authenticator App</h2>
    <p>Instead of waiting for text messages, get verification codes for free from Authenticator app. It works even if
        your phone is offline.</p>
    <ul>
        <li>Get the authenticator App from App Store.</li>
        <li>In the app select Set up account</li>
        <li>Choose Scan Barcode</li>
    </ul>
    <img src="/Authenticator/displayqrcode?secretKey=${secretKey}&email=${email}&issuer=${issuer}" class="figure-img img-fluid rounded" alt="QRCode">

    <br>
    
    <h3>Can't scan the barcode?</h3>
    <ol>
        <li>Tap Menu, then Set up account</li>
        <li>Tap Enter provided key.</li>
        <li>Enter your email address and this key</li>
        <input class="form-control" type="text" id="secret" name="secret" value="${secretKey}" disabled>
        <li>Make sure Time Based is turned on, and tap Add to finish.</li>
    </ol>
    <br>
    
    <form action="/Authenticator/twoStepAuthSet" method="post" class="needs-validation" novalidate>
        <div class="form-group">
            <h3>Set up Authenticator</h3>
            <p>Enter the 6-digit form you see in the app</p>
        </div>
        <div class="form-group">
            <input class="form-control" name="code" placeholder="6-digit code" type="number" required>
            <div class="invalid-feedback">Empty field</div>
        </div>
        <button class="btn btn-primary" type="submit">Verify</button>
    </form>
</div>

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
</script>
</main>
</body>

</html>