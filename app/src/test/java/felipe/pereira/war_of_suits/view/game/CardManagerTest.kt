package felipe.pereira.war_of_suits.view.game

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import felipe.pereira.war_of_suits.common.shouldBe
import felipe.pereira.war_of_suits.view.game.cardsmanager.PokerCardViewEntity
import felipe.pereira.war_of_suits.view.game.cardsmanager.Result
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class CardsManagerTest {

    private lateinit var cardsManager: CardsManager

    @Mock
    private lateinit var discardedCardsMagnetoLiveData: MutableLiveData<List<PokerCardViewEntity>>

    @Mock
    private lateinit var discardedCardsProfessorLiveData: MutableLiveData<List<PokerCardViewEntity>>

    @Before
    fun `set up`() {
        discardedCardsMagnetoLiveData = mock()
        discardedCardsProfessorLiveData = mock()
        cardsManager = CardsManager(discardedCardsMagnetoLiveData, discardedCardsProfessorLiveData)

        cardsManager.createCards()
    }

    @Test
    fun `should have 26 cards in Magneto´s card when initCardsShuffled is executed`() {

        cardsManager.initCardsShuffled({ })
        val magnetoCards = cardsManager.getMagnetoCards()

        magnetoCards.size shouldBe 26
    }

    @Test
    fun `should remove a card from Magneto´s cards when play round is executed`() {
        lateinit var result: Result
        lateinit var magnetoCard: PokerCardViewEntity
        lateinit var professorCard: PokerCardViewEntity

        cardsManager.initCardsShuffled({ })
        cardsManager.playRound { res, magnetoC, professorC ->
            result = res
            magnetoCard = magnetoC
            professorCard = professorC
        }
        val magnetoCards = cardsManager.getMagnetoCards()

        magnetoCards.size shouldBe 25
    }

    @Test
    fun `should increase descarded cards of winner when playRound is executed`() {  //TODO I don´t like this this. I don´t know if each case is executed
        lateinit var result: Result

        cardsManager.initCardsShuffled({ })
        cardsManager.playRound { res, magnetoC, professorC ->
            result = res
        }
        cardsManager.getMagnetoCards()

        when (result) {
            Result.MAGNETO -> cardsManager.getDiscardedCardsMagneto().size shouldBe 2
            Result.PROFESSOR -> cardsManager.getDiscardedCardsProfessor().size shouldBe 2
            Result.EQUAL -> {
                cardsManager.getDiscardedCardsMagneto().size shouldBe 0
                cardsManager.getDiscardedCardsProfessor().size shouldBe 0
            }
        }
    }

    //TODO add other tests if possible to check when is neccesary check suits
}