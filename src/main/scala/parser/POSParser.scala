package parser

import compiler.{Location, POSParserError}
import lexer._

import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.{NoPosition, Position, Reader}

object POSParser extends Parsers {
  override type Elem = POSToken

  class POSTokenReader(tokens: Seq[POSToken]) extends Reader[POSToken] {
    override def first: POSToken = tokens.head
    override def atEnd: Boolean = tokens.isEmpty
    override def pos: Position = tokens.headOption.map(_.pos).getOrElse(NoPosition)
    override def rest: Reader[POSToken] = new POSTokenReader(tokens.tail)
  }


  def apply(tokens: Seq[POSToken]): Either[POSParserError, POSAST] = {
    val reader = new POSTokenReader(tokens)
    program(reader) match {
      case NoSuccess(msg, next) => Left(POSParserError(Location(next.pos.line, next.pos.column), msg))
      case Success(result, next) => Right(result)
    }
  }

  def program: Parser[POSAST] = positioned {
    phrase(block)
  }

  def block: Parser[POSAST] = positioned {
    rep1(statement) ^^ { case stmtList => stmtList reduceRight AndThen }
  }

  def statement: Parser[POSAST] = positioned {

    val createShop = CREATESHOP() ~ COLON() ~ string ^^ {
      case _ ~ _ ~ STRING(str) => CreateShop(str)
        //ShopExp ::= CREATESHOP COLON {STRING} | RENAMESHOP COLON STRING
      //case _ => CreateShopEmpty()
    }

/*
    val exit = EXIT() ^^ (_ => Exit)

    val readInput = READINPUT() ~ rep(identifier ~ COMMA()) ~ identifier ^^ {
      case read ~ inputs ~ IDENTIFIER(lastInput) => ReadInput(inputs.map(_._1.str) ++ List(lastInput))
    }
    val callService = CALLSERVICE() ~ literal ^^ {
      case call ~ LITERAL(serviceName) => CallService(serviceName)
    }
    val switch = SWITCH() ~ COLON() ~ INDENT() ~ rep1(ifThen) ~ opt(otherwiseThen) ~ DEDENT() ^^ {
      case _ ~ _ ~ _ ~ ifs ~ otherwise ~ _ => Choice(ifs ++ otherwise)
    }*/

    createShop

    //exit | readInput | callService | switch
  }

  /*def ifThen: Parser[IfThen] = positioned {
    (condition ~ ARROW() ~ INDENT() ~ block ~ DEDENT()) ^^ {
      case cond ~ _ ~ _ ~ block ~ _ => IfThen(cond, block)
    }
  }

  def otherwiseThen: Parser[OtherwiseThen] = positioned {
    (OTHERWISE() ~ ARROW() ~ INDENT() ~ block ~ DEDENT()) ^^ {
      case _ ~ _ ~ _ ~ block ~ _ => OtherwiseThen(block)
    }
  }

  def condition: Parser[Equals] = positioned {
    (identifier ~ EQUALS() ~ literal) ^^ { case IDENTIFIER(id) ~ eq ~ LITERAL(lit) => Equals(id, lit) }
  }*/

  private def identifier: Parser[IDENTIFIER] = positioned {
    accept("identifier", { case id @ IDENTIFIER(name) => id })
  }

  private def string: Parser[STRING] = positioned {
    accept("string", { case lit @ STRING(name) => lit })
  }

}