package ca.jonathanfritz.funopoly.tiles;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Player;

public class IncomeTax implements Tile {

	@Override
	public void land(Player player, Board board) {
		// TODO: include property value in calculation
		final int fifteenPercent = (int) Math.floor(player.getBalance() * 0.15);
		final int amount = Math.max(fifteenPercent, 150);
		player.debit(amount);

		System.out.println(player.toString() + " pays $" + String.valueOf(amount) + " in income tax");
	}

	@Override
	public Type getType() {
		return Type.INCOME_TAX;
	}

}
