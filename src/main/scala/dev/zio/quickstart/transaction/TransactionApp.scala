package dev.zio.quickstart.transaction

import zhttp.http.*
import zio.*
import zio.json.*
import scala.io.Source

object TransactionApp:
  def apply(): Http[TransactionRepo, Throwable, Request, Response] =
    Http.collectZIO[Request] {
      // POST /transaction-check -d '{"src":"1", "dst":"5", "amount":10}'
      case req@(Method.POST -> !! / "transaction-check") =>
        for
          t <- req.bodyAsString.map(_.fromJson[Transaction])
          r <- t match
            case Left(e) =>
              ZIO.debug(s"Failed to parse the input: $e").as(
                Response.text(e).setStatus(Status.BadRequest)
              )
            case Right(t) =>
              TransactionRepo.check(t)
                .map(response => Response.text(response))
        yield r
    }

//curl -i http://localhost:8080/transaction-check -d "{\"src\":\"1\",\"dst\":\"5\",\"amount\":10}"