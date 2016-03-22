package com.moments.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.moments.Dao.AlbumDao;
import com.moments.Dao.PhotoDao;
import com.moments.Dao.UserDao;
import com.moments.model.Album;
import com.moments.model.User;
import com.moments.service.Service;

@Controller
public class ViewController {

	private String redirectHome = "http://localhost:8080/moments";
	
	@Autowired
	private Service service;

	@Autowired
	private HttpSession session;

	@RequestMapping(value = { "/overview" }, method = RequestMethod.GET)
	public ModelAndView getUserOverview() {
		User user = service.getUser(session.getAttribute("username").toString());
		int number_of_albums = user.getNumber_of_albums();
		int number_of_photos = service.getTotalPhotos(user);
		return null;
	}

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
		if (session.getAttribute("username") == null) {
			return new ModelAndView("redirect:" + redirectHome);
		}
		session.removeAttribute("username");
		return new ModelAndView("redirect:home");
	}

	@RequestMapping(value = "/user/album/{album_id}")
	public ModelAndView showAlbum(@PathVariable int album_id) {
		if (session.getAttribute("username") == null) {
			return new ModelAndView("redirect:" + redirectHome);
		}
		ModelAndView mv = new ModelAndView();
		Album album = service.getAlbum(album_id);
		if (album != null) {
			mv.addObject("album_id", album_id);
			mv.addObject("album_name", album.getAlbum_name());
			mv.setViewName("showAlbum");
		} else {
			mv.setViewName("404");
		}
		return mv;
	}

	@RequestMapping(value = "/user/upload/{album_id}")
	public ModelAndView uploadImage(@PathVariable int album_id) {
		if (session.getAttribute("username") == null) {
			return new ModelAndView("redirect:" + redirectHome);
		}
		ModelAndView mv = new ModelAndView();
		Album album = service.getAlbum(album_id);
		if (album != null) {
			mv.addObject("album_name", album.getAlbum_name());
			mv.addObject("album_id", album_id);
			mv.setViewName("uploadImage");
		} else {
			mv.setViewName("404");
		}
		return mv;
	}

	@RequestMapping(value = "/user/allphotos")
	public ModelAndView allPhotos() {
		if (session.getAttribute("username") == null) {
			return new ModelAndView("redirect:" + redirectHome);
		}
		return new ModelAndView("allimages");
	}

	@RequestMapping(value = "/user/profile")
	public ModelAndView profile() {
		if (session.getAttribute("username") == null) {
			return new ModelAndView("redirect:" + redirectHome);
		}
		return new ModelAndView("profile");
	}
}
