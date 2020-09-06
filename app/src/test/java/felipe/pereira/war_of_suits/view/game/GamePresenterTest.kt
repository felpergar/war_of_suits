package felipe.pereira.war_of_suits.view.game

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.*
import felipe.pereira.war_of_suits.common.shouldBe
import felipe.pereira.war_of_suits.domain.model.PokerCard
import felipe.pereira.war_of_suits.domain.model.RoundResult
import felipe.pereira.war_of_suits.domain.usecase.InitGame
import felipe.pereira.war_of_suits.domain.usecase.PlayRound
import felipe.pereira.war_of_suits.view.game.enums.CardValue
import felipe.pereira.war_of_suits.view.game.enums.Result
import felipe.pereira.war_of_suits.view.game.enums.Suit
import felipe.pereira.war_of_suits.view.game.model.RoundResultViewEntity
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GamePresenterTest {

    private lateinit var presenter: GamePresenter
    private lateinit var initGame: InitGame
    private lateinit var playRound: PlayRound
    private lateinit var getSuitPriority: GetSuitPriority
    private lateinit var currentScoreMagnetoMutableLiveData: MutableLiveData<String>
    private lateinit var currentScoreProfessorMutableLiveData: MutableLiveData<String>

    @Mock private lateinit var view: GameActivity

    @Before
    fun `set up`() {
        view = mock()
        initGame = mock()
        playRound = mock()
        getSuitPriority = mock()
        currentScoreMagnetoMutableLiveData = mock()
        currentScoreProfessorMutableLiveData = mock()
        presenter = GamePresenter(initGame, playRound, getSuitPriority, currentScoreMagnetoMutableLiveData, currentScoreProfessorMutableLiveData)

        whenever(initGame.execute(any())).thenReturn(Single.just(Unit))
        whenever(getSuitPriority.execute(any())).thenReturn(Single.just(listOf()))
    }

    @Test
    fun `should execute initGame when view is attached`() {

        presenter.attachView(view)

        verify(initGame).execute(any())
    }

    @Test
    fun `should execute getSuitPriority when view is attached`() {

        presenter.attachView(view)

        verify(getSuitPriority).execute(any())
    }

    @Test
    fun `should execute showSuitsPriority when getSuitPriority was successful`() {
        val suits = listOf(Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES)
        val captor = argumentCaptor<List<Suit>>()
        whenever(getSuitPriority.execute(any())).thenReturn(Single.just(suits))

        presenter.attachView(view)

        verify(view).showSuitsPriority(captor.capture())
        captor.firstValue[0] shouldBe suits[0]
        captor.firstValue[1] shouldBe suits[1]
        captor.firstValue[2] shouldBe suits[2]
        captor.firstValue[3] shouldBe suits[3]
    }

    @Test
    fun `should execute showResult in view when playRound was successful`() {
        val result = RoundResult(Result.EQUAL, PokerCard(CardValue.A, Suit.SPADES), PokerCard(CardValue.EIGHT, Suit.HEARTS))
        whenever(playRound.execute(any())).thenReturn(Single.just(result))
        val captor = argumentCaptor<RoundResultViewEntity>()

        presenter.attachView(view)
        presenter.playRound()

        verify(view).showResult(captor.capture())
        captor.firstValue.winner shouldBe result.winner
        captor.firstValue.magnetoCard.number shouldBe result.magnetoCard.number
        captor.firstValue.magnetoCard.suit shouldBe result.magnetoCard.suit
        captor.firstValue.professorCard.number shouldBe result.professorCard.number
        captor.firstValue.professorCard.suit shouldBe result.professorCard.suit
    }

    @Test
    fun `should execute initGame when resetGame is executed`() {

        presenter.resetGame()

        verify(initGame).execute(any())
    }

    @Test
    fun `should execute getSuitPriority when resetGame is executed`() {

        presenter.resetGame()

        verify(getSuitPriority).execute(any())
    }

    @Test
    fun `should execute resetView when resetGame is executed`() {

        presenter.attachView(view)
        presenter.resetGame()

        verify(view).resetView()
    }
}