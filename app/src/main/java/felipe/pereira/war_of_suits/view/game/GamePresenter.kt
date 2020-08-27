package felipe.pereira.war_of_suits.view.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import felipe.pereira.war_of_suits.domain.usecase.GetSuitPriority
import felipe.pereira.war_of_suits.domain.usecase.InitGame
import felipe.pereira.war_of_suits.domain.usecase.PlayRound
import felipe.pereira.war_of_suits.view.common.Presenter
import felipe.pereira.war_of_suits.view.game.enums.Result
import felipe.pereira.war_of_suits.view.game.enums.Suit
import felipe.pereira.war_of_suits.view.game.model.PokerCardViewEntity
import felipe.pereira.war_of_suits.view.game.model.RoundResultViewEntity
import felipe.pereira.war_of_suits.view.game.model.transformToUi

class GamePresenter(
    private val initGame: InitGame,
    private val playRound: PlayRound,
    private val getSuitPriority: GetSuitPriority,
    private val currentScoreMagnetoMutableLiveData: MutableLiveData<String> = MutableLiveData<String>(),
    private val currentScoreProfessorMutableLiveData: MutableLiveData<String> = MutableLiveData<String>()
) : Presenter<GamePresenter.GameView>() {

    private val discardedCardsMagneto = mutableListOf<PokerCardViewEntity>()
    private val discardedCardsProfessor = mutableListOf<PokerCardViewEntity>()

    val currentScoreMagnetoLiveData: LiveData<String> = currentScoreMagnetoMutableLiveData
    val currentScoreProfessorLiveData: LiveData<String> = currentScoreProfessorMutableLiveData

    override fun onViewAttached() {
        getNullableView()?.initView()
        initGame()
        setSuitsPriority()
    }

    private fun initGame() {
        initGame.execute(Unit).subscribeAndAddToDisposables(
            { },
            { } //I don´t manage error due to time. I´ll would show a notification for the user and leave the state like before the action
        )
    }

    private fun setSuitsPriority() {
        getSuitPriority.execute(Unit).subscribeAndAddToDisposables(
            {
                getNullableView()?.showSuitsPriority(it)
            },
            { } //I don´t manage error due to time. I´ll would show a notification for the user and leave the state like before the action
        )
    }

    fun playRound() {
        playRound.execute(Unit).subscribeAndAddToDisposables(
            {
                showResult(it.transformToUi())
            },
            { } //I don´t manage error due to time. I´ll would show a notification for the user and leave the state like before the action
        )
    }

    private fun showResult(round: RoundResultViewEntity) {
        getNullableView()?.showResult(round)

        if (round.winner == Result.MAGNETO) {
            discardedCardsMagneto.addAll(listOf(round.magnetoCard, round.professorCard))
            currentScoreMagnetoMutableLiveData.postValue(discardedCardsMagneto.size.toString())
        } else {
            discardedCardsProfessor.addAll(listOf(round.magnetoCard, round.professorCard))
            currentScoreProfessorMutableLiveData.postValue(discardedCardsProfessor.size.toString())
        }

        if ((discardedCardsMagneto.size + discardedCardsProfessor.size) == MAX_CARDS_IN_DECK) {
            val finalResult = when {
                discardedCardsMagneto.size > discardedCardsProfessor.size -> Result.MAGNETO  //Here I haven´t put use case to know the solution because I already have the data in presenter to paint scores
                discardedCardsMagneto.size < discardedCardsProfessor.size -> Result.PROFESSOR
                else -> Result.EQUAL
            }
            getNullableView()?.showFinalResult(finalResult)
        }
    }

    fun resetGame() {
        initGame()
        setSuitsPriority()
        discardedCardsMagneto.clear()
        discardedCardsProfessor.clear()
        currentScoreMagnetoMutableLiveData.postValue(discardedCardsMagneto.size.toString())
        currentScoreProfessorMutableLiveData.postValue(discardedCardsProfessor.size.toString())
        getNullableView()?.resetView()
    }

    interface GameView : View {
        fun showResult(round: RoundResultViewEntity)
        fun showFinalResult(result: Result)
        fun initView()
        fun showSuitsPriority(suitsPriority: List<Suit>)
        fun resetView()
    }

    companion object {
        private const val MAX_CARDS_IN_DECK = 56
    }
}