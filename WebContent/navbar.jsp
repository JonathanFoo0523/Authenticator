<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>


<nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="/Authenticator/index">Authenticator</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <% System.out.println(request.getSession(false) + " navbar session"); %>
		<c:choose>
			<c:when test = "${sessionScope.access == null}">
				<ul class="navbar-nav ml-auto mt-2">
		            <li class="nav-item"><a class="nav-link" href="/Authenticator/register">Register</a></li>
		            <li class="nav-item"><a class="nav-link" href="/Authenticator/login">Log In</a></li>
		        </ul>
		     </c:when>
		     <c:otherwise>
		        <ul class="navbar-nav mr-auto mt-2">
		        	<li class="nav-item"><a class="nav-link" href="/Authenticator/twoStepAuthSet">2-step Authentication</a></li>
		        </ul>
		        <ul class="navbar-nav ml-auto mt-2">
		            <li class="nav-item"><a class="nav-link" href="/Authenticator/logout">Log Out</a></li>
		        </ul>
		     </c:otherwise>
		</c:choose>
	</div>
</nav>
