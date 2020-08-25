package felipe.pereira.war_of_suits.view.game

import androidx.lifecycle.LiveData
import felipe.pereira.war_of_suits.view.common.Presenter
import felipe.pereira.war_of_suits.view.game.cardsmanager.PokerCardViewEntity
import felipe.pereira.war_of_suits.view.game.cardsmanager.Result
import felipe.pereira.war_of_suits.view.game.cardsmanager.Suit

class GamePresenter(
    private val cardsManager: CardsManager = CardsManager()
) : Presenter<GamePresenter.GameView>() {

    val currentScoreMagneto: LiveData<List<PokerCardViewEntity>> =
        cardsManager.discardedCardsMagnetoLiveData
    val currentScoreProfessor: LiveData<List<PokerCardViewEntity>> =
        cardsManager.discardedCardsProfessorLiveData

    override fun onViewAttached() {
        getNullableView()?.initView()
        initGame()
    }

    private fun initGame() {
        cardsManager.createCards()
        cardsManager.initCardsShuffled(::showSuitsPriority)
    }

    private fun showSuitsPriority(suitsPriority: MutableList<Suit>) {
        getNullableView()?.showSuitsPriority(suitsPriority)
    }

    fun playRound() {
        if (cardsManager.getMagnetoCards().isNotEmpty()) {
            cardsManager.playRound(::showResult)
        } else {
            val finalResult = when {
                cardsManager.getDiscardedCardsMagneto().size > cardsManager.getDiscardedCardsProfessor().size -> Result.MAGNETO
                cardsManager.getDiscardedCardsMagneto().size < cardsManager.getDiscardedCardsProfessor().size -> Result.PROFESSOR
                else -> Result.EQUAL
            }
            getNullableView()?.showFinalResult(finalResult)
        }
    }

    private fun showResult(
        result: Result,
        magnetoCard: PokerCardViewEntity,
        professorCard: PokerCardViewEntity
    ) {
        getNullableView()?.showResult(result, magnetoCard, professorCard)
    }

    fun resetGame() {
        cardsManager.initCardsShuffled(::showSuitsPriority)
        getNullableView()?.resetView()
    }

    interface GameView : View {
        fun showResult(result: Result, magnetoCard: PokerCardViewEntity, professorCard: PokerCardViewEntity)
        fun showFinalResult(result: Result)
        fun initView()
        fun showSuitsPriority(suitsPriority: MutableList<Suit>)
        fun resetView()
    }
}