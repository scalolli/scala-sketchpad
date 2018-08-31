package com.example.scalasketchpad.functional

import cats.effect.IO
import fs2.Stream
import io.circe.Json
import io.circe.literal._
import org.http4s._
import fs2.Stream
import org.http4s.implicits._
import org.http4s.circe._
import org.scalatest.{FunSpec, Matchers}
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api.{Database => SlickDatabase}
import com.example.scalasketchpad.EmployeeRoute
import com.example.scalasketchpad.dao.Dao
import com.example.scalasketchpad.model.Employee

class HelloWorldSpec extends FunSpec with Matchers {
  private val service: HttpService[IO] = new EmployeeRoute {
    override def dao = new Dao()
  }.service

  it("get should return 200") {
    {
      val getHW = Request[IO](Method.GET, Uri.uri("/hello/world"))
      service.orNotFound(getHW).unsafeRunSync()
    }.status shouldBe (Status.Ok)
  }

  it("should return 404 if name is not passed") {
    val getHW    = Request[IO](Method.GET, Uri.uri("/hello"))
    val response = service.orNotFound(getHW).unsafeRunSync()
    response.status shouldBe (Status.NotFound)
  }

  it("should return greeting") {
    val getHW    = Request[IO](Method.GET, Uri.uri("/hello/name"))
    val response = send(getHW)
    response.as[Json].unsafeRunSync() shouldBe (json"""{"message":"Hello, name"}""")
  }

  it("should post json to add an employee") {
    val entityBody       = Stream.emits(json"""{"id":null,"name": "basu"}""".toString().getBytes())
    val addPersonRequest = Request[IO](Method.POST, Uri.uri("/employee"), body = entityBody)

    val response = send(addPersonRequest)
    response.status shouldBe (Status.Created)

    import io.circe.generic.auto._
    implicit val decoder = jsonOf[IO, Employee]

    val employeeId  = response.as[String].unsafeRunSync()
    val getResponse = send(Request[IO](Method.GET, Uri.unsafeFromString(s"/employee/$employeeId")))
    getResponse.status shouldBe (Status.Ok)
    getResponse.as[Employee].unsafeRunSync() shouldBe (Employee(Some(employeeId.toInt), "basu"))
  }

  private[this] def send(request: Request[IO]) = service.orNotFound(request).unsafeRunSync()

}
