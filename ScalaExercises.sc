import doobie.free.connection.ConnectionIO
import doobie.util.transactor.Transactor
import io.circe.{Decoder, Json, KeyDecoder}

val json = io.circe.parser.parse("""{
                           "order" : {
                             "customer" : {
                               "name" : "Custy McCustomer",
                               "contactDetails" : {
                                 "address" : "1 Fake Street, London, England",
                                 "phone" : "0123-456-789"
                               }
                             },
                             "items" : [
                               {
                                 "id" : 123,
                                 "description" : "banana",
                                 "quantity" : 1
                               },
                               {
                                 "id" : 456,
                                 "description" : "apple",
                                 "quantity" : 2
                               }
                             ],
                             "total" : 123.45
                           }
                         }""").getOrElse(Json.Null)

json.hcursor.downField("order").downField("items").focus.flatMap(_.asArray).getOrElse(Vector.empty)

import io.circe.optics.JsonPath._

val _address = root.order.customer.contactDetails.address.string

import io.circe.syntax._

List(1,2,3).asJson

import io.circe.literal._

json"""[1,2,3]""".as[List[Int]]

import io.circe.generic.semiauto

case class EmployeeID(id: Int)
case class Address(streetName: String, postCode: String)
implicit val decoder: Decoder[Address] = semiauto.deriveDecoder
implicit val keydecoder: KeyDecoder[EmployeeID] = (key: String) => Some(EmployeeID(key.toInt))

json"""{"1":{"streetName": "Munster Road", "postCode": "blah blah"}}""".as[Map[EmployeeID, Address]]

import cats._
import cats.implicits._

sealed trait TrafficLight
object TrafficLight {
  case object RedLight extends TrafficLight
  case object GreenLight extends TrafficLight
  case object YellowLight extends TrafficLight

  val red: TrafficLight = RedLight
  val green: TrafficLight = GreenLight
  val yellow: TrafficLight = YellowLight
}

implicit val trafficLightEq = new Eq[TrafficLight] {
  override def eqv(x: TrafficLight, y: TrafficLight) = x == y
}

TrafficLight.red === TrafficLight.yellow

class Refined[T, P]

type Blah = Int Refined String

val x: Blah = new Refined

x