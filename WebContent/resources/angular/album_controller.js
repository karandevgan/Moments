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

			$scope.images = [1, 2, 3, 4, 5, 6, 7, 8];

			  $scope.loadMore = function() {
			    var last = $scope.images[$scope.images.length - 1];
			    for(var i = 1; i <= 8; i++) {
			      $scope.images.push(last + i);
			    }
			  };
			
			$scope.albums = [];

			this.createAlbum = function(album) {
				console.log(album);
				AlbumService.createAlbum(album).success(function(data) {
					$window.location.href = '/moments/';
				}).error(function(errResponse) {
					$scope.showDiv = true;
					$scope.errorMsgs = errResponse;
				});
			};

			this.getAlbums = function() {
				AlbumService.getAlbums().success(function(response) {
					$scope.albums = response;
					if($scope.albums.length > 0) {
						$scope.page_header_text = "Your albums";
					}
					else {
						$scope.page_header_text = "You have no albums.";
					}
				}).error(function(data) {
					$scope.page_header_text = "Error while retrieving albums.";
				}).finally(function() {
					$scope.albumData = "/moments/pages/albumData.html";
					console.log($scope.albumData);
				});
			};
			
			this.deleteAlbum = function(album_name) {
				$scope.showUpdateDiv = true;
				$scope.update = "Album will be deleted shortly";
				AlbumService.deleteAlbum(album_name).success(function(response) {
					$window.location.href='';
				}).error(function(response){
					$scope.update = "There was some error deleting the album";
				});
			};
			
			this.downloadAlbum = function(album_name) {
				AlbumService.downloadAlbum(album_name).success(function(response) {
					$window.location.href=response;
				}).error(function(response){
					console.error(response);
				});
			};
			
			this.createAlbumLink = function(album_name) {
				AlbumService.createAlbumLink(album_name).success(function(response) {
					console.log(response);
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
			this.call = 0;
			$scope.photos = [];
			this.getAlbum = function(album_name) {
				AlbumService.getAlbum(album_name, this.call).success(function(response) {
					console.log("Album called");
					$scope.photos = response;
					console.log($scope.photos.length);
					if ($scope.photos.length > 0){
						$scope.page_header_text = "Photos in album";
						this.call += $scope.photos.length + 1;
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
	var valid_types = ["image/jpeg", "image/png", "image/jpg", "image/gif"];
	$scope.getTheFiles = function ($files) {
		this.isInvalidFiles = false;
    	console.log("Inside Files");
    	files = $files;
    	$scope.total_files = $files.length;
    	for (var i=0; i < $scope.total_files; i++) {
    		var type = files[i].type;
    		console.log(type);
    		if (valid_types.indexOf(type) == -1){
    			$scope.error = "Only jpeg and png files are allowed.";
    			$scope.showErrorDiv = true;
    			this.isInvalidFiles = true;
    			console.log("Invalid file types");
    			break;
    		}
    	}
    	if (this.isInvalidFiles) {
    		$scope.uploadPhotoForm.$valid = false;
    	}
    	else {
    		$scope.uploadPhotoForm.$valid = true;
    	}
    };
		
	this.uploadFile = function() {
		console.log($scope.uploadPhotoForm.$valid);
		if ($scope.uploadPhotoForm.$valid) {
			$scope.show_upload_count = true;
			$scope.show_upload_form = false;

			angular.forEach(files, function (value, key) {
				var formdata = new FormData();
	            formdata.append(key, value);
	            console.log($scope.album_name);
	            AlbumService.uploadFile($scope.album_name, formdata).success(function(response) {
	    			console.log("Success");
	    			$scope.upload_count += 1;
	    			if ($scope.upload_count == $scope.total_files) {
	    				$window.location.href= "/moments/user/album/" + $scope.album_name;
	    			}
	    		}).error(function(data) {
	    			console.error("Error");
	    			$scope.showErrorDiv = true;
	    			$scope.error = "There was some problem in uploading files. Please try again";
	    			$scope.show_upload_count = false;
	    			$scope.show_upload_form = true;

	    		});
			});
		}
	};
}

]);