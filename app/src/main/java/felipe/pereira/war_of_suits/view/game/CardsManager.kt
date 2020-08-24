package felipe.pereira.war_of_suits.view.game

import felipe.pereira.war_of_suits.view.game.cardsmanager.Result
import felipe.pereira.war_of_suits.view.game.cardsmanager.PokerCardViewEntity
import felipe.pereira.war_of_suits.view.game.cardsmanager.Suit

class CardsManager {

    private val cards = mutableListOf<PokerCardViewEntity>()
    private val suitsPriority =
        mutableListOf(Suit.SPADES, Suit.CLUBS, Suit.DIAMONDS, Suit.DIAMONDS)
    private val MAX_CARDS_FOR_SUITS = 13

    val cardsPlayerOne = mutableListOf<PokerCardViewEntity>()
    val cardsPlayerTwo = mutableListOf<PokerCardViewEntity>()
    val discardedCardsPlayerOne = mutableListOf<PokerCardViewEntity>()
    val discardedCardsPlayerTwo = mutableListOf<PokerCardViewEntity>()

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
            cardsPlayerOne.add(cards[index])
            cardsPlayerTwo.add(cards[index + 1])
            index += 2
        }
    }

    fun playRound(): Result {
        val cardPlayerOne = cardsPlayerOne.first()
        cardsPlayerOne.remove(cardPlayerOne)
        val cardPlayerTwo = cardsPlayerTwo.first()
        cardsPlayerTwo.remove(cardPlayerTwo)

        val result = when {
            cardPlayerOne.number > cardPlayerTwo.number -> Result.ONE
            cardPlayerOne.number < cardPlayerTwo.number -> Result.TWO
            else -> {
                val indexSuitPlayerOne = suitsPriority.indexOfFirst { it == cardPlayerOne.suit }
                val indexSuitPlayerTwo = suitsPriority.indexOfFirst { it == cardPlayerTwo.suit }
                when {
                    indexSuitPlayerOne < indexSuitPlayerTwo -> Result.ONE
                    indexSuitPlayerOne > indexSuitPlayerTwo -> Result.TWO
                    else -> Result.EQUAL
                }
            }
        }

        when (result) {
            Result.ONE -> discardedCardsPlayerOne.addAll(listOf(cardPlayerOne, cardPlayerTwo))
            Result.TWO -> discardedCardsPlayerTwo.addAll(listOf(cardPlayerOne, cardPlayerTwo))
            Result.EQUAL -> { }
        }

        return result
    }
}