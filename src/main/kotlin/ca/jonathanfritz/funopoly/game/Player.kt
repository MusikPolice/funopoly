package ca.jonathanfritz.funopoly.game

import ca.jonathanfritz.funopoly.game.board.Board
import ca.jonathanfritz.funopoly.game.board.BoardSpace
import kotlin.reflect.KClass

data class Player(
    val name: String,
    val token: Token,
    private var funds: Int,
    private var inJail: Boolean = false
) {

    private var position: Int = 0

    fun getBoardSpace(board: Board): BoardSpace {
        return board.spaces[position]
    }

    fun inJail() {
        inJail = true
    }

    fun setBoardSpace(board: Board, space: KClass<out BoardSpace>, collectOnPassGo: Boolean = true) {
        // TODO: if passing GO, collect $200
        board.spaces.forEachIndexed { i, s ->
            if (s.javaClass == space) {
                position = i;
                println("Set $name board position to ${space.simpleName}")
                return;
            }
        }
    }

    fun moveSpaces(board: Board, numSpaces: Int) {
        if (numSpaces < 0) {
            println("Moving $name back $numSpaces spaces")
        } else {
            println("Moving $name forward $numSpaces spaces")
        }

        position -= numSpaces
        position %= board.spaces.size
    }

    fun takeTurn(dice: Dice, board: Board, bank: Bank) {
        // TODO: if in jail, player can try to roll doubles or play a get out of jail free card

        println("Starting ${name}'s turn")

        // roll the dice
        val (roll, isDoubles) = dice.roll()
        println("$name rolls a $roll")

        // advance the token
        position += roll
        position %= board.spaces.size
        println("$name lands on ${board.spaces[position].javaClass.simpleName}")

        // process the landing
        val boardSpace = board.spaces[position]
        boardSpace.doLanding(this, bank, board)
    }

    // TODO this should accept an instance of Bank or another Player so we know where the money goes
    fun debit(amount: Int) {
        if (funds - amount < 0) {
            // TODO: mortgaging? bankruptcy?
        }
        funds -= amount
    }

    // TODO this should accept an instance of Bank or another Player so we know where the money comes from
    fun credit(amount: Int) {
        funds += amount
    }

    fun transferTo(amount: Int, recipient: Player) {
        if (funds - amount < 0) {
            // TODO: mortgaging? bankruptcy?
        }
        funds -= amount
        recipient.funds += amount
    }
}