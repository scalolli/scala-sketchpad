package com.example.scalasketchpad

import cats.effect.IO
import com.example.scalasketchpad.dao.Dao
import fs2.StreamApp
import fs2.StreamApp.ExitCode
import org.http4s.server.blaze.BlazeBuilder

import concurrent.ExecutionContext.Implicits.global

object HelloWorldServer extends StreamApp[IO] {
  override def stream(args: List[String], requestShutdown: IO[Unit]): fs2.Stream[IO, ExitCode] = {
    val service = new EmployeeRoute {
      override def dao: Dao = new Dao
    }.service
    BlazeBuilder[IO]
      .bindHttp(8080, "localhost")
      .mountService(service, "/")
      .serve
  }
}
