package com.initgrep.jpademo.student;

import javax.persistence.Embeddable;

@Embeddable
public class City {

    private String cityName;
    private String cityCode;
    private String cityLang;

    public City(String cityName, String cityCode, String cityLang) {
        this.cityName = cityName;
        this.cityCode = cityCode;
        this.cityLang = cityLang;
    }

    public City() {
    }

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityLang() {
		return cityLang;
	}

	public void setCityLang(String cityLang) {
		this.cityLang = cityLang;
	}

	@Override
	public String toString() {
		return "City [cityName=" + cityName + ", cityCode=" + cityCode + ", cityLang=" + cityLang + "]";
	} 
    
	
     
}
