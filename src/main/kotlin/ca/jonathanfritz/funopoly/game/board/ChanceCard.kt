package ca.jonathanfritz.funopoly.game.board

import ca.jonathanfritz.funopoly.game.Bank
import ca.jonathanfritz.funopoly.game.Player
import kotlin.reflect.KClass

// TODO: test this crap
// see https://monopoly.fandom.com/wiki/Chance
sealed class ChanceCard: Card {

    // Advance to "Go". (Collect $200)
    // Advance to Illinois Ave. {Avenue}. [Advance to Trafalgar Square] If you pass Go, collect $200.
    // Advance to St. Charles Place. [Advance to Pall Mall] If you pass Go, collect $200.
    // Take a trip to Reading Railroad. If you pass Go, collect $200.
    // Take a walk on the Boardwalk. Advance token to Boardwalk.
    class AdvanceTo(
        private val space: KClass<out BoardSpace>
    ) : ChanceCard() {
        override fun doAction(player: Player, bank: Bank, board: Board) {
            player.setBoardSpace(board, space)
        }
    }

    // Advance token to the nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total 10 (ten) times the amount thrown.
    object AdvanceToNearestUtility: ChanceCard() {
        override fun doAction(player: Player, bank: Bank, board: Board) {
            // TODO: this almost certainly fails because we're supposed to go to nearest utility
            //       also how to temporarily modify rent amount?
            player.setBoardSpace(board, Property.Utility::class)
        }
    }

    // Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rental to which they are otherwise entitled. If Railroad is unowned, you may buy it from the Bank.
    object AdvanceToNearestRailroad: ChanceCard() {
        override fun doAction(player: Player, bank: Bank, board: Board) {
            // TODO: this almost certainly fails because we're supposed to go to nearest railroad
            //       also how to temporarily modify rent amount?
            player.setBoardSpace(board, Property.Railroad::class)
        }
    }

    // Bank pays you dividend of $50.
    object BankPaysYouDividend: ChanceCard() {
        override fun doAction(player: Player, bank: Bank, board: Board) {
            bank.transferTo(player, 50)
        }
    }

    object GetOutOfJailFree: ChanceCard() {
        override fun doAction(player: Player, bank: Bank, board: Board) {
            TODO("Not yet implemented")
        }
    }

    // Go back three spaces
    object GoBackThreeSpaces: ChanceCard() {
        override fun doAction(player: Player, bank: Bank, board: Board) {
            player.moveSpaces(board, -3)
        }
    }

    // Go to Jail. Go directly to Jail. Do not pass GO, do not collect $200.
    object GoToJail: ChanceCard() {
        override fun doAction(player: Player, bank: Bank, board: Board) {
            player.setBoardSpace(board, Event.Jail::class, collectOnPassGo = false)
            player.inJail()
        }
    }

    // Make general repairs on all your property: For each house pay $25, For each hotel {pay} $100.
    object GeneralRepairs: ChanceCard() {
        override fun doAction(player: Player, bank: Bank, board: Board) {
            TODO("Not yet implemented")
        }
    }

    // Pay poor tax of $15
    object PoorTax: ChanceCard() {
        override fun doAction(player: Player, bank: Bank, board: Board) {
            bank.transferFrom(15, player)
        }
    }

    // You have been elected Chairman of the Board. Pay each player $50.
    object ChairmanOfTheBoard: ChanceCard() {
        override fun doAction(player: Player, bank: Bank, board: Board) {
            TODO("Not yet implemented")
        }
    }

    // Your building {and} loan matures. Receive {Collect} $150.
    object BuildingAndLoan: ChanceCard() {
        override fun doAction(player: Player, bank: Bank, board: Board) {
            bank.transferTo(player, 150)
        }
    }

    // You have won a crossword competition. Collect $100.
    object CrosswordCompetition: ChanceCard() {
        override fun doAction(player: Player, bank: Bank, board: Board) {
            bank.transferTo(player, 100)
        }
    }
}