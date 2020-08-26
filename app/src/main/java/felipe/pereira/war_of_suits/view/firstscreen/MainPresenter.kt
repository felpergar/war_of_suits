package felipe.pereira.war_of_suits.view.firstscreen

import felipe.pereira.war_of_suits.view.common.Presenter

class MainPresenter : Presenter<MainPresenter.MainView>() {

    override fun onViewAttached() {
        getNullableView()?.initView()
    }

    fun onStartGameButtonClicked() {
        getNullableView()?.startGameActivity()
    }

    interface MainView : View {
        fun initView()
        fun startGameActivity()
    }
}