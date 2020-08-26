package felipe.pereira.war_of_suits.data

import felipe.pereira.war_of_suits.domain.usecase.Deck
import felipe.pereira.war_of_suits.domain.model.PokerCard
import felipe.pereira.war_of_suits.view.game.enums.CardValue
import felipe.pereira.war_of_suits.view.game.enums.Suit

class DeckProvider : Deck {

    private val cards = mutableListOf<PokerCard>()

    override fun createCards() {
        Suit.values().forEach { suit ->
            CardValue.values().forEach {
                cards.add(PokerCard(it, suit))
            }
        }
    }

    override fun getCardsShuffled(): Pair<List<PokerCard>, List<PokerCard>> {
        cards.shuffle()
        var index = 0
        val cardsMagneto = mutableListOf<PokerCard>()
        val cardsProfessor = mutableListOf<PokerCard>()

        while (index < (cards.size - 1)) {
            cardsMagneto.add(cards[index])
            cardsProfessor.add(cards[index + 1])
            index += 2
        }
        return Pair(cardsMagneto.toList(), cardsProfessor.toList())
    }

    override fun getSuitPriority(): List<Suit> = Suit.values().toList().shuffled()
}