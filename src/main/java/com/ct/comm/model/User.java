package com.ct.comm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.springframework.cache.annotation.Cacheable;

@Entity
@Cacheable
@Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
@Table( name = "user",
		uniqueConstraints = { @UniqueConstraint( columnNames = { "email" } ), @UniqueConstraint( columnNames = { "contact" } ) } )
public class User {
	@Id
	@Column( name = "user_id" )
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;

	@NotBlank
	@Size( min = 3, max = 50 )
	@Column( name = "first_name", nullable = false )
	private String firstName;

	@NotBlank
	@Size( min = 3, max = 50 )
	@Column( name = "last_name", nullable = false )
	private String lastName;

	@NaturalId
	@NotBlank
	@Size( max = 50 )
	@Email
	@Column( name = "email", nullable = false )
	private String email;

	@NotBlank
	@Size( min = 10, max = 10 )
	@Column( name = "contact", nullable = false )
	private String contact;

	public User() {}

	public User( String firstName, String lastName, String email, String contact ) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.contact = contact;
	}

	public long getId() {
		return id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName( String firstName ) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName( String lastName ) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact( String contact ) {
		this.contact = contact;
	}

	public void setEmail( String email ) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "USER:\t" + firstName + " " + lastName + " (" + contact + ", " + email + ")";
	}
}
