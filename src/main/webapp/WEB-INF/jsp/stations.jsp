<%@ page language="java" contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="fr" ng-app="sncf" data-ng-init="init()">
<jsp:include page="head.jsp">
	<jsp:param name="title" value="Rechercher une gare | Optitrain" />
</jsp:include>
<jsp:include page="head-end.jsp" />

<body role="document">

	<%@include file="navbar.jsp"%>

	<div class="sncf-header sncf-header-background">
		<div class="sncf-header-layer" id="sncf-bg-photo"></div>
   		<div class="sncf-header-layer-darken"></div>
		
		<div class="container">
			<h1>En gare</h1>
			<p>Prochains départs et arrivées pour plus de 3000 gares</p>
		</div>
	</div>

	<div class="container" ng-controller="StationsCtrl">
		<div class="row">
			<div class="col-md-12">
				<div class="page-header">
					<h1>Rechercher une gare</h1>
				</div>
				<div class="panel panel-default">
					<div class="panel-body">
						<form class="form" role="search">
							<div class="form-group">
								<label for="trainIdInput">Nom de gare</label> <input type="text"
									class="form-control" placeholder="Paris-Gare-de-Lyon..."
									id="searchbarlg">
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="col-md-12">
				<div id="scroller-anchor"></div>
				<div id="scroller">
					<ul class="btn-group" id="alphabet">
						<li class="btn" ng-repeat="letter in alphabet"><a data-scroll
							href="#gare-{{ letter }}" class="h3">{{ letter }}</a></li>
					</ul>
				</div>
				<ul class="list-unstyled" ng-repeat="(key, value) in garesSorted">
					<li class="index-letter h2"><a class="anchor"
						id="gare-{{ key }}"></a>{{ key }} <small><a
							href="#"><span class="glyphicon glyphicon-chevron-up"></span></a></small></li>
					<li class="col-md-4"
						ng-repeat="gare in value | orderBy:'name_gare'"><a
						href="/gare/{{ gare.id_gare }}">{{ gare.name_gare }}</a></li>
				</ul>
			</div>
		</div>
	</div>

	<%@include file="footer.jsp"%>
	<script>
		initFlickrEnzo("train+france", "url_z");
	</script>
	<%@include file="script.jsp"%>
	<script>
		$('#searchbargare').typeahead(null, {
			displayKey : name,
			source : gares.ttAdapter(),
			engine : Hogan
		});
		$('#searchbargare').bind('typeahead:selected',
				function(obj, datum, name) {
					window.location = "/gare/" + datum.id;
				});
	</script>
	<script	src="http://cdnjs.cloudflare.com/ajax/libs/lodash.js/2.4.1/lodash.js"></script>
	<script	src="../assets/js/app.js"></script>
	<script	src="../assets/js/services.js"></script>
	<script src="../assets/js/smooth-scroll.min.js"></script>
	<script>
		function moveScroller() {
			var move = function() {
				var st = $(window).scrollTop();
				var ot = $("#scroller-anchor").offset().top;
				var s = $("#scroller");
				if(st > ot) {
					s.css({
						position: "fixed",
						top: "0px"
					});
				} else {
					if(st <= ot) {
						s.css({
							position: "relative",
							top: ""
						});
					}
				}
			};
			$(window).scroll(move);
			move();
		}
		$(function() {
			moveScroller();
		});
		smoothScroll.init();
	</script>
</body>
</html>
