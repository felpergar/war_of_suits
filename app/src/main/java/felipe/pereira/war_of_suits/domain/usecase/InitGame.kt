package felipe.pereira.war_of_suits.domain.usecase

import felipe.pereira.war_of_suits.domain.UseCase
import felipe.pereira.war_of_suits.view.game.enums.Suit
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single

class InitGame(
    private val deckProvider: Deck,
    private val game: Game,
    threadScheduler: Scheduler,
    postExecutionThread: Scheduler
) : UseCase<Unit, List<Suit>>(threadScheduler, postExecutionThread) {

    override fun buildUseCaseSingle(params: Unit): Single<List<Suit>> = Single.create { emitter ->
        deckProvider.createCards()
        val playersCards = deckProvider.getCardsShuffled()
        game.setPlayersCards(playersCards.first, playersCards.second)

        val suitPriority = deckProvider.getSuitPriority()
        game.setSuitPriority(suitPriority)
        emitter.onSuccess(suitPriority)
    }
}