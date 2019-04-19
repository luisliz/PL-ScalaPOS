import compiler.POSCompiler
import lexer.POSLexer
import shopgui.{Item, ShopWindow}

import scala.io.Source

object Main {
  def main(args: Array[String]) {
//    val gui = new ShopWindow
//    gui.addItem(new Item("food", "arroz2", "arroz.jpg", 300, 1.50))
//    gui.visible = true
//    println("End of main function")
//
    val validCode =
      """
      createShop: "MyShop"
      receiptHeader: "Welcome to SHOP"
      """.stripMargin.trim;

		/*

			addItem: "drink, "cocacola", null, 500, 1;
			addItem: "food", "arroz", "arroz.jpg", 300, 1.50;
			addInventory: "arroz", 3;
			removeInventory: "cocacola", 20;
			updateInventory: "arroz", 200;
			updatePrice: "arroz", 1.60;
			deleteItem: "cocacola";
			receiptFooter: "Thank you for shopping with us";*/

		//    println("LEXER: " + POSLexer.apply(validCode).toString)
		//    println("Compiler: " + POSCompiler.apply(validCode).toString)
		//println(POSCompiler.apply(validCode).toString)

		//////?NEW STUFF HERE
//		val largeFile = Source.fromFile("files")
//		val shortFile = Source.fromFile("TestFile_2.txt")
		val stringInput =
			"""
			  |createShop: "Hey"
|receiptFooter: "new header"
			  |addItem: Lol, juan, pedro, junito, iwr
			  |updateInventory: Lol, juan
			  |addInventory: Lol, juan
			  |removeInventory: Lol, juan
			  |updatePrice: Lol, juan
			  |updateCategory: Lol, juan
			  |updatePhoto: Lol, juan
			  |settlementGridDimensions: Lol, juan
			  |addToCart: Lol, juan
			  |removeToCart: Lol, juan
			""".stripMargin

		val inputFile = stringInput //Decide file here
		val inputSource = inputFile.mkString

		val parser = new LexThisParseThat

		val result = parser.parseAll(parser.program, stringInput)
    //val result = parser.parseAll(parser.expr, "createShop:\"MyMenu\"\nrenameShop:\"Lol\"")
    //val result = parser.parseAll(parser.expr, validCode)

		//println(result.get)
	}
}