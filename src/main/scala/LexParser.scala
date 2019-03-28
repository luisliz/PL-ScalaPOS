package lexParser

import scala.util.parsing.combinator._

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
case object ARROW extends POSToken
case object EQUALS extends POSToken
case object COMMA extends POSToken
case object INDENT extends POSToken
case object DEDENT extends POSToken

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
case object RECEIPTHEADER extends POSToken
case object RECIPTFOOTER extends POSToken
case object DELETEHEADER extends POSToken
case object DELETEFOOTER extends POSToken
case object ADDUSER extends POSToken

class LexParser extends RegexParsers {
    override def skipWhitespace = true
    override val whiteSpace = "[ \t\r\f\n]+".r

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

    def receiptHeader = "receiptHeader" ^^ (_ => RECEIPTHEADER)
    def reciptFooter = "reciptFooter" ^^ (_ => RECIPTFOOTER)
    def deleteHeader = "deleteHeader" ^^ (_ => DELETEHEADER)
    def deleteFooter = "deleteFooter" ^^ (_ => DELETEFOOTER)

    def addUser = "addUser" ^^(_ => ADDUSER)


    /*def tokens: Parser[List[POSToken]] = {
        phrase(rep1(createShop | addItem)) ^^ { rawTokens =>
            var stuf: List[POSToken];
            println("Got Here");
            return stuf.addString("jeu");
        }
    }
*/

    def identifier: Parser[IDENTIFIER] = {
      "[a-zA-Z_][a-zA-Z0-9_]*".r ^^ { str => IDENTIFIER(str) }
    }
}



object TestLexParser extends LexParser {
    def main(args: Array[String]): Unit = {

        parse(identifier, "isidh") match {
            case Success(matched,_) => println(matched)
            case Failure(msg,_) => println(s"FAILURE: $msg")
            case Error(msg,_) => println(s"ERROR: $msg")
        }
    }
}