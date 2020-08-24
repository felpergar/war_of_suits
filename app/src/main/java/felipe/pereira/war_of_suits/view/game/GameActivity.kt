package felipe.pereira.war_of_suits.view.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import felipe.pereira.war_of_suits.R

class GameActivity: AppCompatActivity(), GamePresenter.GameView {

    private val presenter by lazy { GamePresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        presenter.attachView(this)
    }

    companion object {
        fun getCallingIntent(context: Context): Intent = Intent(context, GameActivity::class.java)
    }
}