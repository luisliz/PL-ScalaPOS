package newParser

import scala.util.parsing.combinator.syntactical.StandardTokenParsers
import newParser.models._

class POSLanguageParser extends StandardTokenParsers {
    lexical.reserved +=
      ("renameShop","addItem","deleteItem","updateInventory","addInventory","removeInventory","updatePrice",
      "updateCategory","updatePhoto","addToCart","removeFromCart","receiptHeader","receiptFooter","deleteHeader",
      "deleteFooter","createShop", "addUser", "setElementsGridDimensions")

    lexical.delimiters += (":", ";", ",")

    def program: Parser[Program] = ShopExp | InvExp | ReceiptExp | AccExp ^^ {
        case f ~ c => new Program(f, c)
    }

    def shopExp: Parser[ShopExp] = () ^^ {

    }

    def InvExp: Parser[InvExp] = ()^^ {

    }

    def ReceiptExp: Parser[ReceiptExp] = () ^^ {

    }

    def AccExp: Parser[AccExp] = () ^^ {

    }


    def parseAll[T](p: Parser[T], in: String): ParseResult[T] = {
      phrase(p)(new lexical.Scanner(in))
    }
}