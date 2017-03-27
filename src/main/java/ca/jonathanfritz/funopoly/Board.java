package ca.jonathanfritz.funopoly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.jonathanfritz.funopoly.Dice.DiceRollResult;
import ca.jonathanfritz.funopoly.cards.ChanceCard;
import ca.jonathanfritz.funopoly.cards.CommunityChestCard;
import ca.jonathanfritz.funopoly.cards.Deck;
import ca.jonathanfritz.funopoly.tiles.Chance;
import ca.jonathanfritz.funopoly.tiles.CommunityChest;
import ca.jonathanfritz.funopoly.tiles.FreeParking;
import ca.jonathanfritz.funopoly.tiles.Go;
import ca.jonathanfritz.funopoly.tiles.GoToJail;
import ca.jonathanfritz.funopoly.tiles.IncomeTax;
import ca.jonathanfritz.funopoly.tiles.Jail;
import ca.jonathanfritz.funopoly.tiles.LuxuryTax;
import ca.jonathanfritz.funopoly.tiles.OwnableTile;
import ca.jonathanfritz.funopoly.tiles.Property;
import ca.jonathanfritz.funopoly.tiles.Property.Deed;
import ca.jonathanfritz.funopoly.tiles.Railroad;
import ca.jonathanfritz.funopoly.tiles.Railroad.Line;
import ca.jonathanfritz.funopoly.tiles.Tile;
import ca.jonathanfritz.funopoly.tiles.Tile.Type;
import ca.jonathanfritz.funopoly.tiles.Utility;

public class Board {
	private final Deck<ChanceCard> chanceDeck;
	private final Deck<CommunityChestCard> communityChestDeck;
	private final Map<Player, Integer> playerPositions;

	private final List<Tile> tiles = new ArrayList<>();

	private static final int GO_REWARD = 200;

	private static final Logger log = LoggerFactory.getLogger(Board.class);

	public Board(Deck<ChanceCard> chanceDeck, Deck<CommunityChestCard> communityChestDeck, List<Player> players) {
		this.chanceDeck = chanceDeck;
		this.communityChestDeck = communityChestDeck;

		// each player starts on Go
		playerPositions = new HashMap<>();
		players.forEach(p -> playerPositions.put(p, 0));

		setup();
	}

	/**
	 * Sets up the board
	 */
	private void setup() {
		tiles.add(new Go());
		tiles.add(new Property(Deed.MEDITERRANEAN_AVENUE));
		tiles.add(new CommunityChest(communityChestDeck));
		tiles.add(new Property(Deed.BALTIC_AVENUE));
		tiles.add(new IncomeTax());
		tiles.add(new Railroad(Line.READING));
		tiles.add(new Property(Deed.ORIENTAL_AVENUE));
		tiles.add(new Chance(chanceDeck));
		tiles.add(new Property(Deed.VERMONT_AVENUE));
		tiles.add(new Property(Deed.CONNECTICUT_AVENUE));
		tiles.add(new Jail());
		tiles.add(new Property(Deed.ST_CHARLES_PLACE));
		tiles.add(new Utility("Electric Company", 150));
		tiles.add(new Property(Deed.STATES_AVENUE));
		tiles.add(new Property(Deed.VIRGINIA_AVENUE));
		tiles.add(new Railroad(Line.PENNSYLVANIA));
		tiles.add(new Property(Deed.ST_JAMES_PLACE));
		tiles.add(new CommunityChest(communityChestDeck));
		tiles.add(new Property(Deed.TENNESSEE_AVENUE));
		tiles.add(new Property(Deed.NEW_YORK_AVENUE));
		tiles.add(new FreeParking());
		tiles.add(new Property(Deed.KENTUCKY_AVENUE));
		tiles.add(new Chance(chanceDeck));
		tiles.add(new Property(Deed.INDIANA_AVENUE));
		tiles.add(new Property(Deed.ILLINOIS_AVENUE));
		tiles.add(new Railroad(Line.BO));
		tiles.add(new Property(Deed.ATLANTIC_AVENUE));
		tiles.add(new Property(Deed.VENTNOR_AVENUE));
		tiles.add(new Utility("Water Works", 150));
		tiles.add(new Property(Deed.MARVIN_GARDENS));
		tiles.add(new GoToJail());
		tiles.add(new Property(Deed.PACIFIC_AVENUE));
		tiles.add(new Property(Deed.NORTH_CAROLINA_AVENUE));
		tiles.add(new CommunityChest(communityChestDeck));
		tiles.add(new Property(Deed.PENNSYLVANIA_AVENUE));
		tiles.add(new Railroad(Line.SHORTLINE));
		tiles.add(new Chance(chanceDeck));
		tiles.add(new Property(Deed.PARK_PLACE));
		tiles.add(new LuxuryTax());
		tiles.add(new Property(Deed.BOARDWALK));
	}

	public Tile getPlayerPosition(Player player) {
		return tiles.get(playerPositions.get(player));
	}

	/**
	 * Moves the player directly to the specified tile and executes any actions associated with that tile. Does not
	 * handle passing go
	 * @param player the player to move
	 * @param tile the tile to move the player to
	 */
	private void movePlayerTo(Player player, DiceRollResult diceRoll, Tile tile) {
		playerPositions.put(player, tiles.indexOf(tile));
		tile.land(player, diceRoll, this);
		if (!tile.isOwnable()) {
			return;
		}

		final OwnableTile ownableTile = (OwnableTile) tile;
		if (ownableTile.isOwned() && ownableTile.getOwner() != player) {
			log.info("{} is owned by {}", ownableTile.getName(), ownableTile.getOwner());
			payRent(player, diceRoll, tile);
			return;
		}

		if (player.willBuy(ownableTile)) {
			log.info("{} bought {}  for ${}", player, ownableTile.getName(), ownableTile.getPrice());
			player.debit(ownableTile.getPrice());
			ownableTile.setOwner(player);
		} else {
			// TODO: if other players decline to participate in auction, what does player pay for property?
			log.info("{} has declined to buy {}. It's auction time!", player, ownableTile.getName());
			doAuction(ownableTile);
		}
	}

	private void doAuction(OwnableTile ownableTile) {
		// auction
		int maxBid = 0;
		Player maxBidPlayer = null;
		boolean updated = true;

		// keep going until everybody stops increasing their bid
		while (updated) {
			updated = false;
			for (final Player bidder : playerPositions.keySet()) {
				if (bidder == maxBidPlayer) {
					// this player already has the maximum bid
					continue;
				}
				final int newBid = bidder.getBid(ownableTile, maxBid);
				if (newBid > maxBid) {
					maxBid = newBid;
					maxBidPlayer = bidder;
					updated = true;
					log.info("{} bids ${}", bidder, maxBid);
				}
			}
		}

		// max bid gets it
		if (maxBidPlayer != null) {
			log.info("{} buys {} at auction for ${}", maxBidPlayer, ownableTile.getName(), maxBid);
			maxBidPlayer.debit(maxBid);
			ownableTile.setOwner(maxBidPlayer);
		}
	}

	private void payRent(Player player, DiceRollResult diceRoll, Tile tile) {
		if (tile.getType() == Type.PROPERTY) {
			final Property property = (Property) tile;
			payRent(player, property);
		} else if (tile.getType() == Type.RAILROAD) {
			final Railroad railroad = (Railroad) tile;
			payRent(player, railroad);
		} else if (tile.getType() == Type.UTILITY) {
			final Utility utility = (Utility) tile;
			payRent(player, diceRoll, utility);
		}
	}

	private void payRent(Player player, Property property) {
		final boolean ownsColourGroup = tiles.stream().filter(t -> t.getType() == Type.PROPERTY).filter(t -> ((Property) t).getDeed().getColour() == property.getDeed().getColour()).allMatch(t -> ((Property) t).getOwner() == player);
		final int amount = property.getRent(ownsColourGroup);

		log.info("{} pays ${} in rent to {}", player, amount, property.getOwner());
		player.debit(amount);
		property.getOwner().grant(amount);
	}

	private void payRent(Player player, Railroad railroad) {
		final long numRailroads = tiles.stream().filter(t -> t.getType() == Type.RAILROAD).filter(t -> ((Railroad) t).getOwner() == railroad.getOwner()).count();
		final int amount = (int) Math.floor(25 * Math.pow(2, (double) numRailroads - 1));

		log.info("{} pays ${} in rent to {}", player, amount, railroad.getOwner());
		player.debit(amount);
		railroad.getOwner().grant(amount);
	}

	private void payRent(Player player, DiceRollResult diceRoll, Utility utility) {
		final long numUtilities = tiles.stream().filter(t -> t.getType() == Type.UTILITY).filter(t -> ((Utility) t).getOwner() == utility.getOwner()).count();

		// 4x dice roll if one utility is owned
		// 10x dice roll if both utilities are owned
		final int amount = diceRoll.value * numUtilities == 1 ? 4 : 10;

		log.info("{} pays ${} in rent to {}", player, amount, utility.getOwner());
		player.debit(amount);
		utility.getOwner().grant(amount);
	}

	/**
	 * Moves the player to the next tile of the specified {@link Type} on the board
	 * @param player the player to move
	 * @param type the type of tile to move to. If there is only one type of that tile on the board, such as
	 *            {@link Type#GO}, the player will be moved directly to that tile. If there is more than one tile with
	 *            that type on the board, such as {@link Type#RAILROAD}, the player will be moved to the closest tile of
	 *            that type. The player will always be moved in a clockwise direction.
	 */
	public void movePlayerTo(Player player, DiceRollResult diceRoll, Type type) {
		final int currentPosition = playerPositions.get(player);
		for (int i = currentPosition; i < currentPosition + tiles.size(); i++) {
			final Tile tile = tiles.get(i % tiles.size());
			if (tile.getType() == type) {
				if (i >= tiles.size() && type != Type.JAIL) {
					// passed go, not headed for jail, grant reward
					log.info("{} passed go, gets $200", player);
					player.grant(GO_REWARD);
				}
				movePlayerTo(player, diceRoll, tile);
				break;
			}
		}
	}

	/**
	 * Moves the player to the {@link Property} tile with the specified {@link Deed}
	 * @param player the player to move
	 * @param deed the deed of the property to move to
	 */
	public void movePlayerTo(Player player, DiceRollResult diceRoll, Deed deed) {
		final Optional<Tile> property = tiles.stream().filter(t -> t.getType() == Type.PROPERTY).filter(t -> ((Property) t).getDeed() == deed).findFirst();
		if (!property.isPresent()) {
			throw new IllegalStateException("Failed to find property " + deed.toString());
		}

		if (tiles.indexOf(property) < playerPositions.get(player)) {
			// new position is behind current position, player must pass go to reach it
			log.info("{} passed go, gets $200", player);
			player.grant(GO_REWARD);
		}

		movePlayerTo(player, diceRoll, property.get());
	}

	/**
	 * Moves the player to the {@link Railroad} tile with the specified {@link Line}
	 * @param player the player to move
	 * @param line the line of the railroad to move to
	 */
	public void movePlayerTo(Player player, DiceRollResult diceRoll, Line line) {
		final Optional<Tile> railroad = tiles.stream().filter(t -> t.getType() == Type.RAILROAD).filter(t -> ((Railroad) t).getLine() == line).findFirst();
		if (!railroad.isPresent()) {
			throw new IllegalStateException("Failed to find tile " + line.toString());
		}

		if (tiles.indexOf(railroad) < playerPositions.get(player)) {
			// new position is behind current position, player must pass go to reach it
			log.info("{} passed go, gets $200", player);
			player.grant(GO_REWARD);
		}

		movePlayerTo(player, diceRoll, railroad.get());
	}

	/**
	 * Moves the player left or right by the specified number of spaces.
	 * @param player the player to move
	 * @param diceRoll the roll of the dice. The player will be moved the value shown on the dice
	 */
	public void movePlayerNumSpaces(Player player, DiceRollResult diceRoll) {
		final int currentPosition = playerPositions.get(player);
		final int newPosition = currentPosition + diceRoll.value;
		if (diceRoll.value > 0 && newPosition >= tiles.size()) {
			// passed go in the counter clockwise direction
			log.info("{} passed go, gets $200", player);
			player.grant(GO_REWARD);
		}
		movePlayerTo(player, diceRoll, tiles.get(newPosition % tiles.size()));
	}

	/**
	 * Moves the player left or right by the specified number of spaces.
	 * @param player the player to move
	 * @param diceRoll the roll of the dice
	 * @param numSpaces the number of spaces to move the player
	 */
	public void movePlayerNumSpaces(Player player, DiceRollResult diceRoll, int numSpaces) {
		final int currentPosition = playerPositions.get(player);
		final int newPosition = currentPosition + numSpaces;
		if (numSpaces > 0 && newPosition >= tiles.size()) {
			// passed go in the counter clockwise direction
			log.info("{} passed go, gets $200", player);
			player.grant(GO_REWARD);
		}
		movePlayerTo(player, diceRoll, tiles.get(newPosition % tiles.size()));
	}

	public int getNumPlayers() {
		return playerPositions.size();
	}

	/**
	 * Grants the specified amount of money to each player
	 * @param amount the amount to grant
	 * @param exemptPlayer a player exempt from the reward. Set to null to grant reward to all players.
	 */
	public void grantPlayers(int amount, Player exemptPlayer) {
		for (final Player player : playerPositions.keySet()) {
			if (player == exemptPlayer) {
				continue;
			}
			player.grant(amount);
		}
	}

	/**
	 * Debits the specified amount of money from each player
	 * @param amount the amount to debit
	 * @param exemptPlayer a player exempt from the fee. Set to null to debit all players.
	 */
	public void debitPlayers(int amount, Player exemptPlayer) {
		for (final Player player : playerPositions.keySet()) {
			if (player == exemptPlayer) {
				continue;
			}
			player.debit(amount);
		}
	}

	/**
	 * Debits the player some amount per each house and hotel that they own
	 * @param player the player to debit
	 * @param amountPerHouse the amount to debit per house
	 * @param amountPerHotel the amount to debit per hotel
	 */
	public void streetRepairs(Player player, int amountPerHouse, int amountPerHotel) {
		final int numHouses = tiles.stream().filter(t -> t.getType() == Type.PROPERTY).filter(t -> ((Property) t).getOwner() == player).filter(t -> ((Property) t).getNumHouses() > 0).filter(t -> ((Property) t).getNumHouses() < 5).mapToInt(t -> ((Property) t).getNumHouses()).sum();
		final int numHotels = (int) tiles.stream().filter(t -> t.getType() == Type.PROPERTY).filter(t -> ((Property) t).getOwner() == player).filter(t -> ((Property) t).getNumHouses() == 5).count();

		player.debit((numHouses * amountPerHouse) + (numHotels * amountPerHotel));
	}
}
