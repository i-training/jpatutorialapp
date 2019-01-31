package com.initgrep.jpademo.student;

import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.initgrep.jpademo.address.Address;
import com.initgrep.jpademo.course.Course;
import com.initgrep.jpademo.passport.Passport;

@Repository
public class StudentRepository {

	EntityManager em;
	CriteriaBuilder builder;   
	
    
    @Autowired
    public StudentRepository(EntityManager em ) {
    	this.em = em;
    	this.builder = em.getCriteriaBuilder();
    }

    public StudentRepository() {}

	public Student findById(Long id) {
        return em.find(Student.class, id);
    }

    public void deleteById(Long id) {
         em.remove(findById(id));
    }

    public Student save(Student Student){

        if(Student.getId() == null){
            em.persist(Student);
        }else{
            em.merge(Student);
        }
        return Student;
    }
 
    public void saveStudentWithPassport(){
        Passport passport = new Passport("Z1001");
        em.persist(passport);
        Student student = new Student("Mike");
        student.setPassport(passport);
        em.persist(student);
    }

    public List<Course> getStudentCourses(Long id){
        Student student = findById(id);
        return student.getCourses();
    }
    
    
    public <T> Long getCountOfEntity(Class<T> claz) {
    	
    	CriteriaQuery<Long> studentQuery = builder.createQuery(Long.class);
    	Root<T> root = studentQuery.from(claz);
    	Expression<Long> countExpression = builder.count(root);
    	studentQuery.select(countExpression);
    	TypedQuery<Long> typedStudentQuery = em.createQuery(studentQuery);
    	
    	return typedStudentQuery.getSingleResult();
    }
    
    public <T> List<T> getEntityList(Class<T> claz) {
		
    	CriteriaQuery<T> studentQuery = builder.createQuery(claz);
    	Root<T> root = studentQuery.from(claz);
    	Join<Object, Object> join = root.join("addresses");
    	studentQuery.select(join.get("houseNumber"));
    	TypedQuery<T> typedStudentQuery = em.createQuery(studentQuery);
    	
    	return typedStudentQuery.getResultList();
    }
    
    /**
     * fetch student courses -> autojoin
     * @param student
     * @return
     */
    public Stream<Course> getStudentCourses(Student student){
    	CriteriaQuery<Course> cq = builder.createQuery(Course.class);
    	Root<Student> root = cq.from(Student.class);
    	cq.select(root.get("courses"));
    	Predicate studentEqual = builder.equal(root.get(Student_.id), student.getId());
    	cq.where(studentEqual);
    	return em.createQuery(cq).getResultStream();
    }
   
    
    public Passport fetchStudentPassport(Student student){
    	CriteriaQuery<Passport> cq = builder.createQuery(Passport.class);
    	Root<Student> root = cq.from(Student.class);
    	cq.select(root.get(Student_.passport));
    	Predicate studentEqual = builder.equal(root.get(Student_.id), student.getId());
    	cq.where(studentEqual);
    	return em.createQuery(cq).getSingleResult();
    }

    
    public Stream<Address> getStudentAddresses(Student student){
    	CriteriaQuery<Address> cq = builder.createQuery(Address.class);
    	
    	Root<Student> root = cq.from(Student.class);
    	cq.select(root.get("addresses"));
    	
    	Predicate studentEqual = builder.equal(root.get(Student_.id), student.getId());
    	cq.where(studentEqual);
    	return em.createQuery(cq).getResultStream();
    }
    
    public Stream<Address> fetchStudentAddresses(Student student){
    	CriteriaQuery<Address> cq = builder.createQuery(Address.class);
    	
    	Root<Student> root = cq.from(Student.class);
    	cq.select(root.get("addresses"));
    	
    	Predicate studentEqual = builder.equal(root.get(Student_.id), student.getId());
    	cq.where(studentEqual);
    	return em.createQuery(cq).getResultStream();
    }


    
}
