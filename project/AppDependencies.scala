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

import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  val whitelistFilterVersion = "3.4.0-play-26"
  val govukTemplateVersion = "5.55.0-play-26"
  val playUiVersion = "8.13.0-play-26"

  val jsoupVersion = "1.13.1"
  val bootstrapVersion = "1.13.0"
  val scalaTestPlusVersion = "3.1.3"
  val scalaMockVersion = "3.6.0"

  val compile = Seq(
    "uk.gov.hmrc" %% "play-whitelist-filter" % whitelistFilterVersion,
    "uk.gov.hmrc" %% "govuk-template" % govukTemplateVersion,
    "uk.gov.hmrc" %% "play-ui" % playUiVersion,
    "uk.gov.hmrc" %% "bootstrap-play-26" % bootstrapVersion,
    "uk.gov.hmrc" %% "play-language" % "4.3.0-play-26"
  )

  val test = Seq(
    "org.jsoup" % "jsoup" % jsoupVersion % "test",
    "com.typesafe.play" %% "play-test" % current % "test",
    "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlusVersion % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockVersion % "test",
    "uk.gov.hmrc" %% "bootstrap-play-26" % bootstrapVersion % Test classifier "tests",
    "org.pegdown" % "pegdown" % "1.6.0" % "test"
  )

}
