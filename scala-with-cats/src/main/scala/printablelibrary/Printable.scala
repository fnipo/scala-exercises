package printablelibrary

trait Printable[A] {
  def format(value: A): String
}
