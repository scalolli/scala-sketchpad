package com.example.scalasketchpad.auxpattern

object AuxRunner extends App {

  trait Unwrap[T[_], R] {
    type Out

    def apply(tr: T[R]): Out
  }

  object Unwrap {
    type Aux[T[_], R, Out0] = Unwrap[T, R] { type Out = Out0 }

    implicit object listStringSize extends Unwrap[List, String] {
      override type Out = Int
      override def apply(tr: List[String]): Int = tr.size
    }

//    implicit object optionInstance extends Unwrap[Option, Any] {
//      override type Out = Boolean
//      override def apply(tr: Option[Any]) = tr.isDefined
//    }
  }

  trait PrettyPrinter[T] {
    def apply(t: T): (T, String)
  }
  object PrettyPrinter {
    implicit object IntPrettyPrinter extends PrettyPrinter[Int] {
      override def apply(t: Int): (Int, String) = (t, t.toString)
    }
  }

  def extractor[T[_], R, Out](in: T[R])(implicit unwrap: Unwrap.Aux[T, R, Out], prettyPrinter: PrettyPrinter[Out]) = {
    prettyPrinter(unwrap(in))
  }

  println(extractor(List("abce", "def")))
}
