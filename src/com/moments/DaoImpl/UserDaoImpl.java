package com.moments.DaoImpl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.moments.Dao.UserDao;
import com.moments.model.User;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	@Override
	public void update() {

	}

	@Override
	public void delete() {

	}

	@Override
	public void save(User user) {
		String hashedPassword = ((Integer) user.getPassword().hashCode()).toString();
		user.setPassword(hashedPassword);
		user.setCreation_date(new Date());
		user.setLast_activity(new Date());
		user.setNumber_of_albums(0);
		user.setNumber_of_photos(0);
		getHibernateTemplate().save(user);
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
			String hashedPassword = ((Integer) user.getPassword().hashCode()).toString();
			List users = getHibernateTemplate().find(hql, user.getUsername(), hashedPassword);
			if (users.size() > 0)
				return (User) users.get(0);
		}
		return null;
	}

}
