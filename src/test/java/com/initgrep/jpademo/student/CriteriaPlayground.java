package com.initgrep.jpademo.student;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.initgrep.jpademo.address.Address;
import com.initgrep.jpademo.course.Course;
import com.initgrep.jpademo.passport.Passport;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CriteriaPlayground {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;

	/*
	 * JOIN -> Get all the Records with student along with the address
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
		resultList.forEach(s -> log.info("{}, {}", s, s.getAddresses()));
		log.info("*********** testCriteriaJoin //END ************");
	}

	/**
	 * Depending on the relationship, if the owner is the other side of the onetoOne
	 * Mapping, selecting the root entity is going to fetch related entity in a one
	 * to one mapping. As a result extra queries would be fired to fetch the data.
	 * 
	 * If the attributes of the root entity are explicitly mentioned in the select
	 * statement, Only one query would be fetched. This is a workaround for
	 * efficiently querying the data.
	 */
	@Test
	public void testCriteriafetchStudent() {
		log.info("********** testCriteriafetchOne called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = builder.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		cq.select(root.get("name"));
		root.get("");
//		cq.where(builder.equal(root.get("id"), 10003L));
		List<Student> resultList = em.createQuery(cq).getResultList();
//		resultList.forEach(s -> log.info("{} - passport ={}", s , s.getPassport()));
		log.info("********** testCriteriafetchOne //END **************");
	}

	/**
	 * Since the Passport is the Owner of the relationship between STUDENT -
	 * PASSPORT only one query would be fired to fetch the data
	 */
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
	 * creates multiple queries inner join between student - passport for each
	 * passport Id, it would have three queries to fetch students due to the fact
	 * student is the owner of the relationship
	 */
	@Test
	public void testCriteriafetchStudentWithPassport_autoJoin() {
		log.info("********** testCriteriafetchStudentWithPassport_autoJoin called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Passport> cq = builder.createQuery(Passport.class);
		Root<Student> root = cq.from(Student.class);
		cq.select(root.get("passport"));
//		cq.where(builder.equal(root.get("id"), 10003L));
		List<Passport> resultList = em.createQuery(cq).getResultList();
//		resultList.forEach(s -> log.info("{}", s));
		log.info("********** testCriteriafetchStudentWithPassport_autoJoin //END **************");
	}

	/**
	 * ROOT.JOIN would result in fetching related IDS of the two entities and later
	 * fetching each of the record by firing one query for each ID.
	 * 
	 * 
	 * 
	 * using a WHERE clause acts as a FETCH JOIN and would fire only query
	 * 
	 * The direction needs to be choosen as to where the data should be fetched
	 * from. And the relationship owner should be the source to fetch the data
	 * 
	 * In case of using CriteriaQuery.multiselect, the appropriate constructor needs
	 * to be present Otherwise it will throw an exception and it is not the case
	 * with Criteria.Queryselect
	 */
	@Test
	public void testCriteriafetchStudentWithPassport_explicitJoin() {
		log.info("********** testCriteriafetchStudentWithPassport_explicitJoin called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Passport> cq = builder.createQuery(Passport.class);
		Root<Student> root = cq.from(Student.class);
		Join<Student, Passport> join = root.join("passport", JoinType.INNER);
		cq.select(join);
//		cq.where(builder.equal(root.get("id"), 10003L));
		List<Passport> resultList = em.createQuery(cq).getResultList();
//		resultList.forEach(s -> log.info(" result ->{} -- {} ", s, s.getStudent()));
		log.info("********** testCriteriafetchStudentWithPassport_explicitJoin //END **************");
	}

	/**
	 * FETCH JOIN behaves like the actual Inner JOIN as in SQL It would only use one
	 * query to fetch the fetched entity
	 */

	@Test
	public void testCriteriafetchStudentWithPassport_fetchJoin() {
		log.info("********** testCriteriafetchStudentWithPassport_explicitJoin called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = builder.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		root.fetch("passport", JoinType.INNER);
		root.fetch("addresses", JoinType.INNER);
		cq.select(root);
//		cq.where(builder.equal(root.get("id"), 10003L));
		List<Student> resultList = em.createQuery(cq).getResultList();
		resultList.forEach(
				s -> log.info(" result ->{} - passport -> {}, address->{}", s, s.getPassport(), s.getAddresses()));
		log.info("********** testCriteriafetchStudentWithPassport_explicitJoin //END **************");
	}

	@Test
	public void testCriteriafetchStudentWithAddress() {
		log.info("*********	* testCriteriafetchStudentWithAddress called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = builder.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		cq.select(root.get("addresses"));
		em.createQuery(cq).getResultList();
		log.info("********** testCriteriafetchStudentWithAddress //END **************");
	}

	/**
	 * In the case @OneToMany relationship test in Criteria API 1) Joins will return
	 * multiple queries. 2) using attributes in the select method is like an
	 * implicit fetch and uses only 1 query
	 *
	 * 7 Queries fired for this join
	 */

	@Test
	public void testCriteriafetchStudentWithAddress_explicitJoin() {
		log.info("********** testCriteriafetchStudentWithAddress_explicitJoin called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = builder.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		Join<Student, Address> join = root.join("addresses");
		join.on(builder.equal(root.get("id"), join.get("student")));
		cq.multiselect(root, join);
		cq.where(builder.equal(root.get("id"), 10003L));
		List<Student> resultList = em.createQuery(cq).getResultList();
//		resultList.forEach(s -> log.info("{} - passport ={}", s , s.getPassport()));
		log.info("********** testCriteriafetchStudentWithAddress_explicitJoin //END **************");
	}

	/**
	 * fetch joins only adds 1 query to the list
	 */

	@Test
	public void testCriteriafetchStudentWithAddress_fetchJoin() {
		log.info("********** testCriteriafetchStudentWithAddress_fetchJoin called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = builder.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		root.fetch("addresses");
		cq.select(root);
//		cq.where(builder.equal(root.get("id"), 10003L));
		List<Student> resultList = em.createQuery(cq).getResultList();
//		resultList.forEach(s -> log.info("{} - passport ={}", s , s.getPassport()));
		log.info("********** testCriteriafetchStudentWithAddress_fetchJoin //END **************");
	}

	/**
	 * for implicit joins only one query
	 */

	@Test
	public void testCriteriafetchStudentWithCourse_implicitJoin() {
		log.info("********** testCriteriafetchStudentWithCourse_implicitJoin called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = builder.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		cq.select(root.get("courses"));

//		cq.where(builder.equal(root.get("id"), 10003L));
		List<Student> resultList = em.createQuery(cq).getResultList();
//		resultList.forEach(s -> log.info("{} - passport ={}", s , s.getPassport()));
		log.info("********** testCriteriafetchStudentWithCourse_implicitJoin //END **************");
	}

	/**
	 * only one course query
	 */
	@Test
	public void testCriteriafetchStudentWithCourse_ExplicitJoin() {
		log.info("********** testCriteriafetchStudentWithCourse_ExplicitJoin called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = builder.createQuery(Course.class);
		Root<Student> root = cq.from(Student.class);
		Join<Student, Course> join = root.join("courses");
		cq.select(join);

//		cq.where(builder.equal(root.get("id"), 10003L));
		List<Course> resultList = em.createQuery(cq).getResultList();
//		resultList.forEach(s -> log.info("{} - passport ={}", s , s.getPassport()));
		log.info("********** testCriteriafetchStudentWithCourse_ExplicitJoin //END **************");
	}

	/**
	 * only one course query
	 */
	@Test
	public void testCriteriafetchStudentWithCourse_fetchJoin() {
		log.info("********** testCriteriafetchStudentWithCourse_fetchJoin called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = builder.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		root.fetch("courses");
		cq.select(root);

//		cq.where(builder.equal(root.get("id"), 10003L));
		List<Student> resultList = em.createQuery(cq).getResultList();
//		resultList.forEach(s -> log.info("{} - passport ={}", s , s.getPassport()));
		log.info("********** testCriteriafetchStudentWithCourse_fetchJoin //END **************");
	}

	@Test
	public void testCriteria_groupby_having() {
		log.info("********** testCriteria_groupby_having called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = builder.createQuery(Student.class);
		Root<Address> root = cq.from(Address.class);
		Join<Address, Student> join = root.join("student");

		Expression<Long> count = builder.count(root.get("id"));
		cq.select(join).groupBy(join.get("id")).having(builder.equal(count, 3));

		List<Student> resultList = em.createQuery(cq).getResultList();
		resultList.forEach(s -> log.info("student = {}", s));

		log.info("********** testCriteria_groupby_having //END **************");
	}

	@Test
	public void testCriteria_SubQuery() {
		log.info("********** testCriteria_SubQuery called ***********");
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = builder.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		cq.select(root);

		Subquery<Long> subquery = cq.subquery(Long.class);
		Root<Address> subRoot = subquery.from(Address.class);
		Join<Address, Student> join = subRoot.join("student");
		Path<Long> idPath = join.get("id");
		Expression<Long> idCountExp = builder.count(subRoot.get("id"));
		subquery.select(idPath).groupBy(idPath).having(builder.equal(idCountExp, 3));
		
		cq.where(root.get("id").in(subquery));
		
		List<Student> resultList = em.createQuery(cq).getResultList();
		resultList.forEach(s -> log.info("student = {}", s));

		log.info("********** testCriteria_SubQuery //END **************");
	}
}
