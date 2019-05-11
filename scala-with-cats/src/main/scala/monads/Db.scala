package monads

import cats._
import cats.implicits._
import cats.data._

case class Db(
  usernames: Map[Int, String],
  passwords: Map[String, String]
)

object Db {
  type DbReader[A] = Reader[Db, A]

  def findUserName(userId: Int): DbReader[Option[String]] = {
    Reader(_.usernames.get(userId))
  }

  def checkPassword(username: String, password: String): DbReader[Boolean] = {
    Reader(_.passwords
      .get(username)
      .fold(false)(_ == password)
    )
  }

  def checkLogin(userId: Int, password: String): DbReader[Boolean] = {
    for {
      userName <- findUserName(userId)
      isLogged <- userName.fold(Reader[Db, Boolean](_ => false))(checkPassword(_, password))
    } yield isLogged
  }
}