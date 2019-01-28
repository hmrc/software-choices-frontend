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

import assets.messages.{CommonMessages, SoftwareChoicesMessages}
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import play.twirl.api.HtmlFormat
import utils.TestUtils

trait ViewBaseSpec extends TestUtils {

  lazy val parseView: HtmlFormat.Appendable => Document = x => Jsoup.parse(x.body)

  val opensInANewTabSuffix: String => String = _ + CommonMessages.newTab

  val softwareCategoryAriaLabel: String => String = x => SoftwareChoicesMessages.categoryAriaLabel(x)
  val softwareCompanyAriaLabel: String => String = x => opensInANewTabSuffix(SoftwareChoicesMessages.providerAriaLabel(x))

}
