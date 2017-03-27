package ca.jonathanfritz.funopoly.tiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Dice.DiceRollResult;
import ca.jonathanfritz.funopoly.Player;

public class IncomeTax extends Tile {

	private static final Logger log = LoggerFactory.getLogger(IncomeTax.class);

	@Override
	public void land(Player player, DiceRollResult diceRoll, Board board) {
		// TODO: include property value in calculation
		final int fifteenPercent = (int) Math.floor(player.getBalance() * 0.15);
		final int amount = Math.max(fifteenPercent, 150);
		player.debit(amount);

		log.info("{} pays ${} in income tax", player, amount);
	}

	@Override
	public Type getType() {
		return Type.INCOME_TAX;
	}
}
