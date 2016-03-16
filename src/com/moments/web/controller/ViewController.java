package com.moments.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.moments.Dao.UserDao;

@Controller
public class ViewController {
	@Autowired
	public UserDao userDao;

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String getHomepage() {
		return "home";
	}

	@RequestMapping(value = "/home-login")
	public String homelogin() {
		return "userhome";
	}
}
