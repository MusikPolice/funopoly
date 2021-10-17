package ca.jonathanfritz.funopoly.game.board

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DeckTest {

    @Test
    fun drawTest() {
        val expected = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val deck = Deck(expected)

        // if we draw ten cards, we should get each of the cards in actual, albeit in a random order
        // we can do this three times in a row, demonstrating that the deck is implicitly reshuffled when we hit the end
        for (i in (1..3)) {
            val actual: MutableList<Int> = ArrayList()
            for (j in (1..10)) {
                actual.add(deck.draw())
            }
            assertEquals(10, actual.size)
            assertTrue(actual.containsAll(expected))
        }
    }
}