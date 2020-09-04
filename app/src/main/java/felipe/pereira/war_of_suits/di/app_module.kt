package felipe.pereira.war_of_suits.di

import felipe.pereira.war_of_suits.data.DeckProvider
import felipe.pereira.war_of_suits.data.GameManager
import felipe.pereira.war_of_suits.domain.usecase.*
import felipe.pereira.war_of_suits.view.firstscreen.MainPresenter
import felipe.pereira.war_of_suits.view.game.GamePresenter
import felipe.pereira.war_of_suits.view.game.roundsresult.HistoryRoundsPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.dsl.module

val appModule = module {

    single<Deck> { DeckProvider() }
    single<Game> { GameManager() }

    factory { InitGame(get(), get(), Schedulers.io(), AndroidSchedulers.mainThread()) }
    factory { PlayRound(get(), Schedulers.io(), AndroidSchedulers.mainThread()) }
    factory { ResetLastRound(get(), Schedulers.io(), AndroidSchedulers.mainThread()) }
    factory { GetSuitPriority(get(), get(), Schedulers.io(), AndroidSchedulers.mainThread()) }
    factory { GetRounds(get(), Schedulers.io(), AndroidSchedulers.mainThread()) }
    
    factory { GamePresenter(get(), get(), get(), get()) }
    factory { MainPresenter() }
    factory { HistoryRoundsPresenter(get()) }
}