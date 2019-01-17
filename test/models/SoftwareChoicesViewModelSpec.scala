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

  val softwareChoicesViewModelLetters = SoftwareChoicesViewModel(
    Seq(
      upperA,
      lowerA,
      upperZ,
      lowerZ
    )
  )

  val softwareChoicesViewModelSymbols = SoftwareChoicesViewModel(
    Seq(
      hashSymbol,
      atSymbol,
      number
    )
  )

  "SoftwareChoicesViewModel.getProviders" when {

    "given a variety software choices and 'a'" should {
      "only return the correct 2 providers" in {
        softwareChoicesViewModelAll.getProviders(Some("a")) shouldBe Seq(upperA, lowerA)
      }
    }

    "given a variety software choices and 'A'" should {
      "only return the correct 2 providers" in {
        softwareChoicesViewModelAll.getProviders(Some("A")) shouldBe Seq(upperA, lowerA)
      }
    }

    "given a variety software choices and 'z'" should {
      "only return the correct 2 providers" in {
        softwareChoicesViewModelAll.getProviders(Some("z")) shouldBe Seq(upperZ, lowerZ)
      }
    }

    "given a variety software choices and 'Z'" should {
      "only return the correct 2 providers" in {
        softwareChoicesViewModelAll.getProviders(Some("Z")) shouldBe Seq(upperZ, lowerZ)
      }
    }

    "given only software choices starting with a number or symbol and any letter" should {
      "not return any providers" in {
        softwareChoicesViewModelSymbols.getProviders(Some("b")) shouldBe Seq.empty[SoftwareProviderModel]
      }
    }

    "given a variety software choices and no character" should {
      "only return the correct 3 providers" in {
        softwareChoicesViewModelAll.getProviders(None) shouldBe Seq(hashSymbol, atSymbol, number)
      }
    }

    "given None and only software choices starting with a letter" should {
      "not return any providers" in {
        softwareChoicesViewModelLetters.getProviders(None) shouldBe Seq.empty[SoftwareProviderModel]
      }
    }
  }
}
