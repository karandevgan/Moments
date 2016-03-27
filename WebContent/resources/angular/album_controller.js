App.controller('AlbumController', [ '$scope', '$window', 'AlbumService',
		function($scope, $window, AlbumService) {
			this.album = {
				album_id : '',
				album_name : '',
				coverphoto : '',
				creation_date : '',
				description : '',
				last_modified : '',
				user : {}
			};
			
			$scope.albums = [];
			this.username = '';
			this.album_name = '';
			
			this.createAlbum = function(album) {
				console.log(album);
				AlbumService.createAlbum(album).success(function(data) {
					$window.location.href = '/';
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
					$scope.albumData = "/pages/albumData.html";
					console.log($scope.albumData);
				});
			};
			
			this.getSharedAlbums = function() {
				AlbumService.getSharedAlbums().success(function(response) {
					$scope.albums = response;
					if($scope.albums.length > 0) {
						$scope.page_header_text = "Albums shared with you";
					}
					else {
						$scope.page_header_text = "You have no albums shared with you.";
					}
				}).error(function(data) {
					$scope.page_header_text = "Error while retrieving albums.";
				}).finally(function() {
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
				$scope.shareAlbumLink = "";
				AlbumService.createAlbumLink(album_name).success(function(response) {
					console.log(response);
					$scope.shareAlbumLink = response;
				}).error(function(response){
					console.error(response);
				});
			};
			
			this.checkUsername = function() {
				if (!$scope.shareAlbumForm.username.$error.required
						&& !$scope.shareAlbumForm.username.$error.pattern) {
					$scope.showUsernameStatus = true;
					AlbumService
							.checkUsername(this.username)
							.success(
									function(response) {
										$scope.usernameStatus = response[0];
										$scope.showUsernameStatus = false;
										$scope.isUserNameValid = true;
									})
							.error(
									function(error) {
										$scope.usernameStatus = error[0];
										$scope.isUserNameValid = false;
									});
				} else {
					$scope.showUsernameStatus = false;
				}
			};
			
			this.shareAlbum = function(album_name, username) {
				console.log(album_name);
				console.log(username);
				AlbumService.shareAlbum(album_name, username).success(function(response){
					console.log(response);
					$window.location.href='';
				}).error(function(response){
					$scope.showDiv = true;
					$scope.errorMsgs = response;
					console.error(response);
				});
			};
			
			this.sharesubmit = function() {
				if ($scope.shareAlbumForm.$valid
						&& $scope.isUserNameValid) {
					this.shareAlbum(this.album_name, this.username)
				}
				console.log("Submit Called");
			};
			
			this.submit = function() {
				if ($scope.createAlbumForm.$valid)
					this.createAlbum(this.album);

				console.log("Submit called");
			};

			this.reset = function() {
				$window.location.href = '';
			};
		} ]);

App.controller('GetAlbumController', [ '$scope', '$window', 'AlbumService',
		function($scope, $window, AlbumService) {
			$scope.call = 0;
			$scope.busy = false;
			$scope.notComplete = true;
			$scope.photos = [];
			this.getAlbum = function(album_name) {
				if ($scope.busy) return;
			    $scope.busy = true;
				AlbumService.getAlbum(album_name, $scope.call).success(function(response) {
					if (response.length > 0){
						for (var i=0; i < response.length; i++) {
							$scope.photos.push(response[i]);
						}
						$scope.call += response.length;
						console.log($scope.busy);
						$scope.busy = false;
						console.log($scope.busy);
						$scope.page_header_text = "Photos in album";
					} else {
						if ($scope.call == 0)
							$scope.page_header_text = "No photos in this album";
						$scope.notComplete = false;
					}
				}).error(function(data) {
					console.error("Error");
				}).finally(function() {
					$scope.albumPhotos = "/pages/albumPhotos.html";
				});
			};
			this.getAllPhotos = function() {
				if ($scope.busy) return;
			    $scope.busy = true;
				AlbumService.getAllPhotos($scope.call).success(function(response) {
					if (response.length > 0){
						for (var i=0; i < response.length; i++) {
							$scope.photos.push(response[i]);
						}
						$scope.call += response.length;
						console.log($scope.busy);
						$scope.busy = false;
						console.log($scope.busy);
						$scope.page_header_text = "Your all Photos";
					} else {
						if ($scope.call == 0)
							$scope.page_header_text = "You have no photos";
						$scope.notComplete = false;
					}
				}).error(function(data) {
					console.error("Error");
				}).finally(function() {
					$scope.albumPhotos = "/pages/albumPhotos.html";
				});
			};
			
			this.getSharedAlbum = function(album_id, album_name) {
				AlbumService.getSharedAlbum(album_id, album_name, $scope.call).success(function(response) {
					if (response.length > 0){
						for (var i=0; i < response.length; i++) {
							$scope.photos.push(response[i]);
						}
						$scope.call += response.length;
						console.log($scope.busy);
						$scope.busy = false;
						console.log($scope.busy);
						$scope.page_header_text = "Photos in album";
					} else {
						if ($scope.call == 0)
							$scope.page_header_text = "No photos in this album";
						$scope.notComplete = false;
					}
				}).error(function(data) {
					$scope.page_header_text = "Error retreiving album";
				}).finally(function() {
					$scope.albumPhotos = "/pages/albumPhotoShared.html";
				});
			};
			
			this.deletePhoto = function(public_id) {
				console.log(public_id);
				AlbumService.deletePhoto(public_id).success(function(response){
					$window.location.href='';
				});
			};
			
			this.downloadPhoto = function(public_id) {
				console.log(public_id);
				AlbumService.downloadPhoto(public_id);
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
    	if ($scope.total_files > 50) {
    		$scope.error = "At max 50 files can be uploaded at once";
			$scope.showErrorDiv = true;
			this.isInvalidFiles = true;
    	}
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
			$scope.showErrorDiv = false;
			
			uploadOneFile();
		}
	};
	
	function uploadOneFile() {
		if ($scope.upload_count != $scope.total_files) {
			var formdata = new FormData();
	        formdata.append($scope.upload_count, files[$scope.upload_count]);
	        console.log($scope.album_name);
	        AlbumService.uploadFile($scope.album_name, formdata).success(function(response) {
				console.log("Success");
				$scope.upload_count += 1;
				uploadOneFile();
			}).error(function(data) {
				console.error("Error");
				$scope.showErrorDiv = true;
				$scope.error = "There was some problem in uploading files. Please try again";
				$scope.show_upload_count = false;
				$scope.show_upload_form = true;
			});
		} else{
			$window.location.href= "/user/album/" + $scope.album_name;
		}
	}
}

]);