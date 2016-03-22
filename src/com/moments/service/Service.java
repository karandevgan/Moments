package com.moments.service;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.ArchiveParams;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.moments.Dao.AlbumDao;
import com.moments.Dao.PhotoDao;
import com.moments.Dao.UserDao;
import com.moments.model.Album;
import com.moments.model.Photo;
import com.moments.model.User;

public class Service {
	@Autowired
	private UserDao userDao;

	@Autowired
	private PhotoDao photoDao;

	@Autowired
	private AlbumDao albumDao;

	private Map config = ObjectUtils.asMap("cloud_name", "kaydewgun",
			"api_key", "757818147311579", "api_secret",
			"Jo1xhkKMAiHSMa1ySvSc48r6qlQ");

	public boolean save(User user) {
		return userDao.save(user);

	}

	public boolean isRegistered(String username) {
		return userDao.isRegistered(username);

	}

	public boolean isEmailRegistered(String email) {
		return userDao.isEmailRegistered(email);
	}

	public User getUser(User user) {
		return userDao.getUser(user);
	}

	public User getUser(String username) {
		return userDao.getUser(username);
	}

	public int getTotalPhotos(User user) {
		return photoDao.getTotalPhotos(user);
	}

	public Album getAlbum(int album_id) {
		return albumDao.getAlbum(album_id);
	}

	public boolean update(User user) {
		return userDao.update(user);

	}

	public boolean createAlbum(User user, Album album) {
		album.setUser(user);
		album.setCreation_date(new Date());
		album.setLast_modified(new Date());
		album.setCoverphoto("/moments/resources/static/corousel1.jpg");
		album.setUser(user);
		boolean isAlbumSaved = albumDao.save(album);
		user.setNumber_of_albums(user.getNumber_of_albums() + 1);
		boolean isUserUpdated = update(user);
		System.out.println(isAlbumSaved && isUserUpdated);
		return isAlbumSaved && isUserUpdated;
	}

	public boolean deleteAlbum(String album_to_delete, int album_id, User user) {
		// Cloudinary cloudinary = new Cloudinary(config);
		// Api api = cloudinary.api();
		try {
			// api.deleteResourcesByPrefix(album_to_delete,
			// ObjectUtils.emptyMap());
			albumDao.delete(album_id);
			user.setNumber_of_albums(user.getNumber_of_albums() - 1);
			update(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean uploadImage(Album album, User user, MultipartFile file) {
		File temp = null;
		try {
			String temp_path = "" + file.getOriginalFilename();
			temp = new File(temp_path);
			file.transferTo(temp);
			// Will be set as environment variables when deployed

			String uploadFolder = user.getUsername() + "/"
					+ album.getAlbum_name();
			Map upload_params = ObjectUtils.asMap("folder", uploadFolder);
			// Cloudinary cloudinary = new Cloudinary(config);
			// Map uploadResult = cloudinary.uploader().upload(temp,
			// upload_params);

			// System.out.println((String) uploadResult.get("public_id"));

			Photo photo = new Photo();
			photo.setAlbum(album);
			photo.setCreation_date(new Date());
			// photo.setPath(uploadResult.get("url").toString());
			photo.setPath(temp_path);
			photo.setUser(user);
			photoDao.save(photo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			temp.delete();
		}
	}

	public String downloadAlbum(String download_folder) {
		Cloudinary cloudinary = new Cloudinary(config);
		try {
			String[] prefixes = { download_folder };
			String url = cloudinary.downloadArchive(new ArchiveParams()
					.prefixes(prefixes).flattenFolders(true));
			System.out.println(url);
			return url;
		} catch (Exception e) {
			return null;
		}
	}
}