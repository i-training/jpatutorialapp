package com.initgrep.jpademo.student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
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
import com.initgrep.jpademo.course.Course;
import com.initgrep.jpademo.passport.Passport;

@Entity
public class Student implements Serializable{

	private static final long serialVersionUID = 8859765578654766179L;
	
	@Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy ="student", fetch= FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>(); 

    @OneToOne(fetch = FetchType.LAZY)
    private Passport passport;
    
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "student_course",
            joinColumns = {@JoinColumn(name = "STUDENT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "COURSE_ID")}
    )
    private List<Course> courses = new ArrayList<>();
    
    public Student() {}
    
    public Student(String name) {
        this.name = name;
    }
    
	public Student(Passport e) {
		super();
		this.passport = e;
	}
	
	public Student(Student student, Address e) {
		super();
		this.id = student.getId();
		this.name = student.getName();
		this.addresses.add(e);
	}
	public Student(String name, Address e) {
		super();
		this.name = name;
		this.addresses.add(e);
	}
	public Student( Address e) {
		super();
		this.addresses.add(e);
	}
	public Student(Long id) {
		this.id = id;
	}
	
	public Student(Student student, Passport e) {
		super();
		this.id = student.getId();
		this.name = student.getName();
		this.passport = e;
	}
	
	public Student(Student student, String n) {
		super();
		this.id = student.getId();
		this.name = student.getName();
		this.passport = new Passport(n);
	}
	public Student(String name, String houseNum) {
		this.name = name;
		this.addresses.add(new Address(houseNum));
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
