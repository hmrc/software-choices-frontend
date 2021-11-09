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

package views.templates

import testContants.SoftwareProvidersTestConstants
import views.ViewBaseSpec
import views.html.templates.provider_table_template

class ProviderTableTemplateViewSpec extends ViewBaseSpec with SoftwareProvidersTestConstants {

  val view = app.injector.instanceOf[provider_table_template]

  object Selectors {
    val providerSelector: Int => String = provider => s"table tr:nth-child($provider) > td > a"
    val providerDetailsSelector: Int => String = provider => s"table tr:nth-child($provider) > td > details"
    val noscriptLinkSelector: Int => String = provider => s"table tr:nth-child($provider) > td > noscript > a"
  }

  "The Provider Table Template" when {

    "a list of providers with hrefs when provider details FS is off" should {

      lazy val document = parseView(view(categoryAProviders, 10))

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

    "a list of providers accordions when provider details FS is on" should {

      lazy val document = parseView(view(categoryAProviders, 10, providerDetailsFilterFeature = true))

      for (i <- categoryAProviders.indices) {

        s"for provider $i" should {

          s"have a noscript tag with a link" in {
            val url = s"/making-tax-digital-software/software-information?providerName=${categoryAProviders(i).name}"

            document.select(Selectors.noscriptLinkSelector(i + 1)).attr("href") shouldBe url
          }

          s"have the correct name" in {
            document.select(Selectors.providerDetailsSelector(i + 1)).first().text() shouldBe categoryAProviders(i).name
          }

        }
      }
    }
  }
}
