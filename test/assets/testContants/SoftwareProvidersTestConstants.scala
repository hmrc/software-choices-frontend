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

package assets.testContants

import config.AppConfig
import models.{SoftwareChoicesFilterViewModel, SoftwareChoicesViewModel, SoftwareProviderModel}

trait SoftwareProvidersTestConstants {

  val providerA = SoftwareProviderModel("aName", "aUrl")
  val providerA2 = SoftwareProviderModel("a2Name", "a2Url")
  val providerA3 = SoftwareProviderModel("a3Name", "a3Url")
  val providerA4 = SoftwareProviderModel("a4Name", "a4Url")
  val providerB = SoftwareProviderModel("bName", "bUrl")
  val providerHash = SoftwareProviderModel("#Name", "#Url")

  val providers = SoftwareChoicesViewModel(
    allProviders = Seq(
      providerHash,
      providerA,
      providerB
    ),
    foundProviders = Seq()
  )

  val foundProviders = SoftwareChoicesViewModel(
    allProviders = Seq(
      providerA,
      providerB,
      providerHash
    ),
    foundProviders = Seq(
      providerA
    ))

  def filterViewProviders(implicit appConfig: AppConfig): SoftwareChoicesFilterViewModel = SoftwareChoicesFilterViewModel(
    allProviders = Seq(
      providerHash,
      providerA,
      providerB
    )
  )

  def filterViewFoundProviders(implicit appConfig: AppConfig): SoftwareChoicesFilterViewModel = SoftwareChoicesFilterViewModel(
    allProviders = Seq(
      providerHash,
      providerA,
      providerB
    ),
    filteredProviders = Some(Seq(
      providerA
    ))
  )

  val categoryAProviders = Seq(providerA, providerA2, providerA3, providerA4)
}
