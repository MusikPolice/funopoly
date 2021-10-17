package ca.jonathanfritz.funopoly.game.board

import ca.jonathanfritz.funopoly.game.Bank
import ca.jonathanfritz.funopoly.game.Player

sealed class Property: BoardSpace {

    private val owner: Player? = null

    private fun isOwned(): Boolean = owner != null

    override fun doLanding(player: Player, bank: Bank, board: Board) {
        if (this.isOwned()) {
            // pay rent
            val rent = calculateRent()
            println("Rent on ${this.javaClass.simpleName} is $rent")
            player.transferTo(rent, this.owner!!)
        } else {
            // purchase?
        }
    }

    // and as such, each has a price
    abstract fun getPrice(): Int

    open fun calculateRent(): Int {
        // TODO: implement the house/hotel ownership rules for rent here
        return 1
    }

    // brown
    object MediterraneanAvenue : Property() {
        override fun getGroup() = BoardSpace.Group.Brown
        override fun getPrice() = 60
    }
    object BalticAvenue: Property() {
        override fun getGroup() = BoardSpace.Group.Brown
        override fun getPrice() = 60
    }

    // light blue
    object OrientalAvenue: Property() {
        override fun getGroup() = BoardSpace.Group.LightBlue
        override fun getPrice() = 100
    }
    object VermontAvenue: Property() {
        override fun getGroup() = BoardSpace.Group.LightBlue
        override fun getPrice() = 100
    }
    object ConnecticutAvenue: Property() {
        override fun getGroup() = BoardSpace.Group.LightBlue
        override fun getPrice() = 120
    }

    // pink
    object StCharlesPlace: Property() {
        override fun getGroup() = BoardSpace.Group.Pink
        override fun getPrice() = 140
    }
    object StatesAvenue: Property() {
        override fun getGroup() = BoardSpace.Group.Pink
        override fun getPrice() = 140
    }
    object VirginiaAvenue: Property() {
        override fun getGroup() = BoardSpace.Group.Pink
        override fun getPrice() = 160
    }

    // orange
    object StJamesPlace: Property() {
        override fun getGroup() = BoardSpace.Group.Orange
        override fun getPrice() = 180
    }
    object TennesseeAvenue: Property() {
        override fun getGroup() = BoardSpace.Group.Orange
        override fun getPrice() = 180
    }
    object NewYorkAvenue: Property() {
        override fun getGroup() = BoardSpace.Group.Orange
        override fun getPrice() = 200
    }

    // red
    object KentuckyAvenue: Property() {
        override fun getGroup() = BoardSpace.Group.Red
        override fun getPrice() = 220
    }
    object IndianaAvenue: Property() {
        override fun getGroup() = BoardSpace.Group.Red
        override fun getPrice() = 220
    }
    object IllinoisAvenue: Property() {
        override fun getGroup() = BoardSpace.Group.Red
        override fun getPrice() = 240
    }

    // yellow
    object AtlanticAvenue: Property() {
        override fun getGroup() = BoardSpace.Group.Yellow
        override fun getPrice() = 260
    }
    object VentnorAvenue: Property() {
        override fun getGroup() = BoardSpace.Group.Yellow
        override fun getPrice() = 260
    }
    object MarvinGardens: Property() {
        override fun getGroup() = BoardSpace.Group.Yellow
        override fun getPrice() = 280
    }

    // green
    object PacificAvenue: Property() {
        override fun getGroup() = BoardSpace.Group.Green
        override fun getPrice() = 300
    }
    object NorthCarolinaAvenue: Property() {
        override fun getGroup() = BoardSpace.Group.Green
        override fun getPrice() = 300
    }
    object PennsylvaniaAvenue: Property() {
        override fun getGroup() = BoardSpace.Group.Green
        override fun getPrice() = 320
    }

    // dark blue
    object ParkPlace: Property() {
        override fun getGroup() = BoardSpace.Group.DarkBlue
        override fun getPrice() = 350
    }
    object Boardwalk: Property() {
        override fun getGroup() = BoardSpace.Group.DarkBlue
        override fun getPrice() = 400
    }

    sealed class Railroad : Property() {

        override fun calculateRent(): Int {
            // TODO: implement the railroad ownership rules for rent here
            return 2
        }

        object ReadingRailroad : Railroad() {
            override fun getGroup() = BoardSpace.Group.Railroad
            override fun getPrice() = 200
        }
        object PennsylvaniaRailroad : Railroad() {
            override fun getGroup() = BoardSpace.Group.Railroad
            override fun getPrice() = 200
        }
        object BORailroad : Railroad() {
            override fun getGroup() = BoardSpace.Group.Railroad
            override fun getPrice() = 200
        }
        object ShortLineRailroad : Railroad() {
            override fun getGroup() = BoardSpace.Group.Railroad
            override fun getPrice() = 200
        }
    }

    sealed class Utility : Property() {

        override fun calculateRent(): Int {
            // TODO: implement the utilities ownership rules for rent here
            return 2
        }

        object ElectricCompany : Utility() {
            override fun getGroup() = BoardSpace.Group.Utility
            override fun getPrice() = 150
        }
        object WaterWorks : Utility() {
            override fun getGroup() = BoardSpace.Group.Utility
            override fun getPrice() = 150
        }
    }
}