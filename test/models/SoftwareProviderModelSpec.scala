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

import enums.Filter
import utils.TestUtils

class SoftwareProviderModelSpec extends TestUtils {

  "SoftwareProviderModel" should {
    "parse a file line by name, url and filter" in  {

      val actualResult = SoftwareProviderModel("10 Minute Accounts|https://10minuteaccounts.com*AGENT|BUSINESS")
      val expectedResult = SoftwareProviderModel(
        "10 Minute Accounts",
        "https://10minuteaccounts.com",
        List(Filter.AGENT, Filter.BUSINESS))

      actualResult shouldBe expectedResult
    }
  }
}
