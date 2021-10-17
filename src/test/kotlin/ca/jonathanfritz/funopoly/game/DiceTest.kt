package ca.jonathanfritz.funopoly.game

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DiceTest {

    private val dice = Dice(1L)

    @Test
    fun test() {
        testRoll(5, false)
        testRoll(6, false)
        testRoll(10, false)
        testRoll(6, false)
        testRoll(7, false)
        testRoll(4, true)
        testRoll(7, false)
        testRoll(9, false)
        testRoll(8, false)
        testRoll(12, true)
    }

    private fun testRoll(expectedValue: Int, isDoubles: Boolean) {
        val (roll, doubles) = dice.roll()
        assertEquals(expectedValue, roll)
        assertEquals(isDoubles, doubles)
    }
}