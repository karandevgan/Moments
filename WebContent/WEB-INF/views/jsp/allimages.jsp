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
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="/resources/angular/ng-infinite-scroll.min.js"></script>
<script src="/resources/angular/album_service.js"></script>
<script src="/resources/angular/album_controller.js"></script>


<!-- Bootstrap -->
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="/resources/css/nav.css" rel="stylesheet">
<link href="/resources/css/sidebar.css" rel="stylesheet">
<link href="/resources/css/signin.css" rel="stylesheet">
<link href="/resources/css/all.css" rel="stylesheet">
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
					<a class="navbar-brand" href="/">Moments</a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="/">Home </a></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false"> Album <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="/">All Albums</a></li>
								<li role="separator" class="divider"></li>
								<li><a data-target="#createAlbumModal" role="button"
									data-backdrop="static" data-keyboard="false"
									data-toggle="modal">Create Album</a></li>
							</ul></li>
						<li class="active"><a href="/user/allphotos">All
								Photos <span class="sr-only"></span>
						</a></li>

					</ul>

					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false"> ${ username } <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="#">Profile</a></li>
								<li role="separator" class="divider"></li>
								<li><a href="/logout">Log Out</a></li>
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
					
					<li><a href="/sharedalbums">Shared Albums</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					
					<li><a href="">My Shared Albums</a></li>
				</ul>
			</nav>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<nav>
					<ul class="breadcrumb">
						<li><a href="/">Home</a></li>
						<li class="active">All Photos</li>
					</ul>
				</nav>
				<div ng-controller="GetAlbumController as ctrl"
					infinite-scroll="ctrl.getAllPhotos()"
					infinite-scroll-distance="0" infinite-scroll-disabled="busy"
					ng-cloak>
					<h1 class="page-header">{{ page_header_text }}</h1>
					<div class="col-xs-5 col-sm-5 col-md-4" ng-repeat="photo in photos">
						<ng-include src="albumPhotos"> <img
							src="/resources/static/loader.gif" /></ng-include>
					</div>
					<div class="clearfix"></div>
					<div ng-show="busy && notComplete">
						<img src="/resources/static/loader.gif" />Loading data...
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<ng-include src="'/pages/createAlbumModal.html'"></ng-include>
	

	<script src="/resources/js/bootstrap.js"></script>
</body>
</html>