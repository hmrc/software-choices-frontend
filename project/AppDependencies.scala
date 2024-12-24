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
import sbt.*

object AppDependencies {

  val jsoupVersion          = "1.15.3"
  val bootstrapVersion      = "8.6.0"
  val scalaTestPlusVersion  = "7.0.1"
  val scalaMockVersion      = "5.1.0"

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %%  "bootstrap-frontend-play-30" % bootstrapVersion,
    "uk.gov.hmrc" %%  "play-frontend-hmrc-play-30" % "8.5.0",
    "org.webjars" %   "jquery"                     % "3.7.1"
  )

  val test: Seq[ModuleID] = Seq(
    "org.jsoup"               %  "jsoup"               % jsoupVersion          % "test",
    "org.playframework"       %% "play-test"           % current               % "test",
    "org.scalatestplus.play"  %% "scalatestplus-play"  % scalaTestPlusVersion  % "test",
    "org.scalamock"           %% "scalamock"           % scalaMockVersion      % "test",
    "org.pegdown"             %  "pegdown"             % "1.6.0"               % "test",
    "com.vladsch.flexmark"    %  "flexmark-all"        % "0.64.8"              % "test"
  )

  def apply(): Seq[sbt.ModuleID] = compile ++ test
}
