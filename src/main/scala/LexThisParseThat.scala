import shopgui.{Item, ShopWindow}

import scala.util.parsing.combinator.RegexParsers

class LexThisParseThat extends RegexParsers {
	override def skipWhitespace = true

	val double = """[0-9]*[.]?[0-9]+""".r

	val digit = """[0-9_]+""".r

	val string =
		""""(.*?)"""".r ^^ { str =>
			val content = str.substring(1, str.length - 1).toString
			content
		}

	var gui = new ShopWindow
	gui.visible = false

	def program: Parser[Any] = createShop ~ opt(rep(expr))

	def expr: Parser[Any] = ShopExp | InvExp | ReceiptExp | AccExp

	def createShop: Parser[Any] = "createShop" ~ opt(":" ~ string) ^^ {
		case a ~ None => {
			gui.visible = true
		}

		case a ~ Some(":" ~ b) => {
			gui = new ShopWindow(b)
			//      gui.addItem(new Item("food", "food", "coco.jpg", 10, 20))
			gui.visible = true
		}
	}

	def ShopExp: Parser[Any] = {
		def renameShop: Parser[Any] = "renameShop" ~ ":" ~ string ^^ {
			case _ ~ b => {
				gui.renameShop(b)
			}
		}

		renameShop
	}

	def InvExp: Parser[Any] = {
		def addItem: Parser[Any] = "addItem" ~ ":" ~ string ~ "," ~ string ~ "," ~ string ~ "," ~ digit ~ "," ~ double ^^ {
			case _ ~ category ~ _ ~ itemName ~ _ ~ imageName ~ _ ~ units ~ _ ~ price => {
				println("addItem: " + category + " " + itemName + " " + imageName + " " + units + " " + price)
			}
		}

		def deleteItem: Parser[Any] = "deleteItem" ~ ":" ~ string ^^ {
			case _ ~ a => {
				println("deleteItem: " + a)
			}
		}

		def updateInventory: Parser[Any] = "updateInventory" ~ ":" ~ string ~ "," ~ digit ^^ {
			case _ ~ itemName ~ _ ~ amount => {
				println("updateInventory:" + itemName + " " + amount)
			}
		}

		def addInventory: Parser[Any] = "addInventory" ~ ":" ~ string ~ "," ~ digit ^^ {
			case _ ~ itemName ~ _ ~ amount => {
				println("addinventory: " + itemName + " " + amount)
			}
		}

		def removeInventory: Parser[Any] = "removeInventory" ~ ":" ~ string ~ "," ~ digit ^^ {
			case _ ~ itemName ~ _ ~ amount => {
				println("removeInventory: " + itemName + " " + amount)
			}
		}

		def updatePrice: Parser[Any] = "updatePrice" ~ ":" ~ string ~ "," ~ double ^^ {
			case _ ~ itemName ~ _ ~ newPrice => {
				println("updatePrice: " + itemName + " " + newPrice)
			}
		}

		def updateCategory: Parser[Any] = "updateCategory" ~ ":" ~ string ~ "," ~ string ^^ {
			case _ ~ a ~ _ ~ b => {
				println("updateCategory" + a + " " + b)
			}
		}

		def updatePhoto: Parser[Any] = "updatePhoto" ~ ":" ~ string ~ "," ~ string ^^ {
			case _ ~ a ~ _ ~ b => {
				println("updatePhoto" + a + " " + b)
			}
		}

		def settlementGridDimensions: Parser[Any] = "settlementGridDimensions" ~ ":" ~ string ~ "," ~ string ^^ {
			case _ ~ a ~ _ ~ b => {
				println("settlementGridDimensions" + a + " " + b)
			}
		}

		def addToCart: Parser[Any] = "addToCart" ~ ":" ~ string ~ "," ~ string ^^ {
			case _ ~ a ~ _ ~ b => {
				println("addToCart" + a + " " + b)
			}
		}

		def removeToCart: Parser[Any] = "removeToCart" ~ ":" ~ string ~ "," ~ string ^^ {
			case _ ~ a ~ _ ~ b => {
				println("removeToCart" + a + " " + b)
			}
		}

		addItem | deleteItem | updateInventory | addInventory | removeInventory | updatePrice | updateCategory | updatePhoto | settlementGridDimensions | addToCart | removeToCart
	}

	def ReceiptExp: Parser[Any] = {
		def receiptHeader: Parser[Any] = "receiptHeader" ~ ":" ~ string ^^ {
			case a => {
				println("receiptHeader" + a)
				//gui.changeReceiptHeader(str)
			}
		}
  def ReceiptExp: Parser[Any] = {
    def receiptHeader: Parser[Any] = "receiptHeader:" ~ string ^^ {
      case "receiptHeader:" ~ str  => {
        gui.changeReceiptHeader(str)
      }
    }

		def receiptFooter: Parser[Any] = "receiptFooter:" ~ string ^^ {
			case a => {
				println("receiptFooter" + a)
				//gui.changeReceiptFooter(str)
			}
		}
    def receiptFooter: Parser[Any] = "receiptFooter:" ~ comilla ~ string ~ comilla ^^ {
      case "receiptFooter:" ~ _ ~ str ~ _ => {
        gui.changeReceiptFooter(str)
      }
    }

		def deleteHeader: Parser[Any] = "deleteHeader" ^^ {
			case _ => {
				println("deleteHeader")
				//gui.changeReceiptHeader("")
			}
		}
    def deleteHeader: Parser[Any] = "receiptHeader" ^^ {
      case a => {
        gui.changeReceiptHeader("")
      }
    }

		def deleteFooter: Parser[Any] = "deleteFooter" ^^ {
			case _ => {
				println("deleteFooter")
				//gui.changeReceiptFooter("")
			}
		}
    def deleteFooter: Parser[Any] = "receiptFooter" ^^ {
      case a => {
        gui.changeReceiptFooter("")
      }
    }

		receiptHeader | receiptFooter | deleteHeader | deleteFooter
	}
    receiptHeader | receiptFooter | deleteHeader | deleteFooter
  }

	def AccExp: Parser[Any] = {
		def addUser: Parser[Any] = "addUser:" ~ string ^^ {
			case str => {
				gui.addUserToList(str) //falta hacer esta funcion en ShopWindow. se me ocurre hacer like a label somewhere que diga cual es el current user and maybe algo have like a button to change the current user (like literalmente who is using the gui) and also include the user info on the receipt (like fuiste atendido por tal user)
			}
		}

		addUser
	}
    def removeUser: Parser[Any] = "removeUser:" ~ comilla ~ string ~ comilla ^^ {
      case "removeUser:" ~ _ ~ str ~ _ => {
        gui.removeUserFromList(str)
      }
    }

    addUser | removeUser
  }
}
