package felipe.pereira.war_of_suits.view.game

import androidx.lifecycle.LiveData
import felipe.pereira.war_of_suits.view.common.Presenter
import felipe.pereira.war_of_suits.view.game.cardsmanager.PokerCardViewEntity
import felipe.pereira.war_of_suits.view.game.cardsmanager.Result

class GamePresenter(
    private val cardsManager: CardsManager = CardsManager()
) : Presenter<GamePresenter.GameView>() {

    val currentScorePlayerOne: LiveData<List<PokerCardViewEntity>> = cardsManager.discardedCardsPlayerOneLiveData
    val currentScorePlayerTwo: LiveData<List<PokerCardViewEntity>> = cardsManager.discardedCardsPlayerTwoLiveData

    override fun onViewAttached() {
        getNullableView()?.initView()
        initGame()
    }

    private fun initGame() {
        cardsManager.getCardsShuffled()

    }

    fun playRound() {
        if (cardsManager.cardsPlayerOne.isNotEmpty()) {
            val result = cardsManager.playRound()
            getNullableView()?.showResult(result)
        } else {
            val finalResult = when {
                cardsManager.discardedCardsPlayerOne.size > cardsManager.discardedCardsPlayerTwo.size -> Result.ONE
                cardsManager.discardedCardsPlayerOne.size < cardsManager.discardedCardsPlayerTwo.size -> Result.TWO
                else -> Result.EQUAL
            }
            getNullableView()?.showFinalResult(finalResult)
        }
    }

    interface GameView : View {
        fun showResult(result: Result)
        fun showFinalResult(result: Result)
        fun initView()
    }
}