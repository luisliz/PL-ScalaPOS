import shopgui.ShopWindow


object Main {
  def main(args: Array[String]) {
    val gui = new ShopWindow
    gui.visible = false
    println("End of main function")

    val validCode =
    """
    createShop: "Hey";
    addItem: "food", "arroz", "arroz.jpg", 300, 1.50;
    """.stripMargin.trim;


    //
    //addItem: "food", "arroz", arroz.jpg, 300, 1.50

    println("LEXER: " + POSLexer.apply(validCode).toString)
    println("Compiler: " + POSCompiler.apply(validCode).toString)
    //println(POSCompiler.apply(validCode).toString)
  }
}