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

import enums.Filter._
import utils.TestUtils

class SoftwareProviderModelSpec extends TestUtils {

  "SoftwareProviderModel" should {

    "if all filters are provided" in  {
      val actualResult =
        SoftwareProviderModel("providerA|providerAUrl|x|x|x|x|x|x|x|fully|fully|fully|fully")
      val expectedResult =
        SoftwareProviderModel("providerA", "providerAUrl", List(
          BUSINESS, AGENT, ACCOUNTING, SPREADSHEETS, VIEW_RETURN, VIEW_LIABILITIES, VIEW_PAYMENTS, COGNITIVE, VISUAL, HEARING, MOTOR)
        )

      actualResult shouldBe expectedResult
    }

    "if some filters are provided" in  {

      val actualResult = SoftwareProviderModel("providerB|providerBUrl||x||x|x|||fully||fully|")
      val expectedResult = SoftwareProviderModel("providerB", "providerBUrl", List(AGENT, SPREADSHEETS, VIEW_RETURN, COGNITIVE, HEARING))

      actualResult shouldBe expectedResult
    }

    "if no filters are provided" in  {

      val actualResult =  SoftwareProviderModel("providerC|providerCUrl|||||||||||")
      val expectedResult =  SoftwareProviderModel("providerC", "providerCUrl", List())

      actualResult shouldBe expectedResult
    }
  }
}
