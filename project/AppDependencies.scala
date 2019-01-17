import play.core.PlayVersion.current
import play.sbt.PlayImport._
import sbt.Keys.libraryDependencies
import sbt._

object AppDependencies {

  val compile = Seq(
    "uk.gov.hmrc"             %% "play-whitelist-filter"    % "2.0.0",
    "uk.gov.hmrc"             %% "govuk-template"           % "5.26.0-play-25",
    "uk.gov.hmrc"             %% "play-ui"                  % "7.27.0-play-25",
    "uk.gov.hmrc"             %% "bootstrap-play-25"        % "4.6.0"
  )

  val test = Seq(
    "uk.gov.hmrc"             %% "hmrctest"                    % "3.0.0"                 % "test, it",
    "org.scalatest"           %% "scalatest"                   % "3.0.4"                 % "test",
    "org.jsoup"               %  "jsoup"                       % "1.10.2"                % "test",
    "com.typesafe.play"       %% "play-test"                   % current                 % "test",
    "org.pegdown"             %  "pegdown"                     % "1.6.0"                 % "test, it",
    "uk.gov.hmrc"             %% "service-integration-test"    % "0.2.0"                 % "test, it",
    "org.scalatestplus.play"  %% "scalatestplus-play"          % "2.0.0"                 % "test, it",
    "org.mockito"             %  "mockito-core"                % "2.13.0"                % "test",
    "org.scalamock"           %% "scalamock-scalatest-support" % "3.6.0"                 % "test",
    "com.github.tomakehurst"  %  "wiremock"                    % "2.6.0"                 % "test"

  )

}
