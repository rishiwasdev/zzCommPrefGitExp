package com.ct.comm.model;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class School {
	@NotBlank
	@Size(max = 200)
	@Column(name = "addressLine1")
	private String addressLine1;

	@NotNull
	@Size(max = 200)
	@Column(name = "addressLine2")
	private String addressLine2;

	@NotNull
	@Size(max = 100)
	@Column(name = "city")
	private String city;

	@NotNull
	@Size(max = 100)
	@Column(name = "district")
	private String district;

	@NotNull
	@Size(max = 100)
	@Column(name = "state")
	private String state;

	@NotNull
	@Min(100000)
	@Max(999999)
	@Column(name = "zipCode")
	private Integer zipCode;

	public School() {

	}

	public School(String addressLine1, String addressLine2, String city, String district, String state, int zipCode) {
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.district = district;
		this.zipCode = zipCode;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "Address [addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", city=" + city
				+ ", district=" + district + ", state=" + state + ", zipCode=" + zipCode + "]";
	}

}