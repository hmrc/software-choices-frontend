/*
 * Copyright 2022 HM Revenue & Customs
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
import models.SoftwareProviderModel
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.mocks.MockSoftwareChoicesService
import views.html.software_choices_software_information


class SoftwareInformationControllerSpec extends TestUtils with MockSoftwareChoicesService {

  val view = app.injector.instanceOf[software_choices_software_information]

  object TestSoftwareInformationController extends SoftwareInformationController(
    stubMessagesControllerComponents(),
    mockSoftwareChoicesService,
    view
  )(appConfig)

  val softwareProviders: Seq[SoftwareProviderModel] = Seq(
    SoftwareProviderModel("aName", "aUrl"),
    SoftwareProviderModel("anotherName", "anotherUrl"),
    SoftwareProviderModel("andAnotherName", "andAnotherUrl")
  )

  "SoftwareInformationController.softwareInformation" should {
    "return the provider json if the name is valid" in {
      mockReturnProvider(Some(softwareProviders.head))
      val result = TestSoftwareInformationController.show("aName")(FakeRequest())

      status(result) shouldBe OK
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }

    "return no content if the name is not in list" in {
      mockReturnProvider(None)
      val result = TestSoftwareInformationController.show("wrongName")(FakeRequest())

      status(result) shouldBe NO_CONTENT
    }
  }
}