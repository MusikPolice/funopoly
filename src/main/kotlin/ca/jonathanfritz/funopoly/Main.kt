package ca.jonathanfritz.funopoly

import ca.jonathanfritz.funopoly.config.Config
import ca.jonathanfritz.funopoly.game.*
import ca.jonathanfritz.funopoly.game.board.*
import com.sksamuel.hoplite.ConfigLoader

fun main(args: Array<String>) {
    // load the configuration
    // each Monopoly rule will be made configurable so that we can tweak them for different test scenarios
    val config = ConfigLoader().loadConfigOrThrow<Config>("/application-monopoly.yaml")

    // initialize the various board components
    val board = Board()
    val chanceDeck = Deck(listOf(
        ChanceCard.AdvanceTo(Event.Go::class),
        ChanceCard.AdvanceTo(Property.IllinoisAvenue::class),
        ChanceCard.AdvanceTo(Property.StCharlesPlace::class),
        ChanceCard.AdvanceToNearestUtility,
        ChanceCard.AdvanceToNearestRailroad,
        ChanceCard.AdvanceToNearestRailroad,
        ChanceCard.BankPaysYouDividend,
        ChanceCard.GetOutOfJailFree,
        ChanceCard.GoBackThreeSpaces,
        ChanceCard.GoToJail,
        ChanceCard.GeneralRepairs,
        ChanceCard.PoorTax,
        ChanceCard.AdvanceTo(Property.Railroad.ReadingRailroad::class),
        ChanceCard.AdvanceTo(Property.Boardwalk::class),
        ChanceCard.ChairmanOfTheBoard,
        ChanceCard.BuildingAndLoan,
        ChanceCard.CrosswordCompetition
    ))
    val communityChestDeck = Deck<CommunityChestCard>(emptyList()) // TODO: populate the community chest card deck
    val bank = Bank(config.bank.houses, config.bank.hotels, config.bank.funds, chanceDeck, communityChestDeck)
    val dice = Dice()

    // create a couple of players
    // TODO: in the future, players and their behaviour should be configurable
    val p1 = Player("p1", Token.Battleship, 125)
    val p2 = Player("p2", Token.Boot, 125)

    // start the game
    val game = Game(config, board, bank, dice, p1, p2)
    game.run()
}