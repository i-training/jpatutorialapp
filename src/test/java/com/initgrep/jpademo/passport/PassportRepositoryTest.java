package com.initgrep.jpademo.passport;

import com.initgrep.jpademo.student.Student;
import com.initgrep.jpademo.student.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PassportRepositoryTest {
    Logger logger = LoggerFactory.getLogger(PassportRepositoryTest.class);

    @Autowired
    PassportRepository repository;

    @Test
    @DirtiesContext
    public void saveStudentWithPassport_test1(){
        logger.info("saveStudentWithPassport_test1 called");

        Passport passport = repository.findById(4001L);
        logger.info("passport = {} ", passport);
        Student student = passport.getStudent();
        logger.info("student = {}",student);
    }


}
