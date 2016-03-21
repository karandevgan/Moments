package com.moments.web.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cloudinary.Api;
import com.cloudinary.ArchiveParams;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.moments.Dao.AlbumDao;
import com.moments.Dao.PhotoDao;
import com.moments.Dao.UserDao;
import com.moments.model.Album;
import com.moments.model.Photo;
import com.moments.model.User;

@RequestMapping("/user")
@Controller
public class UserController {
	private Map config = ObjectUtils.asMap("cloud_name", "kaydewgun", "api_key", "757818147311579", "api_secret",
			"Jo1xhkKMAiHSMa1ySvSc48r6qlQ");

	@Autowired
	public UserDao userDao;
	@Autowired
	public AlbumDao albumDao;
	@Autowired
	public PhotoDao photoDao;
	@Autowired
	HttpSession session;
	
	
	@RequestMapping(value = "/album/create", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<List<String>> saveAlbum(@RequestBody Album album, HttpSession session) {
		User user = userDao.getUser(session.getAttribute("username").toString());
		album.setUser(user);
		album.setCreation_date(new Date());
		album.setLast_modified(new Date());
		album.setCoverphoto("/moments/resources/static/emptyAlbum.jpg");
		album.setUser(user);
		albumDao.save(album);
		user.setNumber_of_albums(user.getNumber_of_albums() + 1);
		userDao.update(user);
		return new ResponseEntity<List<String>>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/album/delete", params = { "album_id" }, produces = "application/json")
	public ResponseEntity<Void> deleteAlbum(HttpSession session, HttpServletRequest req) {
		int album_id = Integer.parseInt(req.getParameter("album_id"));
		Cloudinary cloudinary = new Cloudinary(config);
		Api api = cloudinary.api();
		String albumToDelete = session.getAttribute("username").toString() + "/"
				+ albumDao.getAlbum(album_id).getAlbum_name();
		try {
			api.deleteResourcesByPrefix(albumToDelete, ObjectUtils.emptyMap());
			albumDao.delete(album_id);
			User user = userDao.getUser(session.getAttribute("username").toString());
			user.setNumber_of_albums(user.getNumber_of_albums() - 1);
			userDao.update(user);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/albums", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Album>> getAlbums(HttpSession session) {

		int user_id = userDao.getUser(session.getAttribute("username").toString()).getUser_id();
		List<Album> albums = albumDao.getAlbums(user_id);
		HttpStatus returnStatus = null;
		if (albums != null)
			returnStatus = HttpStatus.OK;
		else
			returnStatus = HttpStatus.NOT_FOUND;

		return new ResponseEntity<List<Album>>(albums, returnStatus);
	}

	@RequestMapping(value = "/album", params = { "album_id" }, method = RequestMethod.GET)
	public ResponseEntity<List<Photo>> getPhotos(HttpServletRequest req, HttpSession session) {
		try {
			int user_id = userDao.getUser(session.getAttribute("username").toString()).getUser_id();
			int album_id = Integer.parseInt(req.getParameter("album_id"));
			ResponseEntity<List<Photo>> returnEntity = new ResponseEntity<List<Photo>>(
					photoDao.getPhotos(album_id, user_id), HttpStatus.OK);
			return returnEntity;
		} catch (NumberFormatException e) {
			return new ResponseEntity<List<Photo>>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/upload", params = { "album_id" }, method = RequestMethod.POST)
	public ResponseEntity<Void> uploadFile(MultipartHttpServletRequest request, HttpSession session) {
		
		System.setProperty("proxyHost", "hysbc1.persistent.co.in");
		System.setProperty("proxyPort", "8080");
		int album_id = Integer.parseInt(request.getParameter("album_id"));
		String username = session.getAttribute("username").toString();
		Iterator<String> itr = request.getFileNames();
		User user = userDao.getUser(username);
		Album album = albumDao.getAlbum(album_id);
		Cloudinary cloudinary = new Cloudinary(config);
		while (itr.hasNext()) {
			MultipartFile file = request.getFile(itr.next());

			// Temporary path..This will be updated to server's filesystem when
			// deployed
			File temp = new File("C:/Project/" + file.getOriginalFilename());

			try {
				file.transferTo(temp);
				// Will be set as environment variables when deployed
				String uploadFolder = session.getAttribute("username").toString() + "/" + album.getAlbum_name();
				Map upload_params = ObjectUtils.asMap("folder", uploadFolder);
				Map uploadResult = cloudinary.uploader().upload(temp, upload_params);

				System.out.println((String) uploadResult.get("public_id"));

				Photo photo = new Photo();
				photo.setAlbum(album);
				photo.setCreation_date(new Date());
				photo.setPath(uploadResult.get("url").toString());
				photo.setUser(user);
				photoDao.save(photo);
				temp.delete();
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/album/download/{album_name}", produces = "text/plain")
	public ResponseEntity<String> downloadAlbumZip(@PathVariable String album_name) {
		Cloudinary cloudinary = new Cloudinary(config);
		String downloadFolder = "karan" + "/" + album_name;
		Map<String, Object> options = ObjectUtils.asMap("prefixes", "downloadFolder");
		try {
			String[] prefixes = {"karan/Test"};
			String url = cloudinary.downloadArchive(new ArchiveParams().prefixes(prefixes).flattenFolders(true));
			System.out.println(url);
			return new ResponseEntity<String>(url, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error", HttpStatus.NOT_FOUND);
		}
	}
}