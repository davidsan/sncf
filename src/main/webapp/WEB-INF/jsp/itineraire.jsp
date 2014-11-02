<%@ page language="java" contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="fr">
<jsp:include page="head.jsp">
	<jsp:param name="title" value="Votre train en direct | Optitrain" />
</jsp:include>
<jsp:include page="head-end.jsp" />

<body role="document">

	<%@include file="navbar.jsp"%>


	<div class="sncf-header sncf-header-background">
		<div class="sncf-header-layer" id="sncf-bg-photo"></div>
   		<div class="sncf-header-layer-darken"></div>
		<div class="container">
			<h1>Votre train en direct</h1>
			<p>Info trafic en temps réel</p>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="page-header">
					<h1>Rechercher un train</h1>
				</div>
				<div class="panel panel-default">
					<div class="panel-body">
						<form role="form" action="/train/redirect" method="get">
							<div class="form-group">
								<label for="trainIdInput">Numéro de train</label> <input
									type="text" class="form-control" id="trainIdInput"
									placeholder="9210" name="id">
							</div>
							<button type="submit" class="btn btn-default">Rechercher</button>
						</form>
					</div>
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
