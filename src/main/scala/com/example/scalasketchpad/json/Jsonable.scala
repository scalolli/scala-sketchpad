package com.example.scalasketchpad.json

sealed abstract class Json
private[json] case class JsonString(value: String) extends Json

object Json {
  def asJson[A: Encoder](a: A): Json = implicitly[Encoder[A]].encode(a)
}

trait Encoder[T] {
  def encode(t: T): Json
}

object Encoder {
  implicit object StringEncoder extends Encoder[String] {
    override def encode(t: String): Json = JsonString(t)
  }
}
