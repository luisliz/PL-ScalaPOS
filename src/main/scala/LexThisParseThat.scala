import shopgui.{Item, ShopWindow}

import scala.util.parsing.combinator.RegexParsers

class LexThisParseThat extends RegexParsers {

  val double = """[0-9]*[.]?[0-9]+""".r

  val digit = """[0-9_]+""".r

  val string = "[A-Za-z]+".r

  val comilla = """"""".r
  //val comilla = "\"".r

	var gui = new ShopWindow
	gui.visible = false

  //def expr: Parser[Any] =  createShop ~ opt(rep(expr))
  //def exp: Parser[Any] = ShopExp | InvExp | ReceiptExp | AccExp
  //def exp: Parser[Any] = ShopExp | ReceiptExp

	def program: Parser[Any] = createShop ~ opt(rep(expr))

	def expr: Parser[Any] = ShopExp | InvExp | ReceiptExp | AccExp

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

  def ShopExp: Parser[Any] = {

    def renameShop: Parser[Any] = "renameShop:" ~ comilla ~ string ~ comilla ^^ {
      case "renameShop:" ~ "\"" ~ b ~ "\"" => {
        gui.renameShop(b)
      }
    }

    renameShop
  }

	def InvExp: Parser[Any] = {
    def addItem: Parser[Any] = "addItem" ~ ":" ~ string ~ "," ~ string ~ "," ~ string ~ "," ~ string ~ "," ~ string ^^ {
      case a ~ b ~ c ~ d ~ e => {
        print("add item")
      }
    }

    def deleteItem: Parser[Any] = "deleteItem" ~ ":" ~ string ^^ {
      case a => {
        println("deleteItem")
      }
    }

    def updateInventory: Parser[Any] = "updateInventory" ~ ":" ~ string ~ "," ~ string ^^ {
      case a ~ b => {
        println("updateInventory")
      }
    }

    def addInventory: Parser[Any] = "addInventory" ~ ":" ~ string ~ "," ~ string ^^ {
      case a ~ b => {
        println("addinventory")
      }
    }

    def removeInventory: Parser[Any] = "removeInventory" ~ ":" ~ string ~ "," ~ string ^^ {
      case a ~ b => {
        println("removeInventory")
      }
    }

    def updatePrice: Parser[Any] = "updatePrice" ~ ":" ~ string ~ "," ~ string ^^ {
      case a ~ b => {
        println("updatePrice")
      }
    }

    def updateCategory: Parser[Any] = "updateCategory" ~ ":" ~ string ~ "," ~ string ^^ {
      case a ~ b => {
        println("updateCategory")
      }
    }

    def updatePhoto: Parser[Any] = "updatePhoto" ~ ":" ~ string ~ "," ~ string ^^ {
      case a ~ b => {
        println("updatePhoto")
      }
    }

    def settlementGridDimensions: Parser[Any] = "settlementGridDimensions" ~ ":" ~ string ~ "," ~ string ^^ {
      case a ~ b => {
        println("settlementGridDimensions")
      }
    }

    def addToCart: Parser[Any] = "addToCart" ~ ":" ~ string ~ "," ~ string ^^ {
      case a ~ b => {
        println("addToCart")
      }
    }

    def removeToCart: Parser[Any] = "removeToCart" ~ ":" ~ string ~ "," ~ string ^^ {
      case a ~ b => {
        println("removeToCart")
      }
    }

    addItem | deleteItem | updateInventory | addInventory | removeInventory | updatePrice | updateCategory | updatePhoto | settlementGridDimensions | addToCart | removeToCart
  }

  def ReceiptExp: Parser[Any] = {
    def receiptHeader: Parser[Any] = "receiptHeader:" ~ comilla ~ string ~ comilla ^^ {
      case "receiptHeader:" ~ _ ~ str ~ _ => {
        gui.changeReceiptHeader(str)
      }
    }

    def receiptFooter: Parser[Any] = "receiptFooter:" ~ comilla ~ string ~ comilla ^^ {
      case "receiptFooter:" ~ _ ~ str ~ _ => {
        gui.changeReceiptFooter(str)
      }
    }

    def deleteHeader: Parser[Any] = "receiptHeader" ^^ {
      case a => {
        gui.changeReceiptHeader("")
      }
    }

    def deleteFooter: Parser[Any] = "receiptFooter" ^^ {
      case a => {
        gui.changeReceiptFooter("")
      }
    }

    receiptHeader | receiptFooter | deleteHeader | deleteFooter
  }

  def AccExp: Parser[Any] = {
    def addUser: Parser[Any] = "addUser:" ~ comilla ~ string ~ comilla ^^ {
      case "addUser:" ~ _ ~ str ~ _ => {
        //gui.addUser(str) falta hacer esta funcion en ShopWindow. se me ocurre hacer like a label somewhere que diga cual es el current user and maybe algo have like a button to change the current user (like literalmente who is using the gui) and also include the user info on the receipt (like fuiste atendido por tal user)
      }
    }

    addUser
  }
}
