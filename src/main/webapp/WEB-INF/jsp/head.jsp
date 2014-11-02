<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="../favicon.ico">
<link rel="apple-touch-icon-precomposed" href="/assets/icons/favicon-152.png">
<meta name="msapplication-TileColor" content="#eeeeee">
<meta name="msapplication-TileImage" content="/assets/icons/favicon-144.png">
<title><c:out value="${param.title}">SNCF</c:out></title>

<!-- Bootstrap core CSS -->
<link
	href="../assets/css/bootstrap.min.css"
	rel="stylesheet">
<!-- SNCF theme -->
<link href="../assets/css/site.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<link href="../assets/css/typeaheadjs.css" rel="stylesheet">
<%@include file="script-head.jsp"%>