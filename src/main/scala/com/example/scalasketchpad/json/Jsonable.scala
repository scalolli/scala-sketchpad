package com.example.scalasketchpad.json

sealed abstract class Json
private[json] case class JsonString(value: String)   extends Json
private[json] case class JsonNumber(value: Int)      extends Json
private[json] case class JsonList(values: Seq[Json]) extends Json

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
  implicit object IntEncoder extends Encoder[Int] {
    override def encode(t: Int): Json = JsonNumber(t)
  }

  implicit def SeqEncoder[T: Encoder] = new Encoder[Seq[T]] {
    override def encode(t: Seq[T]): Json = JsonList(t.map(implicitly[Encoder[T]].encode(_)))
  }
}
