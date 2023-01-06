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

  val playUiVersion = "9.7.0-play-28"
  val jsoupVersion = "1.13.1"
  val bootstrapVersion = "7.12.0"
  val scalaTestPlusVersion = "3.1.3"
  val scalaMockVersion = "3.6.0"

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% "bootstrap-frontend-play-28" % bootstrapVersion,
    "uk.gov.hmrc" %% "play-frontend-hmrc" % "5.2.0-play-28",
    "org.webjars" % "jquery" % "3.6.0"
  )

  val test: Seq[ModuleID] = Seq(
    "org.jsoup" % "jsoup" % jsoupVersion % "test",
    "com.typesafe.play" %% "play-test" % current % "test",
    "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlusVersion % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockVersion % "test",
    "org.pegdown" % "pegdown" % "1.6.0" % "test"
  )

}
