package com.moments.DaoImpl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.moments.Dao.UserDao;
import com.moments.hibernateDaoSupport.CustomHibernateDaoSupport;
import com.moments.model.Album;
import com.moments.model.User;

@Transactional
public class UserDaoImpl extends CustomHibernateDaoSupport implements UserDao {

	@Override
	public boolean update(User user) {
		try {
			getHibernateTemplate().update(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void delete() {

	}

	@Override
	public boolean save(User user) {
		try {
			String hashedPassword = ((Integer) user.getPassword().hashCode())
					.toString();
			user.setPassword(hashedPassword);
			user.setCreation_date(new Date());
			user.setLast_activity(new Date());
			user.setNumber_of_albums(0);
			getHibernateTemplate().save(user);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isRegistered(String username) {
		boolean returnValue = false;
		String hql = "from User where username=?";
		List user = getHibernateTemplate().find(hql, username);
		if (user.size() > 0) {
			returnValue = true;
		}
		return returnValue;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isEmailRegistered(String email) {
		boolean returnValue = false;
		String hql = "from User where email=?";
		List user = getHibernateTemplate().find(hql, email);
		if (user.size() > 0) {
			returnValue = true;
		}
		return returnValue;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public User getUser(User user) {
		if (isRegistered(user.getUsername())) {
			String hql = "from User where username=? and password=?";
			String hashedPassword = ((Integer) user.getPassword().hashCode())
					.toString();
			List users = getHibernateTemplate().find(hql, user.getUsername(),
					hashedPassword);
			if (users.size() > 0)
				return (User) users.get(0);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public User getUser(int user_id) {
		String hql = "from User where username=? and password=?";

		List users = getHibernateTemplate().find(hql, user_id);
		if (users.size() > 0)
			return (User) users.get(0);

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public User getUser(String username) {
		if (isRegistered(username)) {
			String hql = "from User where username=?";
			List users = getHibernateTemplate().find(hql, username);
			if (users.size() > 0)
				return (User) users.get(0);
		}
		return null;
	}
	
	@Override
	public Set<Album> getSharedAlbums(User user) {
		User userObj = getUser(user.getUsername());
		getHibernateTemplate().initialize(userObj.getShared_album());
		return userObj.getShared_album();
	}

}
