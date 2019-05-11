package printablelibrary

import PrintableInstances._
import PrintableSintax._

object Program extends App {
  //implicitly[Printable[String]]
  //implicitly[Printable[Int]]

  20 print

  val cat1 = Cat("Homer", 40, "Ginger")
  cat1 print
}
