package ca.jonathanfritz.funopoly.tiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Dice.DiceRollResult;
import ca.jonathanfritz.funopoly.Player;

public class FreeParking extends Tile {

	private static final Logger log = LoggerFactory.getLogger(FreeParking.class);

	@Override
	public void land(Player player, DiceRollResult diceRoll, Board board) {
		// do nothing- free parking money is for people who like to play for 16 hours
		log.info("{} lands on Free Parking", player);
	}

	@Override
	public Type getType() {
		return Type.FREE_PARKING;
	}

}
