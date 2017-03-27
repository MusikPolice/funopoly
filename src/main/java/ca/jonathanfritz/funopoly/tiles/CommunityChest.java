package ca.jonathanfritz.funopoly.tiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Dice.DiceRollResult;
import ca.jonathanfritz.funopoly.Player;
import ca.jonathanfritz.funopoly.cards.CommunityChestCard;
import ca.jonathanfritz.funopoly.cards.Deck;

public class CommunityChest extends Tile {

	final Deck<CommunityChestCard> cards;

	private static final Logger log = LoggerFactory.getLogger(CommunityChest.class);

	public CommunityChest(Deck<CommunityChestCard> cards) {
		this.cards = cards;
	}

	@Override
	public void land(Player player, DiceRollResult diceRoll, Board board) {
		final CommunityChestCard card = cards.draw();
		log.info("{} draws a Community Chest card: {}", player, card);

		switch (card) {
			case ADVANCE_GO:
				board.movePlayerTo(player, diceRoll, Type.GO);
				break;
			case ADVANCE_JAIL:
				player.setInJail(true);
				board.movePlayerTo(player, diceRoll, Type.JAIL);
				break;
			case BANK_ERROR:
				player.grant(200);
				break;
			case BEAUTY_CONTEST:
				player.grant(10);
				break;
			case SERVICES_RENDERED:
				player.grant(25);
				break;
			case DOCTOR_FEE:
				player.debit(50);
				break;
			case GET_OUT_OF_JAIL_FREE:
				// can't possibly have gotten the card if it is unavailable
				player.grantGetOutOfJailFreeCard();
				cards.setGetOutOfJailFreeCardAvailable(false);
				break;
			case GRAND_OPERA:
				// collect $50 from each player
				player.grant((board.getNumPlayers() - 1) * 50);
				board.debitPlayers(50, player);
				break;
			case HOSPITAL_FEES:
				player.debit(100);
				break;
			case HOLIDAY_FUND:
			case INHERIT:
			case LIFE_INSURANCE:
				player.grant(100);
				break;
			case SCHOOL_FEES:
				player.debit(150);
				break;
			case STOCK_SALE:
				player.grant(50);
				break;
			case STREET_REPAIRS:
				board.streetRepairs(player, 40, 115);
				break;
			case TAX_REFUND:
				player.grant(20);
				break;
		}
	}

	@Override
	public Type getType() {
		return Type.COMMUNITY_CHEST;
	}
}