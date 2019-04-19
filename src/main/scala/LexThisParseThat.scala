import shopgui.{Item, ShopWindow}

import scala.util.parsing.combinator.RegexParsers

class LexThisParseThat extends RegexParsers {

	val string = "[A-Za-z]+".r

	val comilla = "\"".r

	var gui = new ShopWindow
	gui.visible = false

	def program: Parser[Any] = createShop ~ opt(rep(expr))

	def expr: Parser[Any] = InvExp //| ReceiptExp | AccExp

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


	/*def ReceiptExp: Parser[Any] =

	def AccExp: Parser[Any] =*/

	/*
		def addItem

		def receiptHeader

		def receiptFooter

		def deleteHeader

		def deleteFooter*/

	//def accExp:

}
