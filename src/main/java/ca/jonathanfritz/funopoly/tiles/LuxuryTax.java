package ca.jonathanfritz.funopoly.tiles;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Player;

public class LuxuryTax extends Tile {

	@Override
	public void land(Player player, Board board) {
		player.debit(100);
		System.out.println(player.toString() + " pays $100 in luxury tax");
	}

	@Override
	public Type getType() {
		return Type.LUXURY_TAX;
	}

}
