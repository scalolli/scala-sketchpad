package com.example.interpolation

final class SqlInterpolator(private val sc: StringContext) {
  case class MyWrapper[A](value: Seq[A])

  def sql[A](args: A*) = fr0

  object fr0 {
    def apply[A](value: A*): MyWrapper[A] = MyWrapper(value)
  }
}

trait ToSqlInterpolator {
  implicit def toSqlInterpolator(sc: StringContext) = new SqlInterpolator(sc)
}

object string extends ToSqlInterpolator
