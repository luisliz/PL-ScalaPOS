import scala.util.parsing.combinator.RegexParsers

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

object POSToken extends RegexParsers {
    override def skipWhitespace = true
    override val whiteSpace = "[ \t\r\f\n]+".r

    def createShop = "createShop" ^^ (_ => CREATESHOP)
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


    def tokens: Parser[List[POSToken]] = {
        phrase(rep1(createShop | addItem)) ^^ { rawTokens =>
            println(rawTokens)
        }
    }


    /*def identifier: Parser[IDENTIFIER] = {
      "[a-zA-Z_][a-zA-Z0-9_]*".r ^^ { str => IDENTIFIER(str) }
    }



    def term  : Parser[Double] = factor ~ rep( "*" ~ factor | "/" ~ factor) ^^ {
      case number ~ list => (number /: list) {
        case (x, "*" ~ y) => x * y
        case (x, "/" ~ y) => x / y
      }
    }

  */
    //Patterns are recognized as term ~ exp ~ number, the ~ is the parser composition
    def expr  : Parser[Double] = term ~ rep("+" ~ log(term)("Plus term") | "-" ~ log(term)("Minus term")) ^^ {
        case number ~ list => list.foldLeft(number) { // same as before, using alternate name for /:
            case (x, "+" ~ y) => x + y
            case (x, "-" ~ y) => x - y
        }
    }

    def apply(tokens: Seq[POSToken]): Either[WorkflowParserError, WorkflowAST] = {
        val reader = new WorkflowTokenReader(tokens)
        program(reader) match {
            case NoSuccess(msg, next) => Left(WorkflowParserError(msg))
            case Success(result, next) => Right(result)
        }
    }
}