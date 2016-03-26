var App = angular.module('myApp', [ 'infinite-scroll' ]);

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
		'$window',
		function($http, $q, $window) {
			return {
				createAlbum : function(album) {
					return $http.post("/user/album/create", album).success(
							function(data, status) {
								console.log(data);
								console.log(status);
								return data;
							}).error(function(data, status) {
						console.log(data);
						console.log(status);
						console.error('Error while creating album');
						return data;
					});
				},
				getAlbum : function(album_name, call) {
					console.log("album name: " + album_name);
					console.log("album call: " + call);
					return $http.get("/user/album", {
						params : {
							album_name : album_name,
							call : call
						}
					}).success(function(response) {
						return response;
					}).error(function(data, status) {
						console.error('Error while getting album');
						return data;
					});
				},
				getSharedAlbum : function(album_id, album_name, call) {
					return $http.get("/user/sharedalbum", {
						params : {
							album_id : album_id,
							album_name : album_name,
							call : call
						}
					}).success(function(response) {
						return response;
					}).error(function(data, status) {
						console.error('Error while getting album');
						return data;
					});
				},
				getAlbums : function() {
					return $http.get("/user/albums").success(
							function(responseData, status) {
								return responseData;
							}).error(function(data, status) {
						console.error("Error while getting albums");
						return data;
					});
				},

				uploadFile : function(album_name, formData) {
					var url = '/user/upload?album_name=' + album_name;
					return $http.post(url, formData, {
						transformRequest : angular.identity,
						headers : {
							'Content-Type' : undefined
						}
					}).success(function(data, status) {
						console.log(status);
						return data;
					}).error(function(data, status) {
						console.log(status);
						return data;
					});
				},

				deleteAlbum : function(album_name) {
					var url = '/user/album/delete?album_name=' + album_name;
					return $http.get(url).success(
							function(responseData, status) {
								return responseData;
							}).error(function(data, status) {
						console.error("Error while deleting album");
						return data;
					});
				},

				downloadAlbum : function(album_name) {
					var url = '/user/album/download/' + album_name;
					console.log(url);
					return $http.get(url).success(
							function(responseData, status) {
								return responseData;
							}).error(function(data, status) {
						console.error("Error while downloading album");
						return data;
					});
				},

				createAlbumLink : function(album_name) {
					var url = '/user/album/createlink/' + album_name;
					console.log(url);
					return $http.get(url).success(
							function(responseData, status) {
								return responseData;
							}).error(function(data, status) {
								console.error("Error creating link");
								return data;
							});
				},

				deletePhoto : function(public_id) {
					var url = '/user/photo/delete?public_id=' + public_id;
					return $http.get(url).success(
							function(responseData, status) {
								return responseData;
							}).error(function(data, status) {
						console.error("Error while deleting photo");
						return data;
					});
				},

				downloadPhoto : function(public_id) {
					var url = '/user/photo/download?public_id=' + public_id;
					$window.open(url, "_blank");
				}
			}
		} ]);