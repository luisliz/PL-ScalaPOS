package compiler

import lexer.POSLexer
import parser.{POSParser, POSAST}

object POSCompiler {
  def apply(code: String): Either[POSCompilationError, POSAST] = {
    for {
      tokens <- POSLexer(code).right
      ast <- POSParser(tokens).right
    } yield ast
  }
}