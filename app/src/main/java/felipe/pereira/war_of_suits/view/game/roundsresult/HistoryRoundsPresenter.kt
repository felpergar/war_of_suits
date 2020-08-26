package felipe.pereira.war_of_suits.view.game.roundsresult

import felipe.pereira.war_of_suits.domain.usecase.GetRounds
import felipe.pereira.war_of_suits.view.common.Presenter
import felipe.pereira.war_of_suits.view.game.model.RoundResultViewEntity
import felipe.pereira.war_of_suits.view.game.model.transformToUi

class HistoryRoundsPresenter(
    private val getRounds: GetRounds
) : Presenter<HistoryRoundsPresenter.HistoryRoundsView>() {

    override fun onViewAttached() {
        getNullableView()?.initView()
        getRounds()
    }

    private fun getRounds() {
        getRounds.execute(Unit).subscribeAndAddToDisposables(
            { rounds ->
                val results = rounds.map { it.transformToUi() }
                getNullableView()?.setItems(results)
            },
            {

            }
        )
    }

    interface HistoryRoundsView : View {
        fun initView()
        fun setItems(rounds: List<RoundResultViewEntity>)
    }
}