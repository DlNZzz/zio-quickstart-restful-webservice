package dev.zio.quickstart.transaction

import zio.*
import scala.io.Source
import scala.collection.mutable

case class TransactionRepo(map: Ref[mutable.Map[String, Transaction]]):
  def check(transaction: Transaction): Task[String] = {
    val fileName = "blacklist.txt"
    val file = Source.fromResource(fileName)
    var isFlag = false
    for (v <- file.getLines) {
      if transaction.src == v || transaction.dst == v then
        isFlag = true
    }
    file.close()
    if isFlag then
      ZIO.succeed("Cancel")
    else
      ZIO.succeed("Success")
  }

object TransactionRepo:
  def check(transaction: Transaction): ZIO[TransactionRepo, Throwable, String] =
    ZIO.serviceWithZIO[TransactionRepo](_.check(transaction))

  def layer: ZLayer[Any, Nothing, TransactionRepo] =
    ZLayer.fromZIO(
      Ref.make(mutable.Map.empty[String, Transaction]).map(new TransactionRepo(_))
    )