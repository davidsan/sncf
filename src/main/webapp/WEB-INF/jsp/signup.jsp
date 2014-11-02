<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="fr">
<jsp:include page="head.jsp">
	<jsp:param name="title" value="Inscription | Optitrain" />
</jsp:include>
<jsp:include page="head-end.jsp" />

<body role="document">

	<div id="sncf-bg-photo" class="sncf-fullscreen-bg" />
	<div class="sncf-header-layer-darken">
		<div class="container sncf-signin">
		
			<form class="form-signin" action="/auth/signup" method="post">
				<h1 class="form-signin-heading text-muted">Optitrain</h1>
				<c:if test="${error!=null}">
					<div class="alert alert-danger">${ error }</div>
				</c:if>
				<c:if test="${info!=null}">
					<div class="alert alert-info">${ info }</div>
				</c:if>

				<input type="text" class="form-control form-signin-top"
					placeholder="Nom d'utilisateur" required autofocus name="username">

				<input type="text" class="form-control form-signin-middle"
				placeholder="Adresse électronique" required 
				name="mail">
				
				<input type="password"
				class="form-control form-signin-bottom" placeholder="Mot de passe"
				required name="password">
				
				<button class="btn btn-lg btn-primary btn-block" type="submit">
				  Inscription
				</button>

			</form>
		</div>
		<div class="sncf-footer-sign">
			<a class="sncf-footer-sign-link" href="/signin">Connexion</a>
		</div>
	</div>

	<script>
		initFlickrEnzo("train+france", "url_o");
	</script>
	<%@include file="script.jsp"%>
</body>
</html>
