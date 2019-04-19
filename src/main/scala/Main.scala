import scala.io.Source

object Main {
    def main(args: Array[String]) {
        //////?NEW STUFF HERE
        //		val largeFile = Source.fromFile("files")
        //		val shortFile = Source.fromFile("TestFile_2.txt")
        val stringInput =
        """
          |createShop: "MyShop"
		  |renameShop: "hey"
		  |addItem: "Lol", "juan", "pedro", 50, 1.50
		  |deleteItem: "eyy"
		  |updateInventory: "oh", 20
		  |removeInventory: "oh", 30
		  |updatePrice: "option11", 1.50
		  |updateCategory: "option1", "option2"
		  |updatePhoto: "option 1", "option2"
		  |settlementGridDimensions: "option 1", "option2"
		  |addToCart: "option 1", "option2"
		  |removeToCart: "option 1", "option2"
		  |receiptHeader: "word"
		  |receiptFooter: "stuff"
		  |deleteHeader
		  |deleteFooter
        """.stripMargin.trim

        val inputFile = stringInput //Decide file here
        val inputSource = inputFile.mkString

        val parser = new LexThisParseThat

        val result = parser.parseAll(parser.program, stringInput)

        println(result.get)
    }
}