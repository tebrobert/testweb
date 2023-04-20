package com.example.testweb

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object TestwebRoutes {

  def jokeRoutes[F[_]: Sync](J: Jokes[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root  =>
        println(J)
        for {
          joke <- J.get
          resp <- Ok(joke)
        } yield resp
      case e => Ok(e.uri.toString)
    }
  }

}