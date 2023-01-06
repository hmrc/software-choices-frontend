/*
 * Copyright 2023 HM Revenue & Customs
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
import assets.messages.FilterSearchMessages
import forms.FiltersForm
import models.SoftwareProviderModel
import org.jsoup.Jsoup
import play.api.http.Status
import play.api.mvc.{Cookie, MessagesControllerComponents}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.mocks.MockSoftwareChoicesService
import views.html.software_choices_filter
import views.html.templates.{provider_info_template, provider_table_template}

class SoftwareChoicesControllerSpec extends TestUtils with MockSoftwareChoicesService {

  val view: software_choices_filter = app.injector.instanceOf[software_choices_filter]
  val viewProvider: provider_table_template = app.injector.instanceOf[provider_table_template]
  val viewInfo: provider_info_template = app.injector.instanceOf[provider_info_template]
  val mcc: MessagesControllerComponents = app.injector.instanceOf[MessagesControllerComponents]


  object TestSoftwareChoicesController extends SoftwareChoicesController(
    mockSoftwareChoicesService,
    mcc,
    view,
    viewProvider,
    viewInfo
  )(appConfig)

  val softwareProviders: Seq[SoftwareProviderModel] = Seq(
    SoftwareProviderModel("aName", "aUrl"),
    SoftwareProviderModel("anotherName", "anotherUrl"),
    SoftwareProviderModel("andAnotherName", "andAnotherUrl")
  )

  def playLangCookie(welsh: Boolean = false): Cookie = Cookie("PLAY_LANG", if (welsh) "cy" else "en")

  "SoftwareChoicesController.show" should {

    lazy val result = TestSoftwareChoicesController.show(fakeRequest)

    "return 200 (OK)" in {
      mockReadProviders(softwareProviders)
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }

  "the Welsh feature is enabled" when {
    "the welsh language cookie is set to Welsh" should {
      "show the welsh language toggle" in {
        appConfig.welshEnabled(true)
        val request = fakeRequest.withCookies(playLangCookie(welsh = true))
        lazy val result = TestSoftwareChoicesController.show(request)
        val doc = Jsoup.parse(contentAsString(result))

        status(result) shouldBe Status.OK
        doc.select(".hmrc-language-select").isEmpty shouldBe false
        doc.select("h1").text() shouldBe FilterSearchMessages.welshTitle
      }
    }
    "the welsh language cookie is set to english" should {
      "use english content" in {
        appConfig.welshEnabled(true)
        val request = fakeRequest.withCookies(playLangCookie())
        lazy val result = TestSoftwareChoicesController.show(request)
        val doc = Jsoup.parse(contentAsString(result))

        status(result) shouldBe Status.OK
        doc.select(".hmrc-language-select").isEmpty shouldBe false
        doc.select("h1").text() shouldBe FilterSearchMessages.title
      }
    }
  }
  "the welsh language feature is disabled" when {
    "the welsh language cookie is set to welsh" should {
      "not show the language toggle and use english content" in {
        appConfig.welshEnabled(false)
        val request = fakeRequest.withCookies(playLangCookie(welsh = true))
        lazy val result = TestSoftwareChoicesController.show(request)
        val doc = Jsoup.parse(contentAsString(result))

        status(result) shouldBe Status.OK
        doc.select(".hmrc-language-select").isEmpty shouldBe false
        doc.select("h1").text() shouldBe FilterSearchMessages.title
      }
    }
  }

  "SoftwareChoicesController.search" when {
    appConfig.providerDetailsEnabled(false)

    "given a valid search" should {

      lazy val result = TestSoftwareChoicesController.search(FakeRequest("POST", "/").withFormUrlEncodedBody((FiltersForm.term, "A Team")))

      "return 200 (OK)" in {
        mockReadProviders(softwareProviders)
        setupMockFilterProviders(softwareProviders)
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

      lazy val result =
        TestSoftwareChoicesController.search(FakeRequest("POST", "/").withFormUrlEncodedBody((FiltersForm.term, "a" * (FiltersForm.maxLength + 1))))

      "return 200 (OK)" in {
        mockReadProviders(softwareProviders)
        status(result) shouldBe Status.BAD_REQUEST
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }

    }

  }

  "SoftwareChoicesController.ajaxFilterSearch" when {
    appConfig.providerDetailsEnabled(false)

    "the search is valid" should {

      lazy val result = TestSoftwareChoicesController.ajaxFilterSearch(FakeRequest("POST", "/"))

      "return 200 (OK)" in {
        mockReadProviders(softwareProviders)
        setupMockFilterProviders(softwareProviders)
        status(result) shouldBe Status.OK
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }

    "the search is invalid" should {

      lazy val result = TestSoftwareChoicesController.ajaxFilterSearch(FakeRequest("POST", "/")
        .withFormUrlEncodedBody((FiltersForm.term, "a" * (FiltersForm.maxLength + 1))))

      "return 400 (BAD_REQUEST)" in {
        mockReadProviders(softwareProviders)
        status(result) shouldBe Status.BAD_REQUEST
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }
  }

  "SoftwareChoicesController.ajaxProvider" should {
    "return the provider json if the name is valid" in {
      mockReturnProvider(Some(softwareProviders.head))
      val result = TestSoftwareChoicesController.ajaxProvider("aName")(FakeRequest())

      status(result) shouldBe OK
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }

    "return no content if the name is not in list" in {
      mockReturnProvider(None)
      val result = TestSoftwareChoicesController.ajaxProvider("wrongName")(FakeRequest())

      status(result) shouldBe NO_CONTENT
    }
  }
}
