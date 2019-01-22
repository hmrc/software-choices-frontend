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

package views.templates

import assets.testContants.SoftwareProvidersTestConstants
import utils.ViewTestUtils

class FoundProviderTemplateViewSpec extends ViewTestUtils with SoftwareProvidersTestConstants {

  object Selectors {
    val heading = "h2"
    val providerSelector: Int => String = provider => s"li:nth-child($provider) > a"
  }

  "The Found Provider Template" when {

    "given multiple a category with a list of providers" should {

      val providers = Seq(providerA, providerB, providerHash)

      lazy val document = parseView(views.html.templates.found_provider_template(providers))

      for (i <- providers.indices) {

        s"for provider $i" should {

          "have the correct name" in {
            document.select(Selectors.providerSelector(i + 1)).text() shouldBe opensInANewTabSuffix(providers(i).name)
          }

          "have the correct link" in {
            document.select(Selectors.providerSelector(i + 1)).attr("href") shouldBe providers(i).url
          }
        }
      }
    }
  }
}
