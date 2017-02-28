package ca.jonathanfritz.funopoly;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ca.jonathanfritz.funopoly.Dice.DiceRollResult;
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

	private final Dice dice = new Dice();
	private final ChanceDeck chanceDeck = new ChanceDeck();
	private final CommunityChestDeck communityChestDeck = new CommunityChestDeck();

	private final List<Tile> tiles = new ArrayList<>();

	private final List<Player> players = new ArrayList<>();

	private int lastDiceRoll = 0;

	private final static int STARTING_CASH = 1500;
	private final static int GO_REWARD = 200;

	public Board(int numPlayers) {
		// set up the board
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

		for (int i = 0; i < numPlayers; i++) {
			// all players start on GO with some cash money
			players.add(new Player("Player" + (i + 1), STARTING_CASH, tiles.get(0)));
		}
	}

	public void play() {
		int roundCounter = 0;
		boolean gameOver = false;
		while (!gameOver) {
			System.out.println("== Starting round " + String.valueOf(roundCounter + 1) + " ==");
			doRound();

			// players that are out of money don't get to play any more
			players.removeAll(players.stream()
			        .filter(p -> p.getBalance() <= 0)
			        .collect(Collectors.toList()));

			// when there's only one player left with money, the game is over
			gameOver = players.size() == 1;
			roundCounter++;
		}

		System.out.println("The winner is " + players.get(0).toString());
	}

	public void doRound() {
		for (final Player player : players) {
			System.out.println(player.toString() + " starts their turn " + (player.isInJail() ? "in jail " : "")
			        + "with $" + String.valueOf(player.getBalance()));

			boolean playerCanRoll = true;
			int doublesCounter = 0;
			while (playerCanRoll) {
				// if in jail, player can choose to use a card or pay to get out prior to rolling
				if (player.isInJail()) {
					if (player.debitGetOutOfJailFreeCard()) {
						System.out.println(player.toString() + " uses a get out of jail free card");
						player.setInJail(false);
					} else if (player.payToGetOutOfJail()) {
						System.out.println(player.toString() + " pays $50 to get out of jail");
						player.debit(50);
						player.setInJail(false);
					}
				}

				// roll the dice
				final DiceRollResult result = dice.roll();
				lastDiceRoll = result.value;
				if (player.isInJail()) {
					if (result.isDoubles) {
						// doubles releases player from jail, but player does not get to roll again
						System.out.println(player.toString() + " rolled doubles to get out of jail");
						player.setInJail(false);
						playerCanRoll = false;
						doublesCounter = 0;
					} else {
						System.out.println(player.toString() + " ends their turn in jail");
						playerCanRoll = false;
						continue;
					}
				} else if (result.isDoubles) {
					// if player isn't in jail, doubles grants another turn
					playerCanRoll = true;
					doublesCounter++;
				} else {
					// if player did not roll doubles, they don't get to roll again
					playerCanRoll = false;
					doublesCounter = 0;
				}
				System.out.println(player.toString() + " rolls a"
				        + (lastDiceRoll == 8 || lastDiceRoll == 11 ? "n " : " ") + String.valueOf(lastDiceRoll)
				        + (doublesCounter > 0 ? " (doubles x" + String.valueOf(doublesCounter) + ")" : ""));

				if (doublesCounter == 3) {
					// 3 doubles in a row is speeding - go to jail
					System.out.println(player.toString() + " is in jail for speeding (3x doubles)");
					player.setInJail(true);
					movePlayerTo(player, Type.JAIL);
					break;
				} else {
					// turn proceeds as normal
					movePlayerNumSpaces(player, lastDiceRoll);
					if (player.isInJail()) {
						// if the player ended up in jail (landed on go to jail, got a bad card, etc), turn is over
						System.out.println(player.toString() + "'s turn is over");
						break;
					}

					// TODO: build houses? trade?
					player.buildHouses(tiles);
				}
			}
		}
	}

	/**
	 * Moves the player directly to the specified tile and executes any actions associated with that tile. Does not
	 * handle passing go
	 * @param player the player to move
	 * @param tile the tile to move the player to
	 */
	private void movePlayerTo(Player player, Tile tile) {
		player.setPosition(tile);
		tile.land(player, this);

		if (tile.getType() == Type.PROPERTY || tile.getType() == Type.RAILROAD || tile.getType() == Type.UTILITY) {
			final OwnableTile ownableTile = (OwnableTile) tile;
			if (ownableTile.getOwner() == null) {
				if (player.willBuy(ownableTile)) {
					System.out.println(player.toString() + " bought " + ownableTile.getName() + " for $"
					        + String.valueOf(ownableTile.getPrice()));

					player.debit(ownableTile.getPrice());
					ownableTile.setOwner(player);
				} else {
					// auction
					int maxBid = 0;
					Player maxBidPlayer = null;
					boolean updated = true;
					System.out.println(player.toString() + " declines to buy " + ownableTile.getName()
					        + ". It's auction time!");

					// keep going until everybody stops increasing their bid
					while (updated) {
						updated = false;
						for (final Player bidder : players) {
							if (bidder == maxBidPlayer) {
								// this player already has the maximum bid
								continue;
							}
							final int newBid = bidder.getBid(ownableTile, maxBid);
							if (newBid > maxBid) {
								maxBid = newBid;
								maxBidPlayer = bidder;
								updated = true;
								System.out.println(bidder.toString() + " bids " + String.valueOf(maxBid));
							}
						}
					}

					// max bid gets it
					if (maxBidPlayer != null) {
						System.out.println(maxBidPlayer.toString() + " buys " + ownableTile.getName()
						        + " at auction for $" + String.valueOf(maxBid));

						maxBidPlayer.debit(maxBid);
						ownableTile.setOwner(maxBidPlayer);
					}
				}
			} else if (tile.getType() == Type.PROPERTY) {
				final Property property = (Property) tile;
				if (property.getOwner() == player) {
					System.out.println(player.toString() + " owns " + property.getName());
				} else {
					final boolean ownsColourGroup = tiles.stream()
					        .filter(t -> t.getType() == Type.PROPERTY)
					        .filter(t -> ((Property) t).getDeed().getColour() == property.getDeed().getColour())
					        .allMatch(t -> ((Property) t).getOwner() == player);
					final int amount = property.getRent(ownsColourGroup);

					System.out.println(player.toString() + " pays $" + String.valueOf(amount) + " in rent to "
					        + property.getOwner().toString());
					player.debit(amount);
					property.getOwner().grant(amount);
				}
			} else if (tile.getType() == Type.RAILROAD) {
				final Railroad railroad = (Railroad) tile;
				if (railroad.getOwner() == player) {
					System.out.println(player.toString() + " owns " + railroad.getName());
				} else {
					final long numRailroads = tiles.stream()
					        .filter(t -> t.getType() == Type.RAILROAD)
					        .filter(t -> ((Railroad) t).getOwner() == railroad.getOwner())
					        .count();
					final int amount = (int) Math.floor(25 * Math.pow(2, numRailroads - 1));

					System.out.println(player.toString() + " pays $" + String.valueOf(amount) + " in rent to "
					        + railroad.getOwner().toString());
					player.debit(amount);
					railroad.getOwner().grant(amount);
				}
			} else if (tile.getType() == Type.UTILITY) {
				final Utility utility = (Utility) tile;
				if (utility.getOwner() == player) {
					System.out.println(player.toString() + " owns " + utility.getName());
				} else {
					final long numUtilities = tiles.stream()
					        .filter(t -> t.getType() == Type.UTILITY)
					        .filter(t -> ((Utility) t).getOwner() == utility.getOwner())
					        .count();

					// 4x dice roll if one utility is owned
					// 10x dice roll if both utilities are owned
					final int amount = lastDiceRoll * numUtilities == 1 ? 4 : 10;

					System.out.println(player.toString() + " pays $" + String.valueOf(amount) + " in rent to "
					        + utility.getOwner().toString());
					player.debit(amount);
					utility.getOwner().grant(amount);
				}
			}
		}
	}

	/**
	 * Moves the player to the next tile of the specified {@link Type} on the board
	 * @param player the player to move
	 * @param type the type of tile to move to. If there is only one type of that tile on the board, such as
	 *            {@link Type#GO}, the player will be moved directly to that tile. If there is more than one tile with
	 *            that type on the board, such as {@link Type#RAILROAD}, the player will be moved to the closest tile of
	 *            that type. The player will always be moved in a clockwise direction.
	 */
	public void movePlayerTo(Player player, Type type) {
		final int currentPosition = tiles.indexOf(player.getPosition());
		for (int i = currentPosition; i < currentPosition + tiles.size(); i++) {
			final Tile tile = tiles.get(i % tiles.size());
			if (tile.getType() == type) {
				if (i >= tiles.size() && type != Type.JAIL) {
					// passed go, not headed for jail, grant reward
					System.out.println(player.toString() + " passed go, gets $200");
					player.grant(GO_REWARD);
				}
				movePlayerTo(player, tile);
				break;
			}
		}
	}

	/**
	 * Moves the player to the {@link Property} tile with the specified {@link Deed}
	 * @param player the player to move
	 * @param deed the deed of the property to move to
	 */
	public void movePlayerTo(Player player, Deed deed) {
		final Tile tile = tiles.stream()
		        .filter(t -> t.getType() == Type.PROPERTY)
		        .filter(t -> ((Property) t).getDeed() == deed)
		        .findFirst()
		        .get();

		if (tiles.indexOf(tile) < tiles.indexOf(player.getPosition())) {
			// new position is behind current position, player must pass go to reach it
			System.out.println(player.toString() + " passed go, gets $200");
			player.grant(GO_REWARD);
		}

		movePlayerTo(player, tile);
	}

	/**
	 * Moves the player to the {@link Railroad} tile with the specified {@link Line}
	 * @param player the player to move
	 * @param line the line of the railroad to move to
	 */
	public void movePlayerTo(Player player, Line line) {
		final Tile tile = tiles.stream()
		        .filter(t -> t.getType() == Type.RAILROAD)
		        .filter(t -> ((Railroad) t).getLine() == line)
		        .findFirst()
		        .get();

		if (tiles.indexOf(tile) < tiles.indexOf(player.getPosition())) {
			// new position is behind current position, player must pass go to reach it
			System.out.println(player.toString() + " passed go, gets $200");
			player.grant(GO_REWARD);
		}

		movePlayerTo(player, tile);
	}

	/**
	 * Moves the player left or right by the specified number of spaces.
	 * @param player the player to move
	 * @param numSpaces the number of spaces to move. If positive, and the player passes go, $200 will be granted to the
	 *            player
	 */
	public void movePlayerNumSpaces(Player player, int numSpaces) {
		final int currentPosition = tiles.indexOf(player.getPosition());
		final int newPosition = currentPosition + numSpaces;
		if (numSpaces > 0 && newPosition >= tiles.size()) {
			// passed go in the counter clockwise direction
			System.out.println(player.toString() + " passed go, gets $200");
			player.grant(GO_REWARD);
		}
		movePlayerTo(player, tiles.get(newPosition % tiles.size()));
	}

	public int getNumPlayers() {
		return players.size();
	}

	/**
	 * Grants the specified amount of money to each player
	 * @param amount the amount to grant
	 * @param exemptPlayer a player exempt from the reward. Set to null to grant reward to all players.
	 */
	public void grantPlayers(int amount, Player exemptPlayer) {
		for (final Player player : players) {
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
		for (final Player player : players) {
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
		final int numHouses = tiles.stream()
		        .filter(t -> t.getType() == Type.PROPERTY)
		        .filter(t -> ((Property) t).getOwner() == player)
		        .filter(t -> ((Property) t).getNumHouses() > 0)
		        .filter(t -> ((Property) t).getNumHouses() < 5)
		        .mapToInt(t -> ((Property) t).getNumHouses())
		        .sum();

		final int numHotels = (int) tiles.stream()
		        .filter(t -> t.getType() == Type.PROPERTY)
		        .filter(t -> ((Property) t).getOwner() == player)
		        .filter(t -> ((Property) t).getNumHouses() == 5)
		        .count();

		player.debit((numHouses * amountPerHouse) + (numHotels * amountPerHotel));
	}

	public static void main(String[] args) {
		final Board board = new Board(4);
		board.play();
	}
}
