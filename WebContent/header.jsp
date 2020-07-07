<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header>
	<c:if test = "${alert != null}">
		<c:choose>
	         <c:when test = "${alertType eq 1}">
	            <div class="alert alert-success border text-center" role="alert">
      				<c:out value="${alert}"/>
  			 	</div>
	         </c:when>
	         <c:when test = "${alertType eq 2}">
	            <div class="alert alert-danger border text-center" role="alert">
      				<c:out value="${alert}"/>
  			 	</div>
	         </c:when>
	         
	         <c:otherwise>
	            <div class="alert alert-primary border text-center" role="alert">
      				<c:out value="${alert}"/>
  			 	</div>
	         </c:otherwise>
		</c:choose>
	    
	</c:if>
</header>