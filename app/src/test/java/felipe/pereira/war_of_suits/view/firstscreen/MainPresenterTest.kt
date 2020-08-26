package felipe.pereira.war_of_suits.view.firstscreen

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import java.util.*

class MainPresenterTest {

    private lateinit var presenter: MainPresenter
    @Mock
    private lateinit var view: MainActivity

    @Before
    fun `set up`() {
        presenter = MainPresenter()
        view = mock()

        presenter.attachView(view)
    }

    @Test
    fun `should call startGameActivity when onStartGameButtonClicked is executed`() {

        presenter.onStartGameButtonClicked()

        verify(view).startGameActivity()
    }
}