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

package models

import _root_.utils.TestUtils
import views.html.templates.{found_provider_template, provider_template}

class SoftwareChoicesViewModelSpec extends TestUtils {

  val upperA = SoftwareProviderModel("Abc", "url")
  val lowerA = SoftwareProviderModel("abc", "url")
  val upperZ = SoftwareProviderModel("Zyx", "url")
  val lowerZ = SoftwareProviderModel("zyx", "url")
  val hashSymbol = SoftwareProviderModel("#", "url")
  val atSymbol = SoftwareProviderModel("@", "url")
  val number = SoftwareProviderModel("1", "url")

  val softwareProvidersAll = Seq(
    upperA,
    lowerA,
    upperZ,
    lowerZ,
    hashSymbol,
    atSymbol,
    number
  )

  val softwareProvidersFound = Seq(
    upperA,
    lowerA,
    hashSymbol
  )

  "SoftwareChoicesViewModel.renderAllProviders" when {

    "given a variety software choices" should {
      "group the providers and sort them" in {
        SoftwareChoicesViewModel(softwareProvidersAll).renderAllProviders shouldEqual Seq(
          provider_template("#", Seq(hashSymbol, number, atSymbol)),
          provider_template("A", Seq(upperA, lowerA)),
          provider_template("Z", Seq(upperZ, lowerZ))
        )
      }
    }

    "given a variety software choices and found choices" should {
      "group the providers and sort them" in {
        SoftwareChoicesViewModel(softwareProvidersAll, softwareProvidersFound).renderAllProviders shouldEqual Seq(
          provider_template("#", Seq(hashSymbol, number, atSymbol)),
          provider_template("A", Seq(upperA, lowerA)),
          provider_template("Z", Seq(upperZ, lowerZ))
        )
      }
    }

    "given a no software choices" should {
      "not show any providers" in {
        SoftwareChoicesViewModel(Seq.empty).renderAllProviders shouldBe Seq.empty
      }
    }
  }

  "SoftwareChoicesViewModel.renderFoundProviders" when {

    "given a variety software choices" should {
      "sort the providers" in {
        SoftwareChoicesViewModel(softwareProvidersAll,softwareProvidersFound).renderFoundProviders shouldEqual found_provider_template(Seq(hashSymbol, upperA, lowerA))
      }
    }

    "given a no software choices" should {
      "not show any providers" in {
        SoftwareChoicesViewModel(Seq.empty).renderFoundProviders shouldBe found_provider_template(Seq.empty)
      }
    }
  }

  "SoftwareChoicesViewModel.sortProviders" should {
    "return a sorted Seq of providers" in {
      SoftwareChoicesViewModel.sortProviders(softwareProvidersFound) shouldBe Seq(hashSymbol, upperA, lowerA)
    }
  }
}
