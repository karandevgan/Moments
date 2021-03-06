package com.moments.Dao;

import java.util.List;

import com.moments.model.Album;
import com.moments.model.User;

public interface AlbumDao {

	boolean save(Album album);

	void update(Album album);

	void delete(String album_name, int user_id);

	void details();

	List<Album> getAlbums(int user_id);

	Album getAlbum(int album_id);
	
	Album getAlbum(int album_id, String album_name);

	boolean isAlbumAvailable(int user_id, String album_name);

	Album getAlbum(String album_name, int user_id);

	void shareAlbumWithUser(int album_id, User share_user);
}
