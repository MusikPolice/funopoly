package ca.jonathanfritz.funopoly;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import ca.jonathanfritz.funopoly.CommunityChestDeck.CommunityChestCard;

public class CommunityChestDeckTest {

	@Test
	public void getAllCardsTest() {
		final Map<CommunityChestCard, Integer> cardCount = new HashMap<>();

		// draw all cards
		final CommunityChestDeck deck = new CommunityChestDeck();
		for (int i = 0; i < CommunityChestCard.values().length; i++) {
			// there are 15 unique CommunityChest cards
			final CommunityChestCard card = deck.draw();
			if (cardCount.containsKey(card)) {
				cardCount.put(card, cardCount.get(card) + 1);
			} else {
				cardCount.put(card, 1);
			}
		}

		// ensure we got the expected cards
		Assert.assertThat(cardCount.size(), IsEqual.equalTo(CommunityChestCard.values().length));
		for (final Entry<CommunityChestCard, Integer> entry : cardCount.entrySet()) {
			Assert.assertThat(entry.getValue(), IsEqual.equalTo(1));
		}
	}

	@Test
	public void pidgeonholePrincipleTest() {
		final Map<CommunityChestCard, Integer> cardCount = new HashMap<>();

		// draw all cards plus one, causing the deck to shuffle
		final CommunityChestDeck deck = new CommunityChestDeck();
		for (int i = 0; i < CommunityChestCard.values().length + 1; i++) {
			// there are 15 unique CommunityChest cards
			final CommunityChestCard card = deck.draw();
			if (cardCount.containsKey(card)) {
				cardCount.put(card, cardCount.get(card) + 1);
			} else {
				cardCount.put(card, 1);
			}
		}

		// because we pulled one more card than is in the deck, the deck must have been shuffled at some point, so we
		// will have pulled or two exactly one card
		Assert.assertThat(cardCount.size(), IsEqual.equalTo(CommunityChestCard.values().length));
		Assert.assertThat(cardCount.entrySet().stream()
		        .filter(entry -> entry.getValue() == 2)
		        .count(), IsEqual.equalTo(1L));
	}

	@Test
	public void getOutOfJailFreeCardTest() {
		final Map<CommunityChestCard, Integer> cardCount = new HashMap<>();

		// make the goojf card unavailable
		final CommunityChestDeck deck = new CommunityChestDeck();
		deck.setGetOutOfJailFreeCardAvailable(false);

		// draw all cards - we've removed the goojf card so we have to draw one less cards than the number in the deck
		for (int i = 0; i < CommunityChestCard.values().length - 1; i++) {
			final CommunityChestCard card = deck.draw();
			if (cardCount.containsKey(card)) {
				cardCount.put(card, cardCount.get(card) + 1);
			} else {
				cardCount.put(card, 1);
			}
		}

		// ensure we got the expected cards
		Assert.assertThat(cardCount.size(), IsEqual.equalTo(CommunityChestCard.values().length - 1));
		for (final Entry<CommunityChestCard, Integer> entry : cardCount.entrySet()) {
			if (entry.getKey() == CommunityChestCard.GET_OUT_OF_JAIL_FREE) {
				Assert.assertThat(entry.getValue(), IsEqual.equalTo(0));
			} else {
				Assert.assertThat(entry.getValue(), IsEqual.equalTo(1));
			}
		}
	}
}
