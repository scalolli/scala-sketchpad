package com.example.scalasketchpad.json

object JsonRunner extends App {

  val j: Json = Json.asJson("basu")
  println(j)

  val listJson = Json.asJson(Seq("Basu", "1"))
  println(listJson)
}
