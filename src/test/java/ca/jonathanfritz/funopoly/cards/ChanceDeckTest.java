package ca.jonathanfritz.funopoly.cards;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import ca.jonathanfritz.funopoly.cards.ChanceCard;
import ca.jonathanfritz.funopoly.cards.Deck;

public class ChanceDeckTest {

	@Test
	public void getAllCardsTest() {
		final Map<ChanceCard, Integer> cardCount = new HashMap<>();

		// draw all cards
		final Deck<ChanceCard> deck = Deck.chance();
		for (int i = 0; i < ChanceCard.values().length + 1; i++) {
			// there are 16 chance cards. two are advance to nearest railroad. others are unique
			final ChanceCard card = deck.draw();
			if (cardCount.containsKey(card)) {
				cardCount.put(card, cardCount.get(card) + 1);
			} else {
				cardCount.put(card, 1);
			}
		}

		// ensure we got the expected cards
		Assert.assertThat(cardCount.size(), IsEqual.equalTo(ChanceCard.values().length));
		for (final Entry<ChanceCard, Integer> entry : cardCount.entrySet()) {
			if (entry.getKey() == ChanceCard.ADVANCE_RAILROAD) {
				Assert.assertThat(entry.getValue(), IsEqual.equalTo(2));
			} else {
				Assert.assertThat(entry.getValue(), IsEqual.equalTo(1));
			}
		}
	}

	@Test
	public void pidgeonholePrincipleTest() {
		final Map<ChanceCard, Integer> cardCount = new HashMap<>();

		// draw all cards plus one, causing the deck to shuffle
		final Deck<ChanceCard> deck = Deck.chance();
		for (int i = 0; i < ChanceCard.values().length + 2; i++) {
			// there are 16 chance cards. two are advance to nearest railroad. others are unique
			final ChanceCard card = deck.draw();
			if (cardCount.containsKey(card)) {
				cardCount.put(card, cardCount.get(card) + 1);
			} else {
				cardCount.put(card, 1);
			}
		}

		// because we pulled one more card than is in the deck, the deck must have been shuffled at some point, so we
		// will either have pulled three advance to next railroad cards, or two of some other card
		Assert.assertThat(cardCount.size(), IsEqual.equalTo(ChanceCard.values().length));
		Assert.assertThat(cardCount.entrySet().stream()
		        .filter(entry -> (entry.getKey() == ChanceCard.ADVANCE_RAILROAD && entry.getValue() == 3) ||
		                (entry.getKey() != ChanceCard.ADVANCE_RAILROAD && entry.getValue() == 2))
		        .count(), IsEqual.equalTo(1L));
	}

	@Test
	public void getOutOfJailFreeCardTest() {
		final Map<ChanceCard, Integer> cardCount = new HashMap<>();

		// make the goojf card unavailable
		final Deck<ChanceCard> deck = Deck.chance();
		deck.setGetOutOfJailFreeCardAvailable(false);

		// draw all cards - we've removed the goojf card so we have to draw one less cards than the number in the deck
		for (int i = 0; i < ChanceCard.values().length; i++) {
			final ChanceCard card = deck.draw();
			if (cardCount.containsKey(card)) {
				cardCount.put(card, cardCount.get(card) + 1);
			} else {
				cardCount.put(card, 1);
			}
		}

		// ensure we got the expected cards
		Assert.assertThat(cardCount.size(), IsEqual.equalTo(ChanceCard.values().length - 1));
		for (final Entry<ChanceCard, Integer> entry : cardCount.entrySet()) {
			if (entry.getKey() == ChanceCard.ADVANCE_RAILROAD) {
				Assert.assertThat(entry.getValue(), IsEqual.equalTo(2));
			} else if (entry.getKey() == ChanceCard.GET_OUT_OF_JAIL_FREE) {
				Assert.assertThat(entry.getValue(), IsEqual.equalTo(0));
			} else {
				Assert.assertThat(entry.getValue(), IsEqual.equalTo(1));
			}
		}
	}
}
