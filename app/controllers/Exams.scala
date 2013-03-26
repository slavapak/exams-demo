package controllers

import play.api.mvc.{Action, Controller}
import services.ExamServiceComponent
import play.api.data._
import play.api.data.Forms._
import models.Grade

/**
 * @author Slava Pak
 */
trait ExamsComponent {
  this: ExamServiceComponent =>

  class Exams extends Controller {

    def all = Action {
      val students = examService.students
      val enrollments = examService.enrollments
      val scored = examService.scoredExams
      val allExams = examService.allExams
      Ok(views.html.exams(students, enrollments, allExams, scored))
    }

    def grade = Action {
      implicit request =>
        gradingForm.bindFromRequest.fold(
          errors => BadRequest,
          data =>  {
            val (studentId, enrollmentId, grade) = data
            examService.grade(studentId, enrollmentId, Grade.withName(grade))
            Redirect("/")
          }
        )
    }

    val gradingForm = Form[(Long, Long, String)](
    tuple(
      "student" -> longNumber,
      "enrollment" -> longNumber,
      "grade" -> nonEmptyText(1, 1)
    ))

  }

}