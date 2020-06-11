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

package config.mocks

import config.AppConfig
import config.features.Features
import javax.inject.Inject
import org.scalamock.scalatest.MockFactory
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

class MockAppConfig @Inject()(implicit val sc: ServicesConfig)
  extends AppConfig with MockFactory {

  lazy val mockFeatures: Features = mock[Features]

  def progressiveDisclosureEnabled(enabled: Boolean): Unit = mockFeatures.progressiveDisclosureEnabled(enabled)
  def filterViewEnabled(enabled: Boolean): Unit = mockFeatures.filterViewEnabled(enabled)
  def agentBusinessFilterEnabled(enabled: Boolean): Unit = mockFeatures.agentBusinessFilterEnabled(enabled)
  def typeOfSoftwareFilterEnabled(enabled: Boolean): Unit = mockFeatures.typeOfSoftwareFilterEnabled(enabled)
  def additionalSoftwareFilterEnabled(enabled: Boolean): Unit = mockFeatures.additionalSoftwareFilterEnabled(enabled)
  def priceFilterEnabled(enabled: Boolean): Unit = mockFeatures.priceFilterEnabled(enabled)
  def accessibilityFilterEnabled(enabled: Boolean): Unit = mockFeatures.accessibilityFilterEnabled(enabled)
  def providerDetailsEnabled(enabled: Boolean): Unit = mockFeatures.providerDetailsEnabled(enabled)

  override val features = mockFeatures

}
