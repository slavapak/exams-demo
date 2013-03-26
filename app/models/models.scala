package models

case class Enrollment(id: Long, title: String)

case class Student(id: Long, name: String)

case class Exam(enrollment: Enrollment, student: Student, grade: Option[Grade.Value])

object Grade extends Enumeration {
  val A = Value("A")
  val B = Value("B")
  val C = Value("C")
  val D = Value("D")
}