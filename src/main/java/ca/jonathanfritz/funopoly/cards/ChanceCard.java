package ca.jonathanfritz.funopoly.cards;

/**
 * Type of card
 * List reference at http://monopoly.wikia.com/wiki/Chance
 */
public enum ChanceCard implements Card {
	ADVANCE_GO("Advance to Go (Collect $200)"),
	ADVANCE_ILLINOIS("Advance to Illinois Ave. - If you pass Go, collect $200"),
	ADVANCE_ST_CHARLES("Advance to St. Charles Place – If you pass Go, collect $200"),
	ADVANCE_UTILITY(
	        "Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times the amount thrown"),
	ADVANCE_RAILROAD(
	        "Advance token to the nearest Railroad and pay owner twice the rental to which he/she is otherwise entitled. If Railroad is unowned, you may buy it from the Bank"),
	BANK_DIVIDEND("Bank pays you dividend of $50"),
	GET_OUT_OF_JAIL_FREE("Get out of Jail Free – This card may be kept until needed, or traded/sold", true),
	GO_BACK("Go Back 3 Spaces"),
	ADVANCE_JAIL("Go to Jail – Go directly to Jail – Do not pass Go, do not collect $200"),
	REPAIRS("Make general repairs on all your property – For each house pay $25 – For each hotel $100"),
	POOR_TAX("Pay poor tax of $15"),
	ADVANCE_READING_RAILROAD("Take a trip to Reading Railroad – If you pass Go, collect $200"),
	ADVANCE_BOARDWALK("Take a walk on the Boardwalk – Advance token to Boardwalk"),
	ELECTED_CHAIRMAN("You have been elected Chairman of the Board – Pay each player $50"),
	LOAN_MATURES("Your building and loan matures – Collect $150");

	private final String text;
	private boolean isGetOutOfJailFreeCard = false;

	private ChanceCard(String text) {
		this.text = text;
	}

	private ChanceCard(String text, boolean isGetOutOfJailFreeCard) {
		this(text);
		this.isGetOutOfJailFreeCard = isGetOutOfJailFreeCard;
	}

	@Override
	public boolean isGetOutOfJailFreeCard() {
		return isGetOutOfJailFreeCard;
	}

	@Override
	public String toString() {
		return text;
	}
}
