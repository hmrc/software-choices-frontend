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

import utils.TestUtils

import scala.io.Source

class SoftwareProviderSpec extends TestUtils {

  "The Software Providers list" should {

    "have the correct list of providers" in {

      val actualProviders: Seq[String] = {
        val stream = getClass.getResourceAsStream("/softwareProviders")
        Source.fromInputStream(stream).getLines.toSeq
      }

      val expectedProviders: Seq[String] = Source.fromFile("test/resources/TestSoftwareProviders").getLines.toSeq

      for (i <- expectedProviders.indices) {
        actualProviders(i) shouldBe expectedProviders(i)
      }
    }
  }
}
