package ca.jonathanfritz.funopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommunityChestDeck {

	private final List<CommunityChestCard> cards = new ArrayList<>();
	private int pointer = 0;

	private boolean getOutOfJailFreeCardAvailable = true;

	public CommunityChestDeck() {
		cards.add(CommunityChestCard.ADVANCE_GO);
		cards.add(CommunityChestCard.BANK_ERROR);
		cards.add(CommunityChestCard.DOCTOR_FEE);
		cards.add(CommunityChestCard.STOCK_SALE);
		cards.add(CommunityChestCard.GET_OUT_OF_JAIL_FREE);
		cards.add(CommunityChestCard.ADVANCE_JAIL);
		cards.add(CommunityChestCard.GRAND_OPERA);
		cards.add(CommunityChestCard.HOLIDAY_FUND);
		cards.add(CommunityChestCard.TAX_REFUND);
		cards.add(CommunityChestCard.LIFE_INSURANCE);
		cards.add(CommunityChestCard.HOSPITAL_FEES);
		cards.add(CommunityChestCard.SCHOOL_FEES);
		cards.add(CommunityChestCard.SERVICES_RENDERED);
		cards.add(CommunityChestCard.STREET_REPAIRS);
		cards.add(CommunityChestCard.BEAUTY_CONTEST);
		cards.add(CommunityChestCard.INHERIT);

		Collections.shuffle(cards);
	}

	public CommunityChestCard draw() {
		if (pointer == cards.size()) {
			Collections.shuffle(cards);
			pointer = 0;
		}

		final CommunityChestCard card = cards.get(pointer);
		pointer++;

		// if somebody has the get out of jail free card, re-draw
		if (card == CommunityChestCard.GET_OUT_OF_JAIL_FREE && !getOutOfJailFreeCardAvailable) {
			return draw();
		}

		return card;
	}

	public void setGetOutOfJailFreeCardAvailable(boolean getOutOfJailFreeCardAvailable) {
		this.getOutOfJailFreeCardAvailable = getOutOfJailFreeCardAvailable;
	}

	/**
	 * Type of card
	 * List reference at http://monopoly.wikia.com/wiki/Community_Chest
	 */
	public enum CommunityChestCard {
		ADVANCE_GO("Advance to Go (Collect $200)"),
		BANK_ERROR("Bank error in your favor – Collect $200"),
		DOCTOR_FEE("Doctor's fees – Pay $50"),
		STOCK_SALE("From sale of stock you get $50"),
		GET_OUT_OF_JAIL_FREE("Get Out of Jail Free – This card may be kept until needed or sold"),
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

		private CommunityChestCard(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}
}
