<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Moments | Sign Up</title>
<!-- Bootstrap -->
<link href="/moments/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="/moments/resources/css/signin.css" rel="stylesheet">
<link href="/moments/resources/css/all.css" rel="stylesheet">
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script type="text/javascript"></script>
</head>
<body>
	<article class="container">
		<form class="form-signin" ng-app="" name="myForm"
			method="post" action="/moments/register">
			<h2 class="form-signin-heading">Please sign up</h2>

			<label for="inputUsername" class="sr-only">Username</label> <input
				type="text" id="inputUsername" class="form-control"
				placeholder="Username" name="username" ng-model="username"
				required="required" /> <span class="formerror"
				ng-show="myForm.username.$touched"> <span
				ng-show="myForm.username.$error.required">Username is
					required.</span>
			</span> <label for="inputfirstname" class="sr-only">First Name</label> <input
				type="text" id="inputfirstname" class="form-control"
				placeholder="First Name" name="first_name" ng-model="first_name"
				required="required" /> <span class="formerror"
				ng-show="myForm.first_name.$touched && myForm.first_name.$invalid">
				<span ng-show="myForm.first_name.$error.required">Firstname
					is required.</span>
			</span> <label for="inputLastname" class="sr-only">Last Name</label> <input
				type="text" id="inputLastname" class="form-control"
				placeholder="Last Name" name="last_name" ng-model="last_name"
				required="required" /> <span class="formerror"
				ng-show="myForm.last_name.$touched && myForm.last_name.$invalid">
				<span ng-show="myForm.last_name.$error.required">Lastname is
					required.</span>
			</span> <label for="inputEmail" class="sr-only">Email address</label> <input
				type="email" id="inputEmail" class="form-control"
				placeholder="Email address" name="email" ng-model="email"
				required="required" /> <span class="formerror"
				ng-show="myForm.email.$touched "> <span
				ng-show="myForm.email.$error.required">Email is required</span> <span
				ng-show="myForm.email.$error.email">Enter a valid email</span>
			</span> <label for="inputPassword" class="sr-only">Password</label> <input
				type="password" id="inputPassword" class="form-control"
				placeholder="Password" name="password" ng-model="password"
				ng-minlength="3" ng-maxlength="10" required="required" /> <span
				class="formerror"
				ng-show="myForm.password.$error.minlength||myForm.password.$error.maxlength">Password
				must in between 3 and 10</span> <span class="formerror"
				ng-show="myForm.password.$touched "> <span
				ng-show="myForm.password.$error.required">Password is
					required</span>
			</span> <label for="inputConfirmPassword" class="sr-only">Confirm
				Password</label> <input type="password" id="inputConfirmPassword"
				class="form-control" placeholder="Confirm Password"
				name="confirm_password" ng-model="confirm_password"
				valid-password-c="password" required="required" /> <span
				class="formerror" ng-show="myForm.confirm_password.$touched ">
				<span
				ng-show="myForm.confirm_password.$error.required&&myForm.confirm_password.$touched">Please
					enter this field</span>
			</span> <span class="formerror"
				ng-show="password!=confirm_password && myForm.confirm_password.$touched">Passwords
				do not match</span>


			<div class="radiobox">
				<input type="radio" name="gender" value="Male" checked="checked" />
				Male <input type="radio" name="gender" value="Female" /> Female
			</div>
			<input class="btn btn-lg btn-primary btn-block" type="submit"
				value="Sign Up" />
		</form>
	</article>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="/moments/resources/js/bootstrap.js"></script>
</body>
</html>