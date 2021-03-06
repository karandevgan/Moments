package com.moments.Dao;

import java.util.Set;

import com.moments.model.Album;
import com.moments.model.User;

public interface UserDao {
	public boolean save(User user);
	
	public boolean update(User user);

	public void delete();
	
	public boolean isRegistered(String username);
	
	public boolean isEmailRegistered(String email);
	
	public User getUser(User user);

	public User getUser(String username);

	User getUser(int user_id);

	Set<Album> getSharedAlbums(User user);
}












