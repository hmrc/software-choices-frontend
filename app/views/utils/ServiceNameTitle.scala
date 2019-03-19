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

package views.utils

import play.api.i18n.Messages

object ServiceNameTitle {

  val error = "common.error"
  val serviceName = "common.service.name"
  val govUk = "common.gov.uk"

  def fullTitle(titleMessage: String, hasErrors: Boolean = false)(implicit messages: Messages): String = {

    val title = s"${messages(titleMessage)} - ${messages(serviceName)} - ${messages(govUk)}"
    if(hasErrors) s"${messages(error)} $title" else title
  }
}
