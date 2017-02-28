package ca.jonathanfritz.funopoly.tiles;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.CommunityChestDeck;
import ca.jonathanfritz.funopoly.CommunityChestDeck.CommunityChestCard;
import ca.jonathanfritz.funopoly.Player;

public class CommunityChest implements Tile {

	private final CommunityChestDeck cards;

	public CommunityChest(CommunityChestDeck cards) {
		this.cards = cards;
	}

	@Override
	public void land(Player player, Board board) {
		final CommunityChestCard card = cards.draw();
		System.out.println(player.toString() + " draws a Community Chest card: " + card.toString());

		switch (card) {
			case ADVANCE_GO:
				board.movePlayerTo(player, Type.GO);
				break;
			case ADVANCE_JAIL:
				player.setInJail(true);
				board.movePlayerTo(player, Type.JAIL);
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
			case HOLIDAY_FUND:
				player.grant(100);
				break;
			case HOSPITAL_FEES:
				player.debit(100);
				break;
			case INHERIT:
				player.grant(100);
				break;
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
