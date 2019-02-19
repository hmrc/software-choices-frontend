/*
 * Copyright 2019 HM Revenue & Customs
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
  val UNKNOWN: Filter.Value = Value

  def apply(string: String): Filter.Value = string.toUpperCase match {
    case "AGENT" => AGENT
    case "BUSINESS" => BUSINESS
    case "VIEW_RETURN" => VIEW_RETURN
    case "VIEW_PAYMENTS" => VIEW_PAYMENTS
    case "VIEW_LIABILITIES" => VIEW_LIABILITIES
    case "ACCOUNTING" => ACCOUNTING
    case "SPREADSHEETS" => SPREADSHEETS
    case _ => UNKNOWN
  }
}
