package felipe.pereira.war_of_suits.view.common




abstract class Presenter<V : Presenter.View> {

  private var view: V? = null

  fun attachView(view: V) {
    this.view = view
    onViewAttached()
  }

  protected abstract fun onViewAttached()

  open fun detachView() {
    this.view = null
  }

  fun getNullableView() =
    try {
      getView()
    } catch (e: Exception) {
      null
    }

  private fun getView(): V = view!!

  interface View {
  }
}