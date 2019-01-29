package com.initgrep.jpademo.student;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.initgrep.jpademo.address.Address;
import com.initgrep.jpademo.address.Passport;
import com.initgrep.jpademo.course.Course;

@StaticMetamodel(Student.class)
public class Student_ {
	public static SingularAttribute<Student, Long> id;
	public static SingularAttribute<Student, String> name;
	public static SingularAttribute<Student, Address> address;
	public static SingularAttribute<Student, Passport> passport;
	public static ListAttribute<Student, Course> courses;
	
	
	
	
	
		
}
