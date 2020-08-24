package felipe.pereira.war_of_suits.view.firstscreen

import felipe.pereira.war_of_suits.view.common.Presenter

class MainPresenter : Presenter<MainPresenter.MainView>() {

    override fun onViewAttached() {
        getNullableView()?.initView()
    }

    interface MainView : View {
        fun initView()
    }
}