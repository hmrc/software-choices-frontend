/*
 * Copyright 2020 HM Revenue & Customs
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
import forms.FiltersForm


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
    val hiddenHeading = "#provider-results > h2"
    val suitableForFilterHeader = "#searchForm > div > div.column-one-third.form-group > div:nth-child(1) > div.filter-head > span"
    val agentFilter = """label[for="AGENT"]"""
    val businessFilter = """label[for="BUSINESS"]"""
    val softwareForFilterHeader = "#searchForm > div > div.column-one-third.form-group > div:nth-child(2) > div.filter-head > span"
    val vatReturnsFilter = """label[for="VIEW_RETURN"]"""
    val vatLiabilitiesFilter = """label[for="VIEW_LIABILITIES"]"""
    val vatPaymentsFilter = """label[for="VIEW_PAYMENTS"]"""
    val softwareFeaturesFilterHeader = "#searchForm > div > div.column-one-third.form-group > div:nth-child(3) > div.filter-head > span"
    val accountingFilter = """label[for="ACCOUNTING"]"""
    val spreadsheetsFilter = """label[for="SPREADSHEETS"]"""
    val priceFilterHeader = "#searchForm > div > div.column-one-third.form-group > div:nth-child(4) > div.filter-head > span"
    val freeFilter = """label[for="FREE"]"""
    val accessibilityFeaturesFilterHeader = "#searchForm > div > div.column-one-third.form-group > div:nth-child(5) > div.filter-head > span"
    val cognitiveFilter = """label[for="COGNITIVE"]"""
    val visualFilter = """label[for="VISUAL"]"""
    val hearingFilter = """label[for="HEARING"]"""
    val motorFilter = """label[for="MOTOR"]"""
    val filterResults = "#content > form > div > div.column-one-third button"
    val p = (i: Int) => s"div.grid-row > div.column-two-thirds > div.form-group > p:nth-of-type($i)"
    val accordionHeading = "details > summary > span"
    val accordionSubHeading1 = (i: Int) => s"details > div > h2:nth-child($i)"
    val accordionBullet1 = (i: Int) => s"details > div > ul > li:nth-child($i)"
    val accordionBullet2 = (i: Int) => s"details > div > ul > li:nth-child($i)"
    val accordionSubHeading2 = (i: Int) => s"details > div > h2:nth-child($i)"
    val accordionBullet3 = (i: Int) => s"details > div > ul > li:nth-child($i)"
    val accordionBullet4 = (i: Int) => s"details > div > ul > li:nth-child($i)"

  }

  "The software choices filter page" when {
    "the provider details filter is disabled" when {
      appConfig.providerDetailsEnabled(false)
      "the search does not contain errors" should {

        lazy val document = parseView(views.html.software_choices_filter(filterViewProviders, FiltersForm.form))

        s"have the correct document title" in {
          document.title shouldBe FilterSearchMessages.fullTitle
        }

        s"have a the correct page heading" in {
          document.select(Selectors.pageHeading).text() shouldBe FilterSearchMessages.title
        }

        "include correct p1" in {
          document.select(Selectors.p(1)).text shouldBe FilterSearchMessages.p1
        }

        "include correct p2" in {
          document.select(Selectors.p(2)).text shouldBe FilterSearchMessages.p2
        }

        "include correct p3" in {
          document.select(Selectors.p(3)).text shouldBe FilterSearchMessages.p3
        }

        "include accordion heading" in {
          document.select(Selectors.accordionHeading).text shouldBe FilterSearchMessages.accordionHeading
        }

        "include accordion subheading 1" in {
          document.select(Selectors.accordionSubHeading1(1)).text should include(FilterSearchMessages.accordionSubHeading1)
        }

        "include accordion bullet 1" in {
          document.select(Selectors.accordionBullet1(1)).text should include(FilterSearchMessages.accordionBullet1)
        }

        "include accordion bullet 2" in {
          document.select(Selectors.accordionBullet2(2)).text should include(FilterSearchMessages.accordionBullet2)
        }

        "include accordion subheading 2" in {
          document.select(Selectors.accordionSubHeading2(3)).text should include(FilterSearchMessages.accordionSubHeading2)
        }

        "include accordion bullet 3" in {
          document.select(Selectors.accordionBullet3(1)).text should include(FilterSearchMessages.accordionBullet3)
        }

        "include accordion bullet 4" in {
          document.select(Selectors.accordionBullet4(2)).text should include(FilterSearchMessages.accordionBullet4)
        }

        "include the search component" in {
          Option(document.select(Selectors.searchComponent)).isDefined shouldBe true
        }

        "include a hidden results heading" in {
          document.select(Selectors.hiddenHeading).text shouldBe FilterSearchMessages.hiddenHeading
        }

        "include the results count component" in {
          Option(document.select(Selectors.searchResultCount)).isDefined shouldBe true
        }

        "include the table of providers component" in {
          Option(document.select(Selectors.providerTable)).isDefined shouldBe true
        }
      }


      "the search contains errors" should {

        val errorForm = FiltersForm.form.withError("term", "AN ERROR")

        lazy val document = parseView(views.html.software_choices_filter(filterViewProviders, errorForm))

        "page title should be prefixed with Error" in {
          document.title shouldBe s"${CommonMessages.error} ${FilterSearchMessages.fullTitle}"
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
            document.select(Selectors.suitableForFilterHeader).text shouldBe FilterSearchMessages.suitableFor
          }

          "Filter Label contains Agents" in {
            document.select(Selectors.agentFilter).text shouldBe FilterSearchMessages.agents
          }

          "Filter Label contains Business" in {
            document.select(Selectors.businessFilter).text shouldBe FilterSearchMessages.businesses
          }
        }

        "contain a filter for Type of software" should {

          "Software for should contain Type of software filter header" in {
            document.select(Selectors.softwareForFilterHeader).text shouldBe FilterSearchMessages.typeOfSoftware
          }

          "Filter Label contains Accounting software" in {
            document.select(Selectors.accountingFilter).text shouldBe FilterSearchMessages.accountingSoftware
          }

          "Filter Label contains Connects to spreadsheets" in {
            document.select(Selectors.spreadsheetsFilter).text shouldBe FilterSearchMessages.spreadSheets
          }
        }

        "contain a filter for Additional Features" should {

          "Software features should contain Additional software features filter header" in {
            document.select(Selectors.softwareFeaturesFilterHeader).text shouldBe FilterSearchMessages.additionalSoftwareFeatures
          }

          "Filter Label contains Check submitted VAT Returns" in {
            document.select(Selectors.vatReturnsFilter).text shouldBe FilterSearchMessages.vatReturns
          }

          "Filter Label contains Check View VAT liabilities" in {
            document.select(Selectors.vatLiabilitiesFilter).text shouldBe FilterSearchMessages.vatLiabilities
          }

          "Filter Label contains Check View VAT payments" in {
            document.select(Selectors.vatPaymentsFilter).text shouldBe FilterSearchMessages.vatPayments
          }
        }

        "contain a filter for Price" should {

          "Price should contain Price filter header" in {
            document.select(Selectors.priceFilterHeader).text shouldBe FilterSearchMessages.price
          }

          "Filter Label contains Free version" in {
            document.select(Selectors.freeFilter).text shouldBe FilterSearchMessages.free
          }
        }

        "contain a filter for Accessibility features" should {

          "Accessibility features should contain Accessibility features filter header" in {
            document.select(Selectors.accessibilityFeaturesFilterHeader).text shouldBe FilterSearchMessages.accessibilityFeatures
          }

          "Filter Label contains COGNITIVE" in {
            document.select(Selectors.cognitiveFilter).text shouldBe FilterSearchMessages.cognitive
          }

          "Filter Label contains VISUAL" in {
            document.select(Selectors.visualFilter).text shouldBe FilterSearchMessages.visual
          }

          "Filter Label contains HEARING" in {
            document.select(Selectors.hearingFilter).text shouldBe FilterSearchMessages.hearing
          }

          "Filter Label contains MOTOR" in {
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
}
