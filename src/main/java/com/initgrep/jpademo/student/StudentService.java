package com.initgrep.jpademo.student;

import java.util.List;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.initgrep.jpademo.address.Passport;
import com.initgrep.jpademo.course.Course;

@Service
@Transactional
public class StudentService {

	Logger logger = LoggerFactory.getLogger(StudentService.class);

	@Autowired
	private StudentRepository repository;

	public void showAllCourses(Long id) {
		List<Course> courses = repository.getStudentCourses(id);
		logger.info("courses for student 1001 are {} ", courses);
	}

	public <T> Long getTotalStudentCount(Class<T> clazz) {
		return repository.getCountOfEntity(clazz);
	}

	public <T> List<T> getTotalStudents(Class<T> clazz) {
		return repository.getEntityList(clazz);
	}

	public void getStudentCourses(Long id) {
		Student student = new Student();
		student.setId(id);
		repository.getStudentCourses(student)
			.forEach(c -> logger.info("course ={}",c));
	}
	
	public void getStudentPassport(Long id) {
		Student student = new Student();
		student.setId(id);
		Passport passport = repository.fetchStudentPassport(student);
		logger.info("passport = {}",passport);
	}

	public void getStudentAddresses(Long id) {
		Student student = new Student();
		student.setId(id);
		repository.getStudentAddresses(student)
			.forEach(c -> logger.info("address ={}, {}",c.getCountry(), c.getCity().getCityName()));
	}
}
