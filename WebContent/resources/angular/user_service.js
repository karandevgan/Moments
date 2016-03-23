var App = angular.module('myApp', []);
App.factory('UserService', [
		'$http',
		'$q',
		function($http, $q) {
			return {
				createUser : function(user) {
					return $http.post("/moments/register", user).success(
							function(data) {
								return data;
							}).error(function(data, status) {
						console.error('Error while creating user');
						return data;
					});
				},
				getUser : function(user) {
					return $http.post("/moments/login", user).success(
							function(response) {
								return response;
							}).error(function(data, status) {
						console.error('Error while logging in');
						return data;
					});
				},
				checkUsername : function(username) {
					return $http.get("/moments/validateuser", {
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
					return $http.get("/moments/emailvalidation", {
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