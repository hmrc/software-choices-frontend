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

import _root_.utils.TestUtils
import assets.messages.{CommonMessages, SearchMessages}

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

    // Search Form
    messages("searchForm.term.missing") shouldBe SearchMessages.searchFormTermMissing
    messages("searchForm.term.max") shouldBe SearchMessages.searchFormTermMax
    messages("softwareChoices.search.label") shouldBe SearchMessages.label
    messages("softwareChoices.search.button.text") shouldBe SearchMessages.buttonText
  }
}
