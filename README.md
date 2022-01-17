This is the test task for **Metadata**

**Prerequisites:** You need to have installed: Java 17+, Maven 3.6.3+,
Docker 20.10.10+ (lower versions of Maven and Docker may also be fine but weren't tested).
You also need to have **8080** port available on your machine.

**How to run the application** (assuming you are in the app root folder):

`mvn clean install`- build the application

`docker-compose -f src/main/docker/docker-compose.yml up` - start the application with docker-compose.

**Endpoints descriptions:**

Students endpoint:

`GET localhost:8080/students` - retrieve students list, you can add `course_id` param to filter by course id. 
You may put any numeric id or 'none' to filter students which do not have courses assigned. The output is paginated so you can use `page` and `size` params.

`GET localhost:8080/students/{id}` - retrieve student info by id.

`POST localhost:8080/students/` - create student. Request body JSON example: `{"name": "Jack"}`

`PUT localhost:8080/students/` - update student.

`DELETE localhost:8080/students/{id}` - retrieve student info by student id.

Courses endpoint:

`GET localhost:8080/courses` - retrieve courses list, you can add `student_id` param to filter by course id.
You may put any numeric id or 'none' to filter students which do not have courses assigned. The output is paginated so you can use `page` and `size` params.

`GET localhost:8080/courses/{id}` - retrieve course info by course id.

`POST localhost:8080/courses/` - create course. Request body JSON example: `{
"name":"Swift"
}`

`PUT localhost:8080/courses/` - update course.

`DELETE localhost:8080/courses/{id}` - retrieve course info by id.

Relationship endpoint:

`POST localhost:8080/relationships` - create relationship between a course and a student, i.e. register student to a course.
Request body JSON example:
`{"studentId": 1,
"courseId": 2}`

Reports endpoint:

`GET localhost:8080/reports` - get info about relationships between students and courses. You can add `course_id` param to filter by course id and/or `student_id` param to filter by student id.
