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

import org.jsoup.nodes.{Document, Element}

class AccessibilityStatementViewSpec extends ViewBaseSpec {

  object Messages {
    val postFix = " - Find software for Making Tax Digital - GOV.UK"
    val heading = "Accessibility statement for finding software for Making Tax Digital for VAT"
    val title: String = heading + postFix
    val para1 = "This accessibility statement explains how accessible this service is, what to do if you have difficulty using it, and how to report accessibility problems with the service."
    val para2 = "This service is part of the wider GOV.UK website. There is a separate accessibility statement for the main GOV.UK website."
    val para3 = "This page only contains information about finding software for the Making Tax Digital for VAT service, available at https://www.tax.service.gov.uk/making-tax-digital-software"

    val usingServiceHeading = "Using this service"
    val usingServicePara1 = "This service helps you find compatible software for keeping digital records and submitting VAT Returns to HMRC. You can also find bridging software to connect non-compatible software (like spreadsheets) to HMRC systems."
    val usingServicePara2 = "This service is run by HM Revenue and Customs (HMRC). We want as many people as possible to be able to use this service. This means you should be able to:"
    val usingServiceBullet1 = "change colours, contrast levels and fonts"
    val usingServiceBullet2 = "zoom in up to 300% without the text spilling off the screen"
    val usingServiceBullet3 = "get from the start of the service to the end using just a keyboard"
    val usingServiceBullet4 = "get from the start of the service to the end using speech recognition software"
    val usingServiceBullet5 = "listen to the service using a screen reader (including the most recent versions of JAWS, NVDA and VoiceOver)"
    val usingServicePara3 = "We have also made the text in the service as simple as possible to understand."
    val usingServicePara4 = "AbilityNet has advice on making your device easier to use if you have a disability."

    val howAccessibleHeading = "How accessible this service is"
    val howAccessiblePara1 = "This service is partially compliant with the Web Content Accessibility Guidelines version 2.1 AA standard."
    val howAccessiblePara2 = "Some people may find parts of this service difficult to use:"
    val howAccessibleBullet1 = "We use filters to help users find specific software. However, the filter headings are not currently viewable by assistive technology which means screen readers cannot jump to these filters."
    val howAccessibleBullet2 = "The links to the various software providers doesn't make it clear they will take the user to the vendor's home page where users can read more information about the vendor's software products."

    val reportingProblemsHeading = "Reporting accessibility problems with this service"
    val reportingProblemsPara1 = "We are always looking to improve the accessibility of this service. If you find any problems that are not listed on this page or think we are not meeting accessibility requirements, contact hmrc-accessibility-problems@digital.hmrc.gov.uk."

    val ifNotHappyHeading = "What to do if you are not happy with how we respond to your complaint"
    val ifNotHappyPara1 = "The Equality and Human Rights Commission (EHRC) is responsible for enforcing the Public Sector Bodies (Websites and Mobile Applications) (No. 2) Accessibility Regulations 2018 (the 'accessibility regulations'). If you are not happy with how we respond to your complaint, contact the Equality Advisory and Support Service (EASS), or the Equality Commission for Northern Ireland (ECNI) if you live in Northern Ireland."

    val contactUsHeading = "Contacting us by phone or getting a visit from us in person"
    val contactUsPara1 = "We provide a text relay service if you are deaf, hearing impaired or have a speech impediment."
    val contactUsPara2 = "We can provide a British Sign Language (BSL) interpreter, or you can arrange a visit from an HMRC advisor to help you complete the service."
    val contactUsPara3 = "Find out how to contact us."

    val technicalInformationHeading = "Technical information about this service's accessibility"
    val technicalInformationPara1 = "HMRC is committed to making this service accessible, in accordance with the Public Sector Bodies (Websites and Mobile Applications) (No. 2) Accessibility Regulations 2018."
    val technicalInformationPara2 = "This service is partially compliant with the Web Content Accessibility Guidelines version 2.1 AA standard, due to the non-compliances listed below."
    val technicalInformationNonAccessibleHeading = "Non-accessible content"
    val technicalInformationNonAccessiblePara1 = "The content listed below is non-accessible for the following reasons."
    val technicalInformationNonAccessibleSubheading = "Non-compliance with the accessibility regulations"
    val technicalInformationNonAccessibleNonCompliant1Info = "We use filters to help users find specific software. However, the filter headings are not currently viewable by assistive technology which means screen readers cannot jump to these filters."
    val technicalInformationNonAccessibleNonCompliant1Criteria = "This doesn't meet WCAG 2.1 success criterion 1.3.1 (info and relationships)."
    val technicalInformationNonAccessibleNonCompliant1Plan = "We plan to add the code necessary to make the filter headings viewable to assistive technologies by November 2019. If we add new headings, we'll make sure they meet accessibility standards."
    val technicalInformationNonAccessibleNonCompliant2Info = "The links to the various software providers doesn't make it clear they will take the user to the vendor's home page where users can read more information about the vendor's software products."
    val technicalInformationNonAccessibleNonCompliant2Criteria = "This doesn't meet WCAG 2.1 success criterion 2.4.4 (link purpose in context)."
    val technicalInformationNonAccessibleNonCompliant2Plan = "We'll label these links to tell users where they go if they click on a link, for example: Visit software provider ABC Accounts Ltd (opens in a new tab). This will be done by November 2019. If we add new links, we'll make sure they meet accessibility standards."

    val howTestedHeading = "How we tested this service"
    val howTestedPara1 = "The service was last tested on 28 August 2019 and was checked for compliance with WCAG 2.1 AA."
    val howTestedPara2 = "The service was built using parts that were tested by the Digital Accessibility Centre. The full service was tested by HMRC and included disabled users."
    val howTestedPara3 = "This page was prepared on 19 September 2019. It was last updated on 19 September 2019."

    val govUkAccessibilityStatementLinkText = "accessibility statement"
    val serviceLinkText = "https://www.tax.service.gov.uk/making-tax-digital-software"
    val abilityNetLinkText = "AbilityNet"
    val accessibilityGuidelinesLinkText = "Web Content Accessibility Guidelines version 2.1 AA standard"
    val eassLinkText = "contact the Equality Advisory and Support Service"
    val ecniLinkText = "Equality Commission for Northern Ireland"
    val dacLinkText = "Digital Accessibility Centre"
    val emailText = "hmrc-accessibility-problems@digital.hmrc.gov.uk"
  }

  lazy val document: Document = parseView(views.html.accessibility_statement())
  lazy val body: Element = document.getElementById("content")

  "accessibility_statement" must {
    "have a title" in {
      document.title shouldBe Messages.title
    }

    "have a heading" in {
      body.select("h1").text shouldBe Messages.heading
    }

    "have multiple h2" in {
      body.select("h2").text() shouldBe Seq(
        Messages.usingServiceHeading,
        Messages.howAccessibleHeading,
        Messages.reportingProblemsHeading,
        Messages.ifNotHappyHeading,
        Messages.contactUsHeading,
        Messages.technicalInformationHeading,
        Messages.howTestedHeading
      ).mkString(" ")
    }

    "have a h3" in {
      body.select("h3").text() shouldBe Messages.technicalInformationNonAccessibleHeading
    }

    "have a h4" in {
      body.select("h4").text() shouldBe Messages.technicalInformationNonAccessibleSubheading
    }

    "have multiple bullet points" in {
      body.select("li").text() shouldBe Seq(
        Messages.usingServiceBullet1,
        Messages.usingServiceBullet2,
        Messages.usingServiceBullet3,
        Messages.usingServiceBullet4,
        Messages.usingServiceBullet5,
        Messages.howAccessibleBullet1,
        Messages.howAccessibleBullet2
      ).mkString(" ")
    }

    "have multiple paragraphs" in {
      body.select("p").text() shouldBe Seq(
        Messages.para1,
        Messages.para2,
        Messages.para3,
        Messages.usingServicePara1,
        Messages.usingServicePara2,
        Messages.usingServicePara3,
        Messages.usingServicePara4,
        Messages.howAccessiblePara1,
        Messages.howAccessiblePara2,
        Messages.reportingProblemsPara1,
        Messages.ifNotHappyPara1,
        Messages.contactUsPara1,
        Messages.contactUsPara2,
        Messages.contactUsPara3,
        Messages.technicalInformationPara1,
        Messages.technicalInformationPara2,
        Messages.technicalInformationNonAccessiblePara1,
        Messages.technicalInformationNonAccessibleNonCompliant1Info,
        Messages.technicalInformationNonAccessibleNonCompliant1Criteria,
        Messages.technicalInformationNonAccessibleNonCompliant1Plan,
        Messages.technicalInformationNonAccessibleNonCompliant2Info,
        Messages.technicalInformationNonAccessibleNonCompliant2Criteria,
        Messages.technicalInformationNonAccessibleNonCompliant2Plan,
        Messages.howTestedPara1,
        Messages.howTestedPara2,
        Messages.howTestedPara3
      ).mkString(" ")
    }
  }

  "have a link to the govuk accessibility statement" in {
    body.select("a[href='https://www.gov.uk/help/accessibility']").text shouldBe Messages.govUkAccessibilityStatementLinkText
  }

  "have a link to the service" in {
    body.select("a[href='https://www.tax.service.gov.uk/making-tax-digital-software']").text shouldBe Messages.serviceLinkText
  }

  "have a link to ability net" in {
    body.select("a[href='https://mcmw.abilitynet.org.uk/']").text shouldBe Messages.abilityNetLinkText
  }

  "have two links to the accessibility guidelines" in {
    body.select("a[href='https://www.w3.org/TR/WCAG21/']").text shouldBe Seq(
      Messages.accessibilityGuidelinesLinkText,
      Messages.accessibilityGuidelinesLinkText
    ).mkString(" ")
  }

  "have a link to EASS" in {
    body.select("a[href='https://www.equalityadvisoryservice.com/']").text shouldBe Messages.eassLinkText
  }

  "have a link to ECNI" in {
    body.select("a[href='https://www.equalityni.org/Home']").text shouldBe Messages.ecniLinkText
  }

  "have a link to DAC" in {
    body.select("a[href='http://www.digitalaccessibilitycentre.org/']").text shouldBe Messages.dacLinkText
  }

  "have an email link" in {
    body.select("a[href='mailto:hmrc-accessibility-problems@digital.hmrc.gov.uk']").text shouldBe Messages.emailText
  }

}
