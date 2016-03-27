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
//		Criterion user_criteria =  Restrictions.eq("user", user);
		criteria.add(album_criteria);
		criteria.setFirstResult(call);
		criteria.setMaxResults(10);
		System.out.println(criteria.list().size());
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> getPhotos(Album album, int call) {
		Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(Photo.class);
		Criterion album_criteria =  Restrictions.eq("album", album);
		criteria.add(album_criteria);
		criteria.setFirstResult(call);
		criteria.setMaxResults(10);
		System.out.println(criteria.list().size());
		return criteria.list();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Photo> getTotalPhotos(User user, int call) {
		Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(Photo.class);
		Criterion user_criteria =  Restrictions.eq("user", user);
		criteria.add(user_criteria);
		criteria.setFirstResult(call);
		criteria.setMaxResults(10);
		System.out.println(criteria.list().size());
		return criteria.list();
	}

	@Override
	public boolean delete(String public_id, int user_id) {
		String hql = "delete from Photo where user_id=? and public_id=?";
		int deletedPhotos = getHibernateTemplate().bulkUpdate(hql, user_id, public_id);
		if (deletedPhotos > 0)
			return true;
		else
			return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getImagePath(String public_id) {
		String hql = "from Photo where public_id=?";
		List photo = getHibernateTemplate().find(hql, public_id);
		if (photo.size() > 0) {
			return ((Photo)photo.get(0)).getPath();
		}
		return null;
	}

}
