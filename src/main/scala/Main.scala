import compiler.POSCompiler
import lexer.POSLexer
import shopgui.ShopWindow


object Main {
  def main(args: Array[String]) {
    val gui = new ShopWindow
    gui.visible = false
    println("End of main function")

    val validCode =
      """
         createShop: "MyShop"
         addItem: "food", "arroz", "arroz.jpg", 300, 1.50
      """.stripMargin.trim;

    //createShop: "MyShop"
    //addItem: "food", "arroz", arroz.jpg, 300, 1.50

    println(POSLexer.apply(validCode).toString)
    //println(POSCompiler.apply(validCode).toString)
  }
}