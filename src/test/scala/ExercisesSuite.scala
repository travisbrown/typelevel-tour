import io.circe.literal._
import io.circe.syntax._
import org.scalatest.FunSuite

class ExercisesSuite extends FunSuite {
  test("Ex1 should pass") {
    import Ex1._

    val expected = Polygon(
      List(
        List(
          Coord(-122.422003528252475, 37.808480096967251),
          Coord(-122.422076013325281, 37.808835019815085),
          Coord(-122.421102174348633, 37.808803534992904),
          Coord(-122.421062569067274, 37.808601056818148)
        )
      )
    )

    assert(Ex1.decodePolygon.decodeJson(CityLots.samplePolygon) === Right(expected))
  }

  test("Ex2 should pass") {
    import Ex2._

    val expected = Polygon(
      List(
        List(
          Coord(-122.422003528252475, 37.808480096967251),
          Coord(-122.422076013325281, 37.808835019815085),
          Coord(-122.421102174348633, 37.808803534992904),
          Coord(-122.421062569067274, 37.808601056818148)
        )
      )
    )

    val badJson = json"""{ "type": "Polygon", "coordinates": [], "other": null }"""

    assert(Ex2.decodePolygon.decodeJson(CityLots.samplePolygon) === Right(expected))
    assert(Ex2.decodePolygon.decodeJson(badJson).isLeft)
  }


  test("Ex5 should pass") {
    val expected = List("ygritteygritte", "joyding", "ManceRayder7", "joydingtesting", "omgwtfwhatyes", "YesKingRobert", "NightsWatchJonS", "RebornAzorAhai", "MelisandreBurns", "mancerayder8", "WinterfellaNed", "TyrionDragon", "DanJackson415", "atornes", "kwchang", "FrostBike", "BlipQA", "QAHitman", "mrdonut", "ntakayama", "NallsyMove", "cjburrows", "iamnicksheng", "joy__ebooks", "kehli", "allenschen")

    assert(Ex5.values === expected.map(_.asJson))
  }
}