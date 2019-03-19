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

import assets.messages.{CommonMessages, FilterSearchMessages, SoftwareChoicesMessages}
import forms.{FiltersForm, SearchForm}
import models.SoftwareProviderModel
import org.jsoup.Jsoup
import play.api.http.Status
import play.api.test.FakeRequest
import services.SoftwareChoicesService
import _root_.utils.TestUtils
import play.api.test.Helpers._
import services.mocks.MockSoftwareChoicesService
import views.ViewBaseSpec


class SoftwareChoicesControllerSpec extends TestUtils with MockSoftwareChoicesService {

  object TestSoftwareChoicesController extends SoftwareChoicesController(
    mockSoftwareChoicesService,
    messagesApi,
    appConfig
  )

  val softwareProviders = Seq(
    SoftwareProviderModel("aName", "aUrl"),
    SoftwareProviderModel("anotherName", "anotherUrl"),
    SoftwareProviderModel("andAnotherName", "andAnotherUrl")
  )

  "SoftwareChoicesController.show" when {

    "the filter view is disabled" should {

      lazy val result = TestSoftwareChoicesController.show(fakeRequest)

      "return 200 (OK)" in {
        appConfig.filterViewEnabled(false)
        mockReadProviders(softwareProviders)
        status(result) shouldBe Status.OK
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }

      "render the basic search page" in {
        Jsoup.parse(bodyOf(result)).title shouldBe SoftwareChoicesMessages.title
      }
    }

    "the filter view is enabled" should {

      lazy val result = TestSoftwareChoicesController.show(fakeRequest)

      "return 200 (OK)" in {
        mockReadProviders(softwareProviders)
        status(result) shouldBe Status.OK
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }

      "render the filter search page" in {
        Jsoup.parse(bodyOf(result)).title shouldBe FilterSearchMessages.fullTitle
      }
    }
  }

  "SoftwareChoicesController.search" when {

    "the filter view is disabled" should {

      "given a valid search" should {

        lazy val result = TestSoftwareChoicesController.search(FakeRequest("POST", "/").withFormUrlEncodedBody((SearchForm.term, "A Team")))

        "return 200 (OK)" in {
          appConfig.filterViewEnabled(false)
          mockReadProviders(softwareProviders)
          setupMockSearchProviders(softwareProviders)
          status(result) shouldBe Status.OK
        }

        "return HTML" in {
          contentType(result) shouldBe Some("text/html")
          charset(result) shouldBe Some("utf-8")
        }

        "render the basic search results page" in {
          Jsoup.parse(bodyOf(result)).title shouldBe SoftwareChoicesMessages.title
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
          appConfig.filterViewEnabled(false)
          mockReadProviders(softwareProviders)
          status(result) shouldBe Status.BAD_REQUEST
        }

        "return HTML" in {
          contentType(result) shouldBe Some("text/html")
          charset(result) shouldBe Some("utf-8")
        }

        "render the basic search results page" in {
          Jsoup.parse(bodyOf(result)).title shouldBe s"${CommonMessages.error} ${SoftwareChoicesMessages.title}"
        }
      }
    }

    "the filter view is enabled" should {

      "given a valid search" should {

        lazy val result = TestSoftwareChoicesController.search(FakeRequest("POST", "/").withFormUrlEncodedBody((SearchForm.term, "A Team")))

        "return 200 (OK)" in {
          mockReadProviders(softwareProviders)
          setupMockFilterProviders(softwareProviders)
          status(result) shouldBe Status.OK
        }

        "return HTML" in {
          contentType(result) shouldBe Some("text/html")
          charset(result) shouldBe Some("utf-8")
        }

        "render the basic search results page" in {
          Jsoup.parse(bodyOf(result)).title shouldBe FilterSearchMessages.fullTitle
        }
      }


      "given a invalid search" should {

        val softwareProviders = Seq(
          SoftwareProviderModel("aName", "aUrl"),
          SoftwareProviderModel("anotherName", "anotherUrl"),
          SoftwareProviderModel("andAnotherName", "andAnotherUrl")
        )

        lazy val result = TestSoftwareChoicesController.search(FakeRequest("POST", "/").withFormUrlEncodedBody((SearchForm.term, "a" * (FiltersForm.maxLength + 1))))

        "return 200 (OK)" in {
          mockReadProviders(softwareProviders)
          status(result) shouldBe Status.BAD_REQUEST
        }

        "return HTML" in {
          contentType(result) shouldBe Some("text/html")
          charset(result) shouldBe Some("utf-8")
        }

        "render the filter search results page" in {
          Jsoup.parse(bodyOf(result)).title shouldBe s"${CommonMessages.error} ${FilterSearchMessages.fullTitle}"
        }
      }
    }

  }

  "SoftwareChoicesController.ajaxFilterSearch" when {

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
        .withFormUrlEncodedBody((SearchForm.term, "a" * (FiltersForm.maxLength + 1))))

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
}
