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

package utils

import config.mocks.MockAppConfig
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.i18n.{Lang, Messages, MessagesApi, MessagesImpl}
import play.api.inject.Injector
import play.api.test.FakeRequest

trait TestUtils extends WordSpec with Matchers with GuiceOneAppPerSuite with BeforeAndAfterEach with MaterializerSupport {

  override def beforeEach() {
    super.beforeEach()
    appConfig.priceFilterEnabled(true)
    appConfig.welshEnabled(false)
  }

  implicit lazy val fakeRequest = FakeRequest("GET", "/")

  lazy val injector: Injector = app.injector
  lazy val messagesApi: MessagesApi = injector.instanceOf[MessagesApi]
  implicit lazy val messages: Messages = MessagesImpl(Lang("en"), messagesApi)
  implicit lazy val appConfig = injector.instanceOf[MockAppConfig]

}
