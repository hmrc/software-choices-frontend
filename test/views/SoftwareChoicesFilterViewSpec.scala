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

package views

import assets.messages.{CommonMessages, FilterSearchMessages}
import assets.testContants.SoftwareProvidersTestConstants
import forms.{FiltersForm, SearchForm}


class SoftwareChoicesFilterViewSpec extends ViewBaseSpec with SoftwareProvidersTestConstants {

  object Selectors {
    val pageHeading = "h1"
    val searchComponent = ".search-field"
    val providerTable = "#provider-table"
    val searchResultCount = "#search-result-count"
    val errorSummaryDisplay = "#error-summary-display"
    val termFieldError = "#term-error-summary"
    val formFieldError = ".form-field--error"
    val fieldErrorMessage = ".error-message"
    val filterHeader = (i: Int) => s"#content > form > div > div.column-one-third > div:nth-child($i) > div.filter-head > span"
    val agentFilter = """label[for="AGENT"]"""
    val businessFilter = """label[for="BUSINESS"]"""
    val vatReturnsFilter ="""label[for="VIEW_RETURN"]"""
    val vatLiabilitiesFilter ="""label[for="VIEW_LIABILITIES"]"""
    val vatPaymentsFilter ="""label[for="VIEW_PAYMENTS"]"""
    val accountingFilter ="""label[for="ACCOUNTING"]"""
    val spreadsheetsFilter ="""label[for="SPREADSHEETS"]"""
    val cognitiveFilter ="""label[for="COGNITIVE"]"""
    val visualFilter ="""label[for="VISUAL"]"""
    val hearingFilter ="""label[for="HEARING"]"""
    val motorFilter ="""label[for="MOTOR"]"""
    val filterResults ="#content > form > div > div.column-one-third button"
    val p = (i: Int) => s"div.grid-row > div.column-two-thirds > p:nth-of-type($i)"
  }

  "The software choices filter page" when {

    "the search does not contain errors" should {

      lazy val document = parseView(views.html.software_choices_filter(filterViewProviders, FiltersForm.form))

      s"have the correct document title" in {
        document.title shouldBe FilterSearchMessages.title
      }

      s"have a the correct page heading" in {
        document.select(Selectors.pageHeading).text() shouldBe FilterSearchMessages.title
      }

      "include an introductory paragraph" in {
        document.select(Selectors.p(1)).text shouldBe FilterSearchMessages.p1
      }

      "include a disclaimer" in {
        document.select(Selectors.p(2)).text shouldBe FilterSearchMessages.p2
      }

      "include the search component" in {
        Option(document.select(Selectors.searchComponent)).isDefined shouldBe true
      }

      "include the results count component" in {
        Option(document.select(Selectors.searchResultCount)).isDefined shouldBe true
      }

      "include the table of providers component" in {
        Option(document.select(Selectors.providerTable)).isDefined shouldBe true
      }
    }


    "the search contains errors" should {

      val errorForm = FiltersForm.form.withError("term","AN ERROR")

      lazy val document = parseView(views.html.software_choices_filter(filterViewProviders, errorForm))

      "page title should be prefixed with Error" in {
        document.title shouldBe s"${CommonMessages.error} ${FilterSearchMessages.title}"
      }

      "show the error summary" in {
        document.select(Selectors.errorSummaryDisplay).isEmpty shouldBe false
      }

      "have an error message with link to the term field" in {
        val summaryError = document.select(Selectors.termFieldError)
        summaryError.text shouldBe errorForm.errors.head.message
        summaryError.attr("href") shouldBe "#term"
        summaryError.attr("data-focuses") shouldBe "term"
      }

      "highlight the errored field" in {
        document.select(Selectors.formFieldError).isEmpty shouldBe false
        document.select(Selectors.fieldErrorMessage).text shouldBe "AN ERROR"
      }
    }


    "Filter search" should {

      lazy val document = parseView(views.html.software_choices_filter(filterViewProviders, FiltersForm.form))

      "contain a filter for Agents and Business which" should {

        "Suitable for should contain Agents and Business filter header" in {
          document.select(Selectors.filterHeader(1)).text shouldBe FilterSearchMessages.suitableFor
        }

        "Filter Header contains Agents" in {
          document.select(Selectors.agentFilter).text shouldBe FilterSearchMessages.agents
        }

        "Filter Header contains Business" in {
          document.select(Selectors.businessFilter).text shouldBe FilterSearchMessages.businesses
        }
      }

      "contain a filter for Additional Features" should {

        "Suitable for should contain Additional software features filter header" in {
          document.select(Selectors.filterHeader(2)).text shouldBe FilterSearchMessages.additionalSoftwareFeatures
        }

        "Filter Header contains Check submitted VAT Returns" in {
          document.select(Selectors.vatReturnsFilter).text shouldBe FilterSearchMessages.vatReturns
        }

        "Filter Header contains Check View VAT liabilities" in {
          document.select(Selectors.vatLiabilitiesFilter).text shouldBe FilterSearchMessages.vatLiabilities
        }

        "Filter Header contains Check View VAT payments" in {
          document.select(Selectors.vatPaymentsFilter).text shouldBe FilterSearchMessages.vatPayments
        }
      }

      "contain a filter for Type of software" should {

        "Suitable for should contain Type of software filter header" in {
          document.select(Selectors.filterHeader(3)).text shouldBe FilterSearchMessages.typeOfSoftware
        }

        "Filter Header contains Accounting software" in {
          document.select(Selectors.accountingFilter).text shouldBe FilterSearchMessages.accountingSoftware
        }

        "Filter Header contains Connects to spreadsheets" in {
          document.select(Selectors.spreadsheetsFilter).text shouldBe FilterSearchMessages.spreadSheets
        }
      }

      "contain a filter for Accessibility" should {

        "Suitable for should contain Accessibility filter header" in {
          document.select(Selectors.filterHeader(4)).text shouldBe FilterSearchMessages.accessibility
        }

        "Filter Header contains COGNITIVE" in {
          document.select(Selectors.cognitiveFilter).text shouldBe FilterSearchMessages.cognitive
        }

        "Filter Header contains VISUAL" in {
          document.select(Selectors.visualFilter).text shouldBe FilterSearchMessages.visual
        }

        "Filter Header contains HEARING" in {
          document.select(Selectors.hearingFilter).text shouldBe FilterSearchMessages.hearing
        }

        "Filter Header contains MOTOR" in {
          document.select(Selectors.motorFilter).text shouldBe FilterSearchMessages.motor
        }
      }

      "contain a Filter results button which" should {

        "have the correct message for the button" in {
          document.select(Selectors.filterResults).text shouldBe FilterSearchMessages.filterResults
        }

        "have a form-group class" in {
          document.select(Selectors.filterResults).hasClass("form-group") shouldBe true
        }
      }
    }
  }
}
