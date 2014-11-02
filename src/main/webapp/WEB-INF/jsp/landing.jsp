<%@ page language="java" contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="fr">
<jsp:include page="head.jsp">
	<jsp:param name="title" value="Optitrain" />
</jsp:include>
<jsp:include page="head-end.jsp" />

<body role="document">

	<%@include file="navbar.jsp"%>

	<div class="sncf-header sncf-header-background">
		<div class="sncf-header-layer" id="sncf-bg-photo"></div>
   		<div class="sncf-header-layer-darken"></div>
		<div class="container sncf-landing">

			<div class="col-md-12">
				<h1>Optitrain</h1>
				<p>Infos pratiques et horaires des trains au départ ou à
					l'arrivée disponibles pour plus de 3000 gares</p>
			</div>
			<div class="col-md-12">
				<div id="landing-search">
					<div class="landing-search-form col-md-7">
						<form class="form-horizontal" role="searchGare" id="formGare">
							<div class="form-group">
								<label for="searchbarlg"
									class="control-label control-label-landing">Votre gare</label>
								<input type="text"
									class="form-control input-lg transparent-input"
									id="searchbarlg" placeholder="Paris-Gare-de-Lyon...">
							</div>
						</form>
					</div>
					<div class="landing-search-form col-md-7">
						<form class="form-horizontal" role="searchTrain"
							action="/train/redirect" method="get" id="formTrain">
							<div class="form-group">
								<label for="inputTrain"
									class="control-label control-label-landing">Votre
									numéro de train</label> <input type="text"
									class="form-control input-lg transparent-input" id="inputTrain"
									placeholder="9210" name="id"><input type="submit"
									style="visibility: hidden;" />
							</div>
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
