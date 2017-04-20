import scala.io.Source._
import org.json4s._
import org.json4s.native.JsonMethods._

/**
  * Created by Wyatt on 4/17/17.
  */
object ConwaysGameOfLife {

  implicit val formats = DefaultFormats

  case class Parameters(iterations: Int,
                        xDimension: Int,
                        yDimension: Int,
                        seeds: List[String])

  def loadParams(path: String): Parameters = {
    val rawParams = fromFile(path).getLines.mkString
    parse(rawParams).extract[Parameters]
  }

  def parametersInvalid(iterations: Int, xDim: Int, yDim: Int, seeds: Set[Cell]): Boolean =
    iterations < 0 || xDim < 0 || yDim < 0 || !seeds.forall(Grid.onGrid(xDim, yDim))

  def main(args: Array[String]): Unit = {
    if (args.isEmpty) throw new Exception("ERROR: Must provide starting parameters")
    val Parameters(iterations, xDim, yDim, seedCoords) = loadParams(args.head)
    val seeds = seedCoords.map { arg =>
      val Array(x, y) = arg.split(",")
      Cell(x.toInt, y.toInt)
    }.toSet
    if (parametersInvalid(iterations, xDim, yDim, seeds)) throw new Exception("ERROR: Parameters are invalid")
    val grid = new Grid(xDim, yDim, seeds)
    for (_ <- 0 to iterations) {
      grid.evolve()
    }
    println {
      """
        |END OF GAME
      """.stripMargin
    }
  }
}
