<%@ page language="java" contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="fr" ng-app="sncf" ng-controller="MissionCtrl"
	data-ng-init="init()">
<jsp:include page="head.jsp">
	<jsp:param name="title" value="Optitrain" />
</jsp:include>
<jsp:include page="head-end.jsp" />

<body role="document">

	<%@include file="navbar.jsp"%>

	<div class="sncf-header sncf-header-background">
		<div class="sncf-header-layer" id="sncf-bg-photo"></div>
   		<div class="sncf-header-layer-darken"></div>
		<div class="container sncf-header-container">
			<h1>
				<a href="/train/{{mission.num}}"><span
					ng-bind-html="mission.pretty_brand"></span> <span
					ng-bind-html="mission.num"></span></a>
			</h1>
			<div class="row">
				<div class="col-md-6 sncf-header-mission">
					<h3>Au départ de</h3>
					<div id="header-mission-departure-station">
						<a href="/gare/{{mission.arrets[0].id_gare}}"><i
							class="glyphicon glyphicon-map-marker"></i> <span
							ng-bind-html="mission.arrets[0].name_gare"></span></a>
					</div>
					<div id="header-mission-departure-time">
						<i class="glyphicon glyphicon-time"></i> <span
							ng-bind-html="mission.arrets[0].lt_time_reel"></span>
					</div>
				</div>
				<div class="col-md-6 sncf-header-mission">
					<h3>A destination de</h3>
					<div id="header-mission-arrival-station">
						<a
							href="/gare/{{mission.arrets[mission.arrets.length-1].id_gare}}"><i
							class="glyphicon glyphicon-map-marker"></i> <span
							ng-bind-html="mission.arrets[mission.arrets.length-1].name_gare"></span></a>
					</div>
					<div id="header-mission-arrival-time">
						<i class="glyphicon glyphicon-time"></i> <span
							ng-bind-html="mission.arrets[mission.arrets.length-1].lt_time_reel"></span>
					</div>
				</div>
			</div>
			<c:if test="${user != null}">
				<div>
					<c:choose>
						<c:when test="${favorited == null}">
							<form class="form" role="form"
								action="/favorite/train/create/${trainId}" method="post">
								<button class="btn btn-lg  btn-primary" type="submit">
									<i class="glyphicon glyphicon-heart"></i> Ajouter au favoris
								</button>
							</form>
						</c:when>
						<c:otherwise>
							<form class="form" role="form"
								action="/favorite/train/delete/${trainId}" method="delete">
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
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<div class="sncf-itineraire">
					<div class="page-header">
						<h1>Arrêts</h1>
					</div>
					<ul class="sncf-route">


						<li ng-repeat="arret in mission.arrets">
							<div class="sncf-route-badge">
								<div class="sncf-route-dot"
									ng-class="arret.is_over ? 'sncf-route-dot' : 'sncf-route-dot sncf-route-illuminated-dot'"></div>
							</div>
							<div class="sncf-route-panel">
								<div class="sncf-route-heading">
									<h4 class="sncf-route-title">
										<a href="/gare/{{arret.id_gare}}"><span
											ng-bind-html="arret.name_gare"></span></a>
									</h4>
								</div>
								<div class="sncf-route-body">
									<div>
										<small class="text-muted"><i
											class="glyphicon glyphicon-time"></i> <span
											ng-bind-html="arret.pretty_time_reel"></span>
									</div>
									<div>
										<i class="glyphicon glyphicon-info-sign"></i>
										{{arret.minutes_retard}} min de retard &middot; <i
											class="glyphicon glyphicon-bell"></i> <span
											ng-bind-html="arret.from_now_time_reel"></span> </small>
									</div>
								</div>
							</div>
						</li>

					</ul>
				</div>
			</div>
			<div class="col-md-8">
				<div class="sncf-comments">
					<div class="page-header">
						<h1>
							En direct du <span ng-bind-html="mission.pretty_brand">train</span>
							<span ng-bind-html="mission.num"></span>
						</h1>
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
										<c:out value="${message.content}"></c:out>
									</div>
									<div class="comment-info">
										<span><i class="glyphicon glyphicon-user"></i> <a
											href="/voyageur/${message.person.username}">${message.person.username}</a></span>
										&middot; <span> <i class="glyphicon glyphicon-time"></i>
											<fmt:formatDate value="${message.date}"
												pattern="dd/MM/yyyy HH:mm" /></span>
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
									<form class="form" role="form" action="/message/train/create"
										method="post">
										<div class="form-group">
											<textarea class="form-control" rows="2" name="content"></textarea>
											<input type="hidden" name="resourceId" value="${trainId}">
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
		</div>
	</div>

	<%@include file="footer.jsp"%>
	<script>
		initFlickrEnzo("train+france", "url_z");
	</script>
	<%@include file="script.jsp"%>

	<script>
	moment.locale('fr');
	var app = angular.module("sncf", ['ngSanitize']).controller("MissionCtrl", function ($scope, $http) {
	    $scope.mission = [];
	    $scope.train = {};
	    $scope.init = function () {
	        var today = moment().format('YYYY-MM-DD');
	        var missionId = "";
	        var urlTrain = 'http://www.raildar.fr/json/get_train?num=${trainId}&date=' + today;
	        var httpRequestTrain = $http({
	            method: 'GET',
	            url: urlTrain
	        }).success(

	        function (data, status) {
	            $.each(
	            data, function (
	            index, value) {
	                if (value.id_mission.length > 0 && value.id_mission[0].day_of_circu == today) {
	                    // find the correct train
	                    $scope.train = value;
	                    missionId = value.id_mission[0].id_mission;
	                }
	            });
				if(missionId == ""){
					$("#btn-fav").hide();
					document.location.href = '/error'; // redirect to /gare page
					return;
				}
	            var urlMission = 'http://www.raildar.fr/json/get_mission?id_mission=' + missionId;
	            var httpRequestMission = $http({
	                method: 'GET',
	                url: urlMission
	            }).success(

	            function (
	            data, status) {
	                $scope.mission = data;
	                $scope.mission.pretty_brand = $scope.mission.brand.replace(/OCE/, "");
	                $.each(
	                $scope.mission.arrets, function (
	                index, value) {
	                    var d = value.time_reel;
	                    value.lt_time_reel = moment(
	                    d).format('H:mm')
	                    value.pretty_time_reel = moment(
	                    d).format("LLL");
	                    value.from_now_time_reel = moment(
	                    d).fromNow();
	                    value.is_over = moment().isAfter(
	                    moment(d));
	                });
	                var destination = $scope.mission.arrets[$scope.mission.arrets.length - 1];
	                document.title = $scope.mission.pretty_brand + ' ' + $scope.mission.num + " | " + document.title;
	            });
	        });
	    };
	});
	</script>
</body>
</html>
