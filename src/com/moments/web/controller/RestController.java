package com.moments.web.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.moments.Dao.AlbumDao;
import com.moments.Dao.PhotoDao;
import com.moments.Dao.UserDao;
import com.moments.model.Album;
import com.moments.model.Photo;
import com.moments.model.User;

@Controller
public class RestController {
	@Autowired
	public UserDao userDao;
	@Autowired
	public AlbumDao albumDao;
	@Autowired
	public PhotoDao photoDao;

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<List<String>> saveUser(@RequestBody User user) {
		boolean usernameExists = userDao.isRegistered(user.getUsername());
		boolean emailExists = userDao.isEmailRegistered(user.getEmail());
		HttpStatus status = null;
		List<String> responseBuilder = new ArrayList<String>();
		if (usernameExists || emailExists) {
			if (usernameExists)
				responseBuilder.add("Username not available");
			if (emailExists)
				responseBuilder.add("Email already registered");
			status = HttpStatus.CONFLICT;
		} else {
			userDao.save(user);
			responseBuilder.add("User Created");
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<List<String>>(responseBuilder, status);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Void> loginUser(@RequestBody User user, HttpSession session) {
		if (userDao.getUser(user) != null) {
			session.setAttribute("username", user.getUsername());
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/validateUser", method = RequestMethod.GET)
	public boolean isUserRegistered(@RequestParam String username) {
		return userDao.isRegistered(username);
	}

	@RequestMapping(value = "/userEmailValidation", method = RequestMethod.GET)
	public boolean isEmailRegistered(@RequestParam String email) {
		return userDao.isEmailRegistered(email);
	}
}