package ca.jonathanfritz.funopoly.game.board

class Deck<T>(
    private var cards: List<T>,
    private var index: Int = 0
) {
    fun draw(): T {
        if (index == cards.size) {
            index = 0
        }
        if (index == 0) {
            cards = cards.shuffled()
        }
        return cards[index++]
    }
}