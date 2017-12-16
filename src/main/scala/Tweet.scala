import io.circe.Json
import io.circe.jawn.parse
import scala.io.Source

object Tweet {
  val Right(quoteTweetSample: Json) = parse(
    Source.fromResource("tweet-01.json").mkString("")
  )
}