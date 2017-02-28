package ca.jonathanfritz.funopoly.tiles;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Player;

public interface Tile {

	public Type getType();

	/**
	 * Called when the specified player lands on the tile
	 * @param player the player that landed on the tile
	 */
	public void land(Player player, Board board);

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
