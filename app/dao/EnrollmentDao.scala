package dao

import anorm.SqlParser._
import anorm.~
import models.Enrollment

/**
 * @author Slava Pak
 */
trait EnrollmentDao {

  def all: List[Enrollment]

}

trait EnrollmentDaoComponent {

  def enrollmentDao: EnrollmentDao

}

trait AnormEnrollmentDaoComponent extends EnrollmentDaoComponent {

  class AnormEnrollmentDao extends AnormDao[Enrollment]("enrollment") with EnrollmentDao {

    val rowParser = {
      get[Long]("id") ~
        get[String]("title") map {
        case id~title => Enrollment(id, title)
      }
    }

  }

  val enrollmentDao = new AnormEnrollmentDao

}
