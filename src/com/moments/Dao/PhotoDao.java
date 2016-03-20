package com.moments.Dao;

import java.util.List;

import com.moments.model.Photo;
import com.moments.model.User;

public interface PhotoDao {
	
	public void save(Photo photo);
	
	public void update();
	
	public void delete();
	
	public void details();
	
	public int getTotalPhotos(User user);
	
	public boolean isRegistered(int album_id);
	
	List<Photo> getPhotos(int album_id, int user_id);
}