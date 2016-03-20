package com.moments.DaoImpl;

import java.util.List;

import com.moments.Dao.PhotoDao;
import com.moments.hibernateDaoSupport.CustomHibernateDaoSupport;
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

	@Override
	public List<Photo> getPhotos(int album_id, int user_id) {
		String hql = "from Photo where album_id=? and user_id=?";
		List photos = getHibernateTemplate().find(hql, album_id, user_id);
		return (List<Photo>) photos;
	}

	@Override
	public int getTotalPhotos(User user) {
		String hql = "from Photo where user_id=?";
		List photos = getHibernateTemplate().find(hql, user.getUser_id());
		return photos.size();
	}

}
