package com.example.scalasketchpad

import cats.effect.IO
import com.example.scalasketchpad.model.model.Person
import io.circe.Json
import io.circe.generic.auto._
import org.http4s.HttpService
import org.http4s.circe._
import org.http4s.dsl.io._

trait PersonRoute {

  implicit val decoder = jsonOf[IO, Person]

  val service: HttpService[IO] = {
    HttpService[IO] {
      case GET -> Root / "hello" / name =>
        Ok(Json.obj("message" -> Json.fromString(s"Hello, ${name}")))

      case request @ POST -> Root / "person" =>
        val person = request.as[Person]
        Created("some-id")
    }
  }
}
