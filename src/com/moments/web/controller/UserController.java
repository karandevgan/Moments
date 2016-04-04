package com.moments.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.moments.model.Album;
import com.moments.model.Photo;
import com.moments.model.User;
import com.moments.service.Service;
import com.moments.service.ValidationService;

@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	private Service service;

	@Autowired
	HttpSession session;

	@RequestMapping(value = "/album/create", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<List<String>> saveAlbum(@RequestBody Album album,
			@RequestHeader(value = "Auth-Token", required = false) String token_value) {

		User user = null;
		Object sessionUser = session.getAttribute("username");

		user = service.getUser(token_value, sessionUser);

		List<String> responseList = new ArrayList<String>();
		HttpStatus status = null;
		if (user != null) {
			List<String> errorList = ValidationService.validateAlbumName(service, album, user);
			if (errorList.size() > 0) {
				responseList = errorList;
				status = HttpStatus.CONFLICT;
			} else {
				if (service.createAlbum(user, album)) {
					responseList.add("Album Created");
					status = HttpStatus.CREATED;
				} else {
					responseList.add("Error Creating Album");
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
			}
		} else {
			responseList.add("Unauthorized");
			status = HttpStatus.UNAUTHORIZED;
		}

		return new ResponseEntity<List<String>>(responseList, status);
	}

	@RequestMapping(value = "/album/delete", params = { "album_name" }, produces = "application/json")
	public ResponseEntity<Void> deleteAlbum(HttpServletRequest req,
			@RequestHeader(value = "Auth-Token", required = false) String token_value) {

		User user = null;
		Object sessionUser = session.getAttribute("username");

		user = service.getUser(token_value, sessionUser);

		if (user != null) {
			String album_name = req.getParameter("album_name");
			String album_to_delete = session.getAttribute("username").toString() + "/"
					+ service.getAlbum(album_name, user.getUser_id()).getAlbum_name();
			boolean album_deleted = service.deleteAlbum(album_to_delete, album_name, user);
			if (album_deleted)
				return new ResponseEntity<Void>(HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
	}

	@RequestMapping(value = "/albums", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Album>> getAlbums(HttpServletRequest req,
			@RequestHeader(value = "Auth-Token", required = false) String token_value) {
		User user = null;
		Object sessionUser = session.getAttribute("username");
		HttpStatus returnStatus = null;
		user = service.getUser(token_value, sessionUser);
		List<Album> albums = null;
		if (user != null) {
			int user_id = user.getUser_id();
			albums = service.getAlbums(user_id);
			System.out.println(albums);
			if (albums != null)
				returnStatus = HttpStatus.OK;
			else
				returnStatus = HttpStatus.NO_CONTENT;
		} else {
			returnStatus = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<List<Album>>(albums, returnStatus);
	}

	@RequestMapping(value = "/album", params = { "album_name", "call" }, method = RequestMethod.GET)
	public ResponseEntity<List<Photo>> getPhotos(HttpServletRequest req,
			@RequestHeader(value = "Auth-Token", required = false) String token_value) {
		User user = null;
		Object sessionUser = session.getAttribute("username");
		ResponseEntity<List<Photo>> returnEntity = null;
		user = service.getUser(token_value, sessionUser);
		if (user != null) {
			try {
				String album_name = req.getParameter("album_name");
				int call = Integer.parseInt(req.getParameter("call"));
				System.out.println(album_name);
				returnEntity = new ResponseEntity<List<Photo>>(service.getPhotos(album_name, user, call),
						HttpStatus.OK);
				return returnEntity;
			} catch (NumberFormatException e) {
				returnEntity = new ResponseEntity<List<Photo>>(HttpStatus.BAD_REQUEST);
			}
		} else {
			returnEntity = new ResponseEntity<List<Photo>>(HttpStatus.UNAUTHORIZED);
		}
		return returnEntity;
	}

	@RequestMapping(value = "/sharedalbum", params = { "album_id", "album_name", "call" }, method = RequestMethod.GET)
	public ResponseEntity<List<Photo>> getPhotosShared(HttpServletRequest req) {
		ResponseEntity<List<Photo>> returnEntity = null;

		String album_name = req.getParameter("album_name");
		int album_id = Integer.parseInt(req.getParameter("album_id"));
		int call = Integer.parseInt(req.getParameter("call"));
		List<Photo> sharedPhotos = service.getPhotosShared(album_id, album_name, call);
		if (sharedPhotos != null)
			returnEntity = new ResponseEntity<List<Photo>>(sharedPhotos, HttpStatus.OK);
		else
			returnEntity = new ResponseEntity<List<Photo>>(HttpStatus.NOT_FOUND);
		return returnEntity;
	}

	@RequestMapping(value = "/upload", params = { "album_name" }, method = RequestMethod.POST)
	public ResponseEntity<Void> uploadFile(MultipartHttpServletRequest request, HttpServletRequest req,
			@RequestHeader(value = "Auth-Token", required = false) String token_value) {

		User user = null;
		Object sessionUser = session.getAttribute("username");
		ResponseEntity<Void> returnEntity = null;
		user = service.getUser(token_value, sessionUser);

		if (user != null) {
			String album_name = request.getParameter("album_name");
			Album album = service.getAlbum(album_name, user.getUser_id());

			Iterator<String> itr = request.getFileNames();

			MultipartFile file = request.getFile(itr.next());

			if (service.uploadImage(album, user, file)) {
				returnEntity = new ResponseEntity<Void>(HttpStatus.CREATED);
			} else {
				returnEntity = new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			returnEntity = new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
		return returnEntity;

	}

	@RequestMapping(value = "/album/download/{album_name}", produces = "text/plain")
	public ResponseEntity<String> downloadAlbumZip(@PathVariable String album_name, HttpServletRequest req,
			@RequestHeader(value = "Auth-Token", required = false) String token_value) {

		User user = null;
		Object sessionUser = session.getAttribute("username");
		ResponseEntity<String> returnEntity = null;
		user = service.getUser(token_value, sessionUser);
		if (user != null) {
			Album album = service.getAlbum(album_name, user.getUser_id());
			if (album != null) {
				String download_folder = user.getUsername() + "/" + album_name;
				String url = service.downloadAlbum(download_folder);
				returnEntity = new ResponseEntity<String>(url, HttpStatus.OK);
			} else {
				returnEntity = new ResponseEntity<String>("Album not found", HttpStatus.NOT_FOUND);
			}

		} else {
			returnEntity = new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		return returnEntity;
	}

	@RequestMapping(value = "/album/createlink/{album_name}", produces = "text/plain")
	public ResponseEntity<String> createAlbumLink(@PathVariable String album_name, HttpServletRequest req,
			@RequestHeader(value = "Auth-Token", required = false) String token_value) {

		User user = null;
		Object sessionUser = session.getAttribute("username");
		ResponseEntity<String> returnEntity = null;
		user = service.getUser(token_value, sessionUser);
		if (user != null) {
			Album album = service.getAlbum(album_name, user.getUser_id());
			if (album != null) {
				try {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(baos);
					oos.writeObject(album);
					oos.close();
					String object_link = Base64.getUrlEncoder().encodeToString(baos.toByteArray());
					System.out.println(object_link);
					String link = "https://pslmoments.herokuapp.com/sharealbum/" + object_link;
					returnEntity = new ResponseEntity<String>(link, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					returnEntity = new ResponseEntity<String>("Error Creating Link", HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				returnEntity = new ResponseEntity<String>("Album not found", HttpStatus.NOT_FOUND);
			}

		} else {
			returnEntity = new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		return returnEntity;
	}

	@RequestMapping(value = "/photo/delete", params = { "public_id" })
	public ResponseEntity<Void> deleteFile(HttpServletRequest request,
			@RequestHeader(value = "Auth-Token", required = false) String token_value) {

		User user = null;
		Object sessionUser = session.getAttribute("username");
		ResponseEntity<Void> returnEntity = null;
		user = service.getUser(token_value, sessionUser);

		if (user != null) {
			String public_id = request.getParameter("public_id");
			if (service.deletePhoto(public_id, user))
				returnEntity = new ResponseEntity<Void>(HttpStatus.OK);
			else
				returnEntity = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

		} else {
			returnEntity = new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
		return returnEntity;
	}

	@RequestMapping(value = "/getallphotos", params = { "call" })
	public ResponseEntity<List<Photo>> allPhotos(HttpServletRequest request,
			@RequestHeader(value = "Auth-Token", required = false) String token_value) {
		User user = null;
		Object sessionUser = session.getAttribute("username");
		ResponseEntity<List<Photo>> returnEntity = null;
		user = service.getUser(token_value, sessionUser);
		int call = Integer.parseInt(request.getParameter("call"));
		if (user != null) {
			List<Photo> photos = service.getTotalPhotos(user, call);
			if (photos.size() > 0)
				returnEntity = new ResponseEntity<List<Photo>>(photos, HttpStatus.OK);
			else
				returnEntity = new ResponseEntity<List<Photo>>(HttpStatus.NO_CONTENT);

		} else {
			returnEntity = new ResponseEntity<List<Photo>>(HttpStatus.UNAUTHORIZED);
		}
		return returnEntity;
	}

	@RequestMapping(value = "/photo/download", params = { "public_id" })
	public void downloadImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String public_id = request.getParameter("public_id");
		String image_path = service.getImagePath(public_id);
		String image_name = image_path.substring(image_path.lastIndexOf('/') + 1, image_path.length());
		System.out.println(image_name);
		if (image_path != null) {
			URL url = new URL(image_path);
			System.out.println(image_path);
			String mimeType = URLConnection.guessContentTypeFromName(image_name);
			if (mimeType == null)
				mimeType = "application/octet-stream";
			System.out.println(mimeType);
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + image_name + "\""));
			InputStream inputStream = url.openStream();
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}

	@RequestMapping(value = "/album/sharewithuser", params = { "album_name",
			"share_user" }, produces = "application/json")
	public ResponseEntity<List<String>> shareAlbumWithUser(HttpServletRequest request,
			@RequestHeader(value = "Auth-Token", required = false) String token_value) {
		User user = null;
		Object sessionUser = session.getAttribute("username");
		ResponseEntity<List<String>> returnEntity = null;
		List<String> response = new ArrayList<String>();
		user = service.getUser(token_value, sessionUser);
		System.out.println("Inside Share Album Controller");
		if (user != null) {
			String album_name = request.getParameter("album_name");
			String share_user = request.getParameter("share_user");
			if (user.getUsername().equals(share_user)) {
				response.add("Cannot share album with yourself!");
				returnEntity = new ResponseEntity<List<String>>(response, HttpStatus.CONFLICT);
			} else {
				Album album = service.getAlbum(album_name, user.getUser_id());
				if (album != null) {
					try {
						service.shareAlbumWithUser(album.getAlbum_id(), service.getUser(share_user));
						returnEntity = new ResponseEntity<List<String>>(HttpStatus.ACCEPTED);
					} catch (Exception e) {
						response.add("Already Shared With This User");
						returnEntity = new ResponseEntity<List<String>>(response, HttpStatus.CONFLICT);
					}
				} else {
					response.add("Album Does Not Exist");
					returnEntity = new ResponseEntity<List<String>>(response, HttpStatus.BAD_REQUEST);
				}
			}
		} else {
			response.add("Unauthorized");
			returnEntity = new ResponseEntity<List<String>>(response, HttpStatus.UNAUTHORIZED);
		}
		return returnEntity;
	}

	@RequestMapping(value = "/sharedalbums", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Set<Album>> getSharedAlbums(HttpServletRequest req,
			@RequestHeader(value = "Auth-Token", required = false) String token_value) {
		User user = null;
		Object sessionUser = session.getAttribute("username");
		HttpStatus returnStatus = null;
		user = service.getUser(token_value, sessionUser);
		Set<Album> albums = null;
		if (user != null) {
			albums = service.getSharedAlbums(user);
			System.out.println(albums);
			if (albums != null)
				returnStatus = HttpStatus.OK;
			else
				returnStatus = HttpStatus.NO_CONTENT;
		} else {
			returnStatus = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Set<Album>>(albums, returnStatus);
	}

	@RequestMapping(value = "/mysharedalbums", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Map<String, Set<User>>> getMySharedAlbums(HttpServletRequest req,
			@RequestHeader(value = "Auth-Token", required = false) String token_value) {
		User user = null;
		Object sessionUser = session.getAttribute("username");
		HttpStatus returnStatus = null;
		user = service.getUser(token_value, sessionUser);
		Map<String, Set<User>> mySharedAlbums = null;
		if (user != null) {
			mySharedAlbums = service.mySharedAlbums(user.getUser_id());
			if (mySharedAlbums.size() > 0) {
				returnStatus = HttpStatus.OK;
			} else
				returnStatus = HttpStatus.NO_CONTENT;
		} else {
			returnStatus = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Set<User>>>(mySharedAlbums, returnStatus);
	}

	@RequestMapping(value = "/album/unshare", params = { "album_name", "share_user" }, produces = "application/json")
	public ResponseEntity<List<String>> unshareAlbumWithUser(HttpServletRequest request,
			@RequestHeader(value = "Auth-Token", required = false) String token_value) {
		User user = null;
		Object sessionUser = session.getAttribute("username");
		ResponseEntity<List<String>> returnEntity = null;
		List<String> response = new ArrayList<String>();
		user = service.getUser(token_value, sessionUser);
		System.out.println("Inside Share Album Controller");
		if (user != null) {
			String album_name = request.getParameter("album_name");
			String share_user = request.getParameter("share_user");
			Album album = service.getAlbum(album_name, user.getUser_id());
			if (album != null) {
				try {
					service.unshare(album.getAlbum_id(), service.getUser(share_user));
					returnEntity = new ResponseEntity<List<String>>(HttpStatus.ACCEPTED);
				} catch (Exception e) {
					response.add("Error unsharing album");
					returnEntity = new ResponseEntity<List<String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				response.add("Album Does Not Exist");
				returnEntity = new ResponseEntity<List<String>>(response, HttpStatus.BAD_REQUEST);
			}
		} else {
			response.add("Unauthorized");
			returnEntity = new ResponseEntity<List<String>>(response, HttpStatus.UNAUTHORIZED);
		}
		return returnEntity;
	}

	@RequestMapping(value = "/album/makecover", params = { "path", "album_name" }, produces = "application/json")
	public ResponseEntity<List<String>> changeCover(HttpServletRequest request,
			@RequestHeader(value = "Auth-Token", required = false) String token_value) {
		User user = null;
		Object sessionUser = session.getAttribute("username");
		ResponseEntity<List<String>> returnEntity = null;
		List<String> response = new ArrayList<String>();
		user = service.getUser(token_value, sessionUser);

		if (user != null) {
			String path = request.getParameter("path");
			String album_name = request.getParameter("album_name");
			Album album = service.getAlbum(album_name, user.getUser_id());
			if (album != null) {
				try {
					service.changeCover(album, path);
					returnEntity = new ResponseEntity<List<String>>(HttpStatus.ACCEPTED);
				} catch (Exception e) {
					response.add("Error changing cover");
					returnEntity = new ResponseEntity<List<String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				response.add("Album Does Not Exist");
				returnEntity = new ResponseEntity<List<String>>(response, HttpStatus.BAD_REQUEST);
			}
		} else {
			response.add("Unauthorized");
			returnEntity = new ResponseEntity<List<String>>(response, HttpStatus.UNAUTHORIZED);
		}
		return returnEntity;
	}
}