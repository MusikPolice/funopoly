package ca.jonathanfritz.funopoly.game.board

import ca.jonathanfritz.funopoly.game.Bank
import ca.jonathanfritz.funopoly.game.Player

sealed class Event: BoardSpace {

    object Go: Event() {
        override fun getGroup() = BoardSpace.Group.Go

        override fun doLanding(player: Player, bank: Bank, board: Board) {
            // typically, nothing happens when a player lands on Go
            // award $200 if landing on or passing go https://boardgames.stackexchange.com/a/12255
            // TODO: optional double cash on go?
        }
    }

    object CommunityChest: Event() {
        override fun getGroup() = BoardSpace.Group.CommunityChest

        override fun doLanding(player: Player, bank: Bank, board: Board) {
            val card = bank.communityChest.draw()

            TODO("Not yet implemented")
        }
    }

    object IncomeTax: Event() {
        override fun getGroup() = BoardSpace.Group.Penalty

        override fun doLanding(player: Player, bank: Bank, board: Board) {
            TODO("Not yet implemented")
        }
    }

    object Chance: Event() {
        override fun getGroup() = BoardSpace.Group.Chance

        override fun doLanding(player: Player, bank: Bank, board: Board) {
            val card = bank.chance.draw()
            println("${player.name} drew ${card::class.simpleName} Chance card")
            card.doAction(player, bank, board)
        }
    }

    object Jail : Event() {
        override fun getGroup() = BoardSpace.Group.Jail

        override fun doLanding(player: Player, bank: Bank, board: Board) {
            // typically, nothing happens when a player lands on jail
        }
    }

    object FreeParking : Event() {
        override fun getGroup() = BoardSpace.Group.FreeParking

        override fun doLanding(player: Player, bank: Bank, board: Board) {
            // typically, nothing happens when a player lands on Free Parking

            // TODO: house rule that grants the player a pot of money from purchases
        }
    }

    object GoToJail : Event() {
        override fun getGroup() = BoardSpace.Group.GoToJail

        override fun doLanding(player: Player, bank: Bank, board: Board) {
            player.setBoardSpace(board, GoToJail::class)
        }
    }

    object LuxuryTax : Event() {
        override fun getGroup() = BoardSpace.Group.Penalty

        override fun doLanding(player: Player, bank: Bank, board: Board) {
            bank.transferFrom(75, player)
        }
    }
}
