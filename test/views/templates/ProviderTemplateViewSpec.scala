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

package views.templates

import assets.testContants.SoftwareProvidersTestConstants
import views.ViewBaseSpec

class ProviderTemplateViewSpec extends ViewBaseSpec with SoftwareProvidersTestConstants {

  object Selectors {
    val heading = "h2"
    val providerSelector: Int => String = provider => s"li:nth-child($provider) > a"
  }

  "The Provider Template" when {

    "given multiple a category with a list of providers" should {

      val category = "A"

      lazy val document = parseView(views.html.templates.provider_template(category, categoryAProviders))

      s"have a the correct section heading" in {
        document.select(Selectors.heading).text() shouldBe category
      }

      s"have a the correct aria-label for the section heading" in {
        document.select(Selectors.heading).attr("aria-label") shouldBe softwareCategoryAriaLabel(category)
      }

      for (i <- categoryAProviders.indices) {

        s"for provider $i" should {

          s"have the correct name" in {
            document.select(Selectors.providerSelector(i + 1)).text() shouldBe categoryAProviders(i).name
          }

          "have the correct link" in {
            document.select(Selectors.providerSelector(i + 1)).attr("href") shouldBe categoryAProviders(i).url
          }

          "have the correct aria-label to support screenreaders" in {
            document.select(Selectors.providerSelector(i + 1)).attr("aria-label") shouldBe softwareCompanyAriaLabel(categoryAProviders(i).name)
          }
        }
      }
    }
  }
}
