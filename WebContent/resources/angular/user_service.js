var App = angular.module('myApp', []);
App.factory('UserService', [
		'$http',
		'$q',
		function($http, $q) {
			return {
				createUser : function(user) {
					return $http.post("/register", user).success(
							function(data, status) {
								return data;
							}).error(function(data, status) {
						console.error('Error while creating user');
						return data;
					});
				},
				getUser : function(user) {
					return $http.post("/login", user).success(
							function(response, status) {
								return response;
							}).error(function(data, status) {
						console.error('Error while logging in');
						return data;
					});
				},
				checkUsername : function(username) {
					return $http.get("/validateuser", {
						params : {
							username : username
						}
					}).success(function(response) {
						return response;

					}).error(function(error) {
						return error;
					});
				},
				checkEmail:function(email){
					return $http.get("/emailvalidation", {
						params : {
							email : email
						}
					}).success(function(response) {
						return response;

					}).error(function(error) {
						return error;
					});
				}
			}
		} ]);