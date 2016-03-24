package com.moments.DaoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.moments.Dao.PhotoDao;
import com.moments.hibernateDaoSupport.CustomHibernateDaoSupport;
import com.moments.model.Album;
import com.moments.model.Photo;
import com.moments.model.User;

public class PhotoDaoImpl extends CustomHibernateDaoSupport implements PhotoDao {

	@Override
	public void save(Photo photo) {
		getHibernateTemplate().save(photo);
	}

	@Override
	public void update() {

	}

	@Override
	public void delete() {

	}

	@Override
	public void details() {

	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isRegistered(int album_id) {
		boolean returnValue = false;
		String hql = "from Album where album_id=?";
		List album = getHibernateTemplate().find(hql, album_id);
		if (album.size() > 0) {
			returnValue = true;
		}
		return returnValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> getPhotos(Album album, User user, int call) {
		// String hql = "from Photo where album_id=? and user_id=?";
		// List photos = getHibernateTemplate().find(hql, album_id, user_id);
		// return (List<Photo>) photos;

		Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(Photo.class);
		Criterion album_criteria =  Restrictions.eq("album", album);
		Criterion user_criteria =  Restrictions.eq("user", user);
		criteria.add(Restrictions.and(album_criteria, user_criteria));
		criteria.setFirstResult(call);
		criteria.setMaxResults(10);
		System.out.println(criteria.list().size());
		return criteria.list();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int getTotalPhotos(User user) {
		String hql = "from Photo where user_id=?";
		List photos = getHibernateTemplate().find(hql, user.getUser_id());
		return photos.size();
	}

}
