package felipe.pereira.war_of_suits.view.game

import felipe.pereira.war_of_suits.view.game.cardsmanager.PokerCardViewEntity
import felipe.pereira.war_of_suits.view.game.cardsmanager.Suit

class CardsManager {

    private val cards = mutableListOf<PokerCardViewEntity>()
    private val suitsPriority = mutableListOf<Suit>(Suit.SPADES, Suit.CLUBS, Suit.DIAMONDS, Suit.DIAMONDS)
    private val MAX_CARDS_FOR_SUITS = 13

    val cardsPlayer1 = mutableListOf<PokerCardViewEntity>()
    val cardsPlayer2 = mutableListOf<PokerCardViewEntity>()

    fun getCardsShuffled() {
        val suits = Suit.values()

        suits.forEach { suit ->
            for (x in 1..MAX_CARDS_FOR_SUITS) {
                cards.add(PokerCardViewEntity(x, suit))
            }
        }

        cards.shuffle()
        suitsPriority.shuffle()

        var index = 0

        while (index < (cards.size - 1)) {
            cardsPlayer1.add(cards[index])
            cardsPlayer2.add(cards[index + 1])
            index += 2
        }
    }
}