package ca.jonathanfritz.funopoly.tiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Dice.DiceRollResult;
import ca.jonathanfritz.funopoly.Player;

public class Go extends Tile {

	private static final Logger log = LoggerFactory.getLogger(Go.class);

	@Override
	public void land(Player player, DiceRollResult diceRoll, Board board) {
		// do nothing - we grant $200 when player tile pointer resets to zero
		log.info("{} lands on Go", player);
	}

	@Override
	public Type getType() {
		return Type.GO;
	}
}
