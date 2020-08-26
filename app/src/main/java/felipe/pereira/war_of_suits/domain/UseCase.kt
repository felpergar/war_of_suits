package felipe.pereira.war_of_suits.domain

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single

abstract class UseCase<in RQ, RS>(
    private val threadScheduler: Scheduler,
    private val postExecutionScheduler: Scheduler
) {

    internal abstract fun buildUseCaseSingle(params: RQ): Single<RS>

    fun execute(params: RQ): Single<RS> =
        buildUseCaseSingle(params).subscribeOn(threadScheduler).observeOn(postExecutionScheduler)
}