package com.moments.Dao;

import java.util.List;

import com.moments.model.Album;

public interface AlbumDao {

	public void save(Album album);
	
	public void update();
	public void delete();
	
	public void details();

	List<Album> getAlbums(int user_id);

	public Album getAlbum(int i);
	
	
	
}
