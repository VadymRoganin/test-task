DROP TABLE IF EXISTS course_student;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS course;

CREATE TABLE student (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `name` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)
);

CREATE TABLE course (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
);

CREATE TABLE course_student (
                                  `course_id` bigint NOT NULL,
                                  `student_id` bigint NOT NULL,
                                  PRIMARY KEY (`course_id`,`student_id`),
                                  FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
                                  FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
);



