/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package enums

object Filter extends Enumeration {

  val AGENT: Filter.Value = Value
  val BUSINESS: Filter.Value = Value
  val VIEW_RETURN: Filter.Value = Value
  val VIEW_PAYMENTS: Filter.Value = Value
  val VIEW_LIABILITIES: Filter.Value = Value
  val ACCOUNTING: Filter.Value = Value
  val SPREADSHEETS: Filter.Value = Value
  val COGNITIVE: Filter.Value = Value
  val HEARING: Filter.Value = Value
  val MOTOR: Filter.Value = Value
  val VISUAL: Filter.Value = Value
  val FREE: Filter.Value = Value
  val WELSH: Filter.Value = Value

  val UNKNOWN: Filter.Value = Value

  final val agent = AGENT.toString
  final val business = BUSINESS.toString
  final val viewReturn = VIEW_RETURN.toString
  final val viewPayments = VIEW_PAYMENTS.toString
  final val viewLiabilities = VIEW_LIABILITIES.toString
  final val accounting = ACCOUNTING.toString
  final val spreadsheets = SPREADSHEETS.toString
  final val cognitive = COGNITIVE.toString
  final val hearing = HEARING.toString
  final val motor = MOTOR.toString
  final val visual = VISUAL.toString
  final val free = FREE.toString
  final val welsh = WELSH.toString

  //scalastyle:off
  def apply(string: String): Filter.Value = string.toUpperCase match {
    case `agent` => AGENT
    case `business` => BUSINESS
    case `viewReturn` => VIEW_RETURN
    case `viewPayments` => VIEW_PAYMENTS
    case `viewLiabilities` => VIEW_LIABILITIES
    case `accounting` => ACCOUNTING
    case `spreadsheets` => SPREADSHEETS
    case `cognitive` => COGNITIVE
    case `hearing` => HEARING
    case `motor` => MOTOR
    case `visual` => VISUAL
    case `free` => FREE
    case `welsh` => WELSH
    case _ => UNKNOWN
  }
}
