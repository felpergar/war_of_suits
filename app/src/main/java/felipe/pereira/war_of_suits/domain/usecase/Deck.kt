package felipe.pereira.war_of_suits.domain.usecase

import felipe.pereira.war_of_suits.domain.model.PokerCard
import felipe.pereira.war_of_suits.view.game.enums.Suit

interface Deck {
    fun createCards()
    fun getCardsShuffled(): Pair<List<PokerCard>, List<PokerCard>>
    fun getSuitPriority(): List<Suit>
}
