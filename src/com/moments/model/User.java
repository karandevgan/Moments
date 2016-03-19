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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;


@Entity
@Table(name = "tblUser")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int user_id;
	
	@Column(unique=true, nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String first_name;
	
	@Column(nullable=false)
	private String last_name;
	
	@Column(unique=true, nullable=false)
	private String email;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private Gender gender;
	
	@Column(nullable=false)
	private Date creation_date;
	
	@Column(nullable=false)
	private Date last_activity;

	@Column(nullable=false)
	private Integer number_of_albums;
	
	@Column(nullable=false)
	private Integer number_of_photos;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JsonManagedReference
	private List<Photo> myPhotos;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JsonManagedReference
	private List<Album> albums;
	
	@JsonIgnore
	@JsonManagedReference
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name="tblSharedImages", joinColumns = {@JoinColumn(name="user_id")}, inverseJoinColumns = {@JoinColumn(name="photo_id")})
	private Set<Photo> shared_images;
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_Id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	public Date getLast_activity() {
		return last_activity;
	}

	public void setLast_activity(Date last_activity) {
		this.last_activity = last_activity;
	}

	public List<Photo> getMyPhotos() {
		return myPhotos;
	}

	public void setMyPhotos(List<Photo> myPhotos) {
		this.myPhotos = myPhotos;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	public Integer getNumber_of_albums() {
		return number_of_albums;
	}

	public void setNumber_of_albums(Integer number_of_albums) {
		this.number_of_albums = number_of_albums;
	}
	
	public Integer getNumber_of_photos() {
		return number_of_photos;
	}

	public void setNumber_of_photos(Integer number_of_photos) {
		this.number_of_photos = number_of_photos;
	}

	public Set<Photo> getShared_images() {
		return shared_images;
	}

	public void setShared_images(Set<Photo> shared_images) {
		this.shared_images = shared_images;
	}
}
