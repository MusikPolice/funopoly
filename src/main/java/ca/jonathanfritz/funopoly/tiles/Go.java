package ca.jonathanfritz.funopoly.tiles;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Player;

public class Go extends Tile {

	@Override
	public void land(Player player, Board board) {
		// do nothing - we grant $200 when player tile pointer resets to zero
		System.out.println(player.toString() + " lands on Go");
	}

	@Override
	public Type getType() {
		return Type.GO;
	}
}
