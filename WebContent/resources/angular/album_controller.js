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
			}

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
						$scope.page_header_text = "";
					} else {
						$scope.page_header_text = "No photos in this album";
					}
				}).error(function(data) {
					console.error("Error");
				}).finally(function() {
					$scope.albumData = "/moments/pages/albumPhotos.html";
					console.log($scope.albumData);
				});
			};
		} ]);

App.controller('UploadController', [ '$scope', 'AlbumService',

function($scope, AlbumService) {

	this.formdata = new FormData();
    $scope.getTheFiles = function ($files) {
    	console.log("Inside Files");
    	console.log($files);
        angular.forEach($files, function (value, key) {
            this.formdata.append(key, value);
        });
		console.log(this.formdata);
    };
		
	this.uploadFile = function() {

		AlbumService.uploadFile(formdata).success(function(response) {
			console.log("Success");
		}).error(function(data) {
			console.error("Error");
		});
// var file = this.img.value;
// console.log(this.img);
// var filename = file[0].replace(/^.*[\\\/]/, '');
// console.log(filename);
	};

}

])