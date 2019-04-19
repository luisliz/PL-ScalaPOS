import shopgui.{Item, ShopWindow}

import scala.util.parsing.combinator.RegexParsers

class LexThisParseThat extends RegexParsers {

  val string = "[A-Za-z]+".r

  val comilla = "\"".r

  var gui = new ShopWindow

  def expr: Parser[Any] =  createShop ~ opt(rep(expr)) | renameShop

  def createShop: Parser[Any] = "createShop" ~ opt(":" ~ comilla ~ string ~ comilla) ^^ {

    case a ~ None => {
      gui.visible = true
    }

    case a ~ Some(":" ~ "\"" ~ b ~ "\"") => {
      gui = new ShopWindow(b)
      //      gui.addItem(new Item("food", "food", "coco.jpg", 10, 20))
      gui.visible = true
    }
  }

  def renameShop: Parser[Any] = "renameShop:" ~ comilla ~ string ~ comilla ^^ {
    case "renameShop:" ~ "\"" ~ b ~"\"" => {
      gui.renameShop(b)
    }
  }

}
