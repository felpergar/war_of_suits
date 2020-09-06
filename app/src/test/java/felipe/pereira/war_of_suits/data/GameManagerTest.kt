package felipe.pereira.war_of_suits.data

import felipe.pereira.war_of_suits.common.shouldBe
import felipe.pereira.war_of_suits.domain.model.PokerCard
import felipe.pereira.war_of_suits.domain.model.RoundResult
import felipe.pereira.war_of_suits.view.game.enums.CardValue
import felipe.pereira.war_of_suits.view.game.enums.Result
import felipe.pereira.war_of_suits.view.game.enums.Suit
import org.junit.Test

class GameManagerTest {

    private val gameManager = GameManager()

    @Test
    fun `should return same suits list that was set it`() {
        val suits = listOf(Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES, Suit.CLUBS)

        gameManager.setSuitPriority(suits)
        val result = gameManager.getSuitsPriority()

        result[0] shouldBe suits[0]
        result[1] shouldBe suits[1]
        result[2] shouldBe suits[2]
        result[3] shouldBe suits[3]
    }

    @Test
    fun `should return the first cards for each player that were set it`() {
        val magnetoCards =
            listOf(PokerCard(CardValue.SEVEN, Suit.CLUBS), PokerCard(CardValue.EIGHT, Suit.SPADES))
        val professorCards =
            listOf(PokerCard(CardValue.ONE, Suit.HEARTS), PokerCard(CardValue.TEN, Suit.CLUBS))

        gameManager.setPlayersCards(magnetoCards, professorCards)
        val result = gameManager.getNextCards()

        result.first.number shouldBe magnetoCards.first().number
        result.first.suit shouldBe magnetoCards.first().suit
        result.second.number shouldBe professorCards.first().number
        result.second.suit shouldBe professorCards.first().suit
    }

    @Test
    fun `should reset round when setPlayersCards is executed`() {

        gameManager.setPlayersCards(listOf(), listOf())
        val result = gameManager.getRounds()

        result.size shouldBe 0
    }

    @Test
    fun `should add correctly the round when saveRound is executed`() {
        val round1 = RoundResult(
            Result.PROFESSOR,
            PokerCard(CardValue.SEVEN, Suit.CLUBS),
            PokerCard(CardValue.EIGHT, Suit.SPADES)
        )
        val round2 = RoundResult(
            Result.MAGNETO,
            PokerCard(CardValue.ONE, Suit.HEARTS),
            PokerCard(CardValue.TEN, Suit.CLUBS)
        )

        gameManager.saveRound(round1)
        gameManager.saveRound(round2)
        val rounds = gameManager.getRounds()

        rounds.first().winner shouldBe round1.winner
        rounds.first().professorCard shouldBe round1.professorCard
        rounds.first().magnetoCard shouldBe round1.magnetoCard
        rounds[1].winner shouldBe round2.winner
        rounds[1].professorCard shouldBe round2.professorCard
        rounds[1].magnetoCard shouldBe round2.magnetoCard
    }

    @Test (expected = GameCrashedException::class)
    fun `should throw GameCrashedException when resetLastRound is executed and lastCardPlayed is null`() {

        gameManager.resetLastRound()
    }

    @Test
    fun `should return the last cards played to its player after executed resetRound`() {
        val magnetoCards =
            listOf(PokerCard(CardValue.SEVEN, Suit.CLUBS), PokerCard(CardValue.EIGHT, Suit.SPADES))
        val professorCards =
            listOf(PokerCard(CardValue.ONE, Suit.HEARTS), PokerCard(CardValue.TEN, Suit.CLUBS))

        gameManager.setPlayersCards(magnetoCards, professorCards)
        val firstResult = gameManager.getNextCards()
        gameManager.resetLastRound()
        val secondResult = gameManager.getNextCards()

        firstResult.first shouldBe secondResult.first
        firstResult.second shouldBe secondResult.second
    }
}