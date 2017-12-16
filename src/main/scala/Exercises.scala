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
  case class Coord(x: Double, y: Double)
  case class Polygon(coordinates: List[List[Coord]])

  implicit val decodeCoord: Decoder[Coord] =
    Decoder[(Double, Double, Double)].map {
      case (x, y, _) => Coord(x, y)
    }

  /**
   * Write a decoder for this polygon type that works with the city lots
   * examples, and doesn't allow any fields except `type` and `coordinates`.
   */
  def decodePolygon: Decoder[Polygon] = ???
}

object Ex3 {
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

  case class Coord(x: Double, y: Double)

  sealed trait Geometry
  case class Polygon(coordinates: List[List[Coord]]) extends Geometry
  case class MultiPolygon(coordinates: List[List[List[Coord]]]) extends Geometry

  /**
   * Expand this record to include the `MAPBLKLOT` and `BLKLOT` fields and
   * enforce their length.
   */
  type Rec = HNil

  case class Lot(tpe: String, props: Rec, geo: Option[Geometry])

  implicit val decodeCoord: Decoder[Coord] =
    Decoder[(Double, Double, Double)].map {
      case (x, y, _) => Coord(x, y)
    }

  /**
   * Write a decoder for this lot type that works with the city lots
   * examples.
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
  val values: List[Json] = traversal.getAll(Tweet.quoteTweetSample)
}