package pl.mkubala.gol

import scala.annotation.tailrec
import pl.mkubala.gol.cli.Drawable

object Game extends App {

  import Drawable.BoardIsAsciiDrawable

  case class GameState(board: Board) {
    def proceed = copy(board = board.nextGeneration)
    
    def draw(implicit drawable: Drawable[Board, String]): String = drawable.draw(board)
  }
  
  @tailrec
  def run(state: GameState, iteration: Int = 1) {
    println(s"printing $iteration gen..")
    println(state.draw)
    readLine("Press [Enter] to see next generation or type \"quit\"|\"exit\"|\"bye\" to exit.\n") match {
      case "quit" | "exit" | "bye" => println("bye!")
      case _ => run(state.proceed, iteration + 1)
    }
  }

  val ancestries = Set(
    (0, 0),
    (1, 1),
    (2, 2),
    (1, 2)
  )
  val dimensions = (5, 5)

  run(GameState(Board(dimensions, ancestries)))

}
