package ca.jonathanfritz.funopoly.tiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Dice.DiceRollResult;
import ca.jonathanfritz.funopoly.Player;
import ca.jonathanfritz.funopoly.cards.ChanceCard;
import ca.jonathanfritz.funopoly.cards.Deck;
import ca.jonathanfritz.funopoly.tiles.Property.Deed;
import ca.jonathanfritz.funopoly.tiles.Railroad.Line;

public class Chance extends Tile {

	final Deck<ChanceCard> cards;

	private static final Logger log = LoggerFactory.getLogger(Chance.class);

	public Chance(Deck<ChanceCard> cards) {
		this.cards = cards;
	}

	@Override
	public void land(Player player, DiceRollResult diceRoll, Board board) {
		final ChanceCard card = cards.draw();
		log.info("{} draws a Chance card: {}", player, card);

		switch (card) {
			case ADVANCE_BOARDWALK:
				board.movePlayerTo(player, diceRoll, Deed.BOARDWALK);
				break;
			case ADVANCE_GO:
				board.movePlayerTo(player, diceRoll, Type.GO);
				break;
			case ADVANCE_ILLINOIS:
				board.movePlayerTo(player, diceRoll, Deed.ILLINOIS_AVENUE);
				break;
			case ADVANCE_JAIL:
				player.setInJail(true);
				board.movePlayerTo(player, diceRoll, Type.JAIL);
				break;
			case ADVANCE_RAILROAD:
				// TODO: pay owner twice the rental to which he/she is otherwise entitled
				board.movePlayerTo(player, diceRoll, Type.RAILROAD);
				break;
			case ADVANCE_READING_RAILROAD:
				board.movePlayerTo(player, diceRoll, Line.READING);
				break;
			case ADVANCE_ST_CHARLES:
				board.movePlayerTo(player, diceRoll, Deed.ST_CHARLES_PLACE);
				break;
			case ADVANCE_UTILITY:
				// TODO: if owned, throw dice and pay owner a total ten times the amount thrown
				board.movePlayerTo(player, diceRoll, Type.UTILITY);
				break;
			case BANK_DIVIDEND:
				player.grant(50);
				break;
			case ELECTED_CHAIRMAN:
				// pay each player $50
				player.debit((board.getNumPlayers() - 1) * 50);
				board.grantPlayers(50, player);
				break;
			case GET_OUT_OF_JAIL_FREE:
				// can't possibly have gotten the card if it is unavailable
				player.grantGetOutOfJailFreeCard();
				cards.setGetOutOfJailFreeCardAvailable(false);
				break;
			case GO_BACK:
				board.movePlayerNumSpaces(player, diceRoll, -3);
				break;
			case LOAN_MATURES:
				player.grant(150);
				break;
			case POOR_TAX:
				player.grant(15);
				break;
			case REPAIRS:
				board.streetRepairs(player, 25, 100);
				break;
		}
	}

	@Override
	public Type getType() {
		return Type.CHANCE;
	}

}
