package felipe.pereira.war_of_suits.domain.usecase

import felipe.pereira.war_of_suits.domain.UseCase
import felipe.pereira.war_of_suits.domain.model.RoundResult
import felipe.pereira.war_of_suits.view.game.enums.Result
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single

class PlayRound(
    private val game: Game,
    threadScheduler: Scheduler,
    postExecutionThread: Scheduler
) : UseCase<Unit, RoundResult>(threadScheduler, postExecutionThread) {

    override fun buildUseCaseSingle(params: Unit): Single<RoundResult> {
       return Single.create { emitter ->
            val cardsToPlay = game.getNextCards()
            val magnetoCard = cardsToPlay.first
            val professorCard = cardsToPlay.second

            val result = when {
                magnetoCard.number > professorCard.number -> Result.MAGNETO
                magnetoCard.number < professorCard.number -> Result.PROFESSOR
                else -> {
                    val suitsPriority = game.getSuitsPriority()
                    val indexSuitPlayerOne = suitsPriority.indexOfFirst { it == magnetoCard.suit }
                    val indexSuitPlayerTwo = suitsPriority.indexOfFirst { it == professorCard.suit }
                    when {
                        indexSuitPlayerOne < indexSuitPlayerTwo -> Result.MAGNETO
                        indexSuitPlayerOne > indexSuitPlayerTwo -> Result.PROFESSOR
                        else -> Result.EQUAL
                    }
                }
            }

            val roundResult = RoundResult(result, magnetoCard, professorCard)
            game.saveRound(roundResult)
            emitter.onSuccess(roundResult)
        }
    }
}