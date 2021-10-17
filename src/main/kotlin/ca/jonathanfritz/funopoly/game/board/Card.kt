package ca.jonathanfritz.funopoly.game.board

import ca.jonathanfritz.funopoly.game.Bank
import ca.jonathanfritz.funopoly.game.Player

interface Card {

    fun doAction(player: Player, bank: Bank, board: Board)
}