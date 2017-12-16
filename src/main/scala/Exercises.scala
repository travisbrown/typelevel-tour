import cats.implicits._
import io.circe.{ Decoder, Json }
import io.circe.literal._

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
  def decodePolygon: Decoder[Polygon] = io.circe.generic.semiauto.deriveDecoder
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
   * For extra credit, ensure that `type` is `Polygon`.
   */
  def decodePolygon: Decoder[Polygon] = Decoder.fromState(
    for {
      _ <- Decoder.state.decodeField["Polygon"]("type")
      c <- Decoder.state.decodeField[List[List[Coord]]]("coordinates")
      _ <- Decoder.state.requireEmpty
    } yield Polygon(c)
  )
}

object Ex3 {
  import io.circe.generic.extras._

  implicit val config: Configuration =
    Configuration.default.withDiscriminator("type")

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
  def decodeGeometry: Decoder[Geometry] =
    io.circe.generic.extras.semiauto.deriveDecoder
}

object Ex4 {
  import shapeless._
  import shapeless.labelled.FieldType
  import eu.timepit.refined.api.Refined
  import eu.timepit.refined.string._

  import io.circe.generic.extras._
  import io.circe.refined._
  import io.circe.shapes._

  implicit val config: Configuration =
    Configuration.default.withDiscriminator("type")

  implicit val decodeGeometry: Decoder[Geometry] =
    io.circe.generic.extras.semiauto.deriveDecoder

  case class Coord(x: Double, y: Double)

  sealed trait Geometry
  case class Polygon(coordinates: List[List[Coord]]) extends Geometry
  case class MultiPolygon(coordinates: List[List[List[Coord]]]) extends Geometry

  type LotPredicate = MatchesRegex["\\d\\d\\d\\dT?\\d\\d\\d[A-F]?"]

  /**
   * Expand this record to include the `MAPBLKLOT` and `BLKLOT` fields and
   * enforce their length and digitness.
   */
  type Rec =
    FieldType["MAPBLKLOT", Refined[String, LotPredicate]] ::
    FieldType["BLKLOT", Refined[String, LotPredicate]] :: HNil
  case class Lot(props: Rec, geo: Option[Geometry])

  implicit val decodeCoord: Decoder[Coord] =
    Decoder[(Double, Double, Double)].map {
      case (x, y, _) => Coord(x, y)
    }

  /**
   * Write a decoder for this lot type that works with the city lots
   * examples, confirming that the type is always `Feature`.
   */
  def decodeLot: Decoder[Lot] = (
    Decoder["Feature"].prepare(_.downField("type")),
    Decoder[Rec].prepare(_.downField("properties")),
    Decoder[Option[Geometry]].prepare(_.downField("geometry"))
  ).mapN((_, p, g) => Lot(p, g))
}

object Ex5 {
  import io.circe.optics.{ JsonPath, JsonTraversalPath }
  import monocle.Traversal

  /**
   * Define a path that picks all of the user-mentioned screen names out of
   * tweet-01.json. (This should be a one-liner.)
   */
  def path: JsonTraversalPath = JsonPath.root.quoted_status.extended_tweet.entities.user_mentions.each.screen_name

  def traversal: Traversal[Json, Json] = path.json
  def values: List[Json] = traversal.getAll(Tweet.quoteTweetSample)
}

object Ex6 {
  /**
   * Implement a JSON value printer using a `Json.Folder`.
   */
  def printer(json: Json): String = json.noSpaces
}
object Ex7 {
  import io.circe.optics.JsonPath

  val doc: Json = json"""
    {
      "order": {
        "customer": {
          "name": "Foo McCustomer",
          "contactDetails": {
            "address": "1 Fake Street, London, England",
            "phone": "0123-456-789"
          }
        },
        "items": [{
          "id": 123,
          "description": "banana",
          "quantity": 1
        }, {
          "id": 456,
          "description": "apple",
         "quantity": 2
        }],
        "total": 123.45
      }
    }
  """

  /**
   * Implement a function that doubles the quantities in `json`, leaving the
   * rest of the document unchanged.
   */
  def doubleQuantities: Json => Json =
    JsonPath.root.order.items.each.quantity.int.modify(_ * 2)
}