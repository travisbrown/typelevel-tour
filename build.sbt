inThisBuild(
  Seq(
    organization      := "io.circe",
    scalaVersion      := "2.12.4",
    scalaOrganization := "org.typelevel",
    scalaVersion      := "2.12.4-bin-typelevel-4"
  )
)

scalacOptions ++= Seq(
  "-Yliteral-types"
)

val circeVersion = "0.9.0-M2"

libraryDependencies ++= Seq(
  "co.fs2"   %% "fs2-io"               % "0.10.0-M8",
  "io.circe" %% "circe-core"           % circeVersion,
  "io.circe" %% "circe-fs2"            % "0.9.0-M3",
  "io.circe" %% "circe-generic-extras" % circeVersion,
  "io.circe" %% "circe-jawn"           % circeVersion,
  "io.circe" %% "circe-literal"        % circeVersion,
  "io.circe" %% "circe-optics"         % circeVersion,
  "io.circe" %% "circe-refined"        % circeVersion,
  "io.circe" %% "circe-shapes"         % circeVersion,
  "io.circe" %% "circe-testing"        % circeVersion % Test
)

initialCommands in console :=
  """
    |import cats.implicits._
    |import io.circe._
    |import io.circe.jawn
    |import io.circe.literal._
    |import io.circe.syntax._
  """.stripMargin
