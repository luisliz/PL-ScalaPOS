import compiler.POSCompiler
import lexer.POSLexer
import shopgui.{Item, ShopWindow}


object Main {
  def main(args: Array[String]) {
//    val gui = new ShopWindow
//    gui.addItem(new Item("food", "arroz2", "arroz.jpg", 300, 1.50))
//    gui.visible = true
//    println("End of main function")
//
//    val validCode =
//      """
//      createShop: "My Shop";
//      receiptHeader: "Welcome to SHOP";
//      addItem: "food", "arroz", "arroz.jpg", 300, 1.50;
//      receiptFooter: "Thank you for shopping with us";
//      """.stripMargin.trim;

    /*

        addItem: "drink, "cocacola", null, 500, 1;
        addItem: "food", "arroz", "arroz.jpg", 300, 1.50;
        addInventory: "arroz", 3;
        removeInventory: "cocacola", 20;
        updateInventory: "arroz", 200;
        updatePrice: "arroz", 1.60;
        deleteItem: "cocacola";
        receiptFooter: "Thank you for shopping with us";*/


    //addItem: "food", "arroz", arroz.jpg, 300, 1.50

//    println("LEXER: " + POSLexer.apply(validCode).toString)
//    println("Compiler: " + POSCompiler.apply(validCode).toString)
    //println(POSCompiler.apply(validCode).toString)


    val parser = new LexThisParseThat

    val result = parser.parseAll(parser.expr, "createShop:\"MyMenu\" renameShop:\"Lol\"")

    println(result.get)
  }
}