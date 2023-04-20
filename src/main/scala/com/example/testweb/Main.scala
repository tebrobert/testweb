package com.example.testweb

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple {
  val run = TestwebServer.run[IO]
}
