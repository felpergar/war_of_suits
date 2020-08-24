package felipe.pereira.war_of_suits.view.game

import felipe.pereira.war_of_suits.view.common.Presenter

class GamePresenter(
    private val cardsManager: CardsManager = CardsManager()
) : Presenter<GamePresenter.GameView>() {

    override fun onViewAttached() {
        initGame()
    }

    private fun initGame() {
        cardsManager.getCardsShuffled()
    }

    interface GameView : View {

    }
}