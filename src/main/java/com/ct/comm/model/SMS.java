package com.ct.comm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

@Entity
@Cacheable
@Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
@Table( name = "sms" )
public class SMS {
	@Id
	@Column( name = "id" )
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;

	@Size( min = 0, max = 50 )
	@Column( name = "content" )
	private String content;

	@Column( name = "sent", nullable = false )
	private Boolean sent = false;

	@ManyToOne
	@JoinColumn( name = "user_id", nullable = false )
	private User user;

	public SMS() {}

	public SMS( String content, Boolean sent ) {
		this.content = content;
		this.sent = sent;
	}

	public long getId() {
		return id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent( String content ) {
		this.content = content;
	}

	public Boolean getSent() {
		return sent;
	}

	public void setSent( Boolean sent ) {
		this.sent = sent;
	}

	public User getUser() {
		return user;
	}

	public void setUser( User user ) {
		this.user = user;
	}

	@Override
	public String toString() {
		return content;
	}
}
