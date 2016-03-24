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

@Entity
@Table(name="tblToken")
public class Token implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int token_id;
	
	@Column(nullable=false, unique=true)
	private String token_value;
	
	@Column(nullable=false)
	private Date creation_date;
	
	@Column(nullable=false)
	private int expiry_minutes;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	

	public int getToken_id() {
		return token_id;
	}

	public void setToken_id(int token_id) {
		this.token_id = token_id;
	}

	public String getToken_value() {
		return token_value;
	}

	public void setToken_value(String token_value) {
		this.token_value = token_value;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	public int getExpiry_minutes() {
		return expiry_minutes;
	}

	public void setExpiry_minutes(int expiry_minutes) {
		this.expiry_minutes = expiry_minutes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}