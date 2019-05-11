package printablelibrary

object PrintableInstances {
  implicit val stringPrintable: Printable[String] =
    new Printable[String] {
      def format(value: String): String = value
    }

  implicit val integerPrintable: Printable[Int] =
    new Printable[Int] {
      def format(value: Int): String = value.toString
    }
}
