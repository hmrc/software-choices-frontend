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

package models

import _root_.utils.TestUtils
import views.html.templates.provider_table_template

class SoftwareChoicesFilterViewModelSpec extends TestUtils {

  val view = app.injector.instanceOf[provider_table_template]

  val upperA = SoftwareProviderModel("Abc", "url")
  val lowerA = SoftwareProviderModel("abc", "url")
  val upperZ = SoftwareProviderModel("Zyx", "url")
  val lowerZ = SoftwareProviderModel("zyx", "url")
  val hashSymbol = SoftwareProviderModel("#", "url")
  val atSymbol = SoftwareProviderModel("@", "url")
  val number = SoftwareProviderModel("1", "url")

  val softwareProvidersAll = Seq(upperA, lowerA, upperZ, lowerZ, hashSymbol, atSymbol, number)

  val softwareProvidersFound = Seq(upperA, lowerA)

  appConfig.providerDetailsEnabled(false)

  "SoftwareChoicesFilterViewModel.sortedProviders" should {

    "sort the list correctly" in {

      val expected = Seq(hashSymbol, number, atSymbol, upperA, lowerA, upperZ, lowerZ)
      val actual = SoftwareChoicesFilterViewModel(softwareProvidersAll, None, view).sortedProviders(softwareProvidersAll)

      actual shouldBe expected
    }
  }


  "SoftwareChoicesFilterViewModel.renderProviders" when {

    "no search has been exectuted" should {

      "render the expected provider table with all providers" in {

        val model = SoftwareChoicesFilterViewModel(softwareProvidersAll, None, view)
        val expected = view(model.sortedProviders(softwareProvidersAll), softwareProvidersAll.length, showCount = false)

        model.renderProviders shouldEqual expected
      }
    }

    "a search has been executed with found providers" should {

      "render the expected provider table with the found providers" in {

        val model = SoftwareChoicesFilterViewModel(softwareProvidersAll, Some(softwareProvidersFound), view)
        val expected = view(model.sortedProviders(softwareProvidersFound), softwareProvidersAll.length)

        model.renderProviders shouldEqual expected
      }
    }

    "a search has been executed with NO found providers" should {

      "render the expected provider table with no providers" in {

        val model = SoftwareChoicesFilterViewModel(softwareProvidersAll, Some(Seq()), view)
        val expected = view(model.sortedProviders(Seq()), softwareProvidersAll.length)

        model.renderProviders shouldEqual expected
      }
    }
  }
}
