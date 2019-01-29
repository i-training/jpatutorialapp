package com.initgrep.jpademo.student;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.initgrep.jpademo.address.Address;
import com.initgrep.jpademo.address.Passport;
import com.initgrep.jpademo.course.Course;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy ="student", fetch= FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>(); 

    @OneToOne(mappedBy="student", fetch = FetchType.LAZY)
    private Passport passport;
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "student_course",
            joinColumns = {@JoinColumn(name = "STUDENT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "COURSE_ID")}
    )
    private List<Course> courses = new ArrayList<>();

    public Student(String name) {
        this.name = name;
    }

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
     

    public List<Address> getAddresses() {
		return addresses;
	}

	public void addAddresses(Address address) {
		address.setStudent(this);
		this.addresses.add(address);
	}
	
	public void removeAddresses(Address address) {
		this.addresses.remove(address);
	}

	public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        course.addStudent(this);
        this.courses.add(course);
    }

    public void removeCourse(Course course){
        this.courses.remove(course);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
