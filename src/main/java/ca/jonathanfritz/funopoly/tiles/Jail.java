package ca.jonathanfritz.funopoly.tiles;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Player;

public class Jail extends Tile {

	@Override
	public void land(Player player, Board board) {
		// do nothing - this is handled in Board
		if (player.isInJail()) {
			System.out.println(player.toString() + " is in Jail");
		} else {
			System.out.println(player.toString() + " is just visiting the Jail");
		}
	}

	@Override
	public Type getType() {
		return Type.JAIL;
	}

}
