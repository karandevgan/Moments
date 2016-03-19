var App = angular.module('myApp', []);
App.factory('UserService', [
		'$http',
		'$q',
		function($http, $q) {
			return {
				createUser : function(user) {
					return $http.post("http://localhost:8080/moments/register",
							user).success(function(data) {
						return data;
					}).error(function(data, status) {
						console.error('Error while creating user');
						return data;
					});
				},
				getUser : function(user) {
					return $http.post("http://localhost:8080/moments/login",
							user).success(function(response) {
						return response;
					}).error(function(data, status) {
						console.error('Error while logging in');
						return data;
					});
				}
			}
		} ]);