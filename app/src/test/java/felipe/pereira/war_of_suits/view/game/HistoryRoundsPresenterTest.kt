package felipe.pereira.war_of_suits.view.game

import com.nhaarman.mockitokotlin2.*
import felipe.pereira.war_of_suits.common.shouldBe
import felipe.pereira.war_of_suits.domain.model.PokerCard
import felipe.pereira.war_of_suits.domain.model.RoundResult
import felipe.pereira.war_of_suits.domain.usecase.GetRounds
import felipe.pereira.war_of_suits.view.game.enums.CardValue
import felipe.pereira.war_of_suits.view.game.enums.Result
import felipe.pereira.war_of_suits.view.game.enums.Suit
import felipe.pereira.war_of_suits.view.game.model.RoundResultViewEntity
import felipe.pereira.war_of_suits.view.game.roundsresult.HistoryRoundsActivity
import felipe.pereira.war_of_suits.view.game.roundsresult.HistoryRoundsPresenter
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class HistoryRoundsPresenterTest {

    private lateinit var presenter: HistoryRoundsPresenter

    @Mock
    private lateinit var view: HistoryRoundsActivity

    @Mock
    private lateinit var getRounds: GetRounds

    @Before
    fun `set up`() {
        view = mock()
        getRounds = mock()

        presenter = HistoryRoundsPresenter(getRounds)
    }

    @Test
    fun `should execute initView when view is attached`() {
        whenever(getRounds.execute(any())).thenReturn(Single.just(listOf()))

        presenter.attachView(view)

        verify(view).initView()
    }

    @Test
    fun `should execute setItems in view when view is attached`() {
        val round1 = RoundResult(
            Result.EQUAL, PokerCard(CardValue.A, Suit.SPADES), PokerCard(
                CardValue.EIGHT, Suit.HEARTS
            )
        )
        val round2 = RoundResult(
            Result.MAGNETO,
            PokerCard(CardValue.K, Suit.HEARTS),
            PokerCard(CardValue.SEVEN, Suit.DIAMONDS)
        )
        val rounds = listOf(round1, round2)
        val captor = argumentCaptor<List<RoundResultViewEntity>>()
        whenever(getRounds.execute(any())).thenReturn(Single.just(rounds))

        presenter.attachView(view)

        verify(view).setItems(captor.capture())
        captor.firstValue[0].winner shouldBe round1.winner
        captor.firstValue[0].magnetoCard.suit shouldBe round1.magnetoCard.suit
        captor.firstValue[0].magnetoCard.number shouldBe round1.magnetoCard.number
        captor.firstValue[0].professorCard.suit shouldBe round1.professorCard.suit
        captor.firstValue[0].professorCard.number shouldBe round1.professorCard.number
        captor.firstValue[1].winner shouldBe round2.winner
        captor.firstValue[1].magnetoCard.suit shouldBe round2.magnetoCard.suit
        captor.firstValue[1].magnetoCard.number shouldBe round2.magnetoCard.number
        captor.firstValue[1].professorCard.suit shouldBe round2.professorCard.suit
        captor.firstValue[1].professorCard.number shouldBe round2.professorCard.number


    }

}