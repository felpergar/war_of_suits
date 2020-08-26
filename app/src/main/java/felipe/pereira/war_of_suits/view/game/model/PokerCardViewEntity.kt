package felipe.pereira.war_of_suits.view.game.model

import felipe.pereira.war_of_suits.domain.model.PokerCard
import felipe.pereira.war_of_suits.view.game.enums.CardValue
import felipe.pereira.war_of_suits.view.game.enums.Suit

class PokerCardViewEntity (
    val number: CardValue,
    val suit: Suit
)

fun PokerCard.transformToUi() = PokerCardViewEntity(number, suit)