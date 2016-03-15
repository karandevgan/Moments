package com.moments.DaoImpl;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.moments.Dao.UserDao;
import com.moments.model.User;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao  {

	
	@Override
	public void update() {
		
		
	}

	@Override
	public void delete() {
		
		
	}
	
	@Override
	public void save(User user) {
	
		getHibernateTemplate().save(user);
	}

	
}
