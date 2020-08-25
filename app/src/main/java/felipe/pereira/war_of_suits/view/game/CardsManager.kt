package felipe.pereira.war_of_suits.view.game

import androidx.lifecycle.MutableLiveData
import felipe.pereira.war_of_suits.view.game.cardsmanager.Result
import felipe.pereira.war_of_suits.view.game.cardsmanager.PokerCardViewEntity
import felipe.pereira.war_of_suits.view.game.cardsmanager.Suit

class CardsManager {

    private val cards = mutableListOf<PokerCardViewEntity>()
    private val suitsPriority =
        mutableListOf(Suit.SPADES, Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS)
    private val MAX_CARDS_FOR_SUITS = 13

    val cardsMagneto = mutableListOf<PokerCardViewEntity>()
    private val cardsProfessor = mutableListOf<PokerCardViewEntity>()
    val discardedCardsMagneto = mutableListOf<PokerCardViewEntity>()
    val discardedCardsProfessor = mutableListOf<PokerCardViewEntity>()

    val discardedCardsPlayerOneLiveData = MutableLiveData<List<PokerCardViewEntity>>()
    val discardedCardsPlayerTwoLiveData = MutableLiveData<List<PokerCardViewEntity>>()

    fun createCards() {
        suitsPriority.forEach { suit ->
            for (x in 1..MAX_CARDS_FOR_SUITS) {
                cards.add(PokerCardViewEntity(x, suit))
            }
        }
    }

    fun initCardsShuffled(showSuitsPriority: (MutableList<Suit>) -> Unit) {
        cardsMagneto.clear()
        cardsProfessor.clear()
        discardedCardsMagneto.clear()
        discardedCardsProfessor.clear()
        discardedCardsPlayerOneLiveData.postValue(listOf())
        discardedCardsPlayerTwoLiveData.postValue(listOf())

        cards.shuffle()
        suitsPriority.shuffle()
        showSuitsPriority(suitsPriority)

        var index = 0

        while (index < (cards.size - 1)) {
            cardsMagneto.add(cards[index])
            cardsProfessor.add(cards[index + 1])
            index += 2
        }
    }

    fun playRound(showResult: (Result, PokerCardViewEntity, PokerCardViewEntity) -> Unit?) {
        val cardPlayerOne = cardsMagneto.first()
        cardsMagneto.remove(cardPlayerOne)
        val cardPlayerTwo = cardsProfessor.first()
        cardsProfessor.remove(cardPlayerTwo)

        val result = when {
            cardPlayerOne.number > cardPlayerTwo.number -> Result.MAGNETO
            cardPlayerOne.number < cardPlayerTwo.number -> Result.PROFESSOR
            else -> {
                val indexSuitPlayerOne = suitsPriority.indexOfFirst { it == cardPlayerOne.suit }
                val indexSuitPlayerTwo = suitsPriority.indexOfFirst { it == cardPlayerTwo.suit }
                when {
                    indexSuitPlayerOne < indexSuitPlayerTwo -> Result.MAGNETO
                    indexSuitPlayerOne > indexSuitPlayerTwo -> Result.PROFESSOR
                    else -> Result.EQUAL
                }
            }
        }

        when (result) {
            Result.MAGNETO -> {
                discardedCardsMagneto.addAll(listOf(cardPlayerOne, cardPlayerTwo))
                discardedCardsPlayerOneLiveData.postValue(discardedCardsMagneto)
            }
            Result.PROFESSOR -> {
                discardedCardsProfessor.addAll(listOf(cardPlayerOne, cardPlayerTwo))
                discardedCardsPlayerTwoLiveData.postValue(discardedCardsProfessor)
            }
            Result.EQUAL -> { }
        }

        showResult(result, cardPlayerOne, cardPlayerTwo)
    }
}