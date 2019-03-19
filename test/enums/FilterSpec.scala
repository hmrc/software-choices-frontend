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

import _root_.utils.TestUtils
import Filter._

class FilterSpec extends TestUtils {

  "Filter enumeration" should {

    "determine the correct Agent enum value" in {
      Filter("AGENT") shouldBe AGENT
    }

    "determine the correct Business enum value" in {
      Filter("Business") shouldBe BUSINESS
    }

    "determine the correct VIEW_RETURN enum value" in {
      Filter("VIEW_RETURN") shouldBe VIEW_RETURN
    }

    "determine the correct VIEW_PAYMENTS enum value" in {
      Filter("VIEW_PAYMENTS") shouldBe VIEW_PAYMENTS
    }

    "determine the correct VIEW_LIABILITIES enum value" in {
      Filter("VIEW_LIABILITIES") shouldBe VIEW_LIABILITIES
    }

    "determine the correct ACCOUNTING enum value" in {
      Filter("ACCOUNTING") shouldBe ACCOUNTING
    }

    "determine the correct SPREADSHEETS enum value" in {
      Filter("SPREADSHEETS") shouldBe SPREADSHEETS
    }

    "determine the correct COGNITIVE enum value" in {
      Filter("COGNITIVE") shouldBe COGNITIVE
    }

    "determine the correct HEARING enum value" in {
      Filter("HEARING") shouldBe HEARING
    }

    "determine the correct MOTOR enum value" in {
      Filter("MOTOR") shouldBe MOTOR
    }

    "determine the correct VISUAL enum value" in {
      Filter("VISUAL") shouldBe VISUAL
    }

    "determine an unknown enum value" in {
      Filter("UNKNOWN") shouldBe UNKNOWN
    }
  }
}
