/*
 * Copyright 2021 HM Revenue & Customs
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

package views

import testContants.SoftwareProvidersTestConstants
import views.html.software_choices_software_information


class SoftwareInformationViewSpec extends ViewBaseSpec with SoftwareProvidersTestConstants {

  val view = app.injector.instanceOf[software_choices_software_information]

  object Messages {
    val backButton = "Back"
    val heading = "aName"

  }

  "The software information page" should {

    lazy val document = parseView(view(providerA))

    "have a back button" in {
      document.select(".govuk-back-link").text shouldBe Messages.backButton
    }
    "have a heading" in {
      document.select("h1").text shouldBe Messages.heading
    }
  }
}
