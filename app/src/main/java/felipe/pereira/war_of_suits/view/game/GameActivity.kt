package felipe.pereira.war_of_suits.view.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import felipe.pereira.war_of_suits.R
import felipe.pereira.war_of_suits.view.common.showDialog
import felipe.pereira.war_of_suits.view.game.enums.Result
import felipe.pereira.war_of_suits.view.game.model.RoundResultViewEntity
import felipe.pereira.war_of_suits.view.game.enums.Suit
import felipe.pereira.war_of_suits.view.game.roundsresult.HistoryRoundsActivity
import kotlinx.android.synthetic.main.activity_game.*
import org.koin.java.KoinJavaComponent.inject

class GameActivity : AppCompatActivity(), GamePresenter.GameView {

    private val presenter: GamePresenter by inject(GamePresenter::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        presenter.attachView(this)
    }

    override fun initView() {
        playGameButton.setOnClickListener { presenter.playRound() }
        resetGameButton.setOnClickListener { presenter.resetGame() }
        initLiveData()
    }

    override fun showSuitsPriority(suitsPriority: List<Suit>) {
        val stringList = suitsPriority.map { it.name }
        currentSuitPriorityTextView.text = stringList.fold("") { acc, new -> "$acc   $new" }
    }

    private fun initLiveData() {
        presenter.currentScoreMagnetoLiveData.observe(this, Observer {
            currentScoreMagnetoTextView.text = it
        })
        presenter.currentScoreProfessorLiveData.observe(this, Observer {
            currentScoreProfessorTextView.text = it
        })
    }

    override fun showResult(round: RoundResultViewEntity) {
        roundResultTextView.text = when (round.winner) {
            Result.MAGNETO -> getString(R.string.round_result, getString(R.string.magneto))
            Result.PROFESSOR -> getString(R.string.round_result, getString(R.string.professor))
            Result.EQUAL -> getString(R.string.round_equal)
        }

        cardPlayedByMagneto.text = String.format(
            getString(R.string.card_play),
            getString(R.string.magneto),
            "${round.magnetoCard.number}",
            round.magnetoCard.suit.name
        )
        cardPlayedByProfessor.text = String.format(
            getString(R.string.card_play),
            getString(R.string.professor),
            "${round.professorCard.number}",
            round.professorCard.suit.name
        )
    }

    override fun showFinalResult(result: Result) {
        roundResultTextView.visibility = GONE
        finalResultTextView.visibility = VISIBLE
        finalResultTextView.text = when (result) {
            Result.MAGNETO -> getString(R.string.final_result, getString(R.string.magneto))
            Result.PROFESSOR -> getString(R.string.final_result, getString(R.string.professor))
            Result.EQUAL -> getString(R.string.game_equal)
        }
        showRoundsDialog()
        playGameButton.isClickable = false
    }

    override fun resetView() {
        currentScoreMagnetoTextView.text = getString(R.string.start_score)
        currentScoreMagnetoTextView.text = getString(R.string.start_score)
        cardPlayedByMagneto.text = STRING_EMPTY
        cardPlayedByProfessor.text = STRING_EMPTY
        roundResultTextView.text = STRING_EMPTY
        roundResultTextView.visibility = VISIBLE
        finalResultTextView.visibility = GONE
        playGameButton.isClickable = true
    }

    private fun showRoundsDialog() {
        this.showDialog(R.string.show_rounds, R.string.yes, ::showRounds, R.string.no, { }, false)
    }

    override fun showCrashDialog() {
        this.showDialog(R.string.game_crashs, R.string.reset, presenter::resetGame, R.string.no, ::finish, false)
    }

    private fun showRounds() {
        startActivity(HistoryRoundsActivity.getCallingIntent(this))
    }

    override fun showToast(messageId: Int) {
        Toast.makeText(this, getString(messageId), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    companion object {
        fun getCallingIntent(context: Context): Intent = Intent(context, GameActivity::class.java)
        private const val STRING_EMPTY = ""
    }
}