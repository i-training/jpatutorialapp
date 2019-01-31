package com.initgrep.jpademo.passport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class PassportRepository {

    @Autowired
    EntityManager em;

    public Passport findById(Long id) {
        return em.find(Passport.class, id);
    }

    public void deleteById(Long id) {
         em.remove(findById(id));
    }

    public Passport save(Passport Passport){

        if(Passport.getId() == null){
            em.persist(Passport);
        }else{
            em.merge(Passport);
        }
        return Passport;
    }


}
