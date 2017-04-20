import org.scalatest.{FunSpec, Matchers}

/**
  * Created by Wyatt on 4/17/17.
  */
class ConwaySpec extends FunSpec with Matchers {
  def testGame(seed: Set[Cell], iterations: Int, expectedState: Set[Cell]): Boolean = {
    val grid = new Grid(5, 5, seed)
    for (_ <- 0 to iterations) {
      grid.evolve()
    }
    grid.curState == expectedState
  }

  describe("A single cell") {
    it("should die after one iteration") {
      val seed = Set(Cell(0,0))
      testGame(seed, 1, Set.empty) === true
    }
  }

  describe("Two neighbor cells") {
    they("should die after one iteration") {
      val seeds = Set(Cell(0,0), Cell(0,1))
      testGame(seeds, 1, Set.empty) === true
    }
  }

  describe("A cell with the potential for life and three neighbors") {
    it("should come to life") {
      val seeds = Set(Cell(1, 0), Cell(0, 1), Cell(1, 1))
      testGame(seeds, 1, Set(Cell(0, 0), Cell(1, 0), Cell(0, 1), Cell(1, 1))) === true
    }
  }

  describe("A cell collection that reaches a steady state") {
    they("should stay the same") {
      val seeds = Set(Cell(0, 0), Cell(1, 0), Cell(0, 1), Cell(1, 1))
      testGame(seeds, 1, seeds) === true
    }
  }

  describe("A cell collection that reaches an oscillating steady state") {
    val seeds = Set(Cell(2, 2), Cell(3, 2), Cell(4, 2))
    val nextIter = Set(Cell(2, 2), Cell(2, 3), Cell(2, 4))
    they("should iterate to the next state") {
      testGame(seeds, 1, nextIter) === true
    }
    they("should then iterate back to the initial state") {
      testGame(seeds, 2, nextIter) === true
    }
  }

  describe("A glider that exits the grid") {
    it("should end up as a block") {
      val seeds = Set(Cell(1, 2), Cell(0, 1), Cell(2, 0), Cell(2, 1), Cell(2, 1), Cell(2, 2))
      testGame(seeds, 11, Set(Cell(3, 3), Cell(3, 4), Cell(4, 3), Cell(4, 4))) === true
    }
  }
}
