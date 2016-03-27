package com.moments.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity
@Table(name = "tblAlbum")
public class Album implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int album_id;
	
	@Column(nullable=false)
	private String album_name;
	
	@Column(nullable=true)
	private String description;
	
	@Column(nullable=false)
	private Date creation_date;
	
	@Column(nullable=false)
	private Date last_modified;
	
	@JsonIgnore
	@OneToMany(mappedBy = "album", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JsonManagedReference
	private List<Photo> photos;

	@Column(nullable=true)
	private String coverphoto;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	@JsonBackReference
	private User user;
	
	@JsonIgnore
	@JsonManagedReference
	@ManyToMany(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch=FetchType.LAZY, targetEntity = User.class)
	@JoinTable(name="tblSharedAlbums", joinColumns = {@JoinColumn(name="album_id")}, inverseJoinColumns = {@JoinColumn(name="user_id")})
	private Set<User> shared_users;
	
	public int getAlbum_id() {
		return album_id;
	}

	public void setAlbum_id(int album_id) {
		this.album_id = album_id;
	}

	public String getAlbum_name() {
		return album_name;
	}

	public void setAlbum_name(String album_name) {
		this.album_name = album_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	public Date getLast_modified() {
		return last_modified;
	}

	public void setLast_modified(Date last_modified) {
		this.last_modified = last_modified;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCoverphoto() {
		return coverphoto;
	}

	public void setCoverphoto(String coverphoto) {
		this.coverphoto = coverphoto;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public Set<User> getShared_users() {
		return shared_users;
	}

	public void setShared_users(Set<User> shared_users) {
		this.shared_users = shared_users;
	}
}
