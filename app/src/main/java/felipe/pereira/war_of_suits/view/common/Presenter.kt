package felipe.pereira.war_of_suits.view.common

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


abstract class Presenter<V : Presenter.View> {

  private var view: V? = null
  private var disposables = CompositeDisposable()

  fun attachView(view: V) {
    this.view = view
    onViewAttached()
  }

  protected abstract fun onViewAttached()

  open fun detachView() {
    disposables.dispose()
    this.view = null
  }

  fun getNullableView() =
    try {
      getView()
    } catch (e: Exception) {
      null
    }

  fun <T> Single<T>.subscribeAndAddToDisposables(onSuccess: (T) -> Unit = {}, onError: (throwable: Throwable) -> Unit = {}): Disposable {
    val disposable = this.subscribe({ onSuccess(it) }, { onError(it) })
    disposables.add(disposable)
    return disposable
  }

  private fun getView(): V = view!!

  interface View {
  }
}