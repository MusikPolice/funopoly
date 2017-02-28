package ca.jonathanfritz.funopoly.tiles;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Player;
import ca.jonathanfritz.funopoly.cards.ChanceDeck;
import ca.jonathanfritz.funopoly.cards.ChanceDeck.ChanceCard;
import ca.jonathanfritz.funopoly.tiles.Property.Deed;
import ca.jonathanfritz.funopoly.tiles.Railroad.Line;

public class Chance extends Tile {

	private final ChanceDeck cards;

	public Chance(ChanceDeck cards) {
		this.cards = cards;
	}

	@Override
	public void land(Player player, Board board) {
		final ChanceCard card = cards.draw();
		System.out.println(player.toString() + " draws a Chance card: " + card.toString());

		switch (card) {
			case ADVANCE_BOARDWALK:
				board.movePlayerTo(player, Deed.BOARDWALK);
				break;
			case ADVANCE_GO:
				board.movePlayerTo(player, Type.GO);
				break;
			case ADVANCE_ILLINOIS:
				board.movePlayerTo(player, Deed.ILLINOIS_AVENUE);
				break;
			case ADVANCE_JAIL:
				player.setInJail(true);
				board.movePlayerTo(player, Type.JAIL);
				break;
			case ADVANCE_RAILROAD:
				// TODO: pay owner twice the rental to which he/she is otherwise entitled
				board.movePlayerTo(player, Type.RAILROAD);
				break;
			case ADVANCE_READING_RAILROAD:
				board.movePlayerTo(player, Line.READING);
				break;
			case ADVANCE_ST_CHARLES:
				board.movePlayerTo(player, Deed.ST_CHARLES_PLACE);
				break;
			case ADVANCE_UTILITY:
				// TODO: if owned, throw dice and pay owner a total ten times the amount thrown
				board.movePlayerTo(player, Type.UTILITY);
				break;
			case BANK_DIVIDEND:
				player.grant(50);
				break;
			case ELECTED_CHAIRMAN:
				// pay each player $50
				player.debit((board.getNumPlayers() - 1) * 50);
				board.grantPlayers(50, player);
			case GET_OUT_OF_JAIL_FREE:
				// can't possibly have gotten the card if it is unavailable
				player.grantGetOutOfJailFreeCard();
				cards.setGetOutOfJailFreeCardAvailable(false);
				break;
			case GO_BACK:
				board.movePlayerNumSpaces(player, -3);
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
