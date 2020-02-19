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

  val compile = Seq(
    "uk.gov.hmrc" %% "play-whitelist-filter" % "3.1.0-play-25",
    "uk.gov.hmrc" %% "govuk-template" % "5.52.0-play-25",
    "uk.gov.hmrc" %% "play-ui" % "8.8.0-play-25",
    "uk.gov.hmrc" %% "bootstrap-play-25" % "5.1.0"
  )

  val test = Seq(
    "org.jsoup" % "jsoup" % "1.11.3" % "test",
    "com.typesafe.play" %% "play-test" % current % "test",
    "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.1" % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % "test",
    "org.pegdown" % "pegdown" % "1.6.0" % "test"
  )

}
