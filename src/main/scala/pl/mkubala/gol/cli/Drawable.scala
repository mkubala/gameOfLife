package pl.mkubala.gol.cli

import pl.mkubala.gol.Board
import pl.mkubala.gol.Board.Cell

trait Drawable[F, T] {
  def draw(f: F): T
}

object Drawable {

  implicit object BoardIsAsciiDrawable extends Drawable[Board, String] {

    val colSeparator = "|"
    val rowSeparatorSymbol = "-"
    val aliveSymbol = "X"
    val deadSymbol = " "

    private def drawCell(cell: Cell): String = if (cell.isAlive) aliveSymbol else deadSymbol

    override def draw(board: Board): String = {
      val numOfLiceCells = (board.cells.flatten[Cell].foldLeft(0)) { (acc, cell) =>
        if (cell.isAlive) acc + 1
        else acc
      }

      val (w, _) = board.dimensions
      val rowSeparator = s" ${rowSeparatorSymbol * (w * 2 - 1)} \n"

      val asciiBoard = board.cells.map { row =>
        row.map(drawCell).mkString("|", ".", "|\n")
      }.mkString(rowSeparator, "", rowSeparator)

      s"There is $numOfLiceCells alive cells. \n$asciiBoard"

    }

  }

}
