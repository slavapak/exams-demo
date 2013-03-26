package dao

import models.Student
import anorm.SqlParser._
import anorm.~

/**
 * @author Slava Pak
 */
trait StudentDao {

  def all: List[Student]

}

trait StudentDaoComponent {

  def studentDao: StudentDao

}

trait AnormStudentDaoComponent extends StudentDaoComponent {

  class AnormStudentDao extends AnormDao[Student]("student") with StudentDao {

    val rowParser = {
      get[Long]("id") ~
        get[String]("name") map {
        case id~name => Student(id, name)
      }
    }

  }

  val studentDao = new AnormStudentDao

}
