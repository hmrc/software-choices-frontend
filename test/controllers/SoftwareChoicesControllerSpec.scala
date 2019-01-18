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

import forms.SearchForm
import models.SoftwareProviderModel
import play.api.http.Status
import play.api.test.FakeRequest
import services.SoftwareChoicesService
import utils.TestUtils
import play.api.test.Helpers._
import services.mocks.MockSoftwareChoicesService


class SoftwareChoicesControllerSpec extends MockSoftwareChoicesService {

  object TestSoftwareChoicesController extends SoftwareChoicesController(
    mockSoftwareChoicesService,
    messagesApi,
    appConfig
  )

  "SoftwareChoicesController.show" should {

    lazy val result = TestSoftwareChoicesController.show(fakeRequest)

    "return 200 (OK)" in {
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }

  "SoftwareChoicesController.search" when {

    "given a valid search" should {

      val softwareProviders = Seq(
      SoftwareProviderModel("aName", "aUrl"),
      SoftwareProviderModel("anotherName", "anotherUrl"),
      SoftwareProviderModel("andAnotherName", "andAnotherUrl")
      )

      lazy val result = TestSoftwareChoicesController.search(FakeRequest("POST", "/").withFormUrlEncodedBody((SearchForm.term, "A Team")))

      "return 200 (OK)" in {
        setupMockSearchProviders(softwareProviders)
        status(result) shouldBe Status.OK
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }


    "given a invalid search" should {

      val softwareProviders = Seq(
        SoftwareProviderModel("aName", "aUrl"),
        SoftwareProviderModel("anotherName", "anotherUrl"),
        SoftwareProviderModel("andAnotherName", "andAnotherUrl")
      )

      lazy val result = TestSoftwareChoicesController.search(FakeRequest("POST", "/").withFormUrlEncodedBody((SearchForm.term, "")))

      "return 200 (OK)" in {
        status(result) shouldBe Status.BAD_REQUEST
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }
  }
}
