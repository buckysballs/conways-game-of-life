/**
  * Created by Wyatt on 4/17/17.
  */

/**
  * Object for containing coordinates
  *
  * @param x Int x coordinate of this Cell
  * @param y Int y coordinate of this Cell
  */
case class Cell(x: Int, y: Int)

/**
  * Implements game logic and stores state as well as di
  *
  * @param xDim Int max x dimension
  * @param yDim Int max y dimension
  * @param curState Set[Cell] current state of the grid, all living cells at this current iteration
  */
class Grid(xDim: Int, yDim: Int, var curState: Set[Cell]) {

  /**
    * Returns cells that might come to life
    *
    * @param cells Set[Cell]
    * @return Set[Cell] all living and potentially living cells
    */
  private def potentialLife(cells: Set[Cell]): Set[Cell] =
    cells.flatMap {
      cell =>
        val xNeighbors = cell.x - 1 to cell.x + 1
        val yNeighbors = cell.y - 1 to cell.y + 1
        xNeighbors.flatMap { xCoord =>
          yNeighbors.map(yCoord => Cell(xCoord, yCoord))
        }
    }

  /**
    * Returns all neighbors of curCell
    *
    * @param curCell Cell cell who's neighbors we're interested in
    * @param allCells allCells all currently living cells
    * @return Set[Cell] neighbors of curCell
    */
  private def neighbors(curCell: Cell, allCells: Set[Cell]): Set[Cell] =
    allCells.filter {
      cell =>
        cell != curCell && math.abs(cell.x - curCell.x) <= 1 && math.abs(cell.y - curCell.y) <= 1
    }

  /**
    * Transforms current state to the next iteration
    *
    * @param allCells Set[Cell] all currently living cells
    * @return Set[Cell] next state
    */
  private def iterate(allCells: Set[Cell] = curState): Set[Cell] =
    potentialLife(allCells).filter {
      cell =>
        val totalNeighbors = neighbors(cell, allCells).size
        (allCells.contains(cell) && totalNeighbors == 2) || totalNeighbors == 3
    }.filter(Grid.onGrid(xDim, yDim))

  /**
    * Displays state to the user
    *
    * @param curIteration Set[Cell] living cells at this current iteration
    */
  def display(curIteration: Set[Cell] = curState): Unit = {
    val empty = Array.fill[String](xDim, yDim)("0")
    curIteration.foreach { cell =>
      empty(cell.x)(cell.y) = "1"
    }
    println(empty.map(_.mkString(" ")).mkString("\n"))
    println("\n")
  }

  /**
    * Main control flow
    */
  def evolve(): Unit = {
    display()
    curState = this.iterate()
  }
}

object Grid {

  /**
    * Helper for determining if a cell has moved beyond the grid
    *
    * @param xDim Int
    * @param yDim Int
    * @param cell Cell cell in question
    * @return Boolean is this cell on the grid
    */
  def onGrid(xDim: Int, yDim: Int)(cell: Cell): Boolean = cell.x >= 0 && cell.x < xDim && cell.y >= 0 && cell.y < yDim
}