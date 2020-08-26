package felipe.pereira.war_of_suits.domain.usecase

import felipe.pereira.war_of_suits.domain.model.PokerCard
import felipe.pereira.war_of_suits.domain.model.RoundResult
import felipe.pereira.war_of_suits.view.game.enums.Suit

interface Game {

    fun getNextCards(): Pair<PokerCard, PokerCard>
    fun saveRound(round: RoundResult)
    fun setSuitPriority(suits: List<Suit>)
    fun setPlayersCards(magnetoC: List<PokerCard>, professorC: List<PokerCard>)
    fun getSuitsPriority(): List<Suit>
}