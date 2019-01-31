

INSERT INTO PASSPORT(id, number) VALUES (4001, 'E1001');
INSERT INTO PASSPORT(id, number) VALUES (4002, 'E1002');
INSERT INTO PASSPORT(id, number) VALUES (4003, 'E1003');

INSERT INTO STUDENT(id,name,passport_id) VALUES (2001, 'Irshad', 4001);
INSERT INTO STUDENT(id,name,passport_id) VALUES (2002, 'Ahmad', 4002);
INSERT INTO STUDENT(id,name,passport_id) VALUES (2003, 'Sheikh', 4003);



INSERT INTO  ADDRESS ( ID, CITY_CODE,CITY_LANG,CITY_NAME,COUNTRY,HOUSE_NUMBER,STREET,ZIP_CODE,STUDENT_ID)
VALUES(6001,'BLR','KANADIGA','BANGALORE','INDIA','545','10th t cross','560076',2001);
INSERT INTO  ADDRESS ( ID, CITY_CODE,CITY_LANG,CITY_NAME,COUNTRY,HOUSE_NUMBER,STREET,ZIP_CODE,STUDENT_ID)
VALUES(6002,'KMR','Kahmirii','Kashmir','INDIA','111','1212 t cross','190001',2001);
INSERT INTO  ADDRESS ( ID, CITY_CODE,CITY_LANG,CITY_NAME,COUNTRY,HOUSE_NUMBER,STREET,ZIP_CODE,STUDENT_ID)
VALUES(6003,'BLR','KANADIGA','BANGALORE','INDIA','545','10th t cross','560076',2002);
INSERT INTO  ADDRESS ( ID, CITY_CODE,CITY_LANG,CITY_NAME,COUNTRY,HOUSE_NUMBER,STREET,ZIP_CODE,STUDENT_ID)
VALUES(6004,'KMR','Kahmirii','Kashmir','INDIA','111','1212 t cross','190001',2002);
INSERT INTO  ADDRESS ( ID, CITY_CODE,CITY_LANG,CITY_NAME,COUNTRY,HOUSE_NUMBER,STREET,ZIP_CODE,STUDENT_ID)
VALUES(6005,'BLR','KANADIGA','BANGALORE','INDIA','545','10th t cross','560076',2003);
INSERT INTO  ADDRESS ( ID, CITY_CODE,CITY_LANG,CITY_NAME,COUNTRY,HOUSE_NUMBER,STREET,ZIP_CODE,STUDENT_ID)
VALUES(6006,'KMR','Kahmirii','Kashmir','INDIA','111','1212 t cross','190001',2003);



INSERT INTO course(id,name, created_date, updated_date) VALUES (1001,'introduction to bootstrap',sysdate(),sysdate());
INSERT INTO course(id,name, created_date, updated_date) VALUES (1002,'introduction to core Java',sysdate(),sysdate());
INSERT INTO course(id,name, created_date, updated_date) VALUES (1003,'introduction to Spring core',sysdate(),sysdate());
INSERT INTO course(id,name, created_date, updated_date) VALUES (1004,'introduction to Spring Boot',sysdate(),sysdate());
INSERT INTO course(id,name, created_date, updated_date) VALUES (1005,'introduction to Hibernate',sysdate(),sysdate());
INSERT INTO course(id,name, created_date, updated_date) VALUES (1006,'introduction to Spring Rest',sysdate(),sysdate());

INSERT INTO REVIEW(id,rating,description,course_id) VALUES  (5001,'2','great course Bootstrap',1001);
INSERT INTO REVIEW(id,rating,description,course_id) VALUES  (5002,'3','wonderful course Bootstrap',1001);
INSERT INTO REVIEW(id,rating,description,course_id) VALUES  (5003,'3','awesome course Bootstrap',1001);

INSERT INTO REVIEW(id,rating,description,course_id) VALUES  (5004,'2','great course CoreJava',1002);
INSERT INTO REVIEW(id,rating,description,course_id) VALUES  (5005,'3','wonderful course CoreJava',1002);
INSERT INTO REVIEW(id,rating,description,course_id) VALUES  (5006,'3','awesome course CoreJava',1002);


INSERT INTO REVIEW(id,rating,description,course_id) VALUES  (5007,'2','great course Spring Core',1003);
INSERT INTO REVIEW(id,rating,description,course_id) VALUES  (5008,'3','wonderful course Spring Core',1003);
INSERT INTO REVIEW(id,rating,description,course_id) VALUES  (5009,'3','awesome course Spring Core',1003);


INSERT INTO STUDENT_COURSE(student_id,course_id) VALUES (2001,1001);
INSERT INTO STUDENT_COURSE(student_id,course_id) VALUES (2001,1002);
INSERT INTO STUDENT_COURSE(student_id,course_id) VALUES (2001,1003);
INSERT INTO STUDENT_COURSE(student_id,course_id) VALUES (2001,1004);
INSERT INTO STUDENT_COURSE(student_id,course_id) VALUES (2002,1001);
INSERT INTO STUDENT_COURSE(student_id,course_id) VALUES (2002,1002);
INSERT INTO STUDENT_COURSE(student_id,course_id) VALUES (2002,1003);
INSERT INTO STUDENT_COURSE(student_id,course_id) VALUES (2002,1004);
INSERT INTO STUDENT_COURSE(student_id,course_id) VALUES (2003,1001);
INSERT INTO STUDENT_COURSE(student_id,course_id) VALUES (2003,1002);
INSERT INTO STUDENT_COURSE(student_id,course_id) VALUES (2003,1003);
INSERT INTO STUDENT_COURSE(student_id,course_id) VALUES (2003,1004);