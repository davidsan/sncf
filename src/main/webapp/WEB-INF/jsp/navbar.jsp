
<!-- Fixed navbar -->
<header class="navbar navbar-static-top sncf-nav" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button class="navbar-toggle" type="button" data-toggle="collapse"
				data-target=".sncf-navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a href="/" class="navbar-brand">Optitrain</a>
		</div>
		<nav class="collapse navbar-collapse sncf-navbar-collapse"
			role="navigation">
			<ul class="nav navbar-nav">
				<li><a href="/gare">En gare</a></li>
				<li><a href="/train">Mon train</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<c:choose>
					<c:when test="${user != null}">
						<li><a
							href="/voyageur/<c:out value="${user.username}"></c:out>"><c:out
									value="${user.username}"></c:out></a></li>
						<li><a href="/signout">Déconnexion</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="/signin">Connexion</a></li>
						<li><a href="/signup">Inscription</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
			<div class="navbar-search">
				<form class="navbar-form navbar-right" role="search">
					<div class="input-group">
						<input type="text" class="form-control"
							placeholder="Paris-Gare-de-Lyon..." id="searchbar">
						<div class="input-group-btn">
							<button class="btn btn-default btn-search" disabled="disabled">
								<i class="glyphicon glyphicon-search"></i>
							</button>
						</div>
					</div>
				</form>
			</div>
		</nav>
	</div>
</header>