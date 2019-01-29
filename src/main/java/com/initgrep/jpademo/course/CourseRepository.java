package com.initgrep.jpademo.course;

import com.initgrep.jpademo.review.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CourseRepository {
    Logger logger = LoggerFactory.getLogger(CourseRepository.class);
    @Autowired
    EntityManager em;

    public Course findById(Long id) {
        return em.find(Course.class, id);
    }

    public void deleteById(Long id) {
        em.remove(findById(id));
    }

    public Course save(Course course) {

        if (course.getId() == null) {
            em.persist(course);
        } else {
            em.merge(course);
        }
        return course;
    }

    public List<Review> findReviewOfCourseById(Long id) {
        Course course = findById(id);
        List<Review> reviews = course.getReviews();
        return reviews;
    }

    public void addReviewToCourse(Long courseId, Review review){
        Course course = findById(courseId);
        course.addReview(review);
        em.persist(review);
    }

    public void addReviewsToCourse(Long courseId, List<Review> reviews){
        Course course = findById(courseId);
        for (Review r : reviews){
            course.addReview(r);
            em.persist(r);
        }
    }
}
