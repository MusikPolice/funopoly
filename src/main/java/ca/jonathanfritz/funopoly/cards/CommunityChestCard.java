package ca.jonathanfritz.funopoly.cards;

/**
 * Type of card
 * List reference at http://monopoly.wikia.com/wiki/Community_Chest
 */
public enum CommunityChestCard implements Card {
	ADVANCE_GO("Advance to Go (Collect $200)"),
	BANK_ERROR("Bank error in your favor – Collect $200"),
	DOCTOR_FEE("Doctor's fees – Pay $50"),
	STOCK_SALE("From sale of stock you get $50"),
	GET_OUT_OF_JAIL_FREE("Get Out of Jail Free – This card may be kept until needed or sold", true),
	ADVANCE_JAIL("Go to Jail – Go directly to Jail – Do not pass Go, do not collect $200"),
	GRAND_OPERA("Grand Opera Night – Collect $50 from every player for opening night seats"),
	HOLIDAY_FUND("Holiday Fund matures - Receive $100"),
	TAX_REFUND("Income tax refund – Collect $20"),
	LIFE_INSURANCE("Life insurance matures – Collect $100"),
	HOSPITAL_FEES("Pay hospital fees of $100"),
	SCHOOL_FEES("Pay school fees of $150"),
	SERVICES_RENDERED("Receive $25 for services rendered"),
	STREET_REPAIRS("You are assessed for street repairs – $40 per house – $115 per hotel"),
	BEAUTY_CONTEST("You have won second prize in a beauty contest – Collect $10"),
	INHERIT("You inherit $100");

	private final String text;
	private boolean isGetOutOfJailFreeCard = false;

	private CommunityChestCard(String text) {
		this.text = text;
	}

	private CommunityChestCard(String text, boolean isGetOutOfJailFreeCard) {
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
