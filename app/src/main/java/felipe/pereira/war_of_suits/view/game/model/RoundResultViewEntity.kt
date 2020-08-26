package felipe.pereira.war_of_suits.view.game.model

import felipe.pereira.war_of_suits.domain.model.RoundResult
import felipe.pereira.war_of_suits.view.game.enums.Result

class RoundResultViewEntity (
    val winner: Result,
    val magnetoCard: PokerCardViewEntity,
    val professorCard: PokerCardViewEntity
)

fun RoundResult.transformToUi() =
    RoundResultViewEntity(
        winner,
        magnetoCard.transformToUi(),
        professorCard.transformToUi()
    )