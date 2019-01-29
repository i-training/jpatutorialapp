package com.initgrep.jpademo.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class ReviewRepository {

    @Autowired
    EntityManager em;

    public Review findById(Long id) {
        return em.find(Review.class, id);
    }

    public void deleteById(Long id) {
         em.remove(findById(id));
    }

    public Review save(Review Review){

        if(Review.getId() == null){
            em.persist(Review);
        }else{
            em.merge(Review);
        }
        return Review;
    }

    public void saveReviewWithCourse(){

    }
}
