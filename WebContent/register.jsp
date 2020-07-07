<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="meta.jsp" %>  
	<title>Register</title> 
</head>

<body>
<%@ include file="navbar.jsp" %>
<%@ include file="header.jsp" %>

<main class="container p-5 d-flex justify-content-center">
    <form action="/Authenticator/register" method="post" class="needs-validation" novalidate>
        <div class="form-group">
            <input autocomplete="off" autofocus class="form-control" name="username" placeholder="Username" type="text" id="username">
            <div class="invalid-feedback" id="usernamefeedback"></div>
        </div>
        <div class="form-group">
            <input class="form-control" name="password" placeholder="Password" type="password" id="password" required>
        </div>
        <div class="form-group">
            <input class="form-control" name="confirmation" placeholder="Confirm Password" type="password" id="confirmation" required>
            <div class="invalid-feedback" id="confirmationfeedback"></div>
        </div>
        <button class="btn btn-primary" type="submit">Submit</button>
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

        let username = document.querySelector("#username");

        //check for empty/taken username
        username.onblur = function (){
            if (username.value) {
                $.get('/check?username=' + username.value, function(data) {
                    if (data) {
                        username.className = "form-control is-valid";
                    }
                    else
                    {
                        document.querySelector("#usernamefeedback").innerHTML = "Username Taken"
                        username.className = "form-control is-invalid";
                    }
                });
            } else {
                document.querySelector("#usernamefeedback").innerHTML = ""
                username.className = "form-control is-invalid"
            }
        };

        function delay(callback, ms) {
          var timer = 0;
          return function() {
            var context = this, args = arguments;
            clearTimeout(timer);
            timer = setTimeout(function () {
              callback.apply(context, args);
            }, ms || 0);
          };
        }

        let password = document.querySelector("#password");
        let confirmation = document.querySelector("#confirmation")
        let confirmationChecked = 0;

        //Check for empth password/confirmation field
        password.onblur = function (){
            if (!confirmationChecked) {
                if (password.value) {
                    password.classList.remove("is-invalid")
                    password.classList.add("is-valid")
                } else {
                    password.classList.remove("is-valid")
                    password.classList.add("is-invalid")
                }
            }
        };
        confirmation.onblur = function (){
            if (!confirmationChecked) {
                if (password.value) {
                    confirmation.classList.remove("is-invalid")
                    confirmation.classList.add("is-valid")
                } else {
                    confirmation.classList.remove("is-valid")
                    confirmation.classList.add("is-invalid")
                }
            }
        };

        //Check for password, confirmation check
        confirmation.onkeyup = delay(function() {
            if (password.value && confirmation.value) {
                if (password.value != confirmation.value) {
                    document.querySelector("#confirmationfeedback").innerHTML = "Password doesn't match."
                    password.className = "form-control is-invalid"
                    confirmation.className = "form-control is-invalid"
                }
                else
                {
                    password.className = "form-control is-valid"
                    confirmation.className = "form-control is-valid"
                }
            }
            confirmationChecked = 1
        },500);

    </script>
</main>
</body>

</html>