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

			this.createUser = function(user) {
				UserService.createUser(user).then(function() {
					$window.location.href = '/moments/';
				}, function(errResponse) {
					$scope.showDiv = true;
					$scope.errorMsg = 'Error creating user';
				});
			};

			this.submit = function() {
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

				UserService.getUser(user).then(function(response) {
					$window.location.href = '/moments/home-login';
				}, function(errResponse) {
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