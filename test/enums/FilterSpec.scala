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

package enums

import utils.TestUtils

class FilterSpec extends TestUtils {

  "Filter enumeration" should {
    "determine the correct Agent enum value" in {

      val actualResult = Filter.apply("AGENT")
      val expectedResult = Filter.AGENT

      actualResult shouldBe expectedResult
    }

    "determine the correct Business enum value" in {

      val actualResult = Filter.apply("Business")
      val expectedResult = Filter.BUSINESS

      actualResult shouldBe expectedResult
    }

    "determine an unknown enum value" in {

      val actualResult = Filter.apply("UNKNOWN")
      val expectedResult = Filter.UNKNOWN

      actualResult shouldBe expectedResult
    }
  }
}
