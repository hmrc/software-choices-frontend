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

package models

import enums.Filter
import enums.Filter._

case class FiltersFormModel(filters: Seq[Filter.Value],
                            searchTerm: Option[String] = None)

object FiltersFormModel {

  def customApply(searchTerm: Option[String] = None,
                  business: Option[Filter.Value] = None,
                  agent: Option[Filter.Value] = None,
                  viewReturn: Option[Filter.Value] = None,
                  viewLiabilities: Option[Filter.Value] = None,
                  viewPayments: Option[Filter.Value] = None,
                  accounting: Option[Filter.Value] = None,
                  spreadsheets: Option[Filter.Value] = None,
                  cognitive: Option[Filter.Value] = None,
                  visual: Option[Filter.Value] = None,
                  hearing: Option[Filter.Value] = None,
                  motor: Option[Filter.Value] = None,
                  free: Option[Filter.Value] = None,
                  welsh: Option[Filter.Value] = None): FiltersFormModel = {

    val filters: Seq[Filter.Value] = Seq(
      business,
      agent,
      viewReturn,
      viewLiabilities,
      viewPayments,
      accounting,
      spreadsheets,
      cognitive,
      visual,
      hearing,
      motor,
      free,
      welsh
    ).flatten

    new FiltersFormModel(filters, searchTerm)
  }

  def customUnapply(arg: FiltersFormModel): Option[(Option[String],
    Option[Filter.Value],
    Option[Filter.Value],
    Option[Filter.Value],
    Option[Filter.Value],
    Option[Filter.Value],
    Option[Filter.Value],
    Option[Filter.Value],
    Option[Filter.Value],
    Option[Filter.Value],
    Option[Filter.Value],
    Option[Filter.Value],
    Option[Filter.Value],
    Option[Filter.Value])] = {

    def giveBack(filter: Filter.Value): Option[Filter.Value] = if (arg.filters.contains(filter)) Some(filter) else None

    Some((
      arg.searchTerm,
      giveBack(BUSINESS),
      giveBack(AGENT),
      giveBack(VIEW_RETURN),
      giveBack(VIEW_LIABILITIES),
      giveBack(VIEW_PAYMENTS),
      giveBack(ACCOUNTING),
      giveBack(SPREADSHEETS),
      giveBack(COGNITIVE),
      giveBack(HEARING),
      giveBack(MOTOR),
      giveBack(VISUAL),
      giveBack(FREE),
      giveBack(WELSH)
    ))
  }
}
