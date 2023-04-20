package com.example.testweb

import cats.effect.kernel.Sync
import cats.implicits.toFunctorOps
import org.http4s.client.Client
import org.http4s.implicits.http4sLiteralsSyntax

import java.nio.charset.StandardCharsets

trait Jokes[F[_]] {
  def get: F[String]
}

object Jokes {
  def apply[F[_]](implicit ev: Jokes[F]): Jokes[F] = ev

  def impl[F[_] : Sync ](C: Client[F]): Jokes[F] = new Jokes[F] {
    def get: F[String] = {
      C.get((uri"https://tebrobert.pythonanywhere.com/api/get_random_story")) {
        _.body.compile
          .fold(Array.empty[Byte])(_ appended _)
          .map(new String(_, StandardCharsets.UTF_8))
      }
    }
  }
}
