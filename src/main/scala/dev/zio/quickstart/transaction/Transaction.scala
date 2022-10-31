package dev.zio.quickstart.transaction

import zio.json.*

case class Transaction(src: String, dst: String, amount: Int)

object Transaction:
  implicit val encoder: JsonEncoder[Transaction] =
    DeriveJsonEncoder.gen[Transaction]
  implicit val decoder: JsonDecoder[Transaction] =
    DeriveJsonDecoder.gen[Transaction]
      
/*
package dev.zio.quickstart.trsansaction

import java.util.UUID
import zio.json.*

case class Transaction(src: String, dst: String, amount: Int)

object Transaction:
  given JsonEncoder[Transaction] =
    DeriveJsonEncoder.gen[Transaction]
  given JsonDecoder[Transaction] =
    DeriveJsonDecoder.gen[Transaction]
*/