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

package controllers

import play.api.i18n.Lang
import play.api.mvc.MessagesControllerComponents
import uk.gov.hmrc.play.language.LanguageUtils
import utils.TestUtils

class LanguageSwitchControllerSpec extends TestUtils {

  val mcc: MessagesControllerComponents = app.injector.instanceOf[MessagesControllerComponents]
  val lu: LanguageUtils = app.injector.instanceOf[LanguageUtils]

  val languages: Map[String, Lang] = Map("english" -> Lang("en"), "cymraeg" -> Lang("cy"))
  val fallbackUrl = "/making-tax-digital-software"

  object TestLanguageSwitchController extends LanguageSwitchController(
    appConfig,
    mcc,
    lu
  )

  "LanguageSwitchController.languageMap" should {

    lazy val result = TestLanguageSwitchController.languageMap

    "return languages from app config" in {
      result shouldBe languages
    }
  }

  "LanguageSwitchController.fallbackURL" should {

    lazy val result = TestLanguageSwitchController.fallbackURL

    "return correct fallback url" in {
      result shouldBe fallbackUrl
    }
  }
}