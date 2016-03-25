package com.moments.web.controller;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.moments.model.Album;
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

	@RequestMapping(value = "/user/album/{album_name}")
	public ModelAndView showAlbum(@PathVariable String album_name) {
		if (session.getAttribute("username") == null) {
			return new ModelAndView("redirect:" + redirectHome);
		}
		String username = session.getAttribute("username").toString();
		ModelAndView mv = new ModelAndView();
		Album album = service.getAlbum(album_name, service.getUser(username).getUser_id());

		if (album != null) {
			mv.addObject("album_name", album.getAlbum_name());
			mv.setViewName("showAlbum");
		} else {
			mv.setViewName("404");
		}
		return mv;
	}

	@RequestMapping(value = "/user/upload/{album_name}")
	public ModelAndView uploadImage(@PathVariable String album_name) {
		if (session.getAttribute("username") == null) {
			return new ModelAndView("redirect:" + redirectHome);
		}
		String username = session.getAttribute("username").toString();
		ModelAndView mv = new ModelAndView();
		Album album = service.getAlbum(album_name, service.getUser(username).getUser_id());
		if (album != null) {
			mv.addObject("album_name", album.getAlbum_name());
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

	@RequestMapping(value = "/sharealbum/{album_link}")
	public ModelAndView shareAlbumUsingLink(@PathVariable String album_link, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView();
		System.out.println("Inside share link");
		if (album_link != null) {
			try {
				byte[] data = Base64.getUrlDecoder().decode(album_link);
				ByteArrayInputStream bais = new ByteArrayInputStream(data);
				ObjectInputStream ois = new ObjectInputStream(bais);

				Album album = (Album) ois.readObject();
				mv.setViewName("showSharedAlbum");
				mv.addObject("album_name", album.getAlbum_name());
				mv.addObject("album_id", album.getAlbum_id());
			} catch (Exception e) {
				mv.setViewName("404");
			}
		} else {
			mv.setViewName("400");
		}
		return mv;
	}
}
