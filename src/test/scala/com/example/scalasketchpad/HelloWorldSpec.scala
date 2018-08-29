package com.example.scalasketchpad

import cats.effect.IO
import io.circe.Json
import io.circe.literal._
import org.http4s._
import fs2.Stream
import org.http4s.implicits._
import org.http4s.circe._
import org.scalatest.{Assertion, FunSpec, Matchers}

class HelloWorldSpec extends FunSpec with Matchers {
  private var service: HttpService[IO] = new PersonRoute {}.service

  it("get should return 200") {
    uriReturns200()
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
    val entityBody       = Stream.emits(json"{}".toString().getBytes())
    val addPersonRequest = Request[IO](Method.POST, Uri.uri("/person"), body = entityBody)

    val response = send(addPersonRequest)
    response.status shouldBe (Status.Created)

    val employeeId = response.as[String].unsafeRunSync()
    send(Request[IO](Method.GET, Uri.unsafeFromString(s"/person/$employeeId"))).status shouldBe (Status.Ok)
  }

  private[this] val retHelloWorld: Response[IO] = {
    val getHW = Request[IO](Method.GET, Uri.uri("/hello/world"))
    service.orNotFound(getHW).unsafeRunSync()
  }

  private[this] def uriReturns200(): Assertion =
    retHelloWorld.status shouldBe (Status.Ok)

  private[this] def send(request: Request[IO]) = service.orNotFound(request).unsafeRunSync()
}
