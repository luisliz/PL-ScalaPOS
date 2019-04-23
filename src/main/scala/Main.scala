import scala.io.Source

object Main {
  def main(args: Array[String]) {
//    val largeFile = Source.fromFile("largeFile.spos").mkString.stripMargin.trim
    val shortFile = Source.fromFile("shortFile.spos").mkString.stripMargin.trim
//    val stringInput =
//      """
//        |createShop: "MyShop"
//        		  |renameShop: "hey"
//        		  |addItem: "Lol", "juan", "pedro", 50, 1.50
//        		  |deleteItem: "eyy"
//        		  |updateInventory: "oh", 20
//        		  |removeInventory: "oh", 30
//        		  |updatePrice: "item", 1.50
//        		  |updateCategory: "item", "category"
//        		  |updatePhoto: "item", "img.png"
//        		  |settlementGridDimensions: "option 1", "option2"
//        		  |addToCart: "item", "quantity"
//        		  |removeToCart: "item", "quantity"
//        		  |receiptHeader: "word"
//        		  |receiptFooter: "stuff"
//        		  |deleteHeader
//        		  |deleteFooter
//      """.stripMargin.trim

    val parser = new LexThisParseThat

    val result = parser.parseAll(parser.program, shortFile)

    println(result.get)

    var flag = true

    while (flag) {
      println("Enter command:")
      val input: String = scala.io.StdIn.readLine()
      if (input == "quit") { flag = false }
      else { val toParse = parser.parseAll(parser.expr, input) }
    }
  }
}