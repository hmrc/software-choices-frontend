/*
 * Copyright 2024 HM Revenue & Customs
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

import forms.FiltersForm
import play.api.data.FormError
import utils.TestUtils

class ViewUtilsSpec extends TestUtils {

  "ViewUtils.title" should {
    "return correct title" in {
      val form = FiltersForm.form
      val expectedResult = " service.title - service.name - site.govuk"
      val actualResult = ViewUtils.title(form, "service.title")

      actualResult shouldBe expectedResult
    }

    "return correct title with error prefix" in {
      val form = FiltersForm.form.copy(errors = Seq(FormError.apply("e1", "some error msg")))
      val expectedResult = "error.browser.title.prefix service.title - service.name - site.govuk"
      val actualResult = ViewUtils.title(form, "service.title")

      actualResult shouldBe expectedResult
    }
  }
}