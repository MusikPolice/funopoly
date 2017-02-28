package ca.jonathanfritz.funopoly.tiles;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Player;

public class GoToJail implements Tile {

	@Override
	public void land(Player player, Board board) {
		System.out.println(player.toString() + " lands on Go to Jail");
		player.setInJail(true);
		board.movePlayerTo(player, Type.JAIL);
	}

	@Override
	public Type getType() {
		return Type.GO_TO_JAIL;
	}
}
