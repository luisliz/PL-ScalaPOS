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
        |createshop: "createshop"
      """.stripMargin.trim;

    println(POSLexer.apply(validCode).toString)
  }
}