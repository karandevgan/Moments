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

@Controller
public class ViewController {
	@Autowired
	public UserDao userDao;

	@Autowired
	public AlbumDao albumDao;

	@Autowired
	public PhotoDao photoDao;

	@Autowired
	public HttpSession session;

	@RequestMapping(value = { "/overview" }, method = RequestMethod.GET)
	public ModelAndView getUserOverview() {
		User user = userDao.getUser(session.getAttribute("username").toString());
		int number_of_albums = user.getNumber_of_albums();
		int number_of_photos = photoDao.getTotalPhotos(user);
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
		session.removeAttribute("username");
		return new ModelAndView("redirect:home");
	}

	@RequestMapping(value = "/user/album/{album_id}")
	public ModelAndView showAlbum(@PathVariable int album_id) {
		ModelAndView mv = new ModelAndView();
		Album album = albumDao.getAlbum(album_id);
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
		ModelAndView mv = new ModelAndView();
		Album album = albumDao.getAlbum(album_id);
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
		return new ModelAndView("allimages");
	}
}
