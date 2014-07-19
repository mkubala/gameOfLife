package pl.mkubala.gol

import collection.immutable.Seq
import pl.mkubala.gol.Board._

case class Board(dimensions: Dimensions)(val cells: Array[Array[Cell]]) {

  def nextGeneration: Board = {

    def boardContains(x: Int, y: Int): Boolean = {
      val (w, h) = dimensions
      x >= 0 && x < w && y >= 0 && y < h
    }

    def cellAtPos(x: Int, y: Int): Option[Cell] =
      if (boardContains(x, y)) Some(cells(x)(y))
      else None

    def neighborsOf(cell: Cell): Seq[Cell] = {
      val (cellX, cellY) = cell.position
      for {
        x <- (cellX - 1) to (cellX + 1)
        y <- (cellY - 1) to (cellY + 1)
        if (x, y) != (cellX, cellY) && boardContains(x, y)
        cell <- cellAtPos(x, y)
      } yield cell
    }

    def countLiveNeighbors(cell: Cell): Int =
      neighborsOf(cell).filter(_.isAlive).size

    /*
     * According to a Conway's Rules
     */
    def shouldBeAlive(cell: Cell): Boolean =
      countLiveNeighbors(cell) match {
        case 3 => true
        case 2 if cell.isAlive => true
        case _ => false
      }

    Board(dimensions) {
      cells.map { row =>
        row.map { cell =>
          cell.copy(isAlive = shouldBeAlive(cell))
        }
      }
    }
  }

}

object Board {

  type Position = (Int, Int)
  type Dimensions = (Int, Int)

  case class Cell(position: Position, isAlive: Boolean) {
    val isDead = !isAlive
  }

  def apply(dimensions: Dimensions, ancestries: Set[Position]): Board = Board(dimensions){
    val (w, h) = dimensions
    (for {
      col <- 0 until w
      row <- 0 until h
      pos = (col, row)
    } yield Cell(pos, ancestries.contains((row, col)))).toArray.grouped(w).toArray
  }

}