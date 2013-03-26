package dao

import models.{Grade, Student, Enrollment, Exam}
import play.api.db.DB
import play.api.Play.current
import anorm._
import anorm.SqlParser._

/**
 * @author Slava Pak
 */
trait ExamDao {

  def all: List[Exam]

  def scored: List[Exam]

  def addGrade(studentId: Long, enrollmentId: Long, grade: Grade.Value)

}

trait ExamDaoComponent {

  def examDao: ExamDao

}

trait AnormExamDaoComponent extends ExamDaoComponent {

  class AnormExamDao extends ExamDao {

    def all = DB.withConnection {
      implicit c =>
        SQL("""
             select v.e_id, v.e_title, v.s_id, v.s_name, e_s.grade from
             (select e.id as e_id, e.title as e_title, s.id as s_id, s.name as s_name from enrollment e, student s) v
             left join
             enrollment_student e_s
             on e_s.enrollment_id = v.e_id and e_s.student_id = v.s_id
            """).
          as(long("e_id")~str("e_title")~long("s_id")~str("s_name")~str("grade").? *).
          map {
          case eId~eTitle~sId~sName~grade =>
            Exam(Enrollment(eId, eTitle), Student(sId, sName), grade.map(Grade.withName(_)))
        }
    }

    def scored = DB.withConnection {
      implicit c =>
      SQL("""
             select e.id as e_id, e.title as e_title, s.id as s_id, s.name as s_name, e_s.grade as grade
             from enrollment e, student s, enrollment_student e_s
             where e.id = e_s.enrollment_id and s.id = e_s.student_id
             order by e_title, s_name
          """).
        as(long("enrollment.id")~str("enrollment.title")~long("student.id")~str("student.name")~str("grade") *).
        map {
        case eId~eTitle~sId~sName~grade =>
          Exam(Enrollment(eId, eTitle), Student(sId, sName), Some(Grade.withName(grade)))
      }
    }

    def addGrade(studentId: Long, enrollmentId: Long, grade: Grade.Value) {
      DB.withConnection {
        implicit c =>
          SQL("merge into enrollment_student key(enrollment_id, student_id) values ({e_id}, {s_id}, {g})").
            on("e_id" -> enrollmentId, "s_id" -> studentId, "g" -> grade.toString).executeInsert()
      }
    }

  }

  val examDao = new AnormExamDao
}
