package felipe.pereira.war_of_suits.view.game

import felipe.pereira.war_of_suits.view.common.Presenter

class GamePresenter: Presenter<GamePresenter.GameView>() {

    override fun onViewAttached() {
    }

    interface GameView: View {

    }
}