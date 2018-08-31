package com.example.scalasketchpad.dao

import cats.effect.IO
import com.example.scalasketchpad.model.Employee
import slick.ast.ColumnOption
import slick.jdbc.PostgresProfile.api.{Database => SlickDatabase}

class Dao {
  val db =
    SlickDatabase.forURL(url = "jdbc:postgresql://localhost:5432/postgres",
                         user = "postgres",
                         password = "example",
                         driver = "org.postgresql.Driver")

  import slick.jdbc.PostgresProfile.api._

  class Employees(tag: Tag) extends Table[(Int, String)](tag, "employee") {
    def id         = column[Int]("id", ColumnOption.AutoInc)
    def name       = column[String]("name")
    override def * = (id, name)
  }

  val employees = TableQuery[Employees]

  def save(employee: Employee): IO[Employee] = {
    val actions =
      (employees returning employees.map(_.id)
        into ((employee, insertedId) => employee.copy(_1 = insertedId))) += (-1, employee.name)

    IO.fromFuture(IO(db.run(actions))).map(a => Employee(a._1, a._2))
  }
}
