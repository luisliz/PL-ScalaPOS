import scala.io.Source

object Main {
  def main(args: Array[String]) {
    val largeFile = Source.fromFile("largeFile.spos").mkString.stripMargin.trim
    val shortFile = Source.fromFile("shortFile.spos").mkString.stripMargin.trim

    val parser = new LexThisParseThat

    val result = parser.parseAll(parser.program, shortFile)

    println(result.get)

    var flag = true

    while (flag) {
      println("Enter command:")
      val input: String = scala.io.StdIn.readLine()
      if (input == "quit") { flag = false }
      else { val toParse = parser.parseAll(parser.expr, input) }
    }
  }
}