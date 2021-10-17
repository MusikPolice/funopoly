package ca.jonathanfritz.funopoly.game

import kotlin.random.Random

class Dice(private val seed: Long = System.currentTimeMillis()) {

    private val random: Random = Random(seed)

    /**
     * Rolls the dice - returns a pair containing the sum of the two dice in the first value,
     * and a boolean indicating if doubles were rolled in the second
     */
    fun roll(): Pair<Int, Boolean> {
        val first = random.nextInt(1, 7)
        val second = random.nextInt(1, 7)
        return Pair(first + second, first == second)
    }
}