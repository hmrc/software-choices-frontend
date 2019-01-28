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

import assets.messages.{CommonMessages, SearchMessages, SoftwareChoicesMessages}
import utils.TestUtils

class MessagesSpec extends TestUtils {

  "Test message content is correct" in {

    //Service name
    messages("common.service.name") shouldBe CommonMessages.serviceName

    // Common Messages
    messages("common.back") shouldBe CommonMessages.back
    messages("common.errorSummary.heading") shouldBe CommonMessages.errorHeading
    messages("common.error") shouldBe CommonMessages.error
    messages("common.newTab") shouldBe CommonMessages.newTab


    // Feedback
    messages("feedback.before") shouldBe CommonMessages.feedbackBefore
    messages("feedback.link") shouldBe CommonMessages.feedbackLink
    messages("feedback.after") shouldBe CommonMessages.feedbackAfter

    // Software Choices Search/Results
    messages("softwareChoices.title") shouldBe SoftwareChoicesMessages.title
    messages("softwareChoices.show") shouldBe SoftwareChoicesMessages.showAll

    // Software Choices Results
    messages("softwareChoicesResults.noResults.title") shouldBe SoftwareChoicesMessages.noResultsHeader
    messages("softwareChoicesResults.noResults") shouldBe SoftwareChoicesMessages.noResults
    messages("softwareChoicesResults.results.title") shouldBe SoftwareChoicesMessages.resultsHeader
    messages("softwareChoicesResults.results") shouldBe SoftwareChoicesMessages.results

    // Search Form
    messages("searchForm.term.missing") shouldBe SearchMessages.searchFormTermMissing
    messages("searchForm.term.max") shouldBe SearchMessages.searchFormTermMax
    messages("softwareChoices.search.label") shouldBe SearchMessages.label
    messages("softwareChoices.search.text") shouldBe SearchMessages.text
    messages("softwareChoices.search.text.link") shouldBe SearchMessages.textLink
    messages("softwareChoices.search.button.text") shouldBe SearchMessages.buttonText
    messages("softwareChoices.search.clear") shouldBe SearchMessages.clear
    messages("softwareChoices.text.p1") shouldBe SearchMessages.p1
    messages("softwareChoices.text.p2") shouldBe SearchMessages.p2

  }
}
