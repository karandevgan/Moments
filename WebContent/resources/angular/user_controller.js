App.controller('SignupUserController', [ '$scope', '$window', 'UserService',
		function($scope, $window, UserService) {
			this.user = {
				username : '',
				first_name : '',
				last_name : '',
				password : '',
				email : '',
				gender : ''
			};

			this.showGenderError = false;

			this.createUser = function(user) {
				UserService.createUser(user).success(function() {
					$window.location.href = '/moments/';
					$window.alert("Registration Successful. Kindly login using Sign In button.")
				}).error(function(errResponse) {
					$scope.showDiv = true;
					$scope.errorMsgs = errResponse;
				});
			};

			this.submit = function() {
				if (this.user.password != $scope.confirm_password) {
					$scope.signupForm.$valid = false;
				}

				if ($scope.signupForm.$valid)
					this.createUser(this.user);

				console.log("Submit called");
			};

			this.reset = function() {
				$window.location.href = '/moments/'
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
					$window.location.href = '/moments/';
				}).error(function(data) {
					$scope.showDiv = true;
					$scope.errorMsg = 'Incorrect Username or Password';
				});
			};

			this.submit = function() {
				this.getUser(this.user);
				console.log("Submit called");
			};

			this.reset = function() {
				$window.location.href = '/moments/'
			}
		} ]);