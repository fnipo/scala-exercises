package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      (c, r) match {
        case (c, r) if c < 0 || r < 0 || c > r => {
          throw new IllegalArgumentException
        }
        case (c, r) if c == 0 || c == r => {
          1
        }
        case _ => {
          pascal(c - 1, r - 1) + pascal(c, r - 1)
        }
      }
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      def check(opened: Integer, chars: List[Char]): Boolean = {
        if (opened < 0) false
        else if (chars.isEmpty) true
        else
          chars.head match {
            case '(' => check(opened + 1, chars.tail)
            case ')' => check(opened - 1, chars.tail)
            case _ => check(opened, chars.tail)
          }
      }
      check(0, chars)
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      // return amount of possibilities

      if (money <= 0) {
        0
      }
      else {
        def countChangeOnSorted(amount: Int, coins: List[Int]): Int = {
          if (coins.isEmpty) 0
          else if (amount < money) {
            val headCount = countChangeOnSorted(amount + coins.head, coins)
            val tailCount = countChangeOnSorted(amount, coins.tail)
            headCount + tailCount
          } else if (amount == money) {
            1
          } else {
            0
          }
          //countChangeOnSorted(0, coins.tail)
        }
        countChangeOnSorted(0, /*0 +: */coins.sorted)
      }
    }
  }
