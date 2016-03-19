var App = angular.module('myApp', []);

App.directive('ngFiles', [ '$parse', function($parse) {

	function fn_link(scope, element, attrs) {
		var onChange = $parse(attrs.ngFiles);
		element.on('change', function(event) {
			onChange(scope, {
				$files : event.target.files
			});
		});
	}
	;

	return {
		link : fn_link
	}
} ]);

App.factory('AlbumService', [
		'$http',
		'$q',
		function($http, $q) {

			return {
				createAlbum : function(album) {
					return $http.post(
							"http://localhost:8080/moments/user/album/create",
							album).success(function(data) {
						return data;
					}).error(function(data, status) {
						console.error('Error while creating album');
						return data;
					});
				},
				getAlbum : function(id) {
					return $http.get(
							"http://localhost:8080/moments/user/album", {
								params : {
									album_id : id
								}
							}).success(function(response) {
						return response;
					}).error(function(data, status) {
						console.error('Error while getting album');
						return data;
					});
				},
				getAlbums : function() {
					return $http.get(
							"http://localhost:8080/moments/user/albums")
							.success(function(responseData, status) {
								return responseData;
							}).error(function(data, status) {
								console.error("Error while getting albums");
								return data;
							});
				},

				uploadFile : function(formData) {
					return $http.post('http://localhost:8080/moments/upload',
							formData, {
								/*transformRequest : angular.identity,*/
								headers : {
									'Content-Type' : 'undefined'
								}
							}).success(function(data, status) {
						return data;
					}).error(function(data, status) {
						return data;
					});
				}

			}
		} ]);