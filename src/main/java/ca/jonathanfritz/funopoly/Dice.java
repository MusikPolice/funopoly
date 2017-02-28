package ca.jonathanfritz.funopoly;

import java.util.Random;

public class Dice {
	private final Random random;

	public Dice() {
		random = new Random();
	}

	public Dice(long seed) {
		random = new Random(seed);
	}

	public DiceRollResult roll() {
		final int die1 = random.nextInt(6) + 1;
		final int die2 = random.nextInt(6) + 1;
		return new DiceRollResult(die1 == die2, die1 + die2);
	}

	public class DiceRollResult {
		public final boolean isDoubles;
		public final int value;

		public DiceRollResult(boolean isDoubles, int value) {
			this.isDoubles = isDoubles;
			this.value = value;
		}
	}
}
