package ca.jonathanfritz.funopoly.tiles;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Player;

public class FreeParking implements Tile {

	@Override
	public void land(Player player, Board board) {
		// do nothing- free parking money is for people who like to play for 16 hours
		System.out.println(player.toString() + " lands on Free Parking");
	}

	@Override
	public Type getType() {
		return Type.FREE_PARKING;
	}

}
