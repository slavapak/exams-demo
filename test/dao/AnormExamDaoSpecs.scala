package dao

import org.scalatest.path.FunSpec

import play.api.db.DB
import play.api.Play.current
import anorm._
import org.specs2.matcher.ShouldMatchers
import models.Grade
import play.api.test.FakeApplication
import play.api.test.Helpers._

/**
 * @author Slava Pak
 */
class AnormExamDaoSpecs extends FunSpec with ShouldMatchers {

  class Context extends AnormExamDaoComponent

  val context = new Context

  describe("AnormExamDao should") {
    it("add new grade for enrollment-student pair if not yet exists") {
      running(FakeApplication()) {
        assert(count(2) == 0)
        context.examDao.addGrade(1, 2, Grade.A)
        count(2) should equalTo(1L)
      }
    }

    it("update grade for enrollment-student pair if already exists") {
      running(FakeApplication()) {
        assert(count(1) == 1)
        context.examDao.addGrade(1, 1, Grade.A)
        count(1) should equalTo(1L)
      }
    }

    def count(id: Long) = DB.withConnection {
      implicit connection =>
        SQL("select count(*) as c from enrollment_student where enrollment_id = 1 and student_id = {id}").
          onParams(id).apply().head[Long]("c")
    }
  }

}
