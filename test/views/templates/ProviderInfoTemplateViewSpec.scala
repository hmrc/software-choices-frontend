/*
 * Copyright 2024 HM Revenue & Customs
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

package views.templates

import models.SoftwareProviderModel
import views.ViewBaseSpec
import enums.Filter._
import messages.{ProviderInfoTemplateMessages => testMessages}
import views.html.templates.provider_info_template

class ProviderInfoTemplateViewSpec extends ViewBaseSpec {

  val view = app.injector.instanceOf[provider_info_template]

  val testName = "testName"
  val testUrl = "testUrl"

  "ProviderInfoTemplateView" should {
    "return html with all fields when all filters are present" in {
      val providerFull = SoftwareProviderModel(
        testName,
        testUrl,
        List(
          BUSINESS,
          AGENT,
          VIEW_RETURN,
          VIEW_LIABILITIES,
          VIEW_PAYMENTS,
          ACCOUNTING,
          SPREADSHEETS,
          COGNITIVE,
          VISUAL,
          HEARING,
          MOTOR,
          FREE
        )
      )

      lazy val document = parseView(view(providerFull))

      document.select("p").get(0).text() shouldBe testMessages.forBoth(testName)

      document.select("p").get(1).text() shouldBe testMessages.softwareType
      document.select("ul > li").get(0).text() shouldBe testMessages.records
      document.select("ul > li").get(1).text() shouldBe testMessages.bridging

      document.select("p").get(2).text() shouldBe testMessages.softwareFeatures
      document.select("ul > li").get(2).text() shouldBe testMessages.submitVat
      document.select("ul > li").get(3).text() shouldBe testMessages.viewReturns
      document.select("ul > li").get(4).text() shouldBe testMessages.viewLiabilities
      document.select("ul > li").get(5).text() shouldBe testMessages.viewPayments

      document.select("p").get(3).text() shouldBe testMessages.accessibilityFeatures(
        List(
          messages("softwareChoices.filter.cognitive"),
          messages("softwareChoices.filter.visual"),
          messages("softwareChoices.filter.hearing"),
          messages("softwareChoices.filter.motor")
          ).map(_.toLowerCase())
      )
      document.select("p").get(4).text() shouldBe testMessages.free
      document.select("p").get(5).text() shouldBe testMessages.visit(testName)

      document.select("a").get(0).attr("href") shouldBe testUrl
    }

    "return the correct html when no filters are given" in {
      val provider = SoftwareProviderModel(
        testName,
        testUrl,
        List()
      )

      lazy val document = parseView(view(provider))

      document.select("p").get(0).text() shouldBe testMessages.softwareFeatures
      document.select("ul > li").get(0).text() shouldBe testMessages.submitVat

      document.select("p").get(1).text() shouldBe testMessages.visit(testName)

      document.select("a").get(0).attr("href") shouldBe testUrl
    }

    "return the correct html when another set of filters is given" in {
      val provider = SoftwareProviderModel(
        testName,
        testUrl,
        List(
          BUSINESS,
          ACCOUNTING,
          COGNITIVE
        )
      )

      lazy val document = parseView(view(provider))

      document.select("p").get(0).text() shouldBe testMessages.forBusinesses(testName)

      document.select("p").get(1).text() shouldBe testMessages.softwareType
      document.select("ul > li").get(0).text() shouldBe testMessages.records

      document.select("p").get(2).text() shouldBe testMessages.softwareFeatures
      document.select("ul > li").get(1).text() shouldBe testMessages.submitVat

      document.select("p").get(3).text() shouldBe testMessages.accessibilityFeature(messages("softwareChoices.filter.cognitive").toLowerCase())

      document.select("p").get(4).text() shouldBe testMessages.visit(testName)

      document.select("a").get(0).attr("href") shouldBe testUrl
    }

    "return the correct html when an alternative set of filters is given" in {
      val provider = SoftwareProviderModel(
        testName,
        testUrl,
        List(
          AGENT,
          SPREADSHEETS,
          COGNITIVE,
          HEARING
        )
      )

      lazy val document = parseView(view(provider))

      document.select("p").get(0).text() shouldBe testMessages.forAgents(testName)

      document.select("p").get(1).text() shouldBe testMessages.softwareType
      document.select("ul > li").get(0).text() shouldBe testMessages.bridging

      document.select("p").get(2).text() shouldBe testMessages.softwareFeatures
      document.select("ul > li").get(1).text() shouldBe testMessages.submitVat

      document.select("p").get(3).text() shouldBe testMessages.accessibilityFeatures(
        List(messages("softwareChoices.filter.cognitive"), messages("softwareChoices.filter.hearing")).map(_.toLowerCase())
      )
      document.select("p").get(4).text() shouldBe testMessages.visit(testName)

      document.select("a").get(0).attr("href") shouldBe testUrl
    }
  }
}
