package ca.jonathanfritz.funopoly.game

import ca.jonathanfritz.funopoly.game.board.ChanceCard
import ca.jonathanfritz.funopoly.game.board.CommunityChestCard
import ca.jonathanfritz.funopoly.game.board.Deck

class Bank(
    private var houses: Int,
    private var hotels: Int,
    private var funds: Int,
    val chance: Deck<ChanceCard>,
    val communityChest: Deck<CommunityChestCard>
) {

    private var chanceDeckIndex: Int = 0

    fun transferTo(player: Player, amount: Int) {
        println("Bank transfers $amount to ${player.name}")
        funds -= amount
        player.credit(funds)
    }

    fun transferFrom(amount: Int, player: Player) {
        println("Player transfers $amount to Bank")

        // TODO: what happens if player can't afford this? throw an exception?
        player.debit(amount)
        funds += amount
    }
}