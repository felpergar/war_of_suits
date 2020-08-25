package felipe.pereira.war_of_suits.view.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import felipe.pereira.war_of_suits.R
import felipe.pereira.war_of_suits.view.game.cardsmanager.PokerCardViewEntity
import felipe.pereira.war_of_suits.view.game.cardsmanager.Result
import felipe.pereira.war_of_suits.view.game.cardsmanager.Suit
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity(), GamePresenter.GameView {

    private val presenter by lazy { GamePresenter() }

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

    override fun showSuitsPriority(suitsPriority: MutableList<Suit>) {
        val stringList = suitsPriority.map { it.name }
        currentSuitPriorityTextView.text = stringList.fold("") {acc, new -> "$acc $new,"}
    }

    private fun initLiveData() {
        presenter.currentScoreMagneto.observe(this, Observer {
            currentScoreMagnetoTextView.text = it.size.toString()
        })
        presenter.currentScoreProfessor.observe(this, Observer {
            currentScoreProfessorTextView.text = it.size.toString()
        })
    }

    override fun showResult(
        result: Result,
        magnetoCard: PokerCardViewEntity,
        professorCard: PokerCardViewEntity
    ) {
        roundResultTextView.text = when (result) {
            Result.MAGNETO -> getString(R.string.round_result, getString(R.string.magneto))
            Result.PROFESSOR -> getString(R.string.round_result, getString(R.string.professor))
            Result.EQUAL -> getString(R.string.round_equal)
        }

        cardPlayedByMagneto.text = String.format(
            getString(R.string.card_played),
            getString(R.string.magneto),
            "${magnetoCard.number}",
            magnetoCard.suit.name
        )
        cardPlayedByProfessor.text = String.format(
            getString(R.string.card_played),
            getString(R.string.professor),
            "${professorCard.number}",
            professorCard.suit.name
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
    }

    override fun resetView() {
        currentScoreMagnetoTextView.text = getString(R.string.start_score)
        currentScoreMagnetoTextView.text = getString(R.string.start_score)
        cardPlayedByMagneto.text = STRING_EMPTY
        cardPlayedByProfessor.text = STRING_EMPTY
        roundResultTextView.text = STRING_EMPTY
        roundResultTextView.visibility = VISIBLE
        finalResultTextView.visibility = GONE
    }

    companion object {
        fun getCallingIntent(context: Context): Intent = Intent(context, GameActivity::class.java)
        private const val STRING_EMPTY = ""
    }
}