package felipe.pereira.war_of_suits.data

import felipe.pereira.war_of_suits.data.model.PlayerCards
import felipe.pereira.war_of_suits.domain.usecase.Game
import felipe.pereira.war_of_suits.domain.model.PokerCard
import felipe.pereira.war_of_suits.domain.model.RoundResult
import felipe.pereira.war_of_suits.view.game.enums.Result
import felipe.pereira.war_of_suits.view.game.enums.Suit

class GameManager: Game {

    private val rounds: MutableList<RoundResult> = mutableListOf()
    private lateinit var suitPriority: List<Suit>
    private val magneto = PlayerCards(mutableListOf(), mutableListOf())
    private val professor = PlayerCards(mutableListOf(), mutableListOf())

    override fun getNextCards(): Pair<PokerCard, PokerCard> {
        val cards = Pair(magneto.cards.first(), professor.cards.first())
        magneto.cards.remove(magneto.cards.first())
        professor.cards.remove(professor.cards.first())
        return cards
    }

    override fun saveRound(round: RoundResult) {
        when (round.winner) {
            Result.MAGNETO -> magneto.discardedCards.addAll(listOf(round.magnetoCard, round.professorCard))
            Result.PROFESSOR -> professor.discardedCards.addAll(listOf(round.magnetoCard, round.professorCard))
            Result.EQUAL -> { }
        }
        rounds.add(round)
    }

    override fun setSuitPriority(suits: List<Suit>) {
        suitPriority = suits
    }

    override fun setPlayersCards(magnetoC: List<PokerCard>, professorC: List<PokerCard>) {
        magneto.cards.clear()
        professor.cards.clear()
        rounds.clear()
        magneto.cards.addAll(magnetoC)
        professor.cards.addAll(professorC)
    }

    override fun getSuitsPriority(): List<Suit> = suitPriority

    override fun getRounds(): List<RoundResult> = rounds.toList()
}