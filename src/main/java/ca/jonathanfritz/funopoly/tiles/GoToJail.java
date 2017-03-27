package ca.jonathanfritz.funopoly.tiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Dice.DiceRollResult;
import ca.jonathanfritz.funopoly.Player;

public class GoToJail extends Tile {

	private static final Logger log = LoggerFactory.getLogger(GoToJail.class);

	@Override
	public void land(Player player, DiceRollResult diceRoll, Board board) {
		log.info("{} lands on Go to Jail", player);
		player.setInJail(true);
		board.movePlayerTo(player, diceRoll, Type.JAIL);
	}

	@Override
	public Type getType() {
		return Type.GO_TO_JAIL;
	}
}
