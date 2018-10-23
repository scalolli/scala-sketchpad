package com.example.interpolation

object InterpolationUsage extends App{

  import string._

  val name = "basu"
  val myWrapper = sql"abcdef $name"

  println(myWrapper)
}
