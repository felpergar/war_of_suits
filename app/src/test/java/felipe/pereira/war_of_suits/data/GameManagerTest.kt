package felipe.pereira.war_of_suits.data

import felipe.pereira.war_of_suits.common.shouldBe
import felipe.pereira.war_of_suits.domain.model.PokerCard
import felipe.pereira.war_of_suits.view.game.enums.CardValue
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
    fun `should return the firts cards for each player that were set it`() {
        val magnetoCards = listOf(PokerCard(CardValue.SEVEN, Suit.CLUBS), PokerCard(CardValue.EIGHT, Suit.SPADES))
        val professorCards = listOf(PokerCard(CardValue.ONE, Suit.HEARTS), PokerCard(CardValue.TEN, Suit.CLUBS))

        gameManager.setPlayersCards(magnetoCards, professorCards)
        val result = gameManager.getNextCards()

        result.first.number shouldBe magnetoCards.first().number
        result.first.suit shouldBe magnetoCards.first().suit
        result.second.number shouldBe professorCards.first().number
        result.second.suit shouldBe professorCards.first().suit
    }
}