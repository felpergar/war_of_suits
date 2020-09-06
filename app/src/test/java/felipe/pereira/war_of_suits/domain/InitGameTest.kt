package felipe.pereira.war_of_suits.domain

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import felipe.pereira.war_of_suits.common.shouldBe
import felipe.pereira.war_of_suits.domain.model.PokerCard
import felipe.pereira.war_of_suits.domain.usecase.Deck
import felipe.pereira.war_of_suits.domain.usecase.Game
import felipe.pereira.war_of_suits.domain.usecase.InitGame
import felipe.pereira.war_of_suits.view.game.enums.CardValue
import felipe.pereira.war_of_suits.view.game.enums.Suit
import org.junit.Before
import org.junit.Test

class InitGameTest {

    private lateinit var initGame: InitGame
    private lateinit var game: Game
    private lateinit var deck: Deck

    @Before
    fun `set up`() {
        game = mock()
        deck = mock()
        initGame = InitGame(deck, game, mock(), mock())

        val magnetoCards =
            listOf(PokerCard(CardValue.SEVEN, Suit.CLUBS), PokerCard(CardValue.EIGHT, Suit.SPADES))
        val professorCards =
            listOf(PokerCard(CardValue.ONE, Suit.HEARTS), PokerCard(CardValue.TEN, Suit.CLUBS))
        whenever(deck.getCardsShuffled()).thenReturn(Pair(magnetoCards, professorCards))
    }

    @Test
    fun `should set in game cards return by deck`() {

        val magnetoCaptor = argumentCaptor<List<PokerCard>>()
        val professorCaptor = argumentCaptor<List<PokerCard>>()

        initGame.buildUseCaseSingle(Unit).test()

        verify(game).setPlayersCards(magnetoCaptor.capture(), professorCaptor.capture())
    }

    @Test
    fun `should set in game suits return by deck`() {
        val suitsPriority =   listOf(
            Suit.DIAMONDS,
            Suit.HEARTS,
            Suit.SPADES,
            Suit.CLUBS
        )
        whenever(deck.getSuitPriority()).thenReturn(suitsPriority)
        val captor = argumentCaptor<List<Suit>>()

        initGame.buildUseCaseSingle(Unit).test()

        verify(game).setSuitPriority(captor.capture())
        captor.firstValue[0] shouldBe suitsPriority[0]
        captor.firstValue[1] shouldBe suitsPriority[1]
        captor.firstValue[2] shouldBe suitsPriority[2]
        captor.firstValue[3] shouldBe suitsPriority[3]

    }
}