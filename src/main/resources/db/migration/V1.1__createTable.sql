DROP TABLE IF EXISTS `school-application`.registeredCourse CASCADE;
DROP TABLE IF EXISTS `school-application`.Student;
DROP TABLE IF EXISTS `school-application`.course;

CREATE TABLE `school-application`.Student (
	id INT UNSIGNED auto_increment NOT NULL,
	firstname varchar(100) NULL,
	lastname varchar(100) NULL,
	CONSTRAINT student_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

CREATE TABLE `school-application`.course (
	id INT UNSIGNED auto_increment NOT NULL,
	name varchar(100) NOT NULL,
	CONSTRAINT course_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

CREATE TABLE `school-application`.registeredCourse (
	student_id INT UNSIGNED NOT NULL,
	course_id INT UNSIGNED NOT NULL,
	CONSTRAINT courseRegistered_FK FOREIGN KEY (course_id) REFERENCES `school-application`.course(id),
	CONSTRAINT student_FK FOREIGN KEY (student_id) REFERENCES `school-application`.student(id),
	CONSTRAINT courseRegistered_PK PRIMARY KEY (student_id, course_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;