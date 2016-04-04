<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Moments | My Shared Albums</title>
<!-- Bootstrap -->
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="/resources/css/nav.css" rel="stylesheet">
<link href="/resources/css/sidebar.css" rel="stylesheet">
<link href="/resources/css/signin.css" rel="stylesheet">
<link href="/resources/css/all.css" rel="stylesheet">

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="/resources/angular/ng-infinite-scroll.min.js"></script>
<script src="/resources/angular/album_service.js"></script>
<script src="/resources/angular/album_controller.js"></script>
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
						<li><a href="/">Home</span></a></li>
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
						<li><a href="/user/allphotos">All Photos</a></li>
					</ul>

					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false"> ${ username } <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="/user/profile">Profile</a></li>
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
					<li><a href="#">Overview</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a href="/sharedalbums">Shared
							Albums <span class="sr-only">(current)</span>
					</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li class="active"><a href="/mysharedalbums">My Shared Albums</a></li>
				</ul>
			</nav>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main"
				ng-controller="AlbumController as ctrl"
				ng-init="ctrl.getMySharedAlbums()" ng-cloak>
				<nav>
					<ul class="breadcrumb">
						<li class="active">Home</li>
					</ul>
				</nav>
				<div class="alert alert-warning" role="alert"
					ng-show="showUpdateDiv">
					<ul>
						<li>{{ update }}</li>
					</ul>
				</div>
				<h1 class="page-header">{{ page_header_text }}</h1>
				<table class="table  table-hover table-stripped" ng-show="showTable">
					<thead>
						<tr>
							<th>Album Name</th>
							<th>Shared With</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="(album_name, users) in sharedAlbums">
							<td><a href="/user/album/{{album_name}}">{{ album_name }}</a></td>
							<td>
								<p ng-repeat="user in users">{{ user.username }}&nbsp;&nbsp;&nbsp;<a href="#" ng-click="ctrl.unshare(album_name, user.username)">Unshare</a></p>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<ng-include src="'/pages/createAlbumModal.html'"></ng-include>

	<script src="/resources/js/bootstrap.js"></script>
</body>
</html>