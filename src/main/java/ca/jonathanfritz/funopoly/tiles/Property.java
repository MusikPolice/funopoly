package ca.jonathanfritz.funopoly.tiles;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.jonathanfritz.funopoly.Board;
import ca.jonathanfritz.funopoly.Dice.DiceRollResult;
import ca.jonathanfritz.funopoly.Player;

public class Property extends OwnableTile {

	private final Deed deed;
	private int numHouses = 0;

	private static final Logger log = LoggerFactory.getLogger(Property.class);

	public Property(Deed deed) {
		super(deed.name, deed.price);
		this.deed = deed;
	}

	public Deed getDeed() {
		return deed;
	}

	public int getNumHouses() {
		return numHouses;
	}

	/**
	 * Increments the number of houses on the property
	 * @return the cost of the improvement
	 */
	public int buyHouse() {
		numHouses++;
		return deed.colour.houseCost;
	}

	/**
	 * Gets the rent to be charged to an opponent landing on the property
	 * @param ownsColourGroup true if the owner also owns all other properties in this colour group
	 * @return the rent to charge the opponent
	 */
	public int getRent(boolean ownsColourGroup) {
		if (numHouses == 0 && ownsColourGroup) {
			return deed.rents[0] * 2;
		}
		return deed.rents[numHouses];
	}

	@Override
	public void land(Player player, DiceRollResult diceRoll, Board board) {
		// do nothing - this is handled in Board
		log.info("{} lands on {}", player, deed.name);
	}

	@Override
	public Type getType() {
		return Type.PROPERTY;
	}

	@Override
	public String toString() {
		return deed.name + (numHouses > 0 ? " (" + numHouses + " houses)" : "");
	}

	/**
	 * Reference https://en.wikibooks.org/wiki/Monopoly/Official_Rules#Property_Values_Table
	 */
	public enum Deed {
		MEDITERRANEAN_AVENUE("Mediterranean Avenue", Property.ColourGroup.DARK_PURPLE, 60, new int[] { 60, 2, 10, 30,
		        90, 160, 250 }),
		BALTIC_AVENUE("Baltic Avenue", Property.ColourGroup.DARK_PURPLE, 60,
		        new int[] { 60, 4, 20, 60, 180, 320, 450 }),
		ORIENTAL_AVENUE("Oriental Avenue", Property.ColourGroup.LIGHT_BLUE, 100, new int[] { 100, 6, 30, 90, 270, 400,
		        550 }),

		VERMONT_AVENUE("Vermont Avenue", Property.ColourGroup.LIGHT_BLUE, 100, new int[] { 100, 6, 30, 90, 270, 400,
		        550 }),
		CONNECTICUT_AVENUE("Connecticut Avenue", Property.ColourGroup.LIGHT_BLUE, 120, new int[] { 120, 8, 40, 100,
		        300, 450, 600 }),
		ST_CHARLES_PLACE("St. Charles Place", Property.ColourGroup.PURPLE, 140, new int[] { 140, 10, 50, 150, 450, 625,
		        750 }),
		STATES_AVENUE("States Avenue", Property.ColourGroup.PURPLE, 140, new int[] { 140, 10, 50, 150, 450, 625, 750 }),
		VIRGINIA_AVENUE("Virginia Avenue", Property.ColourGroup.PURPLE, 160, new int[] { 160, 12, 60, 180, 500, 700,
		        900 }),
		ST_JAMES_PLACE("St. James Place", Property.ColourGroup.ORANGE, 180,
		        new int[] { 180, 14, 70, 200, 550, 750, 950 }),
		TENNESSEE_AVENUE("Tennessee Avenue", Property.ColourGroup.ORANGE, 180, new int[] { 180, 14, 70, 200, 550, 750,
		        950 }),
		NEW_YORK_AVENUE("New York Avenue", Property.ColourGroup.ORANGE, 200, new int[] { 200, 16, 80, 220, 600, 800,
		        1000 }),
		KENTUCKY_AVENUE("Kentucky Avenue", Property.ColourGroup.RED, 220,
		        new int[] { 220, 18, 90, 250, 700, 875, 1050 }),
		INDIANA_AVENUE("Indiana Avenue", Property.ColourGroup.RED, 220, new int[] { 220, 18, 90, 250, 700, 875, 1050 }),
		ILLINOIS_AVENUE("Illinois Avenue", Property.ColourGroup.RED, 240,
		        new int[] { 240, 20, 100, 300, 750, 925, 1100 }),
		ATLANTIC_AVENUE("Atlantic Avenue", Property.ColourGroup.YELLOW, 260, new int[] { 260, 22, 110, 330, 800, 975,
		        1150 }),
		VENTNOR_AVENUE("Ventnor Avenue", Property.ColourGroup.YELLOW, 260, new int[] { 260, 22, 110, 330, 800, 975,
		        1150 }),
		MARVIN_GARDENS("Marvin Gardens", Property.ColourGroup.YELLOW, 280, new int[] { 280, 24, 120, 360, 850, 1025,
		        1200 }),
		PACIFIC_AVENUE("Pacific Avenue", Property.ColourGroup.GREEN, 300, new int[] { 300, 26, 130, 390, 900, 1100,
		        1275 }),
		NORTH_CAROLINA_AVENUE("North Carolina Avenue", Property.ColourGroup.GREEN, 300, new int[] { 300, 26, 130, 390,
		        900, 1100, 1275 }),
		PENNSYLVANIA_AVENUE("Pennsylvania Avenue", Property.ColourGroup.GREEN, 320, new int[] { 320, 28, 150, 450,
		        1000, 1200, 1400 }),
		PARK_PLACE("Park Place", Property.ColourGroup.BLUE, 350, new int[] { 350, 35, 175, 500, 1100, 1300, 1500 }),
		BOARDWALK("Boardwalk", Property.ColourGroup.BLUE, 400, new int[] { 400, 50, 200, 600, 1400, 1700, 2000 });

		private final String name;
		private final ColourGroup colour;
		private final int price;
		private final int[] rents;

		private Deed(String name, ColourGroup colour, int price, int[] rents) {
			this.name = name;
			this.colour = colour;
			this.price = price;
			this.rents = rents;
		}

		public ColourGroup getColour() {
			return colour;
		}

		public static List<Deed> getDeedsInGroup(ColourGroup group) {
			final List<Deed> deeds = new ArrayList<>();
			for (final Deed d : Deed.values()) {
				if (d.colour == group) {
					deeds.add(d);
				}
			}
			return deeds;
		}
	}

	public enum ColourGroup {
		DARK_PURPLE(50),
		LIGHT_BLUE(50),
		PURPLE(100),
		ORANGE(100),
		RED(150),
		YELLOW(150),
		GREEN(200),
		BLUE(200);

		private final int houseCost;

		private ColourGroup(int houseCost) {
			this.houseCost = houseCost;
		}

		public int getHouseCost() {
			return houseCost;
		}
	}
}
