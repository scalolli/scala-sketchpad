package com.example.mtl

import cats.Monad
import cats.data._
import cats.effect._
import cats.implicits._

object MtlBlog {

  type Config = String
  type Result = String

  def getConfig: IO[Config] = ???

  def serviceCall(c: Config): IO[Result] = ???

  def readerProgram: ReaderT[IO, Config, Result] = for {
      config <- ReaderT.ask[IO, Config]
      result <- ReaderT.liftF(serviceCall(config))
    } yield result

  getConfig.flatMap(readerProgram.run)

  import cats.mtl._

  def readerProgramWithMtl[F[_]: Monad: LiftIO](implicit A: ApplicativeAsk[F, Config]): F[Result] = for {
    config <- A.ask
    result <- serviceCall(config).to[F]
  } yield result

  def readerProgram[F[_]: Monad: LiftIO](implicit A: ApplicativeAsk[F, Config]): F[Result] = for {
    config <- A.ask
    result <- serviceCall(config).to[F]
  } yield result


//  getConfig.flatMap(materializedReaderMtl.run)


  type Env      = String
  type Request  = String
  type Response = String

  def initialEnv: Env                             = ???
  def request(r: Request, env: Env): IO[Response] = ???
  def updateEnv(r: Response, env: Env): Env       = ???

  def req1: Request = ???
  def req2: Request = ???
  def req3: Request = ???
  def req4: Request = ???

  def requestWithState(r: Request): StateT[IO, Env, Response] = for {
      env  <- StateT.get[IO, Env]
      resp <- StateT.liftF(request(r, env))
      _    <- StateT.modify[IO, Env](updateEnv(resp, _))
    } yield resp

  def stateProgram: StateT[IO, Env, Response] = for {
    resp1 <- requestWithState(req1)
    resp2 <- requestWithState(req2)
    resp3 <- requestWithState(req3)
    resp4 <- requestWithState(req4)
  } yield resp4

  stateProgram.run(initialEnv)

  val randomInteger: IO[Int] = ???

  private def randomIDStringGeneric[F[_]: Monad: LiftIO](length: Int) = for {
    n <- randomIntegerF
  } yield n

  def randomIntegerF[F[_]: LiftIO](implicit F: LiftIO[F]): F[Int] = F.liftIO(randomInteger)

}
