package felipe.pereira.war_of_suits.data

import felipe.pereira.war_of_suits.domain.usecase.Game
import felipe.pereira.war_of_suits.domain.model.PokerCard
import felipe.pereira.war_of_suits.domain.model.RoundResult
import felipe.pereira.war_of_suits.view.game.enums.Result
import felipe.pereira.war_of_suits.view.game.enums.Suit

class GameManager: Game {

    private val rounds: MutableList<RoundResult> = mutableListOf()

    override fun getNextCards(): Pair<PokerCard, PokerCard> {
        val cards = Pair(magnetoCards.first(), professorCards.first())
        magnetoCards.remove(magnetoCards.first())
        professorCards.remove(professorCards.first())
        return cards
    }

    override fun saveRound(round: RoundResult) {
        when (round.winner) {
            Result.MAGNETO -> discardedCardsMagneto.addAll(listOf(round.magnetoCard, round.professorCard))
            Result.PROFESSOR -> discardedCardsProfessor.addAll(listOf(round.magnetoCard, round.professorCard))
            Result.EQUAL -> { }
        }
        rounds.add(round)
    }

    override fun setSuitPriority(suits: List<Suit>) {
        suitPriority = suits
    }

    override fun setPlayersCards(magnetoC: List<PokerCard>, professorC: List<PokerCard>) {
        magnetoCards.clear()
        professorCards.clear()
        rounds.clear()
        magnetoCards.addAll(magnetoC)
        professorCards.addAll(professorC)
    }

    override fun getSuitsPriority(): List<Suit> = suitPriority

    override fun getRounds(): List<RoundResult> = rounds.toList()

    companion object {
        private lateinit var suitPriority: List<Suit>
        private var magnetoCards: MutableList<PokerCard> = mutableListOf()
        private var professorCards: MutableList<PokerCard> = mutableListOf()
        private val discardedCardsMagneto: MutableList<PokerCard> = mutableListOf()
        private val discardedCardsProfessor: MutableList<PokerCard> = mutableListOf()

    }
}