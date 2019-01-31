package com.initgrep.jpademo.student;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.initgrep.jpademo.course.Course;
import com.initgrep.jpademo.passport.Passport;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentRepositoryTest {
	Logger logger = LoggerFactory.getLogger(StudentRepositoryTest.class);

	@Autowired
	StudentRepository repository;

	@Autowired
	EntityManager em;

	@Test
	@DirtiesContext
	public void saveStudentWithPassport_test1() {
		logger.info("saveStudentWithPassport_test1 called");
		repository.saveStudentWithPassport();

		Student student = repository.findById(2001L);
		Passport passport = student.getPassport();
	}

	@Test
	@DirtiesContext
	@Transactional
	public void getStudentCourses_test2() {
		logger.info("getStudentCourses_test2 called");
		List<Course> courses = repository.getStudentCourses(2001L);
		logger.info("student courses = {}", courses);
	}


	@Test
	public void testCriteriaQueries_single_select() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<String> criteria = cb.createQuery(String.class);

		Root<Student> studentRoot = criteria.from(Student.class);
		criteria.select(studentRoot.get("name"));
		em.createQuery(criteria).getResultList().stream().forEach(name -> logger.info("name = {}", name));

	}

	@Test
	public void testCriteriaQueries_max() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

		Root<Student> studentRoot = criteria.from(Student.class);
		criteria.select(builder.max(studentRoot.get("id")));

		em.createQuery(criteria).getSingleResult().longValue();

	}

	@Test
	public void testCriteriaQueries_joinsWithmetaModels() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> criteria = builder.createQuery(Student.class);
		
		Root<Student> studentRoot = criteria.from(Student.class);
		studentRoot.join(Student_.courses);
		criteria.select(studentRoot.get("courses"));

		
		List<Student> resultList = em.createQuery(criteria).getResultList();
		logger.info("list = {}", resultList);

	}
	

	@Test
	public void testCriteriaQueries_joinsWithmetaModelsAggregate() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> criteria = builder.createQuery(Student.class);
		
		Root<Student> studentRoot = criteria.from(Student.class);
		Join<Student, Course> studentcourses = studentRoot.join(Student_.courses);
		criteria.where(builder.equal(studentRoot.get(Student_.name), "irshad"));
		
		criteria.select(studentRoot.get(Student_.courses.getName()));
		List<Student> resultList = em.createQuery(criteria).getResultList();
		logger.info("list = {}", resultList);

	}

	@Test
	public void testCriteriaQueries_joinsWithmetaModelsAggregate1() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> criteria = builder.createQuery(Student.class);
		
		Root<Student> studentRoot = criteria.from(Student.class);
		Join<Student, Course> studentcourses = studentRoot.join(Student_.courses);
//		criteria.where(builder.equal(studentRoot.get(Student_.name), "irshad"));
		Predicate nameEqual = builder.equal(studentRoot.get(Student_.name), "irshad");
		Order asc = builder.asc(builder.abs(studentRoot.get(Student_.id)));
		
		
		
		
		criteria.select(studentRoot.get(Student_.courses.getName())).orderBy(asc).where(nameEqual);
		
		List<Student> resultList = em.createQuery(criteria)
									.getResultList();
		logger.info("list = {}", resultList);

	}

}
