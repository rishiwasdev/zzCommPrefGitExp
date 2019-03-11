package com.ct.comm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.springframework.cache.annotation.Cacheable;

@Entity
@Cacheable
@Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
@Table( name = "communication_config" )
public class Communication {
	@Id
	@Column( name = "comm_id" )
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;

	@Column( name = "freq" )
	private int freq;

	@Enumerated( EnumType.STRING )
	@Column( name = "unit", length = 30 )
	private UnitOfTime timeUnit;

	@NaturalId
	@Enumerated( EnumType.STRING )
	@Column( name = "type", length = 30 )
	private CommunicationType typeOfComm;

	@NaturalId
	@ManyToOne
	@JoinColumn( name = "user_id", nullable = false )
	private User user;

	public Communication() {}

	public Communication( int freq, UnitOfTime timeUnit, CommunicationType typeOfComm ) {
		this.freq = freq;
		this.timeUnit = timeUnit;
		this.typeOfComm = typeOfComm;
	}

	public long getId() {
		return id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq( int freq ) {
		this.freq = freq;
	}

	public UnitOfTime getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit( UnitOfTime timeUnit ) {
		this.timeUnit = timeUnit;
	}

	public User getUser() {
		return user;
	}

	public void setUser( User user ) {
		this.user = user;
	}

	public CommunicationType getTypeOfComm() {
		return typeOfComm;
	}

	public void setTypeOfComm( CommunicationType typeOfComm ) {
		this.typeOfComm = typeOfComm;
	}

	@Override
	public String toString() {
		return "COMMUNICATION:\t Frequency: " + freq + ", TimeUnit: " + timeUnit + ", CommunicationType: " + typeOfComm;
	}
}
