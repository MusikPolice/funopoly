package ca.jonathanfritz.funopoly.game

import ca.jonathanfritz.funopoly.config.Config
import ca.jonathanfritz.funopoly.game.board.Board
import ca.jonathanfritz.funopoly.game.board.Event

class Game(
    private val config: Config,
    private val board: Board,
    private val bank: Bank,
    private val dice: Dice,
    private vararg val players: Player) {

    fun run() {
        // initialization
        println("Initialization")
        for (player in players) {
            bank.transferTo(player, config.initialPlayerFunds)
            player.setBoardSpace(board, Event.Go::class)
        }

        // rounds
        for (turn in 0..config.maxRounds) {
            println("\nStarting round $turn")

            for (player in players) {
                player.takeTurn(dice, board, bank)
            }
        }
    }
}