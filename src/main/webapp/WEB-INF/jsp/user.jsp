<%@ page language="java" contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="fr">
<jsp:include page="head.jsp">
	<jsp:param name="title" value="Votre profil | Optitrain" />
</jsp:include>
<jsp:include page="head-end.jsp" />

<body role="document">

	<%@include file="navbar.jsp"%>

	<div class="sncf-header sncf-header-background header-user">
		<div class="sncf-header-layer" id="sncf-bg-photo"></div>
   		<div class="sncf-header-layer-darken"></div>
		<div class="container sncf-header-container">
			<h1>
				<a href="/voyageur/<c:out value="${user.username}" />">
					<c:out value="${user.username}" />
				</a>
			</h1>
			<div id="photo">
				<img src="<c:out value="${user.gravatar}"/>?s=96&default=identicon"
					width="96" height="96" alt="photo" class="img-circle">
			</div>
		</div>
	</div>

	<div class="container">
		<div class="col-md-4">
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active"><a href="#mes-trains"
					role="tab" data-toggle="tab">Mes trains</a></li>
				<li role="presentation"><a href="#mes-gares" role="tab"
					data-toggle="tab">Mes gares</a></li>

			</ul>
			<div class="tab-content">
				<div role="tabpanel" class="tab-pane active fade in panel panel-default"
					id="mes-trains">
					<div class="list-group">
						<c:forEach var="fav" items="${favtrains}">
							<a href="/train/<c:out value="${fav.resourceId}"/>"
								class="list-group-item">${fav.resourceName}</a>
						</c:forEach>
					</div>
				</div>
				<div role="tabpanel" class="tab-pane fade panel panel-default"
					id="mes-gares">
					<div class="list-group">
						<c:forEach var="fav" items="${favgares}">
							<a href="/gare/<c:out value="${fav.resourceId}"/>"
								class="list-group-item">${fav.resourceName}</a>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>

		<div class="col-md-8">
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active"><a
					href="#mes-commentaires" role="tab" data-toggle="tab">Mes
						commentaires</a></li>
			</ul>
			<div class="tab-content">
				<div role="tabpanel" class="tab-pane active fade in panel panel-default"
					id="mes-commentaires">
					<div class="comments">
						<c:forEach var="com" items="${commentaires}">
							<div class="row comment-panel">
								<div class="comment-block-self">
									<div class="comment-text">
										<c:out value="${com.content}" />
									</div>
									<div class="comment-info">
										<c:choose>
											<c:when test="${com.resourceType == 0}">
												<span><i class="glyphicon glyphicon-time"></i> <fmt:formatDate value="${com.date}"
														pattern="dd/MM/yyyy HH:mm" /> </span> &middot; <span><a
													href="/train/<c:out value="${com.resourceId}"/>"><i
														class="glyphicon glyphicon-link"></i></a></span>
											</c:when>
											<c:otherwise>
												<span><i class="glyphicon glyphicon-time"></i> <fmt:formatDate value="${com.date}"
														pattern="dd/MM/yyyy HH:mm" /> </span> &middot; <span><a
													href="/gare/<c:out value="${com.resourceId}"/>"><i
														class="glyphicon glyphicon-link"></i></a></span>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</c:forEach>
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
