package lexer

import scala.util.parsing.input.Positional

sealed trait POSToken extends Positional

case class IDENTIFIER(str: String) extends POSToken
//case class LITERAL(str: String) extends POSToken
//case class INDENTATION(spaces: Int) extends POSToken

case class STRING(str: String) extends POSToken
case class DIGIT(digit: Int) extends POSToken
case class DOUBLE(double: Double) extends POSToken
//case class ALLTOKENS(rawTokens: List[POSToken]) extends POSToken


//case class EXIT() extends POSToken
//case class READINPUT() extends POSToken
//case class CALLSERVICE() extends POSToken
//case class SWITCH() extends POSToken
//case class OTHERWISE() extends POSToken
//case class COLON() extends POSToken
//case class ARROW() extends POSToken
//case class EQUALS() extends POSToken
//case class COMMA() extends POSToken
case class INDENT() extends POSToken
case class DEDENT() extends POSToken
case class INDENTATION(spaces: Int) extends POSToken



case class COLON() extends POSToken
case class SEMICOLON() extends POSToken
case class COMMA() extends POSToken
//testing quote token
case class QUOTE() extends POSToken



//shop tokens
case class CREATESHOP() extends POSToken
case class RENAMESHOP() extends POSToken
case class ADDITEM() extends POSToken
case class DELETEITEM() extends POSToken
case class UPDATEINVENTORY() extends POSToken
case class ADDINVENTORY() extends POSToken
case class REMOVEINVENTORY() extends POSToken
case class UPDATEPRICE() extends POSToken
case class UPDATECATEGORY() extends POSToken
case class UPDATEPHOTO() extends POSToken
case class SETELEMENTSGRIDDIMENSIONS() extends POSToken
case class ADDTOCART() extends POSToken
case class REMOVEFROMCART() extends POSToken

//account management tokens (faltan mas)
case class ADDUSER() extends POSToken

//receipt tokens
case class RECEIPTHEADER() extends POSToken
case class RECIPTFOOTER() extends POSToken
case class DELETEHEADER() extends POSToken
case class DELETEFOOTER() extends POSToken
//FINISH LEXER STUFF