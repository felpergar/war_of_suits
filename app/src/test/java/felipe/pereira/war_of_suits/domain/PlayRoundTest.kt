package felipe.pereira.war_of_suits.domain

import com.nhaarman.mockitokotlin2.*
import felipe.pereira.war_of_suits.common.shouldBe
import felipe.pereira.war_of_suits.domain.model.PokerCard
import felipe.pereira.war_of_suits.domain.model.RoundResult
import felipe.pereira.war_of_suits.domain.usecase.Game
import felipe.pereira.war_of_suits.domain.usecase.PlayRound
import felipe.pereira.war_of_suits.view.game.enums.CardValue
import felipe.pereira.war_of_suits.view.game.enums.Result
import felipe.pereira.war_of_suits.view.game.enums.Suit
import org.junit.Before
import org.junit.Test

class PlayRoundTest {

    private lateinit var game: Game
    private lateinit var useCase: PlayRound
    private val captor = argumentCaptor<RoundResult>()

    @Before
    fun `set up`() {
        game = mock()
        useCase = PlayRound(game, mock(), mock())
    }

    @Test
    fun `should win magneto when his card is bigger than professor´s card`() {
        val magnetoCard = PokerCard(CardValue.SEVEN, Suit.DIAMONDS)
        val professorCard = PokerCard(CardValue.ONE, Suit.DIAMONDS)
        whenever(game.getNextCards()).thenReturn(Pair(magnetoCard, professorCard))
        doNothing().`when`(game).saveRound(any())

        useCase.buildUseCaseSingle(Unit).test()

        verify(game).saveRound(captor.capture())
        captor.firstValue.winner shouldBe Result.MAGNETO
    }

    @Test
    fun `should win professor when his card is bigger than magneto´s card`() {
        val professorCard = PokerCard(CardValue.SEVEN, Suit.DIAMONDS)
        val magnetoCard = PokerCard(CardValue.ONE, Suit.DIAMONDS)
        whenever(game.getNextCards()).thenReturn(Pair(magnetoCard, professorCard))
        doNothing().`when`(game).saveRound(any())

        useCase.buildUseCaseSingle(Unit).test()

        verify(game).saveRound(captor.capture())
        captor.firstValue.winner shouldBe Result.PROFESSOR
    }

    @Test
    fun `should win magneto when number of cards are equal and his suit is better than professor´s suit`() {
        val magnetoCard = PokerCard(CardValue.SEVEN, Suit.DIAMONDS)
        val professorCard = PokerCard(CardValue.SEVEN, Suit.HEARTS)
        val suits = listOf(Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES, Suit.CLUBS)
        whenever(game.getNextCards()).thenReturn(Pair(magnetoCard, professorCard))
        whenever(game.getSuitsPriority()).thenReturn(suits)
        doNothing().`when`(game).saveRound(any())

        useCase.buildUseCaseSingle(Unit).test()

        verify(game).saveRound(captor.capture())
        captor.firstValue.winner shouldBe Result.MAGNETO
    }

    @Test
    fun `should win professor when number of cards are equal and his suit is better than magneto´s suit`() {
        val magnetoCard = PokerCard(CardValue.SEVEN, Suit.CLUBS)
        val professorCard = PokerCard(CardValue.SEVEN, Suit.HEARTS)
        val suits = listOf(Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES, Suit.CLUBS)
        whenever(game.getNextCards()).thenReturn(Pair(magnetoCard, professorCard))
        whenever(game.getSuitsPriority()).thenReturn(suits)
        doNothing().`when`(game).saveRound(any())

        useCase.buildUseCaseSingle(Unit).test()

        verify(game).saveRound(captor.capture())
        captor.firstValue.winner shouldBe Result.PROFESSOR
    }

    @Test
    fun `should save EQUAL as result when numbers and suits are equals for both`() {
        val magnetoCard = PokerCard(CardValue.SEVEN, Suit.HEARTS)
        val professorCard = PokerCard(CardValue.SEVEN, Suit.HEARTS)
        val suits = listOf(Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES, Suit.CLUBS)
        whenever(game.getNextCards()).thenReturn(Pair(magnetoCard, professorCard))
        whenever(game.getSuitsPriority()).thenReturn(suits)
        doNothing().`when`(game).saveRound(any())

        useCase.buildUseCaseSingle(Unit).test()

        verify(game).saveRound(captor.capture())
        captor.firstValue.winner shouldBe Result.EQUAL
    }
}