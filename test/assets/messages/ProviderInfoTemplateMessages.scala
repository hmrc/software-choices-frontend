/*
 * Copyright 2020 HM Revenue & Customs
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

package messages

object ProviderInfoTemplateMessages {
  def forBoth(name: String): String = s"$name is suitable for businesses or agents."
  def forBusinesses(name: String): String = s"$name is suitable for businesses."
  def forAgents(name: String): String = s"$name is suitable for agents."

  val softwareType = "Software type"
  val records = "Record-keeping software"
  val bridging = "Bridging software"

  val softwareFeatures = "VAT specific features"
  val submitVat = "Submit VAT returns"
  val viewReturns = "View submitted VAT returns"
  val viewLiabilities = "Check what VAT you owe"
  val viewPayments = "View VAT payment history"

  def accessibilityFeature(feature: String): String = s"This software includes $feature accessibility features."
  def accessibilityFeatures(features: List[String]): String = s"This software includes ${features.tail.mkString(", ")} and ${features.head} accessibility features."

  val free = "There is a free version of this software."

  def link(name: String): String = s"read more information on the $name website (opens in a new tab)"
  def visit(name: String): String = "You can " + link(name)
}
