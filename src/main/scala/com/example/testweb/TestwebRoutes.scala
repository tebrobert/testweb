package com.example.testweb

import cats.effect.Sync
import cats.effect.std.Console
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

import java.nio.charset.StandardCharsets
import scala.sys.process.Process

object TestwebRoutes {

  def jokeRoutes[F[_]: Sync: Console](J: Jokes[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case req @ GET -> Root  =>
        for {
          reqBody <- req.body.compile.fold(Array.empty[Byte])(_ appended _)
            .map(new String(_, StandardCharsets.UTF_8))
          _ <- Console[F].println(reqBody)
          _ <- Sync[F].delay(Process("curl 0.0.0.0:5432").run())
          joke <- J.get
          resp <- Ok(joke)
        } yield resp
      case e => Ok(e.uri.toString)
    }
  }

}