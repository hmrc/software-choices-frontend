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

import utils.TestUtils

class SoftwareChoicesViewModelSpec extends TestUtils {

  val upperA = SoftwareProviderModel("Abc", "url")
  val lowerA = SoftwareProviderModel("abc", "url")
  val upperZ = SoftwareProviderModel("Zyx", "url")
  val lowerZ = SoftwareProviderModel("zyx", "url")
  val hashSymbol = SoftwareProviderModel("#", "url")
  val atSymbol = SoftwareProviderModel("@", "url")
  val number = SoftwareProviderModel("1", "url")

  val softwareChoicesViewModelAll = SoftwareChoicesViewModel(
    Seq(
      upperA,
      lowerA,
      upperZ,
      lowerZ,
      hashSymbol,
      atSymbol,
      number
    )
  )

  val softwareChoicesViewModelEmpty = SoftwareChoicesViewModel(Seq.empty)

  "SoftwareChoicesViewModel.getroviders" when {

    "given a variety software choices" should {
      "group the providers and sort them" in {
        softwareChoicesViewModelAll.sortedProviders shouldEqual Seq(
          "#" -> Seq(hashSymbol, number, atSymbol),
          "A" -> Seq(upperA, lowerA),
          "Z" -> Seq(upperZ, lowerZ)
        )
      }
    }

    "given a no software choices" should {
      "return empty map" in {
        softwareChoicesViewModelEmpty.sortedProviders shouldBe Seq.empty
      }
    }
  }
}
