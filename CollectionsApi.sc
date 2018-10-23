import cats.data.ValidatedNel
import cats.implicits._

type VPNF[A] = ValidatedNel[String, A]

val one = Option(Some(1).toValidNel("Error1"))
  .sequence[VPNF, Int]

val two = Option(Some(2).toValidNel("Error2"))
  .sequence[VPNF, Int]

(one, two)
  .tupled
  .toEither

case class KbartData(mandatory: String, optional: Option[String])

import cats.implicits._

val data = KbartData("mandatory", Some("optional"))

(1,2,3).tupled







