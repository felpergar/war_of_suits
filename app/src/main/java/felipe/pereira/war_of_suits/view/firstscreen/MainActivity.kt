package felipe.pereira.war_of_suits.view.firstscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import felipe.pereira.war_of_suits.R
import felipe.pereira.war_of_suits.view.game.GameActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.java.KoinJavaComponent.inject

class MainActivity : AppCompatActivity(), MainPresenter.MainView {

    private val presenter by inject(MainPresenter::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
    }

    override fun initView() {
        startGameButton.setOnClickListener {
            startActivity(GameActivity.getCallingIntent(this))
        }
    }
}