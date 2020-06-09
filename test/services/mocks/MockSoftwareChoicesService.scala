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

package services.mocks

import _root_.utils.TestUtils
import enums.Filter
import models.SoftwareProviderModel
import org.scalamock.scalatest.MockFactory
import play.api.libs.json.JsValue
import services.SoftwareChoicesService

trait MockSoftwareChoicesService extends TestUtils with MockFactory{

  lazy val mockSoftwareChoicesService: SoftwareChoicesService = mock[SoftwareChoicesService]

  def mockReadProviders(softwareProviders: Seq[SoftwareProviderModel]): Unit = {
    (mockSoftwareChoicesService.readProviders _: () => Seq[SoftwareProviderModel])
      .expects()
      .returns(softwareProviders)
  }

  def setupMockSearchProviders(softwareProviders: Seq[SoftwareProviderModel]): Unit = {
    (mockSoftwareChoicesService.searchProviders(_: String))
      .expects(*)
      .returns(softwareProviders)
  }

  def setupMockFilterProviders(softwareProviders: Seq[SoftwareProviderModel]): Unit = {
    (mockSoftwareChoicesService.filterProviders(_: Seq[Filter.Value], _: Option[String]))
      .expects(*,*)
      .returns(softwareProviders)
  }

  def mockReturnProviderJson(providerJson: Option[JsValue]): Unit = {
    (mockSoftwareChoicesService.returnProviderJson(_: String))
      .expects(*)
      .returns(providerJson)
  }
}
