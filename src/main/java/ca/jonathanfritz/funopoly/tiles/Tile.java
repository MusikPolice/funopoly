package ca.jonathanfritz.funopoly.tiles;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Player;

public abstract class Tile {

	public abstract Type getType();

	/**
	 * Called when the specified player lands on the tile
	 * @param player the player that landed on the tile
	 */
	public abstract void land(Player player, Board board);

	public boolean isOwnable() {
		return getType() == Type.PROPERTY || getType() == Type.RAILROAD || getType() == Type.UTILITY;
	}

	public enum Type {
		GO,
		PROPERTY,
		COMMUNITY_CHEST,
		INCOME_TAX,
		RAILROAD,
		CHANCE,
		JAIL,
		UTILITY,
		FREE_PARKING,
		GO_TO_JAIL,
		LUXURY_TAX
	}
}
