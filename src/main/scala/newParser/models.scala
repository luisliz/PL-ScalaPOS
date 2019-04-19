package newParser

import scala.collection.mutable.HashMap
import scala.util.parsing.input.Positional


trait Statement extends Positional

class Program(val functions: List[Function], val statements: List[Statement])

case class Function(name: String, arguments: Map[String, Int], statements: List[Statement], val returnValue: Expr)

class Expr extends Positional
case class Number(value: Int) extends Expr
case class Operator(op: String, var left: Expr, var right: Expr) extends Expr
case class Identifier(name: String) extends Expr

