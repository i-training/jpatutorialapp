package com.initgrep.jpademo;

import com.initgrep.jpademo.student.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpademoApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(JpademoApplication.class);

    @Autowired
    StudentRepository repository;
    public static void main(String[] args) {
        SpringApplication.run(JpademoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {



    }

}

