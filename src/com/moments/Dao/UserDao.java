package com.moments.Dao;

import com.moments.model.User;

public interface UserDao {
	public void save(User user);
	
	public void update();

	public void delete();
	
	public boolean isRegistered(String username);
	
	public boolean isEmailRegistered(String email);
	
	public User getUser(User user);
	
}












