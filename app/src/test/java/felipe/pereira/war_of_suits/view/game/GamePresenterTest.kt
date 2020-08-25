package felipe.pereira.war_of_suits.view.game

import com.nhaarman.mockitokotlin2.*
import felipe.pereira.war_of_suits.common.shouldBe
import felipe.pereira.war_of_suits.view.game.cardsmanager.PokerCardViewEntity
import felipe.pereira.war_of_suits.view.game.cardsmanager.Result
import felipe.pereira.war_of_suits.view.game.cardsmanager.Suit
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GamePresenterTest {

    private lateinit var presenter: GamePresenter
    @Mock private lateinit var cardManager: CardsManager
    @Mock private lateinit var view: GameActivity

    @Before
    fun `set up`() {
        cardManager = mock()
        view = mock()
        presenter = GamePresenter(cardManager)

        presenter.attachView(view)
    }

    @Test
    fun `should execute createCards when view is attached`() {

        verify(cardManager).createCards()
    }

    @Test
    fun `should execute initCardsShuffled when view is attached`() {

        verify(cardManager).initCardsShuffled(any())
    }

    @Test
    fun `should execute playRound in cardsManager when Magneto´s cards list is not Empty after execute playRound`() {
        whenever(cardManager.getMagnetoCards()).thenReturn(mutableListOf(PokerCardViewEntity(1, Suit.DIAMONDS)))

        presenter.playRound()

        verify(cardManager).playRound(any())
    }

    @Test
    fun `should execute showFinalResult in view when Magneto´s cards list is empty after execute playRound`() {
        whenever(cardManager.getMagnetoCards()).thenReturn(mutableListOf())

        presenter.playRound()

        verify(view).showFinalResult(any())
    }

    @Test
    fun `should execute initCardsShuffled when resetGame is executed`() {

        presenter.resetGame()

        verify(cardManager, times(2)).initCardsShuffled(any())
    }

    @Test
    fun `should execute resetView in view when resetGame is executed`() {

        presenter.resetGame()

        verify(view).resetView()
    }

    @Test
    fun `should send Magneto as result when his discarded cards are bigger than Professor´s cards and Magneto´s cards list is empty when playRound is executed`(){
        whenever(cardManager.getDiscardedCardsMagneto()).thenReturn(listOf(getPokerCard()))
        whenever(cardManager.getDiscardedCardsProfessor()).thenReturn(listOf())
        whenever(cardManager.getMagnetoCards()).thenReturn(listOf())
        val captor = argumentCaptor<Result>()

        presenter.playRound()

        verify(view).showFinalResult(captor.capture())
        captor.firstValue shouldBe Result.MAGNETO
    }

    @Test
    fun `should send Professor as result when his discarded cards are bigger than Magneto´s cards and Magneto´s cards list is empty when playRound is executed`(){
        whenever(cardManager.getDiscardedCardsMagneto()).thenReturn(listOf())
        whenever(cardManager.getDiscardedCardsProfessor()).thenReturn(listOf(getPokerCard()))
        whenever(cardManager.getMagnetoCards()).thenReturn(listOf())
        val captor = argumentCaptor<Result>()

        presenter.playRound()

        verify(view).showFinalResult(captor.capture())
        captor.firstValue shouldBe Result.PROFESSOR
    }

    @Test
    fun `should send Equal as result when Magneto´s cards are equal than Professor´s cards and Magneto´s cards list is empty when playRound is executed`(){
        whenever(cardManager.getDiscardedCardsMagneto()).thenReturn(listOf())
        whenever(cardManager.getDiscardedCardsProfessor()).thenReturn(listOf())
        whenever(cardManager.getMagnetoCards()).thenReturn(listOf())
        val captor = argumentCaptor<Result>()

        presenter.playRound()

        verify(view).showFinalResult(captor.capture())
        captor.firstValue shouldBe Result.EQUAL
    }

    private fun getPokerCard() = PokerCardViewEntity(1, Suit.DIAMONDS)
}