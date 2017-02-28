package ca.jonathanfritz.funopoly.tiles;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Player;

public class Railroad extends OwnableTile {

	private final Line line;

	public Railroad(Line line) {
		super(line.name, line.price);
		this.line = line;
	}

	@Override
	public void land(Player player, Board board) {
		// do nothing - this is handled in Board
		System.out.println(player.toString() + " lands on " + line.name);
	}

	public Line getLine() {
		return line;
	}

	@Override
	public Type getType() {
		return Type.RAILROAD;
	}

	public enum Line {
		READING("Reading Railroad", 200),
		PENNSYLVANIA("Pennsylvania Railroad", 200),
		BO("B&O Railroad", 200),
		SHORTLINE("Shortline Railroad", 200);

		private final String name;
		private final int price;

		private Line(String name, int price) {
			this.name = name;
			this.price = price;
		}
	}
}