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

  private def string: Parser[STRING] = positioned {
    accept("string", { case lit@STRING(name) => lit })
  }

  private def digit: Parser[DIGIT] = positioned {
    accept("digit", { case num@DIGIT(name) => num })
  }

  private def double: Parser[DOUBLE] = positioned {
    accept("double", { case d@DOUBLE(name) => d })
  }

  def statement: Parser[POSAST] = positioned {

    val createShop = CREATESHOP() ~ COLON() ~ string ~ SEMICOLON() ^^ {
      case _ ~ _ ~ STRING(str) ~ _ => CreateShop(str)
      //case _ => CreateShopEmpty()
    }

    val addItem = ADDITEM() ~ COLON() ~ string ~ COMMA() ~ string ~ COMMA() ~ string ~ COMMA() ~ digit ~ COMMA() ~ double ~ SEMICOLON() ^^ { //i think photo should have its own regex (token) and there should also be numbers
      case _ ~ _ ~ STRING(category) ~ _ ~ STRING(name) ~ _ ~ STRING(photo) ~ _ ~ DIGIT(invAmount) ~ _ ~ DOUBLE(price) ~ _ => AddItem(category, name, photo, invAmount, price)
    }

    createShop | addItem
  }
}