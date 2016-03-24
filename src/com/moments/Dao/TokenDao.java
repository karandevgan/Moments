package com.moments.Dao;

import com.moments.model.Token;
import com.moments.model.User;

public interface TokenDao {
	
	public boolean saveToken(Token token);


	public boolean isTokenValid(String token_value, User user);
	
}
