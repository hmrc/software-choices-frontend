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

package services

import _root_.utils.TestUtils
import enums.Filter._
import models.SoftwareProviderModel
import play.api.libs.json.Json

class SoftwareChoicesServiceSpec extends TestUtils {

  object TestSoftwareChoicesService extends SoftwareChoicesService {

    override protected lazy val jsonFile: String =
      """[
        |  {
        |    "name": "nameOne",
        |    "url":"urlOne",
        |    "business": true,
        |    "agent": true,
        |    "accounting": false,
        |    "spreadsheets": false,
        |    "viewReturn": false,
        |    "viewLiabilities": false,
        |    "viewPayments": false,
        |    "cognitive": false,
        |    "visual": false,
        |    "hearing": false,
        |    "motor": false
        |  },
        |  {
        |    "name":"nameTwo",
        |    "url":"urlTwo",
        |    "business": true,
        |    "agent": false,
        |    "accounting": false,
        |    "spreadsheets": false,
        |    "viewReturn": false,
        |    "viewLiabilities": false,
        |    "viewPayments": false,
        |    "cognitive": false,
        |    "visual": false,
        |    "hearing": false,
        |    "motor": false
        |  },
        |  {
        |    "name":"nameThree",
        |    "url":"urlThree",
        |    "business": true,
        |    "agent": true,
        |    "accounting": false,
        |    "spreadsheets": false,
        |    "viewReturn": false,
        |    "viewLiabilities": false,
        |    "viewPayments": false,
        |    "cognitive": false,
        |    "visual": false,
        |    "hearing": false,
        |    "motor": false
        |  }
        |]""".stripMargin
  }

  "test" in {
    TestSoftwareChoicesService.providersList shouldBe Seq(
      SoftwareProviderModel(
        "nameOne",
        "urlOne",
        List(BUSINESS, AGENT)
      ),
      SoftwareProviderModel(
        "nameTwo",
        "urlTwo",
        List(BUSINESS)
      ),
      SoftwareProviderModel(
        "nameThree",
        "urlThree",
        List(BUSINESS, AGENT)
      )
    )
  }

  "SoftwareChoicesService.searchProviders({search})" should {

    "return the correct sequence of filtered software providers" in {
      TestSoftwareChoicesService.searchProviders("t") shouldBe Seq(
        SoftwareProviderModel("nameTwo", "urlTwo", List(BUSINESS)),
        SoftwareProviderModel("nameThree", "urlThree", List(BUSINESS, AGENT))
      )
    }

    "return the empty sequence of software providers where search term doesn't match any" in {
      TestSoftwareChoicesService.searchProviders("£") shouldBe Seq.empty
    }

    "return all providers where search term matches all" in {
      TestSoftwareChoicesService.searchProviders("na") shouldBe Seq(
        SoftwareProviderModel("nameOne", "urlOne", List(BUSINESS, AGENT)),
        SoftwareProviderModel("nameTwo", "urlTwo", List(BUSINESS)),
        SoftwareProviderModel("nameThree", "urlThree", List(BUSINESS, AGENT))
      )
    }
  }


  "SoftwareChoicesService.filterProviders" when {

    "given filters it does have providers for it" should {
      "return the correct providers" in {

        val actualResult = TestSoftwareChoicesService.filterProviders(List(BUSINESS))
        val expectedResult = Seq(
          SoftwareProviderModel("nameOne", "urlOne", List(BUSINESS, AGENT)),
          SoftwareProviderModel("nameTwo", "urlTwo", List(BUSINESS)),
          SoftwareProviderModel("nameThree", "urlThree", List(BUSINESS, AGENT))
        )

        actualResult shouldBe expectedResult
      }
    }

    "given filters it does not have providers for it" should {
      "return the correct providers" in {

        val actualResult = TestSoftwareChoicesService.filterProviders(List(UNKNOWN))
        val expectedResult = Seq.empty[SoftwareProviderModel]

        actualResult shouldBe expectedResult
      }
    }

    "given filters it has one provider for it" should {
      "return the correct providers" in {

        val actualResult = TestSoftwareChoicesService.filterProviders(List(AGENT))
        val expectedResult = Seq(
          SoftwareProviderModel("nameOne", "urlOne", List(BUSINESS, AGENT)),
          SoftwareProviderModel("nameThree", "urlThree", List(BUSINESS, AGENT))
        )

        actualResult shouldBe expectedResult
      }
    }

    "given a filter and a incorrect search term" should {
      "return no providers" in {

        val actualResult = TestSoftwareChoicesService.filterProviders(List(AGENT), Some("thing"))
        val expectedResult = Seq.empty[SoftwareProviderModel]

        actualResult shouldBe expectedResult
      }
    }

    "given one filter with a correct search term" should {
      "return two providers" in {

        val actualResult = TestSoftwareChoicesService.filterProviders(List(AGENT), Some("name"))
        val expectedResult = Seq(
          SoftwareProviderModel("nameOne", "urlOne", List(BUSINESS, AGENT)),
          SoftwareProviderModel("nameThree", "urlThree", List(BUSINESS, AGENT))
        )

        actualResult shouldBe expectedResult
      }
    }

    "given two filters with a correct search term" should {
      "return one correct provider" in {

        val actualResult = TestSoftwareChoicesService.filterProviders(List(BUSINESS, AGENT), Some("nameThree"))
        val expectedResult = Seq(SoftwareProviderModel("nameThree", "urlThree", List(BUSINESS, AGENT)))

        actualResult shouldBe expectedResult
      }
    }
  }

  "SoftwareChoicesService.returnProviderJson" should {
    "return the json of a provider if found" in {
      val result = TestSoftwareChoicesService.returnProvider("nameOne")

      result shouldBe Some(SoftwareProviderModel(
        "nameOne",
        "urlOne",
        List(BUSINESS, AGENT)
      ))
    }

    "return None if provider not found" in {
      val result = TestSoftwareChoicesService.returnProvider("noName")

      result shouldBe None
    }
  }
}
