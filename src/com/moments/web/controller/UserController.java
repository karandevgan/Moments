package com.moments.web.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.moments.Dao.AlbumDao;
import com.moments.Dao.PhotoDao;
import com.moments.Dao.UserDao;
import com.moments.model.Album;
import com.moments.model.Photo;
import com.moments.model.User;


@RequestMapping("/user")
@Controller
public class UserController {
	@Autowired
	public UserDao userDao;
	@Autowired
	public AlbumDao albumDao;
	@Autowired
	public PhotoDao photoDao;

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
	public ResponseEntity<List<Photo>> getPhotos(HttpServletRequest req) {
		try {
			int album_id = Integer.parseInt(req.getParameter("album_id"));
			ResponseEntity<List<Photo>> returnEntity = new ResponseEntity<List<Photo>>(photoDao.getPhotos(album_id),
					HttpStatus.OK);
			return returnEntity;
		} catch (NumberFormatException e) {
			return new ResponseEntity<List<Photo>>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="/upload", headers = "'Content-Type': 'multipart/form-data'", method = RequestMethod.POST)
    public void UploadFile(MultipartHttpServletRequest request, HttpServletResponse response) {

        Iterator<String> itr=request.getFileNames();

        MultipartFile file=request.getFile(itr.next());

        String fileName=file.getOriginalFilename();
        System.out.println(fileName);
    }
}