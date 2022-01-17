CREATE TABLE course_student (
                                `course_id` bigint NOT NULL,
                                `student_id` bigint NOT NULL,
                                PRIMARY KEY (`course_id`,`student_id`),
                                FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
                                FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
);
