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

package services
import enums.Filter
import models.SoftwareProviderModel
import utils.TestUtils

class SoftwareChoicesServiceSpec extends TestUtils {

  object TestSoftwareChoicesService extends SoftwareChoicesService {
    override lazy val providersList: Seq[String] = Seq(
      "nameOne|urlOne*AGENT|BUSINESS",
      "nameTwo|urlTwo*BUSINESS"
    )

    val AGENT = Filter.AGENT
    val BUSINESS = Filter.BUSINESS
  }

  "SoftwareChoicesService.readProviders" should {

    "return the correct sequence of software providers" in {
      TestSoftwareChoicesService.readProviders shouldBe Seq(
        SoftwareProviderModel("nameOne","urlOne", List(TestSoftwareChoicesService.AGENT, TestSoftwareChoicesService.BUSINESS)),
        SoftwareProviderModel("nameTwo","urlTwo", List(TestSoftwareChoicesService.BUSINESS))
      )
    }
  }

  "SoftwareChoicesService.searchProviders({search})" should {

    "return the correct sequence of filtered software providers" in {
      TestSoftwareChoicesService.searchProviders("t") shouldBe Seq(
        SoftwareProviderModel("nameTwo","urlTwo", List(TestSoftwareChoicesService.BUSINESS))
      )
    }

    "return the empty sequence of software providers where search term doesn't match any" in {
      TestSoftwareChoicesService.searchProviders("£") shouldBe Seq.empty
    }

    "return all providers where search term matches all" in {
      TestSoftwareChoicesService.searchProviders("na") shouldBe Seq(
        SoftwareProviderModel("nameOne","urlOne", List(TestSoftwareChoicesService.AGENT, TestSoftwareChoicesService.BUSINESS)),
        SoftwareProviderModel("nameTwo","urlTwo", List(TestSoftwareChoicesService.BUSINESS))
      )
    }
  }
}
