<%@ page language="java" contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="fr" ng-app="sncf" ng-controller="HorairesCtrl"
	data-ng-init="init()">
<jsp:include page="head.jsp">
	<jsp:param name="title" value="Optitrain" />
</jsp:include>

<jsp:include page="head-end.jsp" />

<body role="document">

	<%@include file="navbar.jsp"%>

	<div class="sncf-header sncf-header-background" id="content">
		<div id="map-canvas"></div>
		<div class="sncf-header-layer-darken"></div>
		<div class="container sncf-header-container">
			<h1>
				<span ng-bind-html="gare.name_gare"></span>
			</h1>
			<div class="row">
				<div id="sncf-address"></div>
				<c:if test="${user != null}">
					<div>
						<c:choose>
							<c:when test="${favorited == null}">
								<form class="form" role="form"
									action="/favorite/gare/create/${gareId}" method="post">
									<button class="btn btn-lg  btn-primary" type="submit">
										<i class="glyphicon glyphicon-heart"></i> Ajouter au favoris
									</button>
								</form>
							</c:when>
							<c:otherwise>
								<form class="form" role="form"
									action="/favorite/gare/delete/${gareId}" method="delete">
									<button class="btn btn-lg  btn-primary" type="submit">
										<i class="glyphicon glyphicon-remove"></i> Retirer des favoris
									</button>
								</form>
							</c:otherwise>
						</c:choose>
					</div>
				</c:if>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="page-header">
					<h1>Trains au départ et trains à l'arrivée</h1>
				</div>
				<div class="table-responsive">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Train</th>
								<th>Heure</th>
								<th>Destination</th>
								<th>Particularités</th>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="train in trains">
								<td><a href="/train/{{train.num}}"><span
										ng-bind-html="train.pretty_brand"></span> <span
										ng-bind-html="train.num"></span> </a></td>
								<td><span ng-bind-html="train.pretty_time_reel"></span></td>
								<td><span ng-bind-html="train.terminus"></span></td>
								<td><a href="/train/{{train.num}}"><span
										ng-bind-html="train.info"></span></a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="sncf-comments">
			<div class="page-header">
				<h1>En direct de la gare</h1>
			</div>
			<div class="comments">
				<c:forEach items="${messages}" var="message">
					<div class="row comment-panel">
						<div class="col-xs-2 col-md-1 comment-pic">
							<img src="${message.person.gravatar}?s=512&default=identicon"
								class="img-rounded img-responsive" />
						</div>
						<div class="col-xs-10 col-md-11 comment-block">
							<div class="comment-text">
								<c:out value="${message.content}" />
							</div>
							<div class="comment-info">
								<span><i class="glyphicon glyphicon-user"></i> <a
									href="/voyageur/${message.person.username}">${message.person.username}</a></span>
								&middot; <span><i class="glyphicon glyphicon-time"></i> <fmt:formatDate
										value="${message.date}" pattern="dd/MM/yyyy HH:mm" /></span>
							</div>
						</div>
					</div>
				</c:forEach>
				<c:if test="${user != null}">
					<div class="row comment-panel">
						<div class="col-xs-2 col-md-1 comment-pic">
							<img
								src="<c:out value="${user.gravatar}"/>?s=512&default=identicon"
								class="img-rounded img-responsive" />
						</div>
						<div class="col-xs-10 col-md-11 comment-block">
							<form class="form" role="form" action="/message/gare/create"
								method="post">
								<div class="form-group">
									<textarea class="form-control" rows="2" name="content"></textarea>
									<input type="hidden" name="resourceId"
										value="<c:out value="${gareId}"/>">
								</div>
								<button type="submit" class="btn btn-primary btn-lg btn-block">
									<i class="glyphicon glyphicon-send"></i> Envoyer
								</button>
							</form>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>


	<%@include file="footer.jsp"%>
	<%@include file="script.jsp"%>
	<script
		src="http://maps.googleapis.com/maps/api/js?sensor=false&extension=.js&output=embed"></script>
	<script src="../assets/js/gmaps.js"></script>
	<script>
	moment.locale('fr');
	var app = angular.module("sncf", ['ngSanitize']).controller("HorairesCtrl", function ($scope, $http) {
	    $scope.trains = [];
	    $scope.gareList = [];
	    $scope.init = function () {
	        var httpRequest = $http({
	            method: 'GET',
	            url: 'http://www.raildar.fr/json/next_missions?id_gare=${gareId}'

	        }).success(

	        function (data, status) {
	            $scope.trains = data;
	            $.each(
	            $scope.trains, function (
	            index, train) {
	                var d = train.time_reel;
	                train.pretty_time_reel = moment(
	                d).format('H:mm')
	                train.pretty_brand = train.brand.replace(/OCE/, "");
	                if (train.info == null) {
	                    train.info = "Pas d'information"
	                }
	            });

	        });
	        var httpRequest2 = $http({
	            method: 'GET',
	            url: 'http://www.raildar.fr/json/gares?id_gare=${gareId}'

	        }).success(

	        function (data, status) {
	            $scope.gareList = data;
	            $scope.gare = $scope.gareList[0];
	            document.title = decodeEntities($scope.gare.name_gare) + " | " + document.title;
	            initGoogleMaps(
	            $scope.gare.lat, $scope.gare.lng);
	        });
	    };
	});
	</script>

</body>
</html>
