package ca.jonathanfritz.funopoly.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck<E extends Card> {

	private List<E> cards = new ArrayList<>();
	private int pointer = 0;
	private boolean getOutOfJailFreeCardAvailable = true;

	public static Deck<CommunityChestCard> communityChest() {
		// the community chest deck has one of each card
		final List<CommunityChestCard> cards = Arrays.asList(CommunityChestCard.values());
		return new Deck<CommunityChestCard>(cards);
	}

	public static Deck<ChanceCard> chance() {
		// the chance deck has one of each card, except for "advance to nearest railroad," of which there are two
		final List<ChanceCard> cards = new ArrayList<>();
		cards.addAll(Arrays.asList(ChanceCard.values()));
		cards.add(ChanceCard.ADVANCE_RAILROAD);
		return new Deck<ChanceCard>(cards);
	}

	private Deck(List<E> cards) {
		this.cards = cards;
	}

	public E draw() {
		if (pointer == cards.size()) {
			Collections.shuffle(cards);
			pointer = 0;
		}

		final E card = cards.get(pointer);
		pointer++;

		// if somebody has the get out of jail free card, re-draw
		if (card.isGetOutOfJailFreeCard() && !getOutOfJailFreeCardAvailable) {
			return draw();
		}

		return card;
	}

	public void setGetOutOfJailFreeCardAvailable(boolean getOutOfJailFreeCardAvailable) {
		this.getOutOfJailFreeCardAvailable = getOutOfJailFreeCardAvailable;
	}
}
