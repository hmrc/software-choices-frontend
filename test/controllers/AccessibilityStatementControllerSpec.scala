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

package controllers

import _root_.utils.TestUtils
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.Future

class AccessibilityStatementControllerSpec extends TestUtils {

  object TestAccessibilityStatementController extends AccessibilityStatementController(
    messagesApi,
    appConfig
  )

  "show" must {
    s"return $OK" in {
      val result: Future[Result] = TestAccessibilityStatementController.show("testPage")(FakeRequest())
      status(result) shouldBe OK
      contentType(result) shouldBe Some("text/html")
    }
  }

}
