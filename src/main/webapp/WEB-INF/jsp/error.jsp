<%@ page language="java" contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="fr">
<jsp:include page="head.jsp">
	<jsp:param name="title" value="Problème | Optitrain" />
</jsp:include>
<jsp:include page="head-end.jsp" />

<body role="document">

	<%@include file="navbar.jsp"%>

	<div class="sncf-header sncf-header-background">
		<div class="sncf-header-layer" id="sncf-bg-photo"></div>
		<div class="sncf-header-layer-darken"></div>
		<div class="container sncf-landing">
			<div class="row">
				<div class="col-md-12 text-center">
					<h1>Problème</h1>
				</div>
				<div class="col-md-12 text-center top-buffer">
					<div>Quelque chose ne s'est pas déroulé comme prévu...</div>
				</div>
				<div class="col-md-12 landing-btns text-center top-buffer">
					<a href="/"><button type="button"
							class="btn btn-lg btn-primary">Retourner à l'accueil</button></a>
				</div>
			</div>
		</div>
	</div>

	<%@include file="footer.jsp"%>
	<script>
		initFlickrEnzo("train+france", "url_z");
	</script>
	<%@include file="script.jsp"%>
</body>
</html>
