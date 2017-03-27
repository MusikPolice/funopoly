package ca.jonathanfritz.funopoly.tiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Dice.DiceRollResult;
import ca.jonathanfritz.funopoly.Player;

public class Utility extends OwnableTile {

	public static final int NUM_UTILITIES = 2;

	private static final Logger log = LoggerFactory.getLogger(Utility.class);

	public Utility(String name, int price) {
		super(name, price);
	}

	@Override
	public void land(Player player, DiceRollResult diceRoll, Board board) {
		// do nothing - this is handled in Board
		log.info("{} lands on {}", player, getName());
	}

	@Override
	public Type getType() {
		return Type.UTILITY;
	}
}
