package felipe.pereira.war_of_suits.domain.usecase

import felipe.pereira.war_of_suits.domain.UseCase
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single

class InitGame(
    private val deckProvider: Deck,
    private val game: Game,
    threadScheduler: Scheduler,
    postExecutionThread: Scheduler
) : UseCase<Unit, Unit>(threadScheduler, postExecutionThread) {

    override fun buildUseCaseSingle(params: Unit): Single<Unit> = Single.create { emitter ->
        deckProvider.createCards()
        val playersCards = deckProvider.getCardsShuffled()
        game.setPlayersCards(playersCards.first, playersCards.second)
        emitter.onSuccess(Unit)
    }
}