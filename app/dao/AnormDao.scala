package dao

import play.api.db.DB
import anorm._
import play.api.Play.current

/**
 * @author Slava Pak
 */
abstract class AnormDao[T](table: String) {

  def rowParser: RowParser[T]

  def all: List[T] = DB.withConnection {
    implicit c =>
      SQL("select * from %s".format(table)).as(rowParser *)
  }

}
