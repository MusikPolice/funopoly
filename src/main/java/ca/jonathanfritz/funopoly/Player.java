package ca.jonathanfritz.funopoly;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import ca.jonathanfritz.funopoly.tiles.OwnableTile;
import ca.jonathanfritz.funopoly.tiles.Property;
import ca.jonathanfritz.funopoly.tiles.Property.ColourGroup;
import ca.jonathanfritz.funopoly.tiles.Tile;
import ca.jonathanfritz.funopoly.tiles.Tile.Type;

public class Player {

	private final String name;
	private int balance;

	private Tile position;
	private boolean inJail;
	private int getOutOfJailFreeCards = 0;

	private final Random random = new Random();

	public Player(String name, int balance, Tile position) {
		this.name = name;
		this.balance = balance;
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public int getBalance() {
		return balance;
	}

	public Tile getPosition() {
		return position;
	}

	public void setPosition(Tile tile) {
		position = tile;
	}

	public boolean isInJail() {
		return position.getType() == Type.JAIL && inJail;
	}

	public boolean isJustVisiting() {
		return position.getType() == Type.JAIL && !inJail;
	}

	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}

	public int debit(int amount) {
		// TODO: what if amount > balance? sell houses, mortgage, etc
		balance -= amount;
		System.out.println(name + " now has $" + String.valueOf(balance));
		return balance;
	}

	public int grant(int amount) {
		balance += amount;
		System.out.println(name + " now has $" + String.valueOf(balance));
		return balance;
	}

	public void grantGetOutOfJailFreeCard() {
		getOutOfJailFreeCards++;
	}

	public boolean debitGetOutOfJailFreeCard() {
		// TODO: make this smarter - sometimes, staying in jail is a good idea
		if (getOutOfJailFreeCards > 0) {
			getOutOfJailFreeCards--;
			return true;
		}
		return false;
	}

	public boolean payToGetOutOfJail() {
		// TODO: make this smarter - sometimes, staying in jail is a good idea
		return balance > 200;
	}

	public boolean willBuy(OwnableTile tile) {
		// TODO: real AI based on what the player owns/needs
		return tile.getPrice() < balance;
	}

	public int getBid(OwnableTile tile, int currentBid) {
		// TODO: real AI based on what the player owns/needs
		final int multiplier = random.nextInt(4) + 1;
		final int bid = currentBid + (int) Math.floor(tile.getPrice() * 0.1 * multiplier);
		if (bid < tile.getPrice() * 1.25 && bid < balance) {
			return bid;
		}
		return 0;
	}

	public void buildHouses(List<Tile> tiles) {
		for (final ColourGroup group : ColourGroup.values()) {
			// find all properties in the group
			final List<Property> propertiesInGroup = tiles.stream()
			        .filter(t -> t.getType() == Type.PROPERTY)
			        .filter(t -> ((Property) t).getDeed().getColour() == group)
			        .map(t -> ((Property) t))
			        .collect(Collectors.toList());

			// if player has a monopoly over an entire colour group, and they don't already have a hotel on all houses
			// in the group, they can opt to upgrade it
			// TODO: limit number of available houses in the bank
			// TODO: buy multiple rounds of houses in a single turn
			if (propertiesInGroup.stream().allMatch(p -> p.getOwner() == this)
			        && propertiesInGroup.get(0).getNumHouses() < 6) {
				final int cost = propertiesInGroup.size() * group.getHouseCost();
				if (cost < balance) {
					// add a house to each
					System.out.println(name + " builds houses on "
					        + propertiesInGroup.stream().map(p -> p.toString()).collect(Collectors.joining(", ")));
					propertiesInGroup.stream()
					        .forEach(p -> p.buyHouse());
					debit(cost);
				}
			}
		}

	}

	@Override
	public String toString() {
		return name;
	}
}
