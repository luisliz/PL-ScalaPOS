import shopgui.{Item, ShopWindow}

import scala.util.parsing.combinator.RegexParsers

class LexThisParseThat extends RegexParsers {

  val ourString = "[A-Za-z]+".r

  val comillas = "\"".r

  def expr: Parser[Any] = "openMenu" ~ opt(":" ~ comillas ~ ourString ~ comillas) ^^ {

    case a ~ None => {
      val gui = new ShopWindow
      gui.visible = true
    }

    case a ~ Some(":" ~ "\"" ~ b ~ "\"") => {
      val gui = new ShopWindow(b)
//      gui.addItem(new Item("food", "food", "coco.jpg", 10, 20))
      gui.visible = true
    }


  }

}
