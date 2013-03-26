package services

import models.{Grade, Exam, Enrollment, Student}
import dao.{ExamDaoComponent, EnrollmentDaoComponent, StudentDaoComponent}
import Grade.Value

/**
 * @author Slava Pak
 */
trait ExamService {

  def students: List[Student]

  def enrollments: List[Enrollment]

  def allExams: List[Exam]

  def scoredExams: List[Exam]

  def grade(studentId: Long, enrollmentId: Long, grade: Grade.Value)

}

trait ExamServiceComponent {

  def examService: ExamService

}

trait ExamServiceComponentImpl extends ExamServiceComponent {
  this: StudentDaoComponent
    with EnrollmentDaoComponent
    with ExamDaoComponent =>

  class ExamServiceImpl extends ExamService {

    def students =
      studentDao.all

    def enrollments =
      enrollmentDao.all

    def allExams =
      examDao.all

    def scoredExams =
      examDao.scored

    def grade(studentId: Long, enrollmentId: Long, grade: Grade.Value) {
      examDao.addGrade(studentId, enrollmentId, grade)
    }
  }

  def examService = new ExamServiceImpl

}
