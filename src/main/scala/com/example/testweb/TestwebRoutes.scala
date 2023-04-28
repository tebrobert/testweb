package com.example.testweb

import cats.effect.Async
import cats.effect.std.Console
import cats.implicits._
import doobie.Transactor
import doobie.implicits.toSqlInterpolator
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

import java.nio.charset.StandardCharsets
import doobie.implicits._

object TestwebRoutes {

  def jokeRoutes[F[_]: Async: Console](J: Jokes[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case req @ GET -> Root  =>
        for {
          reqBody <- req.body.compile.fold(Array.empty[Byte])(_ appended _)
            .map(new String(_, StandardCharsets.UTF_8))
          _ <- Console[F].println(reqBody)

          xa = Transactor.fromDriverManager[F](
            "org.postgresql.Driver", "jdbc:postgresql://0.0.0.0:5432/postgres", "postgres", "Stt-6789"
          )

          x <- sql"select * from test".query[Int].option.transact(xa).map(_.toString)
          _ <- Console[F].println(x)
          resp <- Ok(x)
        } yield resp
      case e => Ok(e.uri.toString)
    }
  }

}