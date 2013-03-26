# Exams schema

# --- !Ups

CREATE SEQUENCE id_seq;
CREATE TABLE enrollment (
    id integer NOT NULL DEFAULT nextval('id_seq'),
    title varchar(255),
    primary key (id)
);
CREATE TABLE student (
    id integer NOT NULL DEFAULT nextval('id_seq'),
    name varchar(255),
    primary key (id)
);

CREATE TABLE enrollment_student (
    enrollment_id integer NOT NULL,
    student_id integer NOT NULL,
    grade char(1) NOT NULL,
    FOREIGN KEY (enrollment_id) REFERENCES enrollment(id),
    FOREIGN KEY (student_id) REFERENCES student(id),
    PRIMARY KEY (enrollment_id, student_id)
);

insert into student (id, name) values (1, 'Brian');
insert into student (id, name) values (2, 'Pavani');
insert into student (id, name) values (3, 'Slava');

insert into enrollment (id, title) values (1, 'Application Design');
insert into enrollment (id, title) values (2, 'RDBMS');
insert into enrollment (id, title) values (3, 'Scala Programming');

insert into enrollment_student (enrollment_id, student_id, grade) values (1, 1, 'A');
insert into enrollment_student (enrollment_id, student_id, grade) values (2, 2, 'A');

# --- !Downs

DROP TABLE enrollment;
DROP TABLE student;
DROP TABLE enrollment_student;
DROP SEQUENCE id_seq;
