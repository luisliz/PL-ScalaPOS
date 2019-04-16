package lexer

import compiler.{Location, POSLexerError}

import scala.util.parsing.combinator.RegexParsers

object POSLexer extends RegexParsers {
  override def skipWhitespace = true
  override val whiteSpace = "[ \t\r\f]+".r

  def apply(code: String): Either[POSLexerError, List[POSToken]] = {
    parse(tokens, code) match {
      case NoSuccess(msg, next) => Left(POSLexerError(Location(next.pos.line, next.pos.column), msg))
      case Success(result, next) => Right(result)
    }
  }

  def tokens: Parser[List[POSToken]] = {
    phrase(rep1(
       renameShop | addItem| deleteItem| updateInventory| addInventory| removeInventory|
      updatePrice| updateCategory| updatePhoto| setElementsGridDimensions| addToCart| removeFromCart | receiptHeader|
      reciptFooter| deleteHeader| deleteFooter | addUser | createShop
    )) ^^ { rawTokens =>
      processIndentations(rawTokens)
    }
  }

  private def processIndentations(tokens: List[POSToken],
                                  indents: List[Int] = List(0)): List[POSToken] = {
    tokens.headOption match {

      // if there is an increase in indentation level, we push this new level into the stack
      // and produce an INDENT
     /* case Some(INDENTATION(spaces)) if spaces > indents.head =>
        INDENT() :: processIndentations(tokens.tail, spaces :: indents)

      // if there is a decrease, we pop from the stack until we have matched the new level and
      // we produce a DEDENT for each pop
      case Some(INDENTATION(spaces)) if spaces < indents.head =>
        val (dropped, kept) = indents.partition(_ > spaces)
        (dropped map (_ => DEDENT())) ::: processIndentations(tokens.tail, kept)

      // if the indentation level stays unchanged, no tokens are produced
      case Some(INDENTATION(spaces)) if spaces == indents.head =>
        processIndentations(tokens.tail, indents)*/

      // other tokens are ignored
      case Some(token) =>
        token :: processIndentations(tokens.tail, indents)

      // the final step is to produce a DEDENT for each indentation level still remaining, thus
      // "closing" the remaining open INDENTS
      /*case None =>
        indents.filter(_ > 0).map(_ => DEDENT())*/

    }
  }



  /*def indentation: Parser[INDENTATION] = positioned {
    "\n[ ]*".r ^^ { whitespace =>
      val nSpaces = whitespace.length - 1
      INDENTATION(nSpaces)
    }
  }*/



  def createShop    = positioned { "createshop" ^^ (_ => CREATESHOP()) }
  def renameShop    = positioned { "renameShop" ^^ (_ => RENAMESHOP()) }
  def addItem     = positioned { "addItem" ^^ (_ => ADDITEM()) }
  def deleteItem    = positioned { "deleteItem" ^^ (_ => DELETEITEM()) }
  def updateInventory     = positioned { "updateInventory" ^^ (_ => UPDATEINVENTORY()) }
  def addInventory    = positioned { "addInventory" ^^ (_ => ADDINVENTORY()) }
  def removeInventory     = positioned { "removeInventory" ^^ (_ => REMOVEINVENTORY()) }
  def updatePrice     = positioned { "updatePrice" ^^ (_ => UPDATEPRICE()) }
  def updateCategory    = positioned { "updateCategory" ^^ (_ => UPDATECATEGORY()) }
  def updatePhoto     = positioned { "updatePhoto" ^^ (_ => UPDATEPHOTO()) }
  def setElementsGridDimensions     = positioned { "setElementsGridDimensions" ^^ (_ => SETELEMENTSGRIDDIMENSIONS()) }
  def addToCart     = positioned { "addToCart" ^^ (_ => ADDTOCART()) }
  def removeFromCart    = positioned { "removeFromCart" ^^ (_ => REMOVEFROMCART()) }

  def receiptHeader     = positioned { "receiptHeader" ^^ (_ => RECEIPTHEADER()) }
  def reciptFooter    = positioned { "reciptFooter" ^^ (_ => RECIPTFOOTER()) }
  def deleteHeader    = positioned { "deleteHeader" ^^ (_ => DELETEHEADER()) }
  def deleteFooter    = positioned { "deleteFooter" ^^ (_ => DELETEFOOTER()) }

  def addUser     = positioned { "addUser" ^^(_ => ADDUSER()) }

  //Symbols
  def colon             = positioned { ":"             ^^ (_ => COLON()) }
  def comma             = positioned { ","             ^^ (_ => COMMA()) }

  def identifier: Parser[IDENTIFIER] = positioned {
    "[a-zA-Z_][a-zA-Z0-9_]*".r ^^ { str => IDENTIFIER(str) }
  }

  def string: Parser[STRING] = positioned {
    """"[^"]*"""".r ^^ { str =>
      val content = str.substring(1, str.length - 1)
      STRING(content)
    }
  }

}