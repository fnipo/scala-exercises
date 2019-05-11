package functors

trait Printable[A] {
  self =>

  def format(value: A): String

  def contraMap[B](f: B => A): Printable[B] = {
    new Printable[B] {
      override def format(value: B): String = {
        self.format(f(value))
      }
    }
  }
}
