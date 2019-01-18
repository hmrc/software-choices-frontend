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

import models.SearchModel
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.{Constraint, Invalid, Valid}

object SearchForm {

  private val emptyCheck: Constraint[String] = Constraint("No Search Term Provided")(term =>
    if (term.nonEmpty) Valid else Invalid("searchForm.term.missing")
  )

  val maxLength = 256
  private val maxLengthCheck: Constraint[String] = Constraint(s"Search Term Exceeds $maxLength characters")(term =>
    if (term.length <= maxLength) Valid else Invalid("searchForm.term.max")
  )

  val term = "term"

  val form: Form[SearchModel] = Form(
    mapping(
      term -> text
        .transform[String](_.trim, x => x)
        .verifying(emptyCheck, maxLengthCheck)
    )(SearchModel.apply)(SearchModel.unapply)
  )
}