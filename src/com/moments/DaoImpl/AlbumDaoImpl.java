package com.moments.DaoImpl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.moments.Dao.AlbumDao;
import com.moments.hibernateDaoSupport.CustomHibernateDaoSupport;
import com.moments.model.Album;
import com.moments.model.User;

@Transactional
public class AlbumDaoImpl extends CustomHibernateDaoSupport implements AlbumDao {

	@Override
	public boolean save(Album album) {
		album.setCreation_date(new Date());
		album.setLast_modified(new Date());
		try {
			getHibernateTemplate().save(album);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void update(Album album) {
		getHibernateTemplate().update(album);
	}

	@Override
	public void delete(String album_name, int user_id) {
		getHibernateTemplate().delete(getAlbum(album_name, user_id));
	}

	@Override
	public void details() {

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Album> getAlbums(int user_id) {
		String hql = "from Album where user_id=?";
		List albums = getHibernateTemplate().find(hql, user_id);
		if (albums.size() > 0)
			return (List<Album>) albums;
		else
			return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isAlbumAvailable(int user_id, String album_name) {
		boolean status = true;
		String hql = "from Album where album_name=? and user_id=?";
		List albums = getHibernateTemplate().find(hql, album_name, user_id);
		if (albums.size() > 0) {
			status = false;
		}
		return status;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Album getAlbum(String album_name, int user_id) {
		String hql = "from Album where album_name=? and user_id=?";
		List albums = getHibernateTemplate().find(hql, album_name, user_id);
		if (albums.size() > 0)
			return (Album) albums.get(0);
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Album getAlbum(int album_id, String album_name) {
		String hql = "from Album where album_id=? and album_name=?";
		List albums = getHibernateTemplate().find(hql, album_id, album_name);
		if (albums.size() > 0)
			return (Album) albums.get(0);
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Album getAlbum(int album_id) {
		String hql = "from Album where album_id=?";
		List albums = getHibernateTemplate().find(hql, album_id);
		if (albums.size() > 0)
			return (Album) albums.get(0);
		return null;
	}
	
	@Override
	public void shareAlbumWithUser(int album_id, User share_user) {
		Album album = getAlbum(album_id);
		getHibernateTemplate().initialize(album.getShared_users());
		album.getShared_users().add(share_user);
		update(album);
	}
}