package com.initgrep.jpademo.student;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {
	Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudentService service;

    @Test
    public void fetchCoursesForStudent_Test(){
            service.showAllCourses(2001L);
    }

//    @Test
//    @DirtiesContext
//    public void updateAddressOfStudent(){
//        City city = new City("bangalore", "BLR", "Kannada");
//        Address address = new Address("555", "12121",city , "kar", "560076");
//    }
//    
//    @Test
//    @DirtiesContext
//    public void getStudentCount(){
//        Long totalStudentCount = service.getTotalStudentCount(Student.class);
//        logger.info("total count of students = {}",totalStudentCount);
//    }
//    
//    @Test
//    @DirtiesContext
//    public void getCourseCount(){
//        Long totalStudentCount = service.getTotalStudentCount(Course.class);
//        logger.info("total count of students = {}",totalStudentCount);
//    }
//    
//    @Test
//    @DirtiesContext
//    public void getEntityList(){
//        List<Student> totalStudents = service.getTotalStudents(Student.class);
//        logger.info("{}", totalStudents);
//        
////        logger.info("total count of students = {}",totalStudent);
//    }
//    
//    @Test
//    public void getStudentCourses_test() {
//    	service.getStudentCourses(2001L);
//    }
//    
//    @Test
//    public void getStudentPassport_test() {
//    	service.getStudentPassport(2001L);
//    }
//    
    @Test
    public void getStudentAddresses_test() {
    	service.getStudentAddresses(2001L);
    }
   
    /**
     * TODO
     * 1) get specific values
     * 2) perform join
     * 3) use case and having
     * 4) order and groupby
     * 
     */
    
    
}
