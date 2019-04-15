package lexer

import scala.util.parsing.input.Positional

sealed trait POSToken extends Positional

case class IDENTIFIER(str: String) extends POSToken
case class LITERAL(str: String) extends POSToken
case class INDENTATION(spaces: Int) extends POSToken
case class EXIT() extends POSToken
case class READINPUT() extends POSToken
case class CALLSERVICE() extends POSToken
case class SWITCH() extends POSToken
case class OTHERWISE() extends POSToken
case class COLON() extends POSToken
case class ARROW() extends POSToken
case class EQUALS() extends POSToken
case class COMMA() extends POSToken
case class INDENT() extends POSToken
case class DEDENT() extends POSToken