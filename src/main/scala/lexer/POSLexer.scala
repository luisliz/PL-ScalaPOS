package lexer

import compiler.{Location, POSLexerError}

import scala.util.parsing.combinator.RegexParsers

object POSLexer extends RegexParsers {
  override def skipWhitespace = true
  override val whiteSpace = "[ \n\t\r\f]+".r

  def apply(code: String): Either[POSLexerError, List[POSToken]] = {
    parse(tokens, code) match {
      case NoSuccess(msg, next) => Left(POSLexerError(Location(next.pos.line, next.pos.column), msg))
      case Success(result, next) => Right(result)
    }
  }

  def tokens: Parser[List[POSToken]] = {
    phrase(rep1(
        renameShop | addItem | deleteItem | updateInventory | addInventory | removeInventory | updatePrice |
        updateCategory | updatePhoto | addToCart | removeFromCart | receiptHeader | reciptFooter | deleteHeader |
        deleteFooter | createShop | identifier | addUser | colon | comma | string | double | digit |
        setElementsGridDimensions | semicolon
    ))
  }

  def identifier: Parser[IDENTIFIER] = positioned {
    """[a-zA-Z_][a-zA-Z0-9_]*""".r ^^ { str => IDENTIFIER(str) }
  }

  def comma                 = positioned {"," ^^ (_ => COMMA())}

  def semicolon           = positioned {";" ^^ (_ => SEMICOLON())}

  def createShop         = positioned { "createShop" ^^ (_ => CREATESHOP()) }
  def renameShop         = positioned { "renameShop" ^^ (_ => RENAMESHOP()) }
  def addItem                = positioned { "addItem" ^^ (_ => ADDITEM()) }
  def deleteItem          = positioned { "deleteItem" ^^ (_ => DELETEITEM()) }
  def updateInventory= positioned { "updateInventory" ^^ (_ => UPDATEINVENTORY()) }
  def addInventory      = positioned { "addInventory" ^^ (_ => ADDINVENTORY()) }
  def removeInventory= positioned { "removeInventory" ^^ (_ => REMOVEINVENTORY()) }
  def updatePrice        = positioned { "updatePrice" ^^ (_ => UPDATEPRICE()) }
  def updateCategory = positioned { "updateCategory" ^^ (_ => UPDATECATEGORY()) }
  def updatePhoto       = positioned { "updatePhoto" ^^ (_ => UPDATEPHOTO()) }

  def addToCart           = positioned { "addToCart" ^^ (_ => ADDTOCART()) }
  def removeFromCart= positioned { "removeFromCart" ^^ (_ => REMOVEFROMCART()) }

  def receiptHeader   = positioned { "receiptHeader" ^^ (_ => RECEIPTHEADER()) }
  def reciptFooter     = positioned { "reciptFooter" ^^ (_ => RECIPTFOOTER()) }
  def deleteHeader     = positioned { "deleteHeader" ^^ (_ => DELETEHEADER()) }
  def deleteFooter     = positioned { "deleteFooter" ^^ (_ => DELETEFOOTER()) }

  def addUser               = positioned { "addUser" ^^(_ => ADDUSER()) }
  def colon                   = positioned {":" ^^ (_ => COLON())}

  def setElementsGridDimensions  = positioned { "setElementsGridDimensions" ^^ (_ => SETELEMENTSGRIDDIMENSIONS()) }

  def double: Parser[DOUBLE] = {"""[0-9]*[.]?[0-9]+""".r ^^ (s => DOUBLE(s.toDouble))}

  def digit: Parser[DIGIT] = positioned {"""[0-9_]+""".r ^^ { num => DIGIT(num.toInt) }}

  def string: Parser[STRING] = positioned {""""[^"]*"""".r ^^ { str =>
                                    val content = str.substring(1, str.length - 1)
                                    STRING(content)
                                  }
                                }


}