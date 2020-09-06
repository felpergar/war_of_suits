package felipe.pereira.war_of_suits.view.game.roundsresult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import felipe.pereira.war_of_suits.R
import felipe.pereira.war_of_suits.view.common.showDialog
import felipe.pereira.war_of_suits.view.game.model.RoundResultViewEntity
import kotlinx.android.synthetic.main.activity_history_rounds.*
import org.koin.java.KoinJavaComponent.inject

class HistoryRoundsActivity : AppCompatActivity(), HistoryRoundsPresenter.HistoryRoundsView {

    private val presenter: HistoryRoundsPresenter by inject(HistoryRoundsPresenter::class.java)
    private val roundsAdapter by lazy { RoundsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_rounds)
        presenter.attachView(this)
    }

    override fun initView() {
        with(roundRecyclerView) {
            layoutManager = LinearLayoutManager(context!!)
            setHasFixedSize(true)
            if (adapter == null)
                adapter = roundsAdapter
        }
    }

    override fun setItems(rounds: List<RoundResultViewEntity>) {
        roundsAdapter.setItems(rounds)
    }

    override fun showDialogError() {
        this.showDialog(
            R.string.rounds_error,
            R.string.yes,
            presenter::getRounds,
            R.string.no,
            ::finish,
            false
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    companion object {
        fun getCallingIntent(context: Context): Intent =
            Intent(context, HistoryRoundsActivity::class.java)
    }
}