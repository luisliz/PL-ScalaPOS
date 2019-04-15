import compiler.POSCompiler
import shopgui.ShopWindow


object Main {
  def main(args: Array[String]) {
    val gui = new ShopWindow
    gui.visible = true
    println("End of main function")

    val validCode =
      """
        |read input name, country
        |switch:
        |  country == "PT" ->
        |    call service "A"
        |    exit
        |  otherwise ->
        |    call service "B"
        |    switch:
        |      name == "unknown" ->
        |        exit
        |      otherwise ->
        |        call service "C"
        |        exit
      """.stripMargin.trim

    println(POSCompiler.apply(validCode).toString)
  }
}