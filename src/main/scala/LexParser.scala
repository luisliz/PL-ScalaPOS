package lexParser

import scala.util.parsing.combinator._
import scala.io.Source

sealed trait POSToken

//The reserved tokens
case class IDENTIFIER(str: String) extends POSToken
case class LITERAL(str: String) extends POSToken
case class INDENTATION(spaces: Int) extends POSToken
case object EXIT extends POSToken
case object READINPUT extends POSToken
case object CALLSERVICE extends POSToken
case object SWITCH extends POSToken
case object OTHERWISE extends POSToken
case object COLON extends POSToken
case object SEMICOLON extends POSToken
case object ARROW extends POSToken
case object EQUALS extends POSToken
case object COMMA extends POSToken
case object INDENT extends POSToken
case object DEDENT extends POSToken

//shop tokens
case object CREATESHOP extends POSToken
case object RENAMESHOP extends POSToken
case object ADDITEM extends POSToken
case object DELETEITEM extends POSToken
case object UPDATEINVENTORY extends POSToken
case object ADDINVENTORY extends POSToken
case object REMOVEINVENTORY extends POSToken
case object UPDATEPRICE extends POSToken
case object UPDATECATEGORY extends POSToken
case object UPDATEPHOTO extends POSToken
case object SETELEMENTSGRIDDIMENSIONS extends POSToken
case object SETCARTSIZE extends POSToken
case object ADDTOCART extends POSToken //maybe we could do these add and remove un poco mas  modulares? i'll explain later -jorge
case object REMOVEFROMCART extends POSToken

//account management tokens (faltan mas)
case object ADDUSER extends POSToken

//receipt tokens
case object RECEIPTHEADER extends POSToken
case object RECIPTFOOTER extends POSToken
case object DELETEHEADER extends POSToken
case object DELETEFOOTER extends POSToken


trait POSCompilationError
case class POSLexerError(msg: String) extends POSCompilationError

object POSLexer extends RegexParsers {
    override def skipWhitespace = true
    override val whiteSpace = "[ \t\r\f\n]+".r

    //Reserved Words
    def createShop = "createShop" ^^(_ => CREATESHOP)
    def renameShop = "renameShop" ^^(_ => RENAMESHOP)

    def addItem = "addItem" ^^ (_ => ADDITEM)
    def deleteItem = "deleteItem" ^^ (_ => DELETEITEM)
    def updateInventory = "updateInventory" ^^ (_ => UPDATEINVENTORY)
    def addInventory = "addInventory" ^^ (_ => ADDINVENTORY)
    def removeInventory = "removeInventory" ^^ (_ => REMOVEINVENTORY)
    def updatePrice = "updatePrice" ^^ (_ => UPDATEPRICE)
    def updateCategory = "updateCategory" ^^ (_ => UPDATECATEGORY)
    def updatePhoto = "updatePhoto" ^^ (_ => UPDATEPHOTO)
    def setElementsGridDimensions = "setElementsGridDimensions" ^^ (_ => SETELEMENTSGRIDDIMENSIONS)
    def setCartSize = "setCartSize" ^^ (_ => SETCARTSIZE)
    def addToCart = "addToCart" ^^ (_ => ADDTOCART)
    def removeFromCart = "removeFromCart" ^^ (_ => REMOVEFROMCART)

    def receiptHeader = "receiptHeader" ^^ (_ => RECEIPTHEADER)
    def reciptFooter = "reciptFooter" ^^ (_ => RECIPTFOOTER)
    def deleteHeader = "deleteHeader" ^^ (_ => DELETEHEADER)
    def deleteFooter = "deleteFooter" ^^ (_ => DELETEFOOTER)

    def addUser = "addUser" ^^(_ => ADDUSER)

    //Symbols
    def colon         = ":"             ^^ (_ => COLON)
    def comma         = ","             ^^ (_ => COMMA)
    def semiColon = ";" ^^(_ => SEMICOLON)

    def identifier: Parser[POSToken] = {
        "\"[a-zA-Z_][a-zA-Z0-9_]*\"".r ^^ { str => IDENTIFIER(str) }
    }

    private def processIndentations(tokens: List[POSToken],
                                    indents: List[Int] = List(0)): List[POSToken] = {
        tokens.headOption match {

            // if there is an increase in indentation level, we push this new level into the stack
            // and produce an INDENT
            case Some(INDENTATION(spaces)) if spaces > indents.head =>
                INDENT :: processIndentations(tokens.tail, spaces :: indents)

            // if there is a decrease, we pop from the stack until we have matched the new level,
            // producing a DEDENT for each pop
            case Some(INDENTATION(spaces)) if spaces < indents.head =>
                val (dropped, kept) = indents.partition(_ > spaces)
                (dropped map (_ => DEDENT)) ::: processIndentations(tokens.tail, kept)

            // if the indentation level stays unchanged, no tokens are produced
            case Some(INDENTATION(spaces)) if spaces == indents.head =>
                processIndentations(tokens.tail, indents)

            // other tokens are ignored
            case Some(token) =>
                token :: processIndentations(tokens.tail, indents)

            // the final step is to produce a DEDENT for each indentation level still remaining, thus
            // "closing" the remaining open INDENTS
            case None =>
                indents.filter(_ > 0).map(_ => DEDENT)

        }
    }


    def tokens: Parser[List[POSToken]] = {
        phrase(rep1(createShop | addItem | colon | comma | semiColon)) ^^ { rawTokens =>
            processIndentations(rawTokens)
        }
    }

    def apply(code: String): Either[POSLexerError, List[POSToken]] = {
        parse(tokens, code) match {
            case NoSuccess(msg, next) => Left(POSLexerError(msg))
            case Success(result, next) => Right(result)
        }
    }
}

object POSParser extends Parsers {

}



object TestLexParser {
    def main(args: Array[String]): Unit = {

        val fileName = "TestFile"
        var fileContent = Source.fromFile(fileName).getLines.mkString;

        println(POSLexer.apply("createShop"))
    }
}