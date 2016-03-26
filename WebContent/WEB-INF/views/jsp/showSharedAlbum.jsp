<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Moments | Home</title>

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="/moments/resources/angular/ng-infinite-scroll.min.js"></script>
<script src="/moments/resources/angular/album_service.js"></script>
<script src="/moments/resources/angular/album_controller.js"></script>

<!-- Bootstrap -->
<link href="/moments/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="/moments/resources/css/nav.css" rel="stylesheet">
<link href="/moments/resources/css/all.css" rel="stylesheet">
</head>

<body ng-app="myApp">
	<div class="container">
		<nav class="navbar navbar-default navbar-fixed-top">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
						aria-expanded="false">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="">Moments</a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="/moments">Home </a></li>
					</ul>
				</div>
			</div>
		</nav>
	</div>

	<div class="container-fluid">
		<div ng-controller="GetAlbumController as ctrl"
			infinite-scroll="ctrl.getSharedAlbum(${album_id}, '${album_name}')"
			infinite-scroll-distance="0" infinite-scroll-disabled="busy" ng-cloak>
			<h1 class="page-header">{{ page_header_text }}</h1>
			<div class="col-xs-5 col-sm-5 col-md-4" ng-repeat="photo in photos">
				<ng-include src="albumPhotos"> <img
					src="/moments/resources/static/loader.gif" /></ng-include>
			</div>
			<div class="clearfix"></div>
			<div ng-show="busy && notComplete">
				<img src="/moments/resources/static/loader.gif" />Loading data...
			</div>
		</div>
	</div>

	<script src="/moments/resources/js/bootstrap.js"></script>
</body>
</html>