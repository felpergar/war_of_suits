package felipe.pereira.war_of_suits.data.model

import felipe.pereira.war_of_suits.domain.model.PokerCard

class PlayerCards(
    val cards: MutableList<PokerCard>,
    val discardedCards: MutableList<PokerCard>
) {
}