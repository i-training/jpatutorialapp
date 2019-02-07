package com.initgrep.jpademo.address;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.initgrep.jpademo.student.City;
import com.initgrep.jpademo.student.Student;

@Entity
public class Address {

	@Id @GeneratedValue
	private Long id;
    private String houseNumber;
    private String street;
    @Embedded
    private City City;
    private String Country;
    private String zipCode;
    
    @ManyToOne
    private Student student;

    public Address(String houseNumber, String street, City city, String country, String zipCode) {
        this.houseNumber = houseNumber;
        this.street = street;
        City = city;
        Country = country;
        this.zipCode = zipCode;
    }

    
    public Address(Long id, Long StudentId) {
    	this.id = id;
    	
    	this.student = new Student(StudentId);
    }
    
    public Address(String houseNumber) {
		super();
		this.houseNumber = houseNumber;
	}



	public Address() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public City getCity() {
		return City;
	}

	public void setCity(City city) {
		City = city;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", houseNumber=" + houseNumber + ", street=" + street + ", City=" + City
				+ ", Country=" + Country + ", zipCode=" + zipCode + ", student=" + student + "]";
	}
    
	
    
    
}
