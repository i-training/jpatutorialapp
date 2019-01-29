package com.initgrep.jpademo.course;

import com.initgrep.jpademo.review.Review;
import com.initgrep.jpademo.review.ReviewRating;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class CourseRepositoryTest {

    Logger logger = LoggerFactory.getLogger(CourseRepositoryTest.class);

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    EntityManager em;

    @Test
    public void findByIdTest_basic() {
        Course course = courseRepository.findById(1001L);
        assertEquals(
                "introdution to bootstrap 4", course.getName()
        );
    }

    @Test
    @DirtiesContext
    public void deleteByIdTest_basic() {
        courseRepository.deleteById(1002L);
        assertNull(courseRepository.findById(1002L));
    }


    @Test
    @DirtiesContext
    public void saveCourseTest_basic() {
        Course course = new Course("Test basic course");
        courseRepository.save(course);
        logger.info("course = {}", course);
        assertEquals(
                course.getName(), courseRepository.findById(course.getId()).getName());
    }

    @Test
    @Transactional
    public void getReviewsForCourse_basictest1() {
        List<Review> reviews = courseRepository.findReviewOfCourseById(1001L);
        reviews.stream().forEach(r -> logger.info("id= {} ", r.getId()));
    }

    @Test
    @DirtiesContext
    public void addReviewsToCourse_basicTest1() {
        courseRepository.addReviewToCourse(1004L, new Review( ReviewRating.FIVE, "wonderfull review course"));
        courseRepository.addReviewToCourse(1004L, new Review(ReviewRating.FOUR, "wonderfull review course2"));
        courseRepository.addReviewToCourse(1004L, new Review(ReviewRating.THREE, "wonderfull review course3"));
    }

    @Test
    @DirtiesContext
    public void addReviewsToCourse_basicTest2() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review(ReviewRating.FOUR, "collective review 1"));
        reviews.add(new Review(ReviewRating.THREE, "collective review 3"));
        reviews.add(new Review(ReviewRating.TWO, "collective review 4"));
        courseRepository.addReviewsToCourse(1004L, reviews);

    }

    @Test
    public void testCriteriaQueries_join() {
        //select c  from Course c join c.students s

        //1- use criteria builder to create a Criteria Query
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cQuery = cb.createQuery(Course.class);

        //2. Define roots of the tables on which query is performed
        Root<Course> courseRoot = cQuery.from(Course.class);

        //3. define a predicate using criteria builder
//        Predicate isCoursesEmpty = cb.isEmpty(studentRoot.get("courses"));
        Join<Object, Object> students = courseRoot.join("students");

        //4. add predicate
//        cQuery.where(isCoursesEmpty);

        // build the typed query using entity manager and typed query
        TypedQuery<Course> query = em.createQuery(cQuery.select(courseRoot));

        List<Course> resultList = query.getResultList();

    }

    @Test
    public void testCriteriaQueries_leftjoin() {
        //select c  from Course c join c.students s

        //1- use criteria builder to create a Criteria Query
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cQuery = cb.createQuery(Course.class);

        //2. Define roots of the tables on which query is performed
        Root<Course> courseRoot = cQuery.from(Course.class);

        //3. define a predicate using criteria builder
//        Predicate isCoursesEmpty = cb.isEmpty(studentRoot.get("courses"));
        Join<Object, Object> students = courseRoot.join("students", JoinType.LEFT);

        //4. add predicate
//        cQuery.where(isCoursesEmpty);

        // build the typed query using entity manager and typed query
        TypedQuery<Course> query = em.createQuery(cQuery.select(courseRoot));

        List<Course> resultList = query.getResultList();

    }


    @Test
    public void testCriteriaQueries_leftjoin_chained() {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = builder.createQuery(Course.class);

        Root<Course> course = criteriaQuery.from(Course.class);
        Predicate creationDategttoday = builder.greaterThan(course.get("createdDate"), LocalDateTime.now());
        Predicate updatedDateltToday = builder.lessThan(course.get("updatedDate"), LocalDateTime.now());
        criteriaQuery.select(course.get("name"))

                .where(
                        builder.and(creationDategttoday, updatedDateltToday), builder.not(creationDategttoday)
                )
                .orderBy(
                        builder.asc(course.get("name")),
                        builder.asc(course.get("id")),
                        builder.desc(course.get("createdDate"))
                ).
                groupBy(
                  course.get("updatedDate"),
                        course.get("id")
                );
        em.createQuery(criteriaQuery).getResultList();


    }
}
