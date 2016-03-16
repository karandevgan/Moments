var App = angular.module('myApp', []);

App.factory('UserService', [
		'$http',
		'$q',
		function($http, $q) {

			return {
				createUser : function(user) {
					return $http.post("http://localhost:8080/moments/register",
							user).then(function(response) {
						return response.data;
					}, function(errResponse) {
						console.error('Error while creating user');
						return $q.reject(errResponse);
					});
				},
	getUser: function(user){
		return $http.post("http://localhost:8080/moments/login",user).then(function(response){
			return response.data;
		},function(eerResponse)
		{
			console.error('Error while logging in');
			return $q.reject(errResponse);
		});
	}
			}
		} ]);