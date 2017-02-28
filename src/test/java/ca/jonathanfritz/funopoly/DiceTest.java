package ca.jonathanfritz.funopoly;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import ca.jonathanfritz.funopoly.Dice.DiceRollResult;

public class DiceTest {

	@Test
	public void lotsOfRollsTest() {
		final Dice dice = new Dice();
		for (int i = 0; i < 1000; i++) {
			final DiceRollResult result = dice.roll();
			Assert.assertTrue(result.value > 0);
			Assert.assertTrue(result.value < 13);

			if (result.isDoubles) {
				Assert.assertTrue(result.value % 2 == 0);
			}
		}
	}

	@Test
	public void expectedSequenceTest() {
		final Dice dice = new Dice(0);
		final int[] expectedSequence = new int[] { 6, 8, 12, 10, 7, 12, 11, 9, 9, 9, 3, 9, 8, 9, 7, 12, 12, 6, 10, 9,
		        5, 9, 9, 10, 6, 6, 4, 9, 11, 6, 11, 3, 5, 5, 6, 8, 12, 4, 7, 7, 6, 6, 5, 4, 6, 9, 2, 5, 5, 8, 7, 12, 6,
		        8, 3, 11, 7, 4, 3, 5, 7, 5, 8, 8, 6, 7, 10, 7, 9, 9, 8, 12, 7, 7, 12, 7, 12, 4, 8, 7, 10, 7, 12, 11,
		        11, 6, 7, 4, 6, 3, 10, 11, 11, 5, 5, 3, 8, 5, 8, 6 };

		for (int i = 0; i < 100; i++) {
			Assert.assertThat(dice.roll().value, IsEqual.equalTo(expectedSequence[i]));
		}
	}
}
