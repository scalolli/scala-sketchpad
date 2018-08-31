package com.example.scalasketchpad.dao

import com.example.scalasketchpad.model.Employee
import org.scalatest.{FunSpec, Matchers}

class DaoSpec extends FunSpec with Matchers {

  val dao = new Dao

  it("should save an employee to the database") {
    val employee = dao.save(Employee(-1, "babya")).unsafeRunSync()
    employee.id shouldNot (be(-1))
    employee.name shouldBe ("babya")
  }
}
