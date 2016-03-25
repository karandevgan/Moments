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
<link href="/moments/resources/css/sidebar.css" rel="stylesheet">
<link href="/moments/resources/css/signin.css" rel="stylesheet">
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
						<li class="dropdown active"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown" role="button"
							aria-haspopup="true" aria-expanded="false"> Album <span
								class="caret"></span> <span class="sr-only">(current)</span></a>
							<ul class="dropdown-menu">
								<li><a href="/moments">All Albums</a></li>
								<li role="separator" class="divider"></li>
								<li><a data-target="#createAlbumModal" role="button"
									data-backdrop="static" data-keyboard="false"
									data-toggle="modal">Create Album</a></li>
							</ul></li>
						<li><a href="/moments/user/allphotos">All Photos</a></li>
						<li><a href="/moments/user/upload/${album_name}">Upload
								Photos</a></li>
					</ul>

					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false"> ${ username } <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="/moments/user/profile">Profile</a></li>
								<li role="separator" class="divider"></li>
								<li><a href="/moments/logout">Log Out</a></li>
							</ul></li>
					</ul>
				</div>
			</div>
		</nav>
	</div>

	<div class="container-fluid">
		<div class="row">
			<nav class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li class="active"><a href="#">Overview <span
							class="sr-only">(current)</span></a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a href="">Shared Images</a></li>
					<li><a href="">Shared Albums</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a href="">My Shared Images</a></li>
					<li><a href="">My Shared Albums</a></li>
				</ul>
			</nav>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<nav>
					<ul class="breadcrumb">
						<li><a href="/moments/">Home</a></li>
						<li class="active">${album_name}</li>
					</ul>
				</nav>
				<div ng-controller="GetAlbumController as ctrl"
					ng-init="ctrl.getAlbum('${album_name}')">
					<ng-include src="albumPhotos"> <img
						src="/moments/resources/static/loader.gif" /></ng-include>

				</div>

			</div>
		</div>
	</div>

	<ng-include src="'/moments/pages/createAlbumModal.html'"></ng-include>

	<script src="/moments/resources/js/bootstrap.js"></script>
</body>
</html>