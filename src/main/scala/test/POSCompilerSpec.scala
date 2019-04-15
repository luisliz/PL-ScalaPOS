package test

import compiler.{Location, POSCompiler, POSParserError}
import parser._
import org.scalatest.{FlatSpec, Matchers}

class POSCompilerSpec extends FlatSpec with Matchers {

  val validCode =
    """
      |read input name, country
      |switch:
      |  country == "PT" ->
      |    call service "A"
      |    exit
      |  otherwise ->
      |    call service "B"
      |    switch:
      |      name == "unknown" ->
      |        exit
      |      otherwise ->
      |        call service "C"
      |        exit
    """.stripMargin.trim

  val invalidCode =
    """
      |read input name, country
      |switch:
      |  country == PT ->
      |    call service "A"
      |    exit
      |  otherwise ->
      |    call service "B"
      |    switch:
      |      name == "unknown" ->
      |        exit
      |      otherwise ->
      |        call service "C"
      |        exit
    """.stripMargin.trim

  val successfulAST = AndThen(
    ReadInput(List("name", "country")),
    Choice(List(
      IfThen( Equals("country", "PT"), AndThen(CallService("A"), Exit) ),
      OtherwiseThen(
        AndThen(
          CallService("B"),
          Choice(List(
            IfThen( Equals("name", "unknown"), Exit ),
            OtherwiseThen( AndThen(CallService("C"), Exit) )
          ))
        )
      )
    ))
  )

  val errorMsg = POSParserError(Location(3,14), "string literal expected")




  "POS compiler" should "successfully parse a valid POS" in {
    POSCompiler(validCode) shouldBe Right(successfulAST)
  }

  it should "return an error with an invalid POS" in {
    POSCompiler(invalidCode) shouldBe Left(errorMsg)
  }

}
