import cats.implicits._
import io.circe.{ Decoder, Json }

object Ex1 {
  case class Coord(x: Double, y: Double)
  case class Polygon(coordinates: List[List[Coord]])

  implicit val decodeCoord: Decoder[Coord] =
    Decoder[(Double, Double, Double)].map {
      case (x, y, _) => Coord(x, y)
    }

  /**
   * Write a decoder for this polygon type that works with the city lots
   * examples.
   */
  def decodePolygon: Decoder[Polygon] = ???
}

object Ex2 {
  import io.circe.literal._

  case class Coord(x: Double, y: Double)
  case class Polygon(coordinates: List[List[Coord]])

  implicit val decodeCoord: Decoder[Coord] =
    Decoder[(Double, Double, Double)].map {
      case (x, y, _) => Coord(x, y)
    }

  /**
   * Write a decoder for this polygon type that works with the city lots
   * examples, and doesn't allow any fields except `type` and `coordinates`.
   * For extra credit, ensure that `type` is `Polygon`.
   */
  def decodePolygon: Decoder[Polygon] = ???
}

object Ex3 {
  import io.circe.generic.extras._

  case class Coord(x: Double, y: Double)

  sealed trait Geometry
  case class Polygon(coordinates: List[List[Coord]]) extends Geometry
  case class MultiPolygon(coordinates: List[List[List[Coord]]]) extends Geometry

  implicit val decodeCoord: Decoder[Coord] =
    Decoder[(Double, Double, Double)].map {
      case (x, y, _) => Coord(x, y)
    }

  /**
   * Write a decoder for this geometry type that works with the city lots
   * examples.
   */
  def decodeGeometry: Decoder[Geometry] = ???
}

object Ex4 {
  import shapeless._
  import shapeless.labelled.FieldType
  import eu.timepit.refined.api.Refined
  import eu.timepit.refined.string._

  import io.circe.generic.extras._
  import io.circe.literal._
  import io.circe.refined._
  import io.circe.shapes._

  case class Coord(x: Double, y: Double)

  sealed trait Geometry
  case class Polygon(coordinates: List[List[Coord]]) extends Geometry
  case class MultiPolygon(coordinates: List[List[List[Coord]]]) extends Geometry

  /**
   * Expand this record to include the `MAPBLKLOT` and `BLKLOT` fields and
   * enforce their length and digitness.
   */
  type Rec = HNil

  case class Lot(props: Rec, geo: Option[Geometry])

  implicit val decodeCoord: Decoder[Coord] =
    Decoder[(Double, Double, Double)].map {
      case (x, y, _) => Coord(x, y)
    }

  /**
   * Write a decoder for this lot type that works with the city lots
   * examples, confirming that the type is always `Feature`.
   */
  def decodeLot: Decoder[Lot] = ???
}

object Ex5 {
  import io.circe.optics.{ JsonPath, JsonTraversalPath }
  import monocle.Traversal

  /**
   * Define a path that picks all of the user-mentioned screen names out of
   * tweet-01.json. (This should be a one-liner.)
   */
  def path: JsonTraversalPath = ???

  def traversal: Traversal[Json, Json] = path.json
  def values: List[Json] = traversal.getAll(Tweet.quoteTweetSample)
}

object Ex6 {
  /**
   * Implement a JSON value printer using a `Json.Folder`.
   */
  def printer(json: Json): String = ???
}