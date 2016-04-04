package com.moments.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

@Entity
@Table(name = "tblPhoto")
public class Photo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=true)
	private String description;
	
	@Column(nullable=false)
	private String path;
	
	@Column(nullable=false)
	private String thumb_path;
	
	@Column(nullable=false)
	private String slide_path;
	
	@Column(nullable=false)
	private String public_id;
	
	@Column(nullable=false)
	private Date creation_date;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id", nullable=false)
	@JsonBackReference
	private User user;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="album_id", nullable=false)
	@JsonBackReference
	private Album album;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	public String getThumb_path() {
		return thumb_path;
	}

	public void setThumb_path(String thumb_path) {
		this.thumb_path = thumb_path;
	}

	public String getSlide_path() {
		return slide_path;
	}

	public void setSlide_path(String slide_path) {
		this.slide_path = slide_path;
	}

	public String getPublic_id() {
		return public_id;
	}

	public void setPublic_id(String public_id) {
		this.public_id = public_id;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	@JsonProperty
	public User getUser() {
		return user;
	}

	@JsonIgnore
	public void setUser(User user) {
		this.user = user;
	}

	@JsonProperty
	public Album getAlbum() {
		return album;
	}

	@JsonIgnore
	public void setAlbum(Album album) {
		this.album = album;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Photo other = (Photo) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
