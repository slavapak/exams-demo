package di

import controllers.ExamsComponent
import services.ExamServiceComponentImpl
import dao.{AnormExamDaoComponent, AnormEnrollmentDaoComponent, AnormStudentDaoComponent}


package object boot
  extends ExamsComponent
  with ExamServiceComponentImpl /* in tests you can replace any implementation with other */
  with AnormExamDaoComponent
  with AnormEnrollmentDaoComponent
  with AnormStudentDaoComponent {

  val exams = new Exams

}