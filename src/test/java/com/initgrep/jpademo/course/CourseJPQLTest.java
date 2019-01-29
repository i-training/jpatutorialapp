package com.initgrep.jpademo.course;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseJPQLTest {

     Logger logger = LoggerFactory.getLogger(CourseJPQLTest.class);

    @Autowired
    EntityManager em;



    @Test
    public void getAllCoursesTest_1(){
            List<Course > courses =
                    em.createQuery("select c from Course c",Course.class).getResultList();

            logger.info("result  => {}",courses);
    }

    @Test
    public void searchCourseTest_2(){
        List<Course> courses = em
                .createQuery("select c from Course c where c.name like '%microservices%' ", Course.class)
                .getResultList();

        logger.info("result from searchCourseTest_2 => {} ", courses);
    }

}
