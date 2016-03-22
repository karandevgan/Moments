package com.moments.Dao;

import java.util.List;

import com.moments.model.Album;

public interface AlbumDao {

	public boolean save(Album album);
	
	public void update();
	public void delete(int user_id);
	
	public void details();

	List<Album> getAlbums(int user_id);

	public Album getAlbum(int i);
	
	
	
}
