package felipe.pereira.war_of_suits.view.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import felipe.pereira.war_of_suits.R
import felipe.pereira.war_of_suits.view.game.cardsmanager.Result
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
        initLiveData()
    }

    private fun initLiveData() {
        presenter.currentScorePlayerOne.observe(this, Observer {
            scorePlayer1TextView.text = it.size.toString()
        })
        presenter.currentScorePlayerTwo.observe(this, Observer {
            scorePlayer2TextView.text = it.size.toString()
        })
    }

    override fun showResult(result: Result) {
        roundResultTextView.text = when (result) {
            Result.ONE -> "Player one wins the round"
            Result.TWO -> "Player two wins the round"
            Result.EQUAL -> "The round is equal"
        }
    }

    override fun showFinalResult(result: Result) {
        roundResultTextView.visibility = GONE
        finalResultTextView.visibility = VISIBLE
        finalResultTextView.text = when (result) {
            Result.ONE -> "Player one wins the game"
            Result.TWO -> "Player two wins the game"
            Result.EQUAL -> "The game is equal"
        }
    }

    companion object {
        fun getCallingIntent(context: Context): Intent = Intent(context, GameActivity::class.java)
    }
}