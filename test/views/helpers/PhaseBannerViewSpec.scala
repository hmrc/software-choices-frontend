/*
 * Copyright 2026 HM Revenue & Customs
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

package views.helpers

import views.ViewBaseSpec
import views.html.helpers.phaseBanner

class PhaseBannerViewSpec extends ViewBaseSpec {

  val view: phaseBanner = app.injector.instanceOf[phaseBanner]

  object Selectors {
    val banner = ".govuk-phase-banner"
    val tag = ".govuk-phase-banner__content__tag"
    val text = ".govuk-phase-banner__text"
    val feedbackLink = "#beta-banner-feedback"
  }

  "The Phase Banner" should {

    lazy val document = parseView(view())

    "have a BETA tag" in {
      document.select(Selectors.tag).text shouldBe "BETA"
    }

    "have the correct feedback text" in {
      document.select(Selectors.text).text shouldBe
        s"${messages("feedback.before")} ${messages("feedback.link")} ${messages("feedback.after")}"
    }

    "have a feedback link with the correct href" in {
      document.select(Selectors.feedbackLink).attr("href") shouldBe appConfig.feedbackUrl
    }

    "have a feedback link with the correct text" in {
      document.select(Selectors.feedbackLink).text shouldBe messages("feedback.link")
    }
  }
}
