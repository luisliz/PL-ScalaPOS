package compiler

sealed trait POSCompilationError

case class POSLexerError(location: Location, msg: String) extends POSCompilationError
case class POSParserError(location: Location, msg: String) extends POSCompilationError

case class Location(line: Int, column: Int) {
  override def toString = s"$line:$column"
}