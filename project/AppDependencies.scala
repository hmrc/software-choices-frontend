import play.core.PlayVersion.current
import play.sbt.PlayImport._
import sbt.Keys.libraryDependencies
import sbt._

object AppDependencies {

  val compile = Seq(
    "uk.gov.hmrc"             %% "play-whitelist-filter"    % "2.0.0",
    "uk.gov.hmrc"             %% "govuk-template"           % "5.27.0-play-25",
    "uk.gov.hmrc"             %% "play-ui"                  % "7.40.0-play-25",
    "uk.gov.hmrc"             %% "bootstrap-play-25"        % "4.12.0"
  )

  val test = Seq(
    "uk.gov.hmrc"             %% "hmrctest"                    % "3.8.0-play-25"         % "test",
    "org.scalatest"           %% "scalatest"                   % "3.0.5"                 % "test",
    "org.jsoup"               %  "jsoup"                       % "1.11.3"                % "test",
    "com.typesafe.play"       %% "play-test"                   % current                 % "test",
    "org.scalatestplus.play"  %% "scalatestplus-play"          % "2.0.0"                 % "test",
    "org.scalamock"           %% "scalamock-scalatest-support" % "3.6.0"                 % "test"

  )

}
