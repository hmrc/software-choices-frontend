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

package config

object ConfigKeys {

  val contactFrontendService: String = "contact-frontend.host"

  private val googleAnalyticsRoot: String = "google-analytics"

  val googleAnalyticsToken: String = googleAnalyticsRoot + ".token"
  val googleAnalyticsHost: String = googleAnalyticsRoot + ".host"

  val host: String = "host"

  val shutterPage: String = "whitelist.shutterPage"
  val whitelistIps: String = "whitelist.ips"
  val whitelistExcludedPaths: String = "whitelist.excludedPaths"
  val whitelistEnabled: String = "whitelist.enabled"

  val progressiveDisclosureFeature: String = "features.progressiveDisclosure.enabled"
  val filterViewFeature: String = "features.filterView.enabled"
  val agentBusinessFilterFeature: String = "features.agentBusinessFilter.enabled"
  val typeOfSoftwareFilterFeature: String = "features.typeOfSoftware.enabled"
  val additionalSoftwareFilterFeature: String = "features.additionalSoftwareFilter.enabled"
  val priceFilterFeature: String = "features.priceFilter.enabled"
  val accessibilityFilterFeature: String = "features.accessibilityFilter.enabled"

  val govUkMtdVatSignUpGuidance = "govuk.mtdvat.signup.url"

}
