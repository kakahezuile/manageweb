package com.xj.bean;

public class City {
	private Integer cityId;
	
	private String city;
	
	private Integer countryId;
	
	private Integer lastUpdate;
	
	private String cityLetter;

	public String getCityLetter() {
		return cityLetter;
	}

	public void setCityLetter(String cityLetter) {
		this.cityLetter = cityLetter;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Integer getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Integer lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
