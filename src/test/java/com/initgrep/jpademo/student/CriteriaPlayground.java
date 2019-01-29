package com.initgrep.jpademo.student;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.initgrep.jpademo.course.Course;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CriteriaPlayground {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;
	
	
	
	@Test
	public void testCriteriaAutoJoin() {
		log.info("testCriteriaAutoJoin called");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = builder.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		root.fetch("addresses",JoinType.LEFT);
		cq.select(root.get("addresses"));
//		cq.where(builder.equal(root.get("id"), 10001L));
		List<Student> resultList = em.createQuery(cq).getResultList();
		resultList.forEach(s -> log.info("{}",s));
		log.info("testCriteriaAutoJoin //END");
	}
	
	
	@Test
	public void testCriteriaAutoJoin1() {
		log.info("***********estCriteriaAutoJoin1 called **************************");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = builder.createQuery(Course.class);
		Root<Student> root = cq.from(Student.class);
		Join<Student, Course> join = root.join("courses");
		cq.select(root.get("courses"));
		List<Course> resultList = em.createQuery(cq).getResultList();
		resultList.forEach(s -> log.info("{}",s));
		log.info("***********estCriteriaAutoJoin1 called //END**************************");
	}

}
