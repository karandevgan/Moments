package com.moments.DaoImpl;

import java.util.List;

import com.moments.Dao.PhotoDao;
import com.moments.hibernateDaoSupport.CustomHibernateDaoSupport;
import com.moments.model.Photo;

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
	public List<Photo> getPhotos(int album_id) {
		if(isRegistered(album_id))
		{
			String hql="from Photo where album_id=?";
			List photos=getHibernateTemplate().find(hql,album_id);
			return (List<Photo>)photos;

		}
		else
		{
			System.out.println("No album found");
			return null;
		}
	}
	
	
	


	
}
