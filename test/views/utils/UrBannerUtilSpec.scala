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

package views.utils

import play.api.i18n.Lang
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.{Cy, En}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.userresearchbanner.UserResearchBanner
import utils.TestUtils

class UrBannerUtilSpec extends TestUtils {

  "getUrBanner" when {

    "the language is English" should {

      "return a UserResearchBanner with En language and the English URL" in {
        UrBannerUtil.getUrBanner() shouldBe UserResearchBanner(
          language = En,
          url = appConfig.urBannerBaseUrl,
          hideCloseButton = true
        )
      }
    }

    "the language is Welsh" should {

      "return a UserResearchBanner with Cy language and the Welsh URL" in {
        val welshMessages = messagesApi.preferred(Seq(Lang("cy")))
        UrBannerUtil.getUrBanner()(appConfig, welshMessages) shouldBe UserResearchBanner(
          language = Cy,
          url = s"${appConfig.urBannerBaseUrl}&Q_Language=CY",
          hideCloseButton = true
        )
      }
    }

    "hideCloseButton is false" should {

      "return a UserResearchBanner with hideCloseButton set to false" in {
        val result = UrBannerUtil.getUrBanner(hideCloseButton = false)
        result.hideCloseButton shouldBe false
      }
    }
  }
}
