//package lexParser
//
//import scala.util.parsing.combinator._
//import scala.util.parsing.input._
//import scala.io.Source
//
//sealed trait POSToken
//
////START LEXER STUFF
////The reserved tokens
//case class IDENTIFIER(str: String) extends POSToken
//case class STRING(str: String) extends POSToken
//case class ALLTOKENS(rawTokens: List[POSToken]) extends POSToken
//
//case object COLON extends POSToken
////case object SEMICOLON extends POSToken
//case object COMMA extends POSToken
//
////case class INDENTATION(spaces: Int) extends POSToken
////case object INDENT extends POSToken
////case object DEDENT extends POSToken
//
////shop tokens
//case object CREATESHOP extends POSToken
//case object RENAMESHOP extends POSToken
//case object ADDITEM extends POSToken
//case object DELETEITEM extends POSToken
//case object UPDATEINVENTORY extends POSToken
//case object ADDINVENTORY extends POSToken
//case object REMOVEINVENTORY extends POSToken
//case object UPDATEPRICE extends POSToken
//case object UPDATECATEGORY extends POSToken
//case object UPDATEPHOTO extends POSToken
//case object SETELEMENTSGRIDDIMENSIONS extends POSToken
//case object ADDTOCART extends POSToken
//case object REMOVEFROMCART extends POSToken
//
////account management tokens (faltan mas)
//case object ADDUSER extends POSToken
//
////receipt tokens
//case object RECEIPTHEADER extends POSToken
//case object RECIPTFOOTER extends POSToken
//case object DELETEHEADER extends POSToken
//case object DELETEFOOTER extends POSToken
////FINISH LEXER STUFF
//
//
////START PARSER STUFF
//sealed trait PosAST
//case class Execute(exp1: PosAST, exp2: PosAST) extends PosAST //dont know if this should stay with two actions or just change it to one
//case class CreateShop(name: String) extends PosAST
//case class CreateShopEmpty() extends PosAST
//case class RenameShop(newName: String) extends PosAST
//case class AddItem(category: String, name: String, photo: String, invAmount: String, price: String) extends PosAST //shouldnt the number parameters be of type Int? could we put em here as strings and then handle the string and do something like toInt to it?
//case class DeleteItem(itemName: String) extends PosAST
//case class UpdateInventory(itemName: String, newAmount: String) extends PosAST
//case class AddInventory(itemName: String, amount: String) extends PosAST
//case class RemoveInventory(itemName: String, amount: String) extends PosAST
//case class UpdatePrice(itemName: String, newPrice: String) extends PosAST
//case class UpdateCategory(itemName: String, newCategory: String) extends PosAST
//case class UpdatePhoto(itemName: String, newPhoto: String) extends PosAST
//case class SetElementGridDimensions(dim1: String, dim2: String) extends PosAST
//case class AddToCart(itemName: String, quantity: String) extends PosAST
//case class RemoveFromCart(itemName: String, quantity: String) extends PosAST
//case class ReceiptHeader(header: String) extends PosAST
//case class ReceiptFooter(footer: String) extends PosAST
//case object DeleteHeader extends PosAST
//case object DeleteFooter extends PosAST
//case class AddUser(userName: String, userCategory: String) extends PosAST
//
//sealed trait ConditionThen { def thenBlock: PosAST }
//case class IfThen(predicate: Condition, thenBlock: PosAST) extends ConditionThen
//case class OtherwiseThen(thenBlock: PosAST) extends ConditionThen
//
//sealed trait Condition
//case class Equals(factName: String, factValue: String) extends Condition
////END PARSER STUFFF
//
//trait POSCompilationError
//case class POSLexerError(msg: String) extends POSCompilationError
//case class POSParserError(msg: String) extends POSCompilationError
//
//
//object POSLexer extends RegexParsers {
//    override def skipWhitespace = true
//
//    override val whiteSpace = "[ \t\r\f\n]+".r
//
//    //Reserved Words
//    def createShop = "createShop" ^^ (_ => CREATESHOP)
//    def renameShop = "renameShop" ^^ (_ => RENAMESHOP)
//    def addItem = "addItem" ^^ (_ => ADDITEM)
//    def deleteItem = "deleteItem" ^^ (_ => DELETEITEM)
//    def updateInventory = "updateInventory" ^^ (_ => UPDATEINVENTORY)
//    def addInventory = "addInventory" ^^ (_ => ADDINVENTORY)
//    def removeInventory = "removeInventory" ^^ (_ => REMOVEINVENTORY)
//    def updatePrice = "updatePrice" ^^ (_ => UPDATEPRICE)
//    def updateCategory = "updateCategory" ^^ (_ => UPDATECATEGORY)
//    def updatePhoto = "updatePhoto" ^^ (_ => UPDATEPHOTO)
//    def setElementsGridDimensions = "setElementsGridDimensions" ^^ (_ => SETELEMENTSGRIDDIMENSIONS)
//    def addToCart = "addToCart" ^^ (_ => ADDTOCART)
//    def removeFromCart = "removeFromCart" ^^ (_ => REMOVEFROMCART)
//
//    def receiptHeader = "receiptHeader" ^^ (_ => RECEIPTHEADER)
//    def reciptFooter = "reciptFooter" ^^ (_ => RECIPTFOOTER)
//    def deleteHeader = "deleteHeader" ^^ (_ => DELETEHEADER)
//    def deleteFooter = "deleteFooter" ^^ (_ => DELETEFOOTER)
//
//    def addUser = "addUser" ^^(_ => ADDUSER)
//
//    //Symbols
//    def colon         = ":"             ^^ (_ => COLON)
//    def comma         = ","             ^^ (_ => COMMA)
//    //def semiColon = ";" ^^(_ => SEMICOLON)
//
////    def string: Parser[POSToken] = {
////        "\"[a-zA-Z_][a-zA-Z0-9_]*\"".r ^^ { str => STRING(str) } //maybe this one is better than the other string def, intente  la otra para dejar que los strings puedan tener espacios libremente
////    }
//
//    def string: Parser[STRING] = {
//        """"[^"]*"""".r ^^ { str =>
//            val content = str.substring(1, str.length - 1)
//            STRING(content)
//        }
//    }
//
//    def identifier: Parser[POSToken] = {
//        "[a-zA-Z_][a-zA-Z0-9_]*".r ^^ { str => IDENTIFIER(str) }
//    }
//
////    private def processIndentations(tokens: List[POSToken],
////                                    indents: List[Int] = List(0)): List[POSToken] = {
////        tokens.headOption match {
////
////            // if there is an increase in indentation level, we push this new level into the stack
////            // and produce an INDENT
////            case Some(INDENTATION(spaces)) if spaces > indents.head =>
////                INDENT :: processIndentations(tokens.tail, spaces :: indents)
////
////            // if there is a decrease, we pop from the stack until we have matched the new level,
////            // producing a DEDENT for each pop
////            case Some(INDENTATION(spaces)) if spaces < indents.head =>
////                val (dropped, kept) = indents.partition(_ > spaces)
////                (dropped map (_ => DEDENT)) ::: processIndentations(tokens.tail, kept)
////
////            // if the indentation level stays unchanged, no tokens are produced
////            case Some(INDENTATION(spaces)) if spaces == indents.head =>
////                processIndentations(tokens.tail, indents)
////
////            // other tokens are ignored
////            case Some(token) =>
////                token :: processIndentations(tokens.tail, indents)
////
////            // the final step is to produce a DEDENT for each indentation level still remaining, thus
////            // "closing" the remaining open INDENTS
////            case None =>
////                indents.filter(_ > 0).map(_ => DEDENT)
////
////        }
////    }
//
//    def tokens: Parser[List[POSToken]] = {
//        phrase(rep1(createShop | addItem | colon | comma | semiColon)) ^^ { rawTokens =>
//            //processIndentations(rawTokens) //we have to make this return a list of tokens. o sea que esta parte sea like another token that "contains" a list of tokens
//            ALLTOKENS(rawTokens)
//        }
//    }
//
//    def apply(code: String): Either[POSLexerError, List[POSToken]] = {
//        parse(tokens, code) match {
//            case NoSuccess(msg, next) => Left(POSLexerError(msg))
//            case Success(result, next) => Right(result)
//        }
//    }
//}
//
//class POSTokenReader(tokens: Seq[POSToken]) extends Reader[POSToken] {
//    def first: POSToken = tokens.head
//    def atEnd: Boolean = tokens.isEmpty
//    override def pos: Position = NoPosition
//    override def rest: Reader[POSToken] = new POSTokenReader(tokens.tail)
//}
//
//object POSParser extends RegexParsers {
//    override type Elem = POSToken
//
//    def program: Parser[PosAST] = {
//        phrase(source)
//    }
//
//    def source: Parser[PosAST] = {
//        rep1(expression) ^^ { case expList => expList reduceRight Execute } //replaced AndThen with Execute (pensando que lo que hace and then es escencialmente execute each step
//    }
//
//    def expression: Parser[PosAST] = {
//      //ShopExp
//        val createShop = CREATESHOP ~ COLON ~ string ^^ {
//            case _ ~ _ ~ STRING(str) => CreateShop(str)
//            case _ ~ _ ~ None => CreateShopEmpty()
//        }
//        val renameShop = RENAMESHOP ~ COLON ~ string ^^ { //val renameShop = RENAMESHOP ~ COLON ~ rep(STRING) ~ SEMICOLON
//            case _ ~ _ ~ STRING(str) => RenameShop(str)
//        }
//      //InvExp
//        val addItem = ADDITEM ~ COLON ~ identifier ~ COMMA ~ identifier ~ COMMA ~ identifier ~ COMMA ~ identifier ~ COMMA ~ identifier ^^ { //should we have separate parsers for numbers? instead of all being in identifier? how exactly do we handle photos?
//            case _ ~ _ ~ IDENTIFIER(category) ~ _ ~ IDENTIFIER(name) ~ _ ~ IDENTIFIER(photo) ~ _ ~ IDENTIFIER(invAmount) ~ _ ~ IDENTIFIER(price) => AddItem(category, name, photo, invAmount, price)
//        }
//        val deleteItem = DELETEITEM ~ COLON ~ identifier ^^ {
//            case _ ~ _ ~ IDENTIFIER(itemName) => DeleteItem(itemName)
//        }
//        val updateInventory = UPDATEINVENTORY ~ COLON ~ identifier ~ COMMA ~ identifier ^^ {
//            case _ ~ _ ~ IDENTIFIER(itemName) ~ _ ~ IDENTIFIER(newAmount) => UpdateInventory(itemName, newAmount)
//        }
//        val addInventory = ADDINVENTORY ~ COLON ~ identifier ~ COMMA ~ identifier ^^ {
//          case _ ~ _ ~ IDENTIFIER(itemName) ~ _ ~ IDENTIFIER(quantity) => AddInventory(itemName, quantity)
//        }
//        val removeInventory = REMOVEINVENTORY ~ COLON ~ identifier ~ COMMA ~ identifier ^^ {
//          case _ ~ _ ~ IDENTIFIER(itemName) ~ _ ~ IDENTIFIER(quantity) => RemoveInventory(itemName, quantity)
//        }
//        val updatePrice = UPDATEPRICE ~ COLON ~ identifier ~ COMMA ~ identifier ^^ {
//          case _ ~ _ ~ IDENTIFIER(itemName) ~ _ ~ IDENTIFIER(newPrice) => UpdatePrice(itemName, newPrice)
//        }
//        val updateCategory = UPDATECATEGORY ~ COLON ~ identifier ~ COMMA ~ identifier ^^ {
//          case _ ~ _ ~ IDENTIFIER(itemName) ~ _ ~ IDENTIFIER(newCategory) => UpdateCategory(itemName, newCategory)
//        }
//        val updatePhoto = UPDATEPHOTO ~ COLON ~ identifier ~ COMMA ~ identifier ^^ {
//          case _ ~ _ ~ IDENTIFIER(itemName) ~ _ ~ IDENTIFIER(newPhoto) => UpdatePhoto(itemName, newPhoto)
//        }
//        val setElementGridDimensions = SETELEMENTGRIDDIMENSIONS ~ COLON ~ identifier ~ COMMA ~ identifier ^^ {
//          case _ ~ _ ~ IDENTIFIER(dim1) ~ _ ~ IDENTIFIER(dim2) => SetElementGridDimensions(dim1, dim2) //this function, along with its token and the prod. rule are subject to change depending on how the backend is implemented - similarly: other functions to deal with the gui and backend might arise
//        }
//        val addToCart = ADDTOCART ~ COLON ~ identifier ~ COMMA ~ identifier ^^ {
//          case _ ~ _ ~ IDENTIFIER(itemName) ~ _ ~ IDENTIFIER(quantity) => AddToCart(itemName, quantity)
//        }
//        val removeFromCart = REMOVEFROMCART ~ COLON ~ identifier ~ COMMA ~ identifier ^^ {
//          case _ ~ _ ~ IDENTIFIER(itemName) ~ _ ~ IDENTIFIER(quantity) => RemoveFromCart(itemName, quantity)
//        }
//      //ReceiptExp
//        val receiptHeader = RECEIPTHEADER ~ COLON ~ string ^^ {
//        case _ ~ _ ~ STRING(header) => ReceiptHeader(header)
//        }
//        val receiptFooter = RECEIPTFOOTER ~ COLON ~ string ^^ {
//          case _ ~ _ ~ STRING(footer) => ReceiptFooter(footer)
//        }
//        val deleteHeader = DELETEHEADER ^^ (_ => DeleteHeader) //the DeleteHeader AST is an object, not a class
//        val deleteFooter = DELETEFOOTER ^^ (_ => DeleteFooter)
//      //AccExp
//        val addUser = ADDUSER ~ COLON ~ identifier ~ COMMA ~ identifier ^^ {
//          case _ ~ _ ~ IDENTIFIER(userName) ~ _ ~ IDENTIFIER(userCategory) => AddUser(userName, userCategory)
//        }
//
//        createShop | renameShop | addItem | deleteItem | updateInventory | addInventory | removeInventory | updatePrice | updateCategory | updatePhoto | setElementGridDimensions | addToCart | removeFromCart | receiptHeader | receiptFooter | deleteHeader | deleteFooter | addUser
//    }
//
//    def apply(tokens: Seq[POSToken]): Either[POSParserError, PosAST] = {
//        val reader = new POSTokenReader(tokens)
//        program(reader) match {
//            case NoSuccess(msg, next) => Left(POSParserError(msg))
//            case Success(result, next) => Right(result)
//        }
//    }
//}
//
//object POSCompiler {
//    def apply(code: String): Either[POSCompilationError, PosAST] = {
//        for {
//            tokens <- POSLexer(code).right
//            ast <- POSParser(tokens).right
//        } yield ast
//    }
//}
//
//
//object TestLexParser {
//    def main(args: Array[String]): Unit = {
//
//        val fileName = "TestFile"
//        var fileContent = Source.fromFile(fileName).getLines.mkString;
//
//        println(POSLexer.apply("createShop"))
//
//        println(POSParser.statement)
//    }
//}