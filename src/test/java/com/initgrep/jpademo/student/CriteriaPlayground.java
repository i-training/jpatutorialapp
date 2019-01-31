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

import com.initgrep.jpademo.address.Address;
import com.initgrep.jpademo.address.Passport;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CriteriaPlayground {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;
	
	
	/*
	 * JOIN ->
	 * Get all the Records with student along with the address
	 */
	
	@Test
	public void testCriteriaJoin() {
		log.info("*********** testCriteriaJoin called ************");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = builder.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		Join<Student, Address> join = root.join("addresses");
		cq.multiselect(root.get("id"), root.get("name"), join.get("houseNumber"));
//		cq.where(builder.equal(root.get("id"), 10003L));
		List<Student> resultList = em.createQuery(cq).getResultList();
		resultList.forEach(s -> log.info("{}, {}",s, s.getAddresses()));
		log.info("*********** testCriteriaJoin //END ************");
	}
	
	@Test
	public void testCriteriafetchStudent() {
		log.info("********** testCriteriafetchOne called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = builder.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		cq.select(root);
//		cq.where(builder.equal(root.get("id"), 10003L));
		List<Student> resultList = em.createQuery(cq).getResultList();
		resultList.forEach(s -> log.info("{}", s));
		log.info("********** testCriteriafetchOne //END **************");
	}
	
	@Test
	public void testCriteriafetchPassport() {
		log.info("********** testCriteriafetchPassport called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Passport> cq = builder.createQuery(Passport.class);
		Root<Passport> root = cq.from(Passport.class);
		cq.select(root);
//		cq.where(builder.equal(root.get("id"), 10003L));
		List<Passport> resultList = em.createQuery(cq).getResultList();
		resultList.forEach(s -> log.info("{}", s));
		log.info("********** testCriteriafetchPassport //END **************");
	}

	/**
	 * creates multiple queries
	 *  inner join between student - passport
	 *  for each passport Id, it would have three queries to fetch students 
	 *  due to the fact student is the owner of the relationship
	 */
	@Test
	public void testCriteriafetchStudentWithPassport_autoJoin() {
		log.info("********** testCriteriafetchStudentWithPassport_autoJoin called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Passport> cq = builder.createQuery(Passport.class);
		Root<Student> root = cq.from(Student.class);
		cq.select( root.get("passport"));
//		cq.where(builder.equal(root.get("id"), 10003L));
		List<Passport> resultList = em.createQuery(cq).getResultList();
		resultList.forEach(s -> log.info("{}", s));
		log.info("********** testCriteriafetchStudentWithPassport_autoJoin //END **************");
	}
	
	
	/**
	 * ROOT.JOIN would result in fetching related IDS of the two entities 
	 * and later fetching each of the record by firing one query for each ID.\
	 * 
	 * 
	 * 
	 * using a WHERE clause acts as a FETCH JOIN and would fire only query
	 * 
	 * basically there has to be direction
	 */
	@Test
	public void testCriteriafetchStudentWithPassport_explicitJoin() {
		log.info("********** testCriteriafetchStudentWithPassport_explicitJoin called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Passport> cq = builder.createQuery(Passport.class);
		Root<Student> root = cq.from(Student.class);
		Join<Student, Passport> join = root.join("passport",JoinType.INNER);
		cq.select(join);
//		cq.where(builder.equal(root.get("id"), 10003L));
		List<Passport> resultList = em.createQuery(cq).getResultList();
		resultList.forEach(s -> log.info(" result ->{} -- {} ", s, s.getStudent()));
		log.info("********** testCriteriafetchStudentWithPassport_explicitJoin //END **************");
	}
	
	/**
	 * FETCH JOIN behaves like the actual Inner JOIN as in SQL
	 * It would only use one query to fetch the fetched entity
	 */
	@Test
	public void testCriteriafetchStudentWithPassport_fetchJoin() {
		log.info("********** testCriteriafetchStudentWithPassport_explicitJoin called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = builder.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		root.fetch("passport", JoinType.INNER);
		cq.select( root);
//		cq.where(builder.equal(root.get("id"), 10003L));
		List<Student> resultList = em.createQuery(cq).getResultList();
		resultList.forEach(s -> log.info(" result ->{} - passport -> {}", s, s.getPassport()));
		log.info("********** testCriteriafetchStudentWithPassport_explicitJoin //END **************");
	}
	
	
}
