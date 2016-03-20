App.controller('AlbumController', [ '$scope', '$window', 'AlbumService',
		function($scope, $window, AlbumService) {
			this.album = {
				album_id : '',
				album_name : '',
				coverphoto : '',
				creation_date : '',
				description : '',
				last_modified : '',
			};

			$scope.albums = [];

			this.createAlbum = function(album) {
				console.log(album);
				AlbumService.createAlbum(album).success(function() {
					$window.location.href = '/moments/';
				}).error(function(errResponse) {
					$scope.showDiv = true;
					$scope.errorMsgs = errResponse;
				});
			};

			this.getAlbums = function() {
				AlbumService.getAlbums().success(function(response) {
					$scope.albums = response;
					$scope.page_header_text = "Your albums";
				}).error(function(data) {
					$scope.page_header_text = "You have no albums.";
				}).finally(function() {
					$scope.albumData = "/moments/pages/albumData.html";
					console.log($scope.albumData);
				});
			};
			
			this.deleteAlbum = function(album_id) {
				console.log(album_id);
				AlbumService.deleteAlbum(album_id).success(function(response) {
					$window.location.href='';
				}).error(function(response){
					console.error(response);
				});
			};
			
			this.downloadAlbum = function(album_name) {
				AlbumService.downloadAlbum(album_name).success(function(response) {
					$window.location.href=response;
				}).error(function(response){
					console.error(response);
				});
			};
			
			this.submit = function() {
				if ($scope.createAlbumForm.$valid)
					this.createAlbum(this.album);

				console.log("Submit called");
			};

			this.reset = function() {
				$window.location.href = '';
			};

			this.getAlbums();
		} ]);

App.controller('GetAlbumController', [ '$scope', '$window', 'AlbumService',
		function($scope, $window, AlbumService) {
			$scope.photos = [];
			this.getAlbum = function(album_id) {
				AlbumService.getAlbum(album_id).success(function(response) {
					console.log("Album called");
					$scope.photos = response;
					console.log($scope.photos.length);
					if ($scope.photos.length > 0){
						$scope.page_header_text = "Photos in album";
						$scope.albumPhotos = "/moments/pages/albumPhotos.html";
					} else {
						$scope.page_header_text = "No photos in this album";
					}
				}).error(function(data) {
					console.error("Error");
				}).finally(function() {
					$scope.albumPhotos = "/moments/pages/albumPhotos.html";
					console.log($scope.albumData);
				});
			};
		} ]);

App.controller('UploadController', [ '$scope', '$window','AlbumService',

function($scope, $window, AlbumService) {
	$scope.show_upload_form = true;
	var files;
	$scope.upload_count = 0;
	$scope.total_files = 0;

	$scope.getTheFiles = function ($files) {
    	console.log("Inside Files");
    	files = $files;
    	$scope.total_files = $files.length;
    };
		
	this.uploadFile = function() {
		$scope.show_upload_count = true;
		$scope.show_upload_form = false;

		angular.forEach(files, function (value, key) {
			var formdata = new FormData();
            formdata.append(key, value);
            console.log(this.album_id);
            AlbumService.uploadFile($scope.album_id, formdata).success(function(response) {
    			console.log("Success");
    			$scope.upload_count += 1;
    			if ($scope.upload_count == $scope.total_files) {
    				$window.location.href= "/moments/user/album/" + $scope.album_id;
    			}
    		}).error(function(data) {
    			console.error("Error");
    		});
        });
	};
}

]);