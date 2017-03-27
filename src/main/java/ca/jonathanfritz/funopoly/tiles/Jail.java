package ca.jonathanfritz.funopoly.tiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Dice.DiceRollResult;
import ca.jonathanfritz.funopoly.Player;

public class Jail extends Tile {

	private static final Logger log = LoggerFactory.getLogger(Jail.class);

	@Override
	public void land(Player player, DiceRollResult diceRoll, Board board) {
		// do nothing - this is handled in Board
		if (player.isInJail()) {
			log.info("{} is in Jail", player);
		} else {
			log.info("{} is just visiting the Jail", player);
		}
	}

	@Override
	public Type getType() {
		return Type.JAIL;
	}
}