package com.example.scalasketchpad

import cats.effect.IO
import com.example.scalasketchpad.dao.Dao
import com.example.scalasketchpad.model.Employee
import io.circe.Json
import io.circe.generic.auto._
import org.http4s.HttpService
import org.http4s.circe._
import org.http4s.dsl.io._

trait EmployeeRoute {

  implicit val decoder = jsonOf[IO, Employee]
  implicit val encoder = jsonEncoderOf[IO, Option[Int]]

  def dao: Dao

  val service: HttpService[IO] = {
    HttpService[IO] {
      case GET -> Root / "hello" / name =>
        Ok(Json.obj("message" -> Json.fromString(s"Hello, ${name}")))

      case request @ POST -> Root / "employee" =>
        request.as[Employee].flatMap { p =>
          dao.save(p).flatMap(e => Created(e.id))
        }
    }
  }
}
