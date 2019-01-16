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

import models.{SoftwareChoicesViewModel, SoftwareProviderModel}
import org.jsoup.Jsoup
import utils.TestUtils

class SoftwareChoicesViewSpec extends TestUtils {

  object Selectors {
    val pageHeading = "#content h1"
    val searchText = "#content > article > div.form-group > p"
    val searchTextLink = "#content > article > div.form-group > p > a"
    val indentTextOne = "#content > article > div.form-group > div.panel.panel-border-wide > p:nth-child(1)"
    val indentTextTwo = "#content > article > div.form-group > div.panel.panel-border-wide > p:nth-child(2)"
    val clearSearchLink = "#content > article > div.form-group > a"
    val showAllLink = "#content > article > div:nth-child(3) > details > summary > span"
    val letterHeaderSelector: Int => String = header => s"#content > article > div:nth-child(3) > details > h2:nth-child($header)"
    val providerSelector: (Int, Int)=> String = (section, provider) =>
      s"#content > article > div:nth-child(3) > details > ul:nth-child($section) > li:nth-child($provider) > a"
    val firstProviderSelector: Int => String = section => providerSelector(section, 1)
  }

  "The software choices search page" when {

    "given multiple providers for a letter" should {

      val softwareProviders = SoftwareChoicesViewModel(Seq(
        SoftwareProviderModel("aName","aUrl"),
        SoftwareProviderModel("anotherName","anotherUrl"),
        SoftwareProviderModel("andAnotherName","andAnotherUrl")
      ))

      lazy val view = views.html.software_choices(softwareProviders)
      lazy val document = Jsoup.parse(view.body)

      s"have the correct document title" in {
        document.title shouldBe "Software that works with Making Tax Digital for VAT"
      }

      s"have a the correct page heading" in {
        document.select(Selectors.pageHeading).text() shouldBe "Software that works with Making Tax Digital for VAT"
      }

      s"have a the correct search text with the correct link" in {
        document.select(Selectors.searchText).text() shouldBe "Search for software that is connected to Making Tax Digital for VAT. You must also sign up to use this service."
        document.select(Selectors.searchTextLink).attr("href") shouldBe "#"
      }

      s"have a the correct indented text" in {
        document.select(Selectors.indentTextOne).text() shouldBe "HMRC does not recommend any one software package. In case of issues with software you will need to contact your software company directly."
        document.select(Selectors.indentTextTwo).text() shouldBe "All links to software packages take you to external websites."
      }

      s"have a clear search link" in {
        document.select(Selectors.clearSearchLink).text() shouldBe "Clear search"
        document.select(Selectors.clearSearchLink).attr("href") shouldBe "#"
      }

      s"have a show all link" in {
        document.select(Selectors.showAllLink).text() shouldBe "Show all software providers"
      }

      "have the correct section header and a single provider for # section" in {
        document.select(Selectors.letterHeaderSelector(2)).text() shouldBe "A"
        document.select(Selectors.firstProviderSelector(3)).text() shouldBe "aName"
        document.select(Selectors.firstProviderSelector(3)).attr("href") shouldBe "aUrl"
        document.select(Selectors.providerSelector(3,2)).text() shouldBe "anotherName"
        document.select(Selectors.providerSelector(3,2)).attr("href") shouldBe "anotherUrl"
        document.select(Selectors.providerSelector(3,3)).text() shouldBe "andAnotherName"
        document.select(Selectors.providerSelector(3,3)).attr("href") shouldBe "andAnotherUrl"
      }
    }

    "given a provider for every letter" should {

      val softwareProviders = SoftwareChoicesViewModel(Seq(
        SoftwareProviderModel("aName","aUrl"),
        SoftwareProviderModel("bName","bUrl"),
        SoftwareProviderModel("cName","cUrl"),
        SoftwareProviderModel("dName","dUrl"),
        SoftwareProviderModel("eName","eUrl"),
        SoftwareProviderModel("fName","fUrl"),
        SoftwareProviderModel("gName","gUrl"),
        SoftwareProviderModel("hName","hUrl"),
        SoftwareProviderModel("iName","iUrl"),
        SoftwareProviderModel("jName","jUrl"),
        SoftwareProviderModel("kName","kUrl"),
        SoftwareProviderModel("lName","lUrl"),
        SoftwareProviderModel("mName","mUrl"),
        SoftwareProviderModel("nName","nUrl"),
        SoftwareProviderModel("oName","oUrl"),
        SoftwareProviderModel("pName","pUrl"),
        SoftwareProviderModel("qName","qUrl"),
        SoftwareProviderModel("rName","rUrl"),
        SoftwareProviderModel("sName","sUrl"),
        SoftwareProviderModel("tName","tUrl"),
        SoftwareProviderModel("uName","uUrl"),
        SoftwareProviderModel("vName","vUrl"),
        SoftwareProviderModel("wName","wUrl"),
        SoftwareProviderModel("xName","xUrl"),
        SoftwareProviderModel("yName","yUrl"),
        SoftwareProviderModel("zName","zUrl"),
        SoftwareProviderModel("#Name","#Url")
      ))

      lazy val view = views.html.software_choices(softwareProviders)
      lazy val document = Jsoup.parse(view.body)

      "have the correct section header and a single provider for # section" in {
        document.select(Selectors.letterHeaderSelector(2)).text() shouldBe "#"
        document.select(Selectors.firstProviderSelector(3)).text() shouldBe "#Name"
        document.select(Selectors.firstProviderSelector(3)).attr("href") shouldBe "#Url"
      }

      "have the correct section header and a single provider for A section" in {
        document.select(Selectors.letterHeaderSelector(4)).text() shouldBe "A"
        document.select(Selectors.firstProviderSelector(5)).text() shouldBe "aName"
        document.select(Selectors.firstProviderSelector(5)).attr("href") shouldBe "aUrl"
      }

      "have the correct section header and a single provider for B section" in {
        document.select(Selectors.letterHeaderSelector(6)).text() shouldBe "B"
        document.select(Selectors.firstProviderSelector(7)).text() shouldBe "bName"
        document.select(Selectors.firstProviderSelector(7)).attr("href") shouldBe "bUrl"
      }

      "have the correct section header and a single provider for C section" in {
        document.select(Selectors.letterHeaderSelector(8)).text() shouldBe "C"
        document.select(Selectors.firstProviderSelector(9)).text() shouldBe "cName"
        document.select(Selectors.firstProviderSelector(9)).attr("href") shouldBe "cUrl"
      }

      "have the correct section header and a single provider for D section" in {
        document.select(Selectors.letterHeaderSelector(10)).text() shouldBe "D"
        document.select(Selectors.firstProviderSelector(11)).text() shouldBe "dName"
        document.select(Selectors.firstProviderSelector(11)).attr("href") shouldBe "dUrl"
      }

      "have the correct section header and a single provider for E section" in {
        document.select(Selectors.letterHeaderSelector(12)).text() shouldBe "E"
        document.select(Selectors.firstProviderSelector(13)).text() shouldBe "eName"
        document.select(Selectors.firstProviderSelector(13)).attr("href") shouldBe "eUrl"
      }

      "have the correct section header and a single provider for F section" in {
        document.select(Selectors.letterHeaderSelector(14)).text() shouldBe "F"
        document.select(Selectors.firstProviderSelector(15)).text() shouldBe "fName"
        document.select(Selectors.firstProviderSelector(15)).attr("href") shouldBe "fUrl"
      }

      "have the correct section header and a single provider for G section" in {
        document.select(Selectors.letterHeaderSelector(16)).text() shouldBe "G"
        document.select(Selectors.firstProviderSelector(17)).text() shouldBe "gName"
        document.select(Selectors.firstProviderSelector(17)).attr("href") shouldBe "gUrl"
      }

      "have the correct section header and a single provider for H section" in {
        document.select(Selectors.letterHeaderSelector(18)).text() shouldBe "H"
        document.select(Selectors.firstProviderSelector(19)).text() shouldBe "hName"
        document.select(Selectors.firstProviderSelector(19)).attr("href") shouldBe "hUrl"
      }

      "have the correct section header and a single provider for I section" in {
        document.select(Selectors.letterHeaderSelector(20)).text() shouldBe "I"
        document.select(Selectors.firstProviderSelector(21)).text() shouldBe "iName"
        document.select(Selectors.firstProviderSelector(21)).attr("href") shouldBe "iUrl"
      }

      "have the correct section header and a single provider for J section" in {
        document.select(Selectors.letterHeaderSelector(22)).text() shouldBe "J"
        document.select(Selectors.firstProviderSelector(23)).text() shouldBe "jName"
        document.select(Selectors.firstProviderSelector(23)).attr("href") shouldBe "jUrl"
      }

      "have the correct section header and a single provider for K section" in {
        document.select(Selectors.letterHeaderSelector(24)).text() shouldBe "K"
        document.select(Selectors.firstProviderSelector(25)).text() shouldBe "kName"
        document.select(Selectors.firstProviderSelector(25)).attr("href") shouldBe "kUrl"
      }

      "have the correct section header and a single provider for L section" in {
        document.select(Selectors.letterHeaderSelector(26)).text() shouldBe "L"
        document.select(Selectors.firstProviderSelector(27)).text() shouldBe "lName"
        document.select(Selectors.firstProviderSelector(27)).attr("href") shouldBe "lUrl"
      }

      "have the correct section header and a single provider for M section" in {
        document.select(Selectors.letterHeaderSelector(28)).text() shouldBe "M"
        document.select(Selectors.firstProviderSelector(29)).text() shouldBe "mName"
        document.select(Selectors.firstProviderSelector(29)).attr("href") shouldBe "mUrl"
      }

      "have the correct section header and a single provider for N section" in {
        document.select(Selectors.letterHeaderSelector(30)).text() shouldBe "N"
        document.select(Selectors.firstProviderSelector(31)).text() shouldBe "nName"
        document.select(Selectors.firstProviderSelector(31)).attr("href") shouldBe "nUrl"
      }

      "have the correct section header and a single provider for O section" in {
        document.select(Selectors.letterHeaderSelector(32)).text() shouldBe "O"
        document.select(Selectors.firstProviderSelector(33)).text() shouldBe "oName"
        document.select(Selectors.firstProviderSelector(33)).attr("href") shouldBe "oUrl"
      }

      "have the correct section header and a single provider for P section" in {
        document.select(Selectors.letterHeaderSelector(34)).text() shouldBe "P"
        document.select(Selectors.firstProviderSelector(35)).text() shouldBe "pName"
        document.select(Selectors.firstProviderSelector(35)).attr("href") shouldBe "pUrl"
      }

      "have the correct section header and a single provider for Q section" in {
        document.select(Selectors.letterHeaderSelector(36)).text() shouldBe "Q"
        document.select(Selectors.firstProviderSelector(37)).text() shouldBe "qName"
        document.select(Selectors.firstProviderSelector(37)).attr("href") shouldBe "qUrl"
      }

      "have the correct section header and a single provider for R section" in {
        document.select(Selectors.letterHeaderSelector(38)).text() shouldBe "R"
        document.select(Selectors.firstProviderSelector(39)).text() shouldBe "rName"
        document.select(Selectors.firstProviderSelector(39)).attr("href") shouldBe "rUrl"
      }

      "have the correct section header and a single provider for S section" in {
        document.select(Selectors.letterHeaderSelector(40)).text() shouldBe "S"
        document.select(Selectors.firstProviderSelector(41)).text() shouldBe "sName"
        document.select(Selectors.firstProviderSelector(41)).attr("href") shouldBe "sUrl"
      }

      "have the correct section header and a single provider for T section" in {
        document.select(Selectors.letterHeaderSelector(42)).text() shouldBe "T"
        document.select(Selectors.firstProviderSelector(43)).text() shouldBe "tName"
        document.select(Selectors.firstProviderSelector(43)).attr("href") shouldBe "tUrl"
      }

      "have the correct section header and a single provider for U section" in {
        document.select(Selectors.letterHeaderSelector(44)).text() shouldBe "U"
        document.select(Selectors.firstProviderSelector(45)).text() shouldBe "uName"
        document.select(Selectors.firstProviderSelector(45)).attr("href") shouldBe "uUrl"
      }

      "have the correct section header and a single provider for V section" in {
        document.select(Selectors.letterHeaderSelector(46)).text() shouldBe "V"
        document.select(Selectors.firstProviderSelector(47)).text() shouldBe "vName"
        document.select(Selectors.firstProviderSelector(47)).attr("href") shouldBe "vUrl"
      }

      "have the correct section header and a single provider for W section" in {
        document.select(Selectors.letterHeaderSelector(48)).text() shouldBe "W"
        document.select(Selectors.firstProviderSelector(49)).text() shouldBe "wName"
        document.select(Selectors.firstProviderSelector(49)).attr("href") shouldBe "wUrl"
      }

      "have the correct section header and a single provider for X section" in {
        document.select(Selectors.letterHeaderSelector(50)).text() shouldBe "X"
        document.select(Selectors.firstProviderSelector(51)).text() shouldBe "xName"
        document.select(Selectors.firstProviderSelector(51)).attr("href") shouldBe "xUrl"
      }

      "have the correct section header and a single provider for Y section" in {
        document.select(Selectors.letterHeaderSelector(52)).text() shouldBe "Y"
        document.select(Selectors.firstProviderSelector(53)).text() shouldBe "yName"
        document.select(Selectors.firstProviderSelector(53)).attr("href") shouldBe "yUrl"
      }

      "have the correct section header and a single provider for Z section" in {
        document.select(Selectors.letterHeaderSelector(54)).text() shouldBe "Z"
        document.select(Selectors.firstProviderSelector(55)).text() shouldBe "zName"
        document.select(Selectors.firstProviderSelector(55)).attr("href") shouldBe "zUrl"
      }
    }

    "given no providers" should {

      lazy val view = views.html.software_choices(SoftwareChoicesViewModel(Seq.empty[SoftwareProviderModel]))
      lazy val document = Jsoup.parse(view.body)

      "not show any sections" in {
        document.select(Selectors.letterHeaderSelector(2)).isEmpty shouldBe true
        document.select(Selectors.letterHeaderSelector(54)).isEmpty shouldBe true
      }
    }
  }
}
