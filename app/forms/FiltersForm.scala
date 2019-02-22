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

package forms

import enums.Filter
import enums.Filter._
import forms.SearchForm.maxLength
import models.FiltersFormModel
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationResult}

object FiltersForm {

  private[forms] def boolToFilter(filter: Filter.Value): Boolean => Option[Filter.Value] = x => if(x) Some(filter) else None

  final val term = "term"
  final val maxLength = 256

  private val maxLengthCheck: Constraint[Option[String]] = Constraint(s"Search Term Exceeds $maxLength characters")(oTerm =>
    oTerm.fold[ValidationResult](Valid) { term =>
      if (term.length <= maxLength) Valid else Invalid("searchForm.term.max", maxLength)
    }
  )

  val form: Form[FiltersFormModel] = Form(
    mapping(
      term -> optional(text).verifying(maxLengthCheck),
      business -> boolean.transform[Option[Filter.Value]](boolToFilter(BUSINESS), _.nonEmpty),
      agent -> boolean.transform[Option[Filter.Value]](boolToFilter(AGENT), _.nonEmpty),
      viewReturn -> boolean.transform[Option[Filter.Value]](boolToFilter(VIEW_RETURN), _.nonEmpty),
      viewLiabilities -> boolean.transform[Option[Filter.Value]](boolToFilter(VIEW_LIABILITIES), _.nonEmpty),
      viewPayments -> boolean.transform[Option[Filter.Value]](boolToFilter(VIEW_PAYMENTS), _.nonEmpty),
      accounting -> boolean.transform[Option[Filter.Value]](boolToFilter(ACCOUNTING), _.nonEmpty),
      spreadsheets -> boolean.transform[Option[Filter.Value]](boolToFilter(SPREADSHEETS), _.nonEmpty),
      cognitive -> boolean.transform[Option[Filter.Value]](boolToFilter(COGNITIVE), _.nonEmpty),
      visual -> boolean.transform[Option[Filter.Value]](boolToFilter(HEARING), _.nonEmpty),
      hearing -> boolean.transform[Option[Filter.Value]](boolToFilter(MOTOR), _.nonEmpty),
      motor -> boolean.transform[Option[Filter.Value]](boolToFilter(VISUAL), _.nonEmpty)

    )(FiltersFormModel.customApply)(FiltersFormModel.customUnapply)
  )
}
