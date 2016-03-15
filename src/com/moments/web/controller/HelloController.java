package com.moments.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.moments.Dao.UserDao;
import com.moments.model.User;

@Controller
public class HelloController {

	@Autowired
	public UserDao userDao;
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView registration()
	{
		return new ModelAndView("signup");		
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ModelAndView saveUser(@RequestBody User user) {
		userDao.save(user);
		return new ModelAndView("login");
	}
	
}