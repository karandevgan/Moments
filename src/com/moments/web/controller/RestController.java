package com.moments.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.moments.model.User;
import com.moments.service.Service;
import com.moments.service.ValidationService;

@Controller
public class RestController {
	@Autowired
	private Service service;

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<List<String>> saveUser(@RequestBody User user) {
		List<String> responseBuilder = new ArrayList<String>();

		List<String> errorList = ValidationService.validateRegisteredUser(service, user);

		HttpStatus status = null;
		if (errorList.size() > 0) {
			responseBuilder = errorList;
			status = HttpStatus.NOT_ACCEPTABLE;
		} else {
			service.save(user);
			responseBuilder.add("User Created");
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<List<String>>(responseBuilder, status);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<List<String>> loginUser(@RequestBody User user, HttpSession session) {
		List<String> responseList = new ArrayList<String>();

		List<String> errorList = ValidationService.validateSigninUser(service, user);
		if (errorList.size() == 0) {
			if (service.getUser(user) != null) {
				String token = service.getToken(service.getUser(user));
				if (token != null) {
					session.setAttribute("username", user.getUsername());
					responseList.add(token);
					return new ResponseEntity<List<String>>(responseList, HttpStatus.OK);
				}
				else {
					responseList.add("Error Generating Token");
					return new ResponseEntity<List<String>>(responseList, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				errorList.add("Incorrect username or password.");
			}
		}
		System.out.println(errorList.size());
		return new ResponseEntity<List<String>>(errorList, HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/validateuser", method = RequestMethod.GET)
	public ResponseEntity<List<String>> isUserRegistered(@RequestParam String username) {
		HttpStatus returnStatus = null;
		List<String> responseList = new ArrayList<String>();
		if (service.isRegistered(username)) {
			responseList.add("Username already exists");
			returnStatus = HttpStatus.CONFLICT;
		} else {
			responseList.add("Username is available");
			returnStatus = HttpStatus.OK;
		}
		return new ResponseEntity<List<String>>(responseList, returnStatus);
	}
	
	@RequestMapping(value = "/userexists", method = RequestMethod.GET)
	public ResponseEntity<List<String>> isUserExisting(@RequestParam String username) {
		HttpStatus returnStatus = null;
		List<String> responseList = new ArrayList<String>();
		if (service.isRegistered(username)) {
			responseList.add("Username is valid");
			returnStatus = HttpStatus.OK;
		} else {
			responseList.add("Username is not registered");
			returnStatus = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<List<String>>(responseList, returnStatus);
	}

	@RequestMapping(value = "/emailvalidation", method = RequestMethod.GET)
	public ResponseEntity<List<String>> isEmailRegistered(@RequestParam String email) {
		HttpStatus returnStatus = null;
		List<String> responseList = new ArrayList<String>();
		if (service.isEmailRegistered(email)) {
			responseList.add("Email already Registered");
			returnStatus = HttpStatus.CONFLICT;
		} else {
			responseList.add("Email available");
			returnStatus = HttpStatus.OK;
		}
		return new ResponseEntity<List<String>>(responseList, returnStatus);
	}

}