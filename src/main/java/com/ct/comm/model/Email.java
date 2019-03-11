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
@Table( name = "email" )
public class Email {
	@Id
	@Column( name = "id" )
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;

	@Size( min = 1, max = 50 )
	@Column( name = "subject" )
	private String subject;

	@Size( min = 0, max = 50 )
	@Column( name = "body" )
	private String body;

	@Column( name = "sent", nullable = false )
	private Boolean sent=false;

	@ManyToOne
	@JoinColumn( name = "user_id", nullable = false )
	private User user;

	public Email() {}

	public Email( String subject, String body, Boolean sent ) {
		this.subject = subject;
		this.body = body;
		this.sent = sent;
	}

	public long getId() {
		return id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject( String subject ) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody( String body ) {
		this.body = body;
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
		return "Subject: '" + subject + "'\nMail Content: " + body;
	}
}
