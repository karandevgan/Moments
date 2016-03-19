package com.moments.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.moments.Dao.UserDao;

@Controller
public class ViewController {
	@Autowired
	public UserDao userDao;

	@Autowired
	public HttpSession session;

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public ModelAndView getHomepage(HttpServletRequest req) {
		ModelAndView model = new ModelAndView();
		if (session.getAttribute("username") != null) {
			model.addObject("username", session.getAttribute("username"));
			model.setViewName("userhome");
		} else {
			if (req.getParameter("newuser") != null)
				model.addObject("newuser", true);
			else
				model.addObject("newuser", false);
			model.setViewName("home");
		}
		return model;
	}

	@RequestMapping(value = "/logout")
	public ModelAndView logoutUser() {
		session.removeAttribute("username");
		return new ModelAndView("redirect:home");
	}
	
	@RequestMapping(value = "/user/album/{album_id}")
	public ModelAndView showAlbum(@PathVariable int album_id) {
		ModelAndView mv = new ModelAndView("showAlbum");
		mv.addObject("album_id", album_id);
		return new ModelAndView("showAlbum");
	}
	
	@RequestMapping(value = "/user/album/upload")
	public ModelAndView uploadImage(){
		return new ModelAndView("uploadImage");
	}
	
	@RequestMapping(value = "/user/allphotos")
	public ModelAndView allPhotos(){
		return new ModelAndView("allimages");
	}
}
