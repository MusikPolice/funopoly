package ca.jonathanfritz.funopoly.tiles;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Player;

public class Utility extends OwnableTile {

	public static int NUM_UTILITIES = 2;

	public Utility(String name, int price) {
		super(name, price);
	}

	@Override
	public void land(Player player, Board board) {
		// do nothing - this is handled in Board
		System.out.println(player.toString() + " lands on " + getName());
	}

	@Override
	public Type getType() {
		return Type.UTILITY;
	}
}
