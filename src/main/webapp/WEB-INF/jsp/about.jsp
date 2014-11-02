<%@ page language="java" contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="fr">
<jsp:include page="head.jsp">
	<jsp:param name="title" value="A propos | Optitrain" />
</jsp:include>
<jsp:include page="head-end.jsp"/>
<body role="document">

	<%@include file="navbar.jsp"%>

	<div class="sncf-header sncf-header-background sncf-header-apropos">
		<div class="sncf-header-layer"></div>
		<div class="container">
			<h1>Optitrain</h1>
		</div>
	</div>
	
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="page-header">
					<h1>A propos</h1>
				</div>
				<h3>Qu'est-ce que c'est ?</h3>
				<div>
					Optitrain est une application web permettant la consultation des
					prochains départs de train dans une gare française. Optitrain
					permet également d'obtenir des informations sur un train du réseau
					<a href="http://www.sncf.com/">SNCF</a>.
				</div>
				<h3>Par qui c'est fait ?</h3>
				<div>
					Cette application web a été réalisée par trois étudiants du master
					2 d'Informatique de l'<a href="http://www.upmc.fr/">Université
						Pierre et Marie Curie</a>.
				</div>
				<h3>Comment c'est fait ?</h3>
				<div>
					Optitrain est une application web utilisant les technologies <a
						href="http://www.oracle.com/technetwork/java/javaee/overview/">Java
						EE</a> et le framework <a href="http://spring.io">Spring</a> (Web MVC,
					JPA). Elle est hébergée sur un simple dyno chez <a
						href="http://heroku.com">Heroku</a> et utilise une base de données
					<a href="http://www.postgresql.org/">PostgreSQL</a>.
				</div>
				<h3>D'où viennent les données ?</h3>
				<div>
					Optitrain utilise les données fournies gracieusement par l'API de <a
						href="http://raildar.fr">Raildar.fr</a>
				</div>
			</div>
		</div>
	</div>

	<%@include file="footer.jsp"%>
	<%@include file="script.jsp"%>
</body>
</html>
