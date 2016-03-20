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

				uploadFile : function(album_id, formData) {
					var url = 'http://localhost:8080/moments/user/upload?album_id=' + album_id;
					return $http.post(url,
							formData, {
								transformRequest : angular.identity,
								headers : {
									'Content-Type' : undefined
								}
							}).success(function(data, status) {
						return status;
					}).error(function(data, status) {
						return status;
					});
				},
				
				deleteAlbum : function(album_id) {
					var url = 'user/album/delete?album_id=' + album_id;
					return $http.get(url).success(function(responseData, status) {
						return responseData;
					}).error(function(data,status) {
						console.error("Error while deleting album");
						return data;
					});
 				},
 				
 				downloadAlbum : function(album_name) {
					var url = 'user/album/download/' + album_name;
					console.log(url);
					return $http.get(url).success(function(responseData, status) {
						return responseData;
					}).error(function(data,status) {
						console.error("Error while downloading album");
						return data;
					});
 				}
			}
		} ]);