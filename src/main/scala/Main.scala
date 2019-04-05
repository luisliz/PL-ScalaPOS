import shopgui.ShopWindow


object GuiProgramOne {
  def main(args: Array[String]) {
    val gui = new ShopWindow
    gui.visible = true
    println("End of main function")
  }
}