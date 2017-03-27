package ca.jonathanfritz.funopoly;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.jonathanfritz.funopoly.Dice.DiceRollResult;
import ca.jonathanfritz.funopoly.cards.ChanceCard;
import ca.jonathanfritz.funopoly.cards.CommunityChestCard;
import ca.jonathanfritz.funopoly.cards.Deck;
import ca.jonathanfritz.funopoly.tiles.Tile.Type;

public class Funopoly {

	// TODO: Guice
	private final Board board;
	private final Dice dice = new Dice();
	private final Deck<ChanceCard> chanceDeck = Deck.chance();
	private final Deck<CommunityChestCard> communityChestDeck = Deck.communityChest();
	private final List<Player> players = new ArrayList<>();

	private static final int STARTING_CASH = 1500;

	private static final Logger log = LoggerFactory.getLogger(Funopoly.class);

	public Funopoly(int numPlayers) {
		for (int i = 0; i < numPlayers; i++) {
			// all players start on GO with some cash money
			players.add(new Player("Player" + (i + 1), STARTING_CASH));
		}
		board = new Board(chanceDeck, communityChestDeck, players);
	}

	public void play() {
		int roundCounter = 0;
		while (true) {
			roundCounter++;
			log.info("Starting round {}", roundCounter);

			doRound();

			// when there's only one player left with money, the game is over
			final List<Player> playersWithMoney = players.stream().filter(p -> p.getBalance() > 0).collect(Collectors.toList());
			if (playersWithMoney.size() == 1) {
				log.info("The winner is {}", playersWithMoney.get(0));
				break;
			}
		}
	}

	private void doRound() {
		for (final Player player : players) {
			if (player.getBalance() <= 0) {
				continue;
			}

			log.info("{} starts their turn {} with ${}", player, player.isInJail() ? "in jail "
			        : "", player.getBalance());

			doPlayerTurn(player, 0);
		}
	}

	private void doPlayerTurn(Player player, int doublesCounter) {
		// if in jail, player can choose to use a card or pay to get out prior to rolling
		if (playerIsInJail(player) && !handlePlayerInJail(player)) {
			return;
		}

		// roll the dice
		final DiceRollResult diceRoll = dice.roll();
		log.info("{} rolls a {}", player, diceRoll.value);

		if (diceRoll.isDoubles && doublesCounter == 2) {
			// 3 doubles in a row is speeding - go to jail
			log.info("{} is in jail for speeding (3x doubles)", player);
			player.setInJail(true);
			board.movePlayerTo(player, diceRoll, Type.JAIL);
			return;
		}

		// turn proceeds as normal
		board.movePlayerNumSpaces(player, diceRoll);

		// TODO: build houses? trade?

		if (!playerIsInJail(player) && diceRoll.isDoubles) {
			// if player isn't in jail, doubles grants another turn
			doPlayerTurn(player, doublesCounter + 1);
		}
	}

	private boolean playerIsInJail(Player player) {
		return board.getPlayerPosition(player).isJail() && player.isInJail();
	}

	/**
	 * Handles player attempting to get out of jail
	 * @param player
	 * @return true if the player's turn should continue, false if it is over
	 */
	private boolean handlePlayerInJail(Player player) {
		if (player.debitGetOutOfJailFreeCard()) {
			log.info("{} uses a get out of jail free card", player);
			player.setInJail(false);
		} else if (player.payToGetOutOfJail()) {
			log.info("{} pays $50 to get out of jail", player);
			player.debit(50);
			player.setInJail(false);
		} else {
			// roll dice - if doubles, advance, turn ends
			final DiceRollResult diceRoll = dice.roll();
			if (diceRoll.isDoubles) {
				board.movePlayerNumSpaces(player, diceRoll);
				log.info("{} rolls doubles and leaves jail, advancing to {}", player, board.getPlayerPosition(player));
			} else {
				log.info("{} ends their turn in jail", player);
			}
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		final Funopoly funopoly = new Funopoly(2);
		funopoly.play();
	}
}
