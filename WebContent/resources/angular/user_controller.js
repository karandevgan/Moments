App
		.controller(
				'SignupUserController',
				[
						'$scope',
						'$window',
						'UserService',
						function($scope, $window, UserService) {
							this.user = {
								username : '',
								first_name : '',
								last_name : '',
								password : '',
								email : '',
								gender : ''
							};

							$scope.isUserNameValid = false;
							$scope.isPasswordMatching = false;
							$scope.isEmailValid = false;
							this.showGenderError = false;

							this.createUser = function(user) {
								UserService
										.createUser(user)
										.success(
												function() {
													$window.location.href = '/';
													$window
															.alert("Registration Successful. Kindly login using Sign In button.")
												}).error(function(errResponse) {
											$scope.showDiv = true;
											$scope.errorMsgs = errResponse;
										});
							};

							this.checkUsername = function() {
								if (!$scope.signupForm.username.$error.required
										&& !$scope.signupForm.username.$error.pattern) {
									$scope.showUsernameStatus = true;
									UserService
											.checkUsername(this.user.username)
											.success(
													function(response) {
														$scope.usernameStatus = response[0];
														$scope.showUsernameStatus = false;
														$scope.isUserNameValid = true;
													})
											.error(
													function(error) {
														$scope.usernameStatus = error[0];
														$scope.isUserNameValid = false;
													});
								} else {
									$scope.showUsernameStatus = false;
								}
							};

							this.checkEmail = function() {
								if (!$scope.signupForm.email.$error.required
										&& $scope.signupForm.email.$valid) {
									$scope.showEmailStatus = true;
									UserService
											.checkEmail(this.user.email)
											.success(
													function(response) {
														$scope.showEmailStatus = false;
														$scope.emailStatus = response[0];
														$scope.isEmailValid = true;
													}).error(function(error) {
												$scope.emailStatus = error[0];
												$scope.isemailValid = false;
											});
								} else {
									$scope.showEmailStatus = false;
								}
							};

							this.submit = function() {
								if (this.user.password == $scope.confirm_password) {
									$scope.isPasswordMatching = true;
								}

								if ($scope.signupForm.$valid
										&& $scope.isPasswordMatching
										&& $scope.isUserNameValid
										&& $scope.isEmailValid)
									this.createUser(this.user);

								console.log("Submit called");
							};

							this.reset = function() {
								$window.location.href = '/'
							};
						} ]);

App.controller('LoginUserController', [ '$scope', '$window', 'UserService',
		function($scope, $window, UserService) {
			this.user = {
				username : '',
				password : '',
			};

			this.getUser = function(user) {

				UserService.getUser(user).success(function(response) {
					sessionStorage.setItem("auth-token", response);
					console.log(sessionStorage.getItem("auth-token"));
					$window.location.href = '/';
				}).error(function(data) {
					$scope.showDiv = true;
					$scope.errorMsgs = data;
				});
			};

			this.submit = function() {
				this.getUser(this.user);
				console.log("Submit called");
			};

			this.reset = function() {
				$window.location.href = '/'
			}
		} ]);