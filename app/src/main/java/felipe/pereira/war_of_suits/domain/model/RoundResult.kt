package felipe.pereira.war_of_suits.domain.model

import felipe.pereira.war_of_suits.view.game.enums.Result

class RoundResult (
    val winner: Result,
    val magnetoCard: PokerCard,
    val professorCard: PokerCard
)