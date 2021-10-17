package ca.jonathanfritz.funopoly.game.board

import ca.jonathanfritz.funopoly.game.Bank
import ca.jonathanfritz.funopoly.game.Player

interface BoardSpace {

    enum class Group {
        // colour groups
        Brown,
        LightBlue,
        Pink,
        Orange,
        Red,
        Yellow,
        Green,
        DarkBlue,

        // other board squares
        Go,
        CommunityChest,
        Penalty, // income tax, luxury tax
        Railroad,
        Chance,
        Jail,
        Utility,
        FreeParking,
        GoToJail
    }

    fun getGroup(): Group

    fun doLanding(player: Player, bank: Bank, board: Board)
}
