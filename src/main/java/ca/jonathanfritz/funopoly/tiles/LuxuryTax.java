package ca.jonathanfritz.funopoly.tiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Dice.DiceRollResult;
import ca.jonathanfritz.funopoly.Player;

public class LuxuryTax extends Tile {

	private static final Logger log = LoggerFactory.getLogger(LuxuryTax.class);

	@Override
	public void land(Player player, DiceRollResult diceRoll, Board board) {
		player.debit(100);
		log.info("{} pays $100 in luxury tax", player);
	}

	@Override
	public Type getType() {
		return Type.LUXURY_TAX;
	}
}