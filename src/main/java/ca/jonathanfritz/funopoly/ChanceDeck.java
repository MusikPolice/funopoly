package ca.jonathanfritz.funopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChanceDeck {

	private final List<ChanceCard> cards = new ArrayList<>();
	private int pointer = 0;

	private boolean getOutOfJailFreeCardAvailable = true;

	public ChanceDeck() {
		cards.add(ChanceCard.ADVANCE_GO);
		cards.add(ChanceCard.ADVANCE_ILLINOIS);
		cards.add(ChanceCard.ADVANCE_ST_CHARLES);
		cards.add(ChanceCard.ADVANCE_UTILITY);
		cards.add(ChanceCard.ADVANCE_RAILROAD);
		cards.add(ChanceCard.ADVANCE_RAILROAD);
		cards.add(ChanceCard.BANK_DIVIDEND);
		cards.add(ChanceCard.GET_OUT_OF_JAIL_FREE);
		cards.add(ChanceCard.GO_BACK);
		cards.add(ChanceCard.ADVANCE_JAIL);
		cards.add(ChanceCard.REPAIRS);
		cards.add(ChanceCard.POOR_TAX);
		cards.add(ChanceCard.ADVANCE_READING_RAILROAD);
		cards.add(ChanceCard.ADVANCE_BOARDWALK);
		cards.add(ChanceCard.ELECTED_CHAIRMAN);
		cards.add(ChanceCard.LOAN_MATURES);

		Collections.shuffle(cards);
	}

	public ChanceCard draw() {
		if (pointer == cards.size()) {
			Collections.shuffle(cards);
			pointer = 0;
		}

		final ChanceCard card = cards.get(pointer);
		pointer++;

		// if somebody has the get out of jail free card, re-draw
		if (card == ChanceCard.GET_OUT_OF_JAIL_FREE && !getOutOfJailFreeCardAvailable) {
			return draw();
		}

		return card;
	}

	public void setGetOutOfJailFreeCardAvailable(boolean getOutOfJailFreeCardAvailable) {
		this.getOutOfJailFreeCardAvailable = getOutOfJailFreeCardAvailable;
	}

	/**
	 * Type of card
	 * List reference at http://monopoly.wikia.com/wiki/Chance
	 */
	public enum ChanceCard {
		ADVANCE_GO("Advance to Go (Collect $200)"),
		ADVANCE_ILLINOIS("Advance to Illinois Ave. - If you pass Go, collect $200"),
		ADVANCE_ST_CHARLES("Advance to St. Charles Place – If you pass Go, collect $200"),
		ADVANCE_UTILITY("Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times the amount thrown"),
		ADVANCE_RAILROAD("Advance token to the nearest Railroad and pay owner twice the rental to which he/she is otherwise entitled. If Railroad is unowned, you may buy it from the Bank"),
		BANK_DIVIDEND("Bank pays you dividend of $50"),
		GET_OUT_OF_JAIL_FREE("Get out of Jail Free – This card may be kept until needed, or traded/sold"),
		GO_BACK("Go Back 3 Spaces"),
		ADVANCE_JAIL("Go to Jail – Go directly to Jail – Do not pass Go, do not collect $200"),
		REPAIRS("Make general repairs on all your property – For each house pay $25 – For each hotel $100"),
		POOR_TAX("Pay poor tax of $15"),
		ADVANCE_READING_RAILROAD("Take a trip to Reading Railroad – If you pass Go, collect $200"),
		ADVANCE_BOARDWALK("Take a walk on the Boardwalk – Advance token to Boardwalk"),
		ELECTED_CHAIRMAN("You have been elected Chairman of the Board – Pay each player $50"),
		LOAN_MATURES("Your building and loan matures – Collect $150");

		private final String text;

		private ChanceCard(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}
}
