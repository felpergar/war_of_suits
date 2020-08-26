package felipe.pereira.war_of_suits.domain.usecase

import felipe.pereira.war_of_suits.domain.UseCase
import felipe.pereira.war_of_suits.domain.model.RoundResult
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single

class GetRounds(
    private val game: Game,
    threadScheduler: Scheduler,
    postExecutionThread: Scheduler
): UseCase<Unit, List<RoundResult>>(threadScheduler, postExecutionThread) {

    override fun buildUseCaseSingle(params: Unit): Single<List<RoundResult>> = Single.create { emitter ->
        emitter.onSuccess(game.getRounds())
    }
}