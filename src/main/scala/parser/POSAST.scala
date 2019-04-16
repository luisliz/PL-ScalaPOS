package parser

import scala.util.parsing.input.Positional

sealed trait POSAST extends Positional

case class AndThen(step1: POSAST, step2: POSAST) extends POSAST

//case class ReadInput(inputs: Seq[String]) extends POSAST
//case class CallService(serviceName: String) extends POSAST
//case class Choice(alternatives: Seq[ConditionThen]) extends POSAST
//case object Exit extends POSAST
//
//sealed trait ConditionThen extends Positional { def thenBlock: POSAST }
//case class IfThen(predicate: Condition, thenBlock: POSAST) extends ConditionThen
//case class OtherwiseThen(thenBlock: POSAST) extends ConditionThen
//
//sealed trait Condition extends Positional
//case class Equals(factName: String, factValue: String) extends Condition

case class CreateShop(shopName: String) extends POSAST

case class CreateShopEmpty() extends POSAST

case class AddItem(category: String, name: String, photo: String, invAmount: Int, price: Double) extends POSAST