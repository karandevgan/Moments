package com.moments.DaoImpl;

import java.util.Date;
import java.util.List;

import com.moments.Dao.TokenDao;
import com.moments.hibernateDaoSupport.CustomHibernateDaoSupport;
import com.moments.model.Token;
import com.moments.model.User;

public class TokenDaoImpl extends CustomHibernateDaoSupport implements TokenDao {

	@Override
	public boolean saveToken(Token token) {
		try {
			getHibernateTemplate().save(token);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isTokenValid(String token_value) {
		boolean isValid = false;
		Date presentDate = new Date();
		String hql = "from Token where token_value=?";
		List tokenList = getHibernateTemplate().find(hql, token_value);
		if (tokenList.size() > 0) {
			Date creation_date = ((Token) tokenList.get(0)).getCreation_date();
			int expiry_minutes = ((Token) tokenList.get(0)).getExpiry_minutes();
			if (presentDate.getTime() - creation_date.getTime() < expiry_minutes) {
				isValid = true;
			}
		}
		return isValid;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public User getUser(String token_value) {
		String hql = "select T.user from Token T where T.token_value=?";
		List tokenList = getHibernateTemplate().find(hql, token_value);
		if (tokenList.size() > 0) {
			return (User)tokenList.get(0);
		}
		return null;
	}
}
