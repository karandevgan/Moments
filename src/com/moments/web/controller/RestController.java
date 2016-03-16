package com.moments.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.moments.Dao.UserDao;
import com.moments.model.User;

@Controller
public class RestController {

	@Autowired
	public UserDao userDao;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Void> saveUser(@RequestBody User user) {
		if (userDao.isRegistered(user.getUsername()) || userDao.isEmailRegistered(user.getEmail())) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		} else {
			userDao.save(user);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Void> loginUser(@RequestBody User user) {
		if (userDao.getUser(user) != null) {
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