package com.moments.DaoImpl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;

import com.moments.Dao.AlbumDao;
import com.moments.hibernateDaoSupport.CustomHibernateDaoSupport;
import com.moments.model.Album;

public class AlbumDaoImpl extends CustomHibernateDaoSupport implements AlbumDao {

	@Override
	public void save(Album album) {
		album.setCreation_date(new Date());
		album.setLast_modified(new Date());
		getHibernateTemplate().save(album);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void delete(int album_id) {
		getHibernateTemplate().delete(getAlbum(album_id));
	}

	@Override
	public void details() {
			
	}

	@Override
	public List<Album> getAlbums(int user_id ) {
		String hql= "from Album where user_id=?";
		List albums=getHibernateTemplate().find(hql,user_id);
		if (albums.size() > 0)
			return (List<Album>)albums;
		else
			return null;
	}

	@Override
	public Album getAlbum(int i) {
		String hql = "from Album where album_id=?";
		return (Album) getHibernateTemplate().find(hql,i).get(0);
	}

	
}
