<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Moments | Home</title>
<!-- Bootstrap -->
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="/resources/css/corousel.css" rel="stylesheet">
<link href="/resources/css/signin.css" rel="stylesheet">
<link href="/resources/css/all.css" rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="/resources/angular/user_service.js"></script>
<script src="/resources/angular/user_controller.js"></script>
</head>
<body ng-app="myApp">
	<div id="myCarousel" class="carousel slide" data-ride="carousel">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
			<li data-target="#myCarousel" data-slide-to="1"></li>
			<li data-target="#myCarousel" data-slide-to="2"></li>
		</ol>
		<div class="carousel-inner" role="listbox">
			<div class="item active">
				<img class="first-slide"
					src="/resources/static/corousel1.jpg" alt="First slide">
				<div class="container">
					<div class="carousel-caption">
						<h1>Moments - Personal photo storage</h1>
						<p>Memories last forever. But pictures don't. Make 'em last
							forever using Moments.</p>
						<p>
							<a data-target="#signUpModal" role="button"
								data-backdrop="static" data-keyboard="false"
								class="btn btn-lg btn-primary" data-toggle="modal">Sign Up</a> <a
								data-target="#signInModal" role="button" data-backdrop="static"
								data-keyboard="false" class="btn btn-lg btn-default"
								data-toggle="modal">Sign In</a>
						</p>
					</div>
				</div>
			</div>
			<div class="item">
				<img class="second-slide"
					src="/resources/static/corousel2.jpg" alt="Second slide">
				<div class="container">
					<div class="carousel-caption">
						<h1>Share the moments</h1>
						<p>Share the pictures and albums you want to share with
							others.</p>
						<p>
							<a data-target="#signUpModal" role="button"
								data-backdrop="static" data-keyboard="false"
								class="btn btn-lg btn-primary" data-toggle="modal">Sign Up</a> <a
								data-target="#signInModal" role="button" data-backdrop="static"
								data-keyboard="false" class="btn btn-lg btn-default"
								data-toggle="modal">Sign In</a>
						</p>
					</div>
				</div>
			</div>
			<div class="item">
				<img class="third-slide"
					src="/resources/static/corousel3.jpg" alt="Third slide">
				<div class="container">
					<div class="carousel-caption">
						<h1>Refresh your memories</h1>
						<p>Our image search selects all the similar memories. Be it a
							romantic getaway or family vacation.</p>
						<p>
							<a data-target="#signUpModal" role="button"
								data-backdrop="static" data-keyboard="false"
								class="btn btn-lg btn-primary" data-toggle="modal">Sign Up</a> <a
								data-target="#signInModal" role="button" data-backdrop="static"
								data-keyboard="false" class="btn btn-lg btn-default"
								data-toggle="modal">Sign In</a>
						</p>
					</div>
				</div>
			</div>
		</div>
		<a class="left carousel-control" href="#myCarousel" role="button"
			data-slide="prev"> <span class="glyphicon glyphicon-chevron-left"
			aria-hidden="true"></span> <span class="sr-only">Previous</span>
		</a> <a class="right carousel-control" href="#myCarousel" role="button"
			data-slide="next"> <span
			class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			<span class="sr-only">Next</span>
		</a>
	</div>
	<!-- /.carousel -->
	<!-- START THE FEATURETTES -->
	<div class="container">
		<div class="row featurette" id="storageFeature">
			<div class="col-md-7">
				<h2 class="featurette-heading">
					Free Storage <span class="text-muted">of 5 GB</span>
				</h2>
				<p class="lead">New users are getting free storage of 5 GB. More
					storage is available as Pay as you Go.</p>
			</div>
			<div class="col-md-5"></div>
		</div>
		<hr class="featurette-divider">
		<div class="row featurette" id="mobileFeature">
			<div class="col-md-7 col-md-push-5">
				<h2 class="featurette-heading">
					Oh yeah, it's that good. <span class="text-muted">Access
						your photos anywhere.</span>
				</h2>
				<p class="lead">Access your photos anywhere and on any device.
					We are mobile first!</p>
			</div>
			<div class="col-md-5 col-md-pull-7"></div>
		</div>
		<hr class="featurette-divider">
		<div class="row featurette" id="appFeature">
			<div class="col-md-7">
				<h2 class="featurette-heading">
					Coming Soon.... <span class="text-muted">Moments App.</span>
				</h2>
				<p class="lead">We will be launching our app on Play Store and
					App Store soon. Stay tuned to get the latest news about it.</p>
			</div>
			<div class="col-md-5"></div>
		</div>
		<hr class="featurette-divider">
		<!-- /END THE FEATURETTES -->
	</div>
	<!--Login Modal -->
	<div id="signInModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!--Login Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Please sign in</h4>
				</div>
				<form name="loginForm" ng-controller="LoginUserController as ctrl"
					ng-submit="loginForm.$valid && ctrl.submit()" ng-cloak>
					<div class="modal-body">
						<div class="alert alert-warning" role="alert" ng-show="showDiv">
							<ul>
								<li ng-repeat="error in errorMsgs">{{ error }}</li>
							</ul>
						</div>
						<div class="form-signin">
							<label for="inputusername" class="sr-only">Username</label> <input
								type="text" id="inputusername" class="form-control"
								placeholder="Username" name="username"
								ng-model="ctrl.user.username" required> <span
								class="formerror" ng-show="loginForm.username.$touched">
								<span ng-show="loginForm.username.$error.required">Username
									is required</span>
							</span> <label for="inputPassword" class="sr-only">Password</label> <input
								type="password" id="inputPassword" class="form-control"
								placeholder="Password" name="password"
								ng-model="ctrl.user.password" required> <span
								class="formerror"
								ng-show="loginForm.password.$error.required && loginForm.password.$touched">Password
								is required</span>
							<div class="checkbox">
								<label> <input type="checkbox" value="remember-me">
									Remember me
								</label>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<input class="btn btn-primary" type="submit" value="Sign in" />
						<button type="button" class="btn btn-default" data-dismiss="modal"
							ng-click="ctrl.reset()">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!--Signup Modal -->
	<div id="signUpModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!--Signup Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Please sign up</h4>
				</div>
				<form class="form-signin" ng-app="myApp" name="signupForm"
					ng-controller="SignupUserController as ctrl"
					ng-submit="ctrl.submit() " novalidate ng-cloak>
					<div class="modal-body">
						<div class="alert alert-warning" role="alert" ng-show="showDiv">
							<ul>
								<li ng-repeat="error in errorMsgs">{{ error }}</li>
							</ul>
						</div>
						<div class="form-signin">
							<label for="inputUsername" class="sr-only">Username</label> <input
								type="text" id="inputUsername" class="form-control"
								placeholder="Username" name="username"
								ng-model="ctrl.user.username" required="required"
								pattern="^[a-z][a-z0-9]{5,15}$" ng-change="ctrl.checkUsername()" /><span
								class="formerror" ng-show="signupForm.username.$touched">
								<span ng-show="signupForm.username.$error.required">Username
									is required.</span> <span
								ng-show="signupForm.username.$error.pattern && !signupForm.username.$error.required"
								class="formerror"> Username must start with lowercase
									letter, can contain only lowercase characters and digits and
									should be between 6 to 15 characters.</span> <span
								ng-show="showUsernameStatus">{{ usernameStatus }}</span>
							</span> <label for="inputfirstname" class="sr-only">First Name</label> <input
								type="text" id="inputfirstname" class="form-control"
								placeholder="First Name" name="first_name"
								ng-model="ctrl.user.first_name" pattern="^[A-Z][a-zA-Z]+$"
								required="required" /> <span class="formerror"
								ng-show="signupForm.first_name.$touched"> <span
								ng-show="signupForm.first_name.$error.required">Firstname
									is required.</span> <span
								ng-show="signupForm.first_name.$error.pattern">Must start
									with uppercase letter. Should contain only letters.</span>
							</span> <label for="inputLastname" class="sr-only">Last Name</label> <input
								type="text" id="inputLastname" class="form-control"
								placeholder="Last Name" name="last_name"
								ng-model="ctrl.user.last_name" required="required"
								pattern="^[A-Z][a-zA-Z]+$" /> <span class="formerror"
								ng-show="signupForm.last_name.$touched"> <span
								ng-show="signupForm.last_name.$error.pattern">Must start
									with uppercase letter. Should contain only letters.</span> <span
								ng-show="signupForm.last_name.$error.required">Lastname
									is required.</span>
							</span> <label for="inputEmail" class="sr-only">Email address</label> <input
								type="email" id="inputEmail" class="form-control"
								placeholder="Email address" name="email"
								ng-model="ctrl.user.email" required="required" ng-change="ctrl.checkEmail()" /> <span
								class="formerror" ng-show="signupForm.email.$touched "> <span
								ng-show="signupForm.email.$error.required">Email is
									required</span> <span ng-show="signupForm.email.$error.email">Enter
									a valid email</span>
									<span
								ng-show="showEmailStatus">{{ emailStatus }}</span>
							</span> <label for="inputPassword" class="sr-only">Password</label> <input
								type="password" id="inputPassword" class="form-control"
								placeholder="Password" name="password"
								ng-model="ctrl.user.password"
								pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,18}"
								required="required" /> <span class="formerror"
								ng-show="signupForm.password.$error.pattern">Password
								must contain at least one number and one uppercase and lowercase
								letter, and between 6 to 18 characters.</span> <span class="formerror"
								ng-show="signupForm.password.$touched "> <span
								ng-show="signupForm.password.$error.required">Password is
									required</span>
							</span> <label for="inputConfirmPassword" class="sr-only">Confirm
								Password</label> <input type="password" id="inputConfirmPassword"
								class="form-control" placeholder="Confirm Password"
								name="confirm_password" ng-model="confirm_password"
								required="required" /> <span class="formerror"
								ng-show="signupForm.confirm_password.$touched "> <span
								ng-show="signupForm.confirm_password.$error.required&&signupForm.confirm_password.$touched">Please
									enter this field</span>
							</span> <span class="formerror"
								ng-show="ctrl.user.password!=confirm_password && !signupForm.confirm_password.$error.required">Passwords
								do not match</span>
							<div class="radiobox">
								<input type="radio" ng-model="ctrl.user.gender" name="gender"
									ng-required="true" value="MALE" /> Male <input type="radio"
									ng-model="ctrl.user.gender" name="gender" ng-required="true"
									value="FEMALE" />Female
							</div>
							<span class="formerror"
								ng-show="signupForm.gender.$error.required && signupForm.$submitted">
								Gender is required</span>
						</div>
					</div>
					<div class="modal-footer">
						<input class="btn btn-primary" type="submit" value="Sign up" />
						<button type="button" class="btn btn-default" data-dismiss="modal"
							ng-click="ctrl.reset()">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="/resources/js/bootstrap.js"></script>
</body>
</html>